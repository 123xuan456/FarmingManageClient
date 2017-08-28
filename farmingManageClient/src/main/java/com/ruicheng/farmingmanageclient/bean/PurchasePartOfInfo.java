package com.ruicheng.farmingmanageclient.bean;
/**
 * 采购单对应的部分信息
 *
 * @author zhaobeibei
 *
 */
public class PurchasePartOfInfo {
	private String purId ; //采购单主表Id
	private String purInfoId	;//详细信息id(采购子表主键)
	private String goodId	;//商品Id
	private String goodName	;//商品名称
	private String goodNumber;//采购重量（商品数量）
	private String goodUnit		;//商品单位
	private String packageWeight;//件重
	private String totalGoodWeight	;//商品总重量
	private String goodPrice	;//商品价格
	private String receivePackagesm	;//收货件数
	private String goodMoney	;// 
	public String getGoodMoney() {
		return goodMoney;
	}
	public void setGoodMoney(String goodMoney) {
		this.goodMoney = goodMoney;
	}
	public String getPurId() {
		return purId;
	}
	public void setPurId(String purId) {
		this.purId = purId;
	}
	public String getPurInfoId() {
		return purInfoId;
	}
	public void setPurInfoId(String purInfoId) {
		this.purInfoId = purInfoId;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodNumber() {
		return goodNumber;
	}
	public void setGoodNumber(String goodNumber) {
		this.goodNumber = goodNumber;
	}
	public String getGoodUnit() {
		return goodUnit;
	}
	public void setGoodUnit(String goodUnit) {
		this.goodUnit = goodUnit;
	}
	public String getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}
	public String getTotalGoodWeight() {
		return totalGoodWeight;
	}
	public void setTotalGoodWeight(String totalGoodWeight) {
		this.totalGoodWeight = totalGoodWeight;
	}
	public String getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getReceivePackagesm() {
		return receivePackagesm;
	}
	public void setReceivePackagesm(String receivePackagesm) {
		this.receivePackagesm = receivePackagesm;
	}
}
