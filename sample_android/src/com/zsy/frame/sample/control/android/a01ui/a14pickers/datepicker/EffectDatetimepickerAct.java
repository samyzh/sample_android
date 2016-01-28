package com.zsy.frame.sample.control.android.a01ui.a14pickers.datepicker;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

public class EffectDatetimepickerAct extends BaseAct implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";

	
	/**
	 * 目前发现自定义控件在构造类时处理数据有点问题
	 */
//	EffectDatetimepickerAct() {
//		super();
//		setHiddenActionBar(false);
//	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a14pickers_datepicker_effectdatetimepicker);
	}

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
		final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

		findViewById(R.id.dateButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datePickerDialog.setVibrate(isVibrate());
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
				datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
			}
		});

		findViewById(R.id.timeButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				timePickerDialog.setVibrate(isVibrate());
				timePickerDialog.setCloseOnSingleTapMinute(isCloseOnSingleTapMinute());
				timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			}
		});

		if (savedInstance != null) {
			DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}
	}

	private boolean isVibrate() {
		return ((CheckBox) findViewById(R.id.checkBoxVibrate)).isChecked();
	}

	private boolean isCloseOnSingleTapDay() {
		return ((CheckBox) findViewById(R.id.checkBoxCloseOnSingleTapDay)).isChecked();
	}

	private boolean isCloseOnSingleTapMinute() {
		return ((CheckBox) findViewById(R.id.checkBoxCloseOnSingleTapMinute)).isChecked();
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
		Toast.makeText(EffectDatetimepickerAct.this, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		Toast.makeText(EffectDatetimepickerAct.this, "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
	}

}
