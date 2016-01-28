package com.zsy.frame.sample.control.android.a26setting.bluetooth.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.helps.ClsUtils;

/**
 * @description：蓝牙配对连接演示
 *  蓝牙要想通信目前是必须要先配对才能连接的。
 *  蓝牙配对的api是hide的。但是api19可以直接调用蓝牙设备的配对方法。
 *  所以配对都是利用反射的方法
 * 
 * 蓝牙连接目前可以有三种连接方式；
 * 方法1.先进行蓝牙自动配对，配对成功，通过UUID获得BluetoothSocket,然后执行connect()方法。
 * 方法2.通过UUID获得BluetoothSocket,然后先根据mDevice.getBondState()进行判断是否需要配对，最后执行connnect()方法。
 * 方法3.利用反射通过端口获得BluetoothSocket,然后执行connect()方法。
 * 
 * 注意要点；
 * 1.蓝牙配对和连接是两回事，不可混为一谈。
 * 2.蓝牙串口连接可通过端口 （1-30）和UUID两种方法进行操作。（重点）
 * 3.通过UUID进行蓝牙连接最好先进行配对操作。
 * @author samy
 * @date 2015-2-8 下午1:51:58
 */
public class BTPairConnectAct extends BaseAct implements OnItemClickListener {
	public static String ErrorMessage;
	Button btnSearch, btnDis, btnExit;
	ToggleButton tbtnSwitch;
	ListView lvBTDevices;
	ArrayAdapter<String> devicesArrayAdapter;
	List<String> deviceList = new ArrayList<String>();
	BluetoothAdapter bluetoothAdapter;
	public static BluetoothSocket btSocket;
	private Button setBtn;

	List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();

