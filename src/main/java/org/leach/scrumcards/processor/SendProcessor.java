package org.leach.scrumcards.processor;

import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author Leach
 * @date 2018/3/30
 */
@Service
public class SendProcessor {

    private Logger logger = LoggerFactory.getLogger(SendProcessor.class);

    private void sendToGroup(Result result, ChannelGroup channelGroup) {
        String msg = JsonUtil.write(result);

        logger.debug("groupSize: {}, send msg: {}", channelGroup.size(), msg);

        TextWebSocketFrame tws = new TextWebSocketFrame(msg);

        channelGroup.writeAndFlush(tws);
    }

    public void sendToMasters(String meetingKey, Result result) {

        ChannelGroup channelGroup = ChannelManager.getMasterChannelGroup(meetingKey);
        if (channelGroup != null) {
            sendToGroup(result, channelGroup);
        }
    }

    public void sendToMembers(String meetingKey, Result result) {

        ChannelGroup channelGroup = ChannelManager.getMemberChannelGroup(meetingKey);
        if (channelGroup != null) {
            sendToGroup(result, channelGroup);
        }
    }

    public void send(CardsNioSocketChannel channel, Result result) {
        String msg = JsonUtil.write(result);

        logger.debug("memberKey: {}, send msg: {}", channel.getMemberKey(), msg);
        TextWebSocketFrame tws = new TextWebSocketFrame(msg);
        channel.writeAndFlush(tws);
    }
}
