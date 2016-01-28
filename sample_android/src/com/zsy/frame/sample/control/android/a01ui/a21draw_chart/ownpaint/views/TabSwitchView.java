package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by rainy.liao on 2014/11/25.
 */
public class TabSwitchView extends LinearLayout {

	/**
	 * 当前选择的tab的index
	 */
	private int selectIndex = 0;
	/**
	 * 监听器
	 */
	private TabChangeListen tabChangeListen;

	public TabSwitchView(Context context) {
		super(context);
	}

	public TabSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabSwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		setSelectIndex(selectIndex);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			final int index = i;
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (selectIndex != index) {
						View oldView = getChildAt(selectIndex);
						oldView.setSelected(false);
						selectIndex = index;
						view.setSelected(true);
						if (tabChangeListen != null) {
							tabChangeListen.tabChanged(selectIndex);
						}
					}
				}
			});
		}
	}

	/**
	 * 设置默认选中的tab
	 * @param selectIndex
	 */
	public void setSelectIndex(int selectIndex) {
		// 防止设置无效的值
		if (selectIndex >= 0 && selectIndex < getChildCount()) {
			this.selectIndex = selectIndex;
			View view = getChildAt(selectIndex);
			view.setSelected(true);
		}
	}

	/**
	 * 如需监听tab切换，请先设置监听器
	 * @param tabChangeListen
	 */
	public void setTabChangeListen(TabChangeListen tabChangeListen) {
		this.tabChangeListen = tabChangeListen;
	}

	/**
	 * tab切换的监听器
	 */
	public interface TabChangeListen {
		public void tabChanged(int select);
	}
}
