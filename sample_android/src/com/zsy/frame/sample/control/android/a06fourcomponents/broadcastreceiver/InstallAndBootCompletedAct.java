package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.zsy.frame.sample.R;

public class InstallAndBootCompletedAct extends Activity {
	
	private MyInstalledReceiver installedReceiver = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_broadcastreceiver_installandbootcompleted);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		installedReceiver = new MyInstalledReceiver();
		IntentFilter filter = new IntentFilter();
		
		filter.addAction("android.intent.action.PACKAGE_ADDED");
		filter.addAction("android.intent.action.PACKAGE_REMOVED");
		filter.addAction("android.intent.action.BOOT_COMPLETED");
		filter.addDataScheme("package");
		
		this.registerReceiver(installedReceiver, filter);
	}
	
	@Override
	public void onDestroy(){
		if(installedReceiver != null) {
			this.unregisterReceiver(installedReceiver);
		}
		super.onDestroy();
	}
}
