package com.example.kys_8.easymeetings.ui.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
//import com.example.kys_8.easymeetings.ui.fragment.ContactFragment;
//import com.example.kys_8.easymeetings.ui.fragment.ConversationFragment;
import com.example.kys_8.easymeetings.ui.fragment.DetailsFragment;
import com.example.kys_8.easymeetings.ui.fragment.MemberFragment;
import com.example.kys_8.easymeetings.ui.fragment.ResFragment;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MeetingDetailsActivity extends AppCompatActivity {


    private BottomNavigationView navigation;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String TAG = "MeetingDetailsActivity";

    /**
     * fragment实例，用来标记当前显示的是哪一个fragment
     */
    private Fragment mFragment;

    private DetailsFragment mDetailsFragment;
    private MemberFragment mMemberFragment;
    private ResFragment mResFragment;

    /**
     * 当前会议
     */
    public MeetingsBean mMeetingsBean;

    public int mark;

    private Toolbar toolbar;

    public AppBarLayout appbar_details;
    public SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);

        mark = getIntent().getIntExtra("mark",0);

        TSVariable.fragmentMark = mark;

        mMeetingsBean = (MeetingsBean) getIntent().getSerializableExtra("meeting");

        initView();
    }
    private void initView(){
        toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(mMeetingsBean.getMeetingTopic());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        appbar_details = (AppBarLayout)findViewById(R.id.appbar_details);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mDetailsFragment == null){
            mDetailsFragment = new DetailsFragment();
            mFragmentTransaction.add(R.id.content_details,mDetailsFragment);
            mFragment = mDetailsFragment;
        }else {
            mFragmentTransaction.show(mDetailsFragment);
            mFragmentTransaction.hide(mFragment);
            mFragment = mDetailsFragment;
        }
        mFragmentTransaction.commit();

        navigation = findViewById(R.id.navigation_details);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRefresh = (SwipeRefreshLayout)findViewById(R.id.refresh_details);
        mRefresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_yellow, R.color.google_red, R.color.google_green);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.nav_1_details:
//                            LogUtil.e(TAG,"   xinxi");
                            mFragmentTransaction.hide(mFragment);
                            if (mDetailsFragment == null) {
                                mDetailsFragment = new DetailsFragment();
                                mFragmentTransaction.add(R.id.content_details, mDetailsFragment);
                                mFragment = mDetailsFragment;
                            } else {
                                mFragmentTransaction.show(mDetailsFragment);
                                mFragment = mDetailsFragment;
                            }
                            mFragmentTransaction.commit();
                            return true;

                        case R.id.nav_resource_details:
                            LogUtil.e(TAG,"   xinxi");
                            mFragmentTransaction.hide(mFragment);
                            if (mResFragment == null) {
                                mResFragment = new ResFragment();
                                mFragmentTransaction.add(R.id.content_details, mResFragment);
                                mFragment = mResFragment;
                            } else {
                                mFragmentTransaction.show(mResFragment);
                                mFragment = mResFragment;
                            }
                            mFragmentTransaction.commit();
                            return true;

                        case R.id.nav_member_details:
                            LogUtil.e(TAG,"   chengyuan");
                            mFragmentTransaction.hide(mFragment);
                            if (mMemberFragment == null) {
                                mMemberFragment = new MemberFragment();
                                mFragmentTransaction.add(R.id.content_details, mMemberFragment);
                                mFragment = mMemberFragment;
                            } else {
                                mFragmentTransaction.show(mMemberFragment);
                                mFragment = mMemberFragment;
                            }
                            mFragmentTransaction.commit();



