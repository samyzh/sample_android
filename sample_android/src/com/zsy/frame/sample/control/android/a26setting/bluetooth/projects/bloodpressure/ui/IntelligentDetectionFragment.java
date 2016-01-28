package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BaseEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BaseEvent.UploadBloodPresureEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.DataSysEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.GattConnectedEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.GattDisConnectedEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.NotFindDeviceEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.ResultEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps.WeBlutoothManager;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.views.BPDetectView;

import de.greenrobot.event.EventBus;
/**
 * @description：血压检测中的智能检测
 * @author samy
 * @date 2015-2-24 下午5:00:20
 */
public class IntelligentDetectionFragment extends IntelligentManualFragment implements OnClickListener {
	LinearLayout sdrLl;
	TextView sbpTv;
	TextView dbpTv;
	TextView hrTv;

	// 是否处于resume状态
	private boolean isResumeState = false;
	// 圆盘刻度view
	private BPDetectView bpDetectView;

	/* 默认的蓝牙适配器 */
	private BluetoothAdapter mBluetoothAdapter;

	/* 请求打开蓝牙 */
	private static final int REQUEST_ENABLE = 0x1;

	WeBlutoothManager mWeBlutoothManager;

	@Override
	protected int inflateContentView() {
		return R.layout.fragment_a26setting_bluetooth_projects_bloodpressure_intelligent_detection;
	}

	@Override
	protected void _initData() {
		super._initData();

		sdrLl = (LinearLayout) rootView.findViewById(R.id.id_sdrLl);
		sbpTv = (TextView) rootView.findViewById(R.id.id_sbpTv);
		dbpTv = (TextView) rootView.findViewById(R.id.id_dbpTv);
		hrTv = (TextView) rootView.findViewById(R.id.id_hrTv);

		setTypeFace(sbpTv);
		setTypeFace(dbpTv);
		setTypeFace(hrTv);

		bpDetectView = (BPDetectView) rootView.findViewById(R.id.bp_detect_layout);
		bpDetectView.setDataSaveListen(new BPDetectView.DataSaveListen() {
			@Override
			public void dataSaveClicked() {
				// 点击保存数据回调
				saveBloodDataToDB();
			}
		});
		im_moreTv.setOnClickListener(this);

		bpDetectView.setState(BPDetectView.STATE_PREPARING);
		// setBloodDataToScale(0,200);
		// tipView.setText("蓝牙连接中");
		// tipView.setEnabled(false);
	}

	private void setTypeFace(TextView tView) {
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Bold.otf");
		tView.setTypeface(face);
	}

	/**
	 * 设置圆盘数据
	 * @param data 当前值
	 * @param maxData 最大值
	 */
	private void setBloodDataToScale(int data, int maxData) {
		// bloodDataTxtView.setText(data+"");
		// scaleDiskView.setProgrees(data*100/maxData);
		bpDetectView.setState(BPDetectView.STATE_DETECTING);
		bpDetectView.setProgrees(data * 100 / maxData);
	}

	@SuppressLint("NewApi")
	@Override
	protected void _initMoreData() {
		try {
			// 获取BluetoothAdapter
			BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
			mBluetoothAdapter = bluetoothManager.getAdapter();

			// 启动蓝牙
			if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
				// 用户请求打开蓝牙
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE);
			}
			else {
				mWeBlutoothManager = new WeBlutoothManager(getActivity());
				mWeBlutoothManager.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (Error error) {

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case Activity.RESULT_CANCELED:
				break;

			case Activity.RESULT_OK:
				mWeBlutoothManager = new WeBlutoothManager(getActivity());
				mWeBlutoothManager.start();
				break;
		}
	}

