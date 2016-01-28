package com.zsy.frame.sample.control.android.a06fourcomponents.service.helps;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

public class DiskTools {

	public DiskTools() {
	}

	/**
	 * 保存到sdcard卡上
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean saveToDisk(String fileName, byte[] data) {
		boolean flag = false;
		File file = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(new File(file, fileName));
				outputStream.write(data, 0, data.length);
				flag = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					}
					catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		return flag;
	}

}
