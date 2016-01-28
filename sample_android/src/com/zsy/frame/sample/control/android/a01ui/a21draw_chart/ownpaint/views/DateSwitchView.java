package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.BodyDataHistoryAct;

/**
 * Created by rainy.liao on 2014/11/28. 日期切换
 */
public class DateSwitchView extends LinearLayout {

	private ImageView leftArrowButton;
	private ImageView rightArrowButton;
	private TextView dateTextView;

	/**
	 * 切换类型，该变量值对应日月周三种类型
	 */
	private int swtichType;
	/**
	 * 今天的日期
	 */
	private String currentDate;

	private Calendar mCalendar = Calendar.getInstance();
	/**
	 * 日期格式
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private ArrowClickListen arrowClickListen;
	/**
	 * 距离今天、或者本周或者本月的偏移值，如type为日时，offset=-1，表示昨天，offset=0，表示今天，offset=1 表示明天，依此类推
	 */
	private int offset = 0;

	public DateSwitchView(Context context) {
		super(context);
	}

	public DateSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DateSwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initView();
	}

	private void initView() {
		leftArrowButton = (ImageView) this.findViewById(R.id.btn_left_arrow);
		rightArrowButton = (ImageView) this.findViewById(R.id.btn_right_arrow);
		dateTextView = (TextView) this.findViewById(R.id.data_text);
		if (offset == 0) {
			rightArrowButton.setEnabled(false);
		}
		else {
			rightArrowButton.setEnabled(true);
		}

		leftArrowButton.setOnClickListener(clickListener);
		rightArrowButton.setOnClickListener(clickListener);
	}

	public void initDateSwitchView(int swtichType) {

		setSwtichType(swtichType);
	}

	/**
	 * 是否还有更早的数据
	 * @param hasOlderData
	 */
	public void setHasOlderData(boolean hasOlderData) {
		leftArrowButton.setEnabled(hasOlderData);
	}

	/**
	 * 是否还有更新的数据
	 * @param hasNewerData
	 */
	public void setHasNewerData(boolean hasNewerData) {
		rightArrowButton.setEnabled(hasNewerData);
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.btn_left_arrow:
					setOffset(offset - 1);
					break;
				case R.id.btn_right_arrow:
					setOffset(offset + 1);
					break;
			}
			if (arrowClickListen != null) {
				arrowClickListen.arrowClicked(currentDate);
			}
		}
	};

	public void setSwtichType(int swtichType) {
		if (this.swtichType != swtichType) {
			this.swtichType = swtichType;
			setOffset(0);
		}
		if (dateTextView != null) {
			dateTextView.setText(getDate());
		}
	}

	public void setArrowClickListen(ArrowClickListen arrowClickListen) {
		this.arrowClickListen = arrowClickListen;
	}

	public String getTodayDateStr() {
		switch (swtichType) {
			case BodyDataHistoryAct.TYPE_DAY:
				return getResources().getString(R.string.today);
			case BodyDataHistoryAct.TYPE_WEEK:
				return getResources().getString(R.string.this_week);
			case BodyDataHistoryAct.TYPE_MONTH:
				return getResources().getString(R.string.this_month);
		}

		return "";
	}

	public void setOffset(int offset) {
		if (this.offset != offset) {
			this.offset = offset;
			dateTextView.setText(getDate());
			if (this.offset >= 0) {
				this.offset = 0;
				this.rightArrowButton.setEnabled(false);
			}
			else {
				this.rightArrowButton.setEnabled(true);
			}
		}
	}

	private String getDate() {
		switch (swtichType) {
			case BodyDataHistoryAct.TYPE_DAY:
				return getDayByOffset();
			case BodyDataHistoryAct.TYPE_WEEK:
				return getWeekByOffset();
			case BodyDataHistoryAct.TYPE_MONTH:
				return getMonthByOffset();
		}
		return "";
	}

	// 获取N天前的日期
	public String getDayByOffset() {
		if (offset == 0) {
			currentDate = sdf.format(new Date(mCalendar.getTimeInMillis()));
			return getTodayDateStr();
		}
		long t = mCalendar.getTimeInMillis();
		long l = t + offset * 24 * 3600 * 1000;
		Date d = new Date(l);
		String s = sdf.format(d);
		currentDate = s;
		return s;
	}

	public String getWeekByOffset() {
		if (offset == 0) { return getTodayDateStr(); }
		int year = mCalendar.get(Calendar.YEAR);
		int weeks = mCalendar.get(Calendar.WEEK_OF_YEAR);
		weeks += offset;
		int w = 0;
		if (weeks <= 0) {
			w = (-weeks) / 52 + 1;
			year -= w;
			weeks += w * 52;
		}

		return year + getResources().getString(R.string.year) + weeks + getResources().getString(R.string.week);
	}

	public String getMonthByOffset() {
		if (offset == 0) { return getTodayDateStr(); }
		int year = mCalendar.get(Calendar.YEAR);
		int months = mCalendar.get(Calendar.MONTH) + 1;
		months += offset;

		int m = 0;
		if (months <= 0) {
			m = (-months) / 12 + 1;
			year -= m;
			months += m * 12;
		}

		return year + getResources().getString(R.string.year) + months + getResources().getString(R.string.month);
	}

	public interface ArrowClickListen {
		/**
		 * 左右箭头点击见天
		 * @param date 切换后的日期
		 */
		public void arrowClicked(String date);
	}
}
