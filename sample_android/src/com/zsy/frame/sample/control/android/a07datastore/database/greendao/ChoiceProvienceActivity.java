package com.zsy.frame.sample.control.android.a07datastore.database.greendao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zsy.frame.sample.support.areainfo.AreaInfoDbHelper;
import com.zsy.frame.sample.support.bean.AreaInfo;

/**
 * @description：选择省份的页面
 * 目前发现这个还有问题；在于SimpleAdapter用发不好控制ListviewItem点击事件;后期应该用ListView显示加载更好
 * @author samy
 * @date 2015-3-10 下午3:24:45
 */
public class ChoiceProvienceActivity extends ListActivity {
	private List<AreaInfo> provinceList = new ArrayList<AreaInfo>();
	private AreaInfoDbHelper mAreaInfoDbHelper;
	private int mLevel = 1;
	private AreaInfo mProvienceInfo, mCityInfo, mAreaInfo;

	private SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAreaInfoDbHelper = new AreaInfoDbHelper();
		initDatas();
		simpleAdapter = new SimpleAdapter(this, getData(provinceList), android.R.layout.simple_list_item_1, new String[] { "title" }, new int[] { android.R.id.text1 });
		setListAdapter(simpleAdapter);
	}

	protected List<Map<String, Object>> getData(List<AreaInfo> provinceList) {
		List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
		for (AreaInfo temp : provinceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", temp.getAreaName());
			myData.add(map);
		}
		return myData;
	}

	protected boolean initDatas() {
		switch (mLevel) {
			case 1:
				provinceList = mAreaInfoDbHelper.getProvinceList();
				break;
			case 2:
				provinceList = mAreaInfoDbHelper.getAreaListByParentID(mProvienceInfo.getAreaId());
				break;
			case 3:
				provinceList = mAreaInfoDbHelper.getAreaListByParentID(mCityInfo.getAreaId());
				break;
		}

		if (mLevel > 3 || provinceList == null || provinceList.size() == 0) { return false; }
		return true;
	}

	@Override
	public void onBackPressed() {
		if (mLevel > 1) {
			mLevel--;
			initDatas();
		}
		else {
			super.onBackPressed();
		}
	}

	public void jumpToActivity(Context from, Class to, Bundle mBundle, Boolean isFinish) {
		Intent intent = new Intent(from, to);
		intent.putExtras(mBundle);
		startActivity(intent);
		// startAnimate(from);
		if (isFinish) {
			finish();
		}
	}

	private OnItemClickListener onItemClickListner = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (parent instanceof ListView) {
				int realPosition = position - ((ListView) parent).getHeaderViewsCount();
				if (realPosition >= 0 && realPosition < simpleAdapter.getCount()) {
					if (mLevel == 1) {
						mProvienceInfo = provinceList.get(realPosition);
					}
					else if (mLevel == 2) {
						mCityInfo = provinceList.get(realPosition);
					}
					mLevel++;
					if (!initDatas()) {// 如果没有下一级，则终止
						String address = mProvienceInfo.getAreaName() + " " + mCityInfo.getAreaName();
						int areaId = mCityInfo.getAreaId();
						if (provinceList != null && provinceList.size() > 0) {
							mAreaInfo = provinceList.get(realPosition);
							address = address + " " + mAreaInfo.getAreaName();
							areaId = mAreaInfo.getAreaId();
						}
						// 地区选择完毕， 返回调用界面
						// Bundle bundle = new Bundle();
						// bundle.putString(Constant.CHOICE_AREA_RESULT, address); // 中文地址
						// bundle.putInt(Constant.CHOICE_AREA_RESULT_ID, areaId); // 地区ID
						// Intent mIntent = getIntent();
						// mIntent.putExtras(bundle);
						// setResult(Activity.RESULT_OK, mIntent);

						Toast.makeText(ChoiceProvienceActivity.this, "当前选择的区域为：" + address, Toast.LENGTH_LONG).show();
						finish();
					}
				}
			}
		}
	};

	protected void onListItemClick(ListView l, View parent, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
//		Intent intent = (Intent) map.get("intent");
		String intent = (String) map.get("title");
		Toast.makeText(ChoiceProvienceActivity.this, "当前选择的区域为：" + intent, Toast.LENGTH_LONG).show();
	}
}