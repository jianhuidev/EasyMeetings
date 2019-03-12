package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText un_et,birthday_et,gender_et,email_et,phone_et,QQ_et,sign_et;

    private ImageView avatar_personal,image_bg_personal;

    private RelativeLayout affirm_modify;

    private FloatingActionButton fab;

    private LinearLayout avatar_change,change_bg,exit,cancel;

    private BottomSheetDialog mBottomSheetDialog;

    /**
     * 从相册选择
     */
    private static final int ALBUM_CODE = 13;

    private String avatarPath,bgPath;

    /**
     * 更换头像为true
     */
    private boolean isAvatar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Toolbar toolbar = findViewById(R.id.toolbar_personal);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("个人信息");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        initImageSwitcher();

        initView();

        initUserInfo();
    }

    private void initView(){

        fab = (FloatingActionButton)findViewById(R.id.fab_personal);
        fab.setOnClickListener(this);

        avatar_personal = (ImageView)findViewById(R.id.avatar_personal);
        image_bg_personal = (ImageView)findViewById(R.id.image_bg_personal);

        un_et = (EditText)findViewById(R.id.un_et);
        birthday_et = (EditText)findViewById(R.id.birthday_et);
        gender_et = (EditText)findViewById(R.id.gender_et);
        email_et = (EditText)findViewById(R.id.email_et);
        phone_et = (EditText)findViewById(R.id.phone_et);
        QQ_et = (EditText)findViewById(R.id.QQ_et);
        sign_et = (EditText)findViewById(R.id.sign_et);

        affirm_modify = (RelativeLayout)findViewById(R.id.affirm_modify_personal);
        affirm_modify.setOnClickListener(this);

    }

    private void initUserInfo(){
        User user = TSVariable.userInfo;
        if (!TextUtils.isEmpty(user.getUsername()))
            un_et.setText(user.getUsername());
        if (!TextUtils.isEmpty(user.getBirthday()))
            birthday_et.setText(user.getBirthday());
        if (!TextUtils.isEmpty(user.getGender()))
            gender_et.setText(user.getGender());
        if (!TextUtils.isEmpty(user.getEmail()))
            email_et.setText(user.getEmail());
        if (!TextUtils.isEmpty(user.getPhone()))
            phone_et.setText(user.getPhone());
        if (!TextUtils.isEmpty(user.getQq()))
            QQ_et.setText(user.getQq());
        if (!TextUtils.isEmpty(user.getSign()))
            sign_et.setText(user.getSign());
        if (TSVariable.userInfo.getAvatar() != null){
            Glide.with(this).load(TSVariable.userInfo.getAvatar().getUrl())
                    .error(R.mipmap.meeting_img3)
                    .crossFade()
                    .into(avatar_personal);
        }
//        if (TSVariable.userInfo.getUserBackground() != null){
//            Glide.with(this).load(TSVariable.userInfo.getUserBackground().getUrl())
//                    .error(R.mipmap.nav_header1)
//                    .crossFade()
//                    .into(image_bg_personal);
//        }
        initBg(TSVariable.userInfo.getUserBg());

    }

    private void initBg(String bg){
        if (bg != null && bg.equals("c1")){
            Glide.with(this).load(R.mipmap.nav_header01)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("c2")){
            Glide.with(this).load(R.mipmap.nav_header02)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("c3")){
            Glide.with(this).load(R.mipmap.nav_header03)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("d1")){
            Glide.with(this).load(R.mipmap.nav_header04)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("d2")){
            Glide.with(this).load(R.mipmap.nav_header05)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("d3")){
            Glide.with(this).load(R.mipmap.nav_header06)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("d4")){
            Glide.with(this).load(R.mipmap.nav_header07)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("s1")){
            Glide.with(this).load(R.mipmap.nav_header08)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);

        }else if (bg != null && bg.equals("s2")){
            Glide.with(this).load(R.mipmap.nav_header09)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }else if (bg != null && bg.equals("bg")){
            if (TSVariable.userInfo.getUserBackground() != null){
                Glide.with(this).load(TSVariable.userInfo.getUserBackground().getUrl())
                        .error(R.mipmap.nav_header1)
                        .crossFade()
                        .into(image_bg_personal);
            }
        }else {
            Glide.with(this).load(R.mipmap.nav_header1)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_personal);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                finish();
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.affirm_modify_personal:
                affirmSubmit();
                break;
            case R.id.fab_personal:

                //更换头像

                changeDialog();


                break;
            case R.id.avatar_change:

                isAvatar = true;

                mBottomSheetDialog.cancel();
                selectAalum();

                break;
            case R.id.bg_change:

                isAvatar = false;

                mBottomSheetDialog.cancel();
                selectAalum();

                break;
            case R.id.exit_change:

