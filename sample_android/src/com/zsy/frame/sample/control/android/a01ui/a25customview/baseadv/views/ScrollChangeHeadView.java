package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;

import com.zsy.frame.sample.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 滚动渐变顶部条
 * <p/>
 * 1.拿到ListView滚动事件 2.拿到高度变化 3.根据高度变化，设置顶部条的背景
 */
public class ScrollChangeHeadView extends ListView {

	private View viewHead;
	private View topBar;

	public ScrollChangeHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//List加载一个头部；
		viewHead = LayoutInflater.from(context).inflate(R.layout.view_activity_a01ui_a25customview_baseadv_scroll_change_head, null);
		addHeaderView(viewHead);
		setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (topBar != null) {
					// 滚动中
					int headTop = viewHead.getTop();
					headTop = Math.abs(headTop);

					// 0-255 0是全透明 255 不透明
					topBar.getBackground().setAlpha(headTop * (255 / viewHead.getHeight()));
				}
			}
		});
	}

	public void setTopBar(View v) {
		topBar = v;
	}

	// /**
	// * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	// */
	// private int dip2px(Context context, float dpValue) {
	// final float scale = context.getResources().getDisplayMetrics().density;
	// return (int) (dpValue * scale + 0.5f);
	// }
}
