package org.leach.scrumcards.processor.impl;

import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.dto.ResultCode;
import org.leach.scrumcards.entity.message.rev.MemberReconnectRevMsg;
import org.leach.scrumcards.entity.message.send.MemberRegSendMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.leach.scrumcards.processor.SendProcessor;
import org.leach.scrumcards.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2018/4/18
 */
@Service("memberReconnectRevMsgProcessor")
public class MemberReconnectRevMsgProcessor extends AbstractRevMsgProcessor<MemberReconnectRevMsg> {

    @Autowired
    private SendProcessor sendProcessor;

    @Override
    public Class<MemberReconnectRevMsg> getContentClass() {
        return MemberReconnectRevMsg.class;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, MemberReconnectRevMsg content) {
        channel.setNickname(content.getNickname());
        channel.setMemberKey(content.getKey());
        ChannelManager.memberJoin(channel);

        MemberRegSendMsg sendMsg = new MemberRegSendMsg();
        sendMsg.setMemberKey(channel.getMemberKey());
        sendMsg.setNickname(channel.getNickname());

        Result<MemberRegSendMsg> result = ResultUtil.get(ResultCode.MEMBER_ONLINE, sendMsg);
        sendProcessor.sendToMasters(channel.getMeetingKey(), result);
    }
}
