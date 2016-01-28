package com.zsy.frame.sample.control.android.a26setting.bluetooth.ble;

import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

/**
 * 
 * Bluetooth LE 将不仅是可穿戴技术的核心技术; Bluetooth Low Energy(低功耗蓝牙)，缩写为Bluetooth LE，或BLE
 *                        Activity for scanning and displaying available Bluetooth LE devices.
 *                        Bluetooth 与 Bluetooth LE 的区别
 *                        第一个主要区别是在配对过程。
 *                        另一个主要的区别是通信本身。
 * 	 * a) BluetoothGattServer作为周边来提供数据；BluetoothGattServerCallback返回周边的状态。
	 * b) BluetoothGatt作为中央来使用和处理数据；BluetoothGattCallback返回中央的状态和周边提供的数据。
	 * 
	 * 一.创建一个周边（虽然目前周边API在Android手机上不工作，但还是看看）
	 *  a）先看看周边用到的class，蓝色椭圆
	 *  b）说明：
	每一个周边BluetoothGattServer，包含多个服务Service，每一个Service包含多个特征Characteristic。
	1.new一个特征：character = new BluetoothGattCharacteristic(
		UUID.fromString(characteristicUUID),
		BluetoothGattCharacteristic.PROPERTY_NOTIFY,
		BluetoothGattCharacteristic.PERMISSION_READ);
	2.new一个服务：service = new BluetoothGattService(UUID.fromString(serviceUUID),
		BluetoothGattService.SERVICE_TYPE_PRIMARY);
	3.把特征添加到服务：service.addCharacteristic(character);
	4.获取BluetoothManager：manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
	5.获取/打开周边：BluetoothGattServer server = manager.openGattServer(this,
		new BluetoothGattServerCallback(){...}); 
	6.把service添加到周边：server.addService(service);
	7.开始广播service：Google还没有广播Service的API，等吧！！！！！所以目前我们还不能让一个Android手机作为周边来提供数据。
	
	二.创建一个中央（这次不会让你失望，可以成功创建并且连接到周边的）
	a）先看看中央用到的class，蓝色椭圆
	b）说明：

	为了拿到中央BluetoothGatt，可要爬山涉水十八弯：
	1.先拿到BluetoothManager：bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
	2.再拿到BluetoothAdapt：btAdapter = bluetoothManager.getAdapter();
	3.开始扫描：btAdapter.startLeScan( BluetoothAdapter.LeScanCallback);
	4.从LeScanCallback中得到BluetoothDevice：public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {.....}
	5.用BluetoothDevice得到BluetoothGatt：gatt = device.connectGatt(this, true, gattCallback);
	终于拿到中央BluetoothGatt了，它有一堆方法（查API吧），调用这些方法，你就可以通过BluetoothGattCallback和周边BluetoothGattServer交互了。
 */
public class BLEBaseAct extends BaseAct {
	@BindView(id = R.id.ble_periphery_btn, click = true)
	private Button ble_periphery_btn;
	@BindView(id = R.id.ble_central_btn, click = true)
	private Button ble_central_btn;

	public BLEBaseAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a26setting_bluetooth_ble_base);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.ble_periphery_btn:
				showActivity(aty, BLEPeripheryAct.class);
				break;
			case R.id.ble_central_btn:
				showActivity(aty, BLECentralAct.class);
				break;
		}
	}
}
