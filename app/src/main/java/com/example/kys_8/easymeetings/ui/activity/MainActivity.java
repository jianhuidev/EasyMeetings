package com.example.kys_8.easymeetings.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;

import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.adapter.MyPageAdapter;
import com.example.kys_8.easymeetings.ui.fragment.AttendFragment;
import com.example.kys_8.easymeetings.ui.fragment.FindFragment;
import com.example.kys_8.easymeetings.ui.fragment.MyFragment;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragment mMyFragment;
    private AttendFragment mAttendFragment;
    public ProgressBar mProgressBar;

    private Button cancel_btn,sure_btn;

    private ImageView avatar_nav_header,image_bg_nav_header;

    private TextView username_nav_header,mail_nav_header;

    private static final String TAG = "MainActivity";

    /**
     * flag 为true 才可以退出软件
     */
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
    }

    private void initView() {
        mProgressBar = findViewById(R.id.pb_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.meeting));
        drawer = findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        FrameLayout nav_header = headerView.findViewById(R.id.nav_layout_header);
        nav_header.setOnClickListener(this);

        avatar_nav_header = (ImageView)nav_header.findViewById(R.id.avatar_nav_header);
        image_bg_nav_header = (ImageView)nav_header.findViewById(R.id.image_bg_nav_header);
        username_nav_header = (TextView)nav_header.findViewById(R.id.username_nav_header);
        mail_nav_header = (TextView)nav_header.findViewById(R.id.mail_nav_header);


