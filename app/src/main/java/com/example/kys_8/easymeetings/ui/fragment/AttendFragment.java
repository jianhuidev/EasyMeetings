package com.example.kys_8.easymeetings.ui.fragment;

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

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by kys-8 on 2018/4/11.
 */

public class AttendFragment extends BaseFragment{

    private String TAG = "AttendFragment";

    private RecyclerView mRecyclerView;

    public MeetingsAdapter meetingsAdapter;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_attend;
    }

    @Override
    protected void initView(View view) {
        LogUtil.e("Fragment","AttendFragment");
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_attend);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_attend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        meetingsAdapter = new MeetingsAdapter(getContext(),1);
        mRecyclerView.setAdapter(meetingsAdapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(meetingsAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_yellow, R.color.google_red, R.color.google_green);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryMeetingAttend();
            }
        });
        queryMeetingAttend();
    }

//    /**
//     * 将该用户参加的会议查询出来
//     * 查询条件：当前账号的用户名，
//     */
//    public void queryMeetingAttend(){
//        ((MainActivity)getActivity()).mProgressBar.setVisibility(View.VISIBLE);
//        BmobQuery<AttendBean> query = new BmobQuery<AttendBean>();
//        query.addWhereEqualTo("username",TSVariable.userInfo.getUsername());
//
//
//    }
//    public void queryMeetingOther(){
//        ((MainActivity)getActivity()).mProgressBar.setVisibility(View.VISIBLE);
//        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
//        query.addWhereNotEqualTo("username",TSVariable.userInfo.getUsername());
//        query.addWhereEqualTo("company",TSVariable.userInfo.getCompany());
//        query.setLimit(50).order("-createdAt");
//        query.findObjects(new FindListener<MeetingsBean>() {
//            @Override
//            public void done(List<MeetingsBean> list, BmobException e) {
//                mRefreshLayout.setRefreshing(false);
//                ((MainActivity)getActivity()).mProgressBar.setVisibility(View.INVISIBLE);
//                if (e == null){
//                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");
//                    meetingsAdapter.setData(list);
//                }else {
//                    ToastUtil.showToast(getContext(),"查询失败");
//                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//        });
//
//    }

    public void queryMeetingAttend(){
        ((MainActivity)getActivity()).mProgressBar.setVisibility(View.VISIBLE);
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
        if (TSVariable.userInfo == null){
            return;
        }
        String[] attendIds = {TSVariable.userInfo.getObjectId()};
        query.addWhereContainsAll("attendId", Arrays.asList(attendIds));
        query.findObjects(new FindListener<MeetingsBean>() {
            @Override
            public void done(List<MeetingsBean> list, BmobException e) {
                mRefreshLayout.setRefreshing(false);
                ((MainActivity)getActivity()).mProgressBar.setVisibility(View.INVISIBLE);
                if (e == null){
//                    ToastUtil.showToast(getContext(),"查询成功：共" + list.size() + "条数据。");
                    meetingsAdapter.setData(list);
                }else {
                    ToastUtil.showToast(getContext(),"查询失败");
                    LogUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }


}
