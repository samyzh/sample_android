package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.CurveInfo;

/**
 * 曲线图  rainy 2014.11.17
 */
@SuppressLint("ViewConstructor")
public class CurveGraphView extends View implements View.OnTouchListener {
	public static final int RECT_SIZE = 10;// 点的直径

	// 枚举两点之间连线的风格
	public static enum LineStyle {
		Line, Curve
	}

	protected LineStyle mstyle = LineStyle.Line;

	protected Context context;
	protected ArrayList<CurveInfo> curveInfos;// 曲线信息

	protected int bgColor = 0; // 背景图颜色
	protected int bgResid = 0; // 背景图的资源id
	protected boolean isShowLineOfY; // 是否显示Y轴上的网线
	private boolean isImaginaryLine = false;// 网格线是否使用虚线

	protected int totalValueOfY = 30; // Y轴上的最大值
	protected int intervalOfY = 5; // Y轴上每一个数据的间距
	protected String[] xStr; // 横纵坐标的属性
	protected String[] yStr;// 横纵坐标的属性
	protected int marginTop = 20; // 距离当前画布顶部的间隔
	protected int marginBottom = 40;// 距离当前画布底部的间隔

	protected int yOrderCount;// Y轴显示的条数
	protected int xOrderCount;// x轴显示的条数（一屏显示的最多个数）
	protected int xOrderTotalCount;// x轴上显示的总个数，如果该值<=xOrderCount,则表示只有一屏，否则表示需要滑动显示
	private int yStartValue = 0;// y轴起始显示的数，最好是y轴间隔的倍数，默认从0开始
	protected int intervalBgColor = 0;// 间隔背景颜色，如有设置，则间隔设置
	protected ArrayList<Integer> landscapeLinesIndex;// 存放需要画指定的横向网格线的index，如全部需画，请传null,如全部不需画，传new ArrayList<Integer>()即可
	protected boolean isShowYOrderNum = true;// 是否显示Y轴坐标值 默认为true
	protected boolean isXOrderUnitOnlyShowStartEnd = false;// x轴坐标值是否只显示起始和结束的，中间采用直线连接
	protected boolean ignoreLessThanYStartValueData = false;// 忽略小于起始点的数值

