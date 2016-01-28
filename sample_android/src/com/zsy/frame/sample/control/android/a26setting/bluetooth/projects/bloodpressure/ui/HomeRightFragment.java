package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.os.Bundle;
import android.view.View;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps.MyEventBusUtils.MyBackEvent;

import de.greenrobot.event.EventBus;

public abstract class HomeRightFragment extends BaseRightFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected int inflateContentView() {
		return R.layout.view_a26setting_bluetooth_projects_bloodpressure_home_right;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.hrtlTv:// show or hide the home left fragment
				EventBus.getDefault().post(new MyBackEvent());
				break;

			default:
				break;
		}

	}

}
