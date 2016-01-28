package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

/**
 * 蓝牙客户端连接线程
 * 蓝牙Socket通信:
 * 客户端的实现
 */
public class BluetoothClientConnThread extends Thread {
	private Handler serviceHandler; // 用于向客户端Service回传消息的handler
	private BluetoothDevice serverDevice; // 服务器设备
	private BluetoothSocket socket; // 通信Socket
	/**一些常量，代表服务器的名称 */
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";

	/**
	 * 构造函数
	 */
	public BluetoothClientConnThread(Handler handler, BluetoothDevice serverDevice) {
		this.serviceHandler = handler;
		this.serverDevice = serverDevice;
	}

	@Override
	public void run() {
		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
		try {
			// 建立连接
			// 通过搜索得到服务器端的BluetoothService
			// 调用BluetoothService的createRfcommSocketToServiceRecord(String)方法获取BluetoothSocket（该UUID应该同于服务器端的UUID）
			// 调用BluetoothSocket的connect()方法（该方法为block方法），如果UUID同服务器端的UUID匹配，并且连接被服务器端accept，则connect()方法返回
			// 注意：在调用connect()方法之前，应当确定当前没有搜索设备，否则连接会变得非常慢并且容易失败
			// 创建一个Socket连接：只需要服务器在注册时的UUID号
			socket = serverDevice.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);
			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			socket.connect();
		}
		catch (Exception ex) {
			try {
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("BluetoothClientConnThread，e=" + ex.getMessage());
			// 发送连接失败消息
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			return;
		}
		/**
		 * 连接完成功后发送消息；
		 */
		// 发送连接成功消息，消息的obj参数为连接的socket
		Message msg = serviceHandler.obtainMessage();
		msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
		msg.obj = socket;
		msg.sendToTarget();
	}
	// /* 停止客户端连接 */
	// private void shutdownClient() {
	// new Thread() {
	// @Override
	// public void run() {
	// if(clientConnectThread!=null)
	// {
	// clientConnectThread.interrupt();
	// clientConnectThread= null;
	// }
	// if(mreadThread != null)
	// {
	// mreadThread.interrupt();
	// mreadThread = null;
	// }
	// if (socket != null) {
	// try {
	// socket.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// socket = null;
	// }
	// };
	// }.start();
	// }
}
