package com.zsy.frame.sample.support.helps;

import android.content.SharedPreferences;

import com.zsy.frame.sample.GlobalApp;

/**
 * @description：PreferHelper
 * @author samy
 * @date 2014年9月17日 下午6:30:24
 */
public class PreferHelper {
	public static final String NAME = "SYFrame_preference";
	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;
	private static PreferHelper mInstance;

	/**
	 * 字段区
	 */
	/** 登录 */
	public static final String KEY_LOGIN_PHONE = "key_login_phone";
	public static final String KEY_LOGIN_PWD = "key_login_pwd";

	/** 定位 */
	public static final String CURRENT_LATITUDE = "Latitude";
	public static final String CURRENT_LONGITUDE = "Longitude";
	public static final String ADDRESS_LOCATION = "address_location";
	/** 版本更新 */
	public static final String LAST_VERSION_CHECK_TIME = "lastVersionCheckTime";
	public static final String LAST_CHECK_VERSION = "lastCheckVersion";
	/**最近的搜索词*/
	public static final String LAST_SEARCH_KEYWORD = "lastSearchKeyword";
	/**商品分类更新时间*/
	public static final String PRODUCT_CATEGORY_UPDATETIME = "productCategoryUpdatetime";
	/**银行卡列表是否展开*/
	public static final String MY_BANKLIST_ISSHOWN = "myBankListShown";
	public static final String MY_BANKLIST_SHOWN_INDEX = "myBankListShownIndex";

	private PreferHelper() {
		sp = GlobalApp.getInstance().getSharedPreferences(NAME, 0);
		editor = sp.edit();
	}

	// 增加了双重判断
	public static PreferHelper getInstance() {
		if (null == mInstance) {
			synchronized (PreferHelper.class) {
				if (null == mInstance) {
					mInstance = new PreferHelper();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 储存值
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void setLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return sp.getString(key, "");
	}

	public int getInt(String key) {
		return sp.getInt(key, -1);
	}

	public long getLong(String key) {
		return sp.getLong(key, 1);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	/**
	 * @description：移除特定的
	 * @date 2014年11月5日 下午4:30:08
	 */
	public void remove(String name) {
		editor.remove(name);
		editor.commit();
	}

	/**
	 * @description：保存登录相关信息
	 * @author samy
	 * @date 2015-1-24 下午9:14:39
	 */
	public void saveLoginInfo(String phone, String pwd) {
		editor.putString(KEY_LOGIN_PHONE, phone);
		editor.putString(KEY_LOGIN_PWD, pwd);
		editor.commit();
	}
}
