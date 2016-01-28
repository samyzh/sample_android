package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * @description：
 * android的broadcast的生命周期：
       ：1)Broadcast receiver生命周期中仅有一个回调方法： void onReceive(Context curContext, Intent broadcastMsg) 
		当接收器接收到一条broadcast消息，Android就会调用onReceiver(),并传递给它一个Intent对象，这个对象携带着那条broadcast消息。我们认为仅当执行这个方式时，Broadcast receiver是活动的；这个方法返回时，它就终止了。这就是Broadcast receiver的生命周期。
	2)由于Broadcast receiver的生命周期很短，一个带有活动的Broadcast receiver的进程是受保护的，以避免被干掉；但是别忘了有一点，Android会在任意时刻干掉那些携带不再活动的组件的进程，所以很可能会造成这个问题。
	3)解决上述问题的方案采用一个Service来完成这项工作，Android会认为那个进程中（Service所在的进程）仍然有在活动的组件。
	
二.BroadcastReceiver的生命周期?                                                                                      
    BroadcastReceiver在onReceive函数执行结束后即表示生命周期结束.
       所以使用BroadcastReceiver需注意几点：
    1. BroadcastReceiver中不适合做一些异步操作 ，如新建线程下载数据，BroadcastReceiver结束后可能在异步操作完成前进程已经被系统kill。
    2.BroadcastReceiver的onReceive函数必须在10秒内完成，否则会ANR。而且onReceive默认会在主线程中执行，所以 BroadcastReceiver中不适合做一些耗时操作 ，对于耗时操作需要交给service处理，比如网络或数据库耗时操作、对话框的显示(因为现实时间可能超时，用Notification代替)。
    3.建议使用动态注册的形式注册广播接收者。 在onResume注册接收者，在onStop方法中卸载接收者 。因为这样能够节省资源。	
 三、注册方式，一种动态注册（代码中）；二种静态注册（xml中）   
         动态注册与静态注册的区别
    1.registerReceiver为动态注册，自己可以手动注册或是取消注册；<receiver>标签为静态注册，由系统开机时自动扫描注册，所以无法手动控制，开机一直运行中。
    2. 资源消耗不同 。registerReceiver可以手动控制，所以适当的注册和取消注册能节省系统资源，<receiver>标签系统开机后一直有效。
    3.使用情景不一样。对于自己发送和接受的广播可以通过registerReceiver注册，对于系统常用广播的接收通常用<receiver>标签注册。
 * @author samy
 * @date 2015-3-8 下午6:16:48
 */
public class BatteryInfoAct extends Activity {
	MybatteryLevelRcvr receiver;
	private Button mybtn = null;
	private TextView batterLevel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 生命周期方法
		super.setContentView(R.layout.activity_a06fourcomponents_broadcastreceiver_batteryinfo); // 设置要使用的布局管理器
		this.mybtn = (Button) super.findViewById(R.id.button1);
		this.mybtn.setOnClickListener(new OnClickListenerImpl());
		batterLevel = (TextView) findViewById(R.id.batteryLevel);
	}

	protected void onResume() {
		super.onResume();
		receiver = new MybatteryLevelRcvr();
		// 监听电量变化的意图
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	private class OnClickListenerImpl implements OnClickListener {

		public void onClick(View v) {
			BatteryInfoBroadcastReceiver receiver = null;
			receiver = new BatteryInfoBroadcastReceiver();
			IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			BatteryInfoAct.this.registerReceiver(receiver, filter);
		}
	}

	class MybatteryLevelRcvr extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
				StringBuilder sb = new StringBuilder();
				int rawlevel = intent.getIntExtra("level", -1);
				int scale = intent.getIntExtra("scale", -1);
				int status = intent.getIntExtra("status", -1);
				int health = intent.getIntExtra("health", -1);
				int level = -1; // percentage, or -1 for unknown
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}
				sb.append("The phone");
				if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {
					sb.append("'s battery feels very hot!");
				}
				else {
					switch (status) {
						case BatteryManager.BATTERY_STATUS_UNKNOWN:
							sb.append("no battery.");
							break;
						case BatteryManager.BATTERY_STATUS_CHARGING:
							sb.append("'s battery");
							if (level <= 33) sb.append(" is charging, battery level is low" + "[" + level + "]");
							else if (level <= 84) sb.append(" is charging." + "[" + level + "]");
							else sb.append(" will be fully charged.");
							break;
						case BatteryManager.BATTERY_STATUS_DISCHARGING:
						case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
							if (level == 0) sb.append(" needs charging right away.");
							else if (level > 0 && level <= 33) sb.append(" is about ready to be recharged, battery level is low" + "[" + level + "]");
							else sb.append("'s battery level is" + "[" + level + "]");
							break;
						case BatteryManager.BATTERY_STATUS_FULL:
							sb.append(" is fully charged.");
							break;
						default:
							sb.append("'s battery is indescribable!");
							break;
					}
				}
				sb.append(' ');
				batterLevel.setText(sb.toString());
			}
		}
	}
}