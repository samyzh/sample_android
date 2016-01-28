package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.CurveInfo;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.SleepQualityInfo;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.CurveGraphView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.DateSwitchView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.MovementStepsCurveGraphView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.SleepQualityCurveGraphView;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views.TabSwitchView;

/**
 * @description：身体数据界面（不包含头部）
 * @author samy
 * @date 2015-2-24 下午4:31:30
 */
public class BodyDataContentFra extends BaseFragment {
	public final int TYPE_DAY = 0;
	public final int TYPE_WEEK = 1;
	public final int TYPE_MONTH = 2;

	/**
	 * 运动步数曲线图
	 */
	private CurveGraphView movementStepsCurveGraphView;
	/**
	 * 睡眠质量曲线图
	 */
	private SleepQualityCurveGraphView sleepQualityCurveGraphView;

	/**
	 * x轴上显示的值
	 */
	private String[] days = new String[24];
	private String[] weeks = new String[7];
	private String[] months = new String[30];

	private int type = TYPE_DAY;

	// 日周月切换tab
	private TabSwitchView tabSwitchView;
	private DateSwitchView dateSwitchView;

	@Override
	protected int inflateContentView() {
		return R.layout.fragment_a01ui_a21draw_chart_ownpaint_bodydata;
	}

	@Override
	protected void _initData() {
		super._initData();
		movementStepsCurveGraphView = (MovementStepsCurveGraphView) rootView.findViewById(R.id.movementStepsCurveView);
		sleepQualityCurveGraphView = (SleepQualityCurveGraphView) rootView.findViewById(R.id.heartRateCurveView);

		tabSwitchView = (TabSwitchView) rootView.findViewById(R.id.tabs_view_layout);
		tabSwitchView.setTabChangeListen(tabChangeListen);

		dateSwitchView = (DateSwitchView) rootView.findViewById(R.id.date_switch_view_layout);
		dateSwitchView.initDateSwitchView(type);
		dateSwitchView.setArrowClickListen(arrowClickListen);

		weeks = new String[] { getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday) };
		for (int i = 0; i < 24; i++) {
			days[i] = i + 1 + "";
			if (i == 0 || i == 23) {
				days[i] = i + 1 + "";
			}
			else {
				days[i] = "";
			}
		}
		for (int i = 0; i < 30; i++) {
			months[i] = i + 1 + "";
		}

		initMovementStepsCurveData();
		initSleepQualityCurveData();
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

			genMovementStepsCurveData(type);
			genSleepQualityCurveData(type);
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
			genMovementStepsCurveData(type);
			genSleepQualityCurveData(type);
		}
	};

	/**
	 * 设置运动步数曲线的数值
	 */
	private void initMovementStepsCurveData() {
		movementStepsCurveGraphView.setMarginTop(20);
		movementStepsCurveGraphView.setMarginBottom(50);
		movementStepsCurveGraphView.setMstyle(CurveGraphView.LineStyle.Curve);
		movementStepsCurveGraphView.setImaginaryLine(true);
		movementStepsCurveGraphView.setIntervalBgColor(getResources().getColor(R.color.curve_bg_color));
		movementStepsCurveGraphView.setLandscapeLinesIndex(new ArrayList<Integer>());
		movementStepsCurveGraphView.setIgnoreLessThanYStartValueData(true);

		genMovementStepsCurveData(type);

	}

	private void genMovementStepsCurveData(int type) {
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
			if (i == 1 || i == count) {
				map.put(i + 0.0, 0.0);
				map2.put(i + 0.0, 0.0);
			}
			else if (type == TYPE_WEEK) {
				map.put(i + 0.0, 2500.0 + Math.random() * 1000 % 800);
				map2.put(i + 0.0, 1000.0 + Math.random() * 1000 % 800);
			}
			else {
				if (i % 5 == 0) {
					map.put(i + 0.0, 2500.0 + Math.random() * 1000 % 800);
					map2.put(i + 0.0, 1000.0 + Math.random() * 1000 % 800);
				}
				else {
					map.put(i + 0.0, 0.0);
					map2.put(i + 0.0, 0.0);
				}
			}
		}
		CurveInfo curveInfo1 = new CurveInfo(map, getResources().getColor(R.color.target_steps_color), getResources().getColor(R.color.target_steps_color));
		curveInfos.add(curveInfo1);
		CurveInfo curveInfo2 = new CurveInfo(map2, getResources().getColor(R.color.practical_steps_color), getResources().getColor(R.color.practical_steps_color));
		curveInfo2.setLinearGradientColors(new int[] { getResources().getColor(R.color.practical_steps_color), 0xff7fd271 });
		curveInfos.add(curveInfo2);
		movementStepsCurveGraphView.setCurveGraphViewData(curveInfos, count, count, 4000, 1000, xUnit, null, true);
		if (type == TYPE_DAY || type == TYPE_MONTH) {
			movementStepsCurveGraphView.setXOrderUnitOnlyShowStartEnd(true);
		}
		else {
			movementStepsCurveGraphView.setXOrderUnitOnlyShowStartEnd(false);
		}
		movementStepsCurveGraphView.reset();
	}

	private void genSleepQualityCurveData(int type) {
		// 清醒时数据不用传
		ArrayList<SleepQualityInfo> infos = new ArrayList<SleepQualityInfo>();
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
		for (int i = 0; i < count; i++) {
			int random = ((int) (Math.random() * 10)) % 3;
			Log.i("rainy", "Math.random()=" + Math.random() + " random=" + random);
			// 清醒时数据不用传
			if (random != 0) {
				infos.add(new SleepQualityInfo(i + 1, random));
			}
		}

		sleepQualityCurveGraphView.setCurveGraphViewData(infos, count, count, xUnit);
		if (type == TYPE_DAY || type == TYPE_MONTH) {
			sleepQualityCurveGraphView.setXOrderUnitOnlyShowStartEnd(true);
		}
		else {
			sleepQualityCurveGraphView.setXOrderUnitOnlyShowStartEnd(false);
		}
		sleepQualityCurveGraphView.reset();
	}

	/**
	 * 设置睡眠质量曲线数值
	 */
	private void initSleepQualityCurveData() {
		sleepQualityCurveGraphView.setMarginTop(20);
		sleepQualityCurveGraphView.setMarginBottom(50);
		sleepQualityCurveGraphView.setShowYOrderNum(false);
		sleepQualityCurveGraphView.setLandscapeLinesIndex(new ArrayList<Integer>());
		sleepQualityCurveGraphView.setMstyle(CurveGraphView.LineStyle.Line);
		sleepQualityCurveGraphView.setImaginaryLine(true);
		genSleepQualityCurveData(type);
	}
}
