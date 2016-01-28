/*
 * Copyright 2013 Chris Banes
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zsy.frame.sample.control.android.a01ui.a27refresh.acitionbarptr;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;

import com.zsy.frame.sample.R;

@EActivity(R.layout.activity_a01ui_a27refresh_acitionbarptr_main)
public class AcitionbarptrMainAct extends Activity {

	@Click
	void myButton1() {
		startActivity(new Intent(this, ListViewActivity.class));
	}

	@Click
	void myButton2() {
		startActivity(new Intent(this, ListViewActivity.class));
	}

	@Click
	void myButton3() {
		startActivity(new Intent(this, ScrollViewActivity.class));
	}

	@Click
	void myButton4() {
		startActivity(new Intent(this, GridViewActivity.class));
	}

	@Click
	void myButton5() {
		startActivity(new Intent(this, WebViewActivity.class));
	}

	//
	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id) {
	// ActivityInfo info = (ActivityInfo) l.getItemAtPosition(position);
	// Intent intent = new Intent();
	// intent.setComponent(new ComponentName(this, info.name));
	// startActivity(intent);
	// }
	//
	// private ListAdapter getSampleAdapter() {
	// ArrayList<ActivityInfo> items = new ArrayList<ActivityInfo>();
	// final String thisClazzName = getClass().getName();
	//
	// try {
	// PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(),
	// PackageManager.GET_ACTIVITIES);
	// ActivityInfo[] aInfos = pInfo.activities;
	//
	// for (ActivityInfo aInfo : aInfos) {
	// if (!thisClazzName.equals(aInfo.name)) {
	// items.add(aInfo);
	// }
	// }
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// }
	//
	// return new SampleAdapter(this, items);
	// }
	//
	// private static class SampleAdapter extends BaseAdapter {
	//
	// private final ArrayList<ActivityInfo> mItems;
	//
	// private final LayoutInflater mInflater;
	//
	// public SampleAdapter(Context context, ArrayList<ActivityInfo> activities) {
	// mItems = activities;
	// mInflater = LayoutInflater.from(context);
	// }
	//
	// @Override
	// public int getCount() {
	// return mItems.size();
	// }
	//
	// @Override
	// public ActivityInfo getItem(int position) {
	// return mItems.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// TextView tv = (TextView) convertView;
	// if (tv == null) {
	// tv = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, parent,
	// false);
	// }
	// ActivityInfo item = getItem(position);
	// if (!TextUtils.isEmpty(item.nonLocalizedLabel)) {
	// tv.setText(item.nonLocalizedLabel);
	// } else {
	// tv.setText(item.labelRes);
	// }
	// return tv;
	// }
	// }
}
