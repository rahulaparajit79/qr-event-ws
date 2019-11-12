package com.event.qr.util;
import java.sql.Date;

public class DateUtil {
	public static Date javaToSql(java.util.Date jaavaDate) {
		if (jaavaDate == null) {
			return null;
		}
		System.out.println("Java Date" + jaavaDate);
		Date sqlDate = new Date(jaavaDate.getTime());
		System.out.println("SQL Date" + sqlDate);
		return sqlDate;

	}
}
