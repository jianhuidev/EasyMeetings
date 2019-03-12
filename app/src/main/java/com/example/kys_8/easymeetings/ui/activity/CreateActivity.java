package com.example.kys_8.easymeetings.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.picker.DatePickerDialog;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;
import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "CreateActivity";
    LinearLayout start_t,start_o;
    TextView start_text,over_text;
    ImageView img_create;
    private RelativeLayout r1,r2,r3,r4,r5;
    private LinearLayout take_photo,select_alum;
    private String type;
    private BottomSheetDialog mBottomSheetDialog;

    private String topic_photo_path;
    private RelativeLayout intro_create_l,intro_create_b;
    private FrameLayout down_btn;
    private RelativeLayout affirm;
    private EditText topic_et,site_et,intro_et;
    private ProgressDialog dialog;

    /**
     * 从相册选择
     */
    private static final int REQUEST_LIST_CODE = 10;
    /**
     * 拍照
     */
    private static final int REQUEST_CAMERA_CODE = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
//        initImageSwitcher();
        initView();
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

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("创建会议");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        topic_et = (EditText)findViewById(R.id.topic_et_create);
        site_et = (EditText)findViewById(R.id.site_et_create);

        intro_et = (EditText)findViewById(R.id.et_intro_create);

        intro_create_b = (RelativeLayout) findViewById(R.id.intro_create_b);
        intro_create_b.setOnClickListener(this);
        intro_create_l = (RelativeLayout)findViewById(R.id.intro_create_l);
        intro_create_l.setOnClickListener(this);
        down_btn = (FrameLayout)findViewById(R.id.down_btn_create);
        down_btn.setOnClickListener(this);

        affirm = (RelativeLayout)findViewById(R.id.affirm_create);
        affirm.setOnClickListener(this);
        img_create = (ImageView)findViewById(R.id.img_create);
        start_t = (LinearLayout)findViewById(R.id.start_t);
        start_o = (LinearLayout)findViewById(R.id.start_o);

        start_text = (TextView)findViewById(R.id.start_text);
        over_text = (TextView)findViewById(R.id.over_text);

        img_create.setOnClickListener(this);
        start_t.setOnClickListener(this);
        start_o.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.start_t:
                showTDialog("请选择开始时间",start_text);
                break;

            case R.id.start_o:
                showTDialog("请选择结束时间",over_text);
                break;
            case R.id.img_create:
                showBottomDialog();
                break;
            case R.id.intro_create_l:
                intro_create_l.setVisibility(View.GONE);
                intro_create_b.setVisibility(View.VISIBLE);

                intro_et.setSelected(true);
                intro_et.setFocusable(true);
                intro_et.setFocusableInTouchMode(true);
                intro_et.requestFocus();
                InputMethodManager imm1 = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(intro_et,0);
                break;
            case R.id.down_btn_create:
                intro_create_b.setVisibility(View.GONE);
                intro_create_l.setVisibility(View.VISIBLE);
                break;

            case R.id.intro_create_b:
                intro_et.setSelected(true);
                intro_et.setFocusable(true);
                intro_et.setFocusableInTouchMode(true);
                intro_et.requestFocus();
                InputMethodManager imm2 = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.showSoftInput(intro_et,0);
                break;
            case R.id.r1:
                type = "1";
                img_create.setImageResource(R.mipmap.meeting_img1);
                mBottomSheetDialog.cancel();
                break;
            case R.id.r2:
                type = "2";
                img_create.setImageResource(R.mipmap.meeting_img2);
                mBottomSheetDialog.cancel();
                break;
            case R.id.r3:
                type = "3";
                img_create.setImageResource(R.mipmap.meeting_img3);
                mBottomSheetDialog.cancel();
                break;
            case R.id.r4:
                type = "4";
                img_create.setImageResource(R.mipmap.meeting_img4);
                mBottomSheetDialog.cancel();
                break;
            case R.id.r5:
                type = "5";
                img_create.setImageResource(R.mipmap.meeting_img5);
                mBottomSheetDialog.cancel();
                break;

            case R.id.take_photo_create:
                mBottomSheetDialog.cancel();
                camera();
                break;
            case R.id.select_alum_create:
                mBottomSheetDialog.cancel();
                selectAalum();
                break;
            case R.id.affirm_create:
                create();
                break;
        }
    }

    private void create(){

        final String topic = topic_et.getText().toString().trim();
        final String site = site_et.getText().toString().trim();
        final String st = start_text.getText().toString();
        final String ot = over_text.getText().toString();
        final String intro = intro_et.getText().toString();
        if (TextUtils.isEmpty(topic)){
            ToastUtil.showToast(this,"请填写会议主题");
            return;
        }
        if (TextUtils.isEmpty(site)){
            ToastUtil.showToast(this,"请填写会议地址");
            return;
        }
        if (TextUtils.isEmpty(st)){
            ToastUtil.showToast(this,"请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(ot)){
            ToastUtil.showToast(this,"请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(intro)){
            ToastUtil.showToast(this,"请填写会议描述");
            return;
        }


        if (topic_photo_path != null){
            final BmobFile bmobFile = new BmobFile(new File(topic_photo_path));
            dialog = ProgressDialog.show(this,null,"正在上传。。。");
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null){

                        uploadMeetingData(bmobFile,topic,site,st,ot,intro);

                    }else {
                        dialog.cancel();
                        LogUtil.e(TAG,"上传标签失败"+e.getMessage());
                    }
                }
            });

        }else {
            dialog = ProgressDialog.show(this,null,"正在上传。。。");
            uploadMeetingData(null,topic,site,st,ot,intro);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void uploadMeetingData(BmobFile bmobFile,String topic,String site,
                                   String st,String ot,String intro){
        final MeetingsBean meetingsBean = new MeetingsBean();
        meetingsBean.setMeetingTopic(topic);
        meetingsBean.setSite(site);
        meetingsBean.setStartTime(st);
        meetingsBean.setEndTime(ot);
        meetingsBean.setType(type);
        meetingsBean.setIntroduce(intro);
        meetingsBean.setMeetingImg(bmobFile);
        meetingsBean.setCreator(TSVariable.userInfo.getRealName());
        meetingsBean.setCompany(TSVariable.userInfo.getCompany());
//        meetingsBean.setUsername(TSVariable.userInfo.getUsername());
        meetingsBean.setCreatorId(TSVariable.userInfo.getObjectId());

        meetingsBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (dialog != null){
                    dialog.cancel();
                }
                if (e == null){
                    ToastUtil.showToast(CreateActivity.this,"创建会议成功");
                    Intent intent = new Intent();
                    intent.putExtra("create",meetingsBean);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


    }

    private void showTDialog(String title, final TextView tv){

        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        DatePickerDialog dialog = builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSelected(int[] dates) {

//                if (dates[4] == 0){
//                    tv.setText(dates[0] + "/" + dates[1] + "/" + dates[2]+" "+dates[3] + ":"+"00" );
//                }else {
//                    tv.setText(dates[0] + "/" + dates[1] + "/" + dates[2]+" "+dates[3] + ":"+dates[4] );
//                }
                tv.setText(dates[0] + "/" + dates[1] + "/" + dates[2] );
//                Toast.makeText(getApplicationContext(),
//                        dates[0] + "/" + dates[1] + "/" + dates[2], Toast.LENGTH_SHORT).show();

            }
        }).create(title);

        dialog.show();

    }

    private void showBottomDialog(){
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        mBottomSheetDialog.setContentView(view);

        r1 = (RelativeLayout)view.findViewById(R.id.r1);
        r2 = (RelativeLayout)view.findViewById(R.id.r2);
        r3 = (RelativeLayout)view.findViewById(R.id.r3);
        r4 = (RelativeLayout)view.findViewById(R.id.r4);
        r5 = (RelativeLayout)view.findViewById(R.id.r5);
        take_photo = (LinearLayout)view.findViewById(R.id.take_photo_create);
        select_alum = (LinearLayout)view.findViewById(R.id.select_alum_create);
        select_alum.setOnClickListener(this);
        take_photo.setOnClickListener(this);
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);
        r5.setOnClickListener(this);
        mBottomSheetDialog.show();
    }

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
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

    public void camera() {
        ISCameraConfig config = new ISCameraConfig.Builder()
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                .build();

        ISNav.getInstance().toCameraActivity(this, config, REQUEST_CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                setImg(path);

            }
        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");

            setImg(path);

        }
    }

    private void setImg(String path){
        type = "100";
        topic_photo_path = path;
        Glide.with(this).load(new File(path))
                .error(R.mipmap.meeting_off)
                .crossFade()
                .into(img_create);
    }

}
