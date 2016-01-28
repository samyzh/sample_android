package com.zsy.frame.sample.support.areainfo;

import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

import com.zsy.frame.sample.support.bean.AreaInfo;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class AreaInfoDaoSession extends AbstractDaoSession {

    private final DaoConfig areaDaoConfig;

    private final AreaInfoDao areaDao;

    public AreaInfoDaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        areaDaoConfig = daoConfigMap.get(AreaInfoDao.class).clone();
        areaDaoConfig.initIdentityScope(type);

        areaDao = new AreaInfoDao(areaDaoConfig, this);

        registerDao(AreaInfo.class, areaDao);
    }
    
    public void clear() {
        areaDaoConfig.getIdentityScope().clear();
    }

    public AreaInfoDao getAreaInfoDao() {
        return areaDao;
    }


}
