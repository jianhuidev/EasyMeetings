package com.example.kys_8.easymeetings.ui.activity;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.Config;
import com.example.kys_8.easymeetings.ui.adapter.GuidePaperAdapter;
import com.example.kys_8.easymeetings.utils.LogUtil;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private RelativeLayout bgGuide;
    private ViewPager mViewPager;
    private Button mBtnGo;
    private GuidePaperAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        getDeviceDensity();

        initViews();
    }

    private void initViews() {

        bgGuide = (RelativeLayout)findViewById(R.id.bg_guide) ;
        mViewPager = (ViewPager)findViewById(R.id.vp_guide);
        mBtnGo = (Button)findViewById(R.id.btn_go);

        //实例化各个界面的布局对象
        View view1 = View.inflate(this, R.layout.guide_view, null);
        View view2 = View.inflate(this, R.layout.guide_view, null);
        View view3 = View.inflate(this, R.layout.guide_view, null);

        ((ImageView)view1.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.guide1);
        ((ImageView)view2.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.guide2);
        ((ImageView)view3.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.guide03);

        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MDLoginActivity.class));
                finish();
            }
        });

        view1.findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        view2.findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        view3.findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        ((TextView)view1.findViewById(R.id.tv_title)).setText("会议管理");
        ((TextView)view1.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#FFC400"));
        ((TextView)view2.findViewById(R.id.tv_title)).setText("快捷参会");
        ((TextView)view2.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#F57C00"));
        ((TextView)view3.findViewById(R.id.tv_title)).setText("资源上传");
        ((TextView)view3.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#16c5c6"));

        ((TextView)view1.findViewById(R.id.tv_desc)).setText("信息查收，维护，轻松无障碍\n");
        ((TextView)view2.findViewById(R.id.tv_desc)).setText("扫一扫，会议号\n简化会议流程");
        ((TextView)view3.findViewById(R.id.tv_desc)).setText("资源分享\n会议更便捷");

        mViewPager = (ViewPager) findViewById(R.id.vp_guide);

        ArrayList<View> views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        vpAdapter = new GuidePaperAdapter(views);

        mViewPager.setOffscreenPageLimit(views.size());
        mViewPager.setPageMargin(-dip2px(135));
        mViewPager.setAdapter(vpAdapter);
//        mViewPager.setAdapter(vpAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArgbEvaluator evaluator = null;
                evaluator = new ArgbEvaluator();

                int evaluate = getResources().getColor(R.color.google_blue);
                if (position == 0) {
                    evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.google_blue), getResources().getColor(R.color.google_green));
                } else if (position == 1) {
                    evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.google_green), getResources().getColor(R.color.google_yellow));
                } else if (position == 2) {
                    evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.google_yellow), getResources().getColor(R.color.google_red));
                } else {
                    evaluate = getResources().getColor(R.color.google_red);
                }
                ((View) mViewPager.getParent()).setBackgroundColor(evaluate);
            }

            @Override
            public void onPageSelected(int position) {
                if (position + 1 == mViewPager.getAdapter().getCount()) {
                    if (mBtnGo.getVisibility() != View.VISIBLE) {
                        mBtnGo.setVisibility(View.VISIBLE);
                        mBtnGo.startAnimation(AnimationUtils.loadAnimation(GuideActivity.this, R.anim.down_in));
                    }
                } else {
                    if (mBtnGo.getVisibility() != View.GONE) {
                        mBtnGo.setVisibility(View.GONE);
                        mBtnGo.startAnimation(AnimationUtils.loadAnimation(GuideActivity.this, R.anim.down_out));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_TXT_SCALE = 0.0f;
            private static final float MIN_TXT_ALPHA = 0.0f;

            @SuppressLint("NewApi")
            @Override
            public void transformPage(View view, float position) {
                View mGuideImage = view.findViewById(R.id.tv_pic);
                View mTitle = view.findViewById(R.id.tv_title);
                View mDesc = view.findViewById(R.id.tv_desc);

                LogUtil.e("transformPage - >",position+"");
                int viewWidth = mDesc.getWidth();

                if (position < -1) {
                    mTitle.setAlpha(0);
                    mDesc.setAlpha(0);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float scaleTxtFactor = Math.max(MIN_TXT_SCALE, 1 - Math.abs(position));

                    mGuideImage.setScaleX(scaleFactor);
                    mGuideImage.setScaleY(scaleFactor);

                    mTitle.setScaleX(scaleTxtFactor);
                    mTitle.setScaleY(scaleTxtFactor);
                    mTitle.setAlpha(MIN_TXT_ALPHA + (scaleTxtFactor - MIN_TXT_SCALE) / (1 - MIN_TXT_SCALE) * (1 - MIN_TXT_ALPHA));

                    mDesc.setAlpha(mTitle.getAlpha());
                    mDesc.setScaleX(scaleTxtFactor);
                    mDesc.setScaleY(scaleTxtFactor);
                } else {
                    mTitle.setAlpha(0);
                    mDesc.setAlpha(0);
                }
            }
        });
    }




    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
