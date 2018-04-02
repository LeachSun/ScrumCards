package org.leach.scrumcards.channel;

import java.nio.channels.SocketChannel;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Leach
 * @date 2017/8/5
 */
public class CardsNioServerSocketChannel extends NioServerSocketChannel {

    private static Logger logger = LoggerFactory.getLogger(CardsNioServerSocketChannel.class);

    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch = javaChannel().accept();

        try {
            if (ch != null) {
                buf.add(new CardsNioSocketChannel(this, ch));
                return 1;
            }
        } catch (Throwable t) {
            logger.warn("Failed to create a new channel from an accepted socket.", t);

            try {
                ch.close();
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        }

        return 0;
    }
}