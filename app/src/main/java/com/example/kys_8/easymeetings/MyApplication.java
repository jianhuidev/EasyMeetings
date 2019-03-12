package com.example.kys_8.easymeetings;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.SpUtil;
import com.google.gson.Gson;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.easeui.EaseUI;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import cn.bmob.v3.Bmob;

/**
 * Created by kys-8 on 2018/4/11.
 */

public class MyApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        initBmob();

//        initUserInfo();

        initImageSwitcher();
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * 初始化Bmob
     */
    private void initBmob(){
        Bmob.initialize(this,"d5b20040dbba20a86c2a99c96b80620c"); // 初始化bmob 云
    }


    /**
     * 初始化图片选择器
     */
    private void initImageSwitcher(){
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

//    private void initUserInfo(){
//
//        String userData = (String) SpUtil.get("userInfo",null);
//
//        if (userData != null && !userData.isEmpty()){
//            TSVariable.userInfo = new Gson().fromJson(userData, User.class);
//        }
//
//    }

}
