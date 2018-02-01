package com.yihuisoft.product.validate.entity;


public enum Indicators {
		netValue(1,"netValue_url"),yieldRate(2,"yieldRate_url"),
		expYieldRate(3,"expYieldRate_url"),expRiskRate(4,"expRiskRate_url"),yieldRatioLine(5,"yieldRatioLine_url");
	
		private Integer index; //
		private String requestMappingUrlTitle;
		private Indicators(Integer index ,String title) {
			this.index=index;
			this.requestMappingUrlTitle=title;
		    		}
		public int getIndex() {
			return index;
		}
		public void setIndex(Integer index) {
			this.index = index;
		}
		public String getTitle() {
			return requestMappingUrlTitle;
		}
		public void setTitle(String title) {
			this.requestMappingUrlTitle = title;
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
