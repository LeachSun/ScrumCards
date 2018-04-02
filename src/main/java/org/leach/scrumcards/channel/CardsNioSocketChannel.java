package org.leach.scrumcards.channel;

import java.nio.channels.SocketChannel;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Leach
 * @date 2017/8/5
 */
public class CardsNioSocketChannel extends NioSocketChannel {

    private boolean master;

    private String meetingKey;

    private String memberKey;

    private String nickname;

    /**
     * Create a new instance
     *
     * @param parent    the {@link Channel} which created this instance or {@code null} if it was created by the user
     * @param socket    the {@link SocketChannel} which will be used
     */
    public CardsNioSocketChannel(Channel parent, SocketChannel socket) {
        super(parent, socket);
    }

    public String getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(String meetingKey) {
        this.meetingKey = meetingKey;
    }

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

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }
}