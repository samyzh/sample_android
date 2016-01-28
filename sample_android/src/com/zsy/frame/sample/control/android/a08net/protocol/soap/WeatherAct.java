package com.zsy.frame.sample.control.android.a08net.protocol.soap;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.ProgressDialogUtils;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.WebServiceUtils;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.WebServiceUtils.WebServiceCallBack;

/**
 * @description：显示天气的Activity
 * @author samy
 * @date 2014年8月3日 下午1:12:32
 */
public class WeatherAct extends BaseAct {
	public WeatherAct() {
		super();
		
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		init();
	}

	private void init() {
		final TextView mTextWeather = (TextView) findViewById(R.id.weather);
		ProgressDialogUtils.showProgressDialog(this, "数据加载中...");
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("theCityName", getIntent().getStringExtra("city"));
		WebServiceUtils.callWebService(WebServiceUtils.serviceURL, WebServiceUtils.getWeatherbyCityName, properties, new WebServiceCallBack() {
			@Override
			public void callBack(SoapObject result) {
				ProgressDialogUtils.dismissProgressDialog();
				if (result != null) {
					// 直接放到一起解析再显示数据
					SoapObject detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < detail.getPropertyCount(); i++) {
						sb.append(detail.getProperty(i)).append("\r\n");
					}
					mTextWeather.setText(sb.toString());
				}
				else {
					Toast.makeText(WeatherAct.this, "获取WebService数据错误", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a08net_protocol_soap_weather);
	}

	// /**
	// * @description：解析返回的结果
	// * @author samy
	// * @date 2014年8月3日 下午2:07:59
	// */
	// protected WeatherBean parserWeather(SoapObject soapObject) {
	// WeatherBean bean = new WeatherBean();
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// // 城市名
	// bean.setCityName(soapObject.getProperty(1).toString());
	// // 城市简介
	// bean.setCityDescription(soapObject.getProperty(soapObject.getPropertyCount() - 1).toString());
	// // 天气实况+建议
	// bean.setLiveWeather(soapObject.getProperty(10).toString() + "\n" + soapObject.getProperty(11).toString());
	//
	// // 其他数据
	// // 日期，
	// String date = soapObject.getProperty(6).toString();
	// // ---------------------------------------------------
	// String weatherToday = "今天：" + date.split(" ")[0];
	// weatherToday += "\n天气：" + date.split(" ")[1];
	// weatherToday += "\n气温：" + soapObject.getProperty(5).toString();
	// weatherToday += "\n风力：" + soapObject.getProperty(7).toString();
	// weatherToday += "\n";
	//
	// List<Integer> icons = new ArrayList<Integer>();
	//
	// icons.add(parseIcon(soapObject.getProperty(8).toString()));
	// icons.add(parseIcon(soapObject.getProperty(9).toString()));
	//
	// map.put("weatherDay", weatherToday);
	// map.put("icons", icons);
	// list.add(map);
	//
	// // -------------------------------------------------
	// map = new HashMap<String, Object>();
	// date = soapObject.getProperty(13).toString();
	// String weatherTomorrow = "明天：" + date.split(" ")[0];
	// weatherTomorrow += "\n天气：" + date.split(" ")[1];
	// weatherTomorrow += "\n气温：" + soapObject.getProperty(12).toString();
	// weatherTomorrow += "\n风力：" + soapObject.getProperty(14).toString();
	// weatherTomorrow += "\n";
	//
	// icons = new ArrayList<Integer>();
	//
	// icons.add(parseIcon(soapObject.getProperty(15).toString()));
	// icons.add(parseIcon(soapObject.getProperty(16).toString()));
	//
	// map.put("weatherDay", weatherTomorrow);
	// map.put("icons", icons);
	// list.add(map);
	// // --------------------------------------------------------------
	// map = new HashMap<String, Object>();
	//
	// date = soapObject.getProperty(18).toString();
	// String weatherAfterTomorrow = "后天：" + date.split(" ")[0];
	// weatherAfterTomorrow += "\n天气：" + date.split(" ")[1];
	// weatherAfterTomorrow += "\n气温：" + soapObject.getProperty(17).toString();
	// weatherAfterTomorrow += "\n风力：" + soapObject.getProperty(19).toString();
	// weatherAfterTomorrow += "\n";
	//
	// icons = new ArrayList<Integer>();
	// icons.add(parseIcon(soapObject.getProperty(20).toString()));
	// icons.add(parseIcon(soapObject.getProperty(21).toString()));
	//
	// map.put("weatherDay", weatherAfterTomorrow);
	// map.put("icons", icons);
	// list.add(map);
	// // --------------------------------------------------------------
	//
	// bean.setList(list);
	// return bean;
	// }
	//
	// /**
	// * @description：解析图标字符串
	// * @author samy
	// * @date 2014年8月3日 下午2:08:12
	// */
	// private int parseIcon(String data) {
	// // 0.gif，返回名称0,
	// int resID = 32;
	// String result = data.substring(0, data.length() - 4).trim();
	// // String []icon=data.split(".");
	// // String result=icon[0].trim();
	// // Log.e("this is the icon", result.trim());
	//
	// if (!result.equals("nothing")) {
	// resID = Integer.parseInt(result.trim());
	// }
	// return resID;
	// // return ("a_"+data).split(".")[0];
	// }

}
