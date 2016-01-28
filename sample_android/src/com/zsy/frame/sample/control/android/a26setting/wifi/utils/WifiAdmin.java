package com.zsy.frame.sample.control.android.a26setting.wifi.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import com.zsy.frame.sample.control.android.a26setting.wifi.projects.wifiqr.bean.UserWifiInfo;

/**
 * 学习笔记WIFI设备
 * 1、WIFI设备是个啥?
 * 2、WIFI有哪些状态?
 * 3、如何操作WIFI?
 * 4、如何得到周围的WIFI热点列表?
 * 5、如何连接上我的WIFI?
 * 6、如何查看已经连接上的WIFI信息?
 * 
 * @description：
 * 在Android的官方文档中定义了如下五种状态：
        WIFI_STATE_DISABLED   WIFI网卡不可用 
        WIFI_STATE_DISABLING  WIFI网卡正在关闭 
        WIFI_STATE_ENABLED     WIFI网卡可用 
        WIFI_STATE_ENABLING    WIFI网卡正在打开 
        WIFI_STATE_UNKNOWN    WIFI网卡状态不可知
 * @author samy
 * @date 2015-2-26 上午10:34:20
 */
public class WifiAdmin {

	/**定义WifiManager对象   */
	private WifiManager mWifiManager;
	/**定义WifiInfo对象*/
	private WifiInfo mWifiInfo;
	/**扫描出的网络连接列表  */
	private List<ScanResult> mWifiList;
	/**网络连接列表  ----保存的热点*/
	private List<WifiConfiguration> mWifiConfiguration;
	/**定义一个WifiLock */
	private WifiLock mWifiLock;
	private String TAG = WifiAdmin.class.getSimpleName();

	/** 定义几种加密方式，一种是WEP，一种是WPA/WPA2，还有没有密码的情况 */
	public enum WifiCipherType {
		WIFI_CIPHER_WEP, // WEP加密
		WIFI_CIPHER_WPA_EAP, // WPA-EAP加密
		WIFI_CIPHER_WPA_PSK, // WPA-PSK加密
		WIFI_CIPHER_WPA2_PSK, // WPA2-PSK加密
		WIFI_CIPHER_NOPASS// 无密码
	}

	public WifiAdmin(Context context) {
		// 取得WifiManager对象
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/**
	 * 获取wifi列表和WifiConfiguration对象
	 */
	public void startScan() {
		openWifi();
		mWifiManager.startScan();
		mWifiList = mWifiManager.getScanResults();
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();

		// 保存点的设置
		// wifiConfigurations.clear();
		// for (WifiConfiguration temp : wifiManager.getConfiguredNetworks()) {
		// wifiConfigurations.add(new MyWifiConfiguration(temp));
		// }
	}

	/**
	 *  打开WIFI
	 */
	public void openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/**
	 *  关闭WIFI
	 */
	public void closeWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/*
	 * WifiManager.WIFI_STATE_DISABLING 正在停止
	 * WifiManager.WIFI_STATE_DISABLED 已停止
	 * WifiManager.WIFI_STATE_ENABLING 正在打开
	 * WifiManager.WIFI_STATE_ENABLED 已开启
	 * WifiManager.WIFI_STATE_UNKNOWN 未知
	 */
	/**
	 * 检测wifi状态 opened return true;
	 */
	public boolean checkWifiState() {
		boolean isOpen = true;
		int wifiState = mWifiManager.getWifiState();

		if (wifiState == WifiManager.WIFI_STATE_DISABLED || wifiState == WifiManager.WIFI_STATE_DISABLING || wifiState == WifiManager.WIFI_STATE_UNKNOWN || wifiState == WifiManager.WIFI_STATE_ENABLING) {
			isOpen = false;
		}

		return isOpen;
	}

	/**
	 * @description：检查当前WIFI状态
	 * @author samy
	 * @date 2015-2-26 下午3:47:33
	 */
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	/**
	 * @description：WIFI开关
	 * @author samy
	 * @date 2015-2-26 下午5:48:54
	 */
	public void checkWifiEnale() {
		mWifiManager.setWifiEnabled(!checkWifiState());
	}

	/**
	 * @description：创建一个WifiLock
	 * @author samy
	 * @date 2015-2-26 下午3:15:04
	 */
	public void creatWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("Test");
	}

