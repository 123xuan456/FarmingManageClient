package com.ruicheng.farmingmanageclient.bean;
/**
 * 获取生产履历统计的列表
 *
 * @author zhaobeibei
 *
 */
public class StatisticsCropInfo {

	private String acode ;	//作物编号
	private String crop_type;//作物名称
	private String plougharea;	//种植面积
	private String Receiveweight;	//收获量
	public String getAcode() {
		return acode;
	}
	public void setAcode(String acode) {
		this.acode = acode;
	}
	public String getCrop_type() {
		return crop_type;
	}
	public void setCrop_type(String crop_type) {
		this.crop_type = crop_type;
	}
	public String getPlougharea() {
		return plougharea;
	}
	public void setPlougharea(String plougharea) {
		this.plougharea = plougharea;
	}
	public String getReceiveweight() {
		return Receiveweight;
	}
	public void setReceiveweight(String receiveweight) {
		Receiveweight = receiveweight;
	}

}
