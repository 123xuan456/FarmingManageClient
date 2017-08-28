package com.ruicheng.farmingmanageclient.bean;

public class ProReStatisticsData {

	private String cropName ;     //农作物名称
	private String cultivatedArea ;//种植面积
	private String harvestYield ;  //收获量
	private String totalCultivatedArea ; // 总的种植面积
	private String totalHarvestYield ;   // 总的收获量
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getCultivatedArea() {
		return cultivatedArea;
	}
	public void setCultivatedArea(String cultivatedArea) {
		this.cultivatedArea = cultivatedArea;
	}
	public String getHarvestYield() {
		return harvestYield;
	}
	public void setHarvestYield(String harvestYield) {
		this.harvestYield = harvestYield;
	}
	public String getTotalCultivatedArea() {
		return totalCultivatedArea;
	}
	public void setTotalCultivatedArea(String totalCultivatedArea) {
		this.totalCultivatedArea = totalCultivatedArea;
	}
	public String getTotalHarvestYield() {
		return totalHarvestYield;
	}
	public void setTotalHarvestYield(String totalHarvestYield) {
		this.totalHarvestYield = totalHarvestYield;
	}



}
