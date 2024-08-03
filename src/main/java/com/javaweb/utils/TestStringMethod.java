package com.javaweb.utils;

public class TestStringMethod {
	public static void main(String[] args) {
		try {
			Object x = null;
			String ts = (String)x;
			System.out.print(ts);
//			String st = x.toString();
//			System.out.print(st);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
