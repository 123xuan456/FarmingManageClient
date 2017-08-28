package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

public class ChargeListInfo implements Serializable{

	private String cuName;
	private String cuCode;
	private String delFlag;
	private String minValue;
	private String maxValue;
	private String costName;
	
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getCuName() {
		return cuName;
	}
	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	public String getCuCode() {
		return cuCode;
	}
	public void setCuCode(String cuCode) {
		this.cuCode = cuCode;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
