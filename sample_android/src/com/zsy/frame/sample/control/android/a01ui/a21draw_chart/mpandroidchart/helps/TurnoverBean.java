package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart.helps;

import java.io.Serializable;
/**交易额的bean*/
public class TurnoverBean implements Serializable{
	private static final long serialVersionUID = -4188058942676441896L;
	private double todayTurnover;//今日成交额
	private double cumulativeTurnover;//累计成交额 
	private double yesterdayTurnover;//昨日成交额
	private double thisMonthTurnover;//本月成交额
	public double getTodayTurnover() {
		return todayTurnover;
	}
	public void setTodayTurnover(double todayTurnover) {
		this.todayTurnover = todayTurnover;
	}
	public double getCumulativeTurnover() {
		return cumulativeTurnover;
	}
	public void setCumulativeTurnover(double cumulativeTurnover) {
		this.cumulativeTurnover = cumulativeTurnover;
	}
	public double getYesterdayTurnover() {
		return yesterdayTurnover;
	}
	public void setYesterdayTurnover(double yesterdayTurnover) {
		this.yesterdayTurnover = yesterdayTurnover;
	}
	public double getThisMonthTurnover() {
		return thisMonthTurnover;
	}
	public void setThisMonthTurnover(double thisMonthTurnover) {
		this.thisMonthTurnover = thisMonthTurnover;
	}
	@Override
	public String toString() {
		return "TurnoverBean [todayTurnover=" + todayTurnover + ", cumulativeTurnover=" + cumulativeTurnover + ", yesterdayTurnover=" + yesterdayTurnover + ", thisMonthTurnover=" + thisMonthTurnover + "]";
	}
	
}
