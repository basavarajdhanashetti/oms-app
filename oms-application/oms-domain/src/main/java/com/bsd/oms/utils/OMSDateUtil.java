package com.bsd.oms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OMSDateUtil {

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date toDate(String strDate) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
		} catch (ParseException e) {
			
		}
		return new Date();
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Object getDBDateTime(Date time) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}

}
