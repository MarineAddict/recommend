package com.yihuisoft.product.validate.entity;


public enum ProductType {

		finance(1,"finance"),fund(2,"fund"),deposit(3,"deposit");
	
		private Integer index;
		private String title;
		private ProductType(Integer index ,String title) {
			this.index=index;
			this.title=title;
		    		}
		public int getIndex() {
			return index;
		}
		public void setIndex(Integer index) {
			this.index = index;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public static ProductType getPrdTypeByNumber(Integer id){
			ProductType[] arr=ProductType.values();
            for(ProductType t:arr){
            	 if (t.index==id){
            		 return t;
            	 }
            	 continue;
            }
            
            return null;
		}
			
	
}
