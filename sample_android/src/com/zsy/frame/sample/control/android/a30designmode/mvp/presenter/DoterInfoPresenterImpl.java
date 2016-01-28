package com.zsy.frame.sample.control.android.a30designmode.mvp.presenter;

import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;
import com.zsy.frame.sample.control.android.a30designmode.mvp.model.DoterInfoInteractorImpl;
import com.zsy.frame.sample.control.android.a30designmode.mvp.model.IDoterInfoInteractor;
import com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.impl.IDoterInfoCallback;
import com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.impl.IDoterInfoPresenter;
import com.zsy.frame.sample.control.android.a30designmode.mvp.view.IDoterInfoView;

/**
 * @description：presenter
 * 处理view 和 model之间的数据通信；
 * 给UI主界面显示调用
 * @author samy
 * @date 2015-4-24 下午4:17:07
 */
public class DoterInfoPresenterImpl implements IDoterInfoPresenter, IDoterInfoCallback {
	// public class DoterInfoPresenterImpl implements IDoterInfoPresenter {
	private IDoterInfoView view;
	private IDoterInfoInteractor interactor;

	public DoterInfoPresenterImpl(IDoterInfoView view) {
		this.view = view;
		// model下处理Presenter的回调
		interactor = new DoterInfoInteractorImpl(this);
	}

	@Override
	public void loadData() {
		view.showLoading();
		interactor.getDoter();
	}

	@Override
	public void onResponse(DoterBean info) {
		view.showContent();
		view.showDoterInfo(info);
	}

	@Override
	public void onError() {
		view.showError();
	}

	@Override
	public void onNonet() {
		view.showNonet();
	}

	@Override
	public void onEmpty() {
		view.showEmpty();
	}
	
	
	/**
	 * @description：新添加方面逻辑
	 * @author samy
	 * @date 2015-4-24 下午5:38:45
	 */
	public void saveUser(int id, String firstName, String lastName) {
		interactor.setID(id);
		interactor.setFirstName(firstName);
		interactor.setLastName(lastName);
	}

	public void loadUser(int id) {
		DoterBean user = interactor.load(id);
		interactor.setFirstName(user.getFirstName());
		interactor.setLastName(user.getLastName());
	}

}
