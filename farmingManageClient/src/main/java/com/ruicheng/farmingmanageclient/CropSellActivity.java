package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CnameInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 作物交售---添加收货信息界面
 *
 * @author zhaobeibei
 *
 */
public class CropSellActivity extends BaseActivity implements OnClickListener {

	private ImageView img_comment_back;
	private Button btn_query;
	private TextView tv_pro_add;
	private TextView tv_operatestarttime, tv_operateendtime;
	private TextView tv_adminer, tv_dcName;
	private TextView et_cname;
	private final int CNAME = 0;
	private CnameInfo cnameInfo;
	private Dialog loadingDialog;
	private GoodsReceiveInfo goodsReceiveInfo;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_cropsell);
		init();
		setListener();
//		 getqueryGoodsReceive(false);
	}

	@Override
	public void init() {
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/* openOrCloseKeybd(false); */
						img_comment_back.setVisibility(View.GONE);
						finish();
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});
		btn_query = (Button) findViewById(R.id.btn_query);
		tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);

		tv_operatestarttime = (TextView) findViewById(R.id.tv_operatestarttime);
		tv_operateendtime = (TextView) findViewById(R.id.tv_operateendtime);
		tv_adminer = (TextView) findViewById(R.id.tv_adminer);
		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		tv_dcName.setText(PreferencesUtils.getString(getApplicationContext(),
				Constant.DCNAME));
		et_cname = (TextView) findViewById(R.id.et_cname);
		loadingDialog = DialogUtils.requestDialog(this);
	}

	@Override
	public void setListener() {
		NormalClickListener normalClickListener = new NormalClickListener();
		tv_operatestarttime.setOnClickListener(normalClickListener);
		tv_operateendtime.setOnClickListener(normalClickListener);
		et_cname.setOnClickListener(normalClickListener);

		btn_query.setOnClickListener(this);
		tv_pro_add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_query:
				getqueryGoodsReceive(true);
				break;
			case R.id.tv_pro_add:
				Intent i = new Intent();
				i.setClass(CropSellActivity.this, AddDetailCropAc.class);
				startActivity(i);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	public class NormalClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_operatestarttime:
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(tv_adminer.getWindowToken(), 0);
					new SelectDateTimePopWin(CropSellActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (tv_operatestarttime.getText().toString() == null) {
								ToastUtils.show(getApplicationContext(),
										"请输入经办开始时间");
								return;
							}
//						if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
//							tv_operatestarttime.setText(date);
//						}
							tv_operatestarttime.setText(date);
//						else {
//							ToastUtils.show(getApplicationContext(), "经办开始时间不能小于当前时间");
//						}
						}
					};
					break;
				case R.id.tv_operateendtime:
					InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					immm.hideSoftInputFromWindow(tv_adminer.getWindowToken(), 0);
					new SelectDateTimePopWin(CropSellActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (tv_operateendtime.getText().toString() == null) {
								ToastUtils.show(getApplicationContext(),
										"请输入经办结束时间");
								return;
							}
							if (tv_operatestarttime.getText().toString()==null||tv_operatestarttime.getText().toString().equals("")) {
								ToastUtils.show(getApplicationContext(), "请先输入开始时间");
								return ;
							}
							if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(tv_operatestarttime.getText().toString()).getTime()>0) {
								tv_operateendtime.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
							};
						}
					};
					break;
				case R.id.et_cname:
					Intent cnameIntent = new Intent();
					cnameIntent.setClass(getApplicationContext(), CnameAc.class);
					cnameIntent.putExtra("fromwhichview", 0);
					startActivityForResult(cnameIntent, CNAME);
					break;
				default:
					break;
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case CNAME:
					cnameInfo = (CnameInfo) data.getSerializableExtra("cname");
					et_cname.setText(cnameInfo.getCname());
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 收货信息管理数据获取
	 *
	 * @param isQuery
	 *            true 查询 false 获取全部数据
	 */
	public void getqueryGoodsReceive(boolean isQuery) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
			params.put("pager.num", Constant.NUM);
			params.put("pager.size", Constant.SIZE);
			if (isQuery == true) {
				/*
				 * if (!estimateInfoIsNullUtils()) { return; }
				 */
				if (cnameInfo!=null) {
					params.put("goodsReceiveVo.areaId", PreferencesUtils.getString(
							getApplicationContext(), Constant.DCID));
					if (!"".equals(cnameInfo.getCname())&&!"null".equals(cnameInfo.getCname())) {
						params.put("goodsReceiveVo.vendorName", cnameInfo.getCname());

					} else {
						params.put("goodsReceiveVo.vendorName","");
					}
					if (!"".equals(cnameInfo.getId())&&!"null".equals(cnameInfo.getId())) {

						params.put("goodsReceiveVo.vendorId", cnameInfo.getId());
					} else {
						params.put("goodsReceiveVo.vendorId","");

					}
					if (!"".equals(cnameInfo.getType())&&!"null".equals(cnameInfo.getType())) {

						params.put("goodsReceiveVo.vendorType", cnameInfo.getType());
					} else {
						params.put("goodsReceiveVo.vendorType", "");
					}
					if (!"".equals(tv_adminer.getText().toString())) {
						params.put("goodsReceiveVo.userName", tv_adminer.getText().toString());

						params.put("goodsReceiveVo.userId", PreferencesUtils.getInt(
								getApplicationContext(), Constant.USERID));
					} else {
						params.put("goodsReceiveVo.userName", "");

						params.put("goodsReceiveVo.userId","");
					}

					if (!"".equals(tv_operatestarttime
							.getText().toString())) {
						params.put("goodsReceiveVo.startTime", tv_operatestarttime
								.getText().toString() + " " + DateUtils.getTimeShort());

					} else {
						params.put("goodsReceiveVo.startTime","");

					}
					if (!"".equals(tv_operateendtime
							.getText().toString())) {
						params.put("goodsReceiveVo.endTime", tv_operateendtime
								.getText().toString() + " " + DateUtils.getTimeShort());
					} else {
						params.put("goodsReceiveVo.endTime","");
					}
				}
			}

			TwitterRestClient.get(Constant.QUERYGOODSRECEIVE, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							try {
								if ("success".equals(JSONUtils
										.getResultMsg(response))) {
									/*ToastUtils.show(getApplicationContext(),
											"获取收获信息成功");*/
									ServiceNameHandler.GoodsReceiveInfoList
											.clear();
									ServiceNameHandler.GoodsReceiveInfoList = JSONUtils
											.getqueryGoodsReceive(response);
									if (ServiceNameHandler.GoodsReceiveInfoList
											.size() > 0) {
										Intent i = new Intent();
										i.setClass(CropSellActivity.this,
												QueryCropActivity.class);
										i.putExtra("GOODSRECEIVE",
												(Serializable) ServiceNameHandler.GoodsReceiveInfoList);
										Bundle bundle = new Bundle();
										bundle.putSerializable(
												"GOODSRECEIVE",
												(GoodsReceiveInfo) ServiceNameHandler.GoodsReceiveInfoList
														.get(0));
										bundle.putInt("position", 0);
										i.putExtras(bundle);
										startActivity(i);
										overridePendingTransition(
												R.anim.zoomout, R.anim.zoomin);
									} else {
										ToastUtils.show(
												getApplicationContext(),
												"没有符合条件的数据");
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}
	}

	/**
	 * 判断提交信息是否为空
	 *
	 * @return
	 */
	public boolean estimateInfoIsNullUtils() {
		if (tv_dcName.getText().toString() == null
				|| "null".equals(tv_dcName.getText().toString())
				|| "".equals(tv_dcName.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "区域不能为空");
			return false;
		}
		if (et_cname.getText().toString() == null
				|| "null".equals(et_cname.getText().toString())
				|| "".equals(et_cname.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "供应商不能为空");
			return false;
		}
		if (tv_adminer.getText().toString() == null
				|| "null".equals(tv_adminer.getText().toString())
				|| "".equals(tv_adminer.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "经办人不能为空");
			return false;
		}
		if (tv_operatestarttime.getText().toString() == null
				|| "null".equals(tv_operatestarttime.getText().toString())
				|| "".equals(tv_operatestarttime.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "经办时间不能为空");
			return false;
		}
		if (tv_operateendtime.getText().toString() == null
				|| "null".equals(tv_operateendtime.getText().toString())
				|| "".equals(tv_operateendtime.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "经办时间不能为空");
			return false;
		}

		return true;
	}

}
