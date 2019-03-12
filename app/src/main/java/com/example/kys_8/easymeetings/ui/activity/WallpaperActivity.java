package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class WallpaperActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout beijing_bg,shanghai_bg,suzhou_bg,design1,design2,design3,design4,scenery1,scenery2;
//    Button wall_btn;
    @Override
    protected int getContentView() {
        return R.layout.activity_wallpaper;
    }

    @Override
    protected void initView() {
        beijing_bg = (LinearLayout)findViewById(R.id.beijing_bg);
        shanghai_bg = (LinearLayout)findViewById(R.id.shanghai_bg);
        suzhou_bg = (LinearLayout)findViewById(R.id.suzhou_bg);

        design1 = (LinearLayout)findViewById(R.id.design1);
        design2 = (LinearLayout)findViewById(R.id.design2);
        design3 = (LinearLayout)findViewById(R.id.design3);
        design4 = (LinearLayout)findViewById(R.id.design4);

        scenery1 = (LinearLayout)findViewById(R.id.scenery1);
        scenery2 = (LinearLayout)findViewById(R.id.scenery2);

//        wall_btn = (Button)findViewById(R.id.wall_btn);
//        wall_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TSVariable.userInfo.setUserBg("1");
//                TSVariable.userInfo.update(new UpdateListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null){
//                            ToastUtil.showToast(WallpaperActivity.this,"成功更换");
//                        }else {
//                            ToastUtil.showToast(WallpaperActivity.this,"更换失败，请检查网络");
//                        }
//                    }
//                });
//            }
//        });

    }

    private void uploadBg(String bg){
        TSVariable.userInfo.setUserBg(bg);
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在提交。。。");
        TSVariable.userInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dialog.cancel();
                if (e == null){
                    ToastUtil.showToast(WallpaperActivity.this,"成功更换");
                }else {
                    ToastUtil.showToast(WallpaperActivity.this,"更换失败，请检查网络");
                }
            }
        });
    }

    @Override
    protected void initListener() {
        beijing_bg.setOnClickListener(this);
        shanghai_bg.setOnClickListener(this);
        suzhou_bg.setOnClickListener(this);

        design1.setOnClickListener(this);
        design2.setOnClickListener(this);
        design3.setOnClickListener(this);
        design4.setOnClickListener(this);

        scenery1.setOnClickListener(this);
        scenery2.setOnClickListener(this);

    }

    @Override
    protected String getTitleText() {
        return "默认壁纸选择";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.beijing_bg:
                uploadBg("c1");
                break;
            case R.id.shanghai_bg:
                uploadBg("c2");
                break;
            case R.id.suzhou_bg:
                uploadBg("c3");
                break;
            case R.id.design1:
                uploadBg("d1");
                break;
            case R.id.design2:
                uploadBg("d2");
                break;
            case R.id.design3:
                uploadBg("d3");
                break;
            case R.id.design4:
                uploadBg("d4");
                break;
            case R.id.scenery1:
                uploadBg("s1");
                break;
            case R.id.scenery2:
                uploadBg("s2");
                break;
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wallpaper);
//    }
}
