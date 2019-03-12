package com.example.kys_8.easymeetings.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kys_8.easymeetings.R;

public abstract class BaseActivity extends AppCompatActivity {

//    protected P mPresenter;
    protected LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initBaseView();
//        mPresenter = loadPresenter();
        initView();
        initListener();
    }

    private void initBaseView(){

        Toolbar toolbar = findViewById(R.id.toolbar_base);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getTitleText());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        content = (LinearLayout)findViewById(R.id.content_base);
        content.addView(View.inflate(this,getContentView(),null));
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

    /**
     * 获取中间内容显示区
     *
     * @return 布局
     */
    protected abstract int getContentView();

//    protected abstract P loadPresenter();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    protected abstract String getTitleText();

}
