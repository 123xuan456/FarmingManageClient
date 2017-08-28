package com.ruicheng.farmingmanageclient.bean;

public class CostEquationData {

	private String farmProductsName ; //农产品名称
	private String equationType ;  //公式类型
	private String isValid ;  //是否有效
	public String getFarmProductsName() {
		return farmProductsName;
	}
	public void setFarmProductsName(String farmProductsName) {
		this.farmProductsName = farmProductsName;
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
