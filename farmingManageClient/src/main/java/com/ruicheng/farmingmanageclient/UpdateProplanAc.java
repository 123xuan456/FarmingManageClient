package com.ruicheng.farmingmanageclient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.PlanListInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
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
 * 更改生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class UpdateProplanAc extends BaseActivity implements OnClickListener {

	private LinearLayout linear_agricuturalname, linear_cropname,
			linear_zhongzhiliang, linear_jiagongliang;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view1, view2, view3, view4;
	private Dialog loadingDialog;
	private EditText  et_planName, et_cropName, et_planAmou,
			et_allPlanAmou, et_planDrawPers, et_predStarDate, et_predEndDate,
			et_planDrawDate, et_planType;
	private Button btn_confirm;
	private int type;
	private Bundle bundle;
	private PlanListInfo planListInfo;
	private TextView et_agriName,tv_title;
	private final int CROPTYPENAME = 0;
	private String dicCode,dicId;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updateproplan);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			type = bundle.getInt("type", -1);
			planListInfo = (PlanListInfo) bundle
					.getSerializable("planListInfo");
		}
		init();
		setListener();

	}
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
	public void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		if (type == 1) {
			tv_title.setText("加工生产计划修改 ");
		} else {
			tv_title.setText("种植生产计划修改 ");
		}

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

		et_agriName = (TextView) findViewById(R.id.et_agriName);
		et_planName = (EditText) findViewById(R.id.et_planName);
		et_cropName = (EditText) findViewById(R.id.et_cropName);
		et_planAmou = (EditText) findViewById(R.id.et_planAmou);
		et_allPlanAmou = (EditText) findViewById(R.id.et_allplanAmou);
		et_planDrawPers = (EditText) findViewById(R.id.et_planDrawPers);
		et_predStarDate = (EditText) findViewById(R.id.et_predStarDate);
		et_predEndDate = (EditText) findViewById(R.id.et_predEndDate);
		et_planDrawDate = (EditText) findViewById(R.id.et_planDrawDate);
		et_planType = (EditText) findViewById(R.id.et_planType);

		et_planName.setText(planListInfo.getPlanName());
		if (type == 1) {

			linear_agricuturalname.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_zhongzhiliang.setVisibility(View.GONE);
			linear_jiagongliang.setVisibility(View.VISIBLE);

			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);


			et_agriName.setText(planListInfo.getAgriName());
			et_allPlanAmou.setText(planListInfo.getPlanAmou());
			et_planType.setText("加工类");
		} else {

			linear_agricuturalname.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_zhongzhiliang.setVisibility(View.VISIBLE);
			linear_jiagongliang.setVisibility(View.GONE);

			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);

			et_planAmou.setText(planListInfo.getPlanAmou());
			et_cropName.setText(planListInfo.getPlanName());
			et_planType.setText("种植类");
		}
		et_planDrawPers.setText(planListInfo.getPlanDrawPers());
		et_predStarDate.setText(planListInfo.getPredStarDate());
		et_predEndDate.setText(planListInfo.getPredEndDate());
		et_planDrawDate.setText(DateUtils.strDateFormatSh(planListInfo
				.getPlanDrawDate()));

		ImageView_Linearlayout_Back.setOnClickListener(
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

		btn_confirm.setOnClickListener(this);
		et_predStarDate.setOnClickListener(this);
		et_predEndDate.setOnClickListener(this);
		et_planDrawDate.setOnClickListener(this);
		et_agriName.setOnClickListener(this);
		et_cropName.setOnClickListener(this);
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
		if (et_predEndDate.getText().toString()==null||"null".equals(et_predEndDate.getText().toString())||"".equals(et_predEndDate.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "预计结束时间不能为空");
			return false ;
		}
		if (type == 1) {
			if (et_agriName.getText().toString()==null||"null".equals(et_agriName.getText().toString())||"".equals(et_agriName.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农产品名称不能为空");
				return false ;
			}
			if (et_allPlanAmou.getText().toString()==null||"null".equals(et_allPlanAmou.getText().toString())||"".equals(et_allPlanAmou.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "加工量不能为空");
				return false ;
			}
		}else{
			if (et_planAmou.getText().toString()==null||"null".equals(et_planAmou.getText().toString())||"".equals(et_planAmou.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "种植量不能为空");
				return false ;
			}
			if (et_cropName.getText().toString()==null||"null".equals(et_cropName.getText().toString())||"".equals(et_cropName.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "作物名称不能为空");
				return false ;
			}
		}
		return true ;
	}
	public void getUpdateProductionPlanPageForProcess() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
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
			params.put("planId", planListInfo.getPlanId());
			params.put("planType", planListInfo.getPlanType());


			params.put("plan.planName", et_planName.getText().toString());
			params.put("plan.planType", planListInfo.getPlanType());

			params.put("plan.predStarDate", et_predStarDate.getText()
					.toString() + " " + DateUtils.getTimeShort());
			params.put("plan.predEndDate", et_predEndDate.getText().toString()
					+ " " + DateUtils.getTimeShort());
			if ("1".equals(planListInfo.getPlanType())) {
				params.put("plan.planAmou", et_allPlanAmou.getText().toString());
				if (!"null".equals(et_agriName.getText().toString())&&!"".equals(et_agriName.getText().toString())) {
					params.put("plan.agriName", et_agriName.getText().toString());
					if (dicId!=null&&!"null".equals(dicId)&&!"".equals(dicId)) {
						params.put("plan.agriId",dicId);
					} else {
						params.put("plan.agriId",planListInfo.getAgriId());
					}
				} else {
					params.put("plan.agriName","");

					params.put("plan.agriId","");
				}
			} else {
				params.put("plan.planAmou", et_planAmou.getText().toString());
				if (!"null".equals(et_cropName.getText().toString())&&!"".equals(et_cropName.getText().toString())) {
					params.put("plan.agriName", et_cropName.getText().toString());
					if (dicId!=null&&!"null".equals(dicId)&&!"".equals(dicId)) {
						params.put("plan.agriId",dicId);
					} else {
						params.put("plan.agriId",planListInfo.getAgriId());
					}
				} else {
					params.put("plan.agriName","");

					params.put("plan.agriId","");
				}
			}
			params.put("plan.planDrawPers", et_planDrawPers.getText()
					.toString());
			params.put("plan.planDrawPersId", planListInfo.getPlanDrawPersId());
			params.put("plan.planDrawDate", et_planDrawDate.getText()
					.toString() + " " + DateUtils.getTimeShort());

			TwitterRestClient.get(Constant.DOUPDATEPRODUCTIONPLAN, params,
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
							ToastUtils.show(getApplicationContext(), "修改失败");
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_confirm:

				getUpdateProductionPlanPageForProcess();

				break;
			case R.id.et_predStarDate:
				InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				immm.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateProplanAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						et_predStarDate.setText(date);
					}
				};
				break;
			case R.id.et_predEndDate:
				InputMethodManager a = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				a.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateProplanAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						et_predEndDate.setText(date);
					}
				};
				break;
			case R.id.et_planDrawDate:
				InputMethodManager b = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				b.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateProplanAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						et_planDrawDate.setText(date);
					}
				};
				break;
			case R.id.et_agriName:
				Intent i = new Intent();
				i.setClass(UpdateProplanAc.this, CropTypeNameAc.class);
				i.putExtra("fromWhichView", 4);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.et_cropName:
				Intent intent = new Intent();
				intent.setClass(UpdateProplanAc.this, CropTypeNameAc.class);
				intent.putExtra("fromWhichView", 4);
				startActivityForResult(intent, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			default:
				break;
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
				default:
					break;
			}
		}
	}
}
