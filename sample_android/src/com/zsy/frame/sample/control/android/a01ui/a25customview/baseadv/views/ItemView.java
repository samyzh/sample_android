package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;

import com.zsy.frame.sample.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemView extends RelativeLayout {
	private TextView tvLeft;
	private TextView tvRight;
	private ImageView ivArrow;

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.view_a01ui_a25customview_baseadv_item_view, this);

		tvLeft = (TextView) findViewById(R.id.tvLeft);
		tvRight = (TextView) findViewById(R.id.tvRight);
		ivArrow = (ImageView) findViewById(R.id.ivArrow);

	}

	/**
	 * 设置内容
	 *
	 * @param tvLeftStr
	 *            左边内容
	 * @param tvRight
	 *            右边内容
	 * @param isShowArrow
	 *            是否显示小箭头
	 */
	public void setView(String tvLeftStr, String tvRightStr, boolean isShowArrow) {

		if (tvLeftStr != null) {
			tvLeft.setText(tvLeftStr);
		}

		if (tvRightStr != null) {
			tvRight.setText(tvRightStr);
		}

		if (isShowArrow) {
			ivArrow.setVisibility(View.VISIBLE);
		} else {
			ivArrow.setVisibility(View.GONE);
		}

	}
}
