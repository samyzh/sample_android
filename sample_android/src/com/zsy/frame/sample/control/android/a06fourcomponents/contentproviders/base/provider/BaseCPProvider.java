package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.db.DBHelper;

/**
 * @description：内容提供者；
 * 1、什么Content Provider 内容提供者
  	     底层封装的是数据库的操作，操作数据，A应用中将本地的数据库的数据，分享给B的应用，必须要使用内容提供者。
  	    内容提供者就是提供数据的数据源，给客户端访问。
   2、内容提供者提供了一套对外的接口访问规则：
       query查询
       insert插入
       update修改
       delete删除
       
       
      注意事项：
      Installation error: INSTALL_FAILED_CONFLICTING_PROVIDER
      这主要是由于调试的环境中已有一个同名的Provider存在。
          <provider
            android:name="com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.provider.BaseCPProvider"
            android:authorities="com.zsy.tc.PersonContentProvider" >
        </provider>
 * @author samy
 * @date 2014年7月13日 下午8:52:00
 */
public class BaseCPProvider extends ContentProvider {
	/** 使用内容解析者访问 */
	private ContentResolver contentResolver;
	private DBHelper dbHelper;
	private static final int PERSONS = 1;// 操作多条记录
	private static final int PERSON = 2;// 操作单行记录

	public final static String AUTH = "com.zsy.tc.PersonContentProvider";
	public final static Uri AUTH_URI = Uri.parse("content://" + AUTH);
	public final static Uri AUTH_URI_PERSON = Uri.parse("content://" + AUTH + "/person");

	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		URI_MATCHER.addURI(AUTH, "person", PERSONS);
		URI_MATCHER.addURI(AUTH, "person/#", PERSON);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(this.getContext());
		contentResolver = getContext().getContentResolver();
		return false;
	}

	/**
	 * 返回当前操作的数据的mimeType
	 */
	@Override
	public String getType(Uri uri) {
		// 解析uri，判断mime的类型
		switch (URI_MATCHER.match(uri)) {
			case PERSONS:
				return "vnd.android.cursor.dir/persons";
			case PERSON:
				return "vnd.android.cursor.item/person";
			default:
				throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	/**
	 * 插入数据
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// ContentUris.parseId(result) > 0 表示插入成功；
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri insertUri = null;
		int flag = URI_MATCHER.match(uri);
		switch (flag) {
			case PERSONS:
				long rowid = db.insert("person", null, values);
				// long rowid = db.insert("person", "name", values);
				insertUri = ContentUris.withAppendedId(uri, rowid);
				break;
			default:
				throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		// 通知数据更新
		contentResolver.notifyChange(uri, null);
		return insertUri;
	}

	/**
	 * 删除数据
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// count>=1表示操作成功；影响数据库的行数
		int count = 0;
		int flag = URI_MATCHER.match(uri);
		switch (flag) {
			case PERSONS:
				count = db.delete("person", selection, selectionArgs);
				break;
			case PERSON:
				long id = ContentUris.parseId(uri);
				String where = "_id=" + id;
				if (selection != null && !"".equals(selection)) {
					where = selection + " and " + where;
					// where += selection;
				}
				count = db.delete("person", where, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		contentResolver.notifyChange(uri, null);
		return count;
	}

	/**
	 * 更新数据
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// count>=1表示操作成功；
		int count = 0;
		switch (URI_MATCHER.match(uri)) {
			case PERSONS:
				count = db.update("person", values, selection, selectionArgs);
				break;
			case PERSON:
				// 通过ContentUri工具类得到ID
				long id = ContentUris.parseId(uri);
				String where = "_id=" + id;
				if (selection != null && !"".equals(selection)) {
					where = selection + " and " + where;
					// where += selection;
				}
				count = db.update("person", values, where, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		contentResolver.notifyChange(uri, null);
		return count;
	}

	/**
	 * 查询数据
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int flag = URI_MATCHER.match(uri);
		Cursor cursor = null;
		switch (flag) {
			case PERSONS:
				cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
				break;
			case PERSON:
				// 查询某个ID的数据
				// 通过ContentUris这个工具类解释出ID
				long id = ContentUris.parseId(uri);
				String where = " _id=" + id;
				if (!"".equals(selection) && selection != null) {
					where = selection + " and " + where;
					// where_value += selection;
				}
				cursor = db.query("person", projection, where, selectionArgs, null, null, sortOrder);
				break;
			default:
				throw new IllegalArgumentException("unknow uri" + uri.toString());
		}
		cursor.setNotificationUri(contentResolver, uri);
		return cursor;
	}

	// @Override
	// public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
	// Cursor cursor = null;
	// int i = uriMatcher.match(uri);
	// DB db = new DB(this.getContext());
	// SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
	// switch (i) {
	// case ALL:
	// // 把详细的查询条件那出去了；而不是设置为null;
	// // cursor = sqLiteDatabase.query("stu", projection, null, null, null, null, sortOrder);
	// cursor = sqLiteDatabase.query("stu", projection, selection, selectionArgs, null, null, sortOrder);
	// break;
	//
	// case ITEM:
	// // 拿多个参数uri.getPathSegments().get(2);
	// // String.valueOf(ContentUris.parseId(uri))这个方法；只能获得参数为一个的;
	// cursor = sqLiteDatabase.query("stu", projection, "id=?", new String[] { String.valueOf(ContentUris.parseId(uri)) }, null, null, sortOrder);
	// break;
	//
	// default:
	// break;
	// }
	// // 这里不能关闭事务；因返回去是cursor;
	//
	// if (cursor != null) {
	// // 通知数据接收者，告诉游标准备完毕，可以立即处理
	// cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
	// }
	//
	// return cursor;
	// }

}
