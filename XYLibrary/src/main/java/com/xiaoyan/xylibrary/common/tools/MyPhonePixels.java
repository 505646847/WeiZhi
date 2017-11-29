package com.xiaoyan.xylibrary.common.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 获取手机像素
 *
 * @author xiejinxiong
 *
 */
public class MyPhonePixels {

	/** 屏幕宽度 */
	private int widthPixels;
	/** 屏幕高度 */
	private int heightPixels;

	/**
	 * 构造方法，获得宽高
	 *
	 * @param context
	 */
	public MyPhonePixels(Context context) {
		Activity activity = (Activity) context;
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

		heightPixels = metric.heightPixels;
		widthPixels = metric.widthPixels;
	}

	public int getWidthPixels() {
		return widthPixels;
	}

	public int getHeightPixels() {
		return heightPixels;
	}

}
