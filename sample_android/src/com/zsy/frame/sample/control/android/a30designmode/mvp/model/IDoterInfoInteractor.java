package com.zsy.frame.sample.control.android.a30designmode.mvp.model;

import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;

public interface IDoterInfoInteractor {
	void getDoter();

	/**
	 * @description：一下后面几个方法，为自己添加的几个方法，更加形象
	 * @author samy
	 * @date 2015-4-24 下午4:35:40
	 */
	
	void setID(int id);
	void setFirstName(String firstName);

	void setLastName(String lastName);

	DoterBean load(int id);
}
