package com.zsy.frame.sample.control.android.a17navigation.actionbar.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.ListFragment;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.ViewsFragment;

/**
 * @description：BadgeAct;相比主界面，第二种viewpager显示方式；
 * @author samy
 * @date 2014年7月30日 上午12:26:25
 */
public class BadgeAct extends FragmentActivity implements ActionBar.TabListener {
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a17navigation_actionbar_tab);

		final ActionBar actionBar = getActionBar();
		// veiwpager中最前面有两个Tabs
		// 设置导航模式为Tab选项标签导航模式
		// public static final int NAVIGATION_MODE_TABS = 2;有两个Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			Tab tab = actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this);
			actionBar.addTab(tab);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
				case 0:
					fragment = new ViewsFragment();
					break;
				case 1:
					fragment = new ListFragment();
					break;
				default:
					break;
			}
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return getString(R.string.title_views);
				case 1:
					return getString(R.string.title_list);
			}
			return null;
		}
	}

}
