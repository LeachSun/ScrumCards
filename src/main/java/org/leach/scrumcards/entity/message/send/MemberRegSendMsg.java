package org.leach.scrumcards.entity.message.send;

/**
 * @author Leach
 * @date 2017/9/24
 */
public class MemberRegSendMsg {
    private String memberKey;
    private String nickname;

    public String getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
