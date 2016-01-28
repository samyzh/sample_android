package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyTools {
	private static int maxFractionReserve = 2;
	private Pattern pattern = Pattern.compile("([\\d]*[.]?[\\d]{0," + Math.max(0, maxFractionReserve) + "}).*");

	/**
	 * 
	 * 方法概述：
	 * 
	 * @description 方法详细描述：保留两位有效数字
	 * @author lzt
	 * @param @param temp
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: MoneyTools.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-4-29 下午5:27:31
	 */
	public static String coreMatchCutMoney(String temp) {

		try {
			String result = coreMatchCut(temp);
			result = amendFraction(result); // 可选
			return result;
		}
		catch (NumberFormatException ignored) {
			return "0.00";
		}
		catch (NullPointerException ignored) {
			return "0.00";
		}
		catch (NoSuchElementException ignored) {
			return "0.00";
		}

	}

	/**
	 * 核心函数，取数字
	 * 
	 * @param src
	 *            - 需要截取的原始数字转换成的字符串
	 * @throws NumberFormatException
	 *             输入格式无法识别
	 * @throws NullPointerException
	 *             空字符串
	 * @throws NoSuchElementException
	 *             无法识别的数字
	 */
	private static String coreMatchCut(String src) throws NumberFormatException, NullPointerException, NoSuchElementException {
		Double.valueOf(src);
		Pattern pattern = Pattern.compile("([\\d]*[.]?[\\d]{0," + Math.max(0, maxFractionReserve) + "}).*");
		Matcher matcher = pattern.matcher(src);
		if (matcher.find())
			return matcher.group(1);

		throw new NoSuchElementException("unrecognized string '" + src + "'");
	}

	/**
	 * 可选：如果小数不足，则补齐
	 * 
	 * @param src
	 *            - 原始数字转化的字符串
	 */
	private static String amendFraction(String src) {
		String ret = src;
		ret = amendDot(src);

		if (maxFractionReserve > 0) {
			int lenFraction = ret.substring(ret.indexOf(".")).length();
			if (lenFraction < maxFractionReserve + 1) {
				for (int i = lenFraction; i < maxFractionReserve + 1; i++)
					ret += "0";
			}
		}
		return ret;
	}

	/**
	 * 可选：整数位填0(如果没有)
	 * 
	 * @param src
	 *            - 原始数字转化的字符串
	 */
	private static String amendInteger(String src) {
		String ret = src;
		ret = amendDot(ret);
		int posDot = ret.indexOf(".");
		if (posDot == 0 || ret.length() == 0)
			ret = "0" + ret;
		return ret;
	}

	/** 这个不应该由外部调用 */
	private static String amendDot(String src) {
		String ret = src;
		int posDot = ret.indexOf(".");
		if (maxFractionReserve < 1 && posDot >= 0) {
			ret = ret.substring(0, ret.indexOf("."));
		}
		else if (maxFractionReserve > 0 && posDot < 0) {
			ret += ".";
		}
		return ret;
	}

}