//        if (TSVariable.userInfo != null){
//            username_nav_header.setText(TSVariable.userInfo.getUsername());
//            mail_nav_header.setText(TSVariable.userInfo.getEmail());
//            if (TSVariable.userInfo.getAvatar() != null){
//                Glide.with(this).load(TSVariable.userInfo.getAvatar().getUrl())
//                        .error(R.mipmap.meeting_img3)
//                        .crossFade()
//                        .into(img_nav_header);
//            }
//        }
//        setUserInfo();

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void initViewPager() {
        mTabLayout = findViewById(R.id.tab_layout_main);
        mViewPager = findViewById(R.id.view_pager_main);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.attend_meeting));
        titles.add(getString(R.string.my_meetings));
        titles.add(getString(R.string.find));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        mAttendFragment = new AttendFragment();
        mMyFragment = new MyFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mAttendFragment);
        fragments.add(mMyFragment);
        fragments.add(new FindFragment());

        mViewPager.setOffscreenPageLimit(2);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(),fragments,titles);
        mViewPager.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(pageAdapter);

        mViewPager.addOnPageChangeListener(pageChangeListener);

    }

    /**
     * 头型背景设置
     */
    private void setUserInfo(){
        if (TSVariable.userInfo != null){
            username_nav_header.setText(TSVariable.userInfo.getUsername());
            mail_nav_header.setText(TSVariable.userInfo.getEmail());

            if (TSVariable.userInfo.getAvatar() != null){
                Glide.with(this).load(TSVariable.userInfo.getAvatar().getUrl())
                        .error(R.mipmap.meeting_img3)
                        .crossFade()
                        .into(avatar_nav_header);
            }

            initBg(TSVariable.userInfo.getUserBg());
        }
    }

    private void initBg(String bg){

        if (bg != null && bg.equals("c1")){
            Glide.with(this).load(R.mipmap.nav_header01)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("c2")){
            Glide.with(this).load(R.mipmap.nav_header02)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("c3")){
            Glide.with(this).load(R.mipmap.nav_header03)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("d1")){
            Glide.with(this).load(R.mipmap.nav_header04)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("d2")){
            Glide.with(this).load(R.mipmap.nav_header05)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("d3")){
            Glide.with(this).load(R.mipmap.nav_header06)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("d4")){
            Glide.with(this).load(R.mipmap.nav_header07)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("s1")){
            Glide.with(this).load(R.mipmap.nav_header08)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);

        }else if (bg != null && bg.equals("s2")){
            Glide.with(this).load(R.mipmap.nav_header09)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }else if (bg != null && bg.equals("bg")){
            if (TSVariable.userInfo.getUserBackground() != null){
                Glide.with(this).load(TSVariable.userInfo.getUserBackground().getUrl())
                        .error(R.mipmap.nav_header1)
                        .crossFade()
                        .into(image_bg_nav_header);
            }
        }else {
            Glide.with(this).load(R.mipmap.nav_header1)
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image_bg_nav_header);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if (position == 1) {
                fab.show();
            } else {
                fab.hide();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.settings_drawer:
                intent.setClass(this,SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.about_us_drawer:
                intent.setClass(this,AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.version_drawer:
                intent.setClass(this,VActivity.class);
                startActivity(intent);
                break;

            case R.id.scan_drawer:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
                    //没有权限去申请
                }else {
                    qrScan();
                }
                break;
            case R.id.feedback_drawer:
                intent.setClass(this,FBActivity.class);
                startActivity(intent);
                break;
            case R.id.photo_drawer:

                startActivity(new Intent(MainActivity.this,WallpaperActivity.class));
                break;
        }
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fab_main:
                Intent intent = new Intent(this,CreateActivity.class);
                startActivityForResult(intent,110);
                break;
            case R.id.nav_layout_header:
                startActivityForResult(new Intent(MainActivity.this,PersonalActivity.class),122);
//                startActivity(new Intent(MainActivity.this,PersonalActivity.class));
                break;
//            case R.id.sure_dialog:
//                mAttendFragment.meetingsAdapter.addItem(0,);
//
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 110:

                if (resultCode == RESULT_OK){
                    MeetingsBean meetingsBean = (MeetingsBean) data.getSerializableExtra("create");
                    if (meetingsBean != null)
                        mMyFragment.meetingsAdapter.addItem(0,meetingsBean);
                }
                break;
            case 111:
                //处理扫描结果（在界面上显示）
                if (null != data){
                    Bundle bundle = data.getExtras();
                    if (bundle == null){
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
                        String result = bundle.getString(CodeUtils.RESULT_STRING);

                        if (result != null)
                            queryMeetingWithId(result);

                        LogUtil.e(TAG,result);

                    }else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED){
                        ToastUtil.showToast(this, "解析二维码失败");
                    }
                }
                break;
            case 122:
                //个人中心反馈
                if (resultCode == RESULT_OK){
                    String outStr = data.getStringExtra("out");
                    if (!TextUtils.isEmpty(outStr) && outStr.equals("log_out")){

                        finish();
                        startActivity(new Intent(MainActivity.this,MDLoginActivity.class));

                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (flag){

                super.onBackPressed();

            }else {
                flag = true;
                ToastUtil.showToast(this,"再按一次将退出程序");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        flag = false;

                    }
                },2000);
            }
        }
    }

    public void queryMeetingWithId(String objId){
        mProgressBar.setVisibility(View.VISIBLE);
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();

//        query.addWhereNotEqualTo("username",TSVariable.userInfo.getUsername());
        query.getObject(objId, new QueryListener<MeetingsBean>() {
            @Override
            public void done(MeetingsBean meetingsBean, BmobException e) {
                mProgressBar.setVisibility(View.INVISIBLE);
                if (e == null){

                    if (meetingsBean.getCreatorId() != null && meetingsBean.getCreatorId().equals(TSVariable.userInfo.getObjectId())){
                        ToastUtil.showToast(MainActivity.this,"自己创建的会议，不需要添加");
                    }else {
                        showDialog(meetingsBean);
                    }



                }else{
                    ToastUtil.showToast(MainActivity.this,"该会议可能已取消");
                    LogUtil.e(TAG,e.getMessage() + ","+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 弹出对话框，让用户决定加入与否
     * @param meetingsBean
     */
    public void showDialog(final MeetingsBean meetingsBean){
        final Dialog dialog = new Dialog(MainActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_after_scan);

        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimScale);
        window.setGravity(Gravity.CENTER);

        ImageView img = (ImageView)window.findViewById(R.id.meeting_img_dialog);
        TextView topic = (TextView)window.findViewById(R.id.meeting_name_dialog);
        TextView t = (TextView)window.findViewById(R.id.meeting_time_dialog);
        TextView site = (TextView)window.findViewById(R.id.meeting_site_dialog);
        TextView creator = (TextView)window.findViewById(R.id.meeting_creator_dialog);

        cancel_btn = (Button)window.findViewById(R.id.cancel_dialog);
        sure_btn = (Button)window.findViewById(R.id.sure_dialog);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //保存参会状态
                saveAttendMeeting(meetingsBean,new AttendInterface() {
                    @Override
                    public void success() {
                        mAttendFragment.queryMeetingAttend();
                        mAttendFragment.meetingsAdapter.addItem(0,meetingsBean);
                    }

                    @Override
                    public void err() {

                    }
                });
                dialog.cancel();
            }
        });

        setImg(meetingsBean,img);
        topic.setText(meetingsBean.getMeetingTopic());
        t.setText(meetingsBean.getStartTime());
        site.setText(meetingsBean.getSite());
        creator.setText(meetingsBean.getCreator());
    }


    interface AttendInterface{
        void success();
        void err();
    }

    /**
     * 当用户点击加入后，将数据保存到数据库
     * @param bean
     * @param attendInterface
     */
    private void saveAttendMeeting(MeetingsBean bean,final AttendInterface attendInterface){
        MeetingsBean meetingsBean = new MeetingsBean();
        meetingsBean.setObjectId(bean.getObjectId());
        if (TSVariable.userInfo == null){
            return;
        }
        meetingsBean.addUnique("attendId",TSVariable.userInfo.getObjectId());
        meetingsBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if(e==null){
                    ToastUtil.showToast(MainActivity.this,"参加会议成功");
                    LogUtil.e(TAG,"参会成功");

                    attendInterface.success();
                }else{
                    ToastUtil.showToast(MainActivity.this,"参加会议失败");
                    LogUtil.e(TAG,"参会失败："+e.getMessage());
                    attendInterface.err();
                }

            }
        });

    }

    /**
     * 通过会议号参加会议
     */
    public void inputDialog(){
        final Dialog dialog = new Dialog(this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_code);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimScale);
        window.setGravity(Gravity.CENTER);
        final EditText code_et = (EditText) window.findViewById(R.id.meeting_code_et);


