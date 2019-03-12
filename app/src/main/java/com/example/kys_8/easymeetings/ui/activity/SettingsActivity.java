package com.example.kys_8.easymeetings.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.utils.ToastUtil;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{


    private LinearLayout personal,change_pw,checkver,about_us;

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {

        personal = (LinearLayout)findViewById(R.id.personal_settings);
        change_pw = (LinearLayout)findViewById(R.id.change_pw_settings);
        checkver = (LinearLayout)findViewById(R.id.checkver_settings);
        about_us = (LinearLayout)findViewById(R.id.about_us_settings);
    }

    @Override
    protected void initListener() {
        personal.setOnClickListener(this);
        change_pw.setOnClickListener(this);
        checkver.setOnClickListener(this);
        about_us.setOnClickListener(this);
    }

    @Override
    protected String getTitleText() {
        return "设置";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal_settings:
                startActivity(new Intent(this,PersonalActivity.class));
                break;
            case R.id.change_pw_settings:
                startActivity(new Intent(this,ChangePWActivity.class));
                break;
            case R.id.checkver_settings:
                ToastUtil.showToast(this,"当前已是最新版本");
                break;
            case R.id.about_us_settings:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;

        }
    }
}
