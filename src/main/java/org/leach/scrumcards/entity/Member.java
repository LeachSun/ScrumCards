package org.leach.scrumcards.entity;

import java.util.Date;

/**
 * @author Leach
 * @date 2017/8/25
 */
public class Member {

    private String key;

    private String nickname;

    private Date loginTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
