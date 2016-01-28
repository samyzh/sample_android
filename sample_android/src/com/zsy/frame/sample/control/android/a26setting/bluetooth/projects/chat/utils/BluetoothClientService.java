package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class BluetoothClientService extends Service {
	/**搜索到的远程设备集合*/
	private List<BluetoothDevice> discoveredDevices = new ArrayList<BluetoothDevice>();
	/**蓝牙适配器*/
	private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	/**蓝牙通讯线程*/
	private BluetoothCommunThread communThread;

	private boolean TempB = false;// 判断是否是主动取消的搜索

	// 控制信息广播的接收器
	private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothTools.ACTION_SELECTED_DEVICE.equals(action)) {
				// 选择了连接的服务器设备
				BluetoothDevice device = (BluetoothDevice) intent.getExtras().get(BluetoothTools.DEVICE);
				// 开启设备连接线程
				new BluetoothClientConnThread(handler, device).start();
			}
			else if (BluetoothTools.ACTION_STOP_SERVICE.equals(action)) {
				// 停止后台服务
				if (communThread != null) {
					communThread.isRun = false;
				}
				stopSelf();
			}
			else if (BluetoothTools.ACTION_DATA_TO_SERVICE.equals(action)) {
				// 获取数据
				Object data = intent.getSerializableExtra(BluetoothTools.DATA);
				if (communThread != null) {
					communThread.writeObject(data);
				}
			}
		}
	};

	// 蓝牙搜索广播的接收器
	private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取广播的Action
			String action = intent.getAction();

			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// 开始搜索
			}
			else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// 发现远程蓝牙设备
				// 获取设备
				BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				String address = bluetoothDevice.getAddress();
				String name = bluetoothDevice.getName();
				Log.e("this bluetooth address", address);
				Log.e("this bluetooth name", name);

				// 这里搜索到与地址匹配的手机后，发送广播，由注册了该广播的Receiver进行连接操作
				if (address.equals(BluetoothTools.BluetoothAddress) || address.equals(BluetoothTools.BluetoothAddress2)) {
					TempB = true;
					bluetoothAdapter.cancelDiscovery();// 取消搜索
					// 将广播发送出去
					Intent selectDeviceIntent = new Intent(BluetoothTools.ACTION_SELECTED_DEVICE);
					selectDeviceIntent.putExtra(BluetoothTools.DEVICE, bluetoothDevice);
					sendBroadcast(selectDeviceIntent);
				}

			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				// 搜索结束，如果不是主动取消的搜索，就发送广播
				if (!TempB) {
					// 若未找到设备，则发动未发现设备广播
					Intent foundIntent = new Intent(BluetoothTools.ACTION_NOT_FOUND_SERVER);
					sendBroadcast(foundIntent);
				}
			}
			else if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
				// 搜索完成，未发现设备，继续调用搜索
				bluetoothAdapter.cancelDiscovery();// 取消搜索
				try {
					Thread.sleep(5000);// 休眠,防止低端手机出错
				}
				catch (Exception e) {
					Log.e("sleep", e.getMessage());
				}
				bluetoothAdapter.startDiscovery(); // 开始搜索
			}
		}
	};

	// 接收其他线程消息的Handler
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 处理消息
			switch (msg.what) {
				case BluetoothTools.MESSAGE_CONNECT_ERROR:
					// 连接错误
					// 发送连接错误广播
					Intent errorIntent = new Intent(BluetoothTools.ACTION_CONNECT_ERROR);
					sendBroadcast(errorIntent);
					break;
				case BluetoothTools.MESSAGE_CONNECT_SUCCESS:
					// 连接成功

					// 开启通讯线程
					communThread = new BluetoothCommunThread(handler, (BluetoothSocket) msg.obj);
					communThread.start();

					// 发送连接成功广播
					Intent succIntent = new Intent(BluetoothTools.ACTION_CONNECT_SUCCESS);
					sendBroadcast(succIntent);
					break;
				case BluetoothTools.MESSAGE_READ_OBJECT:
					// 读取到对象
					// 发送数据广播（包含数据对象）
					Intent dataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_GAME);
					dataIntent.putExtra(BluetoothTools.DATA, (Serializable) msg.obj);
					sendBroadcast(dataIntent);
					break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 获取通讯线程
	 * 
	 * @return
	 */
	public BluetoothCommunThread getBluetoothCommunThread() {
		return communThread;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * Service创建时的回调函数
	 */
	@Override
	public void onCreate() {
		// discoveryReceiver的IntentFilter
		IntentFilter discoveryFilter = new IntentFilter();
		discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
		discoveryFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);

		// controlReceiver的IntentFilter
		IntentFilter controlFilter = new IntentFilter();
		controlFilter.addAction(BluetoothTools.ACTION_SELECTED_DEVICE);
		controlFilter.addAction(BluetoothTools.ACTION_STOP_SERVICE);
		controlFilter.addAction(BluetoothTools.ACTION_DATA_TO_SERVICE);

		// 注册BroadcastReceiver
		registerReceiver(discoveryReceiver, discoveryFilter);
		registerReceiver(controlReceiver, controlFilter);

		discoveredDevices.clear(); // 清空存放设备的集合

		// 直接开启，不经过提示
		bluetoothAdapter.enable(); // 打开蓝牙
		try {
			Thread.sleep(5000);// 休眠1秒
		}
		catch (Exception e) {
			Log.e("sleep", e.getMessage());
		}
		bluetoothAdapter.startDiscovery(); // 开始搜索
		super.onCreate();
	}

	/**
	 * @description：设置蓝牙;完全的启动蓝牙逻辑，应放在Activity中更好；
	 * @author samy
	 * @date 2015-2-10 下午8:54:54
	 */
	// private void setBluetooth() {
	// // bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	// if (bluetoothAdapter != null) { // Device support Bluetooth
	// // 确认开启蓝牙
	// if (!bluetoothAdapter.isEnabled()) {
	// // 请求用户开启
	// Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	// startActivityForResult(intent, RESULT_FIRST_USER);
	// // 使蓝牙设备可见，方便配对
	// Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	// in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
	// startActivity(in);
	// // 直接开启，不经过提示
	// bluetoothAdapter.enable();
	// }
	// }
	// else { // Device does not support Bluetooth
	// AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	// dialog.setTitle("No bluetooth devices");
	// dialog.setMessage("Your equipment does not support bluetooth, please change device");
	// dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	// }
	// });
	// dialog.show();
	// }
	// }

	/**
	 * Service销毁时的回调函数
	 */
	@Override
	public void onDestroy() {
		if (communThread != null) {
			communThread.isRun = false;
		}
		// 解除绑定
		unregisterReceiver(discoveryReceiver);
		// if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
		// bluetoothAdapter.cancelDiscovery();
		// }
		bluetoothAdapter.disable();// 关闭蓝牙
		super.onDestroy();
	}
}
