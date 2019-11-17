package com.event.qr.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static Date javaToSql(java.util.Date jaavaDate) {
		if (jaavaDate == null) {
			return null;
		}
		Date sqlDate = new Date(jaavaDate.getTime());
		return sqlDate;

	}

	public static String javaToDateTimeString(Date date) {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String currentDateTimeString() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	}

	public static java.util.Date sqlDateTimeToJavaDate(String sqldatetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(sqldatetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String javaDateToSqlDateTimeString(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

}
