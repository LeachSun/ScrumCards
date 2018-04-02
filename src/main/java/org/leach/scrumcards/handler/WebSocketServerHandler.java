package org.leach.scrumcards.handler;

import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.entity.message.rev.RevMessage;
import org.leach.scrumcards.processor.RevMsgProcessor;
import org.leach.scrumcards.processor.RevMsgProcessorFactory;
import org.leach.scrumcards.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @author Leach
 * @date 2017/8/5
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private WebSocketServerHandshaker handshaker;

    private RevMsgProcessorFactory revMsgProcessorFactory;

    public WebSocketServerHandler(RevMsgProcessorFactory revMsgProcessorFactory) {
        this.revMsgProcessorFactory = revMsgProcessorFactory;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("{}-active", ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("inactive: {}", ctx);

        CardsNioSocketChannel cardChannel = (CardsNioSocketChannel) ctx.channel();
        revMsgProcessorFactory.getOfflineProcessor().execute(cardChannel, null);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(channelHandlerContext, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(channelHandlerContext, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        CardsNioSocketChannel cardChannel = (CardsNioSocketChannel) ctx.channel();
        String uri = req.uri();
        int separator = uri.lastIndexOf('/');
        if (separator != 0) {
            logger.warn("{}-Illegality uri path: {}", cardChannel.id(), uri);
            closeChannel(ctx);
            return;
        }
        String meetingKey = uri.substring(separator + 1);
        if (StringUtils.isEmpty(meetingKey)) {
            logger.warn("{}-Uri path is null", cardChannel.id());
            closeChannel(ctx);
            return;
        }
        cardChannel.setMeetingKey(meetingKey);

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:7397/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // response to client
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // if not Keep-Alive，close the connetcion
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        Channel channel = ctx.channel();

        // the frame of close the webSocket
        if (frame instanceof CloseWebSocketFrame) {
            logger.info(frame.toString());
            handshaker.close(channel, (CloseWebSocketFrame) frame.retain());
            return;
        }
        // the frame of ping
        if (frame instanceof PingWebSocketFrame) {
            channel.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // only text message, binary message is not supported
        if (!(frame instanceof TextWebSocketFrame)) {
            logger.warn("Binary message is not supported");
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        CardsNioSocketChannel cardChannel = (CardsNioSocketChannel) channel;

        String result = ((TextWebSocketFrame) frame).text();
        logger.debug("{}_{}_{}-rev msg {}", cardChannel.getMeetingKey(), cardChannel.id(), cardChannel.getMemberKey(), result);
        RevMessage revMessage = JsonUtil.read(result, RevMessage.class);
        RevMsgProcessor revMsgProcessor = revMsgProcessorFactory.getMessageRevProcessor(revMessage.getType());
        if (revMsgProcessor != null) {
            revMsgProcessor.execute(cardChannel, revMessage.getContent());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;

            CardsNioSocketChannel channel = (CardsNioSocketChannel) ctx.channel();

            if (event.state().equals(IdleState.READER_IDLE)) {
                // 未进行读操作
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                // 未进行写操作

            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                logger.debug("{}_{}_{}-eventState {}", channel.getMeetingKey(), channel.id(), channel.getMemberKey(), event.state());
                // 未进行读写
                closeChannel(ctx);
            }

        }
    }

    /**
     *
     * 关闭channel，退出
     *
     * @param ctx
     */
    private void closeChannel(ChannelHandlerContext ctx) {
        // 关闭channel
        ctx.close();
    }
}
