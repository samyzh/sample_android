/*
 * Copyright (C) 2013 The Android Open Source Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zsy.frame.sample.control.android.a26setting.bluetooth.ble;

import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 这个类可以做出服务处理更好；
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 * 看看Android BLE SDK的四个关键类（class）：
 *	a) BluetoothGattServer作为周边来提供数据；BluetoothGattServerCallback返回周边的状态。
 *	b) BluetoothGatt作为中央来使用和处理数据；BluetoothGattCallback返回中央的状态和周边提供的数据。
 */
public class BluetoothLeClass {
	private final static String TAG = BluetoothLeClass.class.getSimpleName();

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt;

	private static final int STATE_DISCONNECTED = 0;
	private static final int STATE_CONNECTING = 1;
	private static final int STATE_CONNECTED = 2;
	private int mConnectionState = STATE_DISCONNECTED;

	public interface OnConnectListener {
		public void onConnect(BluetoothGatt gatt);
	}

	public interface OnDisconnectListener {
		public void onDisconnect(BluetoothGatt gatt);
	}

	public interface OnServiceDiscoverListener {
		public void onServiceDiscover(BluetoothGatt gatt);
	}

	public interface OnDataAvailableListener {
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);
	}

	private OnConnectListener mOnConnectListener;
	private OnDisconnectListener mOnDisconnectListener;
	private OnServiceDiscoverListener mOnServiceDiscoverListener;
	private OnDataAvailableListener mOnDataAvailableListener;
	private Context mContext;

	public void setOnConnectListener(OnConnectListener l) {
		mOnConnectListener = l;
	}

	public void setOnDisconnectListener(OnDisconnectListener l) {
		mOnDisconnectListener = l;
	}

	public void setOnServiceDiscoverListener(OnServiceDiscoverListener l) {
		mOnServiceDiscoverListener = l;
	}

	public void setOnDataAvailableListener(OnDataAvailableListener l) {
		mOnDataAvailableListener = l;
	}

	public BluetoothLeClass(Context c) {
		mContext = c;
	}

	// Implements callback methods for GATT events that the app cares about. For example,
	// connection change and services discovered.
	/**
	 * 监听搜索的回调
	 * BluetoothGatt常规用到的几个操作示例:
	connect() ：连接远程设备。
	discoverServices() : 搜索连接设备所支持的service。
	disconnect()：断开与远程设备的GATT连接。
	close()：关闭GATT Client端。
	readCharacteristic(characteristic) ：读取指定的characteristic。
	setCharacteristicNotification(characteristic, enabled) ：设置当指定characteristic值变化时，发出通知。
	getServices() ：获取远程设备所支持的services。
	 */
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			Log.d("samy", "onConnectionStateChange");
			// switch (newState) {
			// case BluetoothProfile.STATE_CONNECTED:
			// Log.d("samy", "STATE_CONNECTED");
			// gatt.discoverServices();
			//
			// break;
			// case BluetoothProfile.STATE_DISCONNECTED:
			// Log.d("samy", "STATE_DISCONNECTED");
			// break;
			// case BluetoothProfile.STATE_CONNECTING:
			// Log.d("samy", "STATE_CONNECTING");
			// break;
			// case BluetoothProfile.STATE_DISCONNECTING:
			// Log.d("samy", "STATE_DISCONNECTING");
			// break;
			// }
			// super.onConnectionStateChange(gatt, status, newState);
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				mConnectionState = STATE_CONNECTED;
				if (mOnConnectListener != null) {
					mOnConnectListener.onConnect(gatt);
					Log.i(TAG, "Connected to GATT server.");
					// Attempts to discover services after successful connection.
					Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
				}
			}
			else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mConnectionState = STATE_DISCONNECTED;
				if (mOnDisconnectListener != null) {
					mOnDisconnectListener.onDisconnect(gatt);
					Log.i(TAG, "Disconnected from GATT server.");
					// // 重新连接
					// if (!TextUtils.isEmpty(mBluetoothDeviceAddress)) {
					// connect(mBluetoothDeviceAddress);
					// }
				}
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Log.d("samy", "onServicesDiscovered");
			if (status == BluetoothGatt.GATT_SUCCESS && mOnServiceDiscoverListener != null) {
				// mEventBus.post(new BlueToothEvent.GattServicesDiscoveredEvent());
				// broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
				mOnServiceDiscoverListener.onServiceDiscover(gatt);
			}
			else {
				Log.w(TAG, "onServicesDiscovered received: " + status);
			}
			super.onServicesDiscovered(gatt, status);
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS && mOnDataAvailableListener != null) {
				mOnDataAvailableListener.onCharacteristicRead(gatt, characteristic, status);
			}
			super.onCharacteristicRead(gatt, characteristic, status);
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			Log.d("samy", "onCharacteristicWrite");
			if (status == BluetoothGatt.GATT_SUCCESS && mOnDataAvailableListener != null) {
				mOnDataAvailableListener.onCharacteristicWrite(gatt, characteristic);
			}
			super.onCharacteristicWrite(gatt, characteristic, status);
		}

		/**
		 * 这个方法有单奇怪
		 */
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			if (mOnDataAvailableListener != null) {
				mOnDataAvailableListener.onCharacteristicWrite(gatt, characteristic);
			}
			// super.onCharacteristicChanged(gatt, characteristic);
		}
	};

	/**
	 * Initializes a reference to the local Bluetooth adapter.
	 *
	 * @return Return true if the initialization is successful.
	 */
	public boolean initialize() {
		// For API level 18 and above, get a reference to BluetoothAdapter through
		// BluetoothManager.
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Log.e(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}

		return true;
	}

	/**
	 * Connects to the GATT server hosted on the Bluetooth LE device.
	 *
	 * @param address The device address of the destination device.
	 *
	 * @return Return true if the connection is initiated successfully. The connection result
	 *         is reported asynchronously through the
	 *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 *         callback.
	 */
	/**
	 * @description：     连接到GATT服务器实际上是一个二阶段过程。
	 * 首先我们必须创建和打开一个代表了GATT服务器的本地代理实例的连接，
	 * 然后我们必须将这个代理连接到传感器上的GATT服务器。
	 * 
	 * 为了创建代理实例，我们需要调用已发现的蓝牙设备BluetoothDevice 实例的connectGatt()方法 。
	 * @author samy
	 * @date 2015-2-22 下午9:36:31
	 */
	public boolean connect(final String address) {
		if (mBluetoothAdapter == null || address == null) {
			Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
			return false;
		}

		// Previously connected device. Try to reconnect.
		if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
			Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
			if (mBluetoothGatt.connect()) {
				mConnectionState = STATE_CONNECTING;
				return true;
			}
			else {
				return false;
			}
		}

		final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		if (device == null) {
			Log.w(TAG, "Device not found.  Unable to connect.");
			return false;
		}
		// We want to directly connect to the device, so we are setting the autoConnect parameter to false.
		// connectGatt() 的返回值是一个BluetoothGatt的实例，通过这个本地的代理对象，我们就可以与传感器上的GATT服务器通信了。
		// 有关GATT服务器，理解本地和远程组件是非常重要的，但是现在我们已经了解，有一个简单的连接方式。如果我们将autoConnect 参数设置为TRUE，那么它会自动连接到传感器上的GATT服务器。connectGatt()方法将很快返回，一旦远程连接完成我们随后会收到一个回调。
		// mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
		mBluetoothGatt = device.connectGatt(mContext, true, mGattCallback);
		Log.d(TAG, "Trying to create a new connection.");
		mBluetoothDeviceAddress = address;
		mConnectionState = STATE_CONNECTING;
		return true;
	}

	/**
	 * Disconnects an existing connection or cancel a pending connection. The disconnection result
	 * is reported asynchronously through the
	 * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 * callback.
	 */
	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
	}

	/**
	 * After using a given BLE device, the app must call this method to ensure resources are
	 * released properly.
	 */
	public void close() {
		if (mBluetoothGatt == null) { return; }
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	/**
	 * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
	 * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
	 * callback.
	 *
	 * @param characteristic The characteristic to read from.
	 */
	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	/**
	 * Enables or disables notification on a give characteristic.
	 *
	 * @param characteristic Characteristic to act on.
	 * @param enabled If true, enable notification.  False otherwise.
	 */
	public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
	}

	public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
		mBluetoothGatt.writeCharacteristic(characteristic);
	}

	/**
	 * Retrieves a list of supported GATT services on the connected device. This should be
	 * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
	 *
	 * @return A {@code List} of supported services.
	 */
	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null) return null;

		return mBluetoothGatt.getServices();
	}
}
