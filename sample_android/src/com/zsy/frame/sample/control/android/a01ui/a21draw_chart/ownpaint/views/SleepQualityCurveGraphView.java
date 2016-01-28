package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.SleepQualityInfo;

/**
 * Created by rainy.liao on 2014/11/20.  睡眠质量曲线图
 */
public class SleepQualityCurveGraphView extends CurveGraphView {

	private ArrayList<SleepQualityInfo> sleepQualityInfos;

	public SleepQualityCurveGraphView(Context ct) {
		super(ct);
	}

	public SleepQualityCurveGraphView(Context ct, AttributeSet attrs) {
		super(ct, attrs);
	}

	public SleepQualityCurveGraphView(Context ct, AttributeSet attrs, int defStyle) {
		super(ct, attrs, defStyle);
	}

	/**
	 *
	 * @param sleepQualityInfos
	 * @param xOrderCount
	 * @param xOrderTotalCount
	 * @param xStr
	 */
	public void setCurveGraphViewData(ArrayList<SleepQualityInfo> sleepQualityInfos, int xOrderCount, int xOrderTotalCount, String[] xStr) {
		isShowLineOfY = true;
		this.sleepQualityInfos = sleepQualityInfos;
		this.xOrderCount = xOrderCount;
		this.xStr = xStr;
		setxOrderTotalCount(xOrderTotalCount);
	}

	protected void drawByMyself(Canvas canvas, Paint paint) {
		if (sleepQualityInfos != null) {
			for (int i = 0; i < sleepQualityInfos.size(); i++) {
				SleepQualityInfo info = sleepQualityInfos.get(i);
				// 清醒状态不用画
				int color = 0xfffffff;
				if (info.getSleepQuality() == SleepQualityInfo.SLEEP_QUALITY_LIGHT) {
					color = context.getResources().getColor(R.color.sleep_light_color);
				}
				else if (info.getSleepQuality() == SleepQualityInfo.SLEEP_QUALITY_DEEP) {
					color = context.getResources().getColor(R.color.sleep_deep_color);
				}
				paint.setColor(color);
				paint.setStyle(Paint.Style.FILL);
				if (info.getTime() <= xOrderTotalCount && info.getTime() >= 0) {
					canvas.drawRect(xIntervalValue * info.getTime() + offsetX + paddingLeft, marginTop + 40, xIntervalValue * (info.getTime() + 1) + offsetX + paddingLeft, bheight + marginTop, paint);
				}
			}
		}

		drawXLines(canvas, paint);
		// 画x轴坐标线及标注值
		drawXYOrderLine(canvas, paint);
	}

	protected String startUnit;
	protected String endUnit;

	/**
	 * 设置x轴上坐标值的起始和结束串，中间就采用直线连接
	 * @param startUnit
	 * @param endUnit
	 */
	public void setXOrderUnit(String startUnit, String endUnit) {
		this.startUnit = startUnit;
		this.endUnit = endUnit;
	}

	private void drawXOrderUnit(Canvas canvas, Paint paint) {
		float startX = xIntervalValue * (2) + offsetX;// i+2是让x轴从第二个点开始
		drawText(startUnit + "", startX, bheight + 48, canvas);// X坐标
		float endX = xIntervalValue * (xOrderTotalCount + 1) + offsetX;
		drawText(endUnit + "", endX, bheight + 48, canvas);// X坐标
		canvas.drawLine(startX, bheight + 48, endX, bheight + 48, paint);
	}

}
