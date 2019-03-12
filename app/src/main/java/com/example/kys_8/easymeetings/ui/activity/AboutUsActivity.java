package com.example.kys_8.easymeetings.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kys_8.easymeetings.R;


public class AboutUsActivity extends BaseActivity {

    private TextView explain;

    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }


    @Override
    protected void initView() {

        explain = (TextView)findViewById(R.id.explain_about_us);

        explain.setText("本软件包含了开会能用到的基本功能，通过二维码或会议号查找会议创建会议、会议签到、查看会议资料与人员信息，" +
                "此外还有信息维护，维护包括会议基本信息、动态调整信息如场地指引等功能。对于动态信息的调整能够做多人审核确认，" +
                "并及时推送至受到该调整影响的参会人员手机上。查看相关人员对调整的阅读确认情况。参会人员通过 APP 参会人员可以随时随地" +
                "查看、自动接收到会议的相关信息和动态，包括由于突发事件调整的紧急通知。能够支持通知是否已阅、是否确认的功能。此外，我们还" +
                "设置了邮箱找回密码，既方便又快捷，为参会者、开会者提供了便利，不会因为一点小问题带来不必要的麻烦。");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getTitleText() {
        return "关于我们";
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about_us);
//    }
}
