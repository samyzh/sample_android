package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rainy.liao on 2014/11/18. 曲线信息 后续再将该类去项目中相关的类合并
 */
public class CurveInfo {
	private HashMap<Double, Double> map;
	private int pointsColor;
	private int lineColor;
	public ArrayList<Double> xDatas;// 存储所有原始点X轴上的的数据，排好序的，如有重复的值设置，后面的值会覆盖前面的
	public Point[] mPoints = new Point[100];
	private int linearGradientColors[]; // 填充的线下渐变颜色

	public CurveInfo(HashMap<Double, Double> map, int pointsColor, int lineColor) {
		this.map = map;
		this.pointsColor = pointsColor;
		this.lineColor = lineColor;
	}

	public void setMap(HashMap<Double, Double> map) {
		this.map = map;
	}

	public void setPointsColor(int pointsColor) {
		this.pointsColor = pointsColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public void setxDatas(ArrayList<Double> xDatas) {
		this.xDatas = xDatas;
	}

	public void setmPoints(Point[] mPoints) {
		this.mPoints = mPoints;
	}

	public HashMap<Double, Double> getMap() {
		return map;
	}

	public int getPointsColor() {
		return pointsColor;
	}

	public int getLineColor() {
		return lineColor;
	}

	public ArrayList<Double> getxDatas() {
		return xDatas;
	}

	public Point[] getmPoints() {
		return mPoints;
	}

	public void setLinearGradientColors(int[] linearGradientColors) {
		this.linearGradientColors = linearGradientColors;
	}

	public int[] getLinearGradientColors() {
		return linearGradientColors;
	}
}
