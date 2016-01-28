package com.zsy.frame.sample.control.android.a26setting.bluetooth.ble;

import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zsy.frame.sample.R;

public class BLEPeripheryAct extends Activity {

	public static String serviceUUID = "039AFFF0-2C94-11E3-9E06-0002A5D5C51B";

	public static String characteristicUUID = "039AFFA1-2C94-11E3-9E06-0002A5D5C51B";

	private BluetoothGattServer server;
	private BluetoothManager manager;
	private BluetoothGattCharacteristic character;
	private BluetoothGattService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		character = new BluetoothGattCharacteristic(UUID.fromString(characteristicUUID), BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);

		service = new BluetoothGattService(UUID.fromString(serviceUUID), BluetoothGattService.SERVICE_TYPE_PRIMARY);

		service.addCharacteristic(character);

		manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		server = manager.openGattServer(this, new BluetoothGattServerCallback() {

			@Override
			public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
				Log.d("samy", "onConnectionStateChange");
				super.onConnectionStateChange(device, status, newState);
			}

			@Override
			public void onServiceAdded(int status, BluetoothGattService service) {

				Log.d("samy", "service added");
				super.onServiceAdded(status, service);
			}

			@Override
			public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
				Log.d("samy", "onCharacteristicReadRequest");
				super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
			}

			@Override
			public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
				Log.d("samy", "onCharacteristicWriteRequest");
				super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
			}

			@Override
			public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
				Log.d("samy", "onDescriptorReadRequest");
				super.onDescriptorReadRequest(device, requestId, offset, descriptor);
			}

			@Override
			public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
				Log.d("samy", "onDescriptorWriteRequest");
				super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
			}

			@Override
			public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
				Log.d("samy", "onExecuteWrite");
				super.onExecuteWrite(device, requestId, execute);
			}

		});

		server.addService(service);

		this.setContentView(R.layout.a26setting_bluetooth_ble_periphery);

		super.onCreate(savedInstanceState);
	}

	int i = 0;

	public void onButtonClicked(View v) {
		Log.d("samy", "XXXX");
		i++;
		character.setValue("index" + i);
		server.addService(service);
	}

}
