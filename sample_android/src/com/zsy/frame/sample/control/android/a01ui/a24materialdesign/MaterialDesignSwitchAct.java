package com.zsy.frame.sample.control.android.a01ui.a24materialdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import com.zsy.frame.sample.R;

public class MaterialDesignSwitchAct extends Activity {

	int backgroundColor = Color.parseColor("#1E88E5");

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a24materialdesign_materialdesign_switchs);
		int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
		findViewById(R.id.checkBox).setBackgroundColor(color);
		findViewById(R.id.switchView).setBackgroundColor(color);
	}

}
