package com.example.kys_8.easymeetings.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.utils.LogUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MemberInfoActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView user_name,company,qq,phone,sign;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        user = (User) getIntent().getSerializableExtra("user_info");
        initView();

    }

    private void initView(){
        avatar = (ImageView)findViewById(R.id.avatar_member_info);
        user_name = (TextView)findViewById(R.id.user_name_member);
        company = (TextView)findViewById(R.id.company_member_info);
        qq = (TextView)findViewById(R.id.qq_member_info);
        phone = (TextView)findViewById(R.id.phone_member_info);
        sign = (TextView)findViewById(R.id.sign_member_info);

        if (user != null){
            if (!TextUtils.isEmpty(user.getUsername()))
                user_name.setText(user.getUsername());
            if (!TextUtils.isEmpty(user.getCompany()))
                company.setText(user.getCompany());
            if (!TextUtils.isEmpty(user.getQq()))
                qq.setText(user.getQq());
            if (!TextUtils.isEmpty(user.getPhone()))
                phone.setText(user.getPhone());
            if (!TextUtils.isEmpty(user.getSign()))
                sign.setText(user.getSign());
        }

        if (user != null && user.getAvatar() != null){
            Glide.with(this).load(user.getAvatar().getUrl())
                    .error(R.mipmap.avatar_off)
                    .crossFade()
                    .into(avatar);
        }

    }


//    /**
//     * @param objId
//     */
//    private void userQuery(String objId){
//        BmobQuery<User> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(objId, new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null){
//
//                }else {
//                    LogUtil.e("MemberFragment",e.getMessage());
//                }
//            }
//        });
//    }

}
