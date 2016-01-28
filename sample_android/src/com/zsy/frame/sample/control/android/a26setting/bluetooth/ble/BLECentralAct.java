package com.zsy.frame.sample.control.android.a26setting.bluetooth.ble;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zsy.frame.sample.R;

public class BLECentralAct extends Activity implements BluetoothAdapter.LeScanCallback {
	private BluetoothAdapter btAdapter;
	private BluetoothGatt gatt;
	private List<BluetoothGattService> serviceList;
	private List<BluetoothGattCharacteristic> characterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		btAdapter = bluetoothManager.getAdapter();

		this.setContentView(R.layout.a26setting_bluetooth_ble_central);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStop() {
		btAdapter.stopLeScan(this);
		super.onStop();
	}

	public void onButtonClicked(View v) {
		Log.d("samy", "onButtonClicked");
		btAdapter.startLeScan(this);
	}

	@Override
	public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
		btAdapter.stopLeScan(this);
		gatt = device.connectGatt(this, true, gattCallback);

		Log.d("samy", "Device Name:" + device.getName());

	}

	private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			Log.d("samy", "onConnectionStateChange");
			switch (newState) {
				case BluetoothProfile.STATE_CONNECTED:
					Log.d("samy", "STATE_CONNECTED");
					gatt.discoverServices();

					break;
				case BluetoothProfile.STATE_DISCONNECTED:
					Log.d("samy", "STATE_DISCONNECTED");
					break;
				case BluetoothProfile.STATE_CONNECTING:
					Log.d("samy", "STATE_CONNECTING");
					break;
				case BluetoothProfile.STATE_DISCONNECTING:
					Log.d("samy", "STATE_DISCONNECTING");
					break;
			}
			super.onConnectionStateChange(gatt, status, newState);
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Log.d("samy", "onServicesDiscovered");
			if (status == BluetoothGatt.GATT_SUCCESS) {
				serviceList = gatt.getServices();
				for (int i = 0; i < serviceList.size(); i++) {
					BluetoothGattService theService = serviceList.get(i);
					Log.d("samy", "ServiceName:" + theService.getUuid());

					characterList = theService.getCharacteristics();
					for (int j = 0; j < characterList.size(); j++) {
						Log.d("samy", "---CharacterName:" + characterList.get(j).getUuid());
					}
				}
			}
			super.onServicesDiscovered(gatt, status);
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			Log.d("samy", "onCharacteristicRead");
			super.onCharacteristicRead(gatt, characteristic, status);
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			Log.d("samy", "onCharacteristicWrite");
			super.onCharacteristicWrite(gatt, characteristic, status);
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			Log.d("samy", "onCharacteristicChanged");
			super.onCharacteristicChanged(gatt, characteristic);
		}

		@Override
		public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
			Log.d("samy", "onDescriptorRead");
			super.onDescriptorRead(gatt, descriptor, status);
		}

		@Override
		public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
			Log.d("samy", "onDescriptorWrite");
			super.onDescriptorWrite(gatt, descriptor, status);
		}

		@Override
		public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
			Log.d("samy", "onReliableWriteCompleted");
			super.onReliableWriteCompleted(gatt, status);
		}

		@Override
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
			Log.d("samy", "onReadRemoteRssi");
			super.onReadRemoteRssi(gatt, rssi, status);
		}

	};
}
