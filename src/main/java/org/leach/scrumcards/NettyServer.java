package org.leach.scrumcards;

import javax.annotation.PostConstruct;

import org.leach.scrumcards.channel.CardsNioServerSocketChannel;
import org.leach.scrumcards.handler.ChildChannelHandler;
import org.leach.scrumcards.processor.RevMsgProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author Leach
 * @date 2017/8/5
 */
@Component
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private ScrumCardsConfig scrumCardsConfig;

    @Autowired
    private RevMsgProcessorFactory revMsgProcessorFactory;

    @PostConstruct
    public void run() {
        logger.info("ScrumCards NettyServer initialized with port(s): {}", scrumCardsConfig.getNettyPort());

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup);
            serverBootstrap.channel(CardsNioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 注意是childOption
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChildChannelHandler(revMsgProcessorFactory));

            Channel channel = serverBootstrap.bind(scrumCardsConfig.getNettyPort()).sync().channel();

            logger.info("ScrumCards NettyServer started on port(s): {}", scrumCardsConfig.getNettyPort());

            channel.closeFuture().sync();



        } catch (Exception e) {
            logger.error("ScrumCards NettyServer start fail", e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
