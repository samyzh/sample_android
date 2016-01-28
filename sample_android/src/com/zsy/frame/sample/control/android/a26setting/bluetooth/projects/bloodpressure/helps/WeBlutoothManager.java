package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps;

import java.util.List;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BlueToothEvent.GattServicesDiscoveredEvent;

import de.greenrobot.event.EventBus;

/**
 * 蓝牙管理类
 * 蓝牙连接后，启动会自动连接设备。并显示血压值
 */
public class WeBlutoothManager {

	private BluetoothAdapter mBluetoothAdapter;
	BluetoothManager mBluetoothManager;
	private Context mContext;

	private boolean isFindDevices = false;
	Handler mHandler;
	private boolean mScanning;
	private BluetoothLeService mBluetoothLeService;

	private String mDeviceName;
	private String mDeviceAddress;

	/**设置扫描超时 10*1000*/
	private static final long SCAN_PERIOD = 10000;
	List<BluetoothGattService> gattServices;

	public WeBlutoothManager(Context context) {
		this.mContext = context;
		mHandler = new Handler();
	}

	// BluetoothLe服务
	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
			if (!mBluetoothLeService.initialize()) {
				// Log.e(TAG, "Unable to initialize Bluetooth");
				// finish();
			}
			mBluetoothLeService.connect(mDeviceAddress);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};

	@SuppressLint("NewApi")
	public void start() {
		mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = mBluetoothManager.getAdapter();

		// 启动蓝牙搜索
		if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
			scanLeDevice(true);
		}

		EventBus.getDefault().register(this);
	}

	public void reStart() {
		// 启动蓝牙搜索
		if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
			scanLeDevice(true);
		}
	}

	public void stop() {
		try {
			scanLeDevice(false);
			mContext.unbindService(mServiceConnection);
		}
		catch (Exception e) {
		}
		EventBus.getDefault().unregister(this);
	}

	/**
	 * @description：搜索蓝牙设备
	 * @author samy
	 * @date 2015-2-24 下午6:59:41
	 */
	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					if (!isFindDevices) {
						EventBus.getDefault().post(new BlueToothEvent.NotFindDeviceEvent());
					}
				}
			}, SCAN_PERIOD);
			mScanning = true;
			isFindDevices = false;
			mBluetoothAdapter.startLeScan(mLeScanCallback);

		}
		else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	/**
	 * 查找到设备的回调
	 */
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			mDeviceName = device.getName();
			mDeviceAddress = device.getAddress();
			// 指定连接的设备
			if (device.getName().contains("eBlood")) {
				isFindDevices = true;
				scanLeDevice(false);
				Intent gattServiceIntent = new Intent(mContext, BluetoothLeService.class);
				mContext.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
			}

		}
	};

	public void onEventBackgroundThread(GattServicesDiscoveredEvent event) {
		if (mBluetoothLeService == null) { return; }
		gattServices = mBluetoothLeService.getSupportedGattServices();
		if (gattServices != null) {
			for (BluetoothGattService service : gattServices) {
				if (BluetoothLeService.BELT_DEVICE_SERVICE.equalsIgnoreCase(service.getUuid().toString())) {
					List<BluetoothGattCharacteristic> gattCharacteristics = service.getCharacteristics();
					if (gattCharacteristics != null) {
						for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
							// 读取数据的uuid
							// --====0000ffe1-0000-1000-8000-00805f9b34fb
							// 匹配的UUID通讯交互数据； // UUID_KEY_DATA是可以跟蓝牙模块串口通信的Characteristic
							if (BluetoothLeService.BELT_BLOOD_GATT_CHARACTERISTICS.equals(gattCharacteristic.getUuid().toString())) {
								// 接受Characteristic被写的通知,收到蓝牙模块的数据后会触发
								// 设置当指定characteristic值变化时，发出通知。
								mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
							}
						}
					}

				}

			}
		}

	}

}
