package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.BaseFragment;

/**
 * @description：血压检测和自动输入Fragment的父类；（主要用处：通过EventBus处理显示）
 * @author samy
 * @date 2015-2-24 下午10:32:56
 */
public class IntelligentManualFragment extends BaseFragment {

	View imView;
	ImageView im_topIv;
	RelativeLayout im_hintRl;
	ImageView im_hintIv;
	TextView im_hintTv;
	TextView im_moreTv;

	String sbpString;
	String dbpString;
	String hrString;

	/**设备测量上传*/
	public static final int INTELLIGENTDECTION = 1;
	/** 手动输入上传*/
	public static final int MANUALINPUT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// BelterHttpRequest.getHttpRequest().register(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// BelterHttpRequest.getHttpRequest().unRegister(this);
	}

	@Override
	protected int inflateContentView() {
		return 0;
	}

	@Override
	protected void _initData() {
		imView = (View) rootView.findViewById(R.id.id_hintLl);
		im_topIv = (ImageView) imView.findViewById(R.id.im_topIv);
		im_hintRl = (RelativeLayout) imView.findViewById(R.id.im_hintRl);
		im_hintIv = (ImageView) imView.findViewById(R.id.im_hintIv);
		im_hintTv = (TextView) imView.findViewById(R.id.im_hintTv);
		im_moreTv = (TextView) imView.findViewById(R.id.im_hintmoreTv);
	}

	/**上传血压数据*/
	void execHttpMethod(int type) {
		// CommonUtils.createProcessingDialog(getActivity(), getString(R.string.progress_loading));

		// BloodPressureInfo bloodPressureInfo = new BloodPressureInfo();
		// bloodPressureInfo.type = type;
		// bloodPressureInfo.systolicPressure = sbpString;
		// bloodPressureInfo.diastolicPressure = dbpString;
		// bloodPressureInfo.heartRate = hrString;
		// bloodPressureInfo.time = CommonUtils.getLongTimeFromat(System.currentTimeMillis());
		// BelterHttpRequest.getHttpRequest().uploadBloodPressure(bloodPressureInfo);
	}

	void saveBloodDataToDB() {
		// LitePalUtils.saveBloodHistoryToLitePal(sbpString, dbpString, hrString);
	}

}
