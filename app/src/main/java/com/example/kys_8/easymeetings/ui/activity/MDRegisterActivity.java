package com.example.kys_8.easymeetings.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MDRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MDRegisterActivity";
    private EditText email_et,username_et,password_et,real_name_et,affirm_password;
    private Button btn_register;
    private LinearLayout back_register;
//    private TextInputLayout company_layout_r,email_layout_r,password_layout_r,username_layout_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdregister);
        initView();
    }
    private void initView(){

//        username_layout_r = (TextInputLayout)findViewById(R.id.username_layout_r);
//        password_layout_r = (TextInputLayout)findViewById(R.id.password_layout_r);
//        email_layout_r = (TextInputLayout)findViewById(R.id.email_layout_r);
//        company_layout_r = (TextInputLayout)findViewById(R.id.company_layout_r);

        username_et = (EditText)findViewById(R.id.username_register);
        password_et = (EditText)findViewById(R.id.password_register);
        affirm_password = (EditText)findViewById(R.id.affirm_password_register);
        email_et = (EditText)findViewById(R.id.email_register);
        real_name_et = (EditText)findViewById(R.id.real_name_register);
        btn_register = (Button)findViewById(R.id.btn_register);
        back_register = (LinearLayout)findViewById(R.id.btn_back_register);

        btn_register.setOnClickListener(this);
        back_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:    
                final String username = username_et.getText().toString().trim();
                final String passwd = password_et.getText().toString().trim();
                String affirmPasswd = affirm_password.getText().toString().trim();
                String email = email_et.getText().toString().trim();
                String realName = real_name_et.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(realName)){
                    if (TextUtils.isEmpty(username))
                        username_et.setError("用户名不能为空！");
                    if (TextUtils.isEmpty(email))
                        email_et.setError("邮箱不能为空！");
                    if (TextUtils.isEmpty(realName))
                        real_name_et.setError("真实姓名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(passwd)){
                    ToastUtil.showToast(MDRegisterActivity.this,"密码不能为空！");
                    return;
                }

                if (passwd.length()<=6 && passwd.length()>=18){
                    ToastUtil.showToast(MDRegisterActivity.this,"密码长度为6-18位");
                    return;
                }else if (!passwd.equals(affirmPasswd)){
                    ToastUtil.showToast(MDRegisterActivity.this,"确认密码密码不正确");
                }


//                if (TextUtils.isEmpty(email)){
//                    email_et.setError("邮箱不能为空！");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(company)){
//                    company_et.setError("公司不能为空！");
//                    return;
//                }

                register(username, passwd, email, realName,new RegisterInterface() {
                    @Override
                    public void success(User user) {
                        ToastUtil.showToast(MDRegisterActivity.this,"注册成功");
                        Intent intent = new Intent();
                        intent.putExtra("username",username);
                        intent.putExtra("password",passwd);
                        setResult(RESULT_OK,intent);
                        onBackPressed();
                        LogUtil.e(TAG,"注册成功");
                    }

                    @Override
                    public void err(BmobException e) {
                        ToastUtil.showToast(MDRegisterActivity.this,"注册失败");
                        LogUtil.e(TAG,"注册失败："+e.getMessage());
                    }
                });

                break;
            case R.id.btn_back_register:
                onBackPressed();
                break;
        }
    }

    private void register(String username, String passwd, String email,
                          String realName, final RegisterInterface callback){
        final ProgressDialog dialog2 = ProgressDialog.show(this,
                null, "正在注册。。。");

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwd);
        user.setEmail(email);
        user.setRealName(realName);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                dialog2.cancel();
                if (e == null){
                    callback.success(user);

                }else {
                    callback.err(e);
                }
            }
        });
    }

    interface RegisterInterface{
        void success(User user);
        void err(BmobException e);
    }
}
