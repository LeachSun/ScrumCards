package org.leach.scrumcards.processor.impl;


import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.ResultCode;
import org.leach.scrumcards.entity.message.rev.MemberRegRevMsg;
import org.leach.scrumcards.entity.message.send.MemberRegSendMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.leach.scrumcards.processor.SendProcessor;
import org.leach.scrumcards.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2018/03/30
 */
@Service("memberRegRevMsgProcessor")
public class MemberRegRevMsgProcessor extends AbstractRevMsgProcessor<MemberRegRevMsg> {

    @Autowired
    private SendProcessor sendProcessor;

    @Override
    public Class<MemberRegRevMsg> getContentClass() {
        return MemberRegRevMsg.class;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, MemberRegRevMsg content) {
        channel.setNickname(content.getNickname());
        ChannelManager.memberJoin(channel);

        MemberRegSendMsg sendMsg = new MemberRegSendMsg();
        sendMsg.setMemberKey(channel.getMemberKey());
        sendMsg.setNickname(channel.getNickname());

        sendProcessor.sendToMasters(channel.getMeetingKey(), ResultUtil.get(ResultCode.MEMBER_ONLINE, sendMsg));
    }
}
