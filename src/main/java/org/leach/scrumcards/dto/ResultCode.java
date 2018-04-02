package org.leach.scrumcards.dto;

/**
 * @author Leach
 * @date 2017/9/25
 */
public enum ResultCode {
    // a new member register (for master)
    MEMBER_ONLINE(200),

    // a member offline (for master)
    MEMBER_OFFLINE(201),

    // the member submit estimate for task (for master)
    ESTIMATE(202),

    // the master resume the task, include the first task (for member)
    RESUME(203),

    SUCCESS(100),
    FAIL(200);

    private int val;

    ResultCode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
