package com.example.kys_8.easymeetings.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by kys-8 on 2018/5/2.
 */

public class ResBean extends BmobObject {
    /**
     * 创建时间已有
     */
    private String name;

    private String type;

    private String meetingId;

    private String mark;

    private String link;

    private BmobFile resImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BmobFile getResImg() {
        return resImg;
    }

    public void setResImg(BmobFile resImg) {
        this.resImg = resImg;
    }
}
