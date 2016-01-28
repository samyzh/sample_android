package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.achartengine;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * @description：饼图
 * @author samy
 * @date 2014年9月9日 下午6:27:36
 */
public class PieChart implements AChartAbstract {

	public Intent getIntent(Context context) {
		return ChartFactory.getPieChartIntent(context, getDataSet(), getPieRenderer(), "饼图实例");
	}

	/**
	 * 构造饼图数据
	 */
	private CategorySeries getDataSet() {
		// 构造数据
		CategorySeries pieSeries = new CategorySeries("手机开发者比例");
		pieSeries.add("Android", 28);
		pieSeries.add("IOS", 46);
		pieSeries.add("其他", 26);
		return pieSeries;
	}

	/**
	 * 获取一个饼图渲染器
	 */
	private DefaultRenderer getPieRenderer() {
		// 构造一个渲染器
		DefaultRenderer renderer = new DefaultRenderer();
		// 设置渲染器显示缩放按钮
		renderer.setZoomButtonsVisible(true);
		// 设置渲染器允许放大缩小
		renderer.setZoomEnabled(true);
		// 设置渲染器标题文字大小
		renderer.setChartTitleTextSize(20);
		// 给渲染器增加3种颜色
		SimpleSeriesRenderer yellowRenderer = new SimpleSeriesRenderer();
		yellowRenderer.setColor(Color.YELLOW);
		SimpleSeriesRenderer blueRenderer = new SimpleSeriesRenderer();
		blueRenderer.setColor(Color.BLUE);
		SimpleSeriesRenderer redRenderer = new SimpleSeriesRenderer();
		redRenderer.setColor(Color.RED);
		renderer.addSeriesRenderer(yellowRenderer);
		renderer.addSeriesRenderer(blueRenderer);
		renderer.addSeriesRenderer(redRenderer);
		// 设置饼图文字字体大小和饼图标签字体大小
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		// 消除锯齿
		renderer.setAntialiasing(true);
		// 设置背景颜色
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK);
		// 设置线条颜色
		renderer.setAxesColor(Color.WHITE);

		return renderer;
	}
}
