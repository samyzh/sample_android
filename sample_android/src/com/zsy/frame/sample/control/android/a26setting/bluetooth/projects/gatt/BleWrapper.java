package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.gatt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

public class BleWrapper {
	private Activity mParent = null;
	private boolean mConnected = false;
	private String mDeviceAddress = "";

	private BluetoothManager mBluetoothManager = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothDevice mBluetoothDevice = null;
	private BluetoothGatt mBluetoothGatt = null;
	private BluetoothGattService mBluetoothSelectedService = null;
	private List<BluetoothGattService> mBluetoothGattServices = null;

	private Handler mTimerHandler = new Handler();
	private boolean mTimerEnabled = false;
	
	/* defines (in milliseconds) how often RSSI should be updated */
	private static final int RSSI_UPDATE_TIME_INTERVAL = 1500; // 1.5 seconds

	/* callback object through which we are returning results to the caller */
	private BleWrapperUiCallbacks mUiCallback = null;
	/* define NULL object for UI callbacks */
	private static final BleWrapperUiCallbacks NULL_CALLBACK = new BleWrapperUiCallbacks.Null();

	/**
	 *  creates BleWrapper object, set its parent activity and callback object 
	 * @param parent
	 * @param callback
	 */
	public BleWrapper(Activity parent, BleWrapperUiCallbacks callback) {
		this.mParent = parent;
		mUiCallback = callback;
		if (mUiCallback == null) {
			mUiCallback = NULL_CALLBACK;
		}
	}

	public BluetoothManager getManager() {
		return mBluetoothManager;
	}

	public BluetoothAdapter getAdapter() {
		return mBluetoothAdapter;
	}

	public BluetoothDevice getDevice() {
		return mBluetoothDevice;
	}

	public BluetoothGatt getGatt() {
		return mBluetoothGatt;
	}

	public BluetoothGattService getCachedService() {
		return mBluetoothSelectedService;
	}

	public List<BluetoothGattService> getCachedServices() {
		return mBluetoothGattServices;
	}

	public boolean isConnected() {
		return mConnected;
	}

	/**
	 * 除了蓝牙权限外，如果需要BLE feature则还需要声明uses-feature：
	 * <uses-feature android:name="android.hardware.bluetooth_le" android:required="true"/>
	 *  按时required为true时，则应用只能在支持BLE的Android设备上安装运行；
	 *  required为false时，Android设备均可正常安装运行，boolean hasBle = mParent.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
	 *  需要在代码运行时判断设备是否支持BLE feature：
	 * @description：run test and check if this device has BT and BLE hardware available
	 * @author samy
	 * @date 2015-2-23 下午7:40:23
	 */
	public boolean checkBleHardwareAvailable() {
		// First check general Bluetooth Hardware:
		// get BluetoothManager...
		final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
		if (manager == null) return false;
		// .. and then get adapter from manager
		final BluetoothAdapter adapter = manager.getAdapter();
		if (adapter == null) return false;

		// and then check if BT LE is also available
		boolean hasBle = mParent.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
		return hasBle;
	}

	/**
	 * before any action check if BT is turned ON and enabled for us
	 * call this in onResume to be always sure that BT is ON when Your
	 * application is put into the foreground
	 */
	public boolean isBtEnabled() {
		final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
		if (manager == null) return false;

		final BluetoothAdapter adapter = manager.getAdapter();
		if (adapter == null) return false;

		return adapter.isEnabled();
	}

	/**
	 * @description：start scanning for BT LE devices around
	 * 这个类似于发送广播
	 * @author samy
	 * @date 2015-2-23 下午7:50:09
	 */
	public void startScanning() {
		mBluetoothAdapter.startLeScan(mDeviceFoundCallback);
	}

	/**
	 * @description：stops current scanning
	 * @author samy
	 * @date 2015-2-23 下午7:50:19
	 */
	public void stopScanning() {
		mBluetoothAdapter.stopLeScan(mDeviceFoundCallback);
	}

