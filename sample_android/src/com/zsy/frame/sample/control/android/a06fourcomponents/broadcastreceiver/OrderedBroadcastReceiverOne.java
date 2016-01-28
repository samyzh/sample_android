package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class OrderedBroadcastReceiverOne extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("OneBroadcastReceiver");
		// 要不要接受上一个广播接收器receiver2传来的的数据
		Bundle bundle = getResultExtras(true);
		System.out.println("a=" + bundle.getString("a") + ",b=" + bundle.getString("b"));
		// 切断广播
		abortBroadcast();
	}

}
