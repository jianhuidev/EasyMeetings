package com.example.kys_8.easymeetings.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.Config;
import com.example.kys_8.easymeetings.utils.PrefConstants;
import com.example.kys_8.easymeetings.utils.SAppUtil;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDeviceDensity();
        checkShowTutorial();
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    /**
     * 判断版本
     */
    private void checkShowTutorial(){
        //没有数据，取出-1
        int oldVersionCode = PrefConstants.getAppPrefInt(this, "version_code");
        int currentVersionCode = SAppUtil.getAppVersionCode(this);

        if(currentVersionCode>oldVersionCode){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            PrefConstants.putAppPrefInt(this,"version_code", currentVersionCode);
            finish();
        }else {
            startWelcome();
        }

    }


    /**
     * 欢迎界面
     */
    public void startWelcome(){
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MDLoginActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        },1000);

    }


    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

}
