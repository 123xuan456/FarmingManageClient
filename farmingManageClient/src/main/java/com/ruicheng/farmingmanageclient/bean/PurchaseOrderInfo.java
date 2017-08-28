package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

/**
 * 采购单号信息
 *
 * @author zhaobeibei
 *
 */
public class PurchaseOrderInfo implements Serializable {

	private String purId;
	private String printCode;
	private String providerName;
	private String providerId;

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

	public String getPurId() {
		return purId;
	}

	public void setPurId(String purId) {
		this.purId = purId;
	}

	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}
}
