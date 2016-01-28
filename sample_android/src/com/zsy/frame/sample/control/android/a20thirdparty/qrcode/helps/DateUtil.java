package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * @Description:时间格式化工具
 */
public class DateUtil {
	private static SimpleDateFormat formatBuilder;
	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private static SimpleDateFormat xmppDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat collectionDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时");
	public static final int WEEKDAYS = 7;

	public static String[] WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");

	public static String getHhMmDate(String format) {
		return sdf1.format(getDateFromString(format));
	}

	/**
	 * 日期变量转成对应的星期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFromString(date));
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {
			return null;
		}

		return WEEK[dayIndex - 1] + "  " + sdf4.format(getDateFromString(date));
	}

	/**
	 * 
	 * 方法概述：判断两个时间戳是否相差指定时间
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @return
	 * @return boolean
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-5-22 上午9:56:39
	 */
	public static boolean isTimeBetweenSeconds(String postTimeStamp, String preTimeStamp, long millisecond) {
		if (Long.parseLong(postTimeStamp) - Long.parseLong(preTimeStamp) <= millisecond) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 方法概述：获取两个时间点之间的差
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @param begin
	 * @param @param end
	 * @param @param dateFormate
	 * @param @return
	 * @return long
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-3-28 上午10:39:37
	 */
	public static long compareTime(String begin, String end, SimpleDateFormat dateFormate) {
		long sss = 0;
		try {
			Date date1 = dateFormate.parse(begin);
			Date date2 = dateFormate.parse(end);
			long diff = date2.getTime() - date1.getTime();
			sss = diff / 1000; // 秒
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}

	public static String formatDate(Date paramDate) {
		long currentTime = new Date().getTime();
		long occurTime = paramDate.getTime();
		long blankTime = currentTime - occurTime;
		String retTime;

		if (blankTime > 86400000L) {
			retTime = sdf.format(paramDate);
		}
		else if (blankTime < 1800000L) {
			retTime = "刚刚";
		}
		else if (blankTime > 1800000L && blankTime < 3600000L) {
			retTime = "半小时前";
		}
		else {
			Object[] arr1 = new Object[2];
			arr1[0] = Long.valueOf(blankTime / 3600000L);
			arr1[1] = "小时前";
			retTime = String.format("%d%s", arr1);
		}
		return retTime;
	}

	public static String formatDate(long paramDate) {
		long currentTime = new Date().getTime();
		long blankTime = currentTime - paramDate;
		String retTime;

		if (blankTime > 86400000L) {
			retTime = sdf.format(new Date(paramDate));
		}
		else if (blankTime < 1800000L) {
			retTime = "刚刚";
		}
		else if (blankTime > 1800000L && blankTime < 3600000L) {
			retTime = "半小时前";
		}
		else {
			Object[] arr1 = new Object[2];
			arr1[0] = Long.valueOf(blankTime / 3600000L);
			arr1[1] = "小时前";
			retTime = String.format("%d%s", arr1);
		}
		return retTime;
	}

	/**
	 * @Title 格式化标准日期
	 * @Description
	 * @param
	 * @return 返回格式如：1970-01-01 :00:00
	 * @throws
	 */
	public static String formatStandardDate(long paramDate) {
		String retDate = null;
		try {
			retDate = sdf1.format(new Date(paramDate));
		}
		catch (Exception e) {
			retDate = "1970-01-01 00:00";
		}
		return retDate;
	}

	public static String getChatTime(long timesamp) {
		String result = "";
		long today = System.currentTimeMillis() /1000;  // 秒
		long other = timesamp / 1000;
		long daySeconds = 24 * 60 * 60;
		int temp = (int) (today / daySeconds) - (int) (other / daySeconds);
		switch (temp) {
			case 0:
				result = getHourAndMin(timesamp);
				break;
			case 1:
				result = "昨天 " + getHourAndMin(timesamp);
				break;
			case 2:
				result = "前天 " + getHourAndMin(timesamp);
				break;
			case 3:
			case 4:
			case 5:
				result = getDateToWeek(timesamp);
				break;

			default:
				result = getTime(timesamp);
				break;
		}

		return result;
	}
	/**
	 * 
	 * 方法概述：通过时间戳格式化聊天时间
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @param timesamp
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-4-10 下午5:16:42
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getChatTime_old(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// tsp modify 原始: SimpleDateFormat sdf = new SimpleDateFormat("dd")
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
			case 0:
				result = getHourAndMin(timesamp);
				break;
			case 1:
				result = "昨天 " + getHourAndMin(timesamp);
				break;
			case 2:
				result = "前天 " + getHourAndMin(timesamp);
				break;

			default:
				result = getTime(timesamp);
				break;
		}

		return result;
	}

	/**
	 * 
	 * 方法概述：获取和交易相关胡时间
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @param timesamp
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-4-10 下午5:16:42
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTradeTime(long timesamp) {
		Date otherDay = new Date(timesamp);

		return xmppDateFormat.format(otherDay);
	}

	public static String getTradeTime(String timestr) {
		if (TextUtils.isEmpty(timestr)) {
			return "";
		}
		timestr += "000";
		long timesamp = Long.valueOf(timestr);
		return getTradeTime(timesamp);
	}

	/**
	 * 集赞列表显示日期格式
	 * 
	 */
	public static String getCollectionTradeTime(String timestr) {
		if (TextUtils.isEmpty(timestr)) {
			return "";
		}
		timestr += "000";
		long timesamp = Long.valueOf(timestr);
		return getCollectionTradeTime(timesamp);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getCollectionTradeTime(long timesamp) {
		Date otherDay = new Date(timesamp);

		return collectionDateFormat.format(otherDay);
	}
	/**
	 * 
	 * 方法概述：消息格式，在消息聊天时使用此格式
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-3-25 上午11:42:20
	 */
	public static String getDate() {
		return xmppDateFormat.format(new Date());
	}

	/**
	 * 
	 * 方法概述：从字符串中提取出Date类型日期，在XMPP获取消息时使用
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @param dateStr
	 * @param @return
	 * @return Date
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-3-26 下午6:40:42
	 */
	public static Date getDateFromString(String dateStr) {
		Date date = null;
		try {
			date = xmppDateFormat.parse(dateStr);
		}
		catch (ParseException e) {
		}
		return date;
	}

	/**
	 * 
	 * 方法概述：获取标准时间
	 * 
	 * @description 方法详细描述：
	 * @author ldm
	 * @param @param timeStamp 时间戳
	 * @param @return
	 * @return Date
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-5-16 下午2:43:21
	 */
	public static String getStandardDataString(String timestr) {
		try {
			if (TextUtils.isEmpty(timestr)) {
				return "";
			}
			timestr += "000";
			Date date = new Date(Long.valueOf(timestr));
			return xmppDateFormat.format(date);
		}
		catch (Exception e) {
		}
		return "时间异常";
	}
	
	public static String getYMString(String timestr) {
		try {
			if (TextUtils.isEmpty(timestr)) {
				return "";
			}
			timestr += "000";
			return getMonthByTimeStamp(timestr);
		}
		catch (Exception e) {
		}
		return "时间异常";
	}

	/**
	 * 
	 * 方法概述：获取当前时间的连续字符串 1.作为文件名使用 2.不带文件后缀
	 * 
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: DateUtil.java
	 * @Package com.huika.huixin.utils
	 * @date 2014-3-28 上午10:11:41
	 */
	public static String getFileNameByTime() {
		return sDateFormat.format(new java.util.Date());
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getDateToWeek(long time) {
		Date otherDay = new Date(time);
		return getDateToWeek(xmppDateFormat.format(otherDay));
	}
	
	public static String getIntervalDays(long sl, long el) {
		long ei = el - sl;
		// 根据毫秒数计算间隔天数
		int month = (int) (ei / (1000 * 60 * 60 * 24 * 12));
		int days = (int) (ei / (1000 * 60 * 60 * 24));
		int housrs = (int) (ei / (1000 * 60 * 60));
		int Minutes = (int) (ei / (1000 * 60));
		int ss = (int) (ei / (1000));
		if (ss <= 60) {
			return ss + " 秒钟前";
		}
		else if (Minutes <= 60) {
			return Minutes + " 分钟前";
		}
		else if (housrs <= 24) {
			return housrs + " 小时前";
		}
		else if (days <= 30) {
			return days + " 天前";
		}
		else {
			return month + " 月前";
		}

	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		return format.format(new Date(time));
	}

	public static String getTimeStamp() { // 得到的是一个时间戳
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf2.format(new Date());// 将当前日期进行格式化操作
	}

	public String getDateComplete() { // 得到的是一个日期：格式为：yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
		return sdf2.format(new Date());// 将当前日期进行格式化操作
	}

	/*
	 * 方法概述：根据时间戳返回当前的年月
	 * @description 方法详细描述：
	 * @author 刘成伟（wwwlllll@126.com）
	 * @param @param timeStamp
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: ViewMoreRecordActivity.java
	 * @Package com.huika.huixin.control.wealth.activity
	 * @date 2014-4-15 下午6:24:57
	 */
	public static String getMonthByTimeStamp(String timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String monthString = sdf.format(Long.parseLong(timeStamp));
		return monthString;
	}

	/**
	 * 
	 * @author:lzt
	 * @Title: getShippintTime 优惠详情时间显示规则
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getShippintTime(String time) {
		Date otherDay = getDateFromString(time);
		
		return getChatTime(otherDay.getTime()); 
/*
		String result = "";

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Date today = new Date(System.currentTimeMillis());
		
		long todayTime = today.getTime();
		long otherTime = otherDay.getTime();
		
		int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
			case 0:
				result = getHourAndMin(time);
				break;
			case 1:
				result = "昨天 " + getHourAndMin(time);
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				result = getDateToWeek(time);
				break;

			default:
				result = getTime(time);
				break;
		}

		return result;  */
	}

	public static String getTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		return format.format(getDateFromString(time));
	}

	public static String getHourAndMin(String time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(getDateFromString(time));
	}

	/**
	 * 日期变量转成对应的星期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateToWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFromString(date));
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {
			return null;
		}

		return WEEK[dayIndex - 1] + "  " + sdf4.format(getDateFromString(date));
	}

	/**
	 * 
	 * @param time
	 * @return 倒计时
	 */
	public static String getTimeFromInt(long time) {

		if (time <= 0) {
			return "已结束";
		}
		long day = time / (1 * 60 * 60 * 24);
		long hour = time / (1 * 60 * 60) % 24;
		long minute = time / (1 * 60) % 60;
		long second = time / (1) % 60;
		return "集赞时间：" + day + "天" + hour + "小时" + minute + "分" + second + "秒";
	}
	
	// 将字符串转为时间戳
	public static long getXmppDateTime(String user_time) {
		long lTime = 0;
		Date d;
		try {
			d = xmppDateFormat.parse(user_time);
			lTime = d.getTime();
			lTime = lTime / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lTime;
	}
}
