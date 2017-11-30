package com.cn.xp.weizhi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.xp.weizhi.base.MyBaseActivity;
import com.cn.xp.weizhi.ui.login.main.FourFragment;
import com.cn.xp.weizhi.ui.login.main.OneFragment;
import com.cn.xp.weizhi.ui.login.main.ThreeFragment;
import com.cn.xp.weizhi.ui.login.main.TwoFragment;
import com.xiaoyan.xylibrary.framework.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends FragmentActivity implements OnClickListener {

    //    @BindView(R.id.vp_viewpager)
//    ViewPager vpViewpager;
//    @BindView(R.id.ll_one)
//    LinearLayout llOne;
//    @BindView(R.id.ll_two)
//    LinearLayout llTwo;
//    @BindView(R.id.ll_three)
//    LinearLayout llThree;
//    @BindView(R.id.ll_four)
//    LinearLayout llFour;
//    @BindView(R.id.iv_one)
//    ImageView ivOne;
//    @BindView(R.id.iv_two)
//    ImageView ivTwo;
//    @BindView(R.id.iv_three)
//    ImageView ivThree;
//    @BindView(R.id.iv_four)
//    ImageView ivFour;
//    @BindView(R.id.tv_one)
//    TextView tvOne;
//    @BindView(R.id.tv_two)
//    TextView tvTwo;
//    @BindView(R.id.tv_three)
//    TextView tvThree;
//    @BindView(R.id.tv_four)
//    TextView tvFour;

    private ImageView ivCurrent;
    private  TextView tvCurrent;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;

    private LinearLayout llOne,llTwo,llThree,llFour;
    private ImageView ivOne,ivTwo,ivThree,ivFour;
    private TextView tvOne,tvTwo,tvThree, tvFour;
    private ViewPager vpViewpager;

    private int[] Tab_select = {R.drawable.a36, R.drawable.a39, R.drawable.a41, R.drawable.a43};
    private int[] Tab_normal = {R.drawable.a37, R.drawable.a38, R.drawable.a40, R.drawable.a42};
    private int[] Tab_textColor = {R.color.colorblue, R.color.colorgray,};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();
    }
    private void initView() {
        llOne = (LinearLayout) findViewById(R.id.ll_one);
        llTwo = (LinearLayout) findViewById(R.id.ll_two);
        llThree = (LinearLayout) findViewById(R.id.ll_three);
        llFour = (LinearLayout) findViewById(R.id.ll_four);

        ivOne = (ImageView) findViewById(R.id.iv_one);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        ivThree = (ImageView) findViewById(R.id.iv_three);
        ivFour = (ImageView) findViewById(R.id.iv_four);

        vpViewpager = (ViewPager) findViewById(R.id.vp_viewpager);

        llOne.setOnClickListener(this);
        llTwo.setOnClickListener(this);
        llThree.setOnClickListener(this);
        llFour.setOnClickListener(this);

        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        tvThree = (TextView) findViewById(R.id.tv_three);
        tvFour = (TextView) findViewById(R.id.tv_four);

        ivThree.setSelected(true);
        tvThree.setSelected(true);
        ivCurrent = ivThree;
        tvCurrent = tvThree;

        //默认值
        int select = getResources().getColor(Tab_textColor[0]);
        tvOne.setTextColor(select);

        ivOne.setImageResource(Tab_select[0]);
        ivTwo.setImageResource(Tab_normal[1]);
        ivThree.setImageResource(Tab_normal[2]);
        ivFour.setImageResource(Tab_normal[3]);

        vpViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == 2){
                    switch (vpViewpager.getCurrentItem()){
                        case 0:
                            changeTab(0);
                            break;
                        case 1:
                            changeTab(1);
                            break;
                        case 2:
                            changeTab(2);
                            break;
                        case 3:
                            changeTab(3);
                            break;
                    }
                }
            }
        });
        vpViewpager.setOffscreenPageLimit(2); //设置向左和向右都缓存limit个页面
    }

    private void initData() {
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();

        fragments.add(oneFragment);
        fragments.add(twoFragment);
        fragments.add(threeFragment);
        fragments.add(fourFragment);

        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
//      MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getFragmentManager(), fragments);
        vpViewpager.setAdapter(adapter);
    }


    private void changeTab(int id) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.ll_one:
                vpViewpager.setCurrentItem(0);
                setResourcestyle(0);
            case 0:
                ivOne.setSelected(true);
                ivCurrent = ivOne;
                tvOne.setSelected(true);
                tvCurrent = tvOne;
                setResourcestyle(0);
                break;
            case R.id.ll_two:
                vpViewpager.setCurrentItem(1);
                setResourcestyle(1);
            case 1:
                ivTwo.setSelected(true);
                ivCurrent = ivTwo;
                tvTwo.setSelected(true);
                tvCurrent = tvTwo;
                setResourcestyle(1);
                break;
            case R.id.ll_three:
                vpViewpager.setCurrentItem(2);
                setResourcestyle(2);
            case 2:
                ivThree.setSelected(true);
                ivCurrent = ivThree;
                tvThree.setSelected(true);
                tvCurrent = tvThree;
                setResourcestyle(2);
                break;
            case R.id.ll_four:
                vpViewpager.setCurrentItem(3);
                setResourcestyle(3);
            case 3:
                ivFour.setSelected(true);
                ivCurrent = ivFour;
                tvFour.setSelected(true);
                tvCurrent = tvFour;
                setResourcestyle(3);
                break;
            default:
                break;
        }
    }

    private void setResourcestyle(int i) {
        int select = getResources().getColor(Tab_textColor[0]);
        int normal = getResources().getColor(Tab_textColor[1]);

        ivOne.setImageResource(Tab_normal[0]);
        ivTwo.setImageResource(Tab_normal[1]);
        ivThree.setImageResource(Tab_normal[2]);
        ivFour.setImageResource(Tab_normal[3]);

        tvOne.setTextColor(normal);
        tvTwo.setTextColor(normal);
        tvThree.setTextColor(normal);
        tvFour.setTextColor(normal);

        switch (i){
            case 0:
                ivOne.setImageResource(Tab_select[0]);
                tvOne.setTextColor(select);
                break;
            case 1:
                ivTwo.setImageResource(Tab_select[1]);
                tvTwo.setTextColor(select);
                break;
            case 2:
                ivThree.setImageResource(Tab_select[2]);
                tvThree.setTextColor(select);
                break;
            case 3:
                ivFour.setImageResource(Tab_select[3]);
                tvFour.setTextColor(select);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }
}