	public BTPairConnectAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a26setting_bluetooth_pair_connect);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void registerBroadcast() {
		super.registerBroadcast();

		// 注册Receiver来获取蓝牙设备相关的结果
		// BluetoothDevice.ACTION_PAIRING_REQUEST
		// String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		// Field requires API level 19 (current min is 14): android.bluetooth.BluetoothDevice#ACTION_PAIRING_REQUEST
		// 在配对的时候会有一个配对广播，可以自定义一个广播接受者获取配对广播，然后在这个广播接收者里设置pin值，取消确定对话框，实现自动配对
		// 在低版本方法处理：在的4.2系统上是没有效果的；两种解决方法：（1）修改setting 系统源码，（2）模拟点击事件。(默认设置不用对话框匹配)
		intent.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(searchDevices, intent);
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		// if(!ListBluetoothDevice())finish();
		setBtn = (Button) findViewById(R.id.button1);
		ErrorMessage = "";
		// ---------------------------------------------------
		btnSearch = (Button) this.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		btnExit = (Button) this.findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);
		btnDis = (Button) this.findViewById(R.id.btnDis);
		btnDis.setOnClickListener(this);
		// ToogleButton设置
		tbtnSwitch = (ToggleButton) this.findViewById(R.id.toggleButton1);
		tbtnSwitch.setOnClickListener(this);
		// ListView及其数据源 适配器
		lvBTDevices = (ListView) this.findViewById(R.id.listView1);
		devicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList);
		lvBTDevices.setAdapter(devicesArrayAdapter);
		lvBTDevices.setOnItemClickListener(this);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		if (bluetoothAdapter.isEnabled()) {
			tbtnSwitch.setChecked(false);
		}
		else {
			tbtnSwitch.setChecked(true);
		}
		setBluetooth();

		/**
		 * 查询和取消设置查询
		 */
		setBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (bluetoothAdapter.isDiscovering()) {
					bluetoothAdapter.cancelDiscovery();
					setBtn.setText("repeat search");
				}
				else {
					findAvalibleDevice();
					bluetoothAdapter.startDiscovery();
					setBtn.setText("stop search");
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
			case RESULT_OK:
				findAvalibleDevice();
				break;
			case RESULT_CANCELED:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @description：找到可用的设备填充数据Adapter
	 * @author samy
	 * @date 2015-2-10 下午9:01:43
	 */
	private void findAvalibleDevice() {
		// 获取可配对蓝牙设备
		Set<BluetoothDevice> device = bluetoothAdapter.getBondedDevices();
		if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
			deviceList.clear();
			devicesArrayAdapter.notifyDataSetChanged();
		}
		if (device.size() > 0) { // 存在已经配对过的蓝牙设备
			for (Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();) {
				BluetoothDevice btd = it.next();
				// if (lstDevices.indexOf(str) == -1) { // 防止重复添加
				// lstDevices.add(str);
				// } // 获取设备名称和mac地址
				deviceList.add(btd.getName() + '\n' + btd.getAddress());
				devicesArrayAdapter.notifyDataSetChanged();
			}
		}
		else { // 不存在已经配对过的蓝牙设备
			deviceList.add("No can be matched to use bluetooth");
			devicesArrayAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * @description：设置蓝牙;完全的启动蓝牙逻辑，应放在Activity中更好；
	 * @author samy
	 * @date 2015-2-10 下午8:54:54
	 */
	private void setBluetooth() {
		// bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter != null) { // Device support Bluetooth
			// 确认开启蓝牙
			if (!bluetoothAdapter.isEnabled()) {
				// 请求用户开启
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, RESULT_FIRST_USER);
				// 使蓝牙设备可见，方便配对
				Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
				startActivity(in);
				// 直接开启，不经过提示
				bluetoothAdapter.enable();
			}
		}
		else { // Device does not support Bluetooth
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("No bluetooth devices");
			dialog.setMessage("Your equipment does not support bluetooth, please change device");
			dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			dialog.show();
		}
	}

	private BroadcastReceiver searchDevices = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();

			// 显示所有收到的消息及其细节
			for (int i = 0; i < lstName.length; i++) {
				String keyName = lstName[i].toString();
				Log.e(keyName, String.valueOf(b.get(keyName)));
			}
			BluetoothDevice device = null;
			// 搜索设备时，取得设备的MAC地址
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				doDeviceSearch(intent);
			}
			else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
				doStateChange(intent);
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // 搜索结束
				if (devicesArrayAdapter.getCount() == 0) {
					deviceList.add("No can be matched to use bluetooth");
					devicesArrayAdapter.notifyDataSetChanged();
				}
				setBtn.setText("repeat search");
			}
			else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
				BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// byte[] pinBytes = BluetoothDevice.convertPinToBytes("1234");
				// device.setPin(pinBytes);
				// doRequestPari(intent);
				try {
					ClsUtils.setPin(btDevice.getClass(), btDevice, "0000"); // 手机和蓝牙采集器配对
					ClsUtils.createBond(btDevice.getClass(), btDevice);
					ClsUtils.cancelPairingUserInput(btDevice.getClass(), btDevice);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void doDeviceSearch(Intent intent) {
			BluetoothDevice device;
			device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if (device.getBondState() == BluetoothDevice.BOND_NONE) { // 搜索没有配过对的蓝牙设备
				// if (btd.getBondState() != BluetoothDevice.BOND_BONDED) {
				String str = "未配对|" + device.getName() + "|" + device.getAddress();
				if (deviceList.indexOf(str) == -1) { // 防止重复添加
					deviceList.add(str);
				} // 获取设备名称和mac地址
				devicesArrayAdapter.notifyDataSetChanged();
				try {
					ClsUtils.setPin(device.getClass(), device, "0000");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ClsUtils.cancelPairingUserInput(device.getClass(), device);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	private void doStateChange(Intent intent) {
		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		switch (device.getBondState()) {
			case BluetoothDevice.BOND_BONDING:
				Log.d("BlueToothTestActivity", "正在配对......");
				break;
			case BluetoothDevice.BOND_BONDED:
				Log.d("BlueToothTestActivity", "完成配对");
				connect(device);// 连接设备
				break;
			case BluetoothDevice.BOND_NONE:
				Log.d("BlueToothTestActivity", "取消配对");
			default:
				break;
		}
	}

	private void doDeviceSearchFinished(Intent intent) {
		// McLog.i("search finished, searchDevices's size = " + searchDevices.size());
		boolean isFindRightDevice = false;
		for (BluetoothDevice device : bluetoothDevices) {
			// if (isRightDevice(device)) { // 如果是我想要的设备，那么我就进行配对
			// doNormalPair(device);
			// isFindRightDevice = true;
			// break;
			// }
		}
		if (!isFindRightDevice) {
			// McLog.i("sorry,do't search user's device[" + bleDeviceMac + "]");
		}
	}

	private void doNormalPair(BluetoothDevice device) {
		// McLog.mByStackTrace();
		// currentPairDevice = device;
		try {
			// 调用配对的方法，此方法是异步的，系统会触发BluetoothDevice.ACTION_PAIRING_REQUEST的广播
			// 收到此广播后，设置配对的密码
			ClsUtils.createBond(BluetoothDevice.class, device);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doRequestPari(Intent intent) {
		// BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		// if (btDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
		// // McLog.e("已经绑定");
		// }
		// else {
		// // McLog.e("没有绑定");
		// if (currentPairDevice != null && btDevice.getAddress().equalsIgnoreCase(currentPairDevice.getAddress())) {
		// try {
		// McLog.i("invoke setpin :" + bleDeviceMac);
		// // 设置配对的密码 <span style="font-family: Arial, Helvetica, sans-serif;">bleDevicePasswd 设备的配对密码</span>
		// ClsUtils.setPin2(BluetoothDevice.class, currentPairDevice, bleDevicePasswd);
		// }
		// catch (Exception e) {
		// McLog.i("e = " + e);
		// }
		// currentPairDevice = null;
		// uiHandler.postDelayed(new Runnable() {
		// public void run() {
		// loadBleData();
		// // 注销监听
		// unregisterReceiver(bleReceiver);
		// }
		// }, 1000);
		// }
		// }
	}

	private void connect(BluetoothDevice btDev) {
		// if (btDev.getBondState() == BluetoothDevice.BOND_BONDED) {// 已经配对的则跳过
		// } else {
		// if (!bluetoothDevices.contains(btDev)) {
		// bluetoothDevices.add(btDev);
		// }
		// }

		final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		UUID uuid = SPP_UUID;
		try {
			// 在做android蓝牙串口连接的时候一般会使用
			// 然后是tmp赋给BluetoothSocket,接着调用connect方法进行蓝牙设备的连接。
			// 可是 BluetoothSocket 的connect方法本身就会报很多异常错误。
			btSocket = btDev.createRfcommSocketToServiceRecord(uuid);
			Log.d("BlueToothTestActivity", "开始连接...");
			btSocket.connect();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// // 进行配对
	// private void onPair(String value) {
	// // 根据类型进行分发
	// switch (mType) {
	// case BluetoothDevice.PAIRING_VARIANT_PIN:
	// // 注意这里是用了转换的方法，不是直接调用value.getBytes();方法
	// byte[] pinBytes = BluetoothDevice.convertPinToBytes(value);
	// if (pinBytes == null) {
	// return;
	// }
	// // 直接调用setPin方法,然后就没有了，等到收到状态改变的广播后就进行dismiss,请看54行的mReceiver
	// mDevice.setPin(pinBytes);
	// break;
	//
	// case BluetoothDevice.PAIRING_VARIANT_PASSKEY:
	// int passkey = Integer.parseInt(value);
	// mDevice.setPasskey(passkey);
	// break;
	//
	// case BluetoothDevice.PAIRING_VARIANT_PASSKEY_CONFIRMATION:
	// case BluetoothDevice.PAIRING_VARIANT_CONSENT:
	// mDevice.setPairingConfirmation(true);
	// break;
	//
	// case BluetoothDevice.PAIRING_VARIANT_DISPLAY_PASSKEY:
	// case BluetoothDevice.PAIRING_VARIANT_DISPLAY_PIN:
	// // Do nothing.
	// break;
	//
	// case BluetoothDevice.PAIRING_VARIANT_OOB_CONSENT:
	// mDevice.setRemoteOutOfBandData();
	// break;
	// }
	// }

	@Override
	public void unRegisterBroadcast() {
		if (searchDevices != null) {
			this.unregisterReceiver(searchDevices);
		}
		super.unRegisterBroadcast();
	}

	public void showMessage(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		// switch (v.getId()) {
		if (v == btnSearch) {// 搜索蓝牙设备，在BroadcastReceiver显示结果
			if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {// 如果蓝牙还没开启
				Toast.makeText(BTPairConnectAct.this, "请先打开蓝牙", 1000).show();
				return;
			}
			if (bluetoothAdapter.isDiscovering()) bluetoothAdapter.cancelDiscovery();
			deviceList.clear();
			Object[] lstDevice = bluetoothAdapter.getBondedDevices().toArray();
			for (int i = 0; i < lstDevice.length; i++) {
				BluetoothDevice device = (BluetoothDevice) lstDevice[i];
				String str = "已配对|" + device.getName() + "|" + device.getAddress();
				deviceList.add(str); // 获取设备名称和mac地址
				devicesArrayAdapter.notifyDataSetChanged();
			}
			setTitle("本机蓝牙地址：" + bluetoothAdapter.getAddress());
			bluetoothAdapter.startDiscovery();
		}
		else if (v == tbtnSwitch) {// 本机蓝牙启动/关闭
			if (tbtnSwitch.isChecked() == false) bluetoothAdapter.enable();

			else if (tbtnSwitch.isChecked() == true) bluetoothAdapter.disable();
		}
		else if (v == btnDis) {// 本机可以被搜索
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
		else if (v == btnExit) {
			try {
				if (btSocket != null) btSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finish();
		}
		// else if(v==btnShow){//显示BluetoothDevice的所有方法和常量，包括隐藏API
		// ClsUtils.printAllInform(btDevice.getClass());
		// }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final String msg = deviceList.get(position);
		if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.cancelDiscovery();
			setBtn.setText("repeat search");
		}

		// /**
		// * 定义一个弹出框对象
		// */
		// AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		// dialog.setTitle("Confirmed connecting device");
		// dialog.setMessage(msg);
		// dialog.setPositiveButton("connect",
		// new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// BluetoothMsg.BlueToothAddress=msg.substring(msg.length()-17);
		//
		// if(BluetoothMsg.lastblueToothAddress!=BluetoothMsg.BlueToothAddress){
		// BluetoothMsg.lastblueToothAddress=BluetoothMsg.BlueToothAddress;
		// }
		//
		// Intent in=new Intent(SearchDeviceActivity.this,BluetoothActivity.class);
		// startActivity(in);
		// }
		// });
		// dialog.setNegativeButton("cancel",
		// new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// BluetoothMsg.BlueToothAddress = null;
		// }
		// });
		// dialog.show();

		String str = deviceList.get(position);
		String[] values = str.split("\\|");
		String address = values[2];
		Log.e("address", values[2]);
		BluetoothDevice btDev = bluetoothAdapter.getRemoteDevice(address);
		try {
			Boolean returnValue = false;
			if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {
				// 利用反射方法调用BluetoothDevice.createBond(BluetoothDevice remoteDevice);
				// Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
				// Log.d("BlueToothTestActivity", "开始配对");
				// returnValue = (Boolean) createBondMethod.invoke(btDev);
				Toast.makeText(aty, "由未配对转为已配对()取消对话框方式", 500).show();
				ClsUtils.pair(address, "0000");
				showMessage("here");
			}
			else if (btDev.getBondState() == BluetoothDevice.BOND_BONDED) {
				connect(btDev);
				// Toast.makeText(aty, "由已配对转为未配对", 500).show();
				// ClsUtils.removeBond(btDev.getClass(), btDev);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /**
	// * @description：方法二
	// * @author samy
	// * @date 2015-2-8 下午3:06:14
	// */
	// private class ConnectThread extends Thread {
	// String macAddress = "";
	//
	// public ConnectThread(String mac) {
	// macAddress = mac;
	// }
	//
	// public void run() {
	// connecting = true;
	// connected = false;
	// if (mBluetoothAdapter == null) {
	// mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	// }
	// mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
	// mBluetoothAdapter.cancelDiscovery();
	// try {
	// socket = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
	//
	// }
	// catch (IOException e) {
	// // e.printStackTrace();
	// Log.e(TAG, "Socket", e);
	// }
	// // adapter.cancelDiscovery();
	// while (!connected && connetTime <= 10) {
	// connectDevice();
	// }
	// // 重置ConnectThread
	// // synchronized (BluetoothService.this) {
	// // ConnectThread = null;
	// // }
	// }
	//
	// public void cancel() {
	// try {
	// socket.close();
	// socket = null;
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	// }
	// finally {
	// connecting = false;
	// }
	// }
	// }
	//
	// protected void connectDevice() {
	// try {
	// // 连接建立之前的先配对
	// if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
	// Method creMethod = BluetoothDevice.class.getMethod("createBond");
	// Log.e("TAG", "开始配对");
	// creMethod.invoke(mBluetoothDevice);
	// }
	// else {
	// }
	// }
	// catch (Exception e) {
	// // TODO: handle exception
	// // DisplayMessage("无法配对！");
	// e.printStackTrace();
	// }
	// mBluetoothAdapter.cancelDiscovery();
	// try {
	// socket.connect();
	// // DisplayMessage("连接成功!");
	// // connetTime++;
	// connected = true;
	// }
	// catch (IOException e) {
	// // TODO: handle exception
	// // DisplayMessage("连接失败！");
	// connetTime++;
	// connected = false;
	// try {
	// socket.close();
	// socket = null;
	// }
	// catch (IOException e2) {
	// // TODO: handle exception
	// Log.e(TAG, "Cannot close connection when connection failed");
	// }
	// }
	// finally {
	// connecting = false;
	// }
	// }
	//
	// /**
	// * 方法三：取得BluetoothSocket
	// */
	// private void initSocket() {
	// BluetoothSocket temp = null;
	// try {
	// Method m = mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
	// temp = (BluetoothSocket) m.invoke(mBluetoothDevice, 1);// 这里端口为1
	// }
	// catch (SecurityException e) {
	// e.printStackTrace();
	// }
	// catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// }
	// catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// }
	// catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// socket = temp;
	// }

}
