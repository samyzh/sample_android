package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description：创建数据库类
 *                     数据类型：varchar int long float boolean text blob clob
 * @author samy
 * @date 2014年7月13日 下午2:52:07
 */
public class DBHelper extends SQLiteOpenHelper {
	// 数据库的名字
	private static final String DB_NAME = "BaseOperationDB.db";
	// 数据库的版本
	private static final int VERSION = 3;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 建表语句执行
		String sql = "create table person(pid integer primary key autoincrement,name varchar(64),address varchar(64),age integer)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "alter table person add age integer";
		db.execSQL(sql);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		System.out.println("--onOpen-->>");
	}
}
