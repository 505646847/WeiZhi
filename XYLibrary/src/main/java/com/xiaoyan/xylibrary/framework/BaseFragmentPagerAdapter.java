package com.xiaoyan.xylibrary.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

  private ArrayList<Fragment> fragmentsList;

  public BaseFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public BaseFragmentPagerAdapter(FragmentManager fm,
      ArrayList<Fragment> fragments) {
    super(fm);
    this.fragmentsList = fragments;
  }


  @Override
  public int getCount() {
    return fragmentsList.size();
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentsList.get(position);
  }

  @Override
  public int getItemPosition(Object object) {
    return super.getItemPosition(object);
  }

  //Remove a page for the given position. The adapter is responsible for removing the view from its container
  //防止重新销毁视图
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    //如果注释这行，那么不管怎么切换，page都不会被销毁
//    if (position == 0) {
      super.destroyItem(container, position, object);

//    }
  }

}