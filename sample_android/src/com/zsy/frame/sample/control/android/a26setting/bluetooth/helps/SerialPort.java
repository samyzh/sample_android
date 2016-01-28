package com.zsy.frame.sample.control.android.a26setting.bluetooth.helps;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

/**
 * @description：android下的串口编程
 * 这个类中主要干了两件事情：
 * 创建了打开串口和关闭串口的本地方法
 * @author samy
 * @date 2015-2-10 下午11:06:19
 */
public class SerialPort {
	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;

	public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {

		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/bin/su");
				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" + "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) { throw new SecurityException(); }
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		mFd = open(device.getAbsolutePath(), baudrate, flags);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
	}

	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	/**
	 * @description：这个下面为对应的代码
	 * @author samy
	 * @date 2015-2-10 下午11:10:31
	 * 
	 * private FileDescriptor mFd;  
	private FileInputStream mFileInputStream;  
	private FileOutputStream mFileOutputStream;  
	mFd = open(device.getAbsolutePath(), baudrate, flags);  
	mFileInputStream = new FileInputStream(mFd);  
	mFileOutputStream = new FileOutputStream(mFd);  
	
	mFileDescriptor与fd关联并返回给JAVA层。
	 * 
	 */

	// JNI
	private native static FileDescriptor open(String path, int baudrate, int flags);

	public native void close();

	static {
		System.loadLibrary("serial_port");
	}

}
