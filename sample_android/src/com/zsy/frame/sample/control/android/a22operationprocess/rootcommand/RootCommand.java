package com.zsy.frame.sample.control.android.a22operationprocess.rootcommand;

import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;


/**
 * @description：调用su执行input命令
* 全局只调用一次init()和exit(),多次调用run()。
 * @author samy
 * @date 2015-4-12 下午7:56:41
 */
public class RootCommand {
	private String TAG="RootCommand";
	private Process process = null;
	private DataOutputStream os = null;
	public void init() {
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
		} catch (IOException e) {
			Log.e(TAG, getExceptionMessage(e));
		}
	}

	/**
	 * 模仿shell来执行命令，必须先root再使用
	 * @param command
	 * @return
	 */
	public boolean run(String command) {
		try {
			os.writeBytes(command + "\n");
			os.flush();
		} catch (Exception e) {
			Log.e(TAG, getExceptionMessage(e));
			return false;
		}
		return true;
	}

	/**
	 * 模仿shell来执行命令，必须先root再使用
	 * @param command
	 * @return
	 */
	public void release() {
		try {
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.e(TAG, getExceptionMessage(e));
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
	}
	
	private static String getExceptionMessage(Exception ex){
		String result="";
		StackTraceElement[] stes = ex.getStackTrace();
		for(int i=0;i<stes.length;i++){
			result=result+stes[i].getClassName() 
			+ "." + stes[i].getMethodName() 
			+ "  " + stes[i].getLineNumber() +"line"
			+"\r\n";
		}
		return result;
	}
}
