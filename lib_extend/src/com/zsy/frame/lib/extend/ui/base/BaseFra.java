package com.zsy.frame.lib.extend.ui.base;


import android.app.Activity;

import com.zsy.frame.lib.ui.fragment.SYBaseFra;

public abstract class BaseFra extends SYBaseFra {
	/**
	 * 嵌套viewpager时，第一次显示回调
	 */
	public interface OnInitShowListener {
		void onInitShow();
	}
	
	protected BaseAct baseAct;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		baseAct = (BaseAct)activity;
	}
//
//	@Override
//	public void startActivity(Intent intent) {
//		super.startActivity(intent);
//		getActivity().overridePendingTransition(R.anim.base_activity_right_in, R.anim.base_activity_right_out);
//	}
//
//	@Override
//	public void startActivityForResult(Intent intent, int requestCode) {
//		super.startActivityForResult(intent, requestCode);
//		getActivity().overridePendingTransition(R.anim.base_activity_right_in, R.anim.base_activity_right_out);
//	}
}
