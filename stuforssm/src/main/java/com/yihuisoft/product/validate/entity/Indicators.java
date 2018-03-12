package com.yihuisoft.product.validate.entity;


public enum Indicators {
		netValue(1,"netValue_url","navaDj"),yieldRate(2,"yieldRate_url","yieldRatio"),
		expYieldRate(3,"expYieldRate_url",null),expRiskRate(4,"expRiskRate_url",null),yieldRatioLine(5,"yieldRatioLine_url",null),
		fundRiskRate(6,"riskRate_url","riskRatio"),depositeInterest(7,"income_url",null),depositeRisk(8,"riskRate_url",null);
	
		private Integer index; // 标识id
		private String requestMappingUrlTitle; //拼接url的时候的url表示符号
		private String returnedTitle;//返回的数据中对应的字段名
		private Indicators(Integer index ,String title,String returnedTitle) {
			this.index=index;
			this.requestMappingUrlTitle=title;
			this.returnedTitle=returnedTitle;
		    		}
	
		
		
		
		
		public Integer getIndex() {
			return index;
		}





		public void setIndex(Integer index) {
			this.index = index;
		}





		public String getRequestMappingUrlTitle() {
			return requestMappingUrlTitle;
		}





		public void setRequestMappingUrlTitle(String requestMappingUrlTitle) {
			this.requestMappingUrlTitle = requestMappingUrlTitle;
		}





		public String getReturnedTitle() {
			return returnedTitle;
		}





		public void setReturnedTitle(String returnedTitle) {
			this.returnedTitle = returnedTitle;
		}





		public static Indicators getIndicatorByNumber(Integer id){
			Indicators[] arr=Indicators.values();
            for(Indicators t:arr){
            	 if (t.index==id){
            		 return t;
            	 }
            	 continue;
            }
            return null;
		}
		
	
	
}
