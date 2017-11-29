package com.xiaoyan.xylibrary.common.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * 数据库的基础运用
 *
 *
 *
 * @author xiejinxiong
 *
 *
 *
 */
public class BaseDBManager {

	/** 用于管理数据的创建更新 */
	private DBHelper dbHelper;
	/** 管理数据库 */
	private SQLiteDatabase sqLiteDatabase;

	/**
	 *
	 * 获取管理数据库对象
	 *
	 *
	 *
	 * @return
	 *
	 */
	public SQLiteDatabase getSqLiteDatabase() {
		return sqLiteDatabase;
	}

	/**
	 *
	 * 初始化数据库运用
	 *
	 *
	 *
	 * @param context
	 *
	 */
	public BaseDBManager(Context context) {

		dbHelper = new DBHelper(context);
		sqLiteDatabase = dbHelper.getWritableDatabase();

	}

	/**
	 *
	 * 关闭数据库连接
	 *
	 */
	public void closeDB() {
		dbHelper.close();
		sqLiteDatabase.close();
	}
}