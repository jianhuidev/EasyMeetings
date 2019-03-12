package com.example.kys_8.easymeetings.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by kys-8 on 2018/4/19.
 */

public class User extends BmobUser {

    /**
     * 已有字段
     * 用户名，密码，邮箱，手机号
     */

    private BmobFile avatar;
    private BmobFile userBackground;
    /**
     * 默认壁纸
     */
    private String userBg;
    private String realName;
//    private String job;
    private String gender;

    private String birthday;
    /**
     * 公司/学校/团队
     */
    private String company;
    private String qq;
    private String phone;

    /**
     * 个性签名
     */
    private String sign;

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobFile getUserBackground() {
        return userBackground;
    }

    public void setUserBackground(BmobFile userBackground) {
        this.userBackground = userBackground;
    }

    public String getUserBg() {
        return userBg;
    }

    public void setUserBg(String userBg) {
        this.userBg = userBg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
