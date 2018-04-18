package org.leach.scrumcards.entity.message.rev;

/**
 * @author Leach
 * @date 2017/9/9
 */
public enum RevMsgType {

    HEARTBEAT,

    // a new metting register (from master)
    MASTER_REG,

    // resume the task (from master)
    RESUME,

    // a new member register (from member)
    MEMBER_REG,

    // submit the estimate for task (from member)
    SUBMIT,

    // reconnect to server (from member)
    RECONNECT
}
