package com.zsy.frame.sample.control.android.a17navigation.actionbar.widget;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * @description：Tab选项标签监听接口
 * @author samy
 * @date 2014年9月10日 下午10:24:07
 */
public class MyTabListener<T extends Fragment> implements TabListener {
	private Fragment fragment;
	private final Activity mActivity;
	private final Class<T> mClass;

	public MyTabListener(Activity activity, Class<T> clz) {
		mActivity = activity;
		mClass = clz;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Fragment fragment3 = null;
		// Fragment fragment1 = null;
		// Fragment fragment2 = null;
		// switch (tab.getPosition()) {
		// case 0:
		// if (fragment1 == null) {
		// fragment1 = new Fragment1();
		// }
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.container, fragment1).commit();
		// break;
		// case 1:
		// if (fragment2 == null) {
		// fragment2 = new Fragment2();
		// }
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.container, fragment2).commit();
		// break;
		// case 2:
		// if (fragment3 == null) {
		// fragment3 = new Fragment3();
		// }
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.container, fragment3).commit();
		// break;
		//

		if (fragment == null) {
			fragment = Fragment.instantiate(mActivity, mClass.getName());
			ft.add(android.R.id.content, fragment, null);
		}
		ft.attach(fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			ft.detach(fragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}
}
