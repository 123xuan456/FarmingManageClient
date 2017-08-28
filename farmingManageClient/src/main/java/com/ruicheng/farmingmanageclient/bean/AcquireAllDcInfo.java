package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;
/**
 * 区域列表实体类
 *
 * @author zhaobeibei
 *
 */
public class AcquireAllDcInfo implements Serializable{

	private String dcId ;
	private String dcName ;
	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}



}
