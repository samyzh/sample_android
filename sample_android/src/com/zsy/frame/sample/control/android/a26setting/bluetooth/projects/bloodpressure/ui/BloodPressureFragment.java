package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.BodyDataHistoryAct;

/**
 * @description：血压检查界面（包含头部）
 * @author samy
 * @date 2015-2-24 下午4:31:30
 */
public class BloodPressureFragment extends HomeRightFragment {

	@Override
	protected void setTopLeftData() {
		top_leftTv.setBackground(getActivity().getResources().getDrawable(R.drawable.a26setting_bluetooth_projects_bloodpressure_btn_menu));
	}

	@Override
	protected void setTopCenterData() {
		top_centerTv.setText(getString(R.string.decetion_blood_pressure));
	}

	@Override
	protected void setTopRightData() {
		top_rightTv.setText(getString(R.string.history));
		top_rightTv.setOnClickListener(rightListener);
	}

	@Override
	protected void setContainerData() {
		BloodPressureDetailsFragment fragment = new BloodPressureDetailsFragment();
		getActivity().getSupportFragmentManager().beginTransaction().add(R.id.hr_content, fragment).commit();
	}

	OnClickListener rightListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), BodyDataHistoryAct.class);
			getActivity().startActivity(intent);
		}
	};

}
