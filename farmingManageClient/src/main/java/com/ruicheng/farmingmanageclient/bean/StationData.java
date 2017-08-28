package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

public class StationData implements Serializable{
	
	private String dcId ;
	private String dcName ;
	private String stationId ;
	
	private String stationCode ;
	private String stationName ;
	private String receiveDate ;
	private String productType ;
	private String cropType;
	private String recordDate;
	private String moveDate;
	private String actionPerson;
	private String seedDate;
	private String actionBak ;
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
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getMoveDate() {
		return moveDate;
	}
	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}
	public String getActionPerson() {
		return actionPerson;
	}
	public void setActionPerson(String actionPerson) {
		this.actionPerson = actionPerson;
	}
	public String getSeedDate() {
		return seedDate;
	}
	public void setSeedDate(String seedDate) {
		this.seedDate = seedDate;
	}
	public String getActionBak() {
		return actionBak;
	}
	public void setActionBak(String actionBak) {
		this.actionBak = actionBak;
	}

}
