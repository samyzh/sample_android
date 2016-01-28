package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans;

/**
 * Created by rainy.liao on 2014/11/24.  睡眠质量
 */
public class SleepQualityInfo {
	final public static int SLEEP_QUALITY_SOBER = 0;// 清醒
	final public static int SLEEP_QUALITY_LIGHT = 1;// 浅睡
	final public static int SLEEP_QUALITY_DEEP = 2;// 深睡
	private int time;// 时间
	private int sleepQuality = SLEEP_QUALITY_SOBER;

	public SleepQualityInfo(int time, int sleepQuality) {
		this.time = time;
		this.sleepQuality = sleepQuality;
	}

	public int getTime() {
		return time;
	}

	public int getSleepQuality() {
		return sleepQuality;
	}

	public void setSleepQuality(int sleepQuality) {
		this.sleepQuality = sleepQuality;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
