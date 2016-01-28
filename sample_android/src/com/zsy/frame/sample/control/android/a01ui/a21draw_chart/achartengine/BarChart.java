package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.achartengine;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * @description：柱状图
 * @author samy
 * @date 2014年9月9日 下午6:27:55
 */
public class BarChart implements AChartAbstract {
	private double[] datas = { 43.1, 27.2, 43.4, 43.8, 41, 100, 9, 33, 66, 77, 88 };

	public Intent getIntent(Context context) {
		// GraphicalView mChartView = ChartFactory.getCombinedXYChartView(activity, dataset, renderer, types);
		// bar_chart_fl.addView(mChartView);
		// bar_chart_fl.destroyDrawingCache();
		// return ChartFactory.getBarChartIntent(context, getDataSet(), getRenderer(), Type.STACKED, "org.achartengine.GraphicalActivity");
		return ChartFactory.getBarChartIntent(context, getDataSet(), getRenderer(), Type.STACKED, "柱状图实例");
	}

	/**
	 * @description：构造数据
	 * @author samy
	 * @date 2014年9月9日 下午6:36:30
	 */
	public XYMultipleSeriesDataset getDataSet() {
		// 构造数据
		XYMultipleSeriesDataset barDataset = new XYMultipleSeriesDataset();
		// AppendData.getInstance().getDatas().add(i, chartArray[i].sellCount);//如配置显示不理想，可以配置修改Lib源码
		CategorySeries barSeries = new CategorySeries("商家被点餐分布");
		for (int i = 0; i < datas.length; i++) {
			barSeries.add(datas[i]);
		}
		barDataset.addSeries(barSeries.toXYSeries());
		return barDataset;
	}

	/**
	 * @description：构造渲染器
	 * @author samy
	 * @date 2014年9月9日 下午6:36:41
	 */
	public XYMultipleSeriesRenderer getRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setChartTitle("拼外卖情况");// getString(R.string.steps_x_date)
		renderer.setXTitle("商家");
		renderer.setYTitle("拼外卖人数(个)");
		renderer.setAxesColor(Color.WHITE);
		renderer.setLabelsColor(Color.WHITE);
		renderer.setAxisTitleTextSize(30);// XY轴标题字体大小
		renderer.setLabelsTextSize(25);// XY轴线字体大小
		renderer.setLegendTextSize(20);// 底部字体大小
		renderer.setShowAxes(true);
		renderer.setShowLabels(true);
		renderer.setShowLegend(true);// 隐藏底部；
		renderer.setMarginsColor(Color.RED);// 设置周围没表数据为白色
		renderer.setMargins(new int[] { 20, 50, 80, 50 });// 表格相对来说的-》上，左，下，右；
		renderer.setApplyBackgroundColor(true); // 设置背景颜色
		renderer.setBackgroundColor(Color.GRAY);
		renderer.setShowGrid(true);// 设置显示网格
		renderer.setGridColor(Color.LTGRAY);// 网格颜色
		renderer.setZoomButtonsVisible(false);// 设置渲染器显示缩放按钮
		renderer.setZoomEnabled(false, false);// 设置渲染器允许放大缩小
		// renderer.setZoomEnabled(false);
		renderer.setAntialiasing(true);// 消除锯齿
		renderer.setPanEnabled(true, false); // 允许左右拖动,但不允许上下拖动.
		// renderer.setClickEnabled(true);//设置了这个会失去拉动效果；可否拉动
		renderer.setInScroll(false);
		renderer.setXAxisMin(1);// 设置X轴的最小数字和最大数字，由于我们的数据是从1开始，所以设置为0.5就可以在1之前让出一部分
		// renderer.setXAxisMax(datas.length + 2);
		renderer.setXAxisMax(12 + 2);
		renderer.setYAxisMin(10);// 设置Y轴的最小数字和最大数字
		renderer.setYAxisMax(150);
		renderer.setXLabels(12); // X轴的近似坐标数；设置x轴标签数
		renderer.setYLabels(5);// Y轴的近似坐标数；150/50=5显示每刻度为5等分；
		// 设置x轴和y轴的标签对齐方式
		renderer.setXLabelsAlign(Align.LEFT);// 刻度线与X轴坐标文字左侧对齐
		renderer.setYLabelsAlign(Align.LEFT);// Y轴与Y轴坐标文字左对齐
		renderer.setBarSpacing(0.5f); // 柱子宽度
		renderer.setXLabelsAngle(300.0f);// 设置文字旋转角度 对文字顺时针旋转
		// renderer.setXLabelsPadding(10);//设置文字和轴的距离
		renderer.setFitLegend(true);// 调整合适的位置

		SimpleSeriesRenderer sr = new SimpleSeriesRenderer();
		sr.setColor(Color.YELLOW);// 设置每条柱子的颜色
		renderer.addSeriesRenderer(sr);

		// SimpleSeriesRenderer sr1 = new SimpleSeriesRenderer();
		// sr1.setColor(Color.GREEN);// 设置每条柱子的颜色
		// sr1.setStroke(BasicStroke.DOTTED);
		// renderer.addSeriesRenderer(sr);

		// 设置每个柱子上是否显示数值
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		renderer.setOrientation(Orientation.HORIZONTAL);

		for (int i = 0; i < datas.length; i++) {
			renderer.addXTextLabel(i + 2, "城市" + i);
		}

		// int[] colors = new int[] { Color.parseColor("#F4A100"), Color.parseColor("#F4A100") };
		// int length = colors.length;
		// for (int i = 0; i < length; i++) {
		// SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		// r.setColor(colors[i]);
		// renderer.addSeriesRenderer(r);
		// }
		// int length2 = renderer.getSeriesRendererCount();
		// for (int i = 0; i < length2; i++) {
		// SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
		// seriesRenderer.setDisplayChartValues(true);
		// }
		return renderer;
	}

}
