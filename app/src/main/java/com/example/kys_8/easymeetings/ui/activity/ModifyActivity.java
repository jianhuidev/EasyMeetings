package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyActivity extends BaseActivity {


    private EditText topic_et,site_et,start_time_et,over_time_et,creator_et,intro_et;

    private RelativeLayout affirm_modify;

    private MeetingsBean meetingsBean;

//    private DetailsFragment mDetailsFragment;
    @Override
    protected int getContentView() {
        return R.layout.activity_modify;
    }

    @Override
    protected void initView() {

//        mDetailsFragment = new DetailsFragment();

        meetingsBean = (MeetingsBean) getIntent().getSerializableExtra("modify");

        topic_et = (EditText) findViewById(R.id.topic_modify);
        site_et = (EditText) findViewById(R.id.site_modify);
        start_time_et = (EditText) findViewById(R.id.start_time_modify);
        over_time_et = (EditText) findViewById(R.id.over_time_modify);
        creator_et = (EditText) findViewById(R.id.creator_modify);
        intro_et = (EditText) findViewById(R.id.intro_modify);
        affirm_modify = (RelativeLayout)findViewById(R.id.affirm_modify);

        if (meetingsBean != null) {

            topic_et.setText(meetingsBean.getMeetingTopic());
            site_et.setText(meetingsBean.getSite());
            start_time_et.setText(meetingsBean.getStartTime());
            over_time_et.setText(meetingsBean.getEndTime());
            creator_et.setText(meetingsBean.getCreator());

            intro_et.setText(meetingsBean.getIntroduce());
        }

        affirm_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyMeetings();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getTitleText() {
        return "修改会议信息";
    }

    private void modifyMeetings(){

        final MeetingsBean bean = new MeetingsBean();
        bean.setMeetingTopic(topic_et.getText().toString().trim());
        bean.setSite(site_et.getText().toString().trim());
        bean.setStartTime(start_time_et.getText().toString().trim());
        bean.setEndTime(over_time_et.getText().toString().trim());
        bean.setCreator(creator_et.getText().toString().trim());
        bean.setIntroduce(intro_et.getText().toString().trim());

        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在提交。。。");
        bean.update(meetingsBean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dialog.cancel();
                if(e==null){

                    ToastUtil.showToast(ModifyActivity.this,"修改会议信息成功");

                    LogUtil.e("ModifyActivity","修改会议信息成功");
                    Intent i = new Intent();
                    i.putExtra("dfragment",bean);
                    setResult(RESULT_OK,i);
                    onBackPressed();
//                    mDetailsFragment.modifySuc();
                }else{
                    LogUtil.e("ModifyActivity","修改会议信息失败："+e.getMessage()+","+e.getErrorCode());
//                    mDetailsFragment.modifySuc();
                }
            }
        });
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_modify);
//
//        initView();
//
//    }
//    private void initView(){
//
//        MeetingsBean meetingsBean = (MeetingsBean) getIntent().getSerializableExtra("modify");
//
//        topic_et = (EditText)findViewById(R.id.topic_modify);
//        site_et = (EditText)findViewById(R.id.site_modify);
//        start_time_et = (EditText)findViewById(R.id.start_time_modify);
//        over_time_et = (EditText)findViewById(R.id.over_time_modify);
//        creator_et = (EditText)findViewById(R.id.creator_modify);
//        intro = (EditText)findViewById(R.id.intro_modify);
//
//        if (meetingsBean != null){
//
//            topic_et.setText(meetingsBean.getMeetingTopic());
//            site_et.setText(meetingsBean.getSite());
//            start_time_et.setText(meetingsBean.getStartTime());
//            over_time_et.setText(meetingsBean.getEndTime());
//            creator_et.setText(meetingsBean.getCreator());
//
//            intro.setText(meetingsBean.getIntroduce());

//
//        }
//    }
}
