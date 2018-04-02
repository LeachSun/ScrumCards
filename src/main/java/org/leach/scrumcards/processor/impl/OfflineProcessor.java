package org.leach.scrumcards.processor.impl;

import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.ResultCode;
import org.leach.scrumcards.entity.message.send.MemberOfflineSendMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.leach.scrumcards.processor.SendProcessor;
import org.leach.scrumcards.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.channel.group.ChannelGroup;

/**
 * @author Leach
 * @date 2018/3/30
 */
@Service("offlineProcessor")
public class OfflineProcessor extends AbstractRevMsgProcessor {

    @Autowired
    private SendProcessor sendProcessor;

    @Override
    public Class getContentClass() {
        return null;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, Object content) {
        String meetingKey = channel.getMeetingKey();
        if (meetingKey != null) {
            if (channel.isMaster()) {
                ChannelGroup memberChannelGroup = ChannelManager.getMemberChannelGroup(meetingKey);
                if (memberChannelGroup != null) {
                    memberChannelGroup.close();
                }
                ChannelManager.masterOffline(meetingKey);
            } else {
                String memberKey = channel.getMemberKey();
                if(memberKey != null) {
                    ChannelManager.memberOffline(meetingKey, memberKey);

                    MemberOfflineSendMsg sendMsg = new MemberOfflineSendMsg();
                    sendMsg.setMemberKey(memberKey);

                    sendProcessor.sendToMasters(channel.getMeetingKey(), ResultUtil.get(ResultCode.MEMBER_OFFLINE, sendMsg));
                }
            }
        }
    }
}
