package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

public class CustomCostListInfo implements Serializable{

	private String customId ;
	private String planInfoId ;
	private String formType ;
	private String formId ;
	private String minValue ;
	private String cuCode ;
	private String isValid ;
	private String costName ;
	private String maxValue ;
	
	private String chargesCollectable ;
	private String unit ;
	private String cid ;
	private String cuName ;
	private String planAmou ;
	
	
	
	public String getPlanAmou() {
		return planAmou;
	}
	public void setPlanAmou(String planAmou) {
		this.planAmou = planAmou;
	}
	public String getCuName() {
		return cuName;
	}
	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getPlanInfoId() {
		return planInfoId;
	}
	public void setPlanInfoId(String planInfoId) {
		this.planInfoId = planInfoId;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getCuCode() {
		return cuCode;
	}
	public void setCuCode(String cuCode) {
		this.cuCode = cuCode;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getChargesCollectable() {
		return chargesCollectable;
	}
	public void setChargesCollectable(String chargesCollectable) {
		this.chargesCollectable = chargesCollectable;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
}
