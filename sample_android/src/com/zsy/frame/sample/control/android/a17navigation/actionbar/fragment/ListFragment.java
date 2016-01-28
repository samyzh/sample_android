package com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.util.Cheeses;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.widget.BadgeView;

/**
 * @description：Listview
 * @author samy
 * @date 2014年7月30日 上午12:27:00
 */
public class ListFragment extends android.support.v4.app.ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ListAdapter(getActivity()));
	}

	private static class ListAdapter extends BaseAdapter {

		private static final String[] DATA = Cheeses.sCheeseStrings;

		private LayoutInflater mInflater;
		private Context mContext;

		public ListAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			mContext = context;
		}

		@Override
		public int getCount() {
			return DATA.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_a17navigation_actionbar_list_row_badge, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(android.R.id.text1);

				holder.badge = new BadgeView(mContext);
				holder.badge.setBadgeGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				holder.badge.setBadgeMargin(0, 0, 8, 0);
				holder.badge.setTargetView(holder.text);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(DATA[position]);
			holder.badge.setBadgeCount(DATA[position].length());

			return convertView;
		}

		static class ViewHolder {
			TextView text;
			BadgeView badge;
		}
	}
}
