package com.zsy.frame.sample.utils;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

/**
 * @description：从assets 文件夹中获取文件并读取数据
 * @author samy
 * @date 2015年6月8日 下午12:08:00
 */
public class AssetsUtils {
	
	public static String getFromAssets(Context context,String fileName) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");// 你的文件的编码
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}