package com.incture.workbox.scpadapter.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class ServicesUtil {
	
	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Map<?, ?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuffer sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuilder sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static Date getFromStringToDate(String fromString) {
		Date date = null;
		try {

			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(fromString);

		} catch (Exception e) {
			System.err.println("[Workbox][SCPAdapter][ServicesUtil][getFromStringToDate][Exception] : " + e.getMessage());
		}
		return date;
	}
	
	public static String getBasicAuth(String userName, String password) {
		String userpass = userName + ":" + password;
		return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
	}
	
	public static String getAuthorization(String accessToken, String tokenType) {
		return tokenType + " " + accessToken;
	}
}
