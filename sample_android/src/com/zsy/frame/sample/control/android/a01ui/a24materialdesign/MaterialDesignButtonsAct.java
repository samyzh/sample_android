package com.zsy.frame.sample.control.android.a01ui.a24materialdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import com.zsy.frame.sample.R;

public class MaterialDesignButtonsAct extends Activity {

	int backgroundColor = Color.parseColor("#1E88E5");

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a24materialdesign_materialdesign_buttons);
		int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
		findViewById(R.id.buttonflat).setBackgroundColor(color);
		findViewById(R.id.button).setBackgroundColor(color);
		findViewById(R.id.buttonFloatSmall).setBackgroundColor(color);
		findViewById(R.id.buttonIcon).setBackgroundColor(color);
		findViewById(R.id.buttonFloat).setBackgroundColor(color);
	}

}
