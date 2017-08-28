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
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.ProductionPlanStatisticsInfo;
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

import java.util.Date;
import java.util.List;

/**
 * 生产计划统计界面
 *
 * @author zhaobeibei
 *
 */
public class ProplantableActivity extends BaseActivity {

	private Button btn_query;
	private TextView tv_pro_add;
	private final int MODIFY = 1;
	private Intent intent;
	private int type;
	private LinearLayout linear_cropname, linear_agriculrualname;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view2, view1;
	private Dialog loadingDialog;
	private boolean isQueryy;
	private List<Object> listAll;
	private EditText et_planName, et_agriName, et_cropName, et_planDrawPers
			;
	private TextView et_startDrawDate, et_endDrawDate, et_predStarMinDate,
			et_predStarMaxDate;
	private int pos = 0;
	private final int CROPTYPENAME = 0;
	private String dicCode;


	//	private ProplantableAdapter updateCostequationAdapter;
	private List<CustomCostListInfo> customCostListInfoList,chargeListInfoList;
//	private ListView listview_costName1 ;

	private String dicId ;
	private int typpe ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_proplantable);
		intent = getIntent();
		if (intent != null) {
			type = intent.getIntExtra("TYPE", -1);
			typpe =  type ;
		}
		init();
		setListener();
		/*getQueryProductionPlanStatisticsPage(false);*/



	}

	@Override
	public void init() {

//		listview_costName1 =(ListView)findViewById(R.id.listview_costName1);

		loadingDialog = DialogUtils.requestDialog(this);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		btn_query = (Button) findViewById(R.id.btn_query);
		tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_agriculrualname = (LinearLayout) findViewById(R.id.linear_agriculrualname);

		et_planName = (EditText) findViewById(R.id.et_planName);
		et_agriName = (EditText) findViewById(R.id.et_agriName);
		et_cropName = (EditText) findViewById(R.id.et_cropName);
		et_planDrawPers = (EditText) findViewById(R.id.et_planDrawPers);
		et_startDrawDate = (TextView) findViewById(R.id.et_startDrawDate);
		et_endDrawDate = (TextView) findViewById(R.id.et_endDrawDate);
		et_predStarMinDate = (TextView) findViewById(R.id.et_predStarMinDate);
		et_predStarMaxDate = (TextView) findViewById(R.id.et_predStarMaxDate);

		if (type == 0) {
			linear_cropname.setVisibility(View.VISIBLE);
			linear_agriculrualname.setVisibility(View.GONE);

			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
		} else {
			linear_cropname.setVisibility(View.GONE);
			linear_agriculrualname.setVisibility(View.VISIBLE);

			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
		}

	}

	@Override
	public void setListener() {
		// 跳转界面监听
		IntentViewListener intentViewListener = new IntentViewListener();
		btn_query.setOnClickListener(intentViewListener);
		tv_pro_add.setOnClickListener(intentViewListener);
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);
		et_agriName.setOnClickListener(intentViewListener);
		et_cropName.setOnClickListener(intentViewListener);
		et_startDrawDate.setOnClickListener(intentViewListener);
		et_endDrawDate.setOnClickListener(intentViewListener);
		et_predStarMinDate.setOnClickListener(intentViewListener);
		et_predStarMaxDate.setOnClickListener(intentViewListener);

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_query:

					getQueryProductionPlanStatisticsPage(true);
					break;
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					finish();
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.et_agriName:
					Intent i = new Intent();
					i.setClass(ProplantableActivity.this, CropTypeNameAc.class);
					i.putExtra("fromWhichView", 5);
					startActivityForResult(i, CROPTYPENAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.et_cropName:
					Intent inten = new Intent();
					inten.setClass(ProplantableActivity.this, CropTypeNameAc.class);
					inten.putExtra("fromWhichView", 5);
					startActivityForResult(inten, CROPTYPENAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
			/*
			 * , , ,
			 * ;
			 */
				case R.id.et_startDrawDate:
					InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					immm.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplantableActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
								et_startDrawDate.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
							}
						}
					};
					break;
				case R.id.et_endDrawDate:
					InputMethodManager a = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					a.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplantableActivity.this, "",
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
					InputMethodManager b = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					b.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplantableActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
								et_predStarMinDate.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
							}
						}
					};
					break;
				case R.id.et_predStarMaxDate:
					InputMethodManager c = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					c.hideSoftInputFromWindow(btn_query.getWindowToken(), 0);
					new SelectDateTimePopWin(ProplantableActivity.this, "",
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
				case CROPTYPENAME:
					String cropType = data.getStringExtra("cropType");
					if (type == 1) {
						et_agriName.setText(cropType);
					} else {
						et_cropName.setText(cropType);
					}
					dicCode = data.getStringExtra("dicCode");
					dicId = data.getStringExtra("dicId");
					break;
				case MODIFY:

					Bundle bundle = data.getExtras();
					typpe = bundle.getInt("type");

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

	public void getQueryProductionPlanStatisticsPage(boolean isQuery) {
		isQueryy = isQuery;
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
			params.put("pvo.planType",typpe);
			if (isQuery == true) {
				if (type == 1) {
					if (!"".equals(et_agriName.getText().toString())&&!"null".equals(et_agriName.getText().toString())) {
						params.put("pvo.agriName", et_agriName.getText().toString());

					} else {
						params.put("pvo.agriName","");

					}
				} else {
					if (!"".equals(et_agriName.getText().toString())&&!"null".equals(et_agriName.getText().toString())) {
						params.put("pvo.agriName", et_agriName.getText().toString());

					} else {
						params.put("pvo.agriName","");
					}
				}
				if (!"".equals(dicId)&&!"null".equals(dicId)&&dicId!=null) {

					params.put("pvo.agriId",dicId);
				} else {
					params.put("pvo.agriId","");

				}
				if (!"".equals(et_planName.getText().toString())&&!"null".equals(et_planName.getText().toString())) {
					params.put("pvo.planName", et_planName.getText().toString());

				} else {
					params.put("pvo.planName","");

				}
				if (!"".equals(et_predStarMinDate.getText()
						.toString())&&!"null".equals(et_predStarMinDate.getText()
						.toString())) {
					params.put("pvo.predStarMinDate", et_predStarMinDate.getText()
							.toString()+ " " + DateUtils.getTimeShort());

				} else {
					params.put("pvo.predStarMinDate","");

				}
				if (!"".equals(et_predStarMaxDate.getText()
						.toString())&&!"null".equals(et_predStarMaxDate.getText()
						.toString())) {
					params.put("pvo.predStarMaxDate", et_predStarMaxDate.getText()
							.toString()+ " " + DateUtils.getTimeShort());

				} else {
					params.put("pvo.predStarMaxDate","");

				}
				if (!"".equals(et_startDrawDate.getText()
						.toString())&&!"null".equals(et_startDrawDate.getText()
						.toString())) {
					params.put("pvo.startDrawDate", et_startDrawDate.getText()
							.toString()+ " " + DateUtils.getTimeShort());

				} else {
					params.put("pvo.startDrawDate","");

				}
				if (!"".equals(et_endDrawDate.getText()
						.toString())&&!"null".equals(et_endDrawDate.getText()
						.toString())) {
					params.put("pvo.endDrawDate", et_endDrawDate.getText()
							.toString());

				} else {
					params.put("pvo.endDrawDate","");

				}
				if (!"".equals(et_planDrawPers.getText().toString())&&!"null".equals(et_planDrawPers.getText().toString())) {

					params.put("pvo.planDrawPers", PreferencesUtils.getString(
							getApplicationContext(), Constant.USERNAME));
					params.put("pvo.planDrawPersId", PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
				} else {
					params.put("pvo.planDrawPers", "");
					params.put("pvo.planDrawPersId","");
				}
			}

			TwitterRestClient.get(Constant.QUERYPRODUCTIONPLANSTATISTICSPAGE,
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
								if ("success".equals(JSONUtils
										.getResultMsg(response))) {
									listAll = JSONUtils
											.getQueryProductionPlanStatisticsPageInfo(
													response, type);
									ServiceNameHandler.allList.clear();
									for (int i = 0; i <listAll.size(); i++) {
										ServiceNameHandler.allList.add(listAll.get(i));
									}
								/*	customCostListInfoList = JSONUtils
											.getListPlanInfoCostValue(response);
									ServiceNameHandler.customCostListInfoList.clear();
									for (int i = 0; i <customCostListInfoList.size(); i++) {
										ServiceNameHandler.customCostListInfoList.add(customCostListInfoList.get(i));
									}
									*/
									chargeListInfoList = JSONUtils
											.getListPlanInfoCostValue_queryProplan(response);
									ServiceNameHandler.customCostListInfoList.clear();
									for (int j = 0; j <chargeListInfoList.size(); j++) {
										ServiceNameHandler.customCostListInfoList.add(chargeListInfoList.get(j));
									}
											/*if (customCostListInfoList!=null&&customCostListInfoList.size()>0) {
												setListAll(customCostListInfoList);
											}*/

									if (isQueryy == true ) {
										if (listAll.size()>0) {
											Intent i = new Intent();
											i.setClass(ProplantableActivity.this,
													QueryProplantableAc.class);
											Bundle bundle = new Bundle();
											bundle.putInt("TYPE", type);
											bundle.putInt("pos", pos);
											bundle.putSerializable(
													"productionPlanStatisticsInfo",
													(ProductionPlanStatisticsInfo) listAll
															.get(pos));
													/*bundle.putSerializable(
															"customCostListInfo",
															(CustomCostListInfo) customCostListInfoList
															.get(pos));*/
											i.putExtras(bundle);
											startActivityForResult(i, MODIFY);
											overridePendingTransition(
													R.anim.zoomout, R.anim.zoomin);
										} else {
											ToastUtils.show(getApplicationContext(), "没有符合条件的数据");
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
	public void setListAll(List<CustomCostListInfo> customCostListInfoList){

//		updateCostequationAdapter = new ProplantableAdapter(customCostListInfoList, getApplicationContext());
//		listview_costName1.setAdapter(updateCostequationAdapter);

	}
}
