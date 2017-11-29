package com.cn.xp.weizhi;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.xp.weizhi.base.MyBaseActivity;

import butterknife.BindView;

public class MainActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    @BindView(R.id.ll_two)
    LinearLayout llTwo;
    @BindView(R.id.ll_three)
    LinearLayout llThree;
    @BindView(R.id.ll_four)
    LinearLayout llFour;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;

    private ImageView ivCurrent;
    private  TextView tvCurrent;


    @Override
    protected View layoutView() {
        return inflateLayout(R.layout.activity_main);
    }

    @Override
    protected void InitView() {
        llOne.setOnClickListener(this);
        llTwo.setOnClickListener(this);
        llThree.setOnClickListener(this);
        llFour.setOnClickListener(this);

        ivThree.setSelected(true);
        tvThree.setSelected(true);
        ivCurrent = ivThree;
        tvCurrent = tvThree;

        vpViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            changeFlag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置向左向右都缓存2个页面
        vpViewpager.setOffscreenPageLimit(2);

        initData();
    }

    private void changeFlag(int position) {
        switch (position){
            case R.id.ll_one:
                vpViewpager.setCurrentItem(0);
                break;
            case 0:
                ivOne.setSelected(true);
                ivCurrent = ivOne;
                tvOne.setSelected(true);
                tvCurrent = tvOne;
                break;
            case R.id.ll_two:
                vpViewpager.setCurrentItem(1);
                break;
            case 1:
                ivTwo.setSelected(true);
                ivCurrent = ivTwo;
                tvTwo.setSelected(true);
                tvCurrent = tvTwo;
                break;
            case R.id.ll_three:
                vpViewpager.setCurrentItem(2);
                break;
            case 2:
                ivThree.setSelected(true);
                ivCurrent = ivThree;
                tvThree.setSelected(true);
                tvCurrent = tvThree;
                break;
            case R.id.ll_four:
                vpViewpager.setCurrentItem(3);
                break;
            case 3:
                ivFour.setSelected(true);
                ivCurrent = ivFour;
                tvFour.setSelected(true);
                tvCurrent = tvFour;
                break;
            default:
                break;
        }
    }

    private void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        changeFlag(v.getId());
    }
}