	/**
	 * @description：initialize BLE and get BT Manager & Adapter
	 * @author samy
	 * @date 2015-2-23 下午7:50:37
	 */
	public boolean initialize() {
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) { return false; }
		}

		if (mBluetoothAdapter == null) mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) { return false; }
		return true;
	}

	/**
	 * @description： connect to the device with specified address 
	 * @author samy
	 * @date 2015-2-23 下午8:34:47
	 */
	public boolean connect(final String deviceAddress) {
		if (mBluetoothAdapter == null || deviceAddress == null) { return false; }
		mDeviceAddress = deviceAddress;

		// check if we need to connect from scratch or just reconnect to previous device
		if (mBluetoothGatt != null && mBluetoothGatt.getDevice().getAddress().equals(deviceAddress)) {
			// just reconnect
			return mBluetoothGatt.connect();
		}
		else {
			// connect from scratch
			// get BluetoothDevice object for specified address
			mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
			if (mBluetoothDevice == null) {
				// we got wrong address - that device is not available!
				return false;
			}
			// connect with remote device
			mBluetoothGatt = mBluetoothDevice.connectGatt(mParent, false, mBleCallback);
		}
		return true;
	}

	/**
	 * @description：disconnect the device. It is still possible to reconnect to it later with this Gatt client
	 * @author samy
	 * @date 2015-2-23 下午9:05:13
	 */
	public void diconnect() {
		if (mBluetoothGatt != null) mBluetoothGatt.disconnect();
		mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
	}

	/**
	 * @description：close GATT client completely 
	 * @author samy
	 * @date 2015-2-23 下午9:05:29
	 */
	public void close() {
		if (mBluetoothGatt != null) mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	/**
	 * defines callback for scanning results
	 */
	private BluetoothAdapter.LeScanCallback mDeviceFoundCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
			mUiCallback.uiDeviceFound(device, rssi, scanRecord);
		}
	};

	/**
	 * @description：request new RSSi value for the connection(获取信号强弱)
	 * @author samy
	 * @date 2015-2-23 下午9:05:44
	 */
	public void readPeriodicalyRssiValue(final boolean repeat) {
		mTimerEnabled = repeat;
		// check if we should stop checking RSSI value
		if (mConnected == false || mBluetoothGatt == null || mTimerEnabled == false) {
			mTimerEnabled = false;
			return;
		}
		mTimerHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (mBluetoothGatt == null || mBluetoothAdapter == null || mConnected == false) {
					mTimerEnabled = false;
					return;
				}

				// request RSSI value
				mBluetoothGatt.readRemoteRssi();
				// add call it once more in the future
				readPeriodicalyRssiValue(mTimerEnabled);
			}
		}, RSSI_UPDATE_TIME_INTERVAL);
	}

	/**
	 * @description： starts monitoring RSSI value
	 * @author samy
	 * @date 2015-2-23 下午9:15:58
	 */
	public void startMonitoringRssiValue() {
		readPeriodicalyRssiValue(true);
	}

	/**
	 * @description：stops monitoring of RSSI value
	 * @author samy
	 * @date 2015-2-23 下午9:16:15
	 */
	public void stopMonitoringRssiValue() {
		readPeriodicalyRssiValue(false);
	}

	/**
	 * @description：
	 * request to discover all services available on the remote devices
	 * results are delivered through callback object
	 * @author samy
	 * @date 2015-2-23 下午9:18:39
	 */
	public void startServicesDiscovery() {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.discoverServices();
		}
	}

	/**
	 * @description： gets services and calls UI callback to handle them
	 * before calling getServices() make sure service discovery is finished!
	 * @author samy
	 * @date 2015-2-23 下午9:33:18
	 */
	public void getSupportedServices() {
		if (mBluetoothGattServices != null && mBluetoothGattServices.size() > 0) {
			mBluetoothGattServices.clear();
		}
		// keep reference to all services in local array:
		if (mBluetoothGatt != null) {
			mBluetoothGattServices = mBluetoothGatt.getServices();
		}
		mUiCallback.uiAvailableServices(mBluetoothGatt, mBluetoothDevice, mBluetoothGattServices);
	}

	/**
	 * @description：get all characteristic for particular service and pass them to the UI callback
	 * @author samy
	 * @date 2015-2-23 下午9:33:54
	 */
	public void getCharacteristicsForService(final BluetoothGattService service) {
		if (service == null) return;
		List<BluetoothGattCharacteristic> chars = null;

		chars = service.getCharacteristics();
		mUiCallback.uiCharacteristicForService(mBluetoothGatt, mBluetoothDevice, service, chars);
		// keep reference to the last selected service
		mBluetoothSelectedService = service;
	}

	/**
	 * @description：request to fetch newest value stored on the remote device for particular characteristic
	 * @author samy
	 * @date 2015-2-23 下午9:34:03
	 */
	public void requestCharacteristicValue(BluetoothGattCharacteristic ch) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

		mBluetoothGatt.readCharacteristic(ch);
		// new value available will be notified in Callback Object
	}

	/**
	 * @description：get characteristic's value (and parse it for some types of characteristics)
		 * before calling this You should always update the value by calling requestCharacteristicValue()
	 * @author samy
	 * @date 2015-2-23 下午9:34:15
	 */
	public void getCharacteristicValue(BluetoothGattCharacteristic ch) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

		byte[] rawValue = ch.getValue();
		String strValue = null;
		int intValue = 0;

		// lets read and do real parsing of some characteristic to get meaningful value from it
		UUID uuid = ch.getUuid();

		if (uuid.equals(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT)) { // heart rate
			// follow https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
			// first check format used by the device - it is specified in bit 0 and tells us if we should ask for index 1 (and uint8) or index 2 (and uint16)
			int index = ((rawValue[0] & 0x01) == 1) ? 2 : 1;
			// also we need to define format
			int format = (index == 1) ? BluetoothGattCharacteristic.FORMAT_UINT8 : BluetoothGattCharacteristic.FORMAT_UINT16;
			// now we have everything, get the value
			intValue = ch.getIntValue(format, index);
			strValue = intValue + " bpm"; // it is always in bpm units
		}
		else if (uuid.equals(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT) || // manufacturer name string
				uuid.equals(BleDefinedUUIDs.Characteristic.MODEL_NUMBER_STRING) || // model number string)
				uuid.equals(BleDefinedUUIDs.Characteristic.FIRMWARE_REVISION_STRING)) // firmware revision string
		{
			// follow https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml etc.
			// string value are usually simple utf8s string at index 0
			strValue = ch.getStringValue(0);
		}
		else if (uuid.equals(BleDefinedUUIDs.Characteristic.APPEARANCE)) { // appearance
			// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.appearance.xml
			intValue = ((int) rawValue[1]) << 8;
			intValue += rawValue[0];
			strValue = BleNamesResolver.resolveAppearance(intValue);
		}
		else if (uuid.equals(BleDefinedUUIDs.Characteristic.BODY_SENSOR_LOCATION)) { // body sensor location
			// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.body_sensor_location.xml
			intValue = rawValue[0];
			strValue = BleNamesResolver.resolveHeartRateSensorLocation(intValue);
		}
		else if (uuid.equals(BleDefinedUUIDs.Characteristic.BATTERY_LEVEL)) { // battery level
			// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.battery_level.xml
			intValue = rawValue[0];
			strValue = "" + intValue + "% battery level";
		}
		else {
			// not known type of characteristic, so we need to handle this in "general" way
			// get first four bytes and transform it to integer
			intValue = 0;
			if (rawValue.length > 0) intValue = (int) rawValue[0];
			if (rawValue.length > 1) intValue = intValue + ((int) rawValue[1] << 8);
			if (rawValue.length > 2) intValue = intValue + ((int) rawValue[2] << 8);
			if (rawValue.length > 3) intValue = intValue + ((int) rawValue[3] << 8);

			if (rawValue.length > 0) {
				final StringBuilder stringBuilder = new StringBuilder(rawValue.length);
				for (byte byteChar : rawValue) {
					stringBuilder.append(String.format("%c", byteChar));
				}
				strValue = stringBuilder.toString();
			}
		}

		String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date());
		mUiCallback.uiNewValueForCharacteristic(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, ch, strValue, intValue, rawValue, timestamp);
	}

	/*
	 * reads and return what what FORMAT is indicated by characteristic's properties
	 * seems that value makes no sense in most cases
	 */
	public int getValueFormat(BluetoothGattCharacteristic ch) {
		int properties = ch.getProperties();

		if ((BluetoothGattCharacteristic.FORMAT_FLOAT & properties) != 0) return BluetoothGattCharacteristic.FORMAT_FLOAT;
		if ((BluetoothGattCharacteristic.FORMAT_SFLOAT & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SFLOAT;
		if ((BluetoothGattCharacteristic.FORMAT_SINT16 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT16;
		if ((BluetoothGattCharacteristic.FORMAT_SINT32 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT32;
		if ((BluetoothGattCharacteristic.FORMAT_SINT8 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT8;
		if ((BluetoothGattCharacteristic.FORMAT_UINT16 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT16;
		if ((BluetoothGattCharacteristic.FORMAT_UINT32 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT32;
		if ((BluetoothGattCharacteristic.FORMAT_UINT8 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT8;

		return 0;
	}

	/* set new value for particular characteristic */
	public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

		// first set it locally....
		ch.setValue(dataToWrite);
		// ... and then "commit" changes to the peripheral
		mBluetoothGatt.writeCharacteristic(ch);
	}

	/* enables/disables notification for characteristic */
	public void setNotificationForCharacteristic(BluetoothGattCharacteristic ch, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

		boolean success = mBluetoothGatt.setCharacteristicNotification(ch, enabled);
		if (!success) {
			Log.e("------", "Seting proper notification status for characteristic failed!");
		}

		// This is also sometimes required (e.g. for heart rate monitors) to enable notifications/indications
		// see: https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
		BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
		if (descriptor != null) {
			byte[] val = enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
			descriptor.setValue(val);
			mBluetoothGatt.writeDescriptor(descriptor);
		}
	}

	/**
	 *  callbacks called for any action on particular Ble Device 
	 */
	private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {
		// 时刻监听状态改变
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				mConnected = true;
				mUiCallback.uiDeviceConnected(mBluetoothGatt, mBluetoothDevice);
				// now we can start talking with the device, e.g.（注意这里是例子）
				// 这个可以不用写，在后面也写了；
				mBluetoothGatt.readRemoteRssi();
				// response will be delivered to callback object!

				// in our case we would also like automatically to call for services discovery
				startServicesDiscovery();

				// and we also want to get RSSI value to be updated periodically
				startMonitoringRssiValue();
			}
			else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mConnected = false;
				mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// now, when services discovery is finished, we can call getServices() for Gatt
				getSupportedServices();
			}
		}

		@Override
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// we got new value of RSSI of the connection, pass it to the UI
				mUiCallback.uiNewRssiAvailable(mBluetoothGatt, mBluetoothDevice, rssi);
			}
		};

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			// characteristic's value was updated due to enabled notification, lets get this value
			// the value itself will be reported to the UI inside getCharacteristicValue
			getCharacteristicValue(characteristic);
			// also, notify UI that notification are enabled for particular characteristic
			mUiCallback.uiGotNotification(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic);
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			// we got response regarding our request to fetch characteristic value
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// and it success, so we can get the value
				getCharacteristicValue(characteristic);
			}
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			String deviceName = gatt.getDevice().getName();
			String serviceName = BleNamesResolver.resolveServiceName(characteristic.getService().getUuid().toString().toLowerCase(Locale.getDefault()));
			String charName = BleNamesResolver.resolveCharacteristicName(characteristic.getUuid().toString().toLowerCase(Locale.getDefault()));
			String description = "Device: " + deviceName + " Service: " + serviceName + " Characteristic: " + charName;

			// we got response regarding our request to write new value to the characteristic
			// let see if it failed or not
			if (status == BluetoothGatt.GATT_SUCCESS) {
				mUiCallback.uiSuccessfulWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description);
			}
			else {
				mUiCallback.uiFailedWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description + " STATUS = " + status);
			}
		};

	};
}
