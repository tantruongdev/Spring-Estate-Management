package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNumber(String value) {
//		C1: Bieu thuc chinh quyy 
//		 return str.matches("-?\\d+(\\.\\d+)?");
//		C2: try -catch
		try {
			 Double.parseDouble(value);
			 return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
}
