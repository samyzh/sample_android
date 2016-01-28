package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps;

public class ByteHexHelper {

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) { return ""; }
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String byteToHexString(byte src) {
		StringBuilder stringBuilder = new StringBuilder("");
		int v = src & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);
		return stringBuilder.toString();
	}

	public static int intPackLength(String str) {
		int intLength = Integer.valueOf(str, 16);// 十六进制字符串转换为十进制
		return intLength;
	}

	public static int intPackLength(byte[] str) {
		String byteStr = bytesToHexString(str);
		int intLength = Integer.valueOf(byteStr, 16);// 十六进制字符串转换为十进制
		return intLength;
	}

	public static int byteToInt(byte src) {
		return src & 0xFF;
	}

	/**
	 * 将16进制字符串转换为byte数组
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			byte[] bytes = new byte[0];
			return bytes;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 转换char类型到byte类型
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static byte[] cutOutByte(byte[] b, int j) {
		if (b.length == 0 || j == 0) { return null; }
		byte[] bjq = new byte[j];
		for (int i = 0; i < j; i++) {
			bjq[i] = b[i];
		}
		return bjq;
	}

}
