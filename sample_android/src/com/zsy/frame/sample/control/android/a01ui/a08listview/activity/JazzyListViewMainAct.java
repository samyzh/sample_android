package com.zsy.frame.sample.control.android.a01ui.a08listview.activity;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zsy.frame.sample.R;

public class JazzyListViewMainAct extends ListActivity {

	private final List<ActivityInfo> activitiesInfo = Arrays.asList(new ActivityInfo(JazzyListViewListAct.class, R.string.simple_list_example), new ActivityInfo(JazzyListViewGridAct.class, R.string.simple_grid_example));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] titles = getActivityTitles();
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Class<? extends Activity> clazz = activitiesInfo.get(position).activityClass;
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	private String[] getActivityTitles() {
		String[] result = new String[activitiesInfo.size()];
		int i = 0;
		for (ActivityInfo info : activitiesInfo) {
			result[i++] = getString(info.titleResourceId);
		}
		return result;
	}
}
