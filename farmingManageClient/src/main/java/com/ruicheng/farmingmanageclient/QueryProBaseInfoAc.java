package com.ruicheng.farmingmanageclient;

import android.app.Activity;
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
import com.ruicheng.farmingmanageclient.bean.BaseStationInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 查询基础信息界面
 *
 * @author zhaobeibei
 *
 */
public class QueryProBaseInfoAc extends BaseActivity {

	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private TextView tv_more;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private final int MODIFY = 0;
	private Dialog loadingDialog;
	private BaseStationInfo baseStationInfo;
	private Bundle bundle;
	private int position;
	private TextView tv_stationCode, tv_stationName, tv_UName, tv_plantingArea,
			tv_cropTypes, tv_dcName, tv_managerPhone, tv_stationAddr;
	private Button btn_last, btn_next;
	private int pos ;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_queryprobaseinfo);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			baseStationInfo = (BaseStationInfo) bundle
					.getSerializable("baseStationInfo");
			position = bundle.getInt("position");

		}
		init();

		setListener();
		initPopupWindow();
	}

	@Override
	public void init() {
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);

		tv_more = (TextView) findViewById(R.id.tv_more);
		loadingDialog = DialogUtils.requestDialog(this);

		tv_stationCode = (TextView) findViewById(R.id.tv_stationCode);
		tv_stationName = (TextView) findViewById(R.id.tv_stationName);
		tv_UName = (TextView) findViewById(R.id.tv_UName);
		tv_plantingArea = (TextView) findViewById(R.id.tv_plantingArea);
		tv_cropTypes = (TextView) findViewById(R.id.tv_cropTypes);
		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		tv_managerPhone = (TextView) findViewById(R.id.tv_managerPhone);
		tv_stationAddr = (TextView) findViewById(R.id.tv_stationAddr);

		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));

		setDataToView();

	}

	public void setDataToView() {

		tv_stationCode.setText(baseStationInfo.getStationCode());
		tv_stationName.setText(baseStationInfo.getStationName());
		tv_UName.setText(baseStationInfo.getUName());
		tv_plantingArea.setText(baseStationInfo.getPlantingArea());
		tv_cropTypes.setText(baseStationInfo.getCropTypes());
		tv_dcName.setText(baseStationInfo.getDcName());
		tv_managerPhone.setText(baseStationInfo.getManagerPhone());
		tv_stationAddr.setText(baseStationInfo.getStationAddr());
	}
	public void setClearDataToView() {

		tv_stationCode.setText("");
		tv_stationName.setText("");
		tv_UName.setText("");
		tv_plantingArea.setText("");
		tv_cropTypes.setText("");
		tv_dcName.setText("");
		tv_managerPhone.setText("");
		tv_stationAddr.setText("");
	}

	@Override
	public void setListener() {
		IntentViewListener intentViewListener = new IntentViewListener();
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);

		tv_more.setOnClickListener(intentViewListener);

		btn_last.setOnClickListener(intentViewListener);
		btn_next.setOnClickListener(intentViewListener);

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				//img_comment_back按钮，返回到其上一界面
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					Intent i = new Intent();
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), ServiceManageAc.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.tv_more:
					tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
							QueryProBaseInfoAc.this, R.anim.activity_translate_in));
					pop.showAtLocation(tv_more, Gravity.BOTTOM, 0, 0);
					break;
				case R.id.btn_last:
					// 点击获取上一条数据
					if (position == 0) {
						ToastUtils.show(getApplicationContext(), "当前已是第一条数据..");
						return;
					}
					btn_next.setBackgroundColor(Color.parseColor("#009933"));
					position--;
					baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
							.get(position);

					setDataToView();

					break;
				case R.id.btn_next:
					btn_last.setBackgroundColor(Color.parseColor("#009933"));
					if (position == ServiceNameHandler.allList.size() - 1) {
						ToastUtils.show(getApplicationContext(), "无更多数据..");
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						return;
					}else{
						position++;
						baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
								.get(position);

						setDataToView();

						if (position == 0) {
							btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
						} else if (position == ServiceNameHandler.allList
								.size() - 1) {
							btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						}
					}
					break;
				default:
					break;
			}
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

	private void initPopupWindow() {
		pop = new PopupWindow(QueryProBaseInfoAc.this);
		View view = getLayoutInflater().inflate(
				R.layout.item_tianyangpopupmenu, null);
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
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 删除
				new AlertDialog.Builder(QueryProBaseInfoAc.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										if (position<0) {
											ToastUtils.show(getApplicationContext(), "已无数据");
											bt1.setBackgroundColor(Color.parseColor("#F2F2F2"));
											return ;
										}else {
											getDeleteServerStation();
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
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 修改
				Intent i = new Intent();
				i.setClass(getApplicationContext(), UpdateServiceManageAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("baseStationInfo",
						(BaseStationInfo) ServiceNameHandler.allList
								.get(position));
				i.putExtras(bundle);
				startActivityForResult(i, MODIFY);
				finish();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
			}
		});
		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 查看
				Intent i = new Intent();
				i.setClass(getApplicationContext(),
						CheckServiceStationInfoAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("baseStationInfo",
						(BaseStationInfo) ServiceNameHandler.allList
								.get(position));
				bundle.putInt("position", position);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case MODIFY:

					break;
				default:
					break;
			}
		}
	}

	/**
	 * 服务站管理删除
	 *
	 */
	public void getDeleteServerStation() {
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
			params.put("stationId",((BaseStationInfo)ServiceNameHandler.allList.get(position)).getStationId());
			ServiceNameHandler.allList.remove(position);
			TwitterRestClient.get(Constant.DELETESERVERSTATION, params,
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
											"删除失败");
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
									ToastUtils.show(getApplicationContext(),
											"删除成功");
									if (position>0) {
										position -- ;
										baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
												.get(position);
										setDataToView();
									} else if(position == 0){

										if (ServiceNameHandler.allList.size()>0) {
											baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
													.get(position);
											setDataToView();
											position -- ;
										} else {
											ToastUtils.show(getApplicationContext(), "已无数据");
											position -- ;
											if (position<0) {
												finish();
											}
										}
									}else {
										ToastUtils.show(getApplicationContext(), "已无数据");
									}


									setDataToView();
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
