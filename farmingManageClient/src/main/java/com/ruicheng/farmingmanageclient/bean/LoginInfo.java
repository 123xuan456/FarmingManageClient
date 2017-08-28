package com.ruicheng.farmingmanageclient.bean;

public class LoginInfo {

	private String userId;
	private String userLoginName;
	private String userCreateName;
	private String dcId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getUserCreateName() {
		return userCreateName;
	}
	public void setUserCreateName(String userCreateName) {
		this.userCreateName = userCreateName;
	}
	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
}
