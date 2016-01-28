package com.zsy.frame.sample.support.areainfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zsy.frame.lib.SYLoger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1000): knows all DAOs.
*/
public class AreaInfoDaoMaster extends AbstractDaoMaster {
	public static final int SCHEMA_VERSION = 1000;

	/**
	 * 拿去本地数据库.db;
	 */
	public static final String DB_NAME = "areaInfo.db";

	// KEEP METHODS - put your custom methods here
	public static String createOrUpdate(Context mCtx) {
		File dbFile = mCtx.getDatabasePath(DB_NAME);
		if (!dbFile.exists()) {
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				File dir = dbFile.getParentFile();
				dir.mkdirs();

				InputStream is = mCtx.getAssets().open(DB_NAME);
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(new FileOutputStream(dbFile));
				byte[] buf = new byte[2048];
				int len = 0;
				while ((len = bis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				bos.flush();
			}
			catch (IOException e) {
				SYLoger.print(e.toString());
				e.printStackTrace();
			}
			finally {
				if (bos != null) {
					try {
						bos.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (bis != null) {
					try {
						bis.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return dbFile.getAbsolutePath();
	}

	// KEEP METHODS END

	public AreaInfoDaoMaster(SQLiteDatabase db) {
		super(db, SCHEMA_VERSION);
		registerDaoClass(AreaInfoDao.class);
	}

	public AreaInfoDaoSession newSession() {
		return new AreaInfoDaoSession(db, IdentityScopeType.None, daoConfigMap);
	}

	public AreaInfoDaoSession newSession(IdentityScopeType type) {
		return new AreaInfoDaoSession(db, type, daoConfigMap);
	}

}
