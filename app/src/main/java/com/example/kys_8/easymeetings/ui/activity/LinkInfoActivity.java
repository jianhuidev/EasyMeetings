package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.ResBean;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class LinkInfoActivity extends BaseActivity implements View.OnClickListener{

    TextInputEditText et_link_info;
    RelativeLayout affirm;
    private EditText name,type;
    String objId;
    @Override
    protected int getContentView() {
        return R.layout.activity_link_info;
    }

    @Override
    protected void initView() {

        objId = getIntent().getStringExtra("objId");

        et_link_info = (TextInputEditText)findViewById(R.id.et_link_info);

        affirm = (RelativeLayout)findViewById(R.id.affirm_link_info);

        name = (EditText)findViewById(R.id.name_link_info);
        type = (EditText)findViewById(R.id.type_link_info);


    }

    @Override
    protected void initListener() {
        affirm.setOnClickListener(this);
    }

    @Override
    protected String getTitleText() {
        return "链接分享";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.affirm_link_info:
                uploadRes();
                break;
        }
    }


    private void uploadRes(){

        if (TextUtils.isEmpty(name.getText().toString().trim())
                ||TextUtils.isEmpty(type.getText().toString().trim())
                || TextUtils.isEmpty(et_link_info.getText().toString().trim())){
            ToastUtil.showToast(LinkInfoActivity.this,"请完善数据");
            return;
        }
        ResBean resBean = new ResBean();
        resBean.setName(name.getText().toString().trim());
        resBean.setType(type.getText().toString().trim());
        resBean.setLink(et_link_info.getText().toString().trim());
        resBean.setMeetingId(objId);
        resBean.setMark("link1");
        final ProgressDialog dialog = ProgressDialog.show(this, null, "上传资源中。。。");
        resBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dialog.cancel();
                if (e == null){
                    ToastUtil.showToast(LinkInfoActivity.this,"上传资源成功");
                    finish();
                }else {
                    ToastUtil.showToast(LinkInfoActivity.this,"上传资源失败，请检查网络");
                }
            }
        });


    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_link_info);
//    }
}
