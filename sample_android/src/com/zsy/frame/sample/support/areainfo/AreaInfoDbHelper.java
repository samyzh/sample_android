package com.zsy.frame.sample.support.areainfo;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zsy.frame.sample.GlobalApp;
import com.zsy.frame.sample.support.areainfo.AreaInfoDao.Properties;
import com.zsy.frame.sample.support.bean.AreaInfo;

/**
 * @description：数据库查询
 *  查询 -- Queries
    1）你可以使用原生的SQl（raw sql）语句；
    2）也可以使用推荐的方法：使用greenDAO提供的QueryBuilder的API。
    3）查询还支持结果延迟加载（lazy-loading），主要为操作较大查询结果是节约内存并提高性能。
 * @author samy
 * @date 2015年6月15日 下午6:34:35
 */
public class AreaInfoDbHelper {
	private AreaInfoDaoSession mAreaInfoDaoSession;
	private AreaInfoDao mAreaInfoDao;
	private SQLiteDatabase db;
	private Context mContext;

	public AreaInfoDbHelper() {
		AreaInfoDaoMaster daoMaster = GlobalApp.getAreaInfoDaoMaster();
		mAreaInfoDaoSession = daoMaster.newSession();
		mAreaInfoDao = mAreaInfoDaoSession.getAreaInfoDao();
		db = daoMaster.getDatabase();
	}

	public AreaInfoDbHelper(Context mContext) {
		this();
		this.mContext = mContext;
	}

	/**
	 * @description：2）也可以使用推荐的方法：使用greenDAO提供的QueryBuilder的API。
	 * @author samy
	 * @date 2015年6月15日 下午6:38:55
	 */
	public long getTotalCount() {
		return mAreaInfoDao.queryBuilder().count();
	}

	public List<AreaInfo> getProvinceList() {
		return mAreaInfoDao.queryBuilder().where(Properties.ParentID.eq(1)).orderAsc(Properties.AreaID).list();
	}

	public List<AreaInfo> getAreaListByParentID(int parentID) {
		return mAreaInfoDao.queryBuilder().where(Properties.ParentID.eq(parentID)).orderAsc(Properties.AreaID).list();
	}

}
