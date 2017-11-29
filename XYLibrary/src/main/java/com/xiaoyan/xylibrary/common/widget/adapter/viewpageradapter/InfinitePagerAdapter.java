package com.xiaoyan.xylibrary.common.widget.adapter.viewpageradapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 无限滑动ViewPager的适配器
 *
 * @author xiejinxiong
 *
 */
public class InfinitePagerAdapter extends PagerAdapter {

	private List<View> lists;

	public InfinitePagerAdapter(List<View> lists) {
		this.lists = lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// System.out.println("(ViewPager) container).getChildCount():"
		// + ((ViewPager) container).getChildCount());
		if (((ViewPager) container).getChildCount() == lists.size()) {
			((ViewPager) container).removeView(lists.get(position
					% lists.size()));
		}

		((ViewPager) container).addView(lists.get(position % lists.size()), 0);
		return lists.get(position % lists.size());
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(container);

	}
}
