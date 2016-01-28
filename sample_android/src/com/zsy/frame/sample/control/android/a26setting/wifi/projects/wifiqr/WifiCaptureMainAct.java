package com.zsy.frame.sample.control.android.a26setting.wifi.projects.wifiqr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zsy.frame.sample.R;

public class WifiCaptureMainAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_a26setting_wifi_projects_wifiqr_wificapture_main);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.generateBtnId: {
				enterGenerate();
			}
				break;
			case R.id.captureBtnId: {
				enterCapture();
			}
				break;
			default: {

			}
				break;
		}
	}

	private void enterGenerate() {
		startActivity(new Intent(this, GenerateWifiQRAct.class));
	}

	private void enterCapture() {
		startActivity(new Intent(this, WifiCaptureAct.class));
	}
}
