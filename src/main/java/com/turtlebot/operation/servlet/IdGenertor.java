package com.turtlebot.operation.servlet;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenertor {
	
	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String generateGUID() {
        return new BigInteger(165, new Random()).toString(36).toUpperCase();
    }
	
	public static String generateOrdersNum() {
		//YYYYMMDD+当前时间（纳秒）  ：   1秒=1000毫秒=1000*1000纳秒
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(now);
		return str+System.nanoTime();
	}
}
