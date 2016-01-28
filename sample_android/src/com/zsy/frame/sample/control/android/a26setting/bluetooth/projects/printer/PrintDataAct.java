package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.printer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.printer.action.PrintDataAction;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.printer.service.PrintDataService;

/**
 * @description：这个就是连接蓝牙设备，通信传递指令数据格式
 * @author samy
 * @date 2015-3-27 下午11:02:05
 */
public class PrintDataAct extends Activity {
	private Context context = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("蓝牙打印");
		this.setContentView(R.layout.activity_a26setting_bluetooth_projects_printer_printdata);
		this.context = this;
		initListener();
	}

	/**  
	 * 获得从上一个Activity传来的蓝牙地址  
	 * @return String  
	 */
	private String getDeviceAddress() {
		// 直接通过Context类的getIntent()即可获取Intent
		Intent intent = this.getIntent();
		// 判断
		if (intent != null) {
			return intent.getStringExtra("deviceAddress");
		}
		else {
			return null;
		}
	}

	private void initListener() {
		TextView deviceName = (TextView) this.findViewById(R.id.device_name);
		TextView connectState = (TextView) this.findViewById(R.id.connect_state);

		PrintDataAction printDataAction = new PrintDataAction(this.context, this.getDeviceAddress(), deviceName, connectState);

		EditText printData = (EditText) this.findViewById(R.id.print_data);
		Button send = (Button) this.findViewById(R.id.send);
		Button command = (Button) this.findViewById(R.id.command);
		printDataAction.setPrintData(printData);

		send.setOnClickListener(printDataAction);
		command.setOnClickListener(printDataAction);
	}

	@Override
	protected void onDestroy() {
		PrintDataService.disconnect();
		super.onDestroy();
	}

}