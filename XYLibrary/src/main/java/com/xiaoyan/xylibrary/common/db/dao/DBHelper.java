package com.xiaoyan.xylibrary.common.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * 数据库的简易管理类（创建数据库，更新数据库）
 *
 *
 *
 * @author xiejinxiong
 *
 *
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	private final String TBNOTEXISTS = "CREATE TABLE IF NOT EXISTS ";

	/**
	 *
	 * 创建数据库
	 *
	 *
	 *
	 * @param context
	 *
	 */
	public DBHelper(Context context) {
		// CursorFactory设置为null,使用默认值

		// super(context, APPConfig.DBNAME, null, APPConfig.DBVERSION);
		super(context, "APPConfig.DBNAME", null, 1);
	}

	/**
	 *
	 * 第一次创建数据库的时候调用，用于创建表
	 *
	 *
	 *
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表

		// 建立事件表

		db.execSQL(TBNOTEXISTS + "tv_event" + " (" + "id" + " VARCHAR," + "content" + " VARCHAR," + "createTime"
				+ " VARCHAR," + "month" + " VARCHAR," + "path" + " VARCHAR," + "phone" + " VARCHAR," + "color"
				+ " VARCHAR," + "place" + " VARCHAR," + "title" + " VARCHAR," + "updateTime" + " VARCHAR," + "userName"
				+ " VARCHAR," + "day" + " VARCHAR)");
		// // 建立留言表

		// db.execSQL(TBNOTEXISTS + MessageColumns.TBNAME + " ("

		// + MessageColumns.MESSAGEID

		// + " INTEGER PRIMARY KEY AUTOINCREMENT," + MessageColumns.USERID

		// + " INTEGER," + MessageColumns.MESSAGECONTENT + " TEXT,"

		// + MessageColumns.MESSAGETIME + " VARCHAR,"

		// + MessageColumns.MESSAGETITLE + " VARCHAR,"

		// + MessageColumns.MESSAGETYPE + " INTEGER,"

		// + MessageColumns.ISANONYMOUS + " INTEGER,"

		// + MessageColumns.ISDELETE + " INTEGER" + ")");

		// // 建立评论表

		// db.execSQL(TBNOTEXISTS + CommentsColumns.TBNAME + " ("

		// + CommentsColumns.COMMENTSID

		// + " INTEGER PRIMARY KEY AUTOINCREMENT,"

		// + CommentsColumns.MESSAGEID + " INTEGER,"

		// + CommentsColumns.USERID + " INTEGER,"

		// + CommentsColumns.COMMENTSTIME + " VARCHAR,"

		// + CommentsColumns.COMMENTSCONTENT + " TEXT,"

		// + CommentsColumns.ISANONYMOUS + " INTEGER,"

		// + CommentsColumns.ISDELETE + " INTEGER" + ")");

		// // 建立手机本地记事本表

		// db.execSQL(TBNOTEXISTS + NotesLocalColumns.TBNAME + " ("

		// + NotesLocalColumns.USERID + " VARCHAR ,"

		// + NotesLocalColumns.CREATETIME + " VARCHAR ,"

		// + NotesLocalColumns.CONTENT + " TEXT,"

		// + NotesLocalColumns.UPDATETIME + " VARCHAR,"

		// + NotesLocalColumns.LABELVALUE + " VARCHAR,"

		// + NotesLocalColumns.ISDELETE + " INTEGER,"

		// + NotesLocalColumns.CLOCKTIME + " VARCHAR" + ")");

		// // 建立赞表

		// db.execSQL(TBNOTEXISTS + PraiseColumns.TBNAME + " ("

		// + PraiseColumns.USERID + " INTEGER ,"

		// + PraiseColumns.MESSAGEID + " INTEGER,"

		// + PraiseColumns.ISDELETE + " INTEGER" + ")");

	}

	/**
	 *
	 * 用于数据库的更新操作
	 *
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("更新数据库成功");
	}
}