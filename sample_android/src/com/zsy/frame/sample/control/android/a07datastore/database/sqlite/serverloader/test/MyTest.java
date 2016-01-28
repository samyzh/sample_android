package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.test;

import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.dao.PersonServiceImp;
import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.service.PersonService;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {

	public MyTest() {
	}

	public void insert() {
		PersonService service = new PersonServiceImp(getContext());
		ContentValues values = new ContentValues();
		values.put("name", "samy");
		values.put("address", "shenzhen");
		service.addPerson(values);
	}
}
