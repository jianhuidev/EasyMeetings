package com.example.kys_8.easymeetings.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.ui.activity.MainActivity;
import com.example.kys_8.easymeetings.ui.activity.MeetingDetailsActivity;
import com.example.kys_8.easymeetings.ui.adapter.MemberAdapter;
import com.example.kys_8.easymeetings.ui.view.FlowLayout;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by kys-8 on 2018/4/17.
 */

public class MemberFragment extends BaseFragment {

    private RecyclerView rv_member;
    private MemberAdapter adapter;


    private MeetingsBean bean;
//    private TextView creator_member;
//    private ImageView avatar_member;

    private List<User> memberList = new ArrayList<>();
    private List<User> notSignMemberList = new ArrayList<>();
    private CardView card_member;
    private FlowLayout flowLayout;
    private LinearLayout refresh_layout;
    private ProgressBar refresh_progress;

    List<String> attendList,signInList,notSignInList;

//    private String[] history = {"李明","李好","李梦明","李好","李梦明","李好","李明","李梦明","李明","李好","李明","李好"};
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    protected void initView(View view) {
        card_member = (CardView)view.findViewById(R.id.card_member);
        card_member.setVisibility(View.GONE);
        refresh_layout = (LinearLayout)view.findViewById(R.id.refresh_layout);
        refresh_progress = (ProgressBar)view.findViewById(R.id.refresh_progress);
        refresh_progress.setVisibility(View.GONE);
        refresh_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean != null)
                    queryMeetingWithId(bean.getObjectId());
                else
                    ToastUtil.showToast(getContext(),"请检查网络");
            }
        });

        flowLayout = (FlowLayout)view.findViewById(R.id.flow_layout_member);

        rv_member = (RecyclerView)view.findViewById(R.id.rv_member);

//        creator_member = (TextView)view.findViewById(R.id.creator_member);
//        avatar_member = (ImageView)view.findViewById(R.id.avatar_member);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        rv_member.setLayoutManager(layoutManager);
        adapter = new MemberAdapter(getContext());
        rv_member.setAdapter(adapter);

        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.flow_up);
        LayoutAnimationController controller=new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(1.7f);
        rv_member.setLayoutAnimation(controller);

        initMember();
    }
    private void initMember(){
        attendList = new ArrayList<>();
        signInList = new ArrayList<>();
        notSignInList = new ArrayList<>();
        bean = ((MeetingDetailsActivity)getActivity()).mMeetingsBean;
        if (bean != null && bean.getAttendId() != null){
            LogUtil.e("MemberFragment",bean.getAttendId().size()+"");
            attendList = bean.getAttendId();
        }

//        //通过比较签到列表与总列表列表，生成未签到列表
//        if (bean != null && bean.getAttendCheckInId() != null){
//            LogUtil.e("MemberFragment",bean.getAttendCheckInId().size()+"");
//            signInList = bean.getAttendCheckInId();
//            notSignInList = getDiffList(attendList,signInList);
//        }
        refreshNotSignList();

        //查询创建者
        if (bean != null && bean.getCreatorId() != null){
            userQuery(bean.getCreatorId(),1);
        }

        //查询成员
        for (String id : attendList){
            userQuery(id,2);
            LogUtil.e("MemberFragment -> 1",memberList.size()+"");
        }

        //查询未签到
        queryNotSign();


        //延迟加载成员数据
        final ProgressDialog dialog = ProgressDialog.show(getContext(), null, "加载。。。");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                if (notSignMemberList.size() != 0 && TSVariable.fragmentMark == 2){
                    card_member.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.flow_down) ;
                    card_member.setAnimation(animation);
                    setFlowLayout();
                }
                LogUtil.e("MemberFragment -> 3",memberList.size()+"");
                adapter.setData(memberList);
            }
        },2000);

    }

    /**
     * 通过比较签到列表与总列表列表，生成未签到列表
     */
    private void refreshNotSignList(){
        if (bean != null && bean.getAttendCheckInId() != null){
            LogUtil.e("MemberFragment",bean.getAttendCheckInId().size()+"");
            signInList = bean.getAttendCheckInId();
            notSignInList = getDiffList(attendList,signInList);
        }
    }

    /**
     * 查询未签到
     */
    private void queryNotSign(){
        for (String id : notSignInList){
            userQuery(id,3);
            LogUtil.e("MemberFragment -> 1",memberList.size()+"");
        }
    }

    private void setFlowLayout(){
//        for (int i = 0; i < history.length; i++) {
//            if (!TextUtils.isEmpty(history[i]) )
//                addTextView(history[i]);
//        }
        for (User user:notSignMemberList){
            if (!TextUtils.isEmpty(user.getRealName()))
                addTextView(user.getRealName());
        }
    }

    /**
     * @param list1 大
     * @param list2 小
     */
    private List<String> getDiffList(List<String> list1,List<String> list2){
        List<String> diffList = new ArrayList<>();
        for (String str : list1){
            if (!list2.contains(str)){
                diffList.add(str);
            }
        }
        return diffList;
    }
    /**
     * 为flowGroupView 添加TextView
     * @param str TextView 的字符串
     */
    private void addTextView(String str) {
        TextView child = new TextView(getContext());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.text_bg);
        child.setText(str);
        child.setTextSize(14);
        child.setTextColor(Color.WHITE);
