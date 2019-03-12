package com.example.kys_8.easymeetings.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.ResBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.ItemTouchHelperCallback;
import com.example.kys_8.easymeetings.ui.activity.ImgInfoActivity;
import com.example.kys_8.easymeetings.ui.activity.LinkInfoActivity;
import com.example.kys_8.easymeetings.ui.activity.MainActivity;
import com.example.kys_8.easymeetings.ui.activity.MeetingDetailsActivity;
import com.example.kys_8.easymeetings.ui.adapter.ResAdapter;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kys-8 on 2018/5/2.
 */

public class ResFragment extends BaseFragment implements View.OnClickListener{
//    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRv;
    private ResAdapter mResAdapter;
    private LinearLayout img_layout,link_layout;
    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout album_res,take_res,cancel;

    private List<ResBean> resList = new ArrayList<>();
    /**
     * 从相册选择
     */
    private static final int ALBUM_RES = 36;
    /**
     * 拍照
     */
    private static final int CAMERA_RES = 37;

//    private static final int IMG_INFO_RES = 38;

    private MeetingsBean meetingsBean;
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_res;
    }

    @Override
    protected void initView(View view) {

        meetingsBean = ((MeetingDetailsActivity)getActivity()).mMeetingsBean;
//        mRefresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh_res);
        img_layout = (LinearLayout)view.findViewById(R.id.img_layout);
        link_layout = (LinearLayout)view.findViewById(R.id.link_layout);

        img_layout.setOnClickListener(this);
        link_layout.setOnClickListener(this);

        mRv = (RecyclerView)view.findViewById(R.id.rv_res);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRv.setLayoutManager(linearLayoutManager);

        mResAdapter = new ResAdapter(getContext());
        mRv.setAdapter(mResAdapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mResAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_layout:
                showBottomDialog();
                break;
            case R.id.link_layout:

                Intent i = new Intent(getContext(), LinkInfoActivity.class);
                i.putExtra("objId",meetingsBean.getObjectId());
                startActivity(i);

                break;
            case R.id.album_res:

                selectAalum();
                mBottomSheetDialog.cancel();
                break;
            case R.id.take_res:

                camera();
                mBottomSheetDialog.cancel();
                break;
            case R.id.cancel_res:

                mBottomSheetDialog.cancel();
                break;

        }
    }

    private void showBottomDialog(){
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_res, null);
        mBottomSheetDialog.setContentView(view);

        album_res = (LinearLayout)view.findViewById(R.id.album_res);
        take_res = (LinearLayout)view.findViewById(R.id.take_res);
        cancel = (LinearLayout)view.findViewById(R.id.cancel_res);
        album_res.setOnClickListener(this);
        take_res.setOnClickListener(this);
        cancel.setOnClickListener(this);

        mBottomSheetDialog.show();
    }

    private void selectAalum(){
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                .backResId(R.mipmap.back)
                .title("请选择一个图片")
                .titleColor(Color.WHITE)
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .allImagesText("所有图片")
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ISNav.getInstance().toListActivity(this, config, ALBUM_RES);
    }

    public void camera() {
        ISCameraConfig config = new ISCameraConfig.Builder()
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                .build();

        ISNav.getInstance().toCameraActivity(this, config, CAMERA_RES);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = "123";
        Intent intent = new Intent(getContext(), ImgInfoActivity.class);
        if (requestCode == ALBUM_RES && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                filePath = path;
            }

            intent.putExtra("res",filePath);
            intent.putExtra("objId",meetingsBean.getObjectId());
            startActivity(intent);

        } else if (requestCode == CAMERA_RES && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");

            filePath = path;

            intent.putExtra("res",filePath);
            intent.putExtra("objId",meetingsBean.getObjectId());
            startActivity(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        queryMeetingAttend();
    }

    public void queryMeetingAttend(){

        BmobQuery<ResBean> query = new BmobQuery<ResBean>();
        if (TSVariable.userInfo == null){
            return;
        }
        if (meetingsBean == null && meetingsBean.getObjectId()== null){
            return;
        }
        query.addWhereEqualTo("meetingId",meetingsBean.getObjectId());
        query.setLimit(50).order("-createdAt");
        query.findObjects(new FindListener<ResBean>() {
            @Override
            public void done(List<ResBean> list, BmobException e) {
                if (e == null){
                    mResAdapter.setData(list);
                }else {
                    LogUtil.e("ResFragment","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
//        query.findObjects(new FindListener<MeetingsBean>() {
//            @Override
//            public void done(List<MeetingsBean> list, BmobException e) {
//                mRefreshLayout.setRefreshing(false);
//                ((MainActivity)getActivity()).mProgressBar.setVisibility(View.INVISIBLE);
//                if (e == null){
////                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");
//                    meetingsAdapter.setData(list);
//                }else {
//                    ToastUtil.showToast(getContext(),"查询失败");
//                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//        });

    }

}
