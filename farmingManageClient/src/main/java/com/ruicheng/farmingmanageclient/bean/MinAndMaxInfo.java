package com.ruicheng.farmingmanageclient.bean;

import android.widget.EditText;

public class MinAndMaxInfo {

	private EditText minValue;
	private EditText maxValue;
	private String cid ;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public EditText getMinValue() {
		return minValue;
	}
	public void setMinValue(EditText minValue) {
		this.minValue = minValue;
	}
	public EditText getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(EditText maxValue) {
		this.maxValue = maxValue;
	}

}
