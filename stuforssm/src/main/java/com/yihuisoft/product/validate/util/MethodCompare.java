package com.yihuisoft.product.validate.util;

import com.yihuisoft.product.validate.exception.Exception;

public class MethodCompare {
	
	public static final String CORRECT="正确";
	public static final String INCORRECT="不正确";
	/**
	 * 完全匹配
	 * @param calculatedValue
	 * @param valueFromExcel
	 * @return
	 */
	public static String identical(String calculatedValue,String valueFromExcel){
		if(calculatedValue==null||valueFromExcel==null||"".equals(calculatedValue)||"".equals(valueFromExcel)) {
			return Exception.NullCompareValue;
		}
		if (string2Double(calculatedValue)-string2Double(valueFromExcel)==0){
			return CORRECT;
		}else{
			return INCORRECT;
		}
		
	}
	/**
	 * 允许一定误差
	 * @param errorRate
	 * @return
	 */
	public static String allowErrorRate(double errorRate,String calculatedValue,String valueFromExcel){
		if(calculatedValue==null||valueFromExcel==null||"".equals(calculatedValue)||"".equals(valueFromExcel)) {
			return Exception.NullCompareValue;
		}
		if (Math.abs(string2Double(calculatedValue)-string2Double(valueFromExcel))<=errorRate){
			return CORRECT;
		}else{
			return INCORRECT;
		}
	}
	
	
	
	private static double string2Double(String number){
		return Double.parseDouble(number);
	}
	

	public static void main(String[] args){
		
		String result=MethodCompare.identical(null, "0");
		System.out.println(result);
		
	}
}
