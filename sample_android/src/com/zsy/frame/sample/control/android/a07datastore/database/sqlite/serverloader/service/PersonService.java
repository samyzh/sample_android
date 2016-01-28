package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.service;

import android.content.ContentValues;
import android.database.Cursor;

public interface PersonService {
	boolean addPerson(ContentValues values);
	Cursor listPersonCursor();
}
