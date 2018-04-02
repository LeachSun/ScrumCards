package org.leach.scrumcards.processor;

import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.util.JsonUtil;
import org.springframework.util.StringUtils;

/**
 * @author Leach
 * @date 2018/3/30
 */
public abstract class AbstractRevMsgProcessor<T> implements RevMsgProcessor {
    @Override
    public void execute(CardsNioSocketChannel channel, String revMsgJson) {
        T msgContent = StringUtils.isEmpty(revMsgJson) ? null : JsonUtil.read(revMsgJson, getContentClass());
        handle(channel, msgContent);
    }

    /**
     * 获取参数内容类型
     * @return
     */
    public abstract Class<T> getContentClass();


    /**
     * 处理消息
     * @param content
     */
    public abstract void handle(CardsNioSocketChannel channel, T content);
}
