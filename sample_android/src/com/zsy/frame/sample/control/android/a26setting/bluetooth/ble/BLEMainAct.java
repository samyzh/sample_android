package com.zsy.frame.sample.control.android.a26setting.bluetooth.ble;

import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.ble.BluetoothLeClass.OnDataAvailableListener;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.ble.BluetoothLeClass.OnServiceDiscoverListener;

/**
 * BLE分为三部分Service、Characteristic、Descriptor，这三部分都由UUID作为唯一标示符。
 * 一个蓝牙4.0的终端可以包含多个Service，
 * 一个Service可以包含多个Characteristic，
 * 一个Characteristic包含一个Value和多个Descriptor，
 * 一个Descriptor包含一个Value。
 * 一般来说，Characteristic是手机与BLE终端交换数据的关键，Characteristic有较多的跟权限相关的字段，
 * 例如PERMISSION和PROPERTY，而其中最常用的是PROPERTY，本文所用的BLE蓝牙模块竟然没有标准的Characteristic的PERMISSION。
 * Characteristic的PROPERTY可以通过位运算符组合来设置读写属性，例如READ|WRITE、READ|WRITE_NO_RESPONSE|NOTIFY，
 * 因此读取PROPERTY后要分解成所用的组合（本文代码已含此分解方法）权限详见Utils工具类。
 * @author samy
 * @date 2015-2-22 下午8:49:46
 */
public class BLEMainAct extends ListActivity {
	private final static String TAG = BLEMainAct.class.getSimpleName();
	private final static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";

	private LeDeviceListAdapter mLeDeviceListAdapter;
	/**搜索BLE终端*/
	private BluetoothAdapter mBluetoothAdapter;
	/**读写BLE终端*/
	private BluetoothLeClass mBLE;
	private boolean mScanning;
	private Handler mHandler;

	// Stops scanning after 10 seconds.
	private static final long SCAN_PERIOD = 10000;
	private boolean isFindDevices = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.title_devices);
		mHandler = new Handler();

		// Use this check to determine whether BLE is supported on the device. Then you can
		// selectively disable BLE-related features.
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
			finish();
		}

		// mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 获得本机蓝牙适配器对象引用(最原始的)
		// Initializes a Bluetooth adapter. For API level 18 and above, get a reference to BluetoothAdapter through BluetoothManager.
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		// 开启蓝牙
		mBluetoothAdapter.enable();

		// 初始化上下文
		mBLE = new BluetoothLeClass(this);
		if (!mBLE.initialize()) {
			Log.e(TAG, "Unable to initialize Bluetooth");
			finish();
		}
		// 发现BLE终端的Service时回调;点击Item设备连接回调
		mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);
		// 收到BLE终端数据交互的事件;点击Item设备连接回调
		mBLE.setOnDataAvailableListener(mOnDataAvailable);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Initializes list view adapter.
		mLeDeviceListAdapter = new LeDeviceListAdapter(this);
		setListAdapter(mLeDeviceListAdapter);
		scanLeDevice(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		mLeDeviceListAdapter.clear();
		mBLE.disconnect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mBLE.close();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
		if (device == null) return;
		if (mScanning) {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
			mScanning = false;
		}
		// 进行请求的链接
		mBLE.connect(device.getAddress());
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					invalidateOptionsMenu();
					if (!isFindDevices) {
						// 做没有查找到的通知设备处理显示
					}
				}
			}, SCAN_PERIOD);

			mScanning = true;
			isFindDevices = false;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
			// 如果你只需要搜索指定UUID的外设，你可以调用 startLeScan(UUID[], BluetoothAdapter.LeScanCallback)方法。
			// 其中UUID数组指定你的应用程序所支持的GATT Services的UUID。
			// mBluetoothAdapter.startLeScan(serviceUuids, callback);
		}
		else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateOptionsMenu();
	}

	/**
	 * 收到BLE终端数据交互的事件
	 */
	private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new OnDataAvailableListener() {
		/**
		 * BLE终端数据被读的事件
		 */
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				Log.e(TAG, "onCharRead " + gatt.getDevice().getName() + " read " + characteristic.getUuid().toString() + " -> " + Utils.bytesToHexString(characteristic.getValue()));
			}
		}

		/**
		 * 收到BLE终端写入数据回调
		 */
		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			Log.e(TAG, "onCharWrite " + gatt.getDevice().getName() + " write " + characteristic.getUuid().toString() + " -> " + new String(characteristic.getValue()));
		}
	};

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mLeDeviceListAdapter.addDevice(device);
					mLeDeviceListAdapter.notifyDataSetChanged();
				}
			});
		}
	};

	/**
	 * 监听搜索的回调
	 * 搜索到BLE终端服务的事件
	 */
	private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new OnServiceDiscoverListener() {
		@Override
		public void onServiceDiscover(BluetoothGatt gatt) {
			displayGattServices(mBLE.getSupportedGattServices());
		}

	};

	/**
	 * 首先是连接BLE设备后，枚举出设备所有Service、Characteristic、Descriptor，
	 * 并且手机会往Characteristic uuid=0000ffe1-0000-1000-8000-00805f9b34fb写入“send data->”字符串，
	 * BLE终端收到数据通过串口传到PC串口助手（见PC串口助手的截图）：
	 * @description：连接Gatt服务器；
	 * @author samy
	 * @date 2015-2-22 下午11:06:13
	 */
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null) return;
		for (BluetoothGattService gattService : gattServices) {
			// -----Service的字段信息-----//
			int type = gattService.getType();
			Log.e(TAG, "-->service type:" + Utils.getServiceType(type));
			Log.e(TAG, "-->includedServices size:" + gattService.getIncludedServices().size());
			Log.e(TAG, "-->service uuid:" + gattService.getUuid());

			// -----Characteristics的字段信息-----//
			List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
			for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
				Log.e(TAG, "---->char uuid:" + gattCharacteristic.getUuid());

				int permission = gattCharacteristic.getPermissions();
				Log.e(TAG, "---->char permission:" + Utils.getCharPermission(permission));

				int property = gattCharacteristic.getProperties();
				Log.e(TAG, "---->char property:" + Utils.getCharPropertie(property));

				byte[] data = gattCharacteristic.getValue();
				if (data != null && data.length > 0) {
					Log.e(TAG, "---->char value:" + new String(data));
				}

				// UUID_KEY_DATA是可以跟蓝牙模块串口通信的Characteristic
				if (gattCharacteristic.getUuid().toString().equals(UUID_KEY_DATA)) {
					// 测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mBLE.readCharacteristic(gattCharacteristic);
						}
					}, 500);
					// 接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
					mBLE.setCharacteristicNotification(gattCharacteristic, true);
					// 设置数据内容
					gattCharacteristic.setValue("send data->");
					// 往蓝牙模块写入数据
					mBLE.writeCharacteristic(gattCharacteristic);
				}

				// -----Descriptors的字段信息-----//
				List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic.getDescriptors();
				for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
					Log.e(TAG, "-------->desc uuid:" + gattDescriptor.getUuid());
					int descPermission = gattDescriptor.getPermissions();
					Log.e(TAG, "-------->desc permission:" + Utils.getDescPermission(descPermission));

					byte[] desData = gattDescriptor.getValue();
					if (desData != null && desData.length > 0) {
						Log.e(TAG, "-------->desc value:" + new String(desData));
					}
				}
			}
		}//

	}
}