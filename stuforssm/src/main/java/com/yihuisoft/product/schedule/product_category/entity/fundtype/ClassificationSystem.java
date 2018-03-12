package com.yihuisoft.product.schedule.product_category.entity.fundtype;
/**
 * 分类体系
 * @author X&Q
 *
 */
public enum ClassificationSystem {
   工行体系("1","工行体系");
	
	private String index;
	private String sysName;
	private ClassificationSystem(String index,String sysName){
		this.index=index;
		this.sysName=sysName;
	}
	public String getIndex() {
		return index;
	}
	public String getSysName() {
		return sysName;
	}
	
}
