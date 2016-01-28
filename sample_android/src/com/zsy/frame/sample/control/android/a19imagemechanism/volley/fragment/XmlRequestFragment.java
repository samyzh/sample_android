package com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zsy.frame.lib.net.http.volley.Response.ErrorListener;
import com.zsy.frame.lib.net.http.volley.Response.Listener;
import com.zsy.frame.lib.net.http.volley.VolleyError;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.custom.XmlRequest;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.StringUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.ToastUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.VolleyUtil;

public class XmlRequestFragment extends Fragment {
	public static final int INDEX = 31;

	private ListView lvWeather;
	private static final int[] ids = { R.id.tv_weather_city, R.id.tv_weather_detail, R.id.tv_weather_temp, R.id.tv_weather_wind };
	private static final String[] keys = { "city", "detail", "temp", "wind" };
	private List<Map<String, String>> weatherDataList;

	private SimpleAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_a19imagemechanism_volley_xml_request, container, false);

		weatherDataList = new ArrayList<Map<String, String>>();

		lvWeather = (ListView) view.findViewById(R.id.lv_weather);
		adapter = new SimpleAdapter(getActivity(), weatherDataList, R.layout.fragment_a19imagemechanism_volley_xml_request_list_item, keys, ids);
		lvWeather.setAdapter(adapter);

		// 发起请求
		XmlRequest request = new XmlRequest(StringUtil.preUrl(Constants.DEFAULT_XML_REQUEST_URL), new Listener<XmlPullParser>() {

			@Override
			public void onResponse(XmlPullParser parser) {
				try {
					weatherDataList.clear();
					int eventType = parser.getEventType();
					while (eventType != XmlPullParser.END_DOCUMENT) {
						switch (eventType) {
							case XmlPullParser.START_TAG:
								String nodeName = parser.getName();
								if ("city".equals(nodeName)) {
									Map<String, String> weatherMap = new HashMap<String, String>();

									weatherMap.put("city", parser.getAttributeValue(2));
									weatherMap.put("detail", parser.getAttributeValue(5));
									weatherMap.put("temp", parser.getAttributeValue(7) + "℃ 到 " + parser.getAttributeValue(6) + "℃");
									weatherMap.put("wind", parser.getAttributeValue(8));
									weatherDataList.add(weatherMap);
								}
								break;
						}
						eventType = parser.next();
					}
					adapter.notifyDataSetChanged();
				}
				catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));
			}
		});

		// 请求加上Tag,用于取消请求
		request.setTag(this);

		VolleyUtil.getQueue(getActivity()).add(request);

		return view;
	}

	@Override
	public void onDestroyView() {
		VolleyUtil.getQueue(getActivity()).cancelAll(this);
		super.onDestroyView();
	}

	/**
	 * 请求网站：http://flash.weather.com.cn/wmaps/xml/china.xml
	 * This XML file does not appear to have any style information associated with it. The document tree is shown below.
	<china dn="nay">
	<city quName="黑龙江" pyName="heilongjiang" cityname="哈尔滨" state1="1" state2="1" stateDetailed="多云" tem1="6" tem2="14" windState="东南风转东风小于3级"/>
	<city quName="吉林" pyName="jilin" cityname="长春" state1="1" state2="7" stateDetailed="多云转小雨" tem1="6" tem2="16" windState="西南风4-5级转3-4级"/>
	<city quName="辽宁" pyName="liaoning" cityname="沈阳" state1="1" state2="1" stateDetailed="多云" tem1="7" tem2="18" windState="西南风3-4级转4-5级"/>
	<city quName="海南" pyName="hainan" cityname="海口" state1="1" state2="1" stateDetailed="多云" tem1="23" tem2="32" windState="微风"/>
	<city quName="内蒙古" pyName="neimenggu" cityname="呼和浩特" state1="1" state2="1" stateDetailed="多云" tem1="5" tem2="17" windState="南风小于3级"/>
	<city quName="新疆" pyName="xinjiang" cityname="乌鲁木齐" state1="1" state2="0" stateDetailed="多云转晴" tem1="2" tem2="9" windState="微风"/>
	<city quName="西藏" pyName="xizang" cityname="拉萨" state1="1" state2="2" stateDetailed="多云转阴" tem1="3" tem2="17" windState="微风"/>
	<city quName="青海" pyName="qinghai" cityname="西宁" state1="1" state2="1" stateDetailed="多云" tem1="1" tem2="26" windState="东北风小于3级"/>
	<city quName="宁夏" pyName="ningxia" cityname="银川" state1="0" state2="1" stateDetailed="晴转多云" tem1="10" tem2="24" windState="南风3-4级"/>
	<city quName="甘肃" pyName="gansu" cityname="兰州" state1="0" state2="1" stateDetailed="晴转多云" tem1="9" tem2="25" windState="微风"/>
	<city quName="河北" pyName="hebei" cityname="石家庄" state1="1" state2="1" stateDetailed="多云" tem1="12" tem2="24" windState="微风转南风小于3级"/>
	<city quName="河南" pyName="henan" cityname="郑州" state1="1" state2="1" stateDetailed="多云" tem1="12" tem2="25" windState="微风"/>
	<city quName="湖北" pyName="hubei" cityname="武汉" state1="7" state2="1" stateDetailed="小雨转多云" tem1="13" tem2="24" windState="微风"/>
	<city quName="湖南" pyName="hunan" cityname="长沙" state1="8" state2="3" stateDetailed="中雨转阵雨" tem1="15" tem2="22" windState="南风小于3级"/>
	<city quName="山东" pyName="shandong" cityname="济南" state1="0" state2="1" stateDetailed="晴转多云" tem1="16" tem2="27" windState="南风3-4级"/>
	<city quName="江苏" pyName="jiangsu" cityname="南京" state1="2" state2="4" stateDetailed="阴转雷阵雨" tem1="14" tem2="20" windState="东南风3-4级"/>
	<city quName="安徽" pyName="anhui" cityname="合肥" state1="2" state2="7" stateDetailed="阴转小雨" tem1="15" tem2="24" windState="东南风小于3级转南风3-4级"/>
	<city quName="山西" pyName="shanxi" cityname="太原" state1="0" state2="1" stateDetailed="晴转多云" tem1="5" tem2="24" windState="南风3-4级"/>
	<city quName="陕西" pyName="sanxi" cityname="西安" state1="0" state2="1" stateDetailed="晴转多云" tem1="11" tem2="25" windState="东北风小于3级"/>
	<city quName="四川" pyName="sichuan" cityname="成都" state1="1" state2="1" stateDetailed="多云" tem1="14" tem2="25" windState="南风小于3级"/>
	<city quName="云南" pyName="yunnan" cityname="昆明" state1="1" state2="1" stateDetailed="多云" tem1="10" tem2="24" windState="微风"/>
	<city quName="贵州" pyName="guizhou" cityname="贵阳" state1="1" state2="1" stateDetailed="多云" tem1="15" tem2="24" windState="东南风小于3级"/>
	<city quName="浙江" pyName="zhejiang" cityname="杭州" state1="8" state2="8" stateDetailed="中雨" tem1="14" tem2="20" windState="南风小于3级"/>
	<city quName="福建" pyName="fujian" cityname="福州" state1="1" state2="4" stateDetailed="多云转雷阵雨" tem1="16" tem2="25" windState="微风"/>
	<city quName="江西" pyName="jiangxi" cityname="南昌" state1="8" state2="3" stateDetailed="中雨转阵雨" tem1="17" tem2="21" windState="微风"/>
	<city quName="广东" pyName="guangdong" cityname="广州" state1="1" state2="2" stateDetailed="多云转阴" tem1="18" tem2="26" windState="微风"/>
	<city quName="广西" pyName="guangxi" cityname="南宁" state1="3" state2="3" stateDetailed="阵雨" tem1="20" tem2="26" windState="东南风小于3级"/>
	<city quName="北京" pyName="beijing" cityname="北京" state1="29" state2="53" stateDetailed="浮尘转霾" tem1="12" tem2="23" windState="微风"/>
	<city quName="天津" pyName="tianjin" cityname="天津" state1="1" state2="1" stateDetailed="多云" tem1="13" tem2="24" windState="南风3-4级"/>
	<city quName="上海" pyName="shanghai" cityname="上海" state1="7" state2="7" stateDetailed="小雨" tem1="14" tem2="17" windState="东南风小于3级转3-4级"/>
	<city quName="重庆" pyName="chongqing" cityname="重庆" state1="1" state2="1" stateDetailed="多云" tem1="16" tem2="29" windState="微风"/>
	<city quName="香港" pyName="xianggang" cityname="香港" state1="1" state2="1" stateDetailed="多云" tem1="21" tem2="25" windState="微风"/>
	<city quName="澳门" pyName="aomen" cityname="澳门" state1="1" state2="3" stateDetailed="多云转阵雨" tem1="21" tem2="27" windState="微风"/>
	<city quName="台湾" pyName="taiwan" cityname="台北" state1="1" state2="2" stateDetailed="多云转阴" tem1="19" tem2="28" windState="微风"/>
	<city quName="西沙" pyName="xisha" cityname="西沙" state1="1" state2="1" stateDetailed="多云" tem1="25" tem2="30" windState="东南风4-5级"/>
	<city quName="南沙" pyName="nanshadao" cityname="南沙" state1="1" state2="1" stateDetailed="多云" tem1="26" tem2="31" windState="东风4-5级"/>
	<city quName="钓鱼岛" pyName="diaoyudao" cityname="钓鱼岛" state1="0" state2="2" stateDetailed="晴转阴" tem1="17" tem2="23" windState="东南风3-4级"/>
	</china>
	 */

}
