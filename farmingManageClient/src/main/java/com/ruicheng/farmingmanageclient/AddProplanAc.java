package com.ruicheng.farmingmanageclient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.ruicheng.farmingmanageclient.bean.IsValidInfo;
import com.ruicheng.farmingmanageclient.bean.PlanInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;
/**
 * 添加生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class AddProplanAc extends BaseActivity {

	private Intent intent ;
	private int type ;

	private LinearLayout linear_agricuturalname ,linear_cropname,linear_zhongzhiliang,linear_jiagongliang ;
	private LinearLayout ImageView_Linearlayout_Back ;
	private ImageView img_comment_back ;
	private View view1,view2,view3,view4;
	private Dialog loadingDialog;
	private EditText  et_planName, et_planAmou,
			et_allPlanAmou, et_planDrawDate;
	private Button btn_confirm ;
	private PlanInfo planInfo ;
	private TextView  tv_title,et_agriName,et_planDrawPers,et_planType, et_cropName, et_predStarDate, et_predEndDate;
	private static final int CROPTYPENAME = 1;
	private String dicCode;
	private String agriId;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_addproplan);
		intent = getIntent();
		if (intent !=null) {
			type = intent.getIntExtra("TYPE", -1);
		}
		loadingDialog = DialogUtils.requestDialog(this);
		getAddProductionPlanForProcess();

	}

	@Override
	public void init() {

		btn_confirm = (Button) findViewById(R.id.btn_confirm);

		loadingDialog = DialogUtils.requestDialog(this);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		linear_agricuturalname = (LinearLayout) findViewById(R.id.linear_agricuturalname);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_zhongzhiliang = (LinearLayout) findViewById(R.id.linear_zhongzhiliang);
		linear_jiagongliang = (LinearLayout) findViewById(R.id.linear_jiagongliang);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		tv_title = (TextView) findViewById(R.id.tv_title);
		et_agriName = (TextView) findViewById(R.id.et_agriName);
		et_planName = (EditText)findViewById(R.id.et_planName);
		et_cropName = (TextView)findViewById(R.id.et_cropName);
		et_planAmou = (EditText)findViewById(R.id.et_planAmou);
		et_allPlanAmou = (EditText)findViewById(R.id.et_allPlanAmou);
		et_planDrawPers = (TextView)findViewById(R.id.et_planDrawPers);
		et_predStarDate = (TextView)findViewById(R.id.et_predStarDate);
		et_predEndDate = (TextView)findViewById(R.id.et_predEndDate);
		et_planDrawDate = (EditText)findViewById(R.id.et_planDrawDate);
		et_planType = (TextView)findViewById(R.id.et_planType);
		et_planDrawPers.setText(planInfo.getPlanDrawPers());
		et_planDrawDate.setText(DateUtils.strDateFormatSh(planInfo.getPlanDrawDate()));

		if (type == 0) {
			tv_title.setText("种植生产计划定义");
			et_planType.setText("种植类");

			linear_zhongzhiliang.setVisibility(View.VISIBLE);
			linear_jiagongliang.setVisibility(View.GONE);

			linear_agricuturalname.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);


			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);

		} else {
			tv_title.setText("加工生产计划定义");
			et_planType.setText("加工类");
			linear_agricuturalname.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_zhongzhiliang.setVisibility(View.GONE);
			linear_jiagongliang.setVisibility(View.VISIBLE);

			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 生产计划定义接口
	 *
	 */
	public void getAddProductionPlanForProcess() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("planType",type);
			TwitterRestClient.get(Constant.ADDPRODUCTIONPLANFORPROCESS, params,
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
									planInfo = JSONUtils.getAddProductionPlanForProcess(response);
									init() ;
									setListener() ;
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
	public void setListener() {
		// 跳转界面监听
		IntentViewListener intentViewListener = new IntentViewListener();
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);
		btn_confirm.setOnClickListener(intentViewListener);

		et_agriName.setOnClickListener(intentViewListener);
		et_cropName.setOnClickListener(intentViewListener);
		et_predStarDate.setOnClickListener(intentViewListener);

		et_predEndDate.setOnClickListener(intentViewListener);

	}
	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), ProplanActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					finish();
					break ;
				case R.id.btn_confirm:

					getAjaxQueryIsValidFromula();
					break ;
				case R.id.et_agriName:
					Intent i = new Intent();
					i.setClass(AddProplanAc.this, CropTypeNameAc.class);
					i.putExtra("fromWhichView",7);
					startActivityForResult(i, CROPTYPENAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break ;
				case R.id.et_cropName:
					Intent cropIntent = new Intent();
					cropIntent.setClass(AddProplanAc.this, CropTypeNameAc.class);
					cropIntent.putExtra("fromWhichView",7);
					startActivityForResult(cropIntent, CROPTYPENAME);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break ;
				case R.id.et_predStarDate:
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0);
					new SelectDateTimePopWin(AddProplanAc.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							et_predStarDate.setText(date);
						}
					};
					break ;
				case R.id.et_predEndDate:
					InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					im.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0);
					new SelectDateTimePopWin(AddProplanAc.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(et_predStarDate.getText().toString()).getTime()>0) {
								et_predEndDate.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
							}
						}
					};
					break ;
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
					if (type == 1) {
						// 农产品名称数据
						String cropType = data.getStringExtra("cropType");
						et_agriName.setText(cropType);
					} else {
						// 作物名称数据
						String cropType = data.getStringExtra("cropType");
						et_cropName.setText(cropType);
					}
					dicCode = data.getStringExtra("dicCode");
					agriId = data.getStringExtra("dicId");
					break;
				default:
					break;
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode ==event.KEYCODE_BACK) {
			/*android.os.Process.killProcess(android.os.Process.myPid()); */
			startActivity(new Intent(this, ProplanActivity.class));
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){
		if (et_planName.getText().toString()==null||"null".equals(et_planName.getText().toString())||"".equals(et_planName.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "计划名称不能为空");
			return false ;
		}
		if (et_predStarDate.getText().toString()==null||"null".equals(et_predStarDate.getText().toString())||"".equals(et_predStarDate.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "预计开始时间不能为空");
			return false ;
		}
		if (et_predEndDate.getText().toString()==null||"null".equals(et_predEndDate.getText().toString())||"".equals(et_predStarDate.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "预计结束时间不能为空");
			return false ;
		}
		if (type ==0) {
			if (et_cropName.getText().toString()==null||"null".equals(et_cropName.getText().toString())||"".equals(et_cropName.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "作物名称不能为空");
				return false ;
			}
			if (et_planAmou.getText().toString()==null||"null".equals(et_planAmou.getText().toString())||"".equals(et_planAmou.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "种植量不能为空");
				return false ;
			}

		} else {
			if (et_agriName.getText().toString()==null||"null".equals(et_agriName.getText().toString())||"".equals(et_agriName.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农产品名称不能为空");
				return false ;
			}
			if (et_allPlanAmou.getText().toString()==null||"null".equals(et_allPlanAmou.getText().toString())||"".equals(et_allPlanAmou.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "加工量不能为空");
				return false ;
			}
		}

		return true ;
	}
	public void getSaveProductionPlan() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType","mobile");
			params.put(
					Constant.USERID,
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");
			params.put(Constant.USERNAME, PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
			params.put("planType",type);
			params.put("plan.planName",et_planName.getText().toString());
			params.put("plan.planType",type);
			params.put("plan.planDrawPersId",planInfo.getPlanDrawPersId());
			params.put("plan.planDrawDate",et_planDrawDate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("plan.planDrawPers",et_planDrawPers.getText().toString());
			params.put("plan.predStarDate",et_predStarDate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("plan.predEndDate",et_predEndDate.getText().toString()+" "+DateUtils.getTimeShort());

			if (type == 1) {
				//加工类
				params.put("plan.agriName",et_agriName.getText().toString());
				params.put("plan.planAmou",et_allPlanAmou.getText().toString());
			} else {
				params.put("plan.agriName",et_cropName.getText().toString());

				params.put("plan.planAmou",et_planAmou.getText().toString());

			}

			params.put("plan.agriId",agriId);

			TwitterRestClient.get(Constant.SAVEPRODUCTIONPLAN, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);

							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "保存失败");

						}
						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "保存成功");
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							finish();						}
					});
		} else {
			ToastUtils.show(getApplicationContext(), "网络不佳,请重试...");
		}
	}
	public void getAjaxQueryIsValidFromula() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			/*loadingDialog.show();*/
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("fromula.agriId",agriId);
			params.put("fromula.formType",type);
			TwitterRestClient.get(Constant.AJAXQUERYISVALIDFROMULA, params,
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
							ToastUtils.show(getApplicationContext(), "没有有效的成本公式");
							if (type == 1) {
								ToastUtils.show(getApplicationContext(), "请重新添加农产品名称");
							} else {
								ToastUtils.show(getApplicationContext(), "请重新添加作物名称");
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

									/*Intent intent = new Intent();
									intent.setClass(getApplicationContext(),CustomProductionPlanAc.class);
									Bundle bundle = new Bundle();
									bundle.putString("agriId", agriId);
									bundle.putString("formType", type+"");
									if (type == 1) {
										bundle.putString("planAmou", et_allPlanAmou.getText().toString());
									} else {
										bundle.putString("planAmou", et_planAmou.getText().toString());
									}
									intent.putExtras(bundle);
									startActivity(intent);*/
									//保存方法
									getSaveProductionPlan();
								}else{
									ToastUtils.show(getApplicationContext(), "请输入定义成本公式的农作物");
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
