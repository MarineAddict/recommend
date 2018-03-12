package com.yihuisoft.product.schedule.product_category.entity.fundtype;

/**
 * 晨星基金类型
 * @author X&Q
 *
 */
public enum CXFundType {
	灵活配置型基金(1,"灵活配置型基金"),
	纯债型基金(2,"纯债型基金"),
	股票型基金(3,"股票型基金"),
	激进配置型基金(4,"激进配置型基金"),
	货币市场基金(5,"货币市场基金"),
	激进债券型基金(6,"激进债券型基金"),
	普通债券型基金(7,"普通债券型基金"),
	保守混合型基金(8,"保守混合型基金"),
	其它基金(9,"其它基金"),
	QDII基金(10,"QDII基金"),
	保本基金(11,"保本基金"),
	短债基金(12,"短债基金"),
	标准混合型基金(13,"标准混合型基金"),
	沪港深混合型基金(14,"沪港深混合型基金"),
	行业股票_医药(15,"行业股票-医药"),
	行业股票_科技传媒及通讯(16,"行业股票-科技、传媒及通讯"),
	可转债基金(17,"可转债基金"),
	沪港深股票型基金(18,"沪港深股票型基金"),
	市场中性策略(19,"市场中性策略"),
	商品型基金(20,"商品型基金"),
	大中华区股债混合(21,"大中华区股债混合(QDII)"),
	其它(22,"其它"),
	亚洲股债混合(23,"亚洲股债混合(QDII)"),
	全球新兴市场股债混合(24,"全球新兴市场股债混合(QDII)"),
	保守配置型基金(25,"保守配置型基金");
	
	private Integer index;
	private String typeName;
	private CXFundType(Integer index,String typeName){
		this.index=index;
		this.typeName=typeName;
			}
	public Integer getIndex() {
		return index;
	}
	public String getTypeName() {
		return typeName;
	}

}