	/*
	 * void loadInitData(){
	 * //测试：初始化数据
	 * sdrLl.setVisibility(View.GONE);
	 * im_topIv.setVisibility(View.GONE);
	 * // im_hintRl.setBackground();
	 * // im_hintIv.setBackground();
	 * im_hintTv.setText(getString(R.string.not_measuring));
	 * im_moreTv.setVisibility(View.GONE);
	 * }
	 * void loadDetectionData(){
	 * //测试：检测中数据
	 * sdrLl.setVisibility(View.VISIBLE);
	 * sbpTv.setText("145");
	 * dbpTv.setText("70");
	 * hrTv.setText("60");
	 * im_topIv.setVisibility(View.GONE);
	 * // im_hintRl.setBackground();
	 * // im_hintIv.setBackground();
	 * im_hintTv.setText(getString(R.string.dection_exec));
	 * tipView.setText(getString(R.string.dection_exec));
	 * im_moreTv.setVisibility(View.GONE);
	 * }
	 * void loadEndData(){
	 * //测试：检测结束数据
	 * sdrLl.setVisibility(View.VISIBLE);
	 * sbpTv.setText("145");
	 * dbpTv.setText("70");
	 * hrTv.setText("60");
	 * im_topIv.setVisibility(View.VISIBLE);
	 * // im_hintRl.setBackground();
	 * // im_hintIv.setBackground();
	 * im_hintTv.setText(getString(R.string.dection_normal));
	 * im_moreTv.setVisibility(View.VISIBLE);
	 * }
	 */

	@Override
	public void onPause() {
		super.onPause();
		isResumeState = false;
	}

	@Override
	public void onResume() {
		super.onResume();
		isResumeState = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		if (mWeBlutoothManager != null) {
			mWeBlutoothManager.stop();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.im_hintmoreTv:

				break;

			default:
				break;
		}

	}

	public void onEventMainThread(UploadBloodPresureEvent baseEvent) {
//		CommonUtils.dismissDialog();

		if (baseEvent.httpRequestSuccess && baseEvent.resultCode == BaseEvent.REQUESTCODE_SUCCESS) {
//			CommonUtils.showToastMessage(getActivity(), baseEvent.resultMessage.toString());
		}
		else {
//			CommonUtils.showToastMessage(getActivity(), baseEvent.resultMessage.toString());
		}
	}

	public void onEventMainThread(GattConnectedEvent event) {
		Log.i("IntelligentDetection", event.toString() + "蓝牙连接：已经连接");
		// tipView.setText(getString(R.string.blood_detecting_tip));
		bpDetectView.setBelowTipText(getString(R.string.blood_detecting_tip));
	}

	public void onEventMainThread(GattDisConnectedEvent event) {
		Log.i("IntelligentDetection", event.toString() + "蓝牙连接：未连接");
	}

	/**
	 * @description：血压检测过程
	 * @author samy
	 * @date 2015-2-24 下午7:05:11
	 */
	public void onEventMainThread(DataSysEvent event) {
		Log.i("IntelligentDetection", event.toString());
		int currentPressure = event.currentBloodPressure;
		// bloodDataTxtView.setText(String.valueOf(currentPressure));
		bpDetectView.setState(BPDetectView.STATE_DETECTING);
		bpDetectView.setAboveTipText(String.valueOf(currentPressure));
		bpDetectView.getBelowTxtView().setEnabled(false);
		// setBloodDataToScale(currentPressure,200);
	}

	/**
	 * @description：没有发现设备
	 * @author samy
	 * @date 2015-2-24 下午7:05:21
	 */
	public void onEventMainThread(NotFindDeviceEvent event) {
		Log.i("IntelligentDetection", event.toString());
		// 当前页面才弹框
		if (isResumeState) {
			new AlertDialog.Builder(getActivity()).setTitle("异常提醒").setMessage("未能找到设备").setPositiveButton("重新连接", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mWeBlutoothManager.reStart();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create().show();

		}

	}

	/**
	 * @description：血压检测完毕
	 * @author samy
	 * @date 2015-2-24 下午7:05:55
	 */
	public void onEventMainThread(ResultEvent event) {
		Log.i("IntelligentDetection", event.toString());
		// tipView.setEnabled(true);
		// bloodDataTxtView.setText("血压检测完毕");
		// tipView.setText("保存至本地");
		bpDetectView.setState(BPDetectView.STATE_COMPLETE);
		// bpDetectView.setAboveTipText("血压检测完毕");
		// bpDetectView.setBelowTipText("保存至本地");
		sbpString = String.valueOf(event.systolic);
		dbpString = String.valueOf(event.diastolic);
		hrString = String.valueOf(event.pulse);

		sbpTv.setText(sbpString);
		dbpTv.setText(dbpString);
		hrTv.setText(hrString);
		execHttpMethod(INTELLIGENTDECTION);
	}

}
