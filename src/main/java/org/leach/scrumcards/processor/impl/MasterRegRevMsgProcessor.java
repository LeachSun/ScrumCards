package org.leach.scrumcards.processor.impl;


import org.leach.scrumcards.cache.ChannelManager;
import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.entity.message.rev.MasterRegRevMsg;
import org.leach.scrumcards.processor.AbstractRevMsgProcessor;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/10/15
 */
@Service("masterRegRevMsgProcessor")
public class MasterRegRevMsgProcessor extends AbstractRevMsgProcessor<MasterRegRevMsg> {

    @Override
    public Class<MasterRegRevMsg> getContentClass() {
        return MasterRegRevMsg.class;
    }

    @Override
    public void handle(CardsNioSocketChannel channel, MasterRegRevMsg content) {
        ChannelManager.createMeeting(channel);
    }
}
