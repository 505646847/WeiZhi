package com.xiaoyan.xylibrary.common.tools;

import android.util.Log;

/**
 * 本类作用：日志输出（可改用log4j等）
 */
public class DataOutput {

	/**
	 * 控制台及面板信息输出:1为数据信息，2为异常信息
	 * 
	 * @param info
	 * @param flag
	 */
	public static void print(String info, int flag) {
		try {
			if (flag == 1)// 输出信息
			{
				System.out.println(info);
			}
			if (flag == 2)// 输出异常信息
			{
				System.err.println(info);
			}
		} catch (Exception e) {
			System.out.println("disData.Outinfo:" + e.getMessage());
			System.err.println(e.getMessage());

		}
	}

	/**
	 * 使用Log.e的方式输出到logcat中
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void logE(String tag, String msg) {
		Log.e(tag, msg);
	}

}
