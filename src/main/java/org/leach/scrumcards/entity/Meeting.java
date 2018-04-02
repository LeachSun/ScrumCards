package org.leach.scrumcards.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/8/5
 */
public class Meeting {

    private String key;

    private Date startTime;

    private Map<String, Member> members = new HashMap<>();

    private List<Estimate> estimates = new ArrayList<>();

    public void addMember(Member member) {
        members.put(member.getKey(), member);
    }

    public void removeMember(String memberKey) {
        members.remove(memberKey);
    }

    public void addEstimate(Estimate estimate) {
        estimates.add(estimate);
    }

    public void clearEstimate() {
        estimates.clear();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }

    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
    }
}
