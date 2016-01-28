package com.zsy.frame.sample.control.android.a17navigation.actionbar.widget;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

import com.zsy.frame.sample.R;

/**
 * @description：代码中加载子菜单；
 * @author samy
 * @date 2014年10月26日 下午6:08:48
 */
public class MyActionProvider extends ActionProvider {

	public MyActionProvider(Context context) {
		super(context);
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add("sub item 1").setIcon(R.drawable.ic_launcher).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return true;
			}
		});
		subMenu.add("sub item 2").setIcon(R.drawable.ic_launcher).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
	}

	/**
	 * 为了表示这个Action Provider是有子菜单的，需要重写hasSubMenu()方法并返回true
	 */
	@Override
	public boolean hasSubMenu() {
		return true;
	}

}