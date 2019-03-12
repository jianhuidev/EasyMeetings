package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.utils.ToastUtil;

public class FBActivity extends BaseActivity {

    private LinearLayout et_layout;
    private EditText et_fb;

    private RelativeLayout affirm_fb;
    @Override
    protected int getContentView() {
        return R.layout.activity_fb;
    }

    @Override
    protected void initView() {
        et_layout = (LinearLayout)findViewById(R.id.et_layout_fb);
        et_fb = (EditText)findViewById(R.id.et_fb);
        affirm_fb = (RelativeLayout)findViewById(R.id.affirm_fb);
        affirm_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_fb.getText().toString().trim())){
                    final ProgressDialog dialog = ProgressDialog.show(FBActivity.this, null, "正在提交。。。");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            ToastUtil.showToast(FBActivity.this,"提交成功");
                        }
                    },1000);
                }else {
                    ToastUtil.showToast(FBActivity.this,"请填写信息");
                }

            }
        });

        et_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_fb.setSelected(true);
                et_fb.setFocusable(true);
                et_fb.setFocusableInTouchMode(true);
                et_fb.requestFocus();
                InputMethodManager imm1 = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(et_fb,0);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getTitleText() {
        return "意见反馈";
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fb);
//    }
}
