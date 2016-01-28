package com.zsy.frame.sample.control.android.a30designmode.mvp.view;

public interface ILoadDataView {
	void showLoading();

	void showContent();

	void showError();

	void showNonet();

	void showEmpty();

	/**
	 * @description：新添加的方法
	 * @author samy
	 * @date 2015-4-24 下午5:41:01
	 */
	int getID();

	String getFristName();

	String getLastName();

	void setFirstName(String firstName);

	void setLastName(String lastName);

}
