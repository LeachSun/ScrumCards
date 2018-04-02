package org.leach.scrumcards;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/8/10
 */

@Component
public class ScrumCardsConfig {

    @Value(value = "${netty.port}")
    private int nettyPort;

    public int getNettyPort() {
        return nettyPort;
    }
}
