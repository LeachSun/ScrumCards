package org.leach.scrumcards.cache;

import io.netty.channel.group.ChannelGroup;

/**
 * @author Leach
 * @date 2017/9/25
 */
public class MeetingChannel {
    private ChannelGroup masterGroup;

    private ChannelGroup memberGroup;

    public ChannelGroup getMasterGroup() {
        return masterGroup;
    }

    public void setMasterGroup(ChannelGroup masterGroup) {
        this.masterGroup = masterGroup;
    }

    public ChannelGroup getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(ChannelGroup memberGroup) {
        this.memberGroup = memberGroup;
    }
}
