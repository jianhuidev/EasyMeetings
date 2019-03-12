package com.example.kys_8.easymeetings.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.SpUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.google.gson.Gson;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MDLoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MDLoginActivity";

    public static final int REQUEST_REGISTER = 1;

    Button register_btn,login_btn;
    AppBarLayout appbar_layout;
    CardView card;
    private EditText username_et,password_et;
    private RelativeLayout forget;
//    private TextInputLayout username_layout_l,password_layout_l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdlogin);
        initView();
    }

    private void initView(){

//        username_layout_l = (TextInputLayout)findViewById(R.id.username_layout_l);
//        password_layout_l = (TextInputLayout)findViewById(R.id.password_layout_l);

        username_et = (EditText)findViewById(R.id.username_login);
        password_et = (EditText)findViewById(R.id.password_login);
        login_btn = (Button) findViewById(R.id.btn_login);

        register_btn = (Button)findViewById(R.id.btn_register_login);
        appbar_layout = (AppBarLayout)findViewById(R.id.appbar_layout_login);
        card = (CardView)findViewById(R.id.card_login);

        forget = (RelativeLayout)findViewById(R.id.forget_login);
        forget.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_register_login:

                Intent intent = new Intent(this,MDRegisterActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Pair<AppBarLayout, String> layoutPair = Pair.create(appbar_layout, "appbar_layout");
                    Pair<CardView, String> cardPair = Pair.create(card,"card" );
//                    Pair<Button, String> loginBtnPair = Pair.create(login_btn,"btn" );
                    Pair<Button, String> registerBtnPair = Pair.create(register_btn,"btn" );

                    Pair[] pairs = {layoutPair,cardPair,registerBtnPair};

                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(MDLoginActivity.this,pairs);

                    startActivityForResult(intent,REQUEST_REGISTER,compat.toBundle());
                }else {
                    startActivityForResult(intent,REQUEST_REGISTER);
                }
                break;

            case R.id.forget_login:

                Intent intent_forget = new Intent(this,ForgetActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Pair<AppBarLayout, String> layoutPair = Pair.create(appbar_layout, "appbar_layout");
                    Pair<CardView, String> cardPair = Pair.create(card,"card" );
                    Pair<Button, String> registerBtnPair = Pair.create(register_btn,"btn" );
                    Pair[] pairs = {layoutPair,cardPair,registerBtnPair};

                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(MDLoginActivity.this,pairs);
                    startActivity(intent_forget,compat.toBundle());
                }else {
                    startActivity(intent_forget);
                }

                break;

            case R.id.btn_login:
                String username = username_et.getText().toString().trim();
                final String passwd = password_et.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    username_et.setError("用户名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(passwd)){
                    ToastUtil.showToast(MDLoginActivity.this,"密码不能为空！");
                    return;
                }

                if (password_et.getText().toString().length()<=6 && password_et.getText().toString().length()>=18){
                    ToastUtil.showToast(MDLoginActivity.this,"密码长度为6-18位");
                    return;
                }

                login(username, passwd, new LoginInterface() {
                    @Override
                    public void success(User user) {
                        TSVariable.userInfo = user;
                        TSVariable.curUserPassWord = passwd;
//                        SpUtil.put("userInfo", new Gson().toJson(user));
                        startActivity(new Intent(MDLoginActivity.this,MainActivity.class));
                        finish();
                    }
                    @Override
                    public void nameOrPasswordErr(BmobException e) {
                        ToastUtil.showToast(MDLoginActivity.this,"请检查账户及密码");
                        LogUtil.e(TAG,"查询失败："+e.getMessage()+" "+e.getErrorCode());
                    }
                });

//                if (!username.isEmpty() && !passwd.isEmpty() ){
//
//                }else {
//                    showError(username_layout_l,"用户名为空！");
//                    ToastUtil.showToast(MDLoginActivity.this,"请检查账户及密码是否填写完全");
//                }
                break;
        }
    }

    private void login(String name,String password, final LoginInterface loginInterface){
        final ProgressDialog dialog2 = ProgressDialog.show(this, null, "正在登录。。。");
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                dialog2.cancel();
                if (e == null){
                    loginInterface.success(user);
                }else {
                    loginInterface.nameOrPasswordErr(e);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REGISTER){
            if (resultCode == RESULT_OK){
                username_et.setText(data.getStringExtra("username"));
                password_et.setText(data.getStringExtra("password"));
            }
        }
    }

    interface LoginInterface{
        void success(User user);
        void nameOrPasswordErr(BmobException e);
    }
}
