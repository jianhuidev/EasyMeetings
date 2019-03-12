package com.example.kys_8.easymeetings.bean;

/**
 * Created by kys-8 on 2018/4/21.
 */

public class TSVariable {

    public static User userInfo = null;

    /**
     * 修改密码，需要旧密码
     * BmobUser 取不出来密码，所以记录一下
     */
    public static String curUserPassWord;

    /**
     * 会议标识，标记是从参加会议点进去，还是我的会议
     */
    public static int fragmentMark = 0;

}
