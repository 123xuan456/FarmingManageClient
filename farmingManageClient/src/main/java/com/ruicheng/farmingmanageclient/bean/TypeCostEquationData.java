package com.ruicheng.farmingmanageclient.bean;

public class TypeCostEquationData {

	private String cropName ; // 作物名称
	private String equationType ;  //公式类型
	private String isValid ;  //是否有效
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getEquationType() {
		return equationType;
	}
	public void setEquationType(String equationType) {
		this.equationType = equationType;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}



}
