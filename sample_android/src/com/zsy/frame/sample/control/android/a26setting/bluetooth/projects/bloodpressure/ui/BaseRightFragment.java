package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.BaseFragment;

public abstract class BaseRightFragment extends BaseFragment implements OnClickListener {
	View topView;
	protected TextView top_leftTv;
	protected TextView top_centerTv;
	protected TextView top_rightTv;

	FrameLayout containerLl;

	@Override
	protected void _initData() {
		topView = (View) rootView.findViewById(R.id.hr_top);

		top_leftTv = (TextView) topView.findViewById(R.id.hrtlTv);
		top_centerTv = (TextView) topView.findViewById(R.id.hrtcTv);
		top_rightTv = (TextView) topView.findViewById(R.id.hrtrTv);

		top_leftTv.setOnClickListener(this);
		setTopLeftData();
		setTopCenterData();
		setTopRightData();

		containerLl = (FrameLayout) rootView.findViewById(R.id.hr_content);
		setContainerData();
	}

	abstract protected void setTopLeftData();// set home_right_top left data

	abstract protected void setTopCenterData();

	abstract protected void setTopRightData();

	abstract protected void setContainerData();

}