//                startActivity(new Intent(PersonalActivity.this,MDLoginActivity.class));

                Intent intentOut = new Intent();
                intentOut.putExtra("out","log_out");
                setResult(RESULT_OK,intentOut);
                finish();
                break;
            case R.id.cancel_change:
                mBottomSheetDialog.cancel();
                break;
        }
    }

    /**
     * 确认提交
     */
    private void affirmSubmit(){
        if (TSVariable.userInfo == null || TSVariable.userInfo.getObjectId() == null){
            return;
        }
        final User user = new User();
        user.setValue("username",un_et.getText().toString().trim());
        user.setValue("gender",gender_et.getText().toString().trim());
        user.setValue("birthday",birthday_et.getText().toString().trim());
        user.setValue("qq",QQ_et.getText().toString().trim());
        user.setValue("sign",sign_et.getText().toString().trim());
        user.setValue("phone",phone_et.getText().toString().trim());
        user.setValue("email",email_et.getText().toString().trim());
//        if (avatarPath != null && !avatarPath.isEmpty()){
//            user.setValue();
//        }
//        if (bgPath != null && !bgPath.isEmpty()){
//            user.setValue();
//        }
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在更新。。。");
        user.update(TSVariable.userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dialog.cancel();
                if(e==null){
                    TSVariable.userInfo.setUsername(un_et.getText().toString().trim());
                    TSVariable.userInfo.setGender(gender_et.getText().toString().trim());
                    TSVariable.userInfo.setBirthday(birthday_et.getText().toString().trim());
                    TSVariable.userInfo.setQq(QQ_et.getText().toString().trim());
                    TSVariable.userInfo.setSign(sign_et.getText().toString().trim());
                    TSVariable.userInfo.setPhone(phone_et.getText().toString().trim());
                    TSVariable.userInfo.setEmail(email_et.getText().toString().trim());
                    ToastUtil.showToast(PersonalActivity.this,"更新成功");
                    LogUtil.e("PersonalActivity","更新成功");
                }else{
                    ToastUtil.showToast(PersonalActivity.this,"更新失败");
                    LogUtil.e("PersonalActivity","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

//    /**
//     * 初始化图片选择器
//     */
//    private void initImageSwitcher(){
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
//    }

    private void selectAalum(){
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                .backResId(R.mipmap.back)
                .title("请选择一个图片")
                .titleColor(Color.WHITE)
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .allImagesText("所有图片")
//                .needCrop(true)
//                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ISNav.getInstance().toListActivity(this, config, ALBUM_CODE);
    }


    private void changeDialog(){
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_change, null);
        mBottomSheetDialog.setContentView(view);

        change_bg = (LinearLayout)view.findViewById(R.id.bg_change);
        exit = (LinearLayout)view.findViewById(R.id.exit_change);
        cancel = (LinearLayout)view.findViewById(R.id.cancel_change);
        avatar_change = (LinearLayout)view.findViewById(R.id.avatar_change);
        avatar_change.setOnClickListener(this);
        change_bg.setOnClickListener(this);
        exit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mBottomSheetDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                setAvatarOrBg(path,isAvatar);
            }
        }
    }
    private void setAvatarOrBg(String path,boolean avatar){
        if (avatar){
            avatarPath = path;
            uploadAvatar(path);
            Glide.with(this).load(new File(avatarPath))
                    .error(R.mipmap.meeting_img3)
                    .crossFade()
                    .into(avatar_personal);
        }else {
            bgPath = path;
            uploadBg(bgPath);
            Glide.with(this).load(new File(bgPath))
                    .error(R.mipmap.nav_header04)
                    .crossFade()
                    .into(image_bg_personal);
        }
    }

    /**
     * 上传头像
     */
    private void uploadAvatar(String path){
        if (path == null || TSVariable.userInfo == null){
            ToastUtil.showToast(PersonalActivity.this,"上传头像失败，请检查网络");
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        final ProgressDialog dialog = ProgressDialog.show(this, null, "上传头像中。。。");
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
//                dialog.cancel();
                if (e == null){
//                    User user = TSVariable.userInfo;
                    TSVariable.userInfo.setAvatar(bmobFile);
                    TSVariable.userInfo.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dialog.cancel();
                            if (e == null){
                                ToastUtil.showToast(PersonalActivity.this,"上传头像成功");
                            }else {
                                ToastUtil.showToast(PersonalActivity.this,"上传头像失败，请检查网络");
                                LogUtil.e("PersonalActivity",e.getMessage());
                            }
                        }
                    });

                }else {
                    ToastUtil.showToast(PersonalActivity.this,"上传头像失败，请检查网络");
                    dialog.cancel();
                }
            }
        });
    }

    /**
     * 上传头像
     */
    private void uploadBg(String path){
        if (path == null || TSVariable.userInfo == null){
            ToastUtil.showToast(PersonalActivity.this,"上传壁纸失败，请检查网络");
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        final ProgressDialog dialog = ProgressDialog.show(this, null, "上传壁纸中。。。");
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
//                dialog.cancel();
                if (e == null){
//                    User user = TSVariable.userInfo;
                    TSVariable.userInfo.setUserBackground(bmobFile);
                    TSVariable.userInfo.setUserBg("bg");
                    TSVariable.userInfo.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dialog.cancel();
                            if (e == null){
                                ToastUtil.showToast(PersonalActivity.this,"上传壁纸成功");
                            }else {
                                ToastUtil.showToast(PersonalActivity.this,"上传壁纸失败，请检查网络");
                                LogUtil.e("PersonalActivity",e.getMessage());
                            }
                        }
                    });

                }else {
                    ToastUtil.showToast(PersonalActivity.this,"上传壁纸失败，请检查网络");
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("hh","haha");
//        setResult(RESULT_OK,intent);
    }
//    private void backIntent(){
//        Intent intent = new Intent();
//        intent.putExtra("username",username);
//        intent.putExtra("password",passwd);
//        setResult(RESULT_OK,intent);
//    }
}
