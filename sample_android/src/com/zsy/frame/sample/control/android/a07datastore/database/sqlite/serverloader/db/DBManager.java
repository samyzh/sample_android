package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private DBHelper helper;

	private SQLiteDatabase database;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		database = helper.getWritableDatabase();
	}

	/**
	 * @description：增
	 * @author samy
	 * @date 2014年7月13日 下午5:43:30
	 */
	public boolean insert(String tableName, String nullColumnHack, ContentValues values) {
		boolean flag = false;
		long count = database.insert(tableName, nullColumnHack, values);
		flag = count > 0 ? true : false;
		return flag;
	}

	/**
	 * @description：查
	 * @author samy
	 * @date 2014年7月13日 下午5:43:37
	 */
	public Cursor queryByCursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = null;
		cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}

	/**
	 * 关闭数据库操作
	 */
	public void closeConn() {
		if (database != null) {
			database.close();
		}
	}
}
