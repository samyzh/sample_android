package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.printer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.printer.action.BluetoothAction;

/**
 * @description：
 * 这边也用的模拟的是Web中的MVC
 * @author samy
 * @date 2015-3-27 下午10:48:05
 */
public class BluetoothPrinterAct extends Activity {

	private Context context = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		setTitle("蓝牙打印");
		setContentView(R.layout.activity_a26setting_bluetooth_projects_printer_bluetoothprinter);
		initListener();
	}

	private void initListener() {
		ListView unbondDevices = (ListView) this.findViewById(R.id.unbondDevices);
		ListView bondDevices = (ListView) this.findViewById(R.id.bondDevices);
		Button switchBT = (Button) this.findViewById(R.id.openBluetooth_tb);
		Button searchDevices = (Button) this.findViewById(R.id.searchDevices);

		BluetoothAction bluetoothAction = new BluetoothAction(this.context, unbondDevices, bondDevices, switchBT, searchDevices, BluetoothPrinterAct.this);

		Button returnButton = (Button) this.findViewById(R.id.return_Bluetooth_btn);
		bluetoothAction.setSearchDevices(searchDevices);
		bluetoothAction.initView();

		switchBT.setOnClickListener(bluetoothAction);
		searchDevices.setOnClickListener(bluetoothAction);
		returnButton.setOnClickListener(bluetoothAction);
	}

}
