package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 采购单号详细信息
 *
 * @author zhaobeibei
 *
 */
public class PurchaseInfo implements Serializable{

	private List<PurchasePartOfInfo> urchasePartOfInfoList ;
	private String providerName ;
	private String providerId ;
	public List<PurchasePartOfInfo> getUrchasePartOfInfoList() {
		return urchasePartOfInfoList;
	}
	public void setUrchasePartOfInfoList(
			List<PurchasePartOfInfo> urchasePartOfInfoList) {
		this.urchasePartOfInfoList = urchasePartOfInfoList;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}
