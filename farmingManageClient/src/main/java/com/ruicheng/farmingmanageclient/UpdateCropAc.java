package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CaculateInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveDetailListInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveInfo;
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

import java.util.List;

/**
 * 修改收货信息
 *
 * @author zhaobeibei
 *
 */
public class UpdateCropAc extends BaseActivity implements OnClickListener {

	private TextView et_goodUnit, et_cname, et_loadCode, et_purId, et_goodName,
			tv_dcName, tv_userName, tv_Time, tv_remark;
	private Button btn_save;
	private EditText et_goodPricem, et_purchasingWeight, et_receivePackagesm,
			et_packageWeight, et_goodMoney;
	private Dialog loadingDialog;
	private GoodsReceiveInfo goodsReceiveInfo;
	private Bundle bundle;
	private List<GoodsReceiveDetailListInfo> GoodsReceiveDetailList;
	private ImageView img_comment_back;
	private final int GOODMONEY_PRICE = 0;
	private final int GOODMONEY_ = 1;
	private CaculateInfo caculateInfo = new CaculateInfo();
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
				case GOODMONEY_PRICE:

					caculateInfo = (CaculateInfo) msg.obj;

					et_goodMoney.setText(String.valueOf(((Float
							.parseFloat(caculateInfo.getGoodPricem()) * (Float
							.parseFloat(caculateInfo.getPurchasingWeight()))))));
					break;

				default:
					break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updatecrop);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			goodsReceiveInfo = (GoodsReceiveInfo) bundle
					.getSerializable("goodsReceiveInfo");
		}
		init();
		getGoodReceiveDetailForPackagePrint();
		setListener();

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

		btn_save = (Button) findViewById(R.id.btn_save);

		et_cname = (TextView) findViewById(R.id.et_cname);
		et_loadCode = (TextView) findViewById(R.id.et_loadCode);
		et_purId = (TextView) findViewById(R.id.et_purId);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_Time = (TextView) findViewById(R.id.tv_Time);
		tv_remark = (TextView) findViewById(R.id.tv_remark);
		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		tv_dcName.setText(PreferencesUtils.getString(getApplicationContext(),
				Constant.DCNAME));

		et_goodName = (TextView) findViewById(R.id.et_goodName);
		et_goodPricem = (EditText) findViewById(R.id.et_goodPricem);
		et_goodUnit = (TextView) findViewById(R.id.et_goodUnit);
		et_purchasingWeight = (EditText) findViewById(R.id.et_purchasingWeight);
		et_receivePackagesm = (EditText) findViewById(R.id.et_receivePackagesm);
		et_packageWeight = (EditText) findViewById(R.id.et_packageWeight);
		et_goodMoney = (EditText) findViewById(R.id.et_goodMoney);
		loadingDialog = DialogUtils.requestDialog(this);

		et_cname.setText(goodsReceiveInfo.getVendorName());
		if (goodsReceiveInfo.getLoadCode() != null
				&&!"null".equals(goodsReceiveInfo.getLoadCode())
				&& !"".equals(goodsReceiveInfo.getLoadCode())) {
			et_loadCode.setText(goodsReceiveInfo.getLoadCode());
		}
		if (!"".equals(goodsReceiveInfo.getReceiveCode())
				&& !"null".equals(goodsReceiveInfo.getReceiveCode())) {

			et_purId.setText(goodsReceiveInfo.getPurId());
		} else {
			et_purId.setText("");

		}
		if (!"".equals(goodsReceiveInfo.getRemark())&&!"null".equals(goodsReceiveInfo.getRemark())) {
			tv_remark.setText(goodsReceiveInfo.getRemark());
		}else{
			tv_remark.setText("");
		}
		tv_Time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));

		mHandler.post(new Runnable() {
			@Override
			public void run() {

				Message msg = Message.obtain();
				caculateInfo.setGoodPricem(et_goodPricem.getText().toString());
				caculateInfo.setPackageWeight(et_packageWeight.getText()
						.toString());

				msg.obj = caculateInfo;

			}
		});

	}

	@Override
	public void setListener() {
		btn_save.setOnClickListener(this);
		// 单价改变
		et_goodPricem.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				et_goodMoney.setText(String.valueOf(((Double
						.parseDouble(et_goodPricem.getText().toString()) * (Double
						.parseDouble(et_purchasingWeight.getText().toString()))))));
			}
		});
		// 总重改变
		et_purchasingWeight
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {

						et_goodMoney.setText(String.valueOf(((Double
								.parseDouble(et_goodPricem.getText().toString()) * (Double
								.parseDouble(et_purchasingWeight.getText()
										.toString()))))));
					}
				});
		// 收货件数改变
		et_receivePackagesm
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						et_packageWeight.setText(String.valueOf(((Double
								.parseDouble(et_purchasingWeight.getText()
										.toString()) / (Double
								.parseDouble(et_receivePackagesm.getText()
										.toString()))))));
					}
				});
		// 件重改变
		et_packageWeight.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				et_receivePackagesm.setText(String.valueOf(((Double
						.parseDouble(et_purchasingWeight.getText().toString()) / (Double
						.parseDouble(et_packageWeight.getText().toString()))))));
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getSaveEdit();
				break;

			default:
				break;
		}
	}

	/**
	 * 收货信息管理数据修改保存
	 *
	 */
	public void getSaveEdit() {
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

			params.put(
					"detailIdm",
					((GoodsReceiveDetailListInfo) GoodsReceiveDetailList.get(0))
							.getDetailId());
			params.put(
					"goodIdm",
					((GoodsReceiveDetailListInfo) GoodsReceiveDetailList.get(0))
							.getGoodId());
			params.put("receivePackagesm", et_receivePackagesm.getText()
					.toString());
			params.put("packageWeightm", et_packageWeight.getText().toString());
			params.put("totalGoodWeightm", et_purchasingWeight.getText()
					.toString());
			params.put("goodPricem", et_goodPricem.getText().toString());
			params.put("goodMoneym", et_goodMoney.getText().toString());

			params.put("goodsReceive.receiveId",
					goodsReceiveInfo.getReceiveId());
			params.put("goodsReceive.totalMoney",
					goodsReceiveInfo.getTotalMoney());
			params.put("goodsReceive.vendorId", goodsReceiveInfo.getVendorId());
			params.put("goodsReceive.vendorType",
					goodsReceiveInfo.getVendorType());
			TwitterRestClient.get(Constant.SAVEEDIT, params,
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
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(),
											"修改失败");
								}
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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
									ToastUtils.show(getApplicationContext(),
											"修改成功");
									finish();
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
	 * 收货信息管理数据查看
	 *
	 */
	public void getGoodReceiveDetailForPackagePrint() {
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
			params.put("receiveId", goodsReceiveInfo.getReceiveId());
			TwitterRestClient.get(Constant.GOODRECEIVEDETAILFORPACKAGEPRINT,
					params, new JsonHttpResponseHandler() {

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
								GoodsReceiveDetailList = JSONUtils
										.getGoodReceiveDetailForPackagePrint(response);
								et_goodName
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getGoodName());
								et_goodPricem
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getGoodPrice());
								if ("1".equals(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
										.get(0)).getGoodUnit())) {
									et_goodUnit.setText("是");
								} else {
									et_goodUnit.setText("否");
								}
								et_purchasingWeight
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getTotalGoodWeight());
								et_receivePackagesm
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getReceivePackages());
								et_packageWeight
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getPackageWeight());
								et_goodMoney
										.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
												.get(0)).getGoodMoney());

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		}
	}
}
