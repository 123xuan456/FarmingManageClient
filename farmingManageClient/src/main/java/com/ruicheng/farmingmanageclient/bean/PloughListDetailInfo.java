package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;

/**
 * 田洋明细信息
 *
 * @author zhaobeibei
 *
 */
public class PloughListDetailInfo implements Serializable{

	private String ploughId ;//	田地id
	private String ploughCode ;//	田地编号
	private String ploughArea ;//田地面积(亩)
	private String soilState ;//土壤情况
	private String stationId ;//服务站id
	private String dicCode ;//作物编号
	private String dicValue ;//作物名称
	private String cropPtype ;//作物品种
	private String cropLevel ; //货品等级
	private String harvestNum ; //收获数量
	private String dicId ;

	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public String getPloughId() {
		return ploughId;
	}
	public void setPloughId(String ploughId) {
		this.ploughId = ploughId;
	}
	public String getPloughCode() {
		return ploughCode;
	}
	public void setPloughCode(String ploughCode) {
		this.ploughCode = ploughCode;
	}
	public String getPloughArea() {
		return ploughArea;
	}
	public void setPloughArea(String ploughArea) {
		this.ploughArea = ploughArea;
	}
	public String getSoilState() {
		return soilState;
	}
	public void setSoilState(String soilState) {
		this.soilState = soilState;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	public String getCropPtype() {
		return cropPtype;
	}
	public void setCropPtype(String cropPtype) {
		this.cropPtype = cropPtype;
	}
	public String getCropLevel() {
		return cropLevel;
	}
	public void setCropLevel(String cropLevel) {
		this.cropLevel = cropLevel;
	}
	public String getHarvestNum() {
		return harvestNum;
	}
	public void setHarvestNum(String harvestNum) {
		this.harvestNum = harvestNum;
	}

}
