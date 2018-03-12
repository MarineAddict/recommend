package com.yihuisoft.product.fund.entity.dao;

public class FundBasicDAO {
	private int ID;//	VARCHAR2(20 BYTE) 实体id
	private String CODE;//	VARCHAR2(20 BYTE) 基金代码
	private int CALCULATE_COVARIANCE;//	NUMBER(1,0)	 是否计算协方差（0不计算，1计算）
	private String NAME;//	VARCHAR2(20 BYTE) 基金名称
	private String IPO_START_DATE;//	DATE 募集开始日期
	private String IPO_END_DATE;//	DATE 募集结束日期
	private String ESTAB_DATE;//	DATE 产品成立日期
	private int STATUS;//	VARCHAR2(1 BYTE) 基金状态
	private String type;//基金类型
	
	private String sname;//	VARCHAR2(20 BYTE) 基金小类名称
	private int scode;//	VARCHAR2(20 BYTE) 基金小类代码
	private int invtypone;//投资类型一（基金类型）
	private String bidcode;//指数标的
	private int cxfundtyp;//晨星基金类型
	private String risklevel;//基金风险类型
	
	
	
	private double growth;//涨幅
	private double expected_annualized_return;//预期收益率
	private double expected_risk_ratio;//预期风险率
	private double Maxdrawdown;//最大回撤
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getGrowth() {
		return growth;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public double getExpected_annualized_return() {
		return expected_annualized_return;
	}
	public void setExpected_annualized_return(double expected_annualized_return) {
		this.expected_annualized_return = expected_annualized_return;
	}
	public double getExpected_risk_ratio() {
		return expected_risk_ratio;
	}
	public void setExpected_risk_ratio(double expected_risk_ratio) {
		this.expected_risk_ratio = expected_risk_ratio;
	}
	public double getMaxdrawdown() {
		return Maxdrawdown;
	}
	public void setMaxdrawdown(double maxdrawdown) {
		Maxdrawdown = maxdrawdown;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public int getCALCULATE_COVARIANCE() {
		return CALCULATE_COVARIANCE;
	}
	public void setCALCULATE_COVARIANCE(int cALCULATE_COVARIANCE) {
		CALCULATE_COVARIANCE = cALCULATE_COVARIANCE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getIPO_START_DATE() {
		return IPO_START_DATE;
	}
	public void setIPO_START_DATE(String iPO_START_DATE) {
		IPO_START_DATE = iPO_START_DATE;
	}
	public String getIPO_END_DATE() {
		return IPO_END_DATE;
	}
	public void setIPO_END_DATE(String iPO_END_DATE) {
		IPO_END_DATE = iPO_END_DATE;
	}
	public String getESTAB_DATE() {
		return ESTAB_DATE;
	}
	public void setESTAB_DATE(String eSTAB_DATE) {
		ESTAB_DATE = eSTAB_DATE;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public int getCxfundtyp() {
		return cxfundtyp;
	}
	public void setCxfundtyp(int cxfundtyp) {
		this.cxfundtyp = cxfundtyp;
	}
	public int getInvtypone() {
		return invtypone;
	}
	public void setInvtypone(int invtypone) {
		this.invtypone = invtypone;
	}
	public String getBidcode() {
		return bidcode;
	}
	public void setBidcode(String bidcode) {
		this.bidcode = bidcode;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getScode() {
		return scode;
	}
	public void setScode(int scode) {
		this.scode = scode;
	}
	public String getRisklevel() {
		return risklevel;
	}
	public void setRisklevel(String risklevel) {
		this.risklevel = risklevel;
	}
	
	
	
}
