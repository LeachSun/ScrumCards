package org.leach.scrumcards.cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.leach.scrumcards.channel.CardsNioSocketChannel;
import org.leach.scrumcards.entity.Meeting;
import org.leach.scrumcards.entity.Member;
import org.leach.scrumcards.util.KeyUtil;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Leach
 * @date 2017/9/25
 */
public class ChannelManager {

    private static Map<String, MeetingChannel> meetingChannels = new ConcurrentHashMap<>();

    private static Map<String, Meeting> meetings = new ConcurrentHashMap<>();

    public static void createMeeting(CardsNioSocketChannel channel) {

        channel.setMaster(true);

        MeetingChannel meetingChannel = new MeetingChannel();
        meetingChannel.setMasterGroup(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        meetingChannel.setMemberGroup(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));

        String meetingKey = channel.getMeetingKey();

        meetingChannels.put(meetingKey, meetingChannel);
        meetings.put(meetingKey, new Meeting());
        meetingChannels.get(meetingKey).getMasterGroup().add(channel);
    }


    public static String memberJoin(CardsNioSocketChannel channel) {

        String meetingKey = channel.getMeetingKey();
        MeetingChannel meetingChannel = meetingChannels.get(meetingKey);
        if (meetingChannel == null) {
            return null;
        }
        meetingChannel.getMemberGroup().add(channel);

        String memberKey = channel.getMemberKey() == null ? KeyUtil.generateMemberKey() : channel.getMemberKey();
        channel.setMemberKey(memberKey);
        channel.setMaster(false);

        Member member = new Member();
        member.setKey(memberKey);
        member.setLoginTime(new Date());
        member.setNickname(channel.getNickname());
        meetings.get(meetingKey).addMember(member);

        return memberKey;
    }

    public static void masterOffline(String meetingKey) {
        meetingChannels.remove(meetingKey);
        meetings.remove(meetingKey);
    }

    public static void memberOffline(String meetingKey, String memberKey) {
        Meeting meeting = meetings.get(meetingKey);
        if (meeting != null) {
            meeting.removeMember(memberKey);
        }
    }

    public static ChannelGroup getMasterChannelGroup(String meetingKey) {
        MeetingChannel meetingChannel = meetingChannels.get(meetingKey);
        return meetingChannel == null ? null : meetingChannel.getMasterGroup();
    }

    public static ChannelGroup getMemberChannelGroup(String meetingKey) {
        MeetingChannel meetingChannel = meetingChannels.get(meetingKey);
        return meetingChannel == null ? null : meetingChannel.getMemberGroup();
    }

    public static Meeting getMeeting(String meetingKey) {
        return meetings.get(meetingKey);
    }
}
