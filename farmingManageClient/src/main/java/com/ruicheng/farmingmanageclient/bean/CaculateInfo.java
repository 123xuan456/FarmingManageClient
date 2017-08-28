package com.ruicheng.farmingmanageclient.bean;
/**
 * 计算数据类
 *
 * @author zhaobeibei
 *
 */
public class CaculateInfo {

	private String goodPricem ;  //单价
	private String purchasingWeight ;  //总重
	private String receivePackagesm ;  // 收货件数
	private String packageWeight ;  //件重
	public String getGoodPricem() {
		return goodPricem;
	}
	public void setGoodPricem(String goodPricem) {
		this.goodPricem = goodPricem;
	}
	public String getPurchasingWeight() {
		return purchasingWeight;
	}
	public void setPurchasingWeight(String purchasingWeight) {
		this.purchasingWeight = purchasingWeight;
	}
	public String getReceivePackagesm() {
		return receivePackagesm;
	}
	public void setReceivePackagesm(String receivePackagesm) {
		this.receivePackagesm = receivePackagesm;
	}
	public String getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}
}
