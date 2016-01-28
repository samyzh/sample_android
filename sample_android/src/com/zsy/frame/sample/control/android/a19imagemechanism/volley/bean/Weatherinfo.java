package com.zsy.frame.sample.control.android.a19imagemechanism.volley.bean;

/**
 * @description：
 * {
	"weatherinfo": {
		"city": "北京",
		"cityid": "101010100",
		"temp": "9",
		"WD": "西南风",
		"WS": "2级",
		"SD": "22%",
		"WSE": "2",
		"time": "10:45",
		"isRadar": "1",
		"Radar": "JC_RADAR_AZ9010_JB",
		"njd": "暂无实况",
		"qy": "1014"
	}
}
 * @author samy
 * @date 2015-3-29 下午10:46:01
 */
public class Weatherinfo {
	public String city;
	public String cityid;
	public String temp;
	public String WD;

	@Override
	public String toString() {
		return "Weatherinfo [city=" + city + ", cityid=" + cityid + ", temp=" + temp + ", WD=" + WD + "]";
	}
}
