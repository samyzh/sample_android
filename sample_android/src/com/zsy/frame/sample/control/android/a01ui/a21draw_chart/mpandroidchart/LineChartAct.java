package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

/**
 * @description：建议使用最新的官方jar包；https://github.com/PhilJay/MPAndroidChart
 * @author samy
 * @date 2015-1-19 下午12:24:04
 */
public class LineChartAct extends BaseAct implements OnSeekBarChangeListener, OnChartValueSelectedListener {

	private LineChart mChart;
	private SeekBar mSeekBarX, mSeekBarY;
	private TextView tvX, tvY;

	public LineChartAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a21draw_chart_mpandroidchart_linechart);
	}

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);

		tvX = (TextView) findViewById(R.id.line_chart_xmax_tv);
		tvY = (TextView) findViewById(R.id.line_chart_ymax_tv);
		mSeekBarX = (SeekBar) findViewById(R.id.line_chart_1seekbar);
		mSeekBarY = (SeekBar) findViewById(R.id.line_chart_2seekbar);

		mSeekBarX.setProgress(45);
		mSeekBarY.setProgress(100);

		mSeekBarY.setOnSeekBarChangeListener(this);
		mSeekBarX.setOnSeekBarChangeListener(this);

		mChart = (LineChart) findViewById(R.id.line_chart_pic_lc);

		initChart();// 注意初始化配置的顺序，后设置数据显示；
		setChartData(45, 100);
	}

	private void initChart() {
		mChart.setOnChartValueSelectedListener(this);// 设置一个选择监听器将生成的图表或没有选择回调时的值。 回调包含所选值及其指数。

		mChart.setUnit(" ¥");
		mChart.setDrawUnitsInChart(true);// 设置表格上数据显示单元；
		mChart.setStartAtZero(true);// 设置是否重零开始计算；
		mChart.setDrawYValues(true);// 如果设置为true,实际值会点,直接点上有值。

		/**
		 * 设置图标跟描述边界线部分
		 */
		mChart.setDrawBorder(true);
		mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM, BorderPosition.LEFT });// 设置图标边框
		mChart.setBorderColor(Color.BLUE);
		mChart.setBorderWidth(2);

		// no description text
		mChart.setDescription("samy测试数据");// 设置一个描述的文本出现在右下角的图。
		mChart.setNoDataTextDescription("samy暂无数据");

		// mChart.highlightValues(highs);//突出显示指定的表中的条目
		mChart.setHighlightEnabled(true);// 如果设置为true,高亮显示/选择值是可能的图表。 默认值:真的。
		mChart.setTouchEnabled(true);// 如果设置为真,触控手势(如缩放和拖动)将可能在图表。 注意:如果触控手势被禁用,强调联系是禁用的。 默认值:真的。
		mChart.setDragEnabled(true);// 和setTouchEnabled设置true才有用；
		mChart.setScaleEnabled(true);// 启用/禁用拖动和缩放为图表。
		mChart.setDrawGridBackground(true);// 设置单独表格的背景是否跟整体表格颜色一样；
		mChart.setDrawVerticalGrid(true); // 设置表格竖直有线
		mChart.setDrawHorizontalGrid(true);// 设置表格水平有线
		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setBackgroundColor(Color.GREEN);// 设置总体背景色

		// mChart.setScaleMinima(scaleXmin, scaleYmin);//集的最小规模因素x和y轴。 如果设置例如3 f,用户将无法完全缩小。
		// mChart.centerViewPort(xIndex, yVal);//这种方法可以目标视图的中心(你可以看到从图)到一个特定的位置在图,描述了该指数x轴和y轴上的价值。 这也很好地结合工作setScaleMinima(...)方法。
		mChart.fitScreen();// 重置所有缩放和拖动,使图表符合具体的界限。

		mChart.animateX(2500);// 模拟水平轴上的图表值的,也就是说图表将建立在指定的时间内从左到右。
		// mChart.animateY(durationMillis);//:模拟垂直轴上的图表值的,也就是说图表将建立在指定时间从下到上。
		// mChart.animateXY(durationMillisX, durationMillisY);//模拟两个水平和垂直轴的,导致左/右下/上累积。

		mChart.setDrawLegend(true);// 设置为true时，下面部分才有用，会显示出来
		mChart.setOffsets(Utils.convertDpToPixel(15f), 0, Utils.convertDpToPixel(15f), 0);// 设置左右上下边境，防止有的数据数字显示不全，先发现源码的设置不对，可对源码修改；现在版本1.7.4；

		// 设置曲线的上园点
		Paint mCirclePaintInner = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaintInner.setStyle(Paint.Style.FILL);
		mCirclePaintInner.setColor(Color.RED);
		mChart.setPaint(mCirclePaintInner, Chart.PAINT_CIRCLES_INNER);

		/**
		 * 显示/样式标签和底部描述
		 */
		// Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		XLabels xl = mChart.getXLabels(); // 描述图的值X轴描述
		// xl.setTypeface(tf);
		xl.setTextColor(Color.WHITE);
		xl.setAvoidFirstLastClipping(true);// 设置不会靠边显示
		xl.setPosition(XLabelPosition.BOTTOM);
		// xl.setTextColor(Color.parseColor("#333333"));
		// xl.setTextSize(10f);
		xl.setSpaceBetweenLabels(0);

		YLabels yl = mChart.getYLabels();// 描述图的值Y轴描述
		// yl.setTypeface(tf);
		yl.setTextColor(Color.WHITE);
		yl.setPosition(YLabelPosition.LEFT);

	}

	private void setChartData(int count, float range) {
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add((i) + "");
		}

		ArrayList<Entry> yVals = new ArrayList<Entry>();

		for (int i = 0; i < count; i++) {
			float mult = (range + 1);
			float val = (float) (Math.random() * mult) + 3;// + (float)((mult * 0.1) / 10);
			yVals.add(new Entry(val, i));
		}

		// List<TradelettersBean> beans = rs;
		// ArrayList<String> xVals = new ArrayList<String>();
		// ArrayList<Entry> yVals = new ArrayList<Entry>();
		// for (int i = 0; i < beans.size(); i++) {
		// xVals.add(beans.get(i).day);
		// yVals.add(new Entry(beans.get(i).turnover,i));
		// }

		// mChart.setDrawLegend(true);//设置为true时，下面部分才有用，会显示出来；
		LineDataSet set1 = new LineDataSet(yVals, "拼宝交易快报");
		// set1.setColor(ColorTemplate.getHoloBlue());
		// set1.setCircleColor(ColorTemplate.getHoloBlue());
		set1.setColor(ColorTemplate.getHoloBlue());
		set1.setLineWidth(2f);
		set1.setCircleColor(Color.RED);
		set1.setCircleSize(4f);
		set1.setFillAlpha(65);
		set1.setFillColor(ColorTemplate.getHoloBlue());
		set1.setHighLightColor(Color.rgb(244, 117, 117));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets
		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);
		// set data
		mChart.setData(data);
		mChart.invalidate();

		// get the legend (only possible after setting data)
		Legend l = mChart.getLegend();// 底部描述表格部分
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setForm(LegendForm.SQUARE);// 设置描述为隔子；
		// l.setTypeface(tf);
		l.setTextColor(Color.WHITE);
		// l.setXEntrySpace(5f);
		// l.setFormSize(10f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_a01ui_a21draw_chart_mpandroidchart_linechart_line, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.actionToggleValues: {
				if (mChart.isDrawYValuesEnabled()) mChart.setDrawYValues(false);
				else mChart.setDrawYValues(true);
				mChart.invalidate();
				break;
			}
			case R.id.actionToggleHighlight: {
				if (mChart.isHighlightEnabled()) mChart.setHighlightEnabled(false);
				else mChart.setHighlightEnabled(true);
				mChart.invalidate();
				break;
			}
			case R.id.actionToggleFilled: {

				ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData().getDataSets();

				for (LineDataSet set : sets) {
					if (set.isDrawFilledEnabled()) set.setDrawFilled(false);
					else set.setDrawFilled(true);
				}
				mChart.invalidate();
				break;
			}
			case R.id.actionToggleCircles: {
				ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData().getDataSets();

				for (LineDataSet set : sets) {
					if (set.isDrawCirclesEnabled()) set.setDrawCircles(false);
					else set.setDrawCircles(true);
				}
				mChart.invalidate();
				break;
			}
			case R.id.actionToggleCubic: {
				ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData().getDataSets();

				for (LineDataSet set : sets) {
					if (set.isDrawCubicEnabled()) set.setDrawCubic(false);
					else set.setDrawCubic(true);
				}
				mChart.invalidate();
				break;
			}
			case R.id.actionToggleStartzero: {
				if (mChart.isStartAtZeroEnabled()) mChart.setStartAtZero(false);
				else mChart.setStartAtZero(true);

				mChart.invalidate();
				break;
			}
			case R.id.actionTogglePinch: {
				if (mChart.isPinchZoomEnabled()) mChart.setPinchZoom(false);
				else mChart.setPinchZoom(true);

				mChart.invalidate();
				break;
			}
			case R.id.animateX: {
				mChart.animateX(3000);
				break;
			}
			case R.id.animateY: {
				mChart.animateY(3000);
				break;
			}
			case R.id.animateXY: {
				mChart.animateXY(3000, 3000);
				break;
			}
			case R.id.actionToggleAdjustXLegend: {
				XLabels xLabels = mChart.getXLabels();

				if (xLabels.isAdjustXLabelsEnabled()) xLabels.setAdjustXLabels(false);
				else xLabels.setAdjustXLabels(true);

				mChart.invalidate();
				break;
			}
			case R.id.actionToggleFilter: {
				Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER, 35);

				if (!mChart.isFilteringEnabled()) {
					mChart.enableFiltering(a);
				}
				else {
					mChart.disableFiltering();
				}
				mChart.invalidate();
				break;
			}
			case R.id.actionSave: {
				if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
					Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!", Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT).show();

				// mChart.saveToGallery("title"+System.currentTimeMillis(),1);//将当前图表状态保存为一个图像画廊。
				// mChart.saveToPath(title, pathOnSD);//将当前图表状态保存为一个图像到指定的路径。
				break;
			}
		}
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		tvX.setText("" + (mSeekBarX.getProgress() + 1));
		tvY.setText("" + (mSeekBarY.getProgress()));

		setChartData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

		// redraw
		mChart.invalidate();
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		Log.i("Entry selected", e.toString());
		Toast.makeText(this, "选中了" + dataSetIndex, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected() {
		Log.i("Nothing selected", "Nothing selected.");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
