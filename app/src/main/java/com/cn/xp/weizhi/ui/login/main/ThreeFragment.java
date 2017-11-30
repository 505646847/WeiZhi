package com.cn.xp.weizhi.ui.login.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.xp.weizhi.R;
import com.cn.xp.weizhi.adapter.CityAdapter;
import com.cn.xp.weizhi.bean.CityBean;
import com.cn.xp.weizhi.decoration.DividerItemDecoration;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;


public class ThreeFragment extends Fragment{

    private static final String TAG = "zxt";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    private ImageView ivNewly;
    private LinearLayout llNewly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_threefragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private boolean current = true;

    private void initListener() {
        ivNewly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current) {
                    current = false;
                    llNewly.setVisibility(View.VISIBLE);
                }else {
                    current = true;
                    llNewly.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initView() {
        mRv = (RecyclerView) getActivity().findViewById(R.id.rv);
        llNewly = (LinearLayout) getActivity().findViewById(R.id.ll_Newly);
        ivNewly = (ImageView) getActivity().findViewById(R.id.iv_Newly);

        Context conext = getContext();
        mRv.setLayoutManager(mManager = new LinearLayoutManager(conext));

        mAdapter = new CityAdapter(conext, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(conext, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(conext, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) getActivity().findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) getActivity().findViewById(R.id.indexBar);//IndexBar

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                // 但是它不需要被ItemDecoration设一个标题titile
                mDatas.add((CityBean) new CityBean("新邀请").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                mDatas.add((CityBean) new CityBean("群聊管理").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                mDatas.add((CityBean) new CityBean("我的工作群").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                mDatas.add((CityBean) new CityBean("服务号").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                for (int i = 0; i < data.length; i++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mDatas.add(cityBean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
            }
        }, 500);
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
        for (int i = 0; i < 5; i++) {
            mDatas.add(new CityBean("东京"));
            mDatas.add(new CityBean("大阪"));
        }

        mIndexBar.setmSourceDatas(mDatas)
                .invalidate();
        mAdapter.notifyDataSetChanged();
    }

}
