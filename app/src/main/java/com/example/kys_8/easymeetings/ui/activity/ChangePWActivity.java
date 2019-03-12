package com.example.kys_8.easymeetings.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePWActivity extends BaseActivity implements View.OnClickListener{

    private TextInputEditText old_password_et,new_password_et;

    private RelativeLayout affirm_modify;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_pw;
    }

    @Override
    protected void initView() {

        old_password_et = (TextInputEditText)findViewById(R.id.old_password_change);
        new_password_et = (TextInputEditText)findViewById(R.id.new_password_change);

        affirm_modify = (RelativeLayout)findViewById(R.id.affirm_modify_change);

    }

    @Override
    protected void initListener() {
        affirm_modify.setOnClickListener(this);

    }

    @Override
    protected String getTitleText() {
        return "修改密码";
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.affirm_modify_change:

                if (TextUtils.isEmpty(old_password_et.getText().toString().trim()) ||
                        TextUtils.isEmpty(new_password_et.getText().toString().trim())){
                    ToastUtil.showToast(this,"新旧密码不能为空");
                    return;
                }

                if (old_password_et.getText().toString().trim().equals(TSVariable.curUserPassWord)){

                    TSVariable.userInfo.setValue("password",new_password_et.getText().toString().trim());
                    TSVariable.userInfo.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                ToastUtil.showToast(ChangePWActivity.this,"修改密码成功");
                            }else {
                                ToastUtil.showToast(ChangePWActivity.this,"修改密码失败");
                                LogUtil.e("ChangePWActivity",e.getMessage());
                            }
                        }
                    });

                }else {
                    ToastUtil.showToast(this,"请检查旧密码是否填写正确");
                }


                break;
        }
    }

}
