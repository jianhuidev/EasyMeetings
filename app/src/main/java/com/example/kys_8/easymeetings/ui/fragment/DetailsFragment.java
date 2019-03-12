package com.example.kys_8.easymeetings.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.ui.activity.MeetingDetailsActivity;
import com.example.kys_8.easymeetings.ui.activity.ModifyActivity;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kys-8 on 2018/4/17.
 */

public class DetailsFragment extends BaseFragment implements View.OnClickListener{//implements MeetingModifyCallback

    private TextView topic_details,site_details,start_time_details,over_time_details,creator_details,intro_details;

    private MeetingsBean meetingsBean;

    private ImageView img_details;

    private LinearLayout modify_details;
    private CardView card_details,card_modify;

    @Override
    protected int getlayoutId() {
        return R.layout.meeting_details;
    }

    @Override
    protected void initView(View view) {

//        refresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh_details);

        meetingsBean = ((MeetingDetailsActivity)getActivity()).mMeetingsBean;

        card_details = (CardView)view.findViewById(R.id.card_details);
        img_details = (ImageView)view.findViewById(R.id.img_details);
        topic_details = (TextView)view.findViewById(R.id.topic_details);
        site_details = (TextView)view.findViewById(R.id.site_details);
        start_time_details = (TextView)view.findViewById(R.id.start_time_details);
        over_time_details = (TextView)view.findViewById(R.id.over_time_details);
        creator_details = (TextView)view.findViewById(R.id.creator_details);
        intro_details = (TextView)view.findViewById(R.id.intro_details);

        card_modify = (CardView)view.findViewById(R.id.card_modify_details);

        modify_details = (LinearLayout)view.findViewById(R.id.modify_details);

        if (((MeetingDetailsActivity)getActivity()).mark!= 2){
            card_modify.setVisibility(View.INVISIBLE);
        }

        modify_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("modify",meetingsBean);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    startActivityForResult(intent,21, ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),card_details,"card_details").toBundle());

                }else {
                    startActivityForResult(intent,21);
                }

            }
        });

        initMeetingInfo(meetingsBean);

        ((MeetingDetailsActivity)getActivity()).mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.e("DetailsFragment","xxxxxxx000");
                if (meetingsBean != null && meetingsBean.getObjectId() != null){
                    queryMeetingWithId(meetingsBean.getObjectId());
                }
            }
        });
    }

    public void initMeetingInfo(MeetingsBean bean){
        if (bean != null){
            setImg();
            topic_details.setText(bean.getMeetingTopic());
            site_details.setText(bean.getSite());
            start_time_details.setText(bean.getStartTime());
            over_time_details.setText(bean.getEndTime());
            creator_details.setText(bean.getCreator());
            intro_details.setText(bean.getIntroduce());
        }
    }

    /**
     * 设置图片
     */
    private void setImg(){
        if (meetingsBean.getType() != null && meetingsBean.getType().equals("1")){
            img_details.setImageResource(R.mipmap.meeting_img1);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("2")){
            img_details.setImageResource(R.mipmap.meeting_img2);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("3")){
            img_details.setImageResource(R.mipmap.meeting_img3);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("4")){
            img_details.setImageResource(R.mipmap.meeting_img4);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("5")){
            img_details.setImageResource(R.mipmap.meeting_img5);
        }else if (meetingsBean.getType() != null && meetingsBean.getType().equals("100")){
            Glide.with(getActivity()).load(meetingsBean.getMeetingImg().getUrl())
                    .error(R.mipmap.meeting_off)
                    .crossFade()
                    .into(img_details);
        }else {
            img_details.setImageResource(R.mipmap.meeting_off);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 21:
                if (resultCode == RESULT_OK){
                    MeetingsBean m = (MeetingsBean) data.getSerializableExtra("dfragment");
                    if (m != null){
                        topic_details.setText(m.getMeetingTopic());
                        site_details.setText(m.getSite());
                        start_time_details.setText(m.getStartTime());
                        over_time_details.setText(m.getEndTime());
                        creator_details.setText(m.getCreator());
                        intro_details.setText(m.getIntroduce());
                        meetingsBean = m;//不包含头像
                    }

                }
                break;
        }


    }

    /**
     * 通过id 进行查询，此界面可用于刷新
     * @param objId
     */
    public void queryMeetingWithId(String objId){
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();
        query.getObject(objId, new QueryListener<MeetingsBean>() {
            @Override
            public void done(MeetingsBean meetingsBean, BmobException e) {
                ((MeetingDetailsActivity)getActivity()).mRefresh.setRefreshing(false);
                if (e == null){
                    initMeetingInfo(meetingsBean);
                }else{
                    LogUtil.e("DetailsFragment",e.getMessage() + ","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.modify_details:
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("modify",meetingsBean);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    startActivityForResult(intent,21, ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),card_details,"card_details").toBundle());

                }else {
                    startActivityForResult(intent,21);
                }
                break;
        }
    }
}
