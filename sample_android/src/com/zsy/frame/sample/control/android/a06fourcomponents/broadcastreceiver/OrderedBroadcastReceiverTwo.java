package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class OrderedBroadcastReceiverTwo extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("TwoBroadcastReceiver");
		Bundle bundle = intent.getExtras();
		bundle.putString("b", "bbb");
		System.out.println("a=" + bundle.get("a"));
		setResultExtras(bundle);
		// 切断广播
		// abortBroadcast();
	}

}
