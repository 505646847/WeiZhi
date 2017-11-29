package com.xiaoyan.xylibrary.common.widget.adapter.listadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoyan.xylibrary.common.widget.adapter.viewholder.MyViewHolder;

import java.util.List;


/**
 * 对于BaseAdapter进行进一步的封装
 *
 * @author xiejinxiong
 * @param <T>
 *
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> list;
	protected int layoutId;

	public MyBaseAdapter(Context context, List<T> list, int layoutId) {
		this.context = context;
		this.list = list;
		this.layoutId = layoutId;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		MyViewHolder viewHolder = MyViewHolder.getViewHolder(context, convertView,
				parent, layoutId);

		conver(viewHolder, getItem(position), position);

		return viewHolder.getConverView();
	}

	public abstract void conver(MyViewHolder viewHolder, Object object,
								int position);

}
