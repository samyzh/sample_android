package com.zsy.frame.sample.control.android.a01ui.a24materialdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.ColorSelector;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.zsy.frame.sample.R;

public class MaterialDesignWidgetAct extends Activity {

	private int backgroundColor = Color.parseColor("#1E88E5");

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a24materialdesign_materialdesign_widgets);

		// SHOW SNACKBAR
		findViewById(R.id.buttonSnackBar).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View flatButton) {
				new SnackBar(MaterialDesignWidgetAct.this, "Do you want change color of this button to red?", "yes", new OnClickListener() {

					@Override
					public void onClick(View v) {
						ButtonFlat btn = (ButtonFlat) findViewById(R.id.buttonSnackBar);
						// btn.setTextColor(Color.RED);
					}
				}).show();
			}
		});
		// SHOW DiALOG
		findViewById(R.id.buttonDialog).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View flatButton) {
				Dialog dialog = new Dialog(MaterialDesignWidgetAct.this, "Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
				dialog.setOnAcceptButtonClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(MaterialDesignWidgetAct.this, "Click accept button", 1).show();
					}
				});
				dialog.setOnCancelButtonClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(MaterialDesignWidgetAct.this, "Click cancel button", 1).show();
					}
				});
				dialog.show();
			}
		});
		// SHOW COLOR SEECTOR
		findViewById(R.id.buttonColorSelector).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View flatButton) {
				new ColorSelector(MaterialDesignWidgetAct.this, Color.RED, null).show();
			}
		});
	}

}