	/**
	 * @description：锁定WifiLock
	 * @author samy
	 * @date 2015-2-26 下午3:14:44
	 */
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	/**
	 * @description：解锁WifiLock
	 * @author samy
	 * @date 2015-2-26 下午3:14:53
	 */
	public void releaseWifiLock() {
		// 判断时候锁定
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	public boolean connectToNetID(int netID) {
		System.out.println("ConnectToNetID netID=" + netID);
		return mWifiManager.enableNetwork(netID, true);
	}

	/**
	 * 指定配置好的网络进行连接   ;根据ID值来自动连接WIFI网络
	 * @param index ID值
	 */
	public void connectWifiBySSID(int index) {
		// 索引大于配置好的网络索引返回
		if (index > mWifiConfiguration.size()) { return; }
		// 连接配置好的指定ID的网络
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
	}

	/**
	 * 根据WifiConfiguration对象来自动连接WIFI网络 ;添加一个网络并连接   
	 * @param wcg WifiConfiguration对象
	 */
	public void connectWifiByConfig(WifiConfiguration wifiConfiguration) {
		int wcgID = mWifiManager.addNetwork(wifiConfiguration);
		boolean b = mWifiManager.enableNetwork(wcgID, true);
	}

	/**
	 * @description：忘记指定ID的网络
	 * @author samy
	 * @date 2015-2-26 下午7:28:40
	 */
	public void removeNetWorkById(int netId) {
		mWifiManager.removeNetwork(netId);
	}

	/**
	 *  断开指定ID的网络
	 * @param ID
	 */
	public void disconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

	/**
	 * 外部调用用法
	 * 自动连接指定SSID的wifi热点（不加密/加密）
	 */
	// WifiAdmin wifiAdmin = new WifiAdmin(this);
	// wifiAdmin.openWifi();
	// wifiAdmin.startScan();
	// wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo("XXX", "XXX", 3));
	// wifiAdmin.connectWifiBySSID(0);

	/**
	 * @description：然后是一个实际应用方法，只验证过没有密码的情况
	 * 分为三种情况：1没有密码2用wep加密3用wpa加密
	 * @author samy
	 * @date 2015-2-26 下午3:23:44
	 */
	public WifiConfiguration createWifiInfo(String ssid, String bssid, String password, WifiCipherType type) {
		int priority;
		WifiConfiguration config = this.isExsits(ssid);
		if (config != null) {
			// Log.v(TAG, "####之前配置过这个网络，删掉它");
			// wifiManager.removeNetwork(config.networkId); // 如果之前配置过这个网络，删掉它

			// 本机之前配置过此wifi热点，调整优先级后，直接返回
			return setMaxPriority(config);
		}

		config = new WifiConfiguration();
		/* 清除之前的连接信息 */
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + ssid + "\"";
		config.status = WifiConfiguration.Status.ENABLED;
		// config.BSSID = BSSID;
		// config.hiddenSSID = true;
		priority = getMaxPriority() + 1;
		if (priority > 99999) {
			priority = shiftPriorityAndSave();
		}
		config.priority = priority; // 2147483647;

		if (type == WifiCipherType.WIFI_CIPHER_NOPASS) {// WIFICIPHER_NOPASS
			Log.i(TAG, "没有密码");
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		else if (type == WifiCipherType.WIFI_CIPHER_WEP) { // WIFICIPHER_WEP
			Log.v(TAG, "WEP加密，密码" + password);
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + password + "\"";
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		else if (type == WifiCipherType.WIFI_CIPHER_WPA_EAP) {// WIFICIPHER_WPA
			Log.v(TAG, "WPA_EAP加密，密码" + password);
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);在if(type == 3)中注释掉;“加入后面两句，否则当wifi热点需要输入密码时，无法加入网络。
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		else if (type == WifiCipherType.WIFI_CIPHER_WPA_PSK) {
			Log.w(TAG, "WPA加密，密码" + password);

			config.preSharedKey = "\"" + password + "\"";
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN | WifiConfiguration.Protocol.WPA);
		}
		else if (type == WifiCipherType.WIFI_CIPHER_WPA2_PSK) {
			Log.w(TAG, "WPA2-PSK加密，密码=======" + password);
			config.preSharedKey = "\"" + password + "\"";
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		}
		else {
			return null;
		}
		return config;
	}

	public int createWifiInfo2(ScanResult wifiinfo, String pwd) {
		WifiCipherType type;
		if (wifiinfo.capabilities.contains("WPA2-PSK")) {// WPA-PSK加密
			type = WifiCipherType.WIFI_CIPHER_WPA2_PSK;
		}
		else if (wifiinfo.capabilities.contains("WPA-PSK")) {// WPA-PSK加密
			type = WifiCipherType.WIFI_CIPHER_WPA_PSK;
		}
		else if (wifiinfo.capabilities.contains("WPA-EAP")) {// WPA-EAP加密
			type = WifiCipherType.WIFI_CIPHER_WPA_EAP;
		}
		else if (wifiinfo.capabilities.contains("WEP")) {// WEP加密
			type = WifiCipherType.WIFI_CIPHER_WEP;
		}
		else {// 无密码
			type = WifiCipherType.WIFI_CIPHER_NOPASS;
		}

		WifiConfiguration config = createWifiInfo(wifiinfo.SSID, wifiinfo.BSSID, pwd, type);
		if (config != null) {
			return mWifiManager.addNetwork(config);
		}
		else {
			return -1;
		}
	}

	/**
	 * 这个方法还有问题
	 * @description：然后是一个实际应用方法，只验证过没有密码的情况
	 * 分为三种情况：1没有密码2用wep加密3用wpa加密
	 * @author samy
	 * @date 2015-2-26 下午3:23:44
	 */
	public WifiConfiguration createWifiInfo3(String ssid, String password, int type) {
		int priority;
		WifiConfiguration config = this.isExsits(ssid);
		if (config != null) {
			// Log.v(TAG, "####之前配置过这个网络，删掉它");
			// wifiManager.removeNetwork(config.networkId); // 如果之前配置过这个网络，删掉它

			// 本机之前配置过此wifi热点，调整优先级后，直接返回
			return setMaxPriority(config);
		}

		config = new WifiConfiguration();
		/* 清除之前的连接信息 */
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + ssid + "\"";
		config.status = WifiConfiguration.Status.ENABLED;
		// config.BSSID = BSSID;
		// config.hiddenSSID = true;
		priority = getMaxPriority() + 1;
		if (priority > 99999) {
			priority = shiftPriorityAndSave();
		}
		config.priority = priority; // 2147483647;

		if (type == 0) {// WIFICIPHER_NOPASS
			Log.i(TAG, "没有密码");
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		else if (type == 1) { // WIFICIPHER_WEP
			Log.v(TAG, "WEP加密，密码" + password);
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + password + "\"";
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		else if (type == 2) {// WIFICIPHER_WPA
			Log.v(TAG, "WPA_EAP加密，密码" + password);
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);在if(type == 3)中注释掉;“加入后面两句，否则当wifi热点需要输入密码时，无法加入网络。
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		else if (type == 3) {
			Log.w(TAG, "WPA加密，密码" + password);

			config.preSharedKey = "\"" + password + "\"";
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN | WifiConfiguration.Protocol.WPA);
		}
		else if (type == 4) {
			Log.w(TAG, "WPA2-PSK加密，密码=======" + password);
			config.preSharedKey = "\"" + password + "\"";
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		}
		else {
			return null;
		}
		return config;
	}

	/**
	 * 查看以前是否也配置过这个网络
	 * @description：检查wifi列表中是否有以输入参数为名的wifi热点，
	 * 如果存在，则在CreateWifiInfo方法开始配置wifi网络之前将其移除，以避免ssid的重复
	 * @author samy
	 * @date 2015-2-26 下午3:28:28
	 */
	public WifiConfiguration isExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
		if (null == existingConfigs) { return null; }
		for (WifiConfiguration existingConfig : existingConfigs) {
			System.out.println("existingConfig == " + existingConfig.toString());
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				System.out.println("existingConfig.SSID == " + existingConfig.SSID + " SSID == " + SSID);
				return existingConfig;
			}
		}
		return null;
	}

	/**
	 * @description：直接通过命令获取系统保存的Wifi配置信息
	 *其实可以通过这个获取：
	 * wifiConfigurations.clear();
	 * for (WifiConfiguration temp : wifiManager.getConfiguredNetworks()) {
	 * wifiConfigurations.add(new MyWifiConfiguration(temp));
	 * } 
	 * @author samy
	 * @date 2015-2-27 下午8:13:10
	 */
	public List<UserWifiInfo> getUserWifiInfoList() throws Exception {
		List<UserWifiInfo> wifiInfos = new ArrayList<UserWifiInfo>();
		Process process = null;
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		StringBuffer wifiConf = new StringBuffer();
		try {
			process = Runtime.getRuntime().exec("su");// Process[pid=31146]
			dataOutputStream = new DataOutputStream(process.getOutputStream());
			dataInputStream = new DataInputStream(process.getInputStream());
			dataOutputStream.writeBytes("cat /data/misc/wifi/*.conf\n");
			dataOutputStream.writeBytes("exit\n");
			dataOutputStream.flush();
			InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				wifiConf.append(line);
			}
			bufferedReader.close();
			inputStreamReader.close();
			process.waitFor();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			try {
				if (dataOutputStream != null) {
					dataOutputStream.close();
				}
				if (dataInputStream != null) {
					dataInputStream.close();
				}
				process.destroy();
			}
			catch (Exception e) {
				throw e;
			}
		}

		Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
		Matcher networkMatcher = network.matcher(wifiConf.toString());
		while (networkMatcher.find()) {
			String networkBlock = networkMatcher.group();
			Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
			Matcher ssidMatcher = ssid.matcher(networkBlock);

			if (ssidMatcher.find()) {
				UserWifiInfo wifiInfo = new UserWifiInfo();
				wifiInfo.Ssid = ssidMatcher.group(1);
				Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
				Matcher pskMatcher = psk.matcher(networkBlock);
				if (pskMatcher.find()) {
					wifiInfo.Password = pskMatcher.group(1);
				}
				else {
					wifiInfo.Password = "无密码";
				}
				wifiInfos.add(wifiInfo);
			}
		}
		return wifiInfos;
	}

