package com.example.kys_8.easymeetings.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.activity.MainActivity;
import com.example.kys_8.easymeetings.ui.activity.ScanActivity;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 * Created by kys-8 on 2018/4/11.
 */

public class FindFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout scan_code,input_code;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(View view) {

        LogUtil.e("Fragment","FindFragment");
        scan_code = (LinearLayout) view.findViewById(R.id.scan_code);
        input_code = (LinearLayout) view.findViewById(R.id.input_code);
        scan_code.setOnClickListener(this);
        input_code.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.scan_code:

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                    //没有权限去申请
                }else {
                    ((MainActivity)getActivity()).qrScan();
                }

//                getActivity().startActivityForResult(new Intent(getActivity(),ScanActivity.class),111);

                break;

            case R.id.input_code:


                ((MainActivity)getActivity()).inputDialog();
                break;

        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
