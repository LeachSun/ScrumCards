package org.leach.scrumcards.entity;

import java.util.Date;

/**
 * @author Leach
 * @date 2017/8/25
 */
public class Estimate {

    private String memberKey;

    private int point;

    private Date estimateTime;

    public String getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Date estimateTime) {
        this.estimateTime = estimateTime;
    }
}
