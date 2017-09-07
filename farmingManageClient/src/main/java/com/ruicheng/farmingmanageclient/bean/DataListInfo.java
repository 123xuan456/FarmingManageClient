package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

public class DataListInfo implements Serializable{

	//播种移栽界面列表数据
	private String detailId ;
	private String ploughId ;
	//private String ploughCode ;
	private String productType ;
	private String recordDate ;
	private String cropType ;
	private String cropCode ;
	private String cropPtype ;
	private String seedDate ;
	private String moveDate ;
	private String temperValue ;
	private String weatherState ;
	private String productItem ;
	private String agrDesc ;
	private String cropState ;
	private String receiveDate ;
	private String actionPerson;
	private String actionBak;
	//田洋收成界面列表数据
	private String recordId;
	private String productAddr;
	private String diluteScale;
	private String cropLevel;
	private String productionRecord;
	private String pesticideAmountUnit;
	private String manureName;
	private String manureAmountUnit;
	private String cropDirection;
	private String receiveWeight;
	private String checkState;
	private String tecName;
	private String ownerName;
	private String manureUse;
	private String checkCode;
	private String receiveCode;
	private String preventObj;
	private String receivePackages;
	private String pesticideName;
	private String delFlag;
	private String phasesId;
	private String ploughCode;


	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	private String sysId;

	//田洋农事界面数据
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getPlou() {
		return ploughId;
	}
	public void setPloughghIdId(String ploughId) {
		this.ploughId = ploughId;
	}
//	public String getPloughCode() {
//		return ploughCode;
//	}
//	public void setPloughCode(String ploughCode) {
//		this.ploughCode = ploughCode;
//	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public String getCropCode() {
		return cropCode;
	}
	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}
	public String getCropPtype() {
		return cropPtype;
	}
	public void setCropPtype(String cropPtype) {
		this.cropPtype = cropPtype;
	}
	public String getSeedDate() {
		return seedDate;
	}
	public void setSeedDate(String seedDate) {
		this.seedDate = seedDate;
	}
	public String getMoveDate() {
		return moveDate;
	}
	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}
	public String getTemperValue() {
		return temperValue;
	}
	public void setTemperValue(String temperValue) {
		this.temperValue = temperValue;
	}
	public String getWeatherState() {
		return weatherState;
	}
	public void setWeatherState(String weatherState) {
		this.weatherState = weatherState;
	}
	public String getProductItem() {
		return productItem;
	}
	public void setProductItem(String productItem) {
		this.productItem = productItem;
	}
	public String getAgrDesc() {
		return agrDesc;
	}
	public void setAgrDesc(String agrDesc) {
		this.agrDesc = agrDesc;
	}
	public String getCropState() {
		return cropState;
	}
	public void setCropState(String cropState) {
		this.cropState = cropState;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getActionPerson() {
		return actionPerson;
	}
	public void setActionPerson(String actionPerson) {
		this.actionPerson = actionPerson;
	}
	public String getActionBak() {
		return actionBak;
	}
	public void setActionBak(String actionBak) {
		this.actionBak = actionBak;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getProductAddr() {
		return productAddr;
	}
	public void setProductAddr(String productAddr) {
		this.productAddr = productAddr;
	}
	public String getDiluteScale() {
		return diluteScale;
	}
	public void setDiluteScale(String diluteScale) {
		this.diluteScale = diluteScale;
	}
	public String getCropLevel() {
		return cropLevel;
	}
	public void setCropLevel(String cropLevel) {
		this.cropLevel = cropLevel;
	}
	public String getProductionRecord() {
		return productionRecord;
	}
	public void setProductionRecord(String productionRecord) {
		this.productionRecord = productionRecord;
	}
	public String getPesticideAmountUnit() {
		return pesticideAmountUnit;
	}
	public void setPesticideAmountUnit(String pesticideAmountUnit) {
		this.pesticideAmountUnit = pesticideAmountUnit;
	}
	public String getManureName() {
		return manureName;
	}
	public void setManureName(String manureName) {
		this.manureName = manureName;
	}
	public String getManureAmountUnit() {
		return manureAmountUnit;
	}
	public void setManureAmountUnit(String manureAmountUnit) {
		this.manureAmountUnit = manureAmountUnit;
	}
	public String getCropDirection() {
		return cropDirection;
	}
	public void setCropDirection(String cropDirection) {
		this.cropDirection = cropDirection;
	}
	public String getReceiveWeight() {
		return receiveWeight;
	}
	public void setReceiveWeight(String receiveWeight) {
		this.receiveWeight = receiveWeight;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public String getTecName() {
		return tecName;
	}
	public void setTecName(String tecName) {
		this.tecName = tecName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getManureUse() {
		return manureUse;
	}
	public void setManureUse(String manureUse) {
		this.manureUse = manureUse;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getReceiveCode() {
		return receiveCode;
	}
	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}
	public String getPreventObj() {
		return preventObj;
	}
	public void setPreventObj(String preventObj) {
		this.preventObj = preventObj;
	}
	public String getReceivePackages() {
		return receivePackages;
	}
	public void setReceivePackages(String receivePackages) {
		this.receivePackages = receivePackages;
	}
	public String getPesticideName() {
		return pesticideName;
	}
	public void setPesticideName(String pesticideName) {
		this.pesticideName = pesticideName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getPhasesId() {
		return phasesId;
	}
	public void setPhasesId(String phasesId) {
		this.phasesId = phasesId;
	}

	public String getPloughCode() {
		return ploughCode;
	}
	public void setPloughCode(String ploughCode) {
		this.ploughCode = ploughCode;
	}
}