	// 以下无需变量无需设置，由程序计算
	protected int bheight = 0;
	protected float xIntervalValue; // X轴间隔
	protected int highestYPosition;// 显示最高的点所对应的y值
	protected boolean needScroll;// 是否需要滑动
	protected float maxScrollX = 0;// 可滑动的最大值
	protected ArrayList<Float> xList = new ArrayList<Float>();// 记录每个x的值（在屏幕上显示的坐标）
	protected PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);// 画虚线
	protected Paint paint = new Paint();// 画笔
	protected int paddingLeft = 50;

	public CurveGraphView(Context context) {
		super(context);
		this.context = context;
	}

	public CurveGraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public CurveGraphView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	/**
	* @param curveInfos 需要的数据，虽然key是double，但是只用于排序和显示，与横向距离无关
	 *@param xOrderCount x轴上显示的条数 （一屏）
	 *@param xOrderTotalCount  x轴上显示的总个数，如果该值<=xOrderCount,则表示只有一屏，否则表示需要滑动显示
	* @param totalValueOfY Y轴的最大值
	* @param intervalOfY Y平均值
	* @param xStr X轴的单位 如果单位相同，只需要传一个
	* @param yStr Y轴的单位 如果单位相同，只需要传一个
	* @param isShowLineOfY 是否显示纵向网格
	 * @return 
	*/
	public void setCurveGraphViewData(ArrayList<CurveInfo> curveInfos, int xOrderCount, int xOrderTotalCount, int totalValueOfY, int intervalOfY, String[] xStr, String[] yStr, boolean isShowLineOfY) {
		this.curveInfos = curveInfos;
		this.xOrderCount = xOrderCount;
		this.totalValueOfY = totalValueOfY;
		this.intervalOfY = intervalOfY;
		this.xStr = xStr;
		this.yStr = yStr;
		this.isShowLineOfY = isShowLineOfY;
		setxOrderTotalCount(xOrderTotalCount);
	}

	public void setxOrderTotalCount(int xOrderTotalCount) {
		this.xOrderTotalCount = xOrderTotalCount;
		// 判断是否需要滑动显示
		if (this.xOrderTotalCount > this.xOrderCount) {
			needScroll = true;
			setOnTouchListener(this);
		}
		else {
			this.xOrderTotalCount = this.xOrderCount;
			needScroll = false;
		}
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		// 画已设置的背景颜色或者背景图片
		if (getBgColor() != 0) {
			this.setbgColor(getBgColor());
		}
		if (bgResid != 0) {
			this.setBackgroundResource(bgResid);
		}

		// 计算相关值
		int height = getHeight();
		if (bheight == 0) {
			bheight = height - marginBottom;
		}
		int width = getWidth() + 4;
		yOrderCount = (totalValueOfY - yStartValue) / intervalOfY;// 界面布局的尺寸的比例,Y轴
		xIntervalValue = ((float) width - paddingLeft) / (xOrderCount + 1);
		maxScrollX = (xOrderTotalCount - xOrderCount) * xIntervalValue;

		drawByMyself(canvas, paint);
	}

	/**
	 * 根据定制的信息，画出曲线，如果该类定制信息不能满足需求，请继承该类后，重写该方法即可
	 * @param canvas
	 * @param paint
	 */
	protected void drawByMyself(Canvas canvas, Paint paint) {
		drawIntervalBgColor(canvas, paint);
		drawXLines(canvas, paint);
		// 画x轴坐标线及标注值
		drawXYOrderLine(canvas, paint);
		drawDataLine(canvas, paint);
	}

	/**
	 * 如有设置间隔背景颜色，就间隔填充背景条
	 * @param canvas
	 * @param paint
	 */
	protected void drawIntervalBgColor(Canvas canvas, Paint paint) {
		if (intervalBgColor != 0) {
			canvas.save();
			canvas.clipRect(new Rect(paddingLeft, marginTop, getWidth(), bheight + marginTop + 48));
			// 画间隔的背景条
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(intervalBgColor);
			for (int i = 0; i < xOrderTotalCount + 1; i++) {
				if (i % 2 == 0) {
					canvas.drawRect((xIntervalValue * (i - 1)) + offsetX + paddingLeft, marginTop, xIntervalValue * (i) + offsetX + paddingLeft, bheight + marginTop, paint);
				}
			}
			canvas.restore();
		}
	}

	protected void drawDataLine(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(new Rect(paddingLeft, marginTop, getWidth(), bheight + marginTop + 48));
		paint.setPathEffect(null);
		if (curveInfos != null) {
			for (int i = 0; i < curveInfos.size(); i++) {
				CurveInfo curveInfo = curveInfos.get(i);
				curveInfo.xDatas = getXDataFromMapByOrder(curveInfo.getMap());
				if (curveInfo.xDatas == null || curveInfo.xDatas.size() <= 0) {
					continue;
				}
				// 点的操作设置
				curveInfo.mPoints = getPoints(curveInfo.xDatas, curveInfo.getMap(), xList, totalValueOfY, bheight);
				drawEachCurve(curveInfo, canvas, paint);
			}
		}
		canvas.restore();
	}

	/**
	 * 画横向网格线及Y轴坐标值
	 * @param canvas
	 * @param paint
	 */
	protected void drawXLines(Canvas canvas, Paint paint) {
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.STROKE);
		for (int i = 0; i < yOrderCount + 1; i++) {
			paint.setColor(context.getResources().getColor(R.color.curve_line));
			int y = bheight - (bheight / yOrderCount) * i + marginTop;
			if (i == 0) {
				drawLine(canvas, false, effects, paddingLeft, y, getWidth(), y, paint);
			}
			if (landscapeLinesIndex == null) {
				drawLine(canvas, isImaginaryLine(), effects, paddingLeft, y, getWidth(), y, paint);
			}
			else if (landscapeLinesIndex.contains(new Integer(i))) {
				drawLine(canvas, isImaginaryLine(), effects, paddingLeft, y, getWidth(), y, paint);
			}
			if (isShowYOrderNum) {
				String yUnit = "";
				if (yStr != null) {
					yUnit = yStr[i % yStr.length];
				}
				paint.setColor(context.getResources().getColor(R.color.blood_pressure_unit));
				drawText(intervalOfY * (i) + getyStartValue() + yUnit, (paddingLeft) / 2, bheight - (bheight / yOrderCount) * i + marginTop, canvas);
			}
		}
	}

	/**
	 * 画XY轴坐标线及纵向网格线、X轴坐标值
	 * @param canvas
	 * @param paint
	 */
	protected void drawXYOrderLine(Canvas canvas, Paint paint) {
		// 画Y轴坐标线（即最左边那条线）
		canvas.save();
		canvas.clipRect(new Rect(paddingLeft, marginTop, getWidth(), bheight + marginTop + 48));
		paint.setColor(context.getResources().getColor(R.color.curve_line));
		canvas.drawLine(paddingLeft, marginTop, paddingLeft, bheight + marginTop, paint);
		xList.clear();
		for (int i = 0; i < xOrderTotalCount; i++) {
			float x = xIntervalValue * (i + 1) + offsetX + paddingLeft;
			xList.add(x);
			if (isShowLineOfY) {
				drawLine(canvas, isImaginaryLine(), effects, x, marginTop, x, bheight + marginTop, paint);
			}
			String xUnit = "";
			if (xStr != null && xStr.length > 0) {
				xUnit = xStr[i % xStr.length];
			}
			if (isXOrderUnitOnlyShowStartEnd) {
				if (i == 0 || i == xOrderTotalCount - 1) {
					drawText(xUnit, x, bheight + 48, canvas);// X坐标
				}
			}
			else {
				drawText(xUnit, x, bheight + 48, canvas);// X坐标
			}
		}
		if (isXOrderUnitOnlyShowStartEnd) {
			float lineY = bheight + marginTop + 20;
			canvas.drawLine(xIntervalValue * (2) + offsetX + paddingLeft, lineY, xIntervalValue * (xOrderTotalCount - 1) + offsetX + paddingLeft, lineY, paint);
		}
		canvas.restore();
	}

	/**
	 * 画圆弧跟X轴之间的区域
	 * @param canvas
	 * @param paint
	 */
	protected void drawCurveRect(Canvas canvas, Paint paint, Point[] mPoints) {
		Path path = new Path();
		if (mPoints.length > 0) {
			path.moveTo(mPoints[0].x, bheight + 20);
			path.lineTo(mPoints[0].x, mPoints[0].y);
		}

		for (int i = 0; i < mPoints.length - 1; i++) {
			Point startp = mPoints[i];
			Point endp = mPoints[i + 1];
			int wt = (startp.x + endp.x) / 2;
			Point p3 = new Point();
			Point p4 = new Point();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
		}
		path.lineTo(mPoints[mPoints.length - 1].x, bheight + 20);
		path.close();

		canvas.drawPath(path, paint);
	}

	/**
	 * 画多边形
	 * @param canvas
	 */
	public void drawPolygon(Canvas canvas, Paint paint, Point[] mPoints) {
		Path path = new Path();
		if (mPoints.length > 0) {
			path.moveTo(mPoints[0].x, bheight + 20);
		}

		for (int i = 0; i < mPoints.length; i++) {
			path.lineTo(mPoints[i].x, mPoints[i].y);
		}
		path.lineTo(mPoints[mPoints.length - 1].x, bheight + 20);
		path.close();
		canvas.drawPath(path, paint);
	}

	/**
	 * 画点之间的曲线
	 * @param points
	 * @param canvas
	 * @param paint
	 */
	protected void drawCurveBetweenPoints(Point[] points, Canvas canvas, Paint paint) {
		Point startp, endp;
		for (int i = 0; i < points.length - 1; i++) {
			startp = points[i];
			endp = points[i + 1];
			int wt = (startp.x + endp.x) / 2;
			Point p3 = new Point();
			Point p4 = new Point();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			Path path = new Path();
			path.moveTo(startp.x, startp.y);
			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			canvas.drawPath(path, paint);
		}
	}

	/**
	 * 画点之间的直线
	 * @param points
	 * @param canvas
	 * @param paint
	 */
	protected void drawLineBetweenPoints(Point[] points, Canvas canvas, Paint paint) {
		Point startp, endp;
		for (int i = 0; i < points.length - 1; i++) {
			startp = points[i];
			endp = points[i + 1];
			canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
		}
	}

	protected Point[] getPoints(ArrayList<Double> dlk, HashMap<Double, Double> map, ArrayList<Float> xlist, int max, int h) {
		Point[] points = new Point[dlk.size()];
		ArrayList<Point> pointsList = new ArrayList<Point>();
		for (int i = 0; i < dlk.size(); i++) {
			double yData = map.get(dlk.get(i));

			int ph = h - (int) (h * yData / max);
			int y = ph + marginTop + yStartValue;
			if (highestYPosition < y) {
				highestYPosition = y;
			}
			points[i] = new Point((int) xlist.get(i).floatValue(), y);
			if (yData > yStartValue) {
				pointsList.add(points[i]);
			}
		}
		// 去掉不用画的点
		if (ignoreLessThanYStartValueData) {
			Point[] newPoints = new Point[pointsList.size() + 2];
			newPoints[0] = new Point((int) xlist.get(0).floatValue(), marginTop + h);
			newPoints[pointsList.size() + 1] = new Point((int) xlist.get(xlist.size() - 1).floatValue(), marginTop + h);
			for (int i = 0; i < pointsList.size(); i++) {
				newPoints[i + 1] = pointsList.get(i);
			}
			return newPoints;
		}
		return points;
	}

	/**
	 * 画文字
	 * @param text 文字内容
	 * @param x x坐标
	 * @param y y坐标
	 * @param canvas 画布
	 */
	protected void drawText(String text, float x, float y, Canvas canvas) {
		Paint p = new Paint();
		p.setTextSize(18);
		// String familyName = "宋体";
		// Typeface font = Typeface.create(familyName, Typeface.ITALIC);
		// p.setTypeface(font);
		p.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(text, x, y, p);
	}

	/**
	 * 将x轴上的值从Map中读取出来，并排好序
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Double> getXDataFromMapByOrder(HashMap<Double, Double> map) {
		ArrayList<Double> result = new ArrayList<Double>();
		int position = 0;
		if (map == null) return null;
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		int index = 0;
		// 已设置x轴的最多个数，如果传入的数值个数超过，则后续的丢弃不要
		while (iterator.hasNext() && index < xOrderTotalCount) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			result.add((Double) mapEntry.getKey());
			index++;
		}
		// 冒泡排序 本项目中暂时用不上
		// for(int i=0;i<result.size();i++){
		// int j=i+1;
		// position=i;
		// Double temp=result.get(i);
		// for(;j<result.size();j++){
		// if(result.get(j)<temp){
		// temp=result.get(j);
		// position=j;
		// }
		// }
		// result.set(position,result.get(i));
		// result.set(i,temp);
		// }
		return result;
	}

	protected void drawEachCurve(CurveInfo curveInfo, Canvas canvas, Paint paint) {
		paint.setColor(curveInfo.getLineColor());
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);

		// 根据不同的风格将点连起来
		if (mstyle == LineStyle.Curve) {
			drawCurveBetweenPoints(curveInfo.mPoints, canvas, paint);
		}
		else {
			drawLineBetweenPoints(curveInfo.mPoints, canvas, paint);
		}

		// 将每个数值单独画
		paint.setColor(curveInfo.getPointsColor());
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);
		for (int i = 0; i < curveInfo.mPoints.length; i++) {
			canvas.drawCircle(curveInfo.mPoints[i].x, curveInfo.mPoints[i].y, RECT_SIZE, paint);
		}

		// 将连线与x轴之间的区域用渐变色填充
		if (curveInfo.getLinearGradientColors() != null) {
			/* 设置渐变色 0xffae73c5 */
			Shader mShader = new LinearGradient(getWidth() / 2, highestYPosition, getWidth() / 2, bheight, curveInfo.getLinearGradientColors(), null, Shader.TileMode.CLAMP); // 一个材质,打造出一个线性梯度沿著一条线。
			paint.setShader(mShader);
			paint.setStyle(Paint.Style.FILL);
			paint.setAlpha(150);
			if (mstyle == LineStyle.Line) {
				drawPolygon(canvas, paint, curveInfo.mPoints);
			}
			else {
				drawCurveRect(canvas, paint, curveInfo.mPoints);
			}
		}

		// 将圆点填充白色，否则可以看到内部的连线
		paint.setShader(null);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		for (int i = 0; i < curveInfo.mPoints.length; i++) {
			canvas.drawCircle(curveInfo.mPoints[i].x, curveInfo.mPoints[i].y, RECT_SIZE - 1, paint);
		}
	}

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 画线
	 * @param canvas
	 * @param isImaginaryLine 是否画虚线
	 * @param effects  虚线效果
	 * @param startX
	 * @param startY
	 * @param stopX
	 * @param stopY
	 * @param paint
	 */
	public void drawLine(Canvas canvas, boolean isImaginaryLine, PathEffect effects, float startX, float startY, float stopX, float stopY, Paint paint) {
		// 采用虚线时，只能使用path才能画出来，圆可以直接画虚线圆
		if (isImaginaryLine) {
			Path path = new Path();
			path.moveTo(startX, startY);
			path.lineTo(stopX, stopY);
			paint.setPathEffect(effects);
			canvas.drawPath(path, paint);
		}
		else {
			paint.setPathEffect(null);
			canvas.drawLine(startX, startY, stopX, stopY, paint);
		}
	}

	private float beginX = 0;// 按下时点的x位置
	protected float offsetX = 0; // 偏移值
	private float lastOffsetX = 0;// 上一次的偏移值

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				beginX = event.getX();
				return true;
			case MotionEvent.ACTION_UP:
				if (needScroll) {
					lastOffsetX = offsetX;
					return true;
				}
				return false;
			case MotionEvent.ACTION_MOVE:
				if (needScroll) {
					float move = lastOffsetX + event.getX() - beginX;
					if (move > 0) {
						move = 0;
					}
					if (move < 0 && (-move) >= maxScrollX) {
						move = -maxScrollX;
					}
					offsetX = move;
					this.invalidate();
					return true;
				}
				return false;
		}
		return true;
	}

	/**
	 * 设置背景颜色
	 * @param c
	 */
	public void setbgColor(int c) {
		this.setBackgroundColor(c);
	}

	public int getTotalValueOfY() {
		return totalValueOfY;
	}

	/**
	 * 设置Y轴最大值
	 * @param totalValueOfY
	 */
	public void setTotalValueOfY(int totalValueOfY) {
		this.totalValueOfY = totalValueOfY;
	}

	public int getIntervalOfY() {
		return intervalOfY;
	}

	/**
	 * 设置Y轴的间隔值
	 * @param intervalOfY
	 */
	public void setIntervalOfY(int intervalOfY) {
		this.intervalOfY = intervalOfY;
	}

	public String[] getxStr() {
		return xStr;
	}

	public String[] getyStr() {
		return yStr;
	}

	public void setxStr(String[] xStr) {
		this.xStr = xStr;
	}

	public void setyStr(String[] yStr) {
		this.yStr = yStr;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public boolean getIsShowLineOfY() {
		return isShowLineOfY;
	}

	/**
	 * 设置是否显示Y轴网格线
	 * @param isShowLineOfY
	 */
	public void setIsShowLineOfY(boolean isShowLineOfY) {
		this.isShowLineOfY = isShowLineOfY;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public LineStyle getMstyle() {
		return mstyle;
	}

	/**
	 * 设置连线风格
	 * @param mstyle
	 */
	public void setMstyle(LineStyle mstyle) {
		this.mstyle = mstyle;
	}

	public int getBheight() {
		return bheight;
	}

	public void setBheight(int bheight) {
		this.bheight = bheight;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public int getBgResid() {
		return bgResid;
	}

	public void setBgResid(int bgResid) {
		this.bgResid = bgResid;
	}

	public boolean isImaginaryLine() {
		return isImaginaryLine;
	}

	/**
	 * 设置是否采用虚线网格线
	 * @param isImaginaryLine
	 */
	public void setImaginaryLine(boolean isImaginaryLine) {
		this.isImaginaryLine = isImaginaryLine;
	}

	public int getyStartValue() {
		return yStartValue;
	}

	public void setyStartValue(int yStartValue) {
		this.yStartValue = yStartValue;
	}

	public void setIntervalBgColor(int intervalBgColor) {
		this.intervalBgColor = intervalBgColor;
	}

	/**
	 * 存放需要画指定的横向网格线的index，如全部需画，请传null,如全部不需画，传new ArrayList<Integer>()即可
	 * @param landscapeLinesIndex
	 */
	public void setLandscapeLinesIndex(ArrayList<Integer> landscapeLinesIndex) {
		this.landscapeLinesIndex = landscapeLinesIndex;
	}

	/**
	 * 设置是否显示Y轴数值，默认为显示
	 * @param isShowYOrderNum
	 */
	public void setShowYOrderNum(boolean isShowYOrderNum) {
		this.isShowYOrderNum = isShowYOrderNum;
	}

	/**
	 * 重置并刷新
	 */
	public void reset() {
		offsetX = 0;
		invalidate();
	}

	public void setXOrderUnitOnlyShowStartEnd(boolean isXOrderUnitOnlyShowStartEnd) {
		this.isXOrderUnitOnlyShowStartEnd = isXOrderUnitOnlyShowStartEnd;
	}

	/**
	 * 是否忽略小于起始点的数值，默认为false  如果为true，小于等于y轴最小值的点将不被画出
	 * @param ignoreLessThanYStartValueData
	 */
	public void setIgnoreLessThanYStartValueData(boolean ignoreLessThanYStartValueData) {
		this.ignoreLessThanYStartValueData = ignoreLessThanYStartValueData;
	}
}
