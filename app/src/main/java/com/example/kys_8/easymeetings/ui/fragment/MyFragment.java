package com.example.kys_8.easymeetings.ui.fragment;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.ItemTouchHelperCallback;
import com.example.kys_8.easymeetings.ui.activity.MainActivity;
import com.example.kys_8.easymeetings.ui.adapter.MeetingsAdapter;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by kys-8 on 2018/4/11.
 */

public class MyFragment extends BaseFragment {

    private String TAG = "MyFragment";

    private RecyclerView mRecyclerView;

    public MeetingsAdapter meetingsAdapter;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {

        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_my);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_my);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        meetingsAdapter = new MeetingsAdapter(getContext(),2);
        mRecyclerView.setAdapter(meetingsAdapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(meetingsAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_yellow, R.color.google_red, R.color.google_green);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                queryMeetingMine();
            }
        });
        queryMeetingMine();
    }

    public void queryMeetingMine(){
        ((MainActivity)getActivity()).mProgressBar.setVisibility(View.VISIBLE);
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
        if (TSVariable.userInfo == null && TSVariable.userInfo.getObjectId() == null){
            return;
        }
        query.addWhereEqualTo("creatorId",TSVariable.userInfo.getObjectId());

        query.setLimit(50).order("-createdAt");
        query.findObjects(new FindListener<MeetingsBean>() {
            @Override
            public void done(List<MeetingsBean> list, BmobException e) {
                mRefreshLayout.setRefreshing(false);
                ((MainActivity)getActivity()).mProgressBar.setVisibility(View.INVISIBLE);
                if (e == null){
//                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");

                    meetingsAdapter.setData(list);
                    
                }else {
                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        queryMineNoProgress();
    }

//    public void queryMineNoProgress(){
//
//        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
//        if (TSVariable.userInfo == null && TSVariable.userInfo.getObjectId() == null){
//            return;
//        }
//        query.addWhereEqualTo("creatorId",TSVariable.userInfo.getObjectId());
//
//        query.setLimit(50).order("-createdAt");
//        query.findObjects(new FindListener<MeetingsBean>() {
//            @Override
//            public void done(List<MeetingsBean> list, BmobException e) {
//
//                if (e == null){
//                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");
//
//                    meetingsAdapter.setData(list);
//
//                }else {
//                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//        });
//    }


    //    public void query() {
//        ((MainActivity)getActivity()).mProgressBar.setVisibility(View.VISIBLE);
////        final ProgressDialog dialog2 = ProgressDialog.show(getContext(),
////                null, "正在查询我穿件的会议，请稍等 ");
//        BmobQuery<UploadBean> query = new BmobQuery<UploadBean>();
//        Log.e("userquery","李老师");
//        query.addWhereEqualTo("creater","李老师");
//
//        query.setLimit(50).order("-createdAt");
//        query.findObjects(new FindListener<UploadBean>() {
//            @Override
//            public void done(List<UploadBean> list, BmobException e) {
////                dialog2.cancel();
//                mRefreshLayout.setRefreshing(false);
//                ((MainActivity)getActivity()).mProgressBar.setVisibility(View.INVISIBLE);
//                if(e==null){
//                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");
//
//                    meetingsAdapter.setData(list);
//
//                }else{
//                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//        });
//
//    }
}
