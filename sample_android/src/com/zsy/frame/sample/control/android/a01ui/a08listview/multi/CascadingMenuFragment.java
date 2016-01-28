package com.zsy.frame.sample.control.android.a01ui.a08listview.multi;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.entity.MenuItem;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.interfaces.CascadingMenuViewOnSelectListener;

/**
 * @description：级联菜单碎片
 * @author samy
 * @date 2014年9月10日 下午9:40:30
 */
public class CascadingMenuFragment extends Fragment {

	private CascadingMenuView cascadingMenuView;
	private ArrayList<MenuItem> menuItems = null;
	// 提供给外的接口
	private CascadingMenuViewOnSelectListener menuViewOnSelectListener;
	private static CascadingMenuFragment instance = null;

	// 单例模式
	public static CascadingMenuFragment getInstance() {
		if (instance == null) {
			instance = new CascadingMenuFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 实例化级联菜单
		cascadingMenuView = new CascadingMenuView(getActivity(), menuItems);
		// 设置回调接口
		cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return cascadingMenuView;
	}

	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void setMenuViewOnSelectListener(CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
		this.menuViewOnSelectListener = menuViewOnSelectListener;
	}

	// 级联菜单选择回调接口
	class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {
		@Override
		public void getValue(MenuItem menuItem) {
			if (menuViewOnSelectListener != null) {
				menuViewOnSelectListener.getValue(menuItem);
			}
		}

	}
}
