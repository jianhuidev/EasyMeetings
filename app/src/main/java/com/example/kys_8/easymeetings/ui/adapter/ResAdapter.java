package com.example.kys_8.easymeetings.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.bean.MeetingsBean;
import com.example.kys_8.easymeetings.bean.ResBean;
import com.example.kys_8.easymeetings.bean.TSVariable;
import com.example.kys_8.easymeetings.ui.activity.WebActivity;
import com.example.kys_8.easymeetings.ui.onMoveAndSwipedListener;
import com.example.kys_8.easymeetings.ui.view.ShowImagesDialog;
import com.example.kys_8.easymeetings.utils.LogUtil;
import com.example.kys_8.easymeetings.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by kys-8 on 2018/5/2.
 */

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> implements onMoveAndSwipedListener {


    private Context mContext;

    private List<ResBean> resList;

    private List<String> urls = new ArrayList<>();

    public ResAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ResBean> list){
        this.resList = list;
        notifyDataSetChanged();
    }

    public void addItem(int position, ResBean insertData) {
        resList.add(position, insertData);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,resList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_res,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (resList!=null && resList.get(position)!=null){
            holder.name_res.setText(resList.get(position).getName());
            holder.time_res.setText(resList.get(position).getCreatedAt());
            holder.type_res.setText(resList.get(position).getType());
            if (resList.get(position).getMark()!=null && resList.get(position).getMark().equals("img1")){
                if (resList.get(position).getResImg()!=null&&resList.get(position).getResImg().getUrl() != null){
                    Glide.with(mContext).load(resList.get(position).getResImg().getUrl())
                            .error(R.mipmap.nofind)
                            .crossFade()
                            .into(holder.img_res);

                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogUtil.e("ResAdapter"," - >img1");
                            urls.clear();//清空
                            urls.add(resList.get(position).getResImg().getUrl());
                            new ShowImagesDialog(mContext,urls).show();

                        }
                    });

                }else {
                    holder.img_res.setImageResource(R.mipmap.nofind);
                }

            }else {
                holder.img_res.setImageResource(R.mipmap.img_link);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtil.e("ResAdapter"," - >link000001");
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra("url_web",resList.get(position).getLink());
                        mContext.startActivity(intent);
                    }
                });
            }
        }else {
            LogUtil.e("ResAdapter","  空");
        }
    }

    @Override
    public int getItemCount() {
        return resList!=null?resList.size():0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(resList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
        return true;
//        return false;
    }

    @Override
    public void onItemRemove(final int position) {

        final ResBean current = resList.get(position);

        resList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,resList.size());

//        new AlertDialog.Builder(mContext).setMessage("你确定要删除该会议吗？")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        if (TSVariable.fragmentMark == 1){
//                            addItem(position,current);  //参会者不能删除资源
//                        }else {
//                            affirmDelData(current.getObjectId());
//                        }
//
//
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        addItem(position,current);
//
//                    }
//                }).show();

        if (TSVariable.fragmentMark == 1){
            DialogAttend(position,current);
        }else {
            DialogMy(position,current);
        }

    }

    /**
     * 将数据库的删除了，是我的会议的删除
     * @param objId
     */
    public void affirmDelData(String objId){
        ResBean bean = new ResBean();
        bean.setObjectId(objId);
        bean.delete(new UpdateListener() {
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
        TextView name_res,type_res,time_res;

        ImageView img_res;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img_res = (ImageView)itemView.findViewById(R.id.img_res);
            name_res = (TextView) itemView.findViewById(R.id.name_res);
            type_res = (TextView) itemView.findViewById(R.id.type_res);
            time_res = (TextView) itemView.findViewById(R.id.time_res);
        }
    }

    private void DialogAttend(final int position, final ResBean current){
        new AlertDialog.Builder(mContext).setMessage("只有工作人员才可删除该资源")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        addItem(position,current);

                    }
                }).show();
    }

    private void DialogMy(final int position, final ResBean current){
        new AlertDialog.Builder(mContext).setMessage("你确定要删除该资源吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        if (TSVariable.fragmentMark == 1){
//                            addItem(position,current);  //参会者不能删除资源
//                        }else {
//                            affirmDelData(current.getObjectId());
//                        }

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

}
