package com.zsy.frame.sample;

import android.database.sqlite.SQLiteDatabase;

import com.zsy.frame.lib.extend.BaseApp;
import com.zsy.frame.sample.support.areainfo.AreaInfoDaoMaster;

public class GlobalApp extends BaseApp {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * GreenDao数据库操作部分
	 * 官方推荐将取得DaoMaster对象的方法放到Application层这样避免多次创建生成Session对象。
	感觉这个框架和Web的Hibernate有异曲同工之妙。
	 */
	public static AreaInfoDaoMaster mAreaInfoDaoMaster;

	public static AreaInfoDaoMaster getAreaInfoDaoMaster() {
		if (mAreaInfoDaoMaster == null) {
			synchronized (GlobalApp.class) {
				if (mAreaInfoDaoMaster == null) {
					String path = AreaInfoDaoMaster.createOrUpdate(getInstance());
					SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
					mAreaInfoDaoMaster = new AreaInfoDaoMaster(db);
				}
			}
		}
		return mAreaInfoDaoMaster;
	}
}
