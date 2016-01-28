package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.base.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @description：数据库操作管理类,增删改查操作
 * @author samy
 * @date 2014年7月13日 下午2:59:28
 */
public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase database;

	public DBManager(Context context) {
		helper = new DBHelper(context);
	}

	/**
	 * @description：获得数据库的链接
	 * @author samy
	 * @date 2014年7月13日 下午2:57:01
	 */
	public void getDataBaseConn() {
		database = helper.getWritableDatabase();
	}

	/**
	 * @description：关闭数据库连接
	 * @author samy
	 * @date 2014年7月13日 下午2:57:08
	 */
	public void releaseConn() {
		if (database != null) {
			database.close();
		}
	}

	/**
	 * @description：通过外部写SQL语句类操作数据库，实现对数据库的添加、删除和修改功能
	 * @author samy
	 * @date 2014年7月13日 下午3:28:13
	 */
	public boolean operateBySQL(String sql, Object[] bindArgs) {
		boolean flag = false;
		try {
			database.execSQL(sql, bindArgs);
			flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @description：增加；insert into tableName(a,b,c) values(?,?,?);
	 * @author samy
	 * @date 2014年7月13日 下午3:13:02
	 */
	public boolean insert(String table, String nullColumnHack, ContentValues values) {
		boolean flag = false;
		long id = database.insert(table, nullColumnHack, values);
		flag = (id > 0 ? true : false);
		return flag;
	}

	/**
	 * @description：删除；delete from tableName where pid = ?
	 * @author samy
	 * @date 2014年7月13日 下午3:16:24
	 */
	public boolean delete(String table, String whereClause, String[] whereArgs) {
		boolean flag = false;
		int count = database.delete(table, whereClause, whereArgs);
		flag = (count > 0 ? true : false);
		return flag;
	}

	/**
	 * @description：更新；update tableName set name = ? ,address = ?, age = ? where pid = ?
	 * @author samy
	 * @date 2014年7月13日 下午3:17:16
	 */
	public boolean update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		boolean flag = false;
		int count = database.update(table, values, whereClause, whereArgs);
		flag = (count > 0 ? true : false);
		return flag;
	}

	/**
	 * @description：查询，sql标准写法:select [distinct][columnName]，... from tableName [where][selection][selectionArgs] [groupBy][having][order by][limit];
	 * @author samy
	 * @date 2014年7月13日 下午3:20:54
	 */
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		Cursor cursor = null;
		cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		return cursor;
	}

	/**
	 * @description：查询,返回的是为游标，一般用到courseAdapter中；
	 * @author samy
	 * @date 2014年7月13日 下午4:19:06
	 */
	public Cursor queryMultiCursor(String sql, String[] selectionArgs) {
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		return cursor;
	}

	/**
	 * ============================================================================
	 */

	/**
	 * @description：通过SQL语句全部查询
	 * @author samy
	 * @date 2014年7月13日 下午4:15:13
	 */
	public Map<String, String> queryBySQL(String sql, String[] selectionArgs) {
		Map<String, String> map = new HashMap<String, String>();
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		int cols_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			for (int i = 0; i < cols_len; i++) {
				String cols_name = cursor.getColumnName(i);
				String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	/**
	 * @description：查询多个，返回为List<Map>
	 * @author samy
	 * @date 2014年7月13日 下午4:16:32
	 */
	public List<Map<String, String>> queryMultiMaps(String sql, String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		int cols_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = cursor.getColumnName(i);
				String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * @description：查询单个class数据，通过反射获得数据库的查询记录； 声明Class 的属性必须都是String类型
	 * @author samy
	 * @date 2014年7月13日 下午4:16:02
	 */
	public <T> T querySingleCursor(String sql, String[] selectionArgs, Class<T> cls) {
		T t = null;
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		int cols_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			try {
				t = cls.newInstance();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_value == null) {
						cols_value = "";
					}
					Field field = cls.getDeclaredField(cols_name);
					field.setAccessible(true);
					field.set(t, cols_value);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * @description：查询多个，也用的是反射；返回为List<T>
	 * @author samy
	 * @date 2014年7月13日 下午4:17:59
	 */
	public <T> List<T> queryMultiCursor(String sql, String[] selectionArgs, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		int cols_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			try {
				T t = cls.newInstance();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_value == null) {
						cols_value = "";
					}
					Field field = cls.getDeclaredField(cols_name);
					field.setAccessible(true);
					field.set(t, cols_value);
					list.add(t);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
