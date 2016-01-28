package com.zsy.frame.sample.support.bean;

import java.io.Serializable;

import com.zsy.frame.sample.support.areainfo.AreaInfoDao;
import com.zsy.frame.sample.support.areainfo.AreaInfoDaoSession;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/** 
 * @description：Entity mapped to table AreaInfo.
 * @author zhoukl
 * @date 2014年12月19日 下午10:21:36
 */
public class AreaInfo implements Serializable {

	private static final long serialVersionUID = 9125292979046408517L;
	private int areaId;
    private int parentId;
    private String areaName;
    private String shortName;
    private int areaType;
//    private String zipCode;
//    private String initial;  		// 首字母
//    private String shortSpelling;	// 简拼
//    private String fullSpelling;	// 全拼


    
    /** Used to resolve relations */
    private transient AreaInfoDaoSession daoSession;

    /** Used for active entity operations. */
    private transient AreaInfoDao myDao;

    public AreaInfo() {
    }

    public AreaInfo(int areaId) {
        this.areaId = areaId;
    }

    public AreaInfo(int areaId, String areaName, int parentId, String shortName, int areaType) {
        this.areaId = areaId;
        this.parentId = parentId;
        this.areaName = areaName;
        this.shortName = shortName;
        this.setAreaType(areaType);
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(AreaInfoDaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAreaInfoDao() : null;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


      /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

}
