package com.fdmgroup.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
	
	/**
	 * Compares dates on a day/month/year level. Workaround for sql date type conversion which counts nanoseconds,
	 * making java.util.Date comparison inaccurate (only counts milliseconds)
	 * @param date1
	 * @param date2
	 * @return True if both dates are the same day, month and year.
	 */
	public static Boolean isSameDay(Date date1, Date date2) {
		return formatDate(date1).equalsIgnoreCase(formatDate(date2));
	}
	
	/**
	 * Returns the String value of a date formatted dd-MMM-yy
	 * @param date
	 */
	public static String formatDate(Date date) {
		return formatter.format(date);
	}
	
	/**
	 * Creates a date instance
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static Date createDate(Integer day, Integer month, Integer year) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}
	
}
