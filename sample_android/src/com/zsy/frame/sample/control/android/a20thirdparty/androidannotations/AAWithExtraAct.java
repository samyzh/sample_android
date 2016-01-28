package com.zsy.frame.sample.control.android.a20thirdparty.androidannotations;

import java.util.Date;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import com.zsy.frame.sample.R;
import android.app.Activity;
import android.widget.TextView;

@EActivity(R.layout.activity_a20thirdparty_androidannotations_aa_with_extra)
public class AAWithExtraAct extends Activity {

	public static final String MY_STRING_EXTRA = "myStringExtra";
	public static final String MY_DATE_EXTRA = "myDateExtra";
	public static final String MY_INT_EXTRA = "myIntExtra";

	@ViewById
	TextView extraTextView;

	@Extra(MY_STRING_EXTRA)
	String myMessage;

	@Extra(MY_DATE_EXTRA)
	Date myDate;

	@Extra("unboundExtra")
	String unboundExtra = "unboundExtraDefaultValue";

	/**
	 * The logs will output a classcast exception, but the program flow won't be interrupted
	 */
	@Extra(MY_INT_EXTRA)
	String classCastExceptionExtra = "classCastExceptionExtraDefaultValue";

	@AfterViews
	protected void init() {
		extraTextView.setText(myMessage + "\n " + myDate + "\n " + unboundExtra + "\n " + classCastExceptionExtra);
	}

}
