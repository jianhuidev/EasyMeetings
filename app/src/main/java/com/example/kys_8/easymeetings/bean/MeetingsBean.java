package com.example.kys_8.easymeetings.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 我的会议表
 * Created by kys-8 on 2018/4/20.
 */

public class MeetingsBean extends BmobObject{

    private String meetingTopic;
    private String site;
    private String startTime;
    private String endTime;
    private String creator;
//    private String username;

    /**
     * 处理默认图片
     */
    private String type;
    private String introduce;
    /**
     * 可选
     */
    private BmobFile meetingImg;

    /**
     * 公司/学校/团队
     */
    private String company;

    private String creatorId;

    private List<String> attendId;

//    private List<User> member;

    /**
     * 允许为1
     */
    private String allowSignIn;

    /**
     * 参会人员的签到情况，签到人员id
     */
    private List<String> attendCheckInId;


    public String getMeetingTopic() {
        return meetingTopic;
    }

    public void setMeetingTopic(String meetingTopic) {
        this.meetingTopic = meetingTopic;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public BmobFile getMeetingImg() {
        return meetingImg;
    }

    public void setMeetingImg(BmobFile meetingImg) {
        this.meetingImg = meetingImg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<String> getAttendId() {
        return attendId;
    }

    public void setAttendId(List<String> attendId) {
        this.attendId = attendId;
    }

    public String getAllowSignIn() {
        return allowSignIn;
    }

    public void setAllowSignIn(String allowSignIn) {
        this.allowSignIn = allowSignIn;
    }

    public List<String> getAttendCheckInId() {
        return attendCheckInId;
    }

    public void setAttendCheckInId(List<String> attendCheckInId) {
        this.attendCheckInId = attendCheckInId;
    }
}
