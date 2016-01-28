package com.zsy.frame.sample.control.android.a17navigation.actionbar.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.ChatFragment;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.ContactsFragment;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.FoundFragment;

/**
 * @description：高仿微信的主界面
 * @author samy
 * @date 2014年7月28日 下午10:54:52
 */
public class ActionbarMainAct extends FragmentActivity {
	/** 聊天界面的Fragment */
	private ChatFragment chatFragment;
	/** 发现界面的Fragment */
	private FoundFragment foundFragment;
	/** 通讯录界面的Fragment */
	private ContactsFragment contactsFragment;
	/** PagerSlidingTabStrip的实例 */
	private PagerSlidingTabStrip tabs;
	/** 获取当前屏幕的密度 */
	private DisplayMetrics dm;

	/**
	 * Activity显示方向
	 */
	public static enum ScreenOrientation {
		HORIZONTAL, VERTICAL, AUTO
	}

	/** 是否允许全屏 */
	private boolean mAllowFullScreen = false;
	/** 是否隐藏ActionBar */
	private boolean mHiddenActionBar = false;
	/** 屏幕方向 */
	private ScreenOrientation orientation = ScreenOrientation.VERTICAL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Action Bar显示和隐藏；
		switch (orientation) {
			case HORIZONTAL:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				break;
			case VERTICAL:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				break;
			case AUTO:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
				break;
		}
		if (mHiddenActionBar) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		else {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.show();
		}
		if (mAllowFullScreen) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		setContentView(R.layout.activity_a17navigation_actionbar_main);
		setOverflowShowingAlways();

		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();
	}

	/**
	 * @description：对PagerSlidingTabStrip的各项属性进行赋值。;自定义控件和其属性；
	 * @author samy
	 * @date 2014年7月28日 下午11:38:37
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_a17navigation_actionbar_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_to_spinner:
				// Toast.makeText(this, "action_to_spinner", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(ActionbarMainAct.this, NextTabsAct.class));
				return true;
			case R.id.action_to_badge:
				// Toast.makeText(this, "action_to_spinner", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(ActionbarMainAct.this, BadgeAct.class));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				}
				catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * @description：屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来。
	 * @author samy
	 * @date 2014年7月28日 下午11:34:38
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {
		private final String[] titles = { "聊天", "发现", "通讯录" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					if (chatFragment == null) {
						chatFragment = new ChatFragment();
					}
					return chatFragment;
				case 1:
					if (foundFragment == null) {
						foundFragment = new FoundFragment();
					}
					return foundFragment;
				case 2:
					if (contactsFragment == null) {
						contactsFragment = new ContactsFragment();
					}
					return contactsFragment;
				default:
					return null;
			}
		}
	}
}