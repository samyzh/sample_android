package com.zsy.frame.sample.control.android.a30designmode.mvp.model;

import java.util.Random;

import android.util.SparseArray;

import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;
import com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.impl.IDoterInfoCallback;

/**
 * @description：这里的Model方法一般会对应到View里面的方法（Model和View中的方法一般是对应的）
 * @author samy
 * @date 2015-4-24 下午5:06:00
 */
public class DoterInfoInteractorImpl implements IDoterInfoInteractor {
	private IDoterInfoCallback callback;

	private String mFristName;
	private String mLastName;
	private int mID;

	private SparseArray<DoterBean> mUsererArray = new SparseArray<DoterBean>();

	public DoterInfoInteractorImpl(IDoterInfoCallback callback) {
		this.callback = callback;
	}

	@Override
	public void getDoter() {
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		int nextInt = new Random().nextInt(4);

		switch (nextInt) {
			case 0:
				callback.onResponse(new DoterBean("samy", "good"));
				break;
			case 1:
				callback.onEmpty();
				break;
			case 2:
				callback.onError();
				break;
			case 3:
				callback.onNonet();
				break;
		}
	}

	@Override
	public void setID(int id) {
		mID = id;
	}

	@Override
	public void setFirstName(String firstName) {
		mFristName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		mLastName = lastName;
		DoterBean UserBean = new DoterBean(mFristName, mLastName);
		mUsererArray.append(mID, UserBean);
	}

	@Override
	public DoterBean load(int id) {
		mID = id;
		DoterBean userBean = mUsererArray.get(mID, new DoterBean("not found", "not found"));
		return userBean;
	}

}
