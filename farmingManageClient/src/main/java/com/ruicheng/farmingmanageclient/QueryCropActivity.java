package com.ruicheng.farmingmanageclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
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

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询作物交售界面
 *
 * @author zhaobeibei
 *
 */
public class QueryCropActivity extends BaseActivity implements OnClickListener {

	private Button tv_modify, btn_last, btn_next;
	private Dialog loadingDialog;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private TextView tv_dc, tv_vendorName, tv_vendorType, tv_receiveCode,
			tv_userName, tv_time, tv_remark;
	private Bundle bundle ;
	private GoodsReceiveInfo goodsReceiveInfo;
	private int position ;
	private ImageView img_comment_back;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_querycrop);
		bundle = getIntent().getExtras();
		if (bundle !=null) {
			goodsReceiveInfo = (GoodsReceiveInfo) bundle.getSerializable("GOODSRECEIVE");
			position = bundle.getInt("position");
		}
		init();
		setListener();

		initPopupWindow();

	}

	@Override
	public void init() {
		tv_modify = (Button) findViewById(R.id.tv_modify);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		loadingDialog = DialogUtils.requestDialog(this);


		tv_dc = (TextView) findViewById(R.id.tv_dc);
		tv_vendorName = (TextView) findViewById(R.id.tv_vendorName);
		tv_vendorType = (TextView) findViewById(R.id.tv_vendorType);
		tv_receiveCode = (TextView) findViewById(R.id.tv_receiveCode);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_remark = (TextView) findViewById(R.id.tv_remark);

		tv_dc.setText(goodsReceiveInfo.getAreaName());
		tv_vendorName.setText(goodsReceiveInfo.getVendorName());

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
		tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
		tv_userName.setText(goodsReceiveInfo.getUserName());
		if (!"".equals(goodsReceiveInfo.getRemark())) {
			tv_remark.setText(goodsReceiveInfo.getRemark());
		}else{
			tv_remark.setText("");
		}
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
		tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));
	}

	@Override
	public void setListener() {
		tv_modify.setOnClickListener(this);
		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_modify:
				tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
						QueryCropActivity.this, R.anim.activity_translate_in));
				pop.showAtLocation(btn_last, Gravity.BOTTOM, 0, 0);
				break;
			case R.id.btn_last:
				// 点击获取上一条数据
				if (position == 0) {
					ToastUtils.show(getApplicationContext(), "当前已是第一条数据..");
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
					return ;
				}

				btn_next.setBackgroundColor(Color.parseColor("#009933"));
				position -- ;
				goodsReceiveInfo = ServiceNameHandler.GoodsReceiveInfoList.get(position);

				tv_dc.setText(goodsReceiveInfo.getAreaName());
				tv_vendorName.setText(goodsReceiveInfo.getVendorName());
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
				tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
				tv_userName.setText(goodsReceiveInfo.getUserName());
				tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));
				if (!"".equals(goodsReceiveInfo.getRemark())) {
					tv_remark.setText(goodsReceiveInfo.getRemark());
				}else{
					tv_remark.setText("");
				}
				break;
			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				if (position ==ServiceNameHandler.GoodsReceiveInfoList.size()-1) {
					ToastUtils.show(getApplicationContext(), "无更多数据..");
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
					return ;
				}
				position ++ ;
				goodsReceiveInfo = ServiceNameHandler.GoodsReceiveInfoList.get(position);

				tv_dc.setText(goodsReceiveInfo.getAreaName());
				tv_vendorName.setText(goodsReceiveInfo.getVendorName());
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
				tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
				tv_userName.setText(goodsReceiveInfo.getUserName());
				tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));
				if (!"".equals(goodsReceiveInfo.getRemark())) {
					tv_remark.setText(goodsReceiveInfo.getRemark());
				}else{
					tv_remark.setText("");
				}
				if (position == 0) {
					btn_last.setBackgroundColor(Color.parseColor("#009933"));
				}else if (position ==(ServiceNameHandler.GoodsReceiveInfoList.size()-1)){
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}
				break;

			default:
				break;
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

			params.put("receiveId",goodsReceiveInfo.getReceiveId());

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
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(),
											"查询失败");
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
											"查询成功");

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}
	}


	private void initPopupWindow() {
		pop = new PopupWindow(QueryCropActivity.this);
		View view = getLayoutInflater().inflate(
				R.layout.item_tianyangpopupmenu_query, null);
		tianynagmenu_popup = (LinearLayout) view
				.findViewById(R.id.equip_popup_menu);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		final Button bt1 = (Button) view.findViewById(R.id.item_equippopup_delete);
		Button bt2 = (Button) view.findViewById(R.id.item_equippopup_modify);
		Button bt3 = (Button) view.findViewById(R.id.item_equippopup_cancel);
		Button bt4 = (Button) view.findViewById(R.id.item_equippopup_query);
		// 点击父布局消失框pop
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pop消失 清除动画
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
			}
		});
		bt4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 查看
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();

				Intent i = new Intent();
				i.setClass(getApplicationContext(), CheckCropAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goodsReceiveInfo", goodsReceiveInfo);
				i.putExtras(bundle);
				startActivity(i);

			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 修改
				Intent i = new Intent();
				i.setClass(getApplicationContext(), UpdateCropAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goodsReceiveInfo", goodsReceiveInfo);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(QueryCropActivity.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										if (position>=0) {
											getDelGoodsReceiveSheet(position);
										}else if(position<0){
											ToastUtils.show(getApplicationContext(), "已无数据");
											bt1.setBackgroundColor(Color.parseColor("#F2F2F2"));
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
									}
								}).show();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 取消

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			startActivity(new Intent(this, CropSellActivity.class));
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	public void getDelGoodsReceiveSheet(int pos) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {

			pop.dismiss();
			tianynagmenu_popup.clearAnimation();
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
					"receiveId",
					(ServiceNameHandler.GoodsReceiveInfoList.get(pos)).getReceiveId());
			TwitterRestClient.get(Constant.DELEGOODSRECEIVESHEET, params,
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
									ToastUtils.show(getApplicationContext(), "删除失败");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
									ToastUtils.show(getApplicationContext(), "删除成功");
									ServiceNameHandler.GoodsReceiveInfoList.remove(position);
									if (position>0) {
										position -- ;
										goodsReceiveInfo = ServiceNameHandler.GoodsReceiveInfoList.get(position);

										tv_dc.setText(goodsReceiveInfo.getAreaName());
										tv_vendorName.setText(goodsReceiveInfo.getVendorName());
										tv_vendorType.setText(goodsReceiveInfo.getVendorType());
										tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
										tv_userName.setText(goodsReceiveInfo.getUserName());
										tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));

									}else if(position == 0){
										if (ServiceNameHandler.GoodsReceiveInfoList.size()>0) {
											goodsReceiveInfo = ServiceNameHandler.GoodsReceiveInfoList.get(position);
											tv_dc.setText(goodsReceiveInfo.getAreaName());
											tv_vendorName.setText(goodsReceiveInfo.getVendorName());
											tv_vendorType.setText(goodsReceiveInfo.getVendorType());
											tv_receiveCode.setText(goodsReceiveInfo.getReceiveCode());
											tv_userName.setText(goodsReceiveInfo.getUserName());
											tv_time.setText(DateUtils.strDateFormatSh(goodsReceiveInfo.getTime()));
											position -- ;

										} else {
											ToastUtils.show(getApplicationContext(), "已无数据");
											position -- ;
											if (position<0) {
												finish();
											}
										}
									}else {
										if(position --<0){
											ToastUtils.show(getApplicationContext(), "已无数据");
										}
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
}