	/***
	 * @description：查看扫描结果   
	 * @author samy
	 * @date 2015-2-26 下午3:19:29
	 */
	public StringBuilder lookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
			// 将ScanResult信息转换成一个字符串包
			// 其中把包括：BSSID、SSID、capabilities、frequency、level
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("/n");
		}
		return stringBuilder;
	}
	
	
	/**
	 * @description： 启动wifi一个Wifi热点
	 * @author samy
	 * @date 2015-2-27 下午9:17:38
	 */
	public void startAWifiHot(String wifiName) {
		Log.i(TAG, "into startAWifiHot(String wifiName) wifiName =" + wifiName);
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
		stratWifiAp(wifiName);
		Log.i(TAG, "out startAWifiHot(String wifiName)");
	}
	
	/**
	 * @description：启动 wifi热点
	 * @author samy
	 * @date 2015-2-27 下午9:13:53
	 */
	public  boolean stratWifiAp(String wifiName) {
		Log.i(TAG, "into startWifiAp（） 启动一个Wifi 热点！");
		Method method1 = null;
		boolean ret = false;
		try {
			method1 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			WifiConfiguration apConfig = null;
//			WifiConfiguration apConfig = createPassHotWifiConfig(wifiName, Global.PASSWORD);
			ret = (Boolean) method1.invoke(mWifiManager, apConfig, true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Log.d(TAG, "stratWifiAp() IllegalArgumentException e");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Log.d(TAG, "stratWifiAp() IllegalAccessException e");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Log.d(TAG, "stratWifiAp() InvocationTargetException e");
		} catch (SecurityException e) {
			e.printStackTrace();
			Log.d(TAG, "stratWifiAp() SecurityException e");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Log.d(TAG, "stratWifiAp() NoSuchMethodException e");
		}
		Log.i(TAG, "out startWifiAp（） 启动一个Wifi 热点！");
		return ret;

	}

	/**
	 * @description：关闭wifi热点
	 * @author samy
	 * @date 2015-2-27 下午9:13:53
	 */
	private boolean closeWifiAp(WifiManager wifiManager) {

		Log.i(TAG, "into closeWifiAp（） 关闭一个Wifi 热点！");
		boolean ret = false;
		if (isWifiApEnabled(wifiManager)) {
			try {
				Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
				method.setAccessible(true);
				WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);
				Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
				ret = (Boolean) method2.invoke(wifiManager, config, false);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i(TAG, "out closeWifiAp（） 关闭一个Wifi 热点！");
		return ret;
	}

	/**
	 * @description：检测Wifi 热点是否可用
	 * @author samy
	 * @date 2015-2-27 下午9:16:34
	 */
	public boolean isWifiApEnabled(WifiManager wifiManager) {
		try {
			Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
			method.setAccessible(true);
			return (Boolean) method.invoke(wifiManager);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @description：wep密码保护 config.wepKeys[0] = "" + mPasswd + ""; 是关键
	 * @author samy
	 * @date 2015-2-27 下午9:16:26
	 */
	private WifiConfiguration createPassHotWifiConfig(String mSSID, String mPasswd) {

		Log.i(TAG, "out createPassHotWifiConfig（） 新建一个Wifi配置！ SSID =" + mSSID + " password =" + mPasswd);
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();

		config.SSID = mSSID;
		config.wepKeys[0] = mPasswd;
		config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		config.wepTxKeyIndex = 0;
		config.priority = 0;

		Log.i(TAG, "out createPassHotWifiConfig（） 启动一个Wifi配置！ config.SSID=" + config.SSID + "password =" + config.wepKeys[0]);
		return config;
	}

	/**
	 * 获取wifi网络配置列表
	 * @return
	 */
	public List<WifiConfiguration> getConfiguration() {
		return mWifiManager.getConfiguredNetworks();
		// return mWifiConfiguration;
	}

	/**
	 * 得到网络列表
	 * @return List<ScanResult>
	 */
	public List<ScanResult> getWifiList() {
		return mWifiManager.getScanResults();
		// return mWifiList;
	}

	/*
	 * info.getBSSID()； 获取BSSID地址。
	 * info.getSSID()； 获取SSID地址。 需要连接网络的ID
	 * info.getIpAddress()； 获取IP地址。4字节Int, XXX.XXX.XXX.XXX 每个XXX为一个字节
	 * info.getMacAddress()； 获取MAC地址。
	 * info.getNetworkId()； 获取网络ID。
	 * info.getLinkSpeed()； 获取连接速度，可以让用户获知这一信息。
	 * info.getRssi()； 获取RSSI，RSSI就是接受信号强度指示
	 */

	/*
	 * getBSSID() 获取BSSID
	 * getDetailedStateOf() 获取客户端的连通性
	 * getHiddenSSID() 获得SSID 是否被隐藏
	 * getIpAddress() 获取IP 地址
	 * getLinkSpeed() 获得连接的速度
	 * getMacAddress() 获得Mac 地址
	 * getRssi() 获得802.11n 网络的信号
	 * getSSID() 获得SSID
	 * getSupplicanState() 返回具体客户端状态的信息
	 */

	public WifiInfo getWifiInfo() {
		return mWifiInfo = mWifiManager.getConnectionInfo();
	}

	// /**
	// * 得到WifiInfo
	// * @return String
	// */
	// public String getWifiInfo() {
	// return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	// }

	/**
	 * 获取MAC地址
	 * @return String
	 */
	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	/**
	 * 得到接入点的SSID
	 * @return String
	 */
	public String getSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	/**
	 * 得到IP地址
	 * @return IP地址
	 */
	public int getIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	/**
	 * 获取已经连接上的网络的ID值
	 * @return ID
	 */
	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	/**
	 * @description：得到接入点的BSSID   
	 * @author samy
	 * @date 2015-2-26 下午3:22:00
	 */
	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	/**
	 * @description：设置当前WIFI为最大线程处理事务
	 * @author samy
	 * @date 2015-2-26 下午7:24:04
	 */
	public WifiConfiguration setMaxPriority(WifiConfiguration config) {
		int priority = getMaxPriority() + 1;
		if (priority > 99999) {
			priority = shiftPriorityAndSave();
		}
		config.priority = priority; // 2147483647;
		System.out.println("priority=" + priority);
		mWifiManager.updateNetwork(config);
		// 本机之前配置过此wifi热点，直接返回
		return config;
	}

	private int getMaxPriority() {
		List<WifiConfiguration> localList = this.mWifiManager.getConfiguredNetworks();
		int i = 0;
		Iterator<WifiConfiguration> localIterator = localList.iterator();
		while (true) {
			if (!localIterator.hasNext()) return i;
			WifiConfiguration localWifiConfiguration = (WifiConfiguration) localIterator.next();
			if (localWifiConfiguration.priority <= i) continue;
			i = localWifiConfiguration.priority;
		}
	}

	private int shiftPriorityAndSave() {
		List<WifiConfiguration> localList = this.mWifiManager.getConfiguredNetworks();
		sortByPriority(localList);
		int i = localList.size();
		for (int j = 0;; ++j) {
			if (j >= i) {
				this.mWifiManager.saveConfiguration();
				return i;
			}
			WifiConfiguration localWifiConfiguration = (WifiConfiguration) localList.get(j);
			localWifiConfiguration.priority = j;
			this.mWifiManager.updateNetwork(localWifiConfiguration);
		}
	}

	private void sortByPriority(List<WifiConfiguration> paramList) {
		Collections.sort(paramList, new SamyWifiManagerCompare());
	}

	class SamyWifiManagerCompare implements Comparator<WifiConfiguration> {
		public int compare(WifiConfiguration paramWifiConfiguration1, WifiConfiguration paramWifiConfiguration2) {
			return paramWifiConfiguration1.priority - paramWifiConfiguration2.priority;
		}
	}
}