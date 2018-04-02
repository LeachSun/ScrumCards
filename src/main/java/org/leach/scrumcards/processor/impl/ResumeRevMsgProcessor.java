package org.leach.scrumcards.processor.impl;


import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.ResultCode;
import org.leach.scrumcards.entity.message.rev.MasterResumeRevMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.leach.scrumcards.processor.SendProcessor;
import org.leach.scrumcards.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2018/03/30
 */
@Service("resumeRevMsgProcessor")
public class ResumeRevMsgProcessor extends AbstractRevMsgProcessor<MasterResumeRevMsg> {

    @Autowired
    private SendProcessor sendProcessor;

    @Override
    public Class<MasterResumeRevMsg> getContentClass() {
        return MasterResumeRevMsg.class;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, MasterResumeRevMsg content) {
        ChannelManager.getMeeting(channel.getMeetingKey()).clearEstimate();
        sendProcessor.sendToMembers(channel.getMeetingKey(), ResultUtil.get(ResultCode.RESUME));
    }
}
