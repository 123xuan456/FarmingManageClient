package com.ruicheng.farmingmanageclient;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PlanListInfo;
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

/**
 * 生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class ProplanActivity extends BaseActivity {

	private Button btn_query;
	private TextView tv_agriCode,tv_pro_add, tv_title, et_agriName, et_startDrawDate,
			et_endDrawDate, et_predStarMinDate, et_predStarMaxDate, et_cropName;
	private final int MODIFY = 0;
	private Intent intent;
	private int type;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private Dialog loadingDialog;
	private boolean isQuery;
	private LinearLayout linear_planName, linear_agriName, linear_cropName;
	private EditText et_planName, et_planDrawPers, et_agriId;
	private final int AGRINAME = 0;
	int nameType ;
	private View view_cropName, view_agriName ;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_proplan);
		intent = getIntent();
		if (intent != null) {
		}
		type = PreferencesUtils.getInt(getApplicationContext(), Constant.TYPE);
		init();
		setListener();
		/*getQueryProductionPlanPage(false);*/
	}

	@Override
	public void init() {
		view_cropName = findViewById(R.id.view_cropName);
		view_agriName = findViewById(R.id.view_agriName);

		btn_query = (Button) findViewById(R.id.btn_query);
		tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
		tv_title = (TextView) findViewById(R.id.tv_title);
		if ("1".equals(String.valueOf(type))) {
			tv_title.setText("加工生产计划管理");

		} else {
			tv_title.setText("种植生产计划管理");
		}
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		loadingDialog = DialogUtils.requestDialog(this);

		linear_planName = (LinearLayout) findViewById(R.id.linear_planName);
		linear_agriName = (LinearLayout) findViewById(R.id.linear_agriName);
		linear_cropName = (LinearLayout) findViewById(R.id.linear_cropName);
		et_agriId = (EditText) findViewById(R.id.et_agriId);
		et_planName = (EditText) findViewById(R.id.et_planName);
		et_agriName = (TextView) findViewById(R.id.et_agriName);
		et_planDrawPers = (EditText) findViewById(R.id.et_planDrawPers);
		et_startDrawDate = (TextView) findViewById(R.id.et_startDrawDate);
		et_endDrawDate = (TextView) findViewById(R.id.et_endDrawDate);
		et_predStarMinDate = (TextView) findViewById(R.id.et_predStarMinDate);
		et_predStarMaxDate = (TextView) findViewById(R.id.et_predStarMaxDate);
		tv_agriCode = (TextView) findViewById(R.id.tv_agriCode);

		et_cropName = (TextView) findViewById(R.id.et_cropName);

		if (type == 1) {
			linear_cropName.setVisibility(View.GONE);
			view_cropName.setVisibility(View.GONE);
			tv_agriCode.setText("农产品名称编号");
		} else {
			linear_agriName.setVisibility(View.GONE);
			view_agriName.setVisibility(View.GONE);
			tv_agriCode.setText("作物名称编号");
		}
	}

	@Override
	public void setListener() {
		// 跳转界面监听
		IntentViewListener intentViewListener = new IntentViewListener();
		btn_query.setOnClickListener(intentViewListener);
		tv_pro_add.setOnClickListener(intentViewListener);
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);
		et_cropName.setOnClickListener(intentViewListener);
		et_agriName.setOnClickListener(intentViewListener);
		et_startDrawDate.setOnClickListener(intentViewListener);
		et_endDrawDate.setOnClickListener(intentViewListener);
		et_predStarMinDate.setOnClickListener(intentViewListener);
		et_predStarMaxDate.setOnClickListener(intentViewListener);

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_pro_add:
					Intent i = new Intent();
					i.setClass(ProplanActivity.this, AddProplanAc.class);
					i.putExtra("TYPE", type);
					startActivityForResult(i, MODIFY);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					finish();
					break;
				case R.id.btn_query:

					getQueryProductionPlanPage(true);

					break;
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					finish();
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.et_agriName:
					Intent agriIntent = new Intent();
					agriIntent.setClass(getApplicationContext(),
							CropTypeNameAc.class);
					agriIntent.putExtra("fromWhichView", 3);
					agriIntent.putExtra("nameType", 0);
					startActivityForResult(agriIntent, AGRINAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.et_cropName:
					Intent cropIntent = new Intent();
					cropIntent.setClass(getApplicationContext(),
							CropTypeNameAc.class);
					cropIntent.putExtra("fromWhichView", 3);
					cropIntent.putExtra("nameType", 0);
					startActivityForResult(cropIntent, AGRINAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.et_startDrawDate:
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplanActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							et_startDrawDate.setText(date);
						/*if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
						} else {
							ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
						}*/
						}
					};
					break;
				case R.id.et_endDrawDate:
					InputMethodManager z = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					z.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplanActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (et_startDrawDate.getText().toString()==null||et_startDrawDate.getText().toString().equals("")) {
								ToastUtils.show(getApplicationContext(), "请先输入开始时间");
								return ;
							}
							if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(et_startDrawDate.getText().toString()).getTime()>0) {
								et_endDrawDate.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
							}
						}
					};
					break;
				case R.id.et_predStarMinDate:
					InputMethodManager immmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					immmm.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplanActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							et_predStarMinDate.setText(date);
						/*if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
						} else {
							ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
						}*/
						}
					};
					break;
				case R.id.et_predStarMaxDate:
					InputMethodManager immmmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					immmmm.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplanActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (et_predStarMinDate.getText().toString()==null||et_predStarMinDate.getText().toString().equals("")) {
								ToastUtils.show(getApplicationContext(), "请先输入开始时间");
								return ;
							}
							if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(et_predStarMinDate.getText().toString()).getTime()>0) {
								et_predStarMaxDate.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
							}
						}
					};
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case AGRINAME:
					nameType = data.getIntExtra("nameType", -1);
					String cropType = data.getStringExtra("cropType");
					String dicCode = data.getStringExtra("dicCode");
					et_agriId.setText(dicCode);
					if (type==1) {
						et_agriName.setText(cropType);
					} else {
						et_cropName.setText(cropType);
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
/*			startActivity(new Intent(this, CostequationActivity.class));
*/			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 *
	 *
	 */
	public void getQueryProductionPlanPage(boolean isQueryy) {
		isQuery = isQueryy;
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
			params.put("pvo.planType", type);
			params.put("pager.num", Constant.NUM);
			params.put("pager.size", Constant.SIZE);
			if (isQueryy == true) {
				if ("1".equals(type)) {
					if (!"".equals(et_agriName.getText().toString())&&!"null".equals(et_agriName.getText().toString())) {
						params.put("pvo.agriName", et_agriName.getText().toString());

					} else {
						params.put("pvo.agriName", "");

					}
				} else {
					if (!"".equals(et_cropName.getText().toString())&&!"null".equals(et_cropName.getText().toString())) {
						params.put("pvo.agriName", et_cropName.getText().toString());
					} else {
						params.put("pvo.agriName", "");
					}
				}
				if (!"".equals(et_agriId.getText().toString())&&!"null".equals(et_agriId.getText().toString())) {

					params.put("pvo.agriId", et_agriId.getText().toString());
				} else {
					params.put("pvo.agriId", "");

				}
				if (!"".equals(et_planName.getText().toString())&&!"null".equals(et_planName.getText().toString())) {
					params.put("pvo.planName",et_planName.getText().toString());
				} else {
					params.put("pvo.planName","");

				}
				if (!"".equals(et_startDrawDate.getText().toString())&&!"null".equals(et_startDrawDate.getText().toString())) {
					params.put("pvo.predStarMinDate",et_startDrawDate.getText().toString());

				} else {
					params.put("pvo.predStarMinDate","");

				}
				if (!"".equals(et_endDrawDate.getText().toString())&&!"null".equals(et_endDrawDate.getText().toString())) {
					params.put("pvo.predStarMaxDate",et_endDrawDate.getText().toString());

				} else {
					params.put("pvo.predStarMaxDate","");

				}
				if (!"".equals(et_predStarMinDate.getText().toString())&&!"null".equals(et_predStarMinDate.getText().toString())) {
					params.put("pvo.startDrawDate",et_predStarMinDate.getText().toString());

				} else {
					params.put("pvo.startDrawDate","");

				}
				if (!"".equals(et_predStarMaxDate.getText().toString())&&"null".equals(et_predStarMaxDate.getText().toString())) {
					params.put("pvo.endDrawDate", et_predStarMaxDate.getText().toString());

				} else {
					params.put("pvo.endDrawDate", "");
				}
				params.put("pvo.planDrawPers", PreferencesUtils.getString(
						getApplicationContext(), Constant.USERNAME));
				params.put("pvo.planDrawPersId", PreferencesUtils.getInt(getApplicationContext(),
						Constant.USERID) + "");
			}
			TwitterRestClient.get(Constant.QUERYPRODUCTIONPLANPAGE, params,
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
									ServiceNameHandler.allList.clear();
									ServiceNameHandler.allList = JSONUtils
											.getQueryProductionPlanPage(response);
									if (isQuery == true) {
										if (ServiceNameHandler.allList != null
												&&ServiceNameHandler.allList.size() == 0) {
											ToastUtils.show(
													getApplicationContext(),
													"没有数据");
											return;
										}
										Intent i = new Intent();
										i.setClass(ProplanActivity.this,
												QueryProplanAc.class);
										Bundle bundle = new Bundle();
										bundle.putSerializable("planListInfo",
												(PlanListInfo) ServiceNameHandler.allList.get(0));
										bundle.putInt("TYPE", type);
										i.putExtras(bundle);
										startActivityForResult(i, MODIFY);
										overridePendingTransition(
												R.anim.zoomout, R.anim.zoomin);
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		getQueryProductionPlanPage(false);
	}
}
