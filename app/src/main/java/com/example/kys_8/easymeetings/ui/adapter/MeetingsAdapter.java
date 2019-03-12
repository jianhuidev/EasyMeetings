package com.example.kys_8.easymeetings.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.activity.MeetingDetailsActivity;
import com.example.kys_8.easymeetings.ui.onMoveAndSwipedListener;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by kys-8 on 2018/4/17.
 */

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.ViewHolder> implements onMoveAndSwipedListener {

    private final String TAG = "MeetingsAdapter";
    private View parentView;

    private Context mContext;

    private List<MeetingsBean> list;

    private int mark;
    public MeetingsAdapter(Context mContext,int mark) {
        this.mContext = mContext;
        this.mark = mark;
    }

    public void setData(List<MeetingsBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(int position, MeetingsBean insertData) {
        list.add(position, insertData);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,list.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_attend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.view.startAnimation(animation);
        AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
        aa1.setDuration(400);
        holder.meeting_img.startAnimation(aa1);
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(400);
//
//
//
        if (list != null){
            holder.meeting_name.setText(list.get(position).getMeetingTopic());
            holder.meeting_site.setText(list.get(position).getSite());
            holder.meeting_time.setText(list.get(position).getStartTime());
            holder.meeting_create.setText(list.get(position).getCreator());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MeetingDetailsActivity.class);
                    intent.putExtra("meeting",list.get(position));
                    intent.putExtra("mark",mark);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mContext.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) mContext,holder.meeting_img,"details").toBundle());
                    } else {
                        mContext.startActivity(intent);
                    }

                }
            });

            if (list.get(position).getType() != null && list.get(position).getType().equals("1")){
                holder.meeting_img.setImageResource(R.mipmap.meeting_img1);
            }else if (list.get(position).getType() != null && list.get(position).getType().equals("2")){
                holder.meeting_img.setImageResource(R.mipmap.meeting_img2);
            }else if (list.get(position).getType() != null && list.get(position).getType().equals("3")){
                holder.meeting_img.setImageResource(R.mipmap.meeting_img3);
            }else if (list.get(position).getType() != null && list.get(position).getType().equals("4")){
                holder.meeting_img.setImageResource(R.mipmap.meeting_img4);
            }else if (list.get(position).getType() != null && list.get(position).getType().equals("5")){
                holder.meeting_img.setImageResource(R.mipmap.meeting_img5);
            }else if (list.get(position).getType() != null && list.get(position).getType().equals("100")){
                Glide.with(mContext).load(list.get(position).getMeetingImg().getUrl())
                        .error(R.mipmap.meeting_off)
                        .crossFade()
                        .into(holder.meeting_img);
            }else {
                holder.meeting_img.setImageResource(R.mipmap.meeting_off);
            }
        }else {
            LogUtil.e(TAG,"   空");
        }
        holder.meeting_img.startAnimation(aa);

    }

    @Override
    public int getItemCount() {
        return list != null?list.size():0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
        return true;
    }

    @Override
    public void onItemRemove(final int position) {

        final MeetingsBean current = list.get(position);

        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());

        new AlertDialog.Builder(mContext).setMessage("你确定要删除该会议吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (mark == 1)
                            cancelAttend(current);
                        else
                            affirmDelData(current.getObjectId());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        addItem(position,current);

                    }
                }).show();

    }

    /**
     * 取消对次会议的参加,但别人可参加
     */
    public void cancelAttend(MeetingsBean bean){
        MeetingsBean meetingsBean = new MeetingsBean();
        meetingsBean.setObjectId(bean.getObjectId());
//        List<String> ids = new ArrayList<>();
//        ids.add(bean.getObjectId());
        String[] ids = {TSVariable.userInfo.getObjectId()};
        meetingsBean.removeAll("attendId", Arrays.asList(ids));

        meetingsBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if(e==null){
                    ToastUtil.showToast(mContext,"已成功取消对改会议的参加");
                    LogUtil.e(TAG," 成功");
                }else{
                    LogUtil.e(TAG,"  失败："+e.getMessage());
                }

            }
        });

    }


    /**
     * 将数据库的删除了，是我的会议的删除
     * @param objId
     */
    public void affirmDelData(String objId){
        MeetingsBean meetingData = new MeetingsBean();
        meetingData.setObjectId(objId);
        meetingData.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.showToast(mContext,"已删除该数据");

                }else{

                    ToastUtil.showToast(mContext,"删除失败，请检查网络");
                }
            }
        });

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView meeting_name,meeting_site,meeting_create,meeting_time;

        ImageView meeting_img;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            meeting_img = (ImageView)itemView.findViewById(R.id.meeting_img);
            meeting_name = (TextView) itemView.findViewById(R.id.meeting_name);
            meeting_site = (TextView) itemView.findViewById(R.id.meeting_site);
            meeting_time = (TextView) itemView.findViewById(R.id.meeting_time);
            meeting_create = (TextView) itemView.findViewById(R.id.meeting_create);
        }
    }

}
