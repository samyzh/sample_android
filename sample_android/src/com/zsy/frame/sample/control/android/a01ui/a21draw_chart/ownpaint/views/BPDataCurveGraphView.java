package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.CurveInfo;

/**
 * Created by rainy.liao on 2014/12/2. 血压值曲线图
 */
public class BPDataCurveGraphView extends CurveGraphView {
	ArrayList<DataTextInfo> dataTextInfos;

	public BPDataCurveGraphView(Context context) {
		super(context);
	}

	public BPDataCurveGraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BPDataCurveGraphView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawDataText(canvas);
	}

	/**
	 * 画数值 (两条线的差值)
	 * @param canvas
	 */
	public void drawDataText(Canvas canvas) {
		getDataTextInfos();
		canvas.save();
		canvas.clipRect(new Rect(paddingLeft, marginTop, getWidth(), bheight + marginTop + 48));
		for (int i = 0; i < dataTextInfos.size(); i++) {
			DataTextInfo dataTextInfo = dataTextInfos.get(i);
			drawText(dataTextInfo.text, dataTextInfo.point.x, dataTextInfo.point.y, canvas);
		}
		canvas.restore();
	}

	public void getDataTextInfos() {
		dataTextInfos = new ArrayList<DataTextInfo>();
		if (curveInfos.size() <= 1) { return; }
		CurveInfo curveInfo1 = curveInfos.get(0);
		CurveInfo curveInfo2 = curveInfos.get(1);
		Point[] points1 = curveInfo1.getmPoints();
		Point[] points2 = curveInfo2.getmPoints();
		for (int i = 0; i < xList.size(); i++) {
			DataTextInfo dataTextInfo = new DataTextInfo();
			dataTextInfo.point = new Point(points1[i].x, (points1[i].y + points2[i].y) / 2);
			double y = curveInfo1.getMap().get(curveInfo1.xDatas.get(i)) - curveInfo2.getMap().get(curveInfo2.xDatas.get(i));
			if (y < 0) {
				y = -y;
			}
			dataTextInfo.text = (int) y;
			dataTextInfos.add(dataTextInfo);
		}
	}

	/**
	 * 画文字
	 * @param text 文字内容
	 * @param x x坐标
	 * @param y y坐标
	 * @param canvas 画布
	 */
	protected void drawText(int text, float x, float y, Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		float textSize = (float) 18.0;
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);
		float width = paint.measureText(text + "");
		float height = textSize + 2;
		RectF rectF = new RectF(x - (float) (width * 0.8), y - height, x + (float) (width * 0.8), y + 6);
		// 画圆角矩形
		float rx = 5;
		float ry = 5;

		if (text <= 50) {
			paint.setColor(getResources().getColor(R.color.white));
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRoundRect(rectF, rx, ry, paint);
			paint.setColor(getResources().getColor(R.color.diastolic_pressure_color));
		}
		else if (text <= 100) {
			paint.setColor(getResources().getColor(R.color.white));
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRoundRect(rectF, rx, ry, paint);
			paint.setColor(getResources().getColor(R.color.systolic_pressure_color));
		}
		else {
			paint.setColor(getResources().getColor(R.color.target_steps_color));
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRoundRect(rectF, rx, ry, paint);
			paint.setColor(getResources().getColor(R.color.white));
		}

		canvas.drawText(text + "", x, y, paint);
	}

	class DataTextInfo {
		Point point;// 显示位置
		int text; // 显示的文本
	}
}
