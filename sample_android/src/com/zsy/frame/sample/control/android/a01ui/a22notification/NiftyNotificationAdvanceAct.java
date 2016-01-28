package com.zsy.frame.sample.control.android.a01ui.a22notification;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Effects;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a22notification.helps.TipViewManager;

public class NiftyNotificationAdvanceAct extends Activity {

	// 动画效果
	private Effects effect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置支持提示的View
		setContentView(TipViewManager.getTipRootView(this, R.layout.activity_a01ui_a15dialog_niftynotification_advance));
	}

	/***
	 * 显示提示
	 * 
	 * @param v
	 */
	public void showNotify(View v) {

		String msg = "Today we would like to share a couple of simple styles and effects for android notifications.";

		switch (v.getId()) {
			case R.id.scale:
				effect = Effects.scale;
				break;
			case R.id.thumbSlider:
				Toast.makeText(this, "this effects is no useable!", Toast.LENGTH_LONG).show();
				effect = Effects.thumbSlider;
				return;
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
		// 显示的内容View
		LinearLayout contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_a01ui_a15dialog_niftynotification_advance_show_normal_tip, null);
		((TextView) contentView.findViewById(R.id.tip_msg)).setText(msg);
		// 显示提示消息
		// TipViewManager.show(this, contentView, 60, effect, 500, 2000);
		TipViewManager.showErrorMsg(this, "错误界面", 2000);

	}

	@Override
	protected void onDestroy() {
		/****
		 * 清空当前activity的显示的通知View
		 */
		TipViewManager.clear(this);
		super.onDestroy();
	}

}
