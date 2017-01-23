package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {
	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		int xPoint = 200;
		int yPoint = 400;
		// Y轴
		canvas.drawLine(xPoint, 400 - 200, xPoint, yPoint, p);
		// X轴
		canvas.drawLine(xPoint, yPoint, xPoint + 300, yPoint, p);

		int xStart1 = xPoint;
		int yStar1 = yPoint;
		int xEnd1 = xPoint + 50;
		int yEnd1 = yPoint - 100;
		canvas.drawLine(xStart1, yStar1, xEnd1, yEnd1, p);
		int xStart2 = xEnd1;
		int yStar2 = yEnd1;
		int xEnd2 = xEnd1 + 50;
		int yEnd2 = yEnd1 + 50;
		canvas.drawLine(xStart2, yStar2, xEnd2, yEnd2, p);
		int xStart3 = xEnd2;
		int yStar3 = yEnd2;
		int xEnd3 = xEnd2 + 50;
		int yEnd3 = yEnd2 - 120;
		canvas.drawLine(xStart3, yStar3, xEnd3, yEnd3, p);
		int xStart4 = xEnd3;
		int yStar4 = yEnd3;
		int xEnd4 = xEnd3 + 50;
		int yEnd4 = yEnd3 + 150;
		canvas.drawLine(xStart4, yStar4, xEnd4, yEnd4, p);
	}
}
