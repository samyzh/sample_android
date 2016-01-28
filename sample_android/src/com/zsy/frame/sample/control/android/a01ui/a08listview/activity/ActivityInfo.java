package com.zsy.frame.sample.control.android.a01ui.a08listview.activity;

import android.app.Activity;

class ActivityInfo {
	public final Class<? extends Activity> activityClass;
	public final int titleResourceId;

	public ActivityInfo(Class<? extends Activity> activityClass, int titleResourceId) {
		this.activityClass = activityClass;
		this.titleResourceId = titleResourceId;
	}
}
