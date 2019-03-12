package com.example.kys_8.easymeetings.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetActivity extends AppCompatActivity {

    private TextInputEditText email_et;
    private Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        email_et = (TextInputEditText)findViewById(R.id.email_et_forget);
        submitBtn = (Button)findViewById(R.id.btn_submit_forget);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    /**
     * 会向你的邮箱发送邮件，通过邮件可以修改密码
     */
    private void resetPassword(){
        final String email = email_et.getText().toString().trim();
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ToastUtil.showToast(ForgetActivity.this,"请到"+email+"，进行密码修改");
                }else {
                    ToastUtil.showToast(ForgetActivity.this,"发送邮件失败，请检查网络");
                }
            }
        });

    }

}
