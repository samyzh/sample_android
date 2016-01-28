package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.db.DBManager;
import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.service.PersonService;

public class PersonServiceImp implements PersonService {
	private DBManager manager;

	public PersonServiceImp(Context context) {
		manager = new DBManager(context);
	}
	
	@Override
	public boolean addPerson(ContentValues values) {
		boolean flag = false;
		flag = manager.insert("person", null, values);
		return flag;
	}

	@Override
	public Cursor listPersonCursor() {
		return manager.queryByCursor("person", null, null, null, null, null, null);
	}


}
