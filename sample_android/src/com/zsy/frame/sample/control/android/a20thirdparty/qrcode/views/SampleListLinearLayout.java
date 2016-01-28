package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.zsy.frame.sample.R;

public class SampleListLinearLayout extends LinearLayout {
	private BaseAdapter mBaseAdapter;
	private DataSetObserver mDataSetObserver;
	private int dividerSize = 0;
	private int dividerColor = 0xFFFFFFFF;
	private boolean showLastDivide;
	private boolean needDivider;

	public SampleListLinearLayout(Context context) {
		super(context, null);
	}

	public SampleListLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SampleListLinearLayout);

		dividerSize = a.getDimensionPixelSize(R.styleable.SampleListLinearLayout_dividerSize, 1);
		dividerColor = a.getColor(R.styleable.SampleListLinearLayout_dividerColor, 0xFFd1d1d1);
		needDivider = a.getBoolean(R.styleable.SampleListLinearLayout_needDivider, true);

		init();

		a.recycle();
	}

	private void init() {
		mDataSetObserver = new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				inflateView();
			}

			@Override
			public void onInvalidated() {
				super.onInvalidated();
				inflateView();
			}
		};
	}

	public void setAdapter(BaseAdapter baseAdapter) {
		if (mBaseAdapter != null) {
			mBaseAdapter.unregisterDataSetObserver(mDataSetObserver);
		}

		mBaseAdapter = baseAdapter;
		mBaseAdapter.registerDataSetObserver(mDataSetObserver);

		inflateView();
	}

	private void inflateView() {
		removeAllViews();

		int count = mBaseAdapter.getCount();
		for (int i = 0; i < mBaseAdapter.getCount(); i++) {
			View listItem = mBaseAdapter.getView(i, null, this);

			addView(listItem);

			if (needDivider && dividerSize > 0 && dividerColor != 0) {
				if (!showLastDivide && (i == count - 1)) {// 最后一条分割线是否显示
					break;
				}

				LayoutParams params = null;
				if (getOrientation() == HORIZONTAL) {// 水平的
					params = new LayoutParams(dividerSize, LayoutParams.MATCH_PARENT);
				}
				else {
					params = new LayoutParams(LayoutParams.MATCH_PARENT, dividerSize);
				}
				if (i < mBaseAdapter.getCount() - 1) {
					View view = new View(getContext());
					view.setBackgroundColor(dividerColor);
					addView(view, params);
				}
			}
		}

		postInvalidate();
	}

}
