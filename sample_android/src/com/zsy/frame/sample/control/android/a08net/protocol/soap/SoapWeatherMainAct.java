package com.zsy.frame.sample.control.android.a08net.protocol.soap;

import java.util.ArrayList;
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
 * @description：显示天气省份的Activity
 * @author samy
 * @date 2014年8月3日 下午1:03:43
 */
public class SoapWeatherMainAct extends BaseAct {
	private List<String> provinceList = new ArrayList<String>();

	
	
	public SoapWeatherMainAct() {
		super();
		
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		init();
	}

	private void init() {
		final ListView mProvinceList = (ListView) findViewById(R.id.province_list);
		// 显示进度条
		ProgressDialogUtils.showProgressDialog(this, "数据加载中...");
		// 通过工具类调用WebService接口
		WebServiceUtils.callWebService(WebServiceUtils.serviceURL, WebServiceUtils.getSupportProvince, null, new WebServiceCallBack() {
			// WebService接口返回的数据回调到这个方法中
			@Override
			public void callBack(SoapObject result) {
				// 关闭进度条
				ProgressDialogUtils.dismissProgressDialog();
				if (result != null) {
					provinceList = parseSoapObject(result);
					mProvinceList.setAdapter(new ArrayAdapter<String>(SoapWeatherMainAct.this, android.R.layout.simple_list_item_1, provinceList));
				}
				else {
					Toast.makeText(SoapWeatherMainAct.this, "获取WebService数据错误", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mProvinceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SoapWeatherMainAct.this, CityAct.class);
				intent.putExtra("province", provinceList.get(position));
				startActivity(intent);
				showToastMsg("选择省份的ID:"+ provinceList.get(position));
			}
		});

	}

	/**
	 * @description：解析SoapObject对象
	 * @author samy
	 * @date 2014年8月3日 下午1:07:39
	 */
	private List<String> parseSoapObject(SoapObject result) {
		List<String> list = new ArrayList<String>();
		SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportProvinceResult");
		if (provinceSoapObject == null) {
			return null;
		}
		for (int i = 0; i < provinceSoapObject.getPropertyCount(); i++) {
			list.add(provinceSoapObject.getProperty(i).toString());
		}

		return list;
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a08net_protocol_soap_soapweather_main);		
	}

}
