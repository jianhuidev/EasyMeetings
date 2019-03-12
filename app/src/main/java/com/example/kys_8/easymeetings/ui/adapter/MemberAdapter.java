package com.example.kys_8.easymeetings.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.User;
import com.example.kys_8.easymeetings.ui.activity.MDLoginActivity;
import com.example.kys_8.easymeetings.ui.activity.MeetingDetailsActivity;
import com.example.kys_8.easymeetings.ui.activity.MemberInfoActivity;
import com.example.kys_8.easymeetings.utils.LogUtil;

import java.util.List;

/**
 * Created by kys-8 on 2018/4/25.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    private Context mContext;

    private List<User> memberList;

    public MemberAdapter(Context context) {
        this.mContext = context;
    }



    public void setData(List<User> list){
        this.memberList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (memberList != null){
            if (memberList.get(position).getAvatar() !=null && memberList.get(position).getAvatar().getUrl() != null){
                Glide.with(mContext).load(memberList.get(position).getAvatar().getUrl())
                        .error(R.mipmap.avatar_off)
                        .crossFade()
                        .into(holder.avatar_member);
            }else {
                Glide.with(mContext).load(R.mipmap.avatar_off)
                        .error(R.mipmap.avatar_off)
                        .crossFade()
                        .into(holder.avatar_member);
            }
            holder.name_member.setText(memberList.get(position).getRealName());
        }else {
            LogUtil.e("MemberAdapter","   空");
        }

        holder.item_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MemberInfoActivity.class);
                intent.putExtra("user_info",memberList.get(position));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Pair<CardView, String> cardPair = Pair.create(holder.item_member,"card_member" );
                    Pair<ImageView, String> imgPair = Pair.create(holder.avatar_member,"avatar_info" );
                    Pair<AppBarLayout,String> appBarLayoutPair = Pair.create(((MeetingDetailsActivity)mContext).appbar_details,"appbar_info");
                    Pair[] pairs = {cardPair,imgPair,appBarLayoutPair};
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) mContext,pairs);
                    mContext.startActivity(intent,compat.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        return memberList != null?memberList.size():0;
        return memberList !=null ? memberList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar_member;
        TextView name_member;
        CardView item_member;

        public ViewHolder(View itemView) {
            super(itemView);
            item_member = (CardView)itemView.findViewById(R.id.item_member);
            avatar_member = (ImageView)itemView.findViewById(R.id.avater_member);
            name_member = (TextView) itemView.findViewById(R.id.name_member);

        }
    }

//    private String[] getUserImgURL(String objId){
//        final String[] url = {"",""};
//        userQuery(objId, new UserQueryInterface() {
//            @Override
//            public void success(User user) {
//                url[0] = user.getRealName();
////                url[1] = user.getAvatar().getUrl();
//                LogUtil.e("MemberAdapter",url[0]);
//            }
//
//            @Override
//            public void err(BmobException e) {
//                LogUtil.e("MemberAdapter",e.getMessage());
//
//            }
//        });
//        return url;
//    }





}
