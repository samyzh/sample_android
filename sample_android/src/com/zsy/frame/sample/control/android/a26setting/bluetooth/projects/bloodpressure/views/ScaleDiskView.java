package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zsy.frame.sample.R;

/**
 * Created by rainy.liao on 2014/11/24. 血压检测，圆盘刻度view
 */
public class ScaleDiskView extends View {

	private int totalCopies = 80;// 总共分成多少份
	private int progress = 0; // 当前进度

	private double eachDegrees = 360.0 / totalCopies;// 每份对应的角度
	private Point[] points = new Point[totalCopies];// 存储圆盘上每个刻度对应的点的实际坐标
	private Point[] toPoints = new Point[totalCopies]; // 存储小圆盘上每个刻度对应的点的实际坐标

	public ScaleDiskView(Context context) {
		super(context);
	}

	public ScaleDiskView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScaleDiskView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 设置圆盘背景
		setBackgroundResource(R.drawable.a26setting_bluetooth_projects_bloodpressure_scale_bg);

		// 初始化画笔
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(getContext().getResources().getColor(R.color.white));
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(8);

		// 圆的半径
		int radius = getHeight() / 2;
		// 圆心坐标
		Point centerPoint = new Point(radius, radius);

		initPoint(points, centerPoint, radius - 20);
		initPoint(toPoints, centerPoint, (float) (radius * 0.82));

		drawProgress(canvas, paint, progress);

		// 画当前进度文字
		// int top=getHeight()/2-20;
		// Log.i("rainy","aboveTipText="+aboveTipText);
		// drawText(aboveTipText+"",100,getWidth()/2,top,canvas);
		// Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.divider);
		//
		// //画分割线
		// canvas.drawBitmap(bitmap,(getWidth()-bitmap.getWidth())/2,top+20,paint);
		// //画提示语
		// canvas.save();
		// // canvas.clipPath();
		// drawText(belowTipText+"",30,getWidth()/2,top+70,canvas);
		// canvas.restore();

	}

	/**
	 * 画文字
	 * @param text 文字内容
	 * @param x x坐标
	 * @param y y坐标
	 * @param canvas 画布
	 */
	protected void drawText(String text, float textSize, float x, float y, Canvas canvas) {
		// Paint p = new Paint();
		// p.setTextSize(textSize);
		// p.setColor(getResources().getColor(R.color.white));
		// p.setTextAlign(Paint.Align.CENTER);
		// canvas.drawText(text, x, y, p);

		TextPaint textPaint = new TextPaint();
		textPaint.setColor(getResources().getColor(R.color.white));
		textPaint.setTextSize(textSize);
		float textWidth = textPaint.measureText(text);

		StaticLayout layout = new StaticLayout(text, textPaint, 240, Layout.Alignment.ALIGN_OPPOSITE, 1.0F, 0.0F, true);
		// 从 (20,80)的位置开始绘制
		canvas.translate(x - textWidth / 2, y);
		layout.draw(canvas);
		canvas.translate(-(x - textWidth / 2), y);
	}

	private void drawProgress(Canvas canvas, Paint paint, int progress) {
		int count = progress * totalCopies / 100;
		for (int i = 0; i < count; i++) {
			canvas.drawLine(points[i].x, points[i].y, toPoints[i].x, toPoints[i].y, paint);
		}
	}

	private void initPoint(Point[] points, Point centerPoint, float radius) {
		for (int i = 0; i < totalCopies; i++) {
			int x = (int) (centerPoint.x + Math.sin(2 * i * eachDegrees * Math.PI / 360) * radius);
			int y = (int) (centerPoint.y + Math.cos(2 * i * eachDegrees * Math.PI / 360) * radius);
			points[i] = new Point(x, y);
		}
	}

	/**
	 * 设置圆盘上的刻度总共有多少个
	 * @param totalCopies
	 */
	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	/**
	 * 设置进度百分比，如20%，请传入20
	 * @param progrees
	 */
	public void setProgrees(int progrees) {
		this.progress = progrees;
	}
}
