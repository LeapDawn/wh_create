package com.fy.wetoband.tool.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//type+随机两位数+日期+随机四位数
public class IDGenerator {

	public static String getWhID() {
		// return new SimpleDateFormat("YYMMDDHHmmss").format(new Date());
		String date = new SimpleDateFormat("YYMMDD").format(new Date());
		return "WH" + (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ date + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
	}

	public static String getPoID() {
		// return new SimpleDateFormat("MMYYDDHHmmss").format(new Date());
		String date = new SimpleDateFormat("YYMMDD").format(new Date());
		return "PO" + (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ date + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
	}

	public static String getShId() {
		// return new SimpleDateFormat("MMDDYYHHmmss").format(new Date());
		String date = new SimpleDateFormat("YYMMDD").format(new Date());
		return "SH" + (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ date + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
	}

	public static String getCmID() {
		// return new SimpleDateFormat("HHMMDDYYmmss").format(new Date())
		// + (int)(Math.random() * 10) + (int)(Math.random() * 10)
		// + (int)(Math.random() * 10) + (int)(Math.random() * 10);
		String date = new SimpleDateFormat("YYMMDD").format(new Date());
		return "CM" + (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ date + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
	}
}
