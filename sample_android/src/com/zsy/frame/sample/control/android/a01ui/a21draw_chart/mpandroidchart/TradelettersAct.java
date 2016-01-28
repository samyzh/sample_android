package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.google.gson.reflect.TypeToken;
import com.zsy.frame.lib.extend.net.http.volley.EncryptWrapAjaxParams;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.net.http.volley.Response.Listener;
import com.zsy.frame.lib.net.http.volley.app.samy.FormResultRequest;
import com.zsy.frame.lib.net.http.volley.app.samy.RequestResult;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.UrlConstants;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart.helps.MoneyShowTool;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart.helps.TradelettersBean;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart.helps.TurnoverBean;

/**
 * @description：拼宝交易快报
 * 更好的方法是把这个做成DialogFragment
 * @author samy
 * @date 2015-1-7 上午10:32:36
 */
public class TradelettersAct extends BaseAct {
	private LinearLayout trade_letter_root_ll;
	private TextView todays_sales_tv;
	private TextView yesterday_sales_tv;
	private TextView this_month_sales_tv;
	private TextView total_sales_tv;
	private LineChart mChart;

	private TurnoverBean turnoverBean;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a21draw_chart_mpandroidchart_trade_letters);
	}

	@Override
	protected void initData() {
		super.initData();

		Window dialogWindow = getWindow();
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (d.getWidth() * 0.8);
		dialogWindow.setAttributes(p);

		// turnoverBean = (TurnoverBean) getIntent().getSerializableExtra(TurnoverBean.class.getName());
		turnoverBean = new TurnoverBean();
		turnoverBean.setCumulativeTurnover(1223434);
		turnoverBean.setThisMonthTurnover(1223434);
		turnoverBean.setTodayTurnover(1223434);
		turnoverBean.setYesterdayTurnover(1223434);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		trade_letter_root_ll = (LinearLayout) findViewById(R.id.trade_letter_root_ll);
		todays_sales_tv = (TextView) findViewById(R.id.todays_sales_tv);
		yesterday_sales_tv = (TextView) findViewById(R.id.yesterday_sales_tv);
		this_month_sales_tv = (TextView) findViewById(R.id.this_month_sales_tv);
		total_sales_tv = (TextView) findViewById(R.id.total_sales_tv);
		mChart = (LineChart) findViewById(R.id.trade_letters_lc);
		todays_sales_tv.setText(Html.fromHtml("¥<B>" + MoneyShowTool.formatNolastDF(turnoverBean.getTodayTurnover()) + "</B><br/><font color=#969696>今日销售额</font>"));
		yesterday_sales_tv.setText(Html.fromHtml("¥<B>" + MoneyShowTool.formatNolastDF(turnoverBean.getYesterdayTurnover()) + "</B><br/><font color=#969696>昨日销售额</font>"));
		this_month_sales_tv.setText(Html.fromHtml("¥<B>" + MoneyShowTool.formatNolastDF(turnoverBean.getThisMonthTurnover()) + "</B><br/><font color=#969696>本月销售额</font>"));
		total_sales_tv.setText(Html.fromHtml("¥<B>" + MoneyShowTool.formatNolastDF(turnoverBean.getCumulativeTurnover()) + "</B><br/><font color=#969696>累计销售额</font>"));

		trade_letter_root_ll.setOnClickListener(this);
		initChart();

		refreshData();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.trade_letter_root_ll:
				finish();
				break;
		}
	}

	private void refreshData() {
		showLoadingDialog(getString(R.string.common_loading));
		EncryptWrapAjaxParams ajaxParams = new EncryptWrapAjaxParams();
		ajaxParams.putCommonTypeParam("userId", 0);
		// ajaxParams.putCommonTypeParam("userId", 0).putCommonTypeParam(key, value, defaultValue);
		// ajaxParams.put(new FileTypeParam("fileName", picFile.getAbsolutePath(), FileUtils.getMimeType(Uri.fromFile(picFile).toString())));
		FormResultRequest<List<TradelettersBean>> getTurnoverListRequest = new FormResultRequest(UrlConstants.GETTURNOVERLIST, getTurnoverListListner, this, new TypeToken<RequestResult<List<TradelettersBean>>>() {
		}.getType());
		getTurnoverListRequest.setRequestParams(ajaxParams);
		executeRequest(getTurnoverListRequest);
	}

	private Listener<RequestResult<List<TradelettersBean>>> getTurnoverListListner = new Listener<RequestResult<List<TradelettersBean>>>() {
		@Override
		public void onResponse(RequestResult<List<TradelettersBean>> result) {
			dismissLoadingDialog();
			if (result != null && result.getRs() != null) {
				fillViewFromNetwork(result.getRs());
			}
		}
	};

	protected void fillViewFromNetwork(List<TradelettersBean> rs) {
		setData(rs);
	}

	private void initChart() {
		mChart.setStartAtZero(true);

		mChart.setDrawBorder(true);
		mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM, BorderPosition.LEFT });// 设置图标边框
		mChart.setBorderColor(Color.parseColor("#969696"));
		mChart.setBorderWidth(1);

		mChart.setDescription("");// 设置一个描述的文本出现在右下角的图。
		mChart.setNoDataText("暂无数据");

		mChart.setHighlightEnabled(true);
		mChart.setTouchEnabled(false);
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);
		mChart.setGridColor(0xffe3e3e4);
		mChart.setDrawVerticalGrid(true);
		mChart.setDrawHorizontalGrid(true);
		mChart.setPinchZoom(true);
		mChart.setBackgroundColor(Color.WHITE);

		Paint mCirclePaintInner = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaintInner.setStyle(Paint.Style.FILL);
		mCirclePaintInner.setColor(Color.RED);
		mChart.setPaint(mCirclePaintInner, Chart.PAINT_CIRCLES_INNER);

		mChart.animateXY(3000, 3000);

		mChart.setDrawYValues(true);
		mChart.setDrawYLabels(false);
		mChart.setDrawXLabels(true);
		mChart.setDrawLegend(false);

		mChart.setValueTextColor(0xffe12228);
		mChart.setMinOffsets(Utils.convertDpToPixel(15f), 0, Utils.convertDpToPixel(15f), 0);

		XLabels xl = mChart.getXLabels();
		xl.setTextColor(0xff333333);
		xl.setTextSize(10f);
		xl.setSpaceBetweenLabels(2);
		xl.setPosition(XLabelPosition.BOTTOM);
		xl.setAdjustXLabels(true);
	}

	private void setData(List<TradelettersBean> rs) {
		if (rs.isEmpty()) { return; }

		List<TradelettersBean> beans = rs;
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int i = 0; i < beans.size(); i++) {
			xVals.add(beans.get(i).day);
			yVals.add(new Entry(beans.get(i).turnover, i));
		}

		LineDataSet set1 = new LineDataSet(yVals, "拼宝交易快报");
		set1.setColor(Color.RED);
		set1.setCircleColor(Color.RED);
		set1.setCircleSize(3f);
		set1.setDrawCircles(true);

		set1.setLineWidth(1f);
		set1.setDrawFilled(false);
		set1.setFillAlpha(100);
		set1.setFillColor(Color.RED);
		// set1.setHighLightColor(Color.rgb(244, 117, 117));
		set1.setHighLightColor(Color.RED);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1);

		LineData data = new LineData(xVals, dataSets);
		mChart.setData(data);
		mChart.fitScreen();
		mChart.invalidate();
	}
}
