package com.kd.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static String getCurrentTime() {
		String time = sdf.format(Calendar.getInstance().getTime());
		return time;
	}

	public static String getCurrentDay(){
		String day = df.format(Calendar.getInstance().getTime());
		return day;
	}

	/**
	 *
	 * @param format = "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		String time = sdFormat.format(Calendar.getInstance().getTime());
		return time;
	}

	/**
	 * 获取几天前yyyy-mm-dd hh:mm:ss
	 * @param day
	 * @return
	 */
	public static String getDaysBefore(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = sdf.format(getDateBefore(currentDate, day));
		System.out.println("time is "+time);
		return time;
	}
	/**
	 * 获取几天前yyyy-mm-dd
	 * @param day
	 * @return
	 */
	public static String daysBefore(int day) {
		Date currentDate = Calendar.getInstance().getTime();
		String time = df.format(getDateBefore(currentDate, day));
		return time;
	}

	/**
	 * 得到几天前的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

}
