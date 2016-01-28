package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zsy.frame.sample.control.base.SYFrameMainAct;

/**
 * @description：
 * @author samy
 * @date 2015-3-8 下午5:23:51
 */
public class MyInstalledReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// 发现-安装和卸载的广播会发送接收两次
		if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) { // install
			String packageName = intent.getDataString();
			System.out.println("安装了 :" + packageName);
		}

		if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) { // uninstall
			String packageName = intent.getDataString();// package:com.baidu.searchbox
			System.out.println("卸载了 :" + packageName);
		}
		/**
		xml 配置
		在AndroidManifest.xml中Application节点内，添加自定义的广播类：
		[html] view plaincopyprint?
		<receiver android:name=".BootReceiver" >  
		    <intent-filter>  
		        <action android:name="android.intent.action.BOOT_COMPLETED" />  
		  
		        <category android:name="android.intent.category.LAUNCHER" />  
		    </intent-filter>  
		</receiver>  
		在AndroidManifest.xml中manifest节点内，添加开机启动权限：
		<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
		 */
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) { // boot
			Intent intent2 = new Intent(context, SYFrameMainAct.class);
//			intent2.setAction("android.intent.action.MAIN");
//			intent2.addCategory("android.intent.category.LAUNCHER");
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
		}
	}
}