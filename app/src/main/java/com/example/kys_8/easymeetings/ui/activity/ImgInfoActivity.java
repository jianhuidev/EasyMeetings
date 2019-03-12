package com.example.kys_8.easymeetings.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.ResBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ImgInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView image;

    RelativeLayout affirm;
    String filePath;
    String objId;

    private EditText name,type;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_img_info);
//
//        String filePath = getIntent().getStringExtra("res");
//
//        image = (ImageView)findViewById(R.id.image_img_info);
//        affirm = (RelativeLayout)findViewById(R.id.affirm_img_info)
//        if (filePath != null){
//            Glide.with(this).load(new File(filePath))
//                    .error(R.mipmap.nav_header1)
//                    .crossFade()
//                    .into(image);
//        }
//
//    }

    @Override
    protected int getContentView() {
        return R.layout.activity_img_info;
    }

    @Override
    protected void initView() {
        filePath = getIntent().getStringExtra("res");

        objId = getIntent().getStringExtra("objId");

        image = (ImageView)findViewById(R.id.image_img_info);
        affirm = (RelativeLayout)findViewById(R.id.affirm_img_info);

        name = (EditText)findViewById(R.id.name_img_info);
        type = (EditText)findViewById(R.id.type_img_info);

        if (filePath != null){
            Glide.with(this).load(new File(filePath))
                    .error(R.mipmap.nav_header1)
                    .crossFade()
                    .into(image);
        }
    }

    @Override
    protected void initListener() {
        image.setOnClickListener(this);
        affirm.setOnClickListener(this);
    }

    @Override
    protected String getTitleText() {
        return "资源上传";
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.affirm_img_info:
                uploadRes();
                break;
        }
    }

    private void uploadRes(){

        if (TextUtils.isEmpty(name.getText().toString().trim())
                ||TextUtils.isEmpty(type.getText().toString().trim())){
            ToastUtil.showToast(ImgInfoActivity.this,"请完善数据");
            return;
        }

        final BmobFile bmobFile = new BmobFile(new File(filePath));
        final ProgressDialog dialog = ProgressDialog.show(this, null, "上传资源中。。。");
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ResBean resBean = new ResBean();
                    resBean.setName(name.getText().toString().trim());
                    resBean.setType(type.getText().toString().trim());
                    resBean.setMeetingId(objId);
                    resBean.setResImg(bmobFile);
                    resBean.setMark("img1");
                    resBean.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            dialog.cancel();
                            if (e == null){
                                ToastUtil.showToast(ImgInfoActivity.this,"上传资源成功");
                                finish();
                            }else {
                                ToastUtil.showToast(ImgInfoActivity.this,"上传资源失败，请检查网络");
                            }
                        }
                    });
                }else {
                    ToastUtil.showToast(ImgInfoActivity.this,"上传资源失败，请检查网络");
                    dialog.cancel();
                }
            }
        });
    }

}
