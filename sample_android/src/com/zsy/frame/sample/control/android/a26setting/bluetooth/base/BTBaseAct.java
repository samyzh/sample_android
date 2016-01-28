package com.zsy.frame.sample.control.android.a26setting.bluetooth.base;

import java.util.Iterator;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

/**
 * @description：蓝牙基础功能测试类
 * @author samy
 * @date 2015-2-4 上午12:23:00
 */
public class BTBaseAct extends BaseAct {
	private static String TAG = "Bluetooth_State";

	private BluetoothAdapter mBluetoothAdapter; // 本机蓝牙适配器对象

	private TextView btDesc;
	private TextView find_dev_desc_tv;

	private Button btOpen;

	private Button btClose;

	private Button btOpenBySystem; // 调用系统API去打开蓝牙

	private Button btDiscoveryDevice;

	private Button btCancelDiscovery;

	private Button btDiscoveryBySystem; // 调用系统Api去扫描蓝牙设备

	private final int REQUEST_OPEN_BT_CODE = 1;
	private final int REQUEST_DISCOVERY_BT_CODE = 2;

	public BTBaseAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a26setting_bluetooth_base_bt);
	}

	@Override
	public void registerBroadcast() {
		super.registerBroadcast();
		// ACTION_STATE_CHANGED 蓝牙状态值发生改变
		// ACTION_SCAN_MODE_CHANGED 蓝牙扫描状态(SCAN_MODE)发生改变
		// ACTION_LOCAL_NAME_CHANGED 蓝牙设备Name发生改变

		// ACTION_REQUEST_DISCOVERABLE 请求用户选择是否使该蓝牙能被扫描
		// PS：如果蓝牙没有开启，用户点击确定后，会首先开启蓝牙，继而设置蓝牙能被扫描。
		// ACTION_REQUEST_ENABLE 请求用户选择是否打开蓝牙

		// ACTION_DISCOVERY_STARTED 蓝牙扫描过程开始
		// ACTION_DISCOVERY_FINISHED 蓝牙扫描过程结束
		// ACTION_FOUND (该常量字段位于BluetoothDevice类中，稍后讲到)
		// 说明：蓝牙扫描时，扫描到任一远程蓝牙设备时，会发送此广播。

		IntentFilter bluetoothFilter = new IntentFilter();
		bluetoothFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		bluetoothFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		this.registerReceiver(BluetoothReciever, bluetoothFilter);

		IntentFilter btDiscoveryFilter = new IntentFilter();
		btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		btDiscoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
		btDiscoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		this.registerReceiver(BTDiscoveryReceiver, btDiscoveryFilter);
	}

	@Override
	public void unRegisterBroadcast() {
		super.unRegisterBroadcast();
		if (null != BTDiscoveryReceiver) {
			unregisterReceiver(BTDiscoveryReceiver);
		}
		if (null != BluetoothReciever) {
			unregisterReceiver(BluetoothReciever);
		}
	}

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);

		// 了解对蓝牙操作一个核心类BluetoothAdapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 获得本机蓝牙适配器对象引用
		if (mBluetoothAdapter == null) {
			toast("对不起 ，您的机器不具备蓝牙功能");
			return;
		}

		// 拿到当前进入界面蓝牙的状态
		int initialBTState = mBluetoothAdapter.getState();
		printBTState(initialBTState); // 初始时蓝牙状态
		initViewsAndListener();

		btDesc.setText("当前设备信息： Name : " + mBluetoothAdapter.getName() + " \nAddress : " + mBluetoothAdapter.getAddress() + "\n Scan Mode --" + mBluetoothAdapter.getScanMode());

		// 打印处当前已经绑定成功的蓝牙设备//获取可配对蓝牙设备；正常情况下应该放到一个List里面再次放到Listview中显示出来
		Set<BluetoothDevice> bts = mBluetoothAdapter.getBondedDevices();
		Iterator<BluetoothDevice> iterator = bts.iterator();
		while (iterator.hasNext()) {
			BluetoothDevice bd = iterator.next();
			Log.i(TAG, " Name : " + bd.getName() + " Address : " + bd.getAddress());
			Log.i(TAG, "Device class" + bd.getBluetoothClass());
		}
		// // MAC地址，由DeviceListActivity设置返回
		// String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// // 得到蓝牙设备句柄
		// _device = _bluetooth.getRemoteDevice(address);
		BluetoothDevice findDevice = mBluetoothAdapter.getRemoteDevice("00:11:22:33:AA:BB");

		Log.i(TAG, "findDevice Name : " + findDevice.getName() + "  findDevice Address : " + findDevice.getAddress());
		Log.i(TAG, "findDevice class" + findDevice.getBluetoothClass());

	}

	private void initViewsAndListener() {
		btDesc = (TextView) findViewById(R.id.btDesc);
		find_dev_desc_tv = (TextView) findViewById(R.id.find_dev_desc_tv);
		btOpen = (Button) findViewById(R.id.btOpen);
		btClose = (Button) findViewById(R.id.btClose);
		btOpenBySystem = (Button) findViewById(R.id.btOpenBySystem);
		btDiscoveryDevice = (Button) findViewById(R.id.btDiscoveryDevice);
		btCancelDiscovery = (Button) findViewById(R.id.btCancelDiscovery);
		btDiscoveryBySystem = (Button) findViewById(R.id.btDiscoveryBySystem);

		btOpen.setOnClickListener(this);
		btClose.setOnClickListener(this);
		btOpenBySystem.setOnClickListener(this);
		btDiscoveryDevice.setOnClickListener(this);
		btCancelDiscovery.setOnClickListener(this);
		btDiscoveryBySystem.setOnClickListener(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_OPEN_BT_CODE) {
			if (resultCode == RESULT_CANCELED) {
				toast("Sorry , 用户拒绝了您的打开蓝牙请求.");
			}
			else toast("Year , 用户允许了您的打开蓝牙请求.");
		}
		else if (requestCode == REQUEST_DISCOVERY_BT_CODE) {
			if (resultCode == RESULT_CANCELED) {
				toast("Sorry , 用户拒绝了，您的蓝牙不能被扫描.");
			}
			else toast("Year , 用户允许了，您的蓝牙能被扫描");
		}
	}

	/**
	 * @author samy
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		boolean wasBtOpened = mBluetoothAdapter.isEnabled(); // 是否已经打开
		switch (v.getId()) {
		/**
		 *  配置本机蓝牙模;其中包括--打开、关闭、
		 */
			case R.id.btOpen: // 直接打开蓝牙 【打开蓝牙的方法一】
				boolean result = mBluetoothAdapter.enable();
				if (result) {
					toast("蓝牙打开操作成功");
				}
				else if (wasBtOpened) {
					toast("蓝牙已经打开了");
				}
				else {
					toast("蓝牙打开失败");
				}
				break;
			case R.id.btOpenBySystem: // 直接打开系统的蓝牙设置面板,第一次操作会弹对话框设置，再次操作时不会【打开蓝牙的方法二】
				Log.i(TAG, " ## click btOpenBySystem ##");
				if (!wasBtOpened) { // 未打开蓝牙，才需要打开蓝牙
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					// startActivityForResult(intent, 0x1);
					startActivityForResult(intent, REQUEST_OPEN_BT_CODE);
				}
				else {
					toast("Hi ，蓝牙已经打开了，不需要在打开啦 ~~~");
				}
				break;
			case R.id.btClose: // 关闭蓝牙
				boolean result1 = mBluetoothAdapter.disable();
				if (result1) {
					toast("蓝牙关闭操作成功");
				}
				else if (!wasBtOpened) {
					toast("蓝牙已经关闭");
				}
				else {
					toast("蓝牙关闭失败.");
				}
				break;
			/**
			 * 搜索蓝牙设备部分
			 * startDiscovery()方法是一个异步方法，调用后会立即返回。该方法会进行对其他蓝牙设备的搜索，该过程会持续12秒。
			 * 该方法调用后，搜索过程实际上是在一个System Service中进行的，
			 * 所以可以调用cancelDiscovery()方法来停止搜索（该方法可以在未执行discovery请求时调用）。
			 */
			/**
			 * 请求Discovery后，系统开始搜索蓝牙设备，在这个过程中，系统会发送以下三个广播：
			 * ACTION_DISCOVERY_START：开始搜索
			 * ACTION_DISCOVERY_FINISHED：搜索结束
			 * ACTION_FOUND：找到设备，这个Intent中包含两个extra fields：EXTRA_DEVICE和EXTRA_CLASS，分别包含BluetooDevice和BluetoothClass。
			 * 我们可以自己注册相应的BroadcastReceiver来接收响应的广播，以便实现某些功能
			 */
			case R.id.btDiscoveryBySystem: // 使蓝牙能被扫描；使设备能够被搜索[调用系统对话框---请求多长时间]
				// 打开本机的蓝牙发现功能（默认打开120秒，可以将时间最多延长至300秒）
				Intent discoveryintent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoveryintent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
				startActivityForResult(discoveryintent, REQUEST_DISCOVERY_BT_CODE);
				break;
			case R.id.btDiscoveryDevice: // 扫描时，必须先打开蓝牙
				if (!mBluetoothAdapter.isDiscovering()) {
					Log.i(TAG, "btCancelDiscovery ### the bluetooth dont't discovering");
					mBluetoothAdapter.startDiscovery();// 其实这里系统在发送广播
				}
				else {
					toast("蓝牙正在搜索设备了 ---- ");
				}
				break;
			case R.id.btCancelDiscovery: // 取消扫描
				if (mBluetoothAdapter.isDiscovering()) {
					Log.i(TAG, "btCancelDiscovery ### the bluetooth is isDiscovering");
					mBluetoothAdapter.cancelDiscovery();
				}
				else {
					toast("蓝牙并未搜索设备 ---- ");
				}
				break;

		}
	}

	/**
	 * 1、 蓝牙绑定(Bound)状态 ， 即蓝牙设备是否与其他蓝牙绑定
	 * int BOND_BONDED 表明蓝牙已经绑定 int BOND_BONDING 表明蓝牙正在绑定过程中 ， bounding int BOND_NONE 表明没有绑定
	 */
	private void printBTState(int btState) {
		switch (btState) {
			case BluetoothAdapter.STATE_OFF:
				toast("蓝牙状态:已关闭");
				Log.i(TAG, "BT State ： BluetoothAdapter.STATE_OFF ###");
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				toast("蓝牙状态:正在关闭");
				Log.i(TAG, "BT State :  BluetoothAdapter.STATE_TURNING_OFF ###");
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				toast("蓝牙状态:正在打开");
				Log.i(TAG, "BT State ：BluetoothAdapter.STATE_TURNING_ON ###");
				break;
			case BluetoothAdapter.STATE_ON:
				toast("蓝牙状态:已打开");
				Log.i(TAG, "BT State ：BluetoothAdapter.STATE_ON ###");
				break;
			default:
				break;
		}
	}

	private void toast(String str) {
		Toast.makeText(BTBaseAct.this, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 蓝牙开个状态以及扫描状态的广播接收器
	 */
	private BroadcastReceiver BluetoothReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
				Log.i(TAG, "### Bluetooth State has changed ##");
				int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
				printBTState(btState);
			}
			else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(intent.getAction())) {
				Log.i(TAG, "### ACTION_SCAN_MODE_CHANGED##");
				int cur_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
				int previous_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
				Log.i(TAG, "### cur_mode_state ##" + cur_mode_state + " ~~ previous_mode_state" + previous_mode_state);
			}
		}

	};

	/**
	 * 蓝牙扫描时的广播接收器
	 */
	private BroadcastReceiver BTDiscoveryReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// Log.i(TAG, "### BT ACTION_DISCOVERY_STARTED ##");
				showToastMsg("ACTION_DISCOVERY_STARTED");
			}
			else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction())) {
				// Log.i(TAG, "### BT ACTION_BOND_STATE_CHANGED ##");
				showToastMsg("ACTION_BOND_STATE_CHANGED");
				int cur_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
				int previous_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_NONE);
				Log.i(TAG, "### cur_bond_state ##" + cur_bond_state + " ~~ previous_bond_state" + previous_bond_state);
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
				// Log.i(TAG, "### BT ACTION_DISCOVERY_FINISHED ##");
				showToastMsg("ACTION_DISCOVERY_FINISHED");// 常用
			}
			else if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
				// Log.i(TAG, "### BT BluetoothDevice.ACTION_FOUND ##");
				showToastMsg("ACTION_FOUND");// 常用
				BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (btDevice != null) {
					// Log.i(TAG, "Name : " + btDevice.getName() + " Address: " + btDevice.getAddress());
//					showToastMsg("Name : " + btDevice.getName() + " Address: " + btDevice.getAddress());
					find_dev_desc_tv.setText("搜索到的设备信息： Name : " + btDevice.getName() + " \nAddress: " + btDevice.getAddress());
				}
			}
		}
	};

}
