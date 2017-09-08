package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CnameInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveInfo;
import com.ruicheng.farmingmanageclient.bean.PurchaseOrderInfo;
import com.ruicheng.farmingmanageclient.bean.StationInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加物品界面
 *
 * @author zhaobeibei
 *
 */
public class AddDetailCropAc extends BaseActivity implements OnClickListener {

	private Button btn_adddetail, btn_save;
	private final int ADDDETAIL = 0 ;
	private final int DISTRICT = 1 ;
	private final int PURID = 2 ;
	private final int CNAME =3;
	private TextView et_cname,tv_dcName,tv_userName,tv_Time,et_purId;

	private EditText et_loadCode,tv_remark;
	private Bundle bundle,detailBundle;
	private Dialog loadingDialog ;
	private StationInfo stationInfo ;
	private PurchaseOrderInfo purchaseOrderInfo;
	private CnameInfo cnameInfo;
	private ImageView img_comment_back;
	private String dcId ;
	private GoodsReceiveInfo goodsReceiveInfo;
	private String vendorId ;
	private String vendorType ;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_adddetailcrop);

		init();
		setListener() ;

		getAddGoodReceive();

	}

	@Override
	public void init() {
		btn_adddetail = (Button) findViewById(R.id.btn_adddetail);
		btn_save = (Button) findViewById(R.id.btn_save);
		et_cname = (TextView) findViewById(R.id.et_cname);
		et_loadCode = (EditText) findViewById(R.id.et_loadCode);
		et_purId = (TextView) findViewById(R.id.et_purId);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_Time = (TextView) findViewById(R.id.tv_Time);
		tv_remark = (EditText) findViewById(R.id.tv_remark);
		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		loadingDialog = DialogUtils.requestDialog(this);
		tv_dcName.setText(PreferencesUtils.getString(getApplicationContext(), Constant.DCNAME));


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

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			/*startActivity(new Intent(this, CropSellActivity.class));*/
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void setListener() {
		btn_adddetail.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		tv_Time.setOnClickListener(this);
		et_cname.setOnClickListener(this);
		et_purId.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getSaveGoodsReceive();
				break;
			case R.id.btn_adddetail:
			/*if (purchaseOrderInfo==null) {
				ToastUtils.show(getApplicationContext(), "请先选择采购单号");
				return ;
			}*/
				Intent i = new Intent();
				i.setClass(getApplicationContext(), AddCropActivity.class);
				Bundle bundle  = new Bundle();
				// isEmpty 判断采购单号是否为空
				if ("".equals(et_purId.getText().toString())) {
					bundle.putBoolean("isEmpty",true);
				} else {
					bundle.putBoolean("isEmpty",false);
					bundle.putString("PurId", purchaseOrderInfo.getPurId());
				}
				i.putExtras(bundle);
				startActivityForResult(i, ADDDETAIL);

				break;
			case R.id.et_purId:
				Intent purIdIntent = new Intent();
				purIdIntent.setClass(getApplicationContext(), PurIdAc.class);
				startActivityForResult(purIdIntent, PURID);
				break ;
			case R.id.et_cname:
				Intent cnameIntent = new Intent();
				cnameIntent.setClass(getApplicationContext(), CnameAc.class);
				cnameIntent.putExtra("fromwhichview",1);
				startActivityForResult(cnameIntent, CNAME);
				break ;
			default:
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case ADDDETAIL:
					detailBundle = data.getExtras();

					break;
				case PURID:
					bundle = data.getExtras();
					purchaseOrderInfo = (PurchaseOrderInfo) bundle.getSerializable("purchaseOrderInfo");
					et_purId.setText(purchaseOrderInfo.getPrintCode());

//				PurchaseInfo purchaseInfo = (PurchaseInfo) bundle.getSerializable("purchaseInfo");
					et_cname.setText(purchaseOrderInfo.getProviderName());
					vendorId = purchaseOrderInfo.getProviderId() ;
					break ;
				case CNAME:
					cnameInfo = (CnameInfo) data.getSerializableExtra("cname");
					et_cname.setText(cnameInfo.getCname());
					vendorId = cnameInfo.getId();
					vendorType = cnameInfo.getType() ;
					break;
				default:
					break;
			}
		}
	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){

		if (tv_dcName.getText().toString()==null||"null".equals(tv_dcName.getText().toString())||"".equals(tv_dcName.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "区域不能为空");
			return false ;
		}
		if (et_cname.getText().toString()==null||"null".equals(et_cname.getText().toString())||"".equals(et_cname.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "供应商不能为空");
			return false ;
		}
		if (detailBundle ==null) {
			ToastUtils.show(getApplicationContext(), "收货单必须要添加要收货的商品");
			btn_adddetail.setBackgroundColor(Color.parseColor("#009933"));
			return false ;
		}
		return true ;
	}
	public void getSaveGoodsReceive(){
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			if (!estimateInfoIsNullUtils()) {
				return ;
			}
			loadingDialog.show();

			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));

			params.put("goodIdm",detailBundle.getString("goodIdm"));
			params.put("purIdm",detailBundle.getString("purIdm"));
			params.put("purInfoIdm",detailBundle.getString("purInfoIdm"));
			//货品名称
			params.put("goodNamem",detailBundle.getString("goodNamem"));
			//件数
			params.put("receivePackagesm",detailBundle.getString("receivePackagesm"));
			params.put("goodUnitm",detailBundle.getString("goodUnitm"));
			//件重
			params.put("packageWeightm",detailBundle.getString("packageWeightm"));
			params.put("totalGoodWeightm",detailBundle.getString("totalGoodWeightm"));
			params.put("goodPricem",detailBundle.getString("goodPricem"));
			params.put("goodMoneym",detailBundle.getString("goodMoneym"));

			params.put("goodsReceive.userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("goodsReceive.userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			if (!"".equals(tv_Time.getText().toString())&&!"null".equals(tv_Time.getText().toString())) {

				params.put("goodsReceive.Time",tv_Time.getText().toString());
			} else {
				params.put("goodsReceive.Time","");

			}
			if (!"".equals(tv_dcName.getText().toString())&&!"null".equals(tv_dcName.getText().toString())) {

				params.put("goodsReceive.areaName",tv_dcName.getText().toString());
			} else {
				params.put("goodsReceive.areaName","");

			}
			params.put("goodsReceive.areaId",PreferencesUtils.getString(getApplicationContext(), Constant.DCID));
			if (!"".equals(tv_remark.getText().toString())&&!"null".equals(tv_remark.getText().toString())) {

				params.put("goodsReceive.remark",tv_remark.getText().toString());
			} else {
				params.put("goodsReceive.remark","");

			}
			if (!"".equals(vendorId)&&!"null".equals(vendorId)) {
				params.put("goodsReceive.vendorId",vendorId);

			} else {
				params.put("goodsReceive.vendorId","");

			}
			if (!"".equals(et_cname.getText().toString())&&!"null".equals(et_cname.getText().toString())) {

				params.put("goodsReceive.vendorName",et_cname.getText().toString());
			} else {
				params.put("goodsReceive.vendorName","");

			}
			if (vendorType!=null&&!"".equals(vendorType)&&!"null".equals(vendorType)) {

				params.put("goodsReceive.vendorType",vendorType);
			} else {
				params.put("goodsReceive.vendorType","");
			}
			if (!"".equals(et_purId.getText().toString())&&!"null".equals(et_purId.getText().toString())) {

				params.put("goodsReceive.purId",purchaseOrderInfo.getPurId());
			} else {
				params.put("goodsReceive.purId","");

			}
			if (!"".equals(et_loadCode.getText().toString())&&!"null".equals(et_loadCode.getText().toString())) {

				params.put("goodsReceive.loadCode",et_loadCode.getText().toString());
			} else {
				params.put("goodsReceive.loadCode","");

			}
			TwitterRestClient.get(Constant.SAVEGOODSRECEIVE, params,new JsonHttpResponseHandler(){
				@Override
				public void onFailure(int statusCode, Header[] headers,
									  Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					if (loadingDialog.isShowing()) {
						loadingDialog.dismiss();
					}
					ToastUtils.show(getApplicationContext(), "保存失败...");
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
							ToastUtils.show(getApplicationContext(), "保存成功");
							finish();
						}else {
							ToastUtils.show(getApplicationContext(),"读取失败");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			});
		}
	}
	public void getAddGoodReceive() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			TwitterRestClient.get(Constant.ADDGOODRECEIVE, params,
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
									goodsReceiveInfo = JSONUtils.getAddGoodReceive(response);

									tv_Time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));
									tv_userName.setText(goodsReceiveInfo.getUserName());

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
}
