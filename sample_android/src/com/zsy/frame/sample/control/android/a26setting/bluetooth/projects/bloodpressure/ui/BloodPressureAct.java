package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BloodPressureAct extends FragmentActivity {
		@Override
		protected void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new BloodPressureFragment()).commit();
		}
}
