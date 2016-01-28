package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

/**
 * 连接管理（数据通信）
 * 分别通过BluetoothSocket的getInputStream()和getOutputStream()方法获取InputStream和OutputStream
 * 使用read(bytes[])和write(bytes[])方法分别进行读写操作
 * 注意：read(bytes[])方法会一直block，知道从流中读取到信息，
 * 而write(bytes[])方法并不是经常的block（比如在另一设备没有及时read或者中间缓冲区已满的情况下，write方法会block）
 */
/**
 * 蓝牙通讯线程;用于连接客户端和服务器线程
 * 
 */
public class BluetoothCommunThread extends Thread {
	/**与Service通信的Handler*/
	private Handler serviceHandler;
	private BluetoothSocket socket;
	/**对象输入流*/
	private ObjectInputStream inStream;
	/**对象输出流*/
	private ObjectOutputStream outStream;
	/**运行标志位*/
	public volatile boolean isRun = true;

	/**
	 * 构造函数
	 * 
	 * @param handler
	 *            用于接收消息
	 * @param socket
	 */
	public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
		this.serviceHandler = handler;
		this.socket = socket;
		try {
			this.outStream = new ObjectOutputStream(socket.getOutputStream());
			this.inStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		catch (Exception e) {
			try {
				socket.close();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			// 发送连接失败消息
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!isRun) {
				break;
			}
			try {
				Object obj = inStream.readObject();
				// Looper.prepare();//这里可能会循环更新主界面；主线程和次线程会循环打交道
				// 发送成功读取到对象的消息，消息的obj参数为读取到的对象
				Message msg = serviceHandler.obtainMessage();
				msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
				msg.obj = obj;
				msg.sendToTarget();
				// Looper.loop();
			}
			catch (Exception ex) {
				// 发送连接失败消息
				serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
				ex.printStackTrace();
				return;
			}
		}

		shutdownConmution();
	}

	/**
	 * @description：关闭连接器；
	 * 其实在这里做的好的话，发送接收消息后，在关闭BluetoothServerConnThread和BluetoothClientConnThread线程；
	 * @author samy
	 * @date 2015-2-10 下午9:49:49
	 */
	private void shutdownConmution() {
		if (inStream != null) {
			try {
				inStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outStream != null) {
			try {
				outStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @description：发送数据写东西；写入一个可序列化的对象
	 * @author samy
	 * @date 2015-2-10 下午9:50:09
	 */
	public void writeObject(Object obj) {
		try {
			outStream.flush();
			outStream.writeObject(obj);
			outStream.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	//
	// //发送数据
	// private void sendMessageHandle(String msg)
	// {
	// if (socket == null)
	// {
	// Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
	// return;
	// }
	// try {
	// OutputStream os = socket.getOutputStream();
	// os.write(msg.getBytes());
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// msgList.add(msg);
	// mAdapter.notifyDataSetChanged();
	// mListView.setSelection(msgList.size() - 1);
	// }
	// //读取数据
	// private class readThread extends Thread {
	// @Override
	// public void run() {
	//
	// byte[] buffer = new byte[1024];
	// int bytes;
	// InputStream mmInStream = null;
	//
	// try {
	// mmInStream = socket.getInputStream();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// while (true) {
	// try {
	// // Read from the InputStream
	// if( (bytes = mmInStream.read(buffer)) > 0 )
	// {
	// byte[] buf_data = new byte[bytes];
	// for(int i=0; i<bytes; i++)
	// {
	// buf_data[i] = buffer[i];
	// }
	// String s = new String(buf_data);
	// Message msg = new Message();
	// msg.obj = s;
	// msg.what = 1;
	// LinkDetectedHandler.sendMessage(msg);
	// }
	// } catch (IOException e) {
	// try {
	// mmInStream.close();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// break;
	// }
	// }
	// }
	// }
}
