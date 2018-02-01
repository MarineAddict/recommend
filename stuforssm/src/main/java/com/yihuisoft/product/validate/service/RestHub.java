package com.yihuisoft.product.validate.service;

public interface RestHub {
   
	/**
	 * 发起验证请求对应的url（带参数）
	 * @param url 请求路径
	 * @return
	 */
	String startARestRequest(String url);
	
	
	
}