//        Button cancel_input = (Button)window.findViewById(R.id.cancel_input);

        Button sure_input = (Button)window.findViewById(R.id.sure_input);

//        cancel_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LogUtil.e(TAG,"xjxjxjxjxj");
//                dialog.cancel();
//            }
//        });
        sure_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = code_et.getText().toString().trim();

                if (!TextUtils.isEmpty(code)){

                    queryMeetingWithId(code);
                }else {
                    ToastUtil.showToast(MainActivity.this,"请输入会议号");
                }
                dialog.cancel();
            }
        });
    }
//    private void saveAttendMeeting(MeetingsBean bean){
//        AttendBean attendBean = new AttendBean();
//        attendBean.setMeetingTopic(bean.getMeetingTopic());
//        attendBean.setCreator(bean.getCreator());
//        attendBean.setStartTime(bean.getStartTime());
//        attendBean.setEndTime(bean.getEndTime());
//        attendBean.setIntroduce(bean.getIntroduce());
//        attendBean.setMeetingImg(bean.getMeetingImg());
//        attendBean.setType(bean.getType());
//        attendBean.setSite(bean.getSite());
//        attendBean.setUsername(bean.getUsername());
//        attendBean.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    ToastUtil.showToast(MainActivity.this,"已成功参加该会议！");
//                }else{
//                    ToastUtil.showToast(MainActivity.this,"参加会议失败，请检查网络！");
//                }
//            }
//        });
//
//    }

    /**
     * 设置图片
     */
    private void setImg(MeetingsBean meetingsBean,ImageView img){
        if (meetingsBean.getType() != null && meetingsBean.getType().equals("1")){
            img.setImageResource(R.mipmap.meeting_img1);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("2")){
            img.setImageResource(R.mipmap.meeting_img2);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("3")){
            img.setImageResource(R.mipmap.meeting_img3);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("4")){
            img.setImageResource(R.mipmap.meeting_img4);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("5")){
            img.setImageResource(R.mipmap.meeting_img5);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("100")){
            Glide.with(this).load(meetingsBean.getMeetingImg().getUrl())
                    .error(R.mipmap.meeting_off)
                    .crossFade()
                    .into(img);
        }else {
            img.setImageResource(R.mipmap.meeting_off);
        }
    }


    public void qrScan(){
        ToastUtil.showToast(this,"通过扫描二维码参加会议");
        startActivityForResult(new Intent(MainActivity.this,ScanActivity.class),111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    qrScan();
                }else {
                    ToastUtil.showToast(MainActivity.this,"没有权限，不能使用该功能！");
                }
                break;
        }
    }
}
