package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
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
 * 作物交售--查看作物界面
 *
 * @author zhaobeibei
 *
 */
public class CheckCropAc extends BaseActivity implements OnClickListener {

	private Dialog loadingDialog;
	private Button btn_last, btn_next;
	private TextView tv_dc, tv_loadCode, tv_vendorName, tv_userName, tv_remark,
			tv_receiveCode, tv_vendorType, tv_time, tv_goodName, tv_goodPricem,
			tv_goodUnitm, tv_totalGoodWeightm, tv_receivePackagesm,
			tv_packageWeightm, tv_goodMoneym;
	private Bundle bundle;
	private GoodsReceiveInfo goodsReceiveInfo;
	private List<GoodsReceiveDetailListInfo> GoodsReceiveDetailList;
	private List<GoodsReceiveInfo> GoodsReceiveList;
	private int position  = 0;
	private ImageView img_comment_back;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode ==event.KEYCODE_BACK) {
			/*android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_checkcrop);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			goodsReceiveInfo = (GoodsReceiveInfo) bundle
					.getSerializable("goodsReceiveInfo");
		}
		init();
		getQueryGood();
		getGoodReceiveDetailForPackagePrint();
		setListener();
	}

	@Override
	public void init() {

		loadingDialog = DialogUtils.requestDialog(this);

		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);

		btn_last.setBackgroundColor(Color.parseColor("#009933"));

		tv_dc = (TextView) findViewById(R.id.tv_dc);
		tv_loadCode = (TextView) findViewById(R.id.tv_loadCode);
		tv_vendorName = (TextView) findViewById(R.id.tv_vendorName);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_remark = (TextView) findViewById(R.id.tv_remark);
		tv_receiveCode = (TextView) findViewById(R.id.tv_receiveCode);
		tv_vendorType = (TextView) findViewById(R.id.tv_vendorType);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_goodName = (TextView) findViewById(R.id.tv_goodName);
		tv_goodPricem = (TextView) findViewById(R.id.tv_goodPricem);
		tv_goodUnitm = (TextView) findViewById(R.id.tv_goodUnitm);
		tv_totalGoodWeightm = (TextView) findViewById(R.id.tv_totalGoodWeightm);
		tv_receivePackagesm = (TextView) findViewById(R.id.tv_receivePackagesm);
		tv_packageWeightm = (TextView) findViewById(R.id.tv_packageWeightm);
		tv_goodMoneym = (TextView) findViewById(R.id.tv_goodMoneym);

		tv_dc.setText(goodsReceiveInfo.getAreaName());
		if (goodsReceiveInfo.getLoadCode()!=null&&!"null".equals(goodsReceiveInfo.getLoadCode())&&!"".equals(goodsReceiveInfo.getLoadCode())) {
			tv_loadCode.setText(goodsReceiveInfo.getLoadCode());
		}
		tv_vendorName.setText(goodsReceiveInfo.getVendorName());
		tv_userName.setText(goodsReceiveInfo.getUserName());
		if (!"null".equals(goodsReceiveInfo.getRemark())) {
			tv_remark.setText(goodsReceiveInfo.getRemark());
		}
		tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
		if ("NCA".equals(goodsReceiveInfo.getVendorType())) {
			tv_vendorType.setText("冷库客户");

		} else if("GT".equals(goodsReceiveInfo.getVendorType())){
			tv_vendorType.setText("农户");

		}else if("NC".equals(goodsReceiveInfo.getVendorType())){
			tv_vendorType.setText("农产品供应商");

		}else if("NZ".equals(goodsReceiveInfo.getVendorType())){
			tv_vendorType.setText("农资包装材料供应商");

		}else if("FWZ".equals(goodsReceiveInfo.getVendorType())){
			tv_vendorType.setText("服务站供应商");

		}else if("WH".equals(goodsReceiveInfo.getVendorType())){
			tv_vendorType.setText("销售商");
		}
		tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));


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
	public void setListener() {
		/* , */

		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_last:
				if (position == 0) {
					ToastUtils.show(getApplicationContext(), "当前已是第一条数据..");
					return ;
				}
				btn_next.setBackgroundColor(Color.parseColor("#009933"));
				position -- ;
				showViewData();
				break;

			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				// 点击获取下一条数据
				if (position ==GoodsReceiveDetailList.size()-1) {
					ToastUtils.show(getApplicationContext(), "无更多数据..");
					return ;
				}
				position ++ ;
				showViewData();
				if (position == 0) {
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}else if (position ==GoodsReceiveDetailList.size()-1){
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}
				break;

			default:
				break;
		}
	}

	/**
	 * 获取商品信息接口
	 *
	 */
	public void getQueryGood() {
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
			TwitterRestClient.get(Constant.QUERYGOOD, params,
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
									/*listAll = JSONUtils
											.getQueryClient(response);*/

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
								showViewData();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		}
	}
	/**
	 * 显示控件上的数据
	 *
	 */
	public void showViewData() {
		tv_goodName
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position)).getGoodName());
		tv_goodPricem
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position)).getGoodPrice());
		if ("1".equals(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
				.get(position)).getGoodUnit())) {
			tv_goodUnitm
					.setText("是");
		} else {
			tv_goodUnitm
					.setText("否");
		}
		tv_totalGoodWeightm
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position))
						.getTotalGoodWeight());
		tv_receivePackagesm
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position))
						.getReceivePackages());
		tv_packageWeightm
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position)).getPackageWeight());
		tv_goodMoneym
				.setText(((GoodsReceiveDetailListInfo) GoodsReceiveDetailList
						.get(position)).getGoodMoney());
	}

}
