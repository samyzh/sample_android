package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.base;

import java.util.List;
import java.util.Map;

import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.base.db.DBHelper;
import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.base.db.DBManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

/**
 * @description：测试用例类
 * @author samy
 * @date 2014年7月13日 下午2:54:12
 */
public class BaseOperationDBTestCase extends AndroidTestCase {

	public BaseOperationDBTestCase() {
	}

	public void initTable() {
		DBHelper dbManager = new DBHelper(getContext());
		dbManager.getReadableDatabase();
		// dbManager.getWritableDatabase();
	}

	/**
	 * @description：直接自己写SQL语句
	 * @author samy
	 * @date 2015-3-9 下午9:38:28
	 */
	public void insert() {
		String sql = "insert into person(name,address,age) values(?,?,?)";
		Object[] bindArgs = { "张三峰", "北京", 34 };
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		manager.operateBySQL(sql, bindArgs);
		manager.releaseConn();
	}

	/**
	 * @description：调用系统自动的Insert方法
	 * @author samy
	 * @date 2015-3-9 下午9:38:49
	 */
	public void insert2() {
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		ContentValues values = new ContentValues();
		// values.put("name", "XX");
		values.put("address", "XXXX");
		values.put("age", 44);
		manager.insert("person", null, values);
		manager.releaseConn();
	}

	public void delete() {
		String sql = "delete  from  person  where pid = ? ";
		Object[] bindArgs = { 1 };
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		manager.operateBySQL(sql, bindArgs);
		manager.releaseConn();
	}

	public void delete2() {
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		manager.delete("person", " pid = ? ", new String[] { "5" });
		manager.releaseConn();
	}

	public void update() {
		String sql = "update  person set name = ?,address = ?,age = ? where pid = ? ";
		Object[] bindArgs = { "张三丰", "河北", 120, 4 };
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		manager.operateBySQL(sql, bindArgs);
		manager.releaseConn();
	}

	public void update2() {
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		ContentValues values = new ContentValues();
		values.put("name", "FFF");
		values.put("address", "FFFFF");
		values.put("age", 44);
		manager.update("person", values, " pid = ?  ", new String[] { "2" });
		manager.releaseConn();
	}

	public void query() {
		String sql = "select *   from  person  ";
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		Map<String, String> map = manager.queryBySQL(sql, null);
		System.out.println("--name->>" + map.get("name"));
		System.out.println("--address->>" + map.get("address"));
		System.out.println("--age->>" + map.get("age"));
		manager.releaseConn();
	}

	public void query2() {
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		Cursor cursor = manager.query("person", null, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			System.out.println("---->>" + cursor.getString(cursor.getColumnIndex("name")));
		}
		manager.releaseConn();
	}

	/**
	 * ========================================================================================
	 */

	public void query3() {
		String sql = "select *   from  person where pid = ?  ";
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		Map<String, String> map = manager.queryBySQL(sql, new String[] { "2" });
		System.out.println("--name->>" + map.get("name"));
		System.out.println("--address->>" + map.get("address"));
		System.out.println("--age->>" + map.get("age"));
		manager.releaseConn();
	}

	/**
	 * @description：模糊查询
	 * @author samy
	 * @date 2015-3-9 下午9:43:04
	 */
	public void query4() {
		String sql = "select *   from  person where name like ?  ";
		DBManager manager = new DBManager(getContext());
		manager.getDataBaseConn();
		List<Map<String, String>> list = manager.queryMultiMaps(sql, new String[] { "%张%" });
		for (Map<String, String> map2 : list) {
			System.out.println("--name->>" + map2.get("name"));
			System.out.println("--address->>" + map2.get("address"));
			System.out.println("--age->>" + map2.get("age"));
		}
		manager.releaseConn();
	}
}
