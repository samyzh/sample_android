package com.zsy.frame.sample.control.android.a17navigation.actionbar.activity;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * @description：Spinner下拉实例；
 * @author samy
 * @date 2014年10月26日 下午9:08:09
 */
public class SpinnerTabsAct extends Activity {
	private ActionBar actionBar;
	private ActionMode mMode;
	String[] actions = new String[] { "Bookmark", "Subscribe", "Share" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		// 返回的监听必须设置；
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		// 1.初始化一个SpinnerAdapter
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
		// 3，将生成好的适配去和监听器塞给ActionBar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);// 导航模式必须设为NAVIGATION_MODE_LIST
		actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

		setContentView(R.layout.activity_a17navigation_spinner_tabs);

		((Button) findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMode = startActionMode(new AnActionModeOfEpicProportions());
			}
		});
		((Button) findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mMode != null) {
					mMode.finish();
				}
			}
		});
	}

	/**
	 * 2.生成一个OnNavigationListener来响应ActionBar的菜单项点击操作
	 * 在这里配合Fragment，实现不同下拉的页面导航
	 */
	OnNavigationListener mOnNavigationListener = new OnNavigationListener() {

		@Override
		public boolean onNavigationItemSelected(int position, long itemId) {
			// Fragment newFragment = null;
			switch (position) {
				case 0:
					// newFragment = new Fragment1();
					Toast.makeText(SpinnerTabsAct.this, "spinner1", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					// newFragment = new Fragment2();
					Toast.makeText(SpinnerTabsAct.this, "spinner2", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					// newFragment = new Fragment3();
					Toast.makeText(SpinnerTabsAct.this, "spinner3", Toast.LENGTH_SHORT).show();
					break;
			}
			// getFragmentManager().beginTransaction().replace(R.id.container, newFragment, strings[position]).commit();
			return true;
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				/**
				 * 方法一：
				 */
				// finish();
				// Intent upIntent = NavUtils.getParentActivityIntent(this);
				// if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				// TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
				// }
				// else {
				// upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// NavUtils.navigateUpTo(this, upIntent);
				// }
				/**
				 * 方法二：// 如果这里有很多原始的Activity,它们应该被添加在这里
				 */
				Intent upIntent = NavUtils.getParentActivityIntent(this);
				TaskStackBuilder.from(this).addNextIntent(new Intent(this, NextTabsAct.class)).addNextIntent(new Intent(this, ActionbarMainAct.class)).addNextIntent(upIntent).startActivities();
				// TaskStackBuilder.from(this).addNextIntent(new Intent(this, NextTabsAct.class)).startActivities();
				// TaskStackBuilder.from(this).addNextIntent(new Intent(this, NextTabsAct.class)).addNextIntent(new Intent(this, MainAct.class)).startActivities();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private final class AnActionModeOfEpicProportions implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			menu.add("Save").setIcon(R.drawable.ic_launcher).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add("edit").setIcon(R.drawable.ic_launcher).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// switch (item.getItemId()) {
			// case value:
			//
			// break;
			// }
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}
	}
}
