package com.zsy.frame.sample.control.android.a01ui.a22notification;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.zsy.frame.sample.R;

public class NiftyNotificationAct extends Activity {
	private Effects effect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a15dialog_niftynotificationeffects);
	}

	public void dialogShow(View v) {

		String msg = "Today we’d like to share a couple of simple styles and effects for android notifications.";

		switch (v.getId()) {
			case R.id.scale:
				effect = Effects.scale;
				break;
			case R.id.thumbSlider:
				effect = Effects.thumbSlider;
				break;
			case R.id.jelly:
				effect = Effects.jelly;
				break;
			case R.id.slidein:
				effect = Effects.slideIn;
				break;
			case R.id.flip:
				effect = Effects.flip;
				break;
			case R.id.slideOnTop:
				effect = Effects.slideOnTop;
				break;
			case R.id.standard:
				effect = Effects.standard;
				break;
		}

//		 NiftyNotificationView
//		 .build(this, msg, effect, R.id.mLyout)
//		 .setIcon(R.drawable.ic_launcher) // You must call this method if you use ThumbSlider effect
//		 .setOnClickListener(null)
//		 .show();

		// You can configure like this
		// The default
		Configuration cfg = new Configuration.Builder().setAnimDuration(700).setDispalyDuration(1500).setBackgroundColor("#FFBDC3C7").setTextColor("#FF444444").setIconBackgroundColor("#FFFFFFFF").setTextPadding(5) // dp
				.setViewHeight(48) // dp
				.setTextLines(2) // You had better use setViewHeight and setTextLines together
				.setTextGravity(Gravity.CENTER) // only text def Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
				.build();

		NiftyNotificationView.build(this, msg, effect, R.id.mLyout, cfg).setIcon(R.drawable.ic_launcher) // remove this line ,only text
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// add your code
						Toast.makeText(view.getContext(), "niftyNotificaion click me ", Toast.LENGTH_LONG).show();
					}
				}).show(); // show(boolean) allow duplicates
	}

}