//        initEvents(child);//监听

        /**给flowGroupView 添加动画*/
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.flow_item_anim);
        //得到一个LayoutAnimationController对象
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.3f);
        flowLayout.setLayoutAnimation(controller);
        flowLayout.addView(child);
    }

//    /**
//     * 添加监听
//     * @param tv textView
//     */
//    private void initEvents(final TextView tv) {
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etQuery.setText(tv.getText().toString());
//            }
//        });
//    }


    /**
     * @param objId
     * @param mark  1 为创建者，2 为成员
     */
    private void userQuery(String objId, final int mark){
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    if (mark == 1){
                        memberList.add(0,user);
//                        creator_member.setText(user.getRealName());
//                        if (user.getAvatar() !=null && user.getAvatar().getUrl() != null){
//                            Glide.with(getContext()).load(user.getAvatar().getUrl())
//                                    .error(R.mipmap.avater_off)
//                                    .crossFade()
//                                    .into(avatar_member);
//                        }else {
//                            Glide.with(getContext()).load(R.mipmap.avater_off)
//                                    .error(R.mipmap.avater_off)
//                                    .crossFade()
//                                    .into(avatar_member);
//                        }
                    }else if (mark == 2){
                        memberList.add(user);
                        LogUtil.e("MemberFragment -> 2",memberList.size()+"");
                    }else {
                        notSignMemberList.add(user);
                    }
                }else {
                    LogUtil.e("MemberFragment",e.getMessage());
                }
            }
        });
    }
    public void queryMeetingWithId(String objId){
        refresh_progress.setVisibility(View.VISIBLE);
        BmobQuery<MeetingsBean> query = new BmobQuery<MeetingsBean>();

        query.getObject(objId, new QueryListener<MeetingsBean>() {
            @Override
            public void done(MeetingsBean meetingsBean, BmobException e) {
                if (e == null){
                    bean = meetingsBean;//刷新数据
                    notSignRefresh(meetingsBean);
                    ToastUtil.showToast(getContext(),"刷新成功");
                }else{
                    refresh_progress.setVisibility(View.GONE);
                    ToastUtil.showToast(getContext(),"刷新失败请检查网络");
                    LogUtil.e("MemberFragment",e.getMessage() + ","+e.getErrorCode());
                }
            }
        });
    }
    private void notSignRefresh(MeetingsBean data){
        attendList = data.getAttendId();
        signInList = data.getAttendCheckInId();

        notSignInList = getDiffList(attendList,signInList);
        //查询未签到
        queryNotSign();

        notSignMemberList.clear();//清空

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh_progress.setVisibility(View.GONE);
                flowLayout.removeAllViews();//清空
                setFlowLayout();
            }
        },2000);

    }

}
