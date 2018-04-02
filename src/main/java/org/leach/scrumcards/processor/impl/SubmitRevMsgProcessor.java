package org.leach.scrumcards.processor.impl;


import java.util.Date;

import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.dto.ResultCode;
import org.leach.scrumcards.entity.Estimate;
import org.leach.scrumcards.entity.Meeting;
import org.leach.scrumcards.entity.message.rev.MemberSubmitRevMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.leach.scrumcards.processor.SendProcessor;
import org.leach.scrumcards.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2018/03/30
 */
@Service("submitRevMsgProcessor")
public class SubmitRevMsgProcessor extends AbstractRevMsgProcessor<MemberSubmitRevMsg> {

    @Autowired
    private SendProcessor sendProcessor;

    @Override
    public Class<MemberSubmitRevMsg> getContentClass() {
        return MemberSubmitRevMsg.class;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, MemberSubmitRevMsg content) {
        Meeting meeting = ChannelManager.getMeeting(channel.getMeetingKey());

        Estimate estimate = new Estimate();
        estimate.setMemberKey(channel.getMemberKey());
        estimate.setEstimateTime(new Date());
        estimate.setPoint(content.getPoint());
        meeting.addEstimate(estimate);

        sendProcessor.sendToMasters(channel.getMeetingKey(), ResultUtil.get(ResultCode.ESTIMATE, estimate));
    }
}
