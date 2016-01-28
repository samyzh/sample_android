package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

public class BaseCPTestCase extends AndroidTestCase {

	public void testInsert() throws Exception {

		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri url = Uri.parse("content://com.zsy.tc.PersonContentProvider/person");
		ContentValues values = new ContentValues();
		values.put("name", "jiahui2");
		values.put("age", 20);
		contentResolver.insert(url, values);
	}

	public void testDelete() throws Exception {
		ContentResolver contentResolver = this.getContext().getContentResolver();

		Uri url = Uri.parse("content://com.zsy.tc.PersonContentProvider/person/1");
		ContentValues values = new ContentValues();

		contentResolver.delete(url, null, null);
	}

	public void testUpdate() throws Exception {

		ContentResolver contentResolver = this.getContext().getContentResolver();

		Uri url = Uri.parse("content://com.zsy.tc.PersonContentProvider/person/1");
		ContentValues values = new ContentValues();
		values.put("name", "jiahui2");
		values.put("age", 20);
		contentResolver.update(url, values, null, null);

	}

	public void testQuery() throws Exception {
		ContentResolver contentResolver = this.getContext().getContentResolver();

		Uri url = Uri.parse("content://com.zsy.tc.PersonContentProvider/person");

		Cursor cursor = contentResolver.query(url, new String[] { "_id", "name", "age" }, null, null, "_id");
		while (cursor.moveToNext()) {
			System.out.println("_id:" + cursor.getInt(cursor.getColumnIndex("_id")));
			System.out.println("name:" + cursor.getString(cursor.getColumnIndex("name")));
			System.out.println("age:" + cursor.getInt(cursor.getColumnIndex("age")));

		}
	}

}
