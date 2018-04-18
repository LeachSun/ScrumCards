package org.leach.scrumcards.entity.message.rev;

/**
 * @author Leach
 * @date 2018/4/17
 */
public class MemberReconnectRevMsg extends MemberRegRevMsg {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
