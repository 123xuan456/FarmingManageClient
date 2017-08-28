package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;
/**
 * 基础信息模块---田洋信息获取
 *
 * @author zhaobeibei
 *
 */
public class PloughListInfoArrayInfo implements Serializable{

	private String ploughId;
	private String ploughCode;
	private String ploughArea;
	private String station;
	private String plantCrop; // 种植作物
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
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getPlantCrop() {
		return plantCrop;
	}
	public void setPlantCrop(String plantCrop) {
		this.plantCrop = plantCrop;
	}

}