//                            Intent intent = new Intent(MeetingDetailsActivity.this, MemberActivity.class);
//                            intent.putExtra("meetingData",meetingsBean);
//                            intent.putExtra("mark",mark);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                        MeetingDetailsActivity.this,toolbar,"toolBer").toBundle());
//                            } else {
//                                startActivity(intent);
//                            }


                            return true;
                    }
                    return false;
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.check_in:
                LogUtil.e(TAG,"check in00000");
                queryCheckIn(new CheckInCallBack() {
                    @Override
                    public void success(MeetingsBean bean) {
                        if (bean.getAllowSignIn() != null && !bean.getAllowSignIn().isEmpty() && bean.getAllowSignIn().equals("1")){
                            //允许签到了
                            saveAttendCheckIn(bean);//允许签到，在当前的会议添加字段
                        }else {
                            ToastUtil.showToast(MeetingDetailsActivity.this,"工作人员还未开始签到或签到已结束");
                        }
                    }

                    @Override
                    public void err(BmobException e) {
                        ToastUtil.showToast(MeetingDetailsActivity.this,"请检查网络");
                        LogUtil.e("MeetingDetailsActivity",e.getMessage());
                    }
                });

                break;
            case R.id.qr_code:
                qrDialog();
                break;

            case R.id.start_check_in:
                ToastUtil.showToast(MeetingDetailsActivity.this,"签到情况到成员界面查看");
                startOrOverCheckIn("1");
                break;

            case R.id.over_check_in:
                startOrOverCheckIn("0");
                break;
        }
        return true;
    }

    /**
     * 工作人员开启或结束签到
     * @param signIn
     */
    private void startOrOverCheckIn(final String signIn){
        if (mMeetingsBean == null && mMeetingsBean.getObjectId() == null){
            return;
        }
        MeetingsBean bean = new MeetingsBean();
        bean.setAllowSignIn(signIn);
        bean.update(mMeetingsBean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    if (signIn.equals("1")){
                        ToastUtil.showToast(MeetingDetailsActivity.this,"开始签到");
                    }else {
                        ToastUtil.showToast(MeetingDetailsActivity.this,"结束签到");
                    }
                }else {
                    ToastUtil.showToast(MeetingDetailsActivity.this,"请检查网络");
                }
            }
        });

    }


    /**
     * 保存参会人员的参会情况
     * @param bean 获取当前会议的objId
     */
    private void saveAttendCheckIn(MeetingsBean bean){
        MeetingsBean meetingsBean = new MeetingsBean();
        meetingsBean.setObjectId(bean.getObjectId());
        if (TSVariable.userInfo == null){
            return;
        }
        meetingsBean.addUnique("attendCheckInId",TSVariable.userInfo.getObjectId());//只保存一次的方法
        meetingsBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.showToast(MeetingDetailsActivity.this,"签到成功");
                    LogUtil.e(TAG,"签到成功");
                }else{
                    ToastUtil.showToast(MeetingDetailsActivity.this,"签到失败");
                    LogUtil.e(TAG,"签到失败："+e.getMessage());
                }
            }
        });

    }

    /**
     * 根据不同情况，判断显示的菜单，二维码或签到
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mark == 1){
            getMenuInflater().inflate(R.menu.details_menu,menu);
        }else if (mark == 2){
            getMenuInflater().inflate(R.menu.details_qr_code,menu);
        }else {
            return super.onCreateOptionsMenu(menu);
        }
        return true;
    }

    /**
     * 查询当前会议是否允许签到
     */
    private void queryCheckIn(final CheckInCallBack callBack){
        if (mMeetingsBean == null && mMeetingsBean.getObjectId() == null){
            return;
        }
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
        query.getObject(mMeetingsBean.getObjectId(), new QueryListener<MeetingsBean>() {
            @Override
            public void done(MeetingsBean meetingsBean, BmobException e) {
                if (e == null){
                    callBack.success(meetingsBean);
                }else {
                    callBack.err(e);
                }
            }
        });
    }

    /**
     * 签到请求的回调接口
     */
    interface CheckInCallBack{
        void success(MeetingsBean bean);
        void err(BmobException e);
    }

    TextView code_qr,time_qr,topic_qr;
    ImageView image_qr,img_meetings_qr;
    FrameLayout code_layout;
    /**
     * 二维码对话框
     */
    private void qrDialog(){
        Dialog dialog = new Dialog(MeetingDetailsActivity.this);

        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.qr_dialog);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        window.setGravity(Gravity.CENTER);
        code_layout = (FrameLayout)window.findViewById(R.id.code_layout_dialog);
        code_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("",mMeetingsBean.getObjectId());
                cm.setPrimaryClip(mClipData);
                ToastUtil.showToast(MeetingDetailsActivity.this,"已复制会议号："+mMeetingsBean.getObjectId());
            }
        });
        code_qr = (TextView)window.findViewById(R.id.code_qr);

        time_qr = (TextView)window.findViewById(R.id.time_qr);
        topic_qr = (TextView)window.findViewById(R.id.topic_qr);
        image_qr = (ImageView)window.findViewById(R.id.image_qr);
        img_meetings_qr = (ImageView)window.findViewById(R.id.img_meetings_qr);


        code_qr.setText("会议号：" + mMeetingsBean.getObjectId());
        time_qr.setText(mMeetingsBean.getStartTime());
        topic_qr.setText(mMeetingsBean.getMeetingTopic());

        setImg(img_meetings_qr);
        image_qr.setImageBitmap(CodeUtils.createImage(mMeetingsBean.getObjectId(),400,400,
                BitmapFactory.decodeResource(getResources(),R.mipmap.logo)));

    }


    /**
     * 设置图片
     */
    private void setImg(ImageView img){
        if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("1")){
            img.setImageResource(R.mipmap.meeting_img1);
        }else if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("2")){
            img.setImageResource(R.mipmap.meeting_img2);
        }else if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("3")){
            img.setImageResource(R.mipmap.meeting_img3);
        }else if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("4")){
            img.setImageResource(R.mipmap.meeting_img4);
        }else if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("5")){
            img.setImageResource(R.mipmap.meeting_img5);
        }else if (mMeetingsBean.getType() != null && mMeetingsBean.getType().equals("100")){
            Glide.with(this).load(mMeetingsBean.getMeetingImg().getUrl())
                    .error(R.mipmap.meeting_off)
                    .crossFade()
                    .into(img);
        }else {
            img.setImageResource(R.mipmap.meeting_off);
        }
    }

}
