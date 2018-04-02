package org.leach.scrumcards.handler;

import java.util.concurrent.TimeUnit;

import org.leach.scrumcards.processor.RevMsgProcessorFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author Leach
 * @date 2017/8/5
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    private RevMsgProcessorFactory revMsgProcessorFactory;

    public ChildChannelHandler(RevMsgProcessorFactory revMsgProcessorFactory) {
        this.revMsgProcessorFactory = revMsgProcessorFactory;
    }

    @Override
    protected void initChannel(SocketChannel e) throws Exception {

        e.pipeline().addLast("http-codec", new HttpServerCodec());
        e.pipeline().addLast("aggregator", new HttpObjectAggregator(64 * 1024));
        e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        e.pipeline().addLast("ping", new IdleStateHandler(10, 10, 5, TimeUnit.SECONDS));

        e.pipeline().addLast("handler", new WebSocketServerHandler(revMsgProcessorFactory));
    }
}