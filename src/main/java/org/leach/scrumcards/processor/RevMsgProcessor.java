package org.leach.scrumcards.processor;

import org.leach.scrumcards.channel.CardsNioSocketChannel;

/**
 * @author Leach
 * @date 2017/9/24
 */
public interface RevMsgProcessor {

    void execute(CardsNioSocketChannel channel, String revMsgJson);
}
