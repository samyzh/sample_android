package com.zsy.frame.sample.control.android.a01ui.a08listview.multi;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.entity.MenuItem;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.interfaces.CascadingMenuViewOnSelectListener;

/**
 * @description：主界面
 * @author samy
 * @date 2014年9月10日 下午9:28:27
 */
public class MultiListviewAct extends FragmentActivity implements OnClickListener {
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	// 两级联动菜单数据
	private CascadingMenuFragment cascadingMenuFragment = null;
	private CascadingMenuPopWindow cascadingMenuPopWindow = null;

	private Button menuViewPopWindow;
	private Button menuViewFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a08listview_multi_multilistview);
		menuViewPopWindow = (Button) findViewById(R.id.menuViewPopWindow);
		menuViewFragment = (Button) findViewById(R.id.menuViewFragment);

		ArrayList<MenuItem> tempMenuItems = null;
		for (int j = 0; j < 7; j++) {
			tempMenuItems = new ArrayList<MenuItem>();
			for (int i = 0; i < 15; i++) {
				tempMenuItems.add(new MenuItem(false, "子菜单" + j + "" + i, null));
			}
			menuItems.add(new MenuItem(true, "主菜单" + j, tempMenuItems));
		}
		findViewById(R.id.base_btn).setOnClickListener(this);
		menuViewPopWindow.setOnClickListener(this);
		menuViewFragment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.base_btn:
				Intent intent = new Intent(MultiListviewAct.this, BaseMulAct.class);
				startActivity(intent);
				break;
			case R.id.menuViewPopWindow:
				showPopMenu();
				break;
			case R.id.menuViewFragment:
				showFragmentMenu();
				break;
		}
	}

	private void showPopMenu() {
		if (cascadingMenuPopWindow == null) {
			cascadingMenuPopWindow = new CascadingMenuPopWindow(getApplicationContext(), menuItems);
			cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow, 5, 5);
		}
		else if (cascadingMenuPopWindow != null && cascadingMenuPopWindow.isShowing()) {
			cascadingMenuPopWindow.dismiss();
		}
		else {
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow, 5, 5);
		}
	}

	public void showFragmentMenu() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.anim_a01ui_a08listview_multi_short_menu_pop_in, R.anim.anim_a01ui_a08listview_multi_short_menu_pop_out);
		if (cascadingMenuFragment == null) {
			cascadingMenuFragment = CascadingMenuFragment.getInstance();
			cascadingMenuFragment.setMenuItems(menuItems);
			cascadingMenuFragment.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			fragmentTransaction.replace(R.id.liner, cascadingMenuFragment);
		}
		else {
			fragmentTransaction.remove(cascadingMenuFragment);
			cascadingMenuFragment = null;
		}
		fragmentTransaction.commit();
	}

	// 级联菜单选择回调接口
	class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {
		@Override
		public void getValue(MenuItem menuItem) {
			cascadingMenuFragment = null;
			Toast.makeText(getApplicationContext(), "" + menuItem.toString(), 1000).show();
		}
	}
}
