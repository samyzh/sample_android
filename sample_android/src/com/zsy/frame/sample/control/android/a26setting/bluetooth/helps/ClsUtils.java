package com.zsy.frame.sample.control.android.a26setting.bluetooth.helps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

/** 
 * @description：蓝牙配对工具类:ctrl+shift+j
 * @author samy
 * @date 2015-2-8 下午1:54:49
 */
public class ClsUtils {

	/**
	 * 与设备配对 参考源码：platform/packages/apps/Settings.git
	 * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
	 * 蓝牙配对的关键代码：
	 */
	/**
	 * @description：建立配对;利用JAVA的反射机制去调用这两项功能对应的函数：createBond和removeBond
	 * @author samy
	 * @date 2015-2-10 下午10:01:46
	 */
	public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	/**
	 * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
	 * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
	 */
	/**
	 * @description：解除配对;利用JAVA的反射机制去调用这两项功能对应的函数：createBond和removeBond
	 * @author samy
	 * @date 2015-2-10 下午10:01:50
	 */
	public static boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception {
		Method removeBondMethod = btClass.getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	public static boolean setPin(Class btClass, BluetoothDevice btDevice, String str) throws Exception {
		try {
			Method removeBondMethod = btClass.getDeclaredMethod("setPin", new Class[] { byte[].class });
			Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice, new Object[] { str.getBytes() });
			Log.e("returnValue", "" + returnValue);
		}
		catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * @description：取消用户输入
	 * @author samy
	 * @date 2015-2-8 下午10:26:47
	 */
	public static boolean cancelPairingUserInput(Class btClass, BluetoothDevice device) throws Exception {
		Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
		// cancelBondProcess()
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	/**
	 * @description：取消配对
	 * @author samy
	 * @date 2015-2-8 下午10:27:07
	 */
	public static boolean cancelBondProcess(Class btClass, BluetoothDevice device) throws Exception {
		Method createBondMethod = btClass.getMethod("cancelBondProcess");
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	/**
	 * @description：显示BluetoothDevice的所有方法和常量，包括隐藏API
	 * @author samy
	 * @date 2015-2-10 下午10:07:27
	 */
	public static void printAllInform(Class clsShow) {
		try {
			// 取得所有方法
			Method[] hideMethod = clsShow.getMethods();
			int i = 0;
			for (; i < hideMethod.length; i++) {
				Log.e("method name", hideMethod[i].getName() + ";and the i is:" + i);
			}
			// 取得所有常量
			Field[] allFields = clsShow.getFields();
			for (i = 0; i < allFields.length; i++) {
				Log.e("Field name", allFields[i].getName());
			}
		}
		catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean pair(String strAddr, String strPsw) {
		boolean result = false;
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		bluetoothAdapter.cancelDiscovery();
		if (!bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.enable();
		}
		if (!BluetoothAdapter.checkBluetoothAddress(strAddr)) { // 检查蓝牙地址是否有效
			Log.d("mylog", "devAdd un effient!");
		}
		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);
		if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
			try {
				Log.d("mylog", "NOT BOND_BONDED");
				ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
				ClsUtils.createBond(device.getClass(), device);
				// remoteDevice = device; // 配对完毕就把这个设备对象传给全局的remoteDevice
				result = true;
			}
			catch (Exception e) {
				Log.d("mylog", "setPiN failed!");
				e.printStackTrace();
			} //

		}
		else {
			Log.d("mylog", "HAS BOND_BONDED");
			try {
				ClsUtils.createBond(device.getClass(), device);
				ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
				ClsUtils.createBond(device.getClass(), device);
				// remoteDevice = device; // 如果绑定成功，就直接把这个设备对象传给全局的remoteDevice
				result = true;
			}
			catch (Exception e) {
				Log.d("mylog", "setPiN failed!");
				e.printStackTrace();
			}
		}
		return result;
	}
}