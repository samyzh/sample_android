package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * @description：接收电池广播信息
 * @author samy
 * @date 2015-3-8 下午5:41:44
 */
public class BatteryInfoBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
			int level = intent.getIntExtra("level", 0);
			int scale = intent.getIntExtra("scale", 0);
			int voltage = intent.getIntExtra("voltage", 0);
			int temperature = intent.getIntExtra("temperature", 0);
			String technology = intent.getStringExtra("technology");
			Dialog dialog = new AlertDialog.Builder(context).setTitle("电池电量").setMessage("电池电量为：" + String.valueOf(level * 100 / scale) + "\n电池电压为：" + String.valueOf((float) voltage / 1000) + "\n电池类型为：" + technology + "\n电池温度为:" + String.valueOf((float) temperature / 10) + "°C").setNegativeButton("关闭", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

				}
			}).create();
			dialog.show();
		}

	}
}