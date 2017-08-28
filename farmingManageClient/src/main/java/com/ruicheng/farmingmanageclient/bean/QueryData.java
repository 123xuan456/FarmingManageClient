package com.ruicheng.farmingmanageclient.bean;

/**
 * 封装查询数据的实体类
 *
 * @author zhaobeibei
 *
 */
public class QueryData {

	private String operate ;
	private String operateDate ;
	private String type ;
	private String describtion ;
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}


}
