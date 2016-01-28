package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @description：短信接收验证
 * @author samy
 * @date 2015-3-8 下午7:38:21
 */
public class SMSReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "短信来了啊...",Toast.LENGTH_SHORT).show();
	}

}
