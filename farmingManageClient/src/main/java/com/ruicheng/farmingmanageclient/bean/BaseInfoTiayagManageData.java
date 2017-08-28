package com.ruicheng.farmingmanageclient.bean;

public class BaseInfoTiayagManageData {

	private String fieldNumber ;// 田地编号

	private String belongsToServiceStation ;// 所属服务站

	private String plantingOfCrops ;// 种植作物
	private String fieldArea ;// 田地面积
	public String getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(String fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	public String getBelongsToServiceStation() {
		return belongsToServiceStation;
	}
	public void setBelongsToServiceStation(String belongsToServiceStation) {
		this.belongsToServiceStation = belongsToServiceStation;
	}
	public String getPlantingOfCrops() {
		return plantingOfCrops;
	}
	public void setPlantingOfCrops(String plantingOfCrops) {
		this.plantingOfCrops = plantingOfCrops;
	}
	public String getFieldArea() {
		return fieldArea;
	}
	public void setFieldArea(String fieldArea) {
		this.fieldArea = fieldArea;
	}

}
