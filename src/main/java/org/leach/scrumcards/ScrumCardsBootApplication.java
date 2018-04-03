package org.leach.scrumcards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Leach
 * @date 2017/8/5
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.leach.scrumcards"})
public class ScrumCardsBootApplication {

    private static Logger logger = LoggerFactory.getLogger(ScrumCardsBootApplication.class);

    public static void main(String[] args) {
        logger.info("ScrumCards is starting");

        ConfigurableApplicationContext applicationContext = SpringApplication.run(ScrumCardsBootApplication.class, args);

        NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
        nettyServer.run();
        logger.info("ScrumCards is started");

    }
}
