package com.xiaoyan.xylibrary.common.widget.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiaoyan.xylibrary.common.widget.adapter.viewholder.ViewHolder;

import java.util.List;

/**
 * 对RecyclerView.Adapter进行封装
 * Created by Jinxiong.Xie on 2017/4/18.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public RecyclerAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
//        holder.updatePosition(position);
        convert(holder, mDatas.get(position), position);
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}