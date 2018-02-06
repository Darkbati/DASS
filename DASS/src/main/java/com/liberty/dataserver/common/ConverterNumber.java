package com.liberty.dataserver.common;

import org.apache.commons.lang3.StringUtils;

public class ConverterNumber {
	public static Integer parseInt(String value, String defaultValue) {
		try {
			return Integer.parseInt(StringUtils.isEmpty(value) == true ? "0" : value);
		} catch (NumberFormatException e) {
			return Integer.parseInt(defaultValue);
		}
	}

	public static Long parseLong(String value, String defaultValue) {
		try {
			return Long.parseLong(StringUtils.isEmpty(value) == true ? "0" : value);
		} catch (NumberFormatException e) {
			return Long.parseLong(defaultValue);
		}
	}
}
