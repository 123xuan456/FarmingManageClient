package com.ruicheng.farmingmanageclient;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.UpdateCostequationAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.ChargeListInfo;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.MinAndMaxInfo;
import com.ruicheng.farmingmanageclient.bean.PlanFromulaListInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

/**
 * 成本公式管理修改界面
 *
 * @author zhaobeibei
 *
 */
public class UpdateCostequationAc extends BaseActivity implements OnClickListener{

	private Button btn_save;
	private TextView tv_title, tv_cropType, tv_agriName, tv_formType;
	private Bundle bundle;
	private PlanFromulaListInfo planFromulaListInfo;
	private Intent intent;
	private Dialog loadingDialog;
	private LinearLayout linear_argricuturalname, linear_cropname,
			linear_processcost, linear_seedcost, linear_caizhaicost,
			linear_huafeicost, linear_rengongcost, linear_baozhuangcost;
	private LinearLayout imageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view1, view2, view3, view4, view5, view6, view7, view8;
	private EditText et_minProcCost, et_maxProcCost, et_minSeedCost,
			et_maxSeedCost, et_minPickCost, et_maxPickCost, et_minChemCost,
			et_maxChemCost, et_minManuCost, et_maxManuCost, et_minPackCost,
			et_maxPackCost, et_minPackNum, et_maxPackNum, et_minShorTranCost,
			et_maxShorTranCost, et_minLongTranCost, et_maxLongTranCost,
			et_minPrecCost, et_maxPrecCost, et_minPackMateCost,
			et_maxPackMateCost;

	private TextView tv_costName1;
	private View view_costName1, view_costName2, view_costName3,
			view_costName4, view_costName5, view_costName6, view_costName7,
			view_costName8;

	private UpdateCostequationAdapter updateCostequationAdapter;
	private List<CustomCostListInfo> customCostListInfoList ;
//	private ListView listview_costName1 ;

