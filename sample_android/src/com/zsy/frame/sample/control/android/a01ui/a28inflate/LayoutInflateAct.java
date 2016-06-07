package com.zsy.frame.sample.control.android.a01ui.a28inflate;

import java.util.Arrays;
import java.util.List;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class LayoutInflateAct extends BaseAct {

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a28inflate_act);
		mListView = (ListView) findViewById(R.id.id_listview);
		mAdapter = new MyAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
	}

	private ListView mListView;
	private MyAdapter mAdapter;
	private List<String> mDatas = Arrays.asList("Hello", "Java", "Android");

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<String> mDatas;

		public MyAdapter(Context context, List<String> datas) {
			mInflater = LayoutInflater.from(context);
			mDatas = datas;
		}

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
//				convertView = mInflater.inflate(R.layout.item_a01ui_a28inflate_lv, null);
				 convertView = mInflater.inflate(R.layout.item_a01ui_a28inflate_lv, parent,false);
//				 convertView = mInflater.inflate(R.layout.item_a01ui_a28inflate_lv, parent ,true);//FATAL EXCEPTION: main  java.lang.UnsupportedOperationException:addView(View, LayoutParams) is not supported in AdapterView  
				holder.mBtn = (Button) convertView.findViewById(R.id.id_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.mBtn.setText(mDatas.get(position));  //  

			return convertView;
		}

		private final class ViewHolder {
			Button mBtn;
		}
	}
}
