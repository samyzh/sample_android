package com.zsy.frame.sample.control.android.a01ui.a08listview.multi;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.adpater.MenuItemAdapter;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.entity.MenuItem;
import com.zsy.frame.sample.control.android.a01ui.a08listview.multi.interfaces.CascadingMenuViewOnSelectListener;

/**
 * @description：二级联动ListView,都用到的
 * @author samy
 * @date 2014年9月10日 下午9:37:56
 */
public class CascadingMenuView extends LinearLayout {
	private static final String TAG = CascadingMenuView.class.getSimpleName();
	private ListView firstMenuListView;
	private ListView secondMenuListView;
	// 每次选择的子菜单内容
	private ArrayList<MenuItem> childrenItem = new ArrayList<MenuItem>();
	private ArrayList<MenuItem> menuItems;
	// 存放着所有的子菜单列表类似HashMap 但在Android中建议采用SparseArray
	private SparseArray<ArrayList<MenuItem>> childrenItems = new SparseArray<ArrayList<MenuItem>>();
	private MenuItemAdapter firstMenuListViewAdapter;
	private MenuItemAdapter secondMenuListViewAdapter;

	// 二级菜单选择后触发的接口，即最终选择的内容
	private CascadingMenuViewOnSelectListener mOnSelectListener;

	private int tEaraPosition = 0;
	private int tBlockPosition = 0;

	public CascadingMenuView(Context context, ArrayList<MenuItem> menuList) {
		super(context);
		this.menuItems = menuList;
		init(context);

	}

	public CascadingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(final Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_a01ui_a08listview_multi_regin, this, true);
		firstMenuListView = (ListView) findViewById(R.id.listView);
		secondMenuListView = (ListView) findViewById(R.id.listView2);
		// setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.choosearea_bg_left));
		// 把所有的子菜单取出放好
		for (int i = 0; i < menuItems.size(); i++) {
			childrenItems.put(i, menuItems.get(i).getChildMenuItems());
		}
		// 初始化一级主菜单
		firstMenuListViewAdapter = new MenuItemAdapter(context, menuItems, R.drawable.a01ui_a08listview_multi_choose_item_selected, R.drawable.a01ui_a08listview_multi_choose_eara_item_selector);
		firstMenuListViewAdapter.setTextSize(17);
		firstMenuListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		firstMenuListView.setAdapter(firstMenuListViewAdapter);
		firstMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// 选择主菜单，清空原本子菜单内容，增加新内容
				childrenItem.clear();
				childrenItem.addAll(childrenItems.get(position));
				// 通知适配器刷新
				secondMenuListViewAdapter.notifyDataSetChanged();

			}
		});
		// 初始化二级主菜单
		if (tEaraPosition < childrenItems.size()) childrenItem.addAll(childrenItems.get(tEaraPosition));
		secondMenuListViewAdapter = new MenuItemAdapter(context, childrenItem, R.drawable.a01ui_a08listview_multi_choose_item_right, R.drawable.a01ui_a08listview_multi_choose_plate_item_selector);
		secondMenuListViewAdapter.setTextSize(15);
		secondMenuListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		secondMenuListView.setAdapter(secondMenuListViewAdapter);
		secondMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, final int position) {
				MenuItem menuItem = childrenItem.get(position);
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue(menuItem);
				}
				Log.e(TAG, menuItem.toString());
			}
		});
		// 设置默认选择
		setDefaultSelect();
	}

	public void setDefaultSelect() {
		firstMenuListView.setSelection(tEaraPosition);
		secondMenuListView.setSelection(tBlockPosition);
	}

	public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}
}