	private List<ChargeListInfo> chargeListInfoList ;
	private String planInfoId ,minValue,maxValue ;
	private LinearLayout mLinearLayout;
	private int mScreenWidth;
	private MinAndMaxInfo minAndMaxInfo ;
	private String cid ;
	private int num =0;
	private EditText et_minValue ;
	private EditText et_maxValue ;
	private LinearLayout linearLayout ;
	private StringBuffer minValueBuffer = new StringBuffer();
	private StringBuffer maxValueBuffer = new StringBuffer();
	private StringBuffer cidBuffer = new StringBuffer();
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updatecostequation);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			planFromulaListInfo = (PlanFromulaListInfo) bundle
					.getSerializable("planFromulaListInfo");
		}
		init();
		setListener();
		getUpdatePlanFromulaPage();

	}
	public void getUpdatePlanFromulaPage() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("formId",planFromulaListInfo.getFormId());
			params.put("planType",planFromulaListInfo.getFormType());
			TwitterRestClient.get(Constant.UPDATEFROMULASETTINGPAGE, params,
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
									customCostListInfoList = JSONUtils
											.getQueryFromulaSettingPages_CustomCost(response);

									chargeListInfoList = JSONUtils
											.getQueryFromulaSettingPages_ChargeList(response);
									ServiceNameHandler.minAndMaxInfoList.clear();
									for (int i = 0; i <customCostListInfoList.size(); i++) {
										num = i ;
										linearLayout = new LinearLayout(
												UpdateCostequationAc.this);
										linearLayout
												.setOrientation(LinearLayout.HORIZONTAL);
										TextView tv_chargesCollectable = new TextView(
												UpdateCostequationAc.this);
										tv_chargesCollectable.setText(customCostListInfoList.get(i).getCostName()+":");
										tv_chargesCollectable.setTextColor(Color.BLACK);
										tv_chargesCollectable.setWidth(mScreenWidth/9);
										tv_chargesCollectable.setGravity(Gravity.RIGHT);
										tv_chargesCollectable.setTextSize(11);


										et_minValue= new EditText(
												UpdateCostequationAc.this);
										et_minValue.setTextColor(Color.BLACK);
										et_minValue.setTextSize(11);
										et_minValue.setBackgroundResource(R.drawable.login_editext_background);
										et_minValue.setText(customCostListInfoList.get(i).getMinValue());
										et_minValue.setWidth(mScreenWidth/9*3);
										et_minValue.setInputType(InputType.TYPE_CLASS_TEXT);
										et_minValue.setHeight(63);
										et_minValue.setGravity(Gravity.CENTER);


										et_minValue.setOnFocusChangeListener(new OnFocusChangeListener() {
											@Override
											public void onFocusChange(View v, boolean hasFocus) {

												if (!hasFocus) {
													if ("".equals(et_minValue.getText().toString())) {
														ToastUtils.show(getApplicationContext(), "最小值不能为空");
														return ;
													}
													if (Double.valueOf(et_minValue.getText().toString())<0) {
														ToastUtils.show(getApplicationContext(), "最小值不能小于0");
														et_minValue.setText("");
														return ;
													}
												}
											}
										});


										TextView tv_seperate = new TextView(
												UpdateCostequationAc.this);
										tv_seperate.setText("--");
										tv_seperate.setTextColor(Color.BLACK);
										tv_seperate.setHeight(9);
										tv_seperate.setWidth(5);

										et_maxValue= new EditText(
												UpdateCostequationAc.this);
										et_maxValue.setTextColor(Color.BLACK);
										et_maxValue.setTextSize(12);
										et_maxValue.setBackgroundResource(R.drawable.login_editext_background);;
										et_maxValue.setText(customCostListInfoList.get(i).getMaxValue());
										et_maxValue.setWidth(mScreenWidth/9*3);
										et_maxValue.setInputType(InputType.TYPE_CLASS_TEXT);
										et_maxValue.setHeight(63);
										et_maxValue.setGravity(Gravity.CENTER);

										et_maxValue.setOnFocusChangeListener(new OnFocusChangeListener() {
											@Override
											public void onFocusChange(View v, boolean hasFocus) {

												if (!hasFocus) {
													if ("".equals(et_maxValue.getText().toString())) {
														ToastUtils.show(getApplicationContext(), "最小值不能为空");
														return ;
													}
													if ("".equals(et_maxValue.getText().toString())&&!"".equals(et_minValue.getText().toString())) {
														ToastUtils.show(getApplicationContext(),customCostListInfoList.get(num).getCostName()+"设定最小值，必须填写最大值");
														return ;
													}
													if (Double.valueOf(et_maxValue.getText().toString())<Double.valueOf(et_minValue.getText().toString())) {
														ToastUtils.show(getApplicationContext(), "最大值不能小于最小值");
														et_maxValue.setText("");
														return ;
													}
												}
											}
										});


										TextView tv_cuName = new TextView(
												UpdateCostequationAc.this);
										tv_cuName.setTextColor(Color.BLACK);
										tv_cuName.setTextSize(13);
										tv_cuName.setWidth(mScreenWidth/9);
										for (int j = 0; j <chargeListInfoList.size(); j++) {
											if (customCostListInfoList.get(i).getCuCode().equals(chargeListInfoList.get(j).getCuCode())) {
												tv_cuName.setText("("+chargeListInfoList.get(j).getCuName()+")");
											}
										}
										LinearLayout.LayoutParams lParams = new LayoutParams(
												LinearLayout.LayoutParams.WRAP_CONTENT,
												LinearLayout.LayoutParams.WRAP_CONTENT);
										lParams.setMargins(3, 5, 3, 5);
										linearLayout.addView(tv_chargesCollectable, lParams);
										linearLayout.addView(et_minValue, lParams);
										linearLayout.addView(tv_seperate, lParams);
										linearLayout.addView(et_maxValue, lParams);
										linearLayout.addView(tv_cuName, lParams);

										//将两个控件和cid值分别封装到同一个对象中
										minAndMaxInfo = new MinAndMaxInfo();

										minAndMaxInfo.setMinValue(et_minValue);
										minAndMaxInfo.setMaxValue(et_maxValue);
										minAndMaxInfo.setCid(customCostListInfoList.get(num).getPlanInfoId());

										ServiceNameHandler.minAndMaxInfoList.add(minAndMaxInfo);


										mLinearLayout.addView(linearLayout,
												mScreenWidth, 100);
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
	public void setListAll(List<CustomCostListInfo> customCostListInfoList,List<ChargeListInfo> chargeListInfoList){

//		updateCostequationAdapter = new UpdateCostequationAdapter(customCostListInfoList, getApplicationContext(),chargeListInfoList);
//		listview_costName1.setAdapter(updateCostequationAdapter);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void init() {

//		listview_costName1 =(ListView)findViewById(R.id.listview_costName1);

		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

		btn_save = (Button) findViewById(R.id.btn_save);
		tv_title = (TextView) findViewById(R.id.tv_title);
		if ("1".equals(planFromulaListInfo.getFormType())) {
			tv_title.setText("修改加工类型");
		} else {
			tv_title.setText("修改种植类型");
		}

		tv_cropType = (TextView) findViewById(R.id.tv_cropType);
		tv_agriName = (TextView) findViewById(R.id.tv_agriName);
		tv_formType = (TextView) findViewById(R.id.tv_formType);
		et_minProcCost = (EditText) findViewById(R.id.et_minProcCost);
		et_maxProcCost = (EditText) findViewById(R.id.et_maxProcCost);
		et_minSeedCost = (EditText) findViewById(R.id.et_minSeedCost);
		et_maxSeedCost = (EditText) findViewById(R.id.et_maxSeedCost);
		et_minPickCost = (EditText) findViewById(R.id.et_minPickCost);
		et_maxPickCost = (EditText) findViewById(R.id.et_maxPickCost);
		et_minChemCost = (EditText) findViewById(R.id.et_minChemCost);
		et_maxChemCost = (EditText) findViewById(R.id.et_maxChemCost);
		et_minManuCost = (EditText) findViewById(R.id.et_minManuCost);
		et_maxManuCost = (EditText) findViewById(R.id.et_maxManuCost);
		et_minPackCost = (EditText) findViewById(R.id.et_minPackCost);
		et_maxPackCost = (EditText) findViewById(R.id.et_maxPackCost);
		et_minPackNum = (EditText) findViewById(R.id.et_minPackNum);
		et_maxPackNum = (EditText) findViewById(R.id.et_maxPackNum);
		et_minShorTranCost = (EditText) findViewById(R.id.et_minShorTranCost);
		et_maxShorTranCost = (EditText) findViewById(R.id.et_maxShorTranCost);
		et_minLongTranCost = (EditText) findViewById(R.id.et_minLongTranCost);
		et_maxLongTranCost = (EditText) findViewById(R.id.et_maxLongTranCost);
//		et_minPackagingServiceCost = (EditText) findViewById(R.id.et_minPackagingServiceCost);
//		et_maxPackagingServiceCost = (EditText) findViewById(R.id.et_maxPackagingServiceCost);
		et_minPrecCost = (EditText) findViewById(R.id.et_minPrecCost);
		et_maxPrecCost = (EditText) findViewById(R.id.et_maxPrecCost);
		et_minPackMateCost = (EditText) findViewById(R.id.et_minPackMateCost);
		et_maxPackMateCost = (EditText) findViewById(R.id.et_maxPackMateCost);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
		view8 = findViewById(R.id.view8);

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		imageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.imageView_Linearlayout_Back);
		linear_argricuturalname = (LinearLayout) findViewById(R.id.linear_argricuturalname);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_processcost = (LinearLayout) findViewById(R.id.linear_processcost);
		linear_seedcost = (LinearLayout) findViewById(R.id.linear_seedcost);
		linear_caizhaicost = (LinearLayout) findViewById(R.id.linear_caizhaicost);
		linear_huafeicost = (LinearLayout) findViewById(R.id.linear_huafeicost);
		linear_rengongcost = (LinearLayout) findViewById(R.id.linear_rengongcost);
		linear_baozhuangcost = (LinearLayout) findViewById(R.id.linear_baozhuangcost);

		// 自定义

		if ("1".equals(planFromulaListInfo.getFormType())) {
			linear_argricuturalname.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_processcost.setVisibility(View.VISIBLE);
			linear_seedcost.setVisibility(View.GONE);
			linear_caizhaicost.setVisibility(View.GONE);
			linear_huafeicost.setVisibility(View.GONE);
			linear_rengongcost.setVisibility(View.VISIBLE);
			linear_baozhuangcost.setVisibility(View.VISIBLE);


			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
			view5.setVisibility(View.GONE);
			view6.setVisibility(View.GONE);
			view7.setVisibility(View.VISIBLE);
			view8.setVisibility(View.VISIBLE);

			tv_cropType.setText(planFromulaListInfo.getAgriName());
			tv_formType.setText("加工类");

			et_minProcCost.setText(planFromulaListInfo.getMinProcCost());
			et_maxProcCost.setText(planFromulaListInfo.getMaxProcCost());
			et_minManuCost.setText(planFromulaListInfo.getMinManuCost());
			et_maxManuCost.setText(planFromulaListInfo.getMinManuCost());
			et_minPackCost.setText(planFromulaListInfo.getMinPackCost());
			et_maxPackCost.setText(planFromulaListInfo.getMinPackCost());
			et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_maxPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_minShorTranCost
					.setText(planFromulaListInfo.getMinShorTranCost());
			et_maxShorTranCost
					.setText(planFromulaListInfo.getMaxShorTranCost());
			et_minLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_maxLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_minPrecCost.setText(planFromulaListInfo.getMinPrecCost());
			et_maxPrecCost.setText(planFromulaListInfo.getMaxPrecCost());
			et_minPackMateCost
					.setText(planFromulaListInfo.getMinPackMateCost());
			et_maxPackMateCost
					.setText(planFromulaListInfo.getMaxPackMateCost());

		} else {
			tv_agriName.setText(planFromulaListInfo.getAgriName());
			tv_formType.setText("种植类");
			linear_argricuturalname.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_processcost.setVisibility(View.GONE);
			linear_seedcost.setVisibility(View.VISIBLE);
			linear_caizhaicost.setVisibility(View.VISIBLE);
			linear_huafeicost.setVisibility(View.VISIBLE);
			linear_rengongcost.setVisibility(View.GONE);
			linear_baozhuangcost.setVisibility(View.GONE);

			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);
			view5.setVisibility(View.VISIBLE);
			view6.setVisibility(View.VISIBLE);
			view7.setVisibility(View.GONE);
			view8.setVisibility(View.GONE);


			et_minSeedCost.setText(planFromulaListInfo.getMinSeedCost());
			et_maxSeedCost.setText(planFromulaListInfo.getMinSeedCost());
			et_minChemCost.setText(planFromulaListInfo.getMinChemCost());
			et_maxChemCost.setText(planFromulaListInfo.getMinChemCost());
			et_minPickCost.setText(planFromulaListInfo.getMinPickCost());
			et_maxPickCost.setText(planFromulaListInfo.getMinPickCost());
			et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_maxPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_minShorTranCost
					.setText(planFromulaListInfo.getMinShorTranCost());
			et_maxShorTranCost
					.setText(planFromulaListInfo.getMinShorTranCost());
			et_minLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_maxLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_minPrecCost.setText(planFromulaListInfo.getMinPrecCost());
			et_maxPrecCost.setText(planFromulaListInfo.getMaxPrecCost());
			et_minPackMateCost
					.setText(planFromulaListInfo.getMinPackMateCost());
			et_maxPackMateCost
					.setText(planFromulaListInfo.getMaxPackMateCost());
		}
	}

	@Override
	public void setListener() {
		btn_save.setOnClickListener(this);
		loadingDialog = DialogUtils.requestDialog(this);
		imageView_Linearlayout_Back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getDoUpdateFromulaSetting();
				break;
			case R.id.imageView_Linearlayout_Back:
				img_comment_back.setVisibility(View.GONE);
				Intent i = new Intent();
				i.setClass(UpdateCostequationAc.this, CostequationActivity.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			default:
				break;
		}
	}

	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils() {
		if ("0".equals(planFromulaListInfo.getFormType())) {
			if (tv_agriName.getText().toString() == null
					|| "null".equals(tv_agriName.getText().toString())
					|| "".equals(tv_agriName.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "作物名称不能为空");
				return false;
			}
			if (!"".equals(et_minSeedCost.getText().toString())&&Double.valueOf(et_minSeedCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "种子费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxSeedCost.getText().toString())&&Double.valueOf(et_maxSeedCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "种子费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minSeedCost.getText().toString())&&"".equals(et_maxSeedCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "种子费用设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxSeedCost.getText().toString())&&!"".equals(et_minSeedCost.getText().toString())&&Double.valueOf(et_minSeedCost.getText().toString())>Double.valueOf(et_maxSeedCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "种子费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minChemCost.getText().toString())&&Double.valueOf(et_minChemCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "化肥费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxChemCost.getText().toString())&&Double.valueOf(et_maxChemCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "化肥费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minChemCost.getText().toString())&&"".equals(et_maxChemCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "化肥费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxChemCost.getText().toString())&&!"".equals(et_minChemCost.getText().toString())&&Double.valueOf(et_minChemCost.getText().toString())>Double.valueOf(et_maxChemCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "化肥费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPickCost.getText().toString())&&Double.valueOf(et_minPickCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "采摘费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPickCost.getText().toString())&&Double.valueOf(et_maxPickCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "采摘费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPickCost.getText().toString())&&"".equals(et_maxPickCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "采摘费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPickCost.getText().toString())&&!"".equals(et_minPickCost.getText().toString())&&Double.valueOf(et_minPickCost.getText().toString())>Double.valueOf(et_maxPickCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "采摘费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPackNum.getText().toString())&&Double.valueOf(et_minPackNum.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装件数最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackNum.getText().toString())&&Double.valueOf(et_maxPackNum.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装件数最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackNum.getText().toString())&&"".equals(et_maxPackNum.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装件数设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPackNum.getText().toString())&&!"".equals(et_minPackNum.getText().toString())&&Double.valueOf(et_minPackNum.getText().toString())>Double.valueOf(et_maxPackNum.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装件数最小值超过最大值");
				return false;
			}
			if (!"".equals(et_maxShorTranCost.getText().toString())&&Double.valueOf(et_maxShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minShorTranCost.getText().toString())&&Double.valueOf(et_minShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_minShorTranCost.getText().toString())&&"".equals(et_maxShorTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "短途运输费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxShorTranCost.getText().toString())&&Double.valueOf(et_maxShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&Double.valueOf(et_minLongTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "长途运输费最大值不能小于最小值");
				return false;
			}
			if (!"".equals(et_maxLongTranCost.getText().toString())&&Double.valueOf(et_maxLongTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "长途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&"".equals(et_maxLongTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "长途运输费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxShorTranCost.getText().toString())&&!"".equals(et_minShorTranCost.getText().toString())&&Double.valueOf(et_minShorTranCost.getText().toString())>Double.valueOf(et_maxShorTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "短途运输费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&!"".equals(et_maxLongTranCost.getText().toString())&&Double.valueOf(et_minLongTranCost.getText().toString())>Double.valueOf(et_maxLongTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "长途运输费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&Double.valueOf(et_minPackMateCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackMateCost.getText().toString())&&Double.valueOf(et_maxPackMateCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&"".equals(et_maxPackMateCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&!"".equals(et_maxPackMateCost.getText().toString())&&Double.valueOf(et_minPackMateCost.getText().toString())>Double.valueOf(et_maxPackMateCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPrecCost.getText().toString())&&Double.valueOf(et_minPrecCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "预冷费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPrecCost.getText().toString())&&Double.valueOf(et_maxPrecCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "预冷费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPrecCost.getText().toString())&&"".equals(et_maxPrecCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "预冷费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPrecCost.getText().toString())&&!"".equals(et_minPrecCost.getText().toString())&&Double.valueOf(et_minPrecCost.getText().toString())>Double.valueOf(et_maxPrecCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "预冷费最小值超过最大值");
				return false;
			}
		} else {
			if (tv_cropType.getText().toString() == null
					|| "null".equals(tv_cropType.getText().toString())
					|| "".equals(tv_cropType.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农产品名称不能为空");
				return false;
			}
			if (!"".equals(et_minProcCost.getText().toString())&&Double.valueOf(et_minProcCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "加工费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxProcCost.getText().toString())&&Double.valueOf(et_maxProcCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "加工费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minProcCost.getText().toString())&&"".equals(et_maxProcCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "加工费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxProcCost.getText().toString())&&!"".equals(et_minProcCost.getText().toString())&&Double.valueOf(et_minProcCost.getText().toString())>Double.valueOf(et_maxProcCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "加工费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minManuCost.getText().toString())&&Double.valueOf(et_minManuCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "人工费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxManuCost.getText().toString())&&Double.valueOf(et_maxManuCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "人工费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minManuCost.getText().toString())&&"".equals(et_maxManuCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "人工费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxManuCost.getText().toString())&&!"".equals(et_minManuCost.getText().toString())&&Double.valueOf(et_minManuCost.getText().toString())>Double.valueOf(et_maxManuCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "人工费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPackCost.getText().toString())&&Double.valueOf(et_minPackCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackCost.getText().toString())&&Double.valueOf(et_maxPackCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackCost.getText().toString())&&"".equals(et_maxPackCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPackCost.getText().toString())&&!"".equals(et_minPackCost.getText().toString())&&Double.valueOf(et_minPackCost.getText().toString())>Double.valueOf(et_maxPackCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPackNum.getText().toString())&&Double.valueOf(et_minPackNum.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装件数最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackNum.getText().toString())&&Double.valueOf(et_maxPackNum.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装件数最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackNum.getText().toString())&&"".equals(et_maxPackNum.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装件数设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPackNum.getText().toString())&&!"".equals(et_minPackNum.getText().toString())&&Double.valueOf(et_minPackNum.getText().toString())>Double.valueOf(et_maxPackNum.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装件数最小值超过最大值");
				return false;
			}

			if (!"".equals(et_maxShorTranCost.getText().toString())&&Double.valueOf(et_maxShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minShorTranCost.getText().toString())&&Double.valueOf(et_minShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_minShorTranCost.getText().toString())&&"".equals(et_maxShorTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "短途运输费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxShorTranCost.getText().toString())&&Double.valueOf(et_maxShorTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "短途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&Double.valueOf(et_minLongTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "长途运输费最大值不能小于最小值");
				return false;
			}
			if (!"".equals(et_maxLongTranCost.getText().toString())&&Double.valueOf(et_maxLongTranCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "长途运输费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&"".equals(et_maxLongTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "长途运输费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxShorTranCost.getText().toString())&&!"".equals(et_minShorTranCost.getText().toString())&&Double.valueOf(et_minShorTranCost.getText().toString())>Double.valueOf(et_maxShorTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "短途运输费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minLongTranCost.getText().toString())&&!"".equals(et_maxLongTranCost.getText().toString())&&Double.valueOf(et_minLongTranCost.getText().toString())>Double.valueOf(et_maxLongTranCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "长途运输费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&Double.valueOf(et_minPackMateCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackMateCost.getText().toString())&&Double.valueOf(et_maxPackMateCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&"".equals(et_maxPackMateCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_minPackMateCost.getText().toString())&&!"".equals(et_maxPackMateCost.getText().toString())&&Double.valueOf(et_minPackMateCost.getText().toString())>Double.valueOf(et_maxPackMateCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值超过最大值");
				return false;
			}
			if (!"".equals(et_minPrecCost.getText().toString())&&Double.valueOf(et_minPrecCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "预冷费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPrecCost.getText().toString())&&Double.valueOf(et_maxPrecCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "预冷费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPrecCost.getText().toString())&&"".equals(et_maxPrecCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "预冷费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_maxPrecCost.getText().toString())&&!"".equals(et_minPrecCost.getText().toString())&&Double.valueOf(et_minPrecCost.getText().toString())>Double.valueOf(et_maxPrecCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "预冷费最小值超过最大值");
				return false;
			}

		}

		return true;
	}

	public void getDoUpdateFromulaSetting() {
		if (!estimateInfoIsNullUtils()) {
			return;
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
			params.put("planType", planFromulaListInfo.getFormType());

			for (int i = 0; i <(num+1); i++) {
				if (ServiceNameHandler.minAndMaxInfoList==null||ServiceNameHandler.minAndMaxInfoList.size()==0) {
					return ;
				}
				//	minAndMaxInfo = (MinAndMaxInfo)linearLayout.getTag();
				minAndMaxInfo = ServiceNameHandler.minAndMaxInfoList.get(i);
				et_minValue = minAndMaxInfo.getMinValue() ;
				et_maxValue = minAndMaxInfo.getMaxValue() ;
				cid = minAndMaxInfo.getCid() ;

				if (!"".equals(et_minValue.getText().toString())&&!"".equals(minValueBuffer.toString())) {
					minValueBuffer.append(",").append(et_minValue.getText().toString());
				}

				if("".equals(et_minValue.getText().toString())&&!"".equals(minValueBuffer.toString())){
					minValueBuffer.append(",").append("0");
				}
				if(!"".equals(et_minValue.getText().toString())&&"".equals(minValueBuffer.toString())){
					minValueBuffer.append(et_minValue.getText().toString());
				}
				if("".equals(et_minValue.getText().toString())&&"".equals(minValueBuffer.toString())){
					minValueBuffer.append("0");
				}

				if (!"".equals(et_maxValue.getText().toString())&&!"".equals(maxValueBuffer.toString())) {
					maxValueBuffer.append(",").append(et_maxValue.getText().toString());
				}
				if("".equals(et_maxValue.getText().toString())&&!"".equals(maxValueBuffer.toString())){
					maxValueBuffer.append(",").append("0");
				}
				if(!"".equals(et_maxValue.getText().toString())&&"".equals(maxValueBuffer.toString())){
					maxValueBuffer.append(et_maxValue.getText().toString());
				}
				if("".equals(et_minValue.getText().toString())&&"".equals(maxValueBuffer.toString())){
					maxValueBuffer.append("0");
				}

				if (!"".equals(cid)&&!"".equals(cidBuffer.toString())) {
					cidBuffer.append(",").append(cid);
				}
				if("".equals(cid)&&!"".equals(cidBuffer.toString())){
					cidBuffer.append(",").append("");
				}
				if(!"".equals(cid)&&"".equals(cidBuffer.toString())){
					cidBuffer.append(cid);
				}
				if("".equals(cid)&&"".equals(cidBuffer.toString())){
					cidBuffer.append("");
				}

			}
			params.put("planInfoId",cidBuffer.toString());
			params.put("minValue", minValueBuffer.toString());
			params.put("maxValue",maxValueBuffer.toString());



			if ("1".equals(planFromulaListInfo.getFormType())) {
				params.put("fromula.formId", planFromulaListInfo.getFormId());
				params.put("fromula.agriName",
						planFromulaListInfo.getAgriName());
				params.put("fromula.agriId", planFromulaListInfo.getAgriId());
				params.put("fromula.formType",
						planFromulaListInfo.getFormType());


				if (!"".equals(et_maxShorTranCost
						.getText().toString())&&!"null".equals(et_maxShorTranCost
						.getText().toString())) {
					params.put("fromula.maxShorTranCost", et_maxShorTranCost
							.getText().toString());
				} else {
					params.put("fromula.maxShorTranCost","0");
				}
				if (!"".equals(et_minShorTranCost
						.getText().toString())&&!"null".equals(et_minShorTranCost
						.getText().toString())) {
					params.put("fromula.minShorTranCost", et_minShorTranCost
							.getText().toString());
				} else {
					params.put("fromula.minShorTranCost","0");
				}
				if (!"".equals(et_maxLongTranCost
						.getText().toString())&&!"null".equals(et_maxLongTranCost
						.getText().toString())) {
					params.put("fromula.maxLongTranCost", et_maxLongTranCost
							.getText().toString());
				} else {
					params.put("fromula.maxLongTranCost","0");
				}
				if (!"".equals(et_minLongTranCost
						.getText().toString())&&!"null".equals(et_minLongTranCost
						.getText().toString())) {
					params.put("fromula.minLongTranCost", et_minLongTranCost
							.getText().toString());
				} else {
					params.put("fromula.minLongTranCost","0");
				}
				if (!"".equals(et_minPackNum.getText()
						.toString())&&!"null".equals(et_minPackNum.getText()
						.toString())) {
					params.put("fromula.minPackNum", et_minPackNum.getText()
							.toString());
				} else {
					params.put("fromula.minPackNum","0");
				}
				if (!"".equals(et_maxPackNum.getText()
						.toString())&&!"null".equals(et_maxPackNum.getText()
						.toString())) {
					params.put("fromula.maxPackNum", et_maxPackNum.getText()
							.toString());
				} else {
					params.put("fromula.maxPackNum","0");
				}
				if (!"".equals(et_maxPackCost.getText()
						.toString())&&!"null".equals(et_maxPackCost.getText()
						.toString())) {
					params.put("fromula.maxPackCost", et_maxPackCost.getText()
							.toString());
				} else {
					params.put("fromula.maxPackCost", "0");
				}
				if (!"".equals(et_minPackCost.getText()
						.toString())&&!"null".equals(et_minPackCost.getText()
						.toString())) {
					params.put("fromula.minPackCost", et_minPackCost.getText()
							.toString());
				} else {
					params.put("fromula.minPackCost","0");
				}
				if (!"".equals(et_maxPrecCost.getText()
						.toString())&&!"null".equals(et_maxPrecCost.getText()
						.toString())) {
					params.put("fromula.maxPrecCost", et_maxPrecCost.getText()
							.toString());
				} else {
					params.put("fromula.maxPrecCost","0");
				}
				if (!"".equals(et_minPrecCost.getText()
						.toString())&&!"null".equals(et_minPrecCost.getText()
						.toString())) {
					params.put("fromula.minPrecCost", et_minPrecCost.getText()
							.toString());
				} else {
					params.put("fromula.minPrecCost","0");
				}
				if (!"".equals(et_maxProcCost.getText()
						.toString())&&!"null".equals(et_maxProcCost.getText()
						.toString())) {
					params.put("fromula.maxProcCost", et_maxProcCost.getText()
							.toString());
				} else {
					params.put("fromula.maxProcCost","0");
				}
				if (!"".equals(et_minProcCost.getText()
						.toString())&&!"null".equals(et_minProcCost.getText()
						.toString())) {

					params.put("fromula.minProcCost", et_minProcCost.getText()
							.toString());
				} else {
					params.put("fromula.minProcCost","0");
				}
				if (!"".equals(et_maxManuCost.getText()
						.toString())&&!"null".equals(et_maxManuCost.getText()
						.toString())) {
					params.put("fromula.maxManuCost", et_maxManuCost.getText()
							.toString());
				} else {
					params.put("fromula.maxManuCost","0");
				}
				if (!"".equals(et_minManuCost.getText()
						.toString())&&!"null".equals(et_minManuCost.getText()
						.toString())) {
					params.put("fromula.minManuCost", et_minManuCost.getText()
							.toString());
				} else {
					params.put("fromula.minManuCost","0");
				}
				if (!"".equals(et_maxPackMateCost
						.getText().toString())&&!"null".equals(et_maxPackMateCost
						.getText().toString())) {

					params.put("fromula.maxPackMateCost", et_maxPackMateCost
							.getText().toString());
				} else {
					params.put("fromula.maxPackMateCost", "0");

				}
				if (!"".equals(et_minPackMateCost
						.getText().toString())&&!"null".equals(et_minPackMateCost
						.getText().toString())) {
					params.put("fromula.minPackMateCost", et_minPackMateCost
							.getText().toString());
				} else {
					params.put("fromula.minPackMateCost","0");
				}
			} else {
				// 种植类型参数
				params.put("fromula.formId", planFromulaListInfo.getFormId());
				params.put("fromula.agriName",
						planFromulaListInfo.getAgriName());
				params.put("fromula.agriId", planFromulaListInfo.getAgriId());
				params.put("fromula.formType",
						planFromulaListInfo.getFormType());
				if (!"".equals(et_minSeedCost.getText()
						.toString())&&!"null".equals(et_minSeedCost.getText()
						.toString())) {
					params.put("fromula.minSeedCost", et_minSeedCost.getText()
							.toString());
				} else {
					params.put("fromula.minSeedCost","0");
				}
				if (!"".equals(et_maxSeedCost.getText()
						.toString())&&!"null".equals(et_maxSeedCost.getText()
						.toString())) {

					params.put("fromula.maxSeedCost", et_maxSeedCost.getText()
							.toString());

				} else {
					params.put("fromula.maxSeedCost", "0");

				}
				if (!"".equals(et_maxChemCost.getText()
						.toString())&&!"null".equals(et_maxChemCost.getText()
						.toString())) {

					params.put("fromula.maxChemCost", et_maxChemCost.getText()
							.toString());
				} else {
					params.put("fromula.maxChemCost","0");

				}
				if (!"".equals(et_minChemCost.getText()
						.toString())&&!"null".equals(et_minChemCost.getText()
						.toString())) {

					params.put("fromula.minChemCost", et_minChemCost.getText()
							.toString());
				} else {
					params.put("fromula.minChemCost","0");

				}
				if (!"".equals(et_maxPickCost.getText()
						.toString())&&!"null".equals(et_maxPickCost.getText()
						.toString())) {
					params.put("fromula.maxPickCost", et_maxPickCost.getText()
							.toString());

				} else {
					params.put("fromula.maxPickCost","0");

				}
				if (!"null".equals(et_minPickCost.getText()
						.toString())&&!"".equals(et_minPickCost.getText()
						.toString())) {
					params.put("fromula.minPickCost", et_minPickCost.getText()
							.toString());

				} else {
					params.put("fromula.minPickCost","0");

				}
				if (!"".equals(et_maxShorTranCost
						.getText().toString())&&!"null".equals(et_maxShorTranCost
						.getText().toString())) {
					params.put("fromula.maxShorTranCost", et_maxShorTranCost
							.getText().toString());

				} else {
					params.put("fromula.maxShorTranCost","0");

				}
				if (!"".equals(et_minShorTranCost
						.getText().toString())&&!"null".equals(et_minShorTranCost
						.getText().toString())) {
					params.put("fromula.minShorTranCost", et_minShorTranCost
							.getText().toString());

				} else {
					params.put("fromula.minShorTranCost","0");

				}
				if (!"".equals(et_maxLongTranCost
						.getText().toString())&&!"null".equals(et_maxLongTranCost
						.getText().toString())) {
					params.put("fromula.maxLongTranCost", et_maxLongTranCost
							.getText().toString());

				} else {
					params.put("fromula.maxLongTranCost","0");

				}
				if (!"".equals(et_minLongTranCost
						.getText().toString())&&!"null".equals(et_minLongTranCost
						.getText().toString())) {

					params.put("fromula.minLongTranCost", et_minLongTranCost
							.getText().toString());
				} else {
					params.put("fromula.minLongTranCost", "0");

				}
				if (!"".equals(et_minPackNum.getText()
						.toString())&&!"null".equals(et_minPackNum.getText()
						.toString())) {
					params.put("fromula.minPackNum", et_minPackNum.getText()
							.toString());

				} else {
					params.put("fromula.minPackNum", "0");

				}
				if (!"".equals(et_maxPackNum.getText()
						.toString())&&!"null".equals(et_maxPackNum.getText()
						.toString())) {
					params.put("fromula.maxPackNum", et_maxPackNum.getText()
							.toString());

				} else {
					params.put("fromula.maxPackNum", "0");

				}
				if (!"".equals(et_maxPrecCost.getText()
						.toString())&&!"null".equals(et_maxPrecCost.getText()
						.toString())) {
					params.put("fromula.maxPrecCost", et_maxPrecCost.getText()
							.toString());

				} else {
					params.put("fromula.maxPrecCost", "0");

				}
				if (!"".equals(et_minPrecCost.getText()
						.toString())&&!"null".equals(et_minPrecCost.getText()
						.toString())) {
					params.put("fromula.minPrecCost", et_minPrecCost.getText()
							.toString());

				} else {
					params.put("fromula.minPrecCost", "0");

				}
				if (!"".equals(et_maxPackMateCost
						.getText().toString())&&!"null".equals(et_maxPackMateCost
						.getText().toString())) {

					params.put("fromula.maxPackMateCost", et_maxPackMateCost
							.getText().toString());
				} else {
					params.put("fromula.maxPackMateCost","0");

				}
				if (!"".equals(et_minPackMateCost
						.getText().toString())&&!"null".equals(et_minPackMateCost
						.getText().toString())) {
					params.put("fromula.minPackMateCost", et_minPackMateCost
							.getText().toString());

				} else {
					params.put("fromula.minPackMateCost","0");
				}
			}
			TwitterRestClient.get(Constant.DOUPDATEFROMULASETTINGPAGE, params,
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
							ToastUtils.show(getApplicationContext(), "修改成功");
							finish();
							overridePendingTransition(R.anim.zoomout,
									R.anim.zoomin);
						}
					});
		}
	}
}
