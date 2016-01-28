package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.gatt;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * @description：首页，查找设备界面
 * @author samy
 * @date 2015-2-23 下午7:30:16
 */
public class GattScanMainAct extends ListActivity {
	private static final long SCANNING_TIMEOUT = 5 * 1000; /* 5 seconds */
	private static final int ENABLE_BT_REQUEST_ID = 1;

	private boolean mScanning = false;
	private Handler mHandler = new Handler();
	private DeviceListAdapter mDevicesListAdapter = null;
	private BleWrapper mBleWrapper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// create BleWrapper with empty callback object except uiDeficeFound function (we need only that here)
		mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {
			@Override
			public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
				// 接受到监听设备的回调
				handleFoundDevice(device, rssi, record);
			}
		});

		// check if we have BT and BLE on board
		if (mBleWrapper.checkBleHardwareAvailable() == false) {
			bleMissing();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// on every Resume check if BT is enabled (user could turn it off while app was in background etc.)
		if (mBleWrapper.isBtEnabled() == false) {
			// BT is not turned on - ask user to make it enabled
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_ID);
			// see onActivityResult to check what is the status of our request
		}

		// initialize BleWrapper object
		mBleWrapper.initialize();

		mDevicesListAdapter = new DeviceListAdapter(this);
		setListAdapter(mDevicesListAdapter);

		// Automatically start scanning for devices
		mScanning = true;
		// remember to add timeout for scanning to not run it forever and drain the battery
		addScanningTimeout();
		mBleWrapper.startScanning();

		invalidateOptionsMenu();
	};

	@Override
	protected void onPause() {
		super.onPause();
		mScanning = false;
		mBleWrapper.stopScanning();
		invalidateOptionsMenu();

		mDevicesListAdapter.clearList();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_a26setting_bluetooth_gatt_scanning, menu);
		if (mScanning) {
			menu.findItem(R.id.scanning_start).setVisible(false);
			menu.findItem(R.id.scanning_stop).setVisible(true);
			menu.findItem(R.id.scanning_indicator).setActionView(R.layout.a26setting_bluetooth_gatt_scan_main_progress_indicator);
		}
		else {
			menu.findItem(R.id.scanning_start).setVisible(true);
			menu.findItem(R.id.scanning_stop).setVisible(false);
			menu.findItem(R.id.scanning_indicator).setActionView(null);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.scanning_start:
				mScanning = true;
				mBleWrapper.startScanning();
				break;
			case R.id.scanning_stop:
				mScanning = false;
				mBleWrapper.stopScanning();
				break;
			case R.id.show_hr_demo_item:
				startHRDemo();
				break;
		}

		invalidateOptionsMenu();
		return true;
	}

	private void startHRDemo() {
		startActivity(new Intent(this, HeartRateAct.class));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final BluetoothDevice device = mDevicesListAdapter.getDevice(position);
		if (device == null) return;

		final Intent intent = new Intent(this, PeripheralAct.class);
		intent.putExtra(PeripheralAct.EXTRAS_DEVICE_NAME, device.getName());
		intent.putExtra(PeripheralAct.EXTRAS_DEVICE_ADDRESS, device.getAddress());
		intent.putExtra(PeripheralAct.EXTRAS_DEVICE_RSSI, mDevicesListAdapter.getRssi(position));

		if (mScanning) {
			mScanning = false;
			invalidateOptionsMenu();
			mBleWrapper.stopScanning();
		}
		startActivity(intent);
	}

	/**
	 * check if user agreed to enable BT；这里的考虑的很周全
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// user didn't want to turn on BT
		if (requestCode == ENABLE_BT_REQUEST_ID) {
			if (resultCode == Activity.RESULT_CANCELED) {
				btDisabled();
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 由于搜索需要尽量减少功耗，因此在实际使用时需要注意：
	 * 1、当找到对应的设备后，立即停止扫描；
	 * 2、不要循环搜索设备，为每次搜索设置适合的时间限制。避免设备不在可用范围的时候持续不停扫描，消耗电量。
	 * make sure that potential scanning will take no longer
	 * than <SCANNING_TIMEOUT> seconds from now on
	 */
	private void addScanningTimeout() {
		Runnable timeout = new Runnable() {
			@Override
			public void run() {
				if (mBleWrapper == null) return;
				mScanning = false;
				mBleWrapper.stopScanning();
				invalidateOptionsMenu();
			}
		};
		mHandler.postDelayed(timeout, SCANNING_TIMEOUT);
	}

	/* add device to the current list of devices */
	private void handleFoundDevice(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
		// adding to the UI have to happen in UI thread
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mDevicesListAdapter.addDevice(device, rssi, scanRecord);
				mDevicesListAdapter.notifyDataSetChanged();
			}
		});
	}

	private void btDisabled() {
		Toast.makeText(this, "Sorry, BT has to be turned ON for us to work!", Toast.LENGTH_LONG).show();
		finish();
	}

	private void bleMissing() {
		Toast.makeText(this, "BLE Hardware is required but not available!", Toast.LENGTH_LONG).show();
		finish();
	}
}
