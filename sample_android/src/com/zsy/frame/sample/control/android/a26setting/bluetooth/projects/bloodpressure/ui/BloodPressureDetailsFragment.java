package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.BaseFragment;

/**
 * @description：血压检查界面（不包含头部）
 * @author samy
 * @date 2015-2-24 下午4:31:30
 */
public class BloodPressureDetailsFragment extends BaseFragment implements OnClickListener {
	TextView detectionTv;
	TextView manualTv;
	private int index;

	IntelligentDetectionFragment idFragment;
	ManualInputFragment miFragment;

	Fragment currentFragment;

	@Override
	protected int inflateContentView() {
		return R.layout.fragment_a26setting_bluetooth_projects_bloodpressure_blood_pressure_details;
	}

	@Override
	protected void _initData() {
		detectionTv = (TextView) rootView.findViewById(R.id.bpd_idTv);
		detectionTv.setOnClickListener(this);
		manualTv = (TextView) rootView.findViewById(R.id.bpd_miTv);
		manualTv.setOnClickListener(this);

		idFragment = new IntelligentDetectionFragment();
		miFragment = new ManualInputFragment();

		getActivity().getSupportFragmentManager().beginTransaction().add(R.id.bpd_containerFl, idFragment).commit();
		index = 0;
		currentFragment = idFragment;

		detectionTv.setSelected(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bpd_idTv:
				if (index != 0) {
					index = 0;
					detectionTv.setSelected(true);
					manualTv.setSelected(false);
					switchContent(miFragment, idFragment);
				}
				break;

			case R.id.bpd_miTv:
				if (index != 1) {
					index = 1;
					detectionTv.setSelected(false);
					manualTv.setSelected(true);
					switchContent(idFragment, miFragment);
				}
				break;

			default:
				break;
		}
	}

	/**
	 * @description：这种切换不错
	 * @author samy
	 * @date 2015-2-25 下午4:42:32
	 */
	public void switchContent(Fragment from, Fragment to) {
		if (currentFragment != to) {
			currentFragment = to;
			if (!to.isAdded()) {// 先判断是否被add过
				getActivity().getSupportFragmentManager().beginTransaction().hide(from).add(R.id.bpd_containerFl, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			}
			else {
				getActivity().getSupportFragmentManager().beginTransaction().hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}

	}

}
