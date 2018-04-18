package org.leach.scrumcards.processor;

import org.leach.scrumcards.entity.message.rev.RevMsgType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/9/24
 */
@Component
public class RevMsgProcessorFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("masterRegRevMsgProcessor")
    private RevMsgProcessor masterRegRevMsgProcessor;

    @Autowired
    @Qualifier("resumeRevMsgProcessor")
    private RevMsgProcessor resumeRevMsgProcessor;

    @Autowired
    @Qualifier("memberRegRevMsgProcessor")
    private RevMsgProcessor memberRegRevMsgProcessor;

    @Autowired
    @Qualifier("memberReconnectRevMsgProcessor")
    private RevMsgProcessor memberReconnectRevMsgProcessor;

    @Autowired
    @Qualifier("submitRevMsgProcessor")
    private RevMsgProcessor submitRevMsgProcessor;

    @Autowired
    @Qualifier("offlineProcessor")
    private RevMsgProcessor offlineProcessor;

    public RevMsgProcessor getMessageRevProcessor(RevMsgType revMsgType) {
        switch (revMsgType) {
            case MASTER_REG:
                return masterRegRevMsgProcessor;
            case RESUME:
                return resumeRevMsgProcessor;
            case MEMBER_REG:
                return memberRegRevMsgProcessor;
            case RECONNECT:
                return memberReconnectRevMsgProcessor;
            case SUBMIT:
                return submitRevMsgProcessor;
        }
        return null;
    }

    public RevMsgProcessor getOfflineProcessor() {
        return offlineProcessor;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
