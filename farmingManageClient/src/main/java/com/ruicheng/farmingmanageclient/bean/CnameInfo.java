package com.ruicheng.farmingmanageclient.bean;

import java.io.Serializable;
/**
 * 供应商信息
 *
 * @author zhaobeibei
 *
 */
public class CnameInfo implements Serializable{

	private String id;
	private String cname;
	private String nameSpell;
	private String phone;
	private String type;
	private String dcId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getNameSpell() {
		return nameSpell;
	}
	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
}
