package com.zsy.frame.sample.control.android.a14html5.webview;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

public class MoneyUIHelp {
	public static String getPhoneNumber(Context mContext) {
		  // 获取手机号、手机串号信息
		  TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		  return tm.getDeviceId();
		}
	
	public static void openCLD(String packageName,Context context) { 
        PackageManager packageManager = context.getPackageManager(); 
        PackageInfo pi = null;  
		try {

			pi = packageManager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {

		}
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);

		List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = apps.iterator().next();
		if (ri != null) {
			String className = ri.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			context.startActivity(intent);
		}
   } 
}
