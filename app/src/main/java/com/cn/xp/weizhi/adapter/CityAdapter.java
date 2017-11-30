package com.cn.xp.weizhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.xp.weizhi.R;
import com.cn.xp.weizhi.bean.CityBean;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    protected Context mContext;
    protected List<CityBean> mDatas;
    protected LayoutInflater mInflater;

    private int[] images_setting = {R.drawable.g01,R.drawable.g02,R.drawable.g03,R.drawable.g04};

    public CityAdapter(Context mContext, List<CityBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<CityBean> getDatas() {
        return mDatas;
    }

    public CityAdapter setDatas(List<CityBean> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder holder, final int position) {
        final CityBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getCity());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        if (position<4){
            switch (position){
                case 0:
                    holder.avatar.setImageResource(images_setting[0]);
                    break;
                case 1:
                    holder.avatar.setImageResource(images_setting[1]);
                    break;
                case 2:
                    holder.avatar.setImageResource(images_setting[2]);
                    break;
                case 3:
                    holder.avatar.setImageResource(images_setting[3]);
                    break;
            }
        }else {
            holder.avatar.setImageResource(R.drawable.friend);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        ImageView avatar;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            content = itemView.findViewById(R.id.content);
        }
    }
}
