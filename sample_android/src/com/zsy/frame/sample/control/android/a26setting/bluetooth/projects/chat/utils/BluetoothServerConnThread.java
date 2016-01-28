package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 服务器连接线程；
 * 蓝牙Socket通信:
 * 服务器端的实现
 */
public class BluetoothServerConnThread extends Thread {
	private Handler serviceHandler; // 用于同Service通信的Handler
	private BluetoothAdapter adapter;
	private BluetoothSocket socket; // 用于通信的Socket
	private BluetoothServerSocket serverSocket;
	/**一些常量，代表服务器的名称 */
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";

	/**
	 * 构造函数
	 * @param handler
	 */
	public BluetoothServerConnThread(Handler handler) {
		this.serviceHandler = handler;
		adapter = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);// 休眠1秒，防止出错
			// 服务器端的实现
			// 通过调用BluetoothAdapter的listenUsingRfcommWithServiceRecord(String, UUID)方法来获取BluetoothServerSocket（UUID用于客户端与服务器端之间的配对）
			// 调用BluetoothServerSocket的accept()方法监听连接请求，如果收到请求，则返回一个BluetoothSocket实例（此方法为block方法，应置于新线程中）
			// 自定义Server接收配置如：btspp
			// serverSocket = adapter.listenUsingRfcommWithServiceRecord("Server", BluetoothTools.PRIVATE_UUID);
			serverSocket = adapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM, BluetoothTools.PRIVATE_UUID);
			socket = serverSocket.accept();
		}
		catch (Exception e) {
			// 发送连接失败消息
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			e.printStackTrace();
			// 打印连接失败信息
			Log.e("BluetoothServerConnThread,e=", e.getMessage());
			return;
		}
		finally {
			try {
				// 如果不想在accept其他的连接，则调用BluetoothServerSocket的close()方法释放资源（调用该方法后，之前获得的BluetoothSocket实例并没有close。
				// 但由于RFCOMM一个时刻只允许在一条channel中有一个连接，则一般在accept一个连接后，便close掉BluetoothServerSocket）
				serverSocket.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				// 打印关闭socket失败信息
				Log.e("close socket,e=", e.getMessage());
			}
		}
		// 把连接成功或失败的信息发送到服务器和客户端
		try {
			if (socket != null) {
				// 监听是否启动其他后台服务机制
				// byte[] data = getSocketResult(mInputStream);
				// String tempString = new String(data);
				// Log.i("蓝牙服务端监听str", tempString);
				// // 向客户端发送数据
				// if (tempString.equals("StartOnNet\n")) {
				// mOutputStream.write("haha".getBytes());
				// mOutputStream.flush();
				// if(!isServiceRunning("com.yqq.endClient3.service.GpsInfoCollectionService",BluethoothServer.this)){
				// // 开启GPS收集服务
				// gpsService= new Intent(BluethoothServer.this,
				// GpsInfoCollectionService.class);
				// startService(gpsService);
				// }
				// }

				// 发送连接成功消息，消息的obj字段为连接的socket
				// 如果连接上了，启动聊天线程；还得携带socket信息用于处理流
				Message msg = serviceHandler.obtainMessage();
				msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
				msg.obj = socket;
				msg.sendToTarget();
			}
			else {
				// 发送连接失败消息
				serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
				Log.e("e", "socket=null");
				return;
			}
		}
		catch (Exception e) {
			Log.e("socket,e", e.getMessage());
		}
	}

	// /* 停止服务器 */
	// private void shutdownServer() {
	// new Thread() {
	// @Override
	// public void run() {
	// if(startServerThread != null)
	// {
	// startServerThread.interrupt();
	// startServerThread = null;
	// }
	// if(mreadThread != null)
	// {
	// mreadThread.interrupt();
	// mreadThread = null;
	// }
	// try {
	// if(socket != null)
	// {
	// socket.close();
	// socket = null;
	// }
	// if (mserverSocket != null)
	// {
	// mserverSocket.close();/* 关闭服务器 */
	// mserverSocket = null;
	// }
	// } catch (IOException e) {
	// Log.e("server", "mserverSocket.close()", e);
	// }
	// };
	// }.start();
	// }
}
