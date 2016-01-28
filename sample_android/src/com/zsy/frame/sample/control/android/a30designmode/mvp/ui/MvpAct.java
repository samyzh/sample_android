package com.zsy.frame.sample.control.android.a30designmode.mvp.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;
import com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.DoterInfoPresenterImpl;
import com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.impl.IDoterInfoPresenter;
import com.zsy.frame.sample.control.android.a30designmode.mvp.view.IDoterInfoView;

@EActivity
public class MvpAct extends Activity implements IDoterInfoView, OnClickListener {
	private ViewContainer vc;
	private IDoterInfoPresenter presenter;

	@ViewById(R.id.tv_info)
	TextView tv_info;

	private EditText mFirstNameEditText, mLastNameEditText, mIdEditText;
	private Button mSaveButton, mLoadButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViewContainer();
	}

	private void initViewContainer() {
		vc = new ViewContainer(this, R.layout.activity_a30designmode_mvp_ui_mvp);
		vc.setDefaultClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickButton();
			}
		});
		vc.build(this);

		mFirstNameEditText = (EditText) findViewById(R.id.first_name_edt);
		mLastNameEditText = (EditText) findViewById(R.id.last_name_edt);
		mIdEditText = (EditText) findViewById(R.id.id_edt);
		mSaveButton = (Button) findViewById(R.id.saveButton);
		mLoadButton = (Button) findViewById(R.id.loadButton);

		mSaveButton.setOnClickListener(this);
		mLoadButton.setOnClickListener(this);
	}

	@AfterViews
	void initPresenter() {
		// 把UI中的实现的接口关联到Presenter中
		presenter = new DoterInfoPresenterImpl(this);
	}

	@Background
	@Click(R.id.bt)
	void clickButton() {
//		这里在Presenter中延时处理数据显示，休眠了数据；所以这里的设置后台运行
		presenter.loadData();
	}

	@UiThread
	@Override
	public void showLoading() {
		vc.showLoading();
	}

	@UiThread
	@Override
	public void showContent() {
		vc.showContent();
	}
	
	@UiThread
	@Override
	public void showDoterInfo(DoterBean info) {
		tv_info.setText(info.toString());
	}

	@UiThread
	@Override
	public void showError() {
		vc.showError();
	}

	@UiThread
	@Override
	public void showNonet() {
		vc.showNonet();
	}

	@UiThread
	@Override
	public void showEmpty() {
		vc.showEmpty();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.saveButton:
				// presenter.saveUser(getID(), getFristName(), getLastName());
				break;
			case R.id.loadButton:
				// presenter.loadUser(getID());
				break;
			default:
				break;
		}
	}

	@Override
	public void setFirstName(String firstName) {
		mFirstNameEditText.setText(firstName);
	}

	@Override
	public void setLastName(String lastName) {
		mLastNameEditText.setText(lastName);
	}

	@Override
	public int getID() {
		return Integer.parseInt(mIdEditText.getText().toString());
	}

	@Override
	public String getFristName() {
		return mFirstNameEditText.getText().toString();
	}

	@Override
	public String getLastName() {
		return mLastNameEditText.getText().toString();
	}
}
