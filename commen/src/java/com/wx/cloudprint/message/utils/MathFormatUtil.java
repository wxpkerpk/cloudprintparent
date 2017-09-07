package com.wx.cloudprint.message.utils;

import java.math.BigDecimal;

public class MathFormatUtil {
	
	public static double format(double f) {  
		return format(f,1);
    }
	
	public static double format(double f, int num) {  
        BigDecimal bg = new BigDecimal(f);  
        double f1 = bg.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue(); 
        return f1;
    }
	
	
	public static void main(String[] args) {
		double f = 111231.5585;  
		System.out.println(format(f));
	}
}
