package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.CurveInfo;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.Information;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.helps.SharePlatformUtils;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.CurveGraphView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.DateSwitchView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.TabSwitchView;

/**
 * @description：历史跟踪界面
 * @author samy
 * @date 2015-2-24 下午4:58:24
 */
//public class BodyDataHistoryActivity extends Activity  {
	public class BodyDataHistoryAct extends Activity implements PlatformActionListener {
	public static final int TYPE_DAY = 0;
	public static final int TYPE_WEEK = 1;
	public static final int TYPE_MONTH = 2;
	private Context mContext;

	Information information;
	/**
	 * 血压曲线图
	 */
	private CurveGraphView bloodPressureCurveGraphView;
	/**
	 * 心率曲线图
	 */
	private CurveGraphView heartRateCurveGraphView;
	/**
	 * x轴上显示的值
	 */
	private String[] days = new String[24];
	private String[] weeks = new String[7];
	private String[] months = new String[30];

	private TextView backTxtView;
	private TextView shareTxtView;
	private TextView titleTxtView;

	// 日周月切换tab
	private TabSwitchView tabSwitchView;

	private int type = TYPE_DAY;

	private DateSwitchView dateSwitchView;

	private static final int SHARE_SUCCESS = 2;
	private static final int SHARE_FAILER = 3;
	private static final int SHARE_CANCEL = 4;
	private PlatformActionListener shareListener;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHARE_CANCEL:
					Toast.makeText(mContext, R.string.share_cancel, Toast.LENGTH_SHORT).show();
					break;
				case SHARE_FAILER:
					Toast.makeText(mContext, R.string.share_failure, Toast.LENGTH_SHORT).show();
					break;
				case SHARE_SUCCESS:
					Toast.makeText(mContext, R.string.share_success, Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(mContext);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a21draw_chart_ownpaint_bodydata_history);
		mContext = this;
		shareListener = this;
		initView();
		initData();
//		还没有申请到KEY先不考虑
		ShareSDK.initSDK(mContext);
	}

	private void initView() {
		bloodPressureCurveGraphView = (CurveGraphView) findViewById(R.id.bloodCurveView);
		heartRateCurveGraphView = (CurveGraphView) findViewById(R.id.heartRateCurveView);

		backTxtView = (TextView) findViewById(R.id.hrtlTv);
		shareTxtView = (TextView) findViewById(R.id.hrtrTv);
		titleTxtView = (TextView) findViewById(R.id.hrtcTv);

		tabSwitchView = (TabSwitchView) findViewById(R.id.tabs_view_layout);
		tabSwitchView.setTabChangeListen(tabChangeListen);

		dateSwitchView = (DateSwitchView) findViewById(R.id.date_switch_view_layout);
		dateSwitchView.initDateSwitchView(type);
		dateSwitchView.setArrowClickListen(arrowClickListen);
	}

	DateSwitchView.ArrowClickListen arrowClickListen = new DateSwitchView.ArrowClickListen() {
		@Override
		public void arrowClicked(String date) {
			// TODO根据当前日期读取数据
			/*
			 * if(没有更新的数据了){
			 * dateSwitchView.setHasNewerData(false);
			 * }else{
			 * dateSwitchView.setHasNewerData(true);
			 * }
			 * if(没有更早的数据了){
			 * dateSwitchView.setHasOlderData(false);
			 * }else{
			 * dateSwitchView.setHasOlderData(true);
			 * }
			 */

			genBloodData(type);
			genHeartRateData(type);
		}
	};

	private TabSwitchView.TabChangeListen tabChangeListen = new TabSwitchView.TabChangeListen() {
		@Override
		public void tabChanged(int select) {
			switch (select) {
				case 0:// 日
					type = TYPE_DAY;
					break;
				case 1:// 周
					type = TYPE_WEEK;
					break;
				case 2:// 月
					type = TYPE_MONTH;
					break;
			}
			dateSwitchView.setSwtichType(type);
			genBloodData(type);
			genHeartRateData(type);
		}
	};

	private void initData() {
		weeks = new String[] { getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday) };
		for (int i = 0; i < 24; i++) {
			days[i] = i + 1 + "";
		}
		for (int i = 0; i < 30; i++) {
			months[i] = i + 1 + "";
		}
		initBloodPressureCurveData();
		initHeartRateCurveData();

		backTxtView.setText(getString(R.string.btn_back)); // TODO 没有图片资源，暂时使用文字
		shareTxtView.setText(getString(R.string.btn_share)); // TODO 没有图片资源，暂时使用文字
		titleTxtView.setText(getString(R.string.title_history));

		backTxtView.setOnClickListener(clickListener);
		shareTxtView.setOnClickListener(clickListener);
		
		information = new Information();
		information.setInfo_id("12");
		information.setInfo_title("历史体检报告");
		information.setInfo_content("血压值：   ，心率值：  ");
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.hrtlTv:
					finish();
					break;
				case R.id.hrtrTv:
					SharePlatformUtils.selectPlatform(BodyDataHistoryAct.this, information, shareListener);
					break;
			}
		}
	};

	/**
	 * 设置血压曲线的数值
	 */
	private void initBloodPressureCurveData() {
		bloodPressureCurveGraphView.setyStartValue(30);
		bloodPressureCurveGraphView.setIntervalBgColor(getResources().getColor(R.color.curve_bg_color));
		bloodPressureCurveGraphView.setMarginTop(20);
		bloodPressureCurveGraphView.setMarginBottom(50);
		bloodPressureCurveGraphView.setMstyle(CurveGraphView.LineStyle.Line);
		bloodPressureCurveGraphView.setImaginaryLine(true);
		ArrayList<Integer> which = new ArrayList<Integer>();
		which.add(new Integer(1));
		which.add(new Integer(4));
		bloodPressureCurveGraphView.setLandscapeLinesIndex(which);

		genBloodData(type);
	}
	
	/**
	 * 随机产生数据
	 * @param type
	 */
	private void genBloodData(int type) {
		ArrayList<CurveInfo> curveInfos = new ArrayList<CurveInfo>();
		HashMap<Double, Double> map = new HashMap<Double, Double>();
		HashMap<Double, Double> map2 = new HashMap<Double, Double>();
		int count = 0;
		String[] xUnit = days;
		switch (type) {
			case TYPE_DAY:
				count = 24;
				xUnit = days;
				break;
			case TYPE_WEEK:
				count = 7;
				xUnit = weeks;
				break;
			case TYPE_MONTH:
				count = 30;
				xUnit = months;
		}

		for (int i = 1; i <= count; i++) {
			map.put(i + 0.0, 120.0 + Math.random() * 100 % 80);
			map2.put(i + 0.0, 40.0 + Math.random() * 100 % 70);
		}

		CurveInfo curveInfo1 = new CurveInfo(map, getResources().getColor(R.color.diastolic_pressure_color), getResources().getColor(R.color.diastolic_pressure_color));
		curveInfos.add(curveInfo1);
		CurveInfo curveInfo2 = new CurveInfo(map2, getResources().getColor(R.color.systolic_pressure_color), getResources().getColor(R.color.systolic_pressure_color));
		curveInfos.add(curveInfo2);

		bloodPressureCurveGraphView.setCurveGraphViewData(curveInfos, 7, count, 210, 30, xUnit, null, true);
		bloodPressureCurveGraphView.reset();
	}
	

	/**
	 * 设置心率曲线数值
	 */
	private void initHeartRateCurveData() {
		// heartRateCurveGraphView.setFillData(true);
		heartRateCurveGraphView.setMarginTop(20);
		heartRateCurveGraphView.setMarginBottom(60);
		heartRateCurveGraphView.setMstyle(CurveGraphView.LineStyle.Line);
		heartRateCurveGraphView.setImaginaryLine(true);

		genHeartRateData(type);
	}

	/**
	 * 随机产生数据
	 * @param type
	 */
	private void genHeartRateData(int type) {
		ArrayList<CurveInfo> heartCurveInfo = new ArrayList<CurveInfo>();
		HashMap<Double, Double> map = new HashMap<Double, Double>();
		int count = 0;
		String[] xUnit = days;
		switch (type) {
			case TYPE_DAY:
				count = 24;
				xUnit = days;
				break;
			case TYPE_WEEK:
				count = 7;
				xUnit = weeks;
				break;
			case TYPE_MONTH:
				count = 30;
				xUnit = months;
		}

		for (int i = 1; i <= count; i++) {
			map.put(i + 0.0, 20.0 + Math.random() * 100 % 30);
		}
		CurveInfo info = new CurveInfo(map, getResources().getColor(R.color.heart_rate_color), getResources().getColor(R.color.heart_rate_color));
		info.setLinearGradientColors(new int[] { getResources().getColor(R.color.heart_rate_color), 0xffedc1fe });
		heartCurveInfo.add(info);
		heartRateCurveGraphView.setCurveGraphViewData(heartCurveInfo, 7, count, 72, 24, xUnit, null, false);
		heartRateCurveGraphView.reset();
	}


	@Override
	public void onComplete(Platform platform, int i, HashMap<String, Object> stringObjectHashMap) {
		mHandler.sendEmptyMessage(SHARE_SUCCESS);
	}

	@Override
	public void onError(Platform platform, int i, Throwable throwable) {
		mHandler.sendEmptyMessage(SHARE_FAILER);
	}

	@Override
	public void onCancel(Platform platform, int i) {
		mHandler.sendEmptyMessage(SHARE_CANCEL);
	}
}
