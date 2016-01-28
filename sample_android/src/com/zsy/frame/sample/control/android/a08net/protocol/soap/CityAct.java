package com.zsy.frame.sample.control.android.a08net.protocol.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.ProgressDialogUtils;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.WebServiceUtils;
import com.zsy.frame.sample.control.android.a08net.protocol.soap.helps.WebServiceUtils.WebServiceCallBack;

/**
 * @description：显示城市的Activity
 * @author samy
 * @date 2014年8月3日 下午1:11:01
 */
public class CityAct extends BaseAct {
	private List<String> cityStringList;
	
	public CityAct() {
		super();
		
		
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		init();
	}

	private void init() {
		final ListView mCityList = (ListView) findViewById(R.id.province_list);
		// 显示进度条
		ProgressDialogUtils.showProgressDialog(this, "数据加载中...");
		// 添加参数
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("byProvinceName", getIntent().getStringExtra("province"));
		WebServiceUtils.callWebService(WebServiceUtils.serviceURL, WebServiceUtils.getSupportCity, properties, new WebServiceCallBack() {
			@Override
			public void callBack(SoapObject result) {
				ProgressDialogUtils.dismissProgressDialog();
				if (result != null) {
					cityStringList = parseSoapObject(result);
					mCityList.setAdapter(new ArrayAdapter<String>(CityAct.this, android.R.layout.simple_list_item_1, cityStringList));
				}
				else {
					Toast.makeText(CityAct.this, "获取WebService数据错误", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mCityList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(CityAct.this, WeatherAct.class);
				intent.putExtra("city", cityStringList.get(position));
				startActivity(intent);
				showToastMsg("选择市区的ID:"+ cityStringList.get(position));
			}
		});
	}

	/**
	 * @description： 解析SoapObject对象
	 * @author samy
	 * @date 2014年8月3日 下午1:11:25
	 */
	private List<String> parseSoapObject(SoapObject result) {
		List<String> list = new ArrayList<String>();
		SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportCityResult");
		int count=result.getPropertyCount();
		for (int i = 0; i < count; i++) {
			String cityString = provinceSoapObject.getProperty(i).toString();
			list.add(cityString.substring(0, cityString.indexOf("(")).trim());
		}
		return list;
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a08net_protocol_soap_soapweather_main);		
	}
}
