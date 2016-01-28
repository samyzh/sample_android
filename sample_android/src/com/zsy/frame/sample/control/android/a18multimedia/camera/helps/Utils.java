package com.zsy.frame.sample.control.android.a18multimedia.camera.helps;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.zsy.frame.sample.control.android.a18multimedia.camera.TakePhotoBySurfaceviewAct;

public class Utils {
	private static final String SDPATH = TakePhotoBySurfaceviewAct.imgPath;

	public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	public static Drawable getBitmapForPath(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) { return null; }
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		Drawable drawable = convertBitmap2Drawable(bitmap);
		return drawable;
	}

	public static Bitmap getBitmapForPath(String filePath, int size) {
		File f = new File(filePath);
		if (!f.exists()) { return null; }
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = size;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		return bitmap;
	}

	/*
	 * get screen density
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String path) {
		File file = new File(path);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory()) return;
		for (File file : dir.listFiles()) {
			if (file.isFile()) file.delete(); // 删除所有文件
			else if (file.isDirectory()) deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) { return false; }
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/*
	 * 删除指定文件
	 */
	public static void delSDFile(String filename) {
		if (filename != null) {
			File file = new File(filename);
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				} else if (file.isDirectory()) {
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						delSDFile(files[i].getName());
					}
				}
				file.delete();
			}
		}
	}
}
