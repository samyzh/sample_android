package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected View rootView;

	protected Bundle savedInstanceState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (inflateContentView() > 0) {
			rootView = (ViewGroup) inflater.inflate(inflateContentView(), null);
			rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			return rootView;
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		_initData();
		_initMoreData();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		_initSaveInstance(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected void _initData() {
	}

	/**
	 * @description： 对_initData()不方便处理的进一步处理
	 * @author samy
	 * @date 2015-2-24 下午3:19:03
	 */
	protected void _initMoreData() {
	}

	protected void _initSaveInstance(Bundle outState) {
	}

	abstract protected int inflateContentView();
}
