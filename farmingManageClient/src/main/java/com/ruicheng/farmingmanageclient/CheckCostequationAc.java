package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.ChargeListInfo;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.PlanFromulaListInfo;
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

import java.util.List;

/**
 * 查看加工-种植计划公式界面
 *
 * @author zhaobeibei
 *
 */
public class CheckCostequationAc extends BaseActivity implements
		OnClickListener {

	private TextView tv_title, tv_cropType, tv_agriName, tv_formType;
	private PlanFromulaListInfo planFromulaListInfo;
	private Intent intent;
	private Dialog loadingDialog;
	private LinearLayout imageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private LinearLayout linear_argricuturalname, linear_cropname,
			linear_processcost, linear_seedcost, linear_caizhaicost,
			linear_huafeicost, linear_rengongcost, linear_baozhuangcost;
	private View view1, view2, view3, view4, view5, view6, view7, view8;
	private TextView et_minProcCost, et_maxProcCost, et_minSeedCost,
			et_maxSeedCost, et_minPickCost, et_maxPickCost, et_minChemCost,
			et_maxChemCost, et_minManuCost, et_maxManuCost, et_minPackCost,
			et_maxPackCost, et_minPackNum, et_maxPackNum, et_minShorTranCost,
			et_maxShorTranCost, et_minLongTranCost, et_maxLongTranCost,
			et_minPackagingServiceCost, et_maxPackagingServiceCost,
			et_minPrecCost, et_maxPrecCost, et_minPackMateCost,
			et_maxPackMateCost;
	private Bundle bundle;
	private Button btn_last, btn_next;
	private int pos;

	private List<CustomCostListInfo> customCostListInfoList ;
//	private ListView listview_costName1 ;

	private List<ChargeListInfo> chargeListInfoList ;
//	private UpdateCostequationAdapter updateCostequationAdapter;

	private LinearLayout mLinearLayout;
	private int mScreenWidth;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_checkcostequation);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			planFromulaListInfo = (PlanFromulaListInfo) bundle
					.getSerializable("planFromulaListInfo");
			pos = bundle.getInt("pos", -1);
		}
		init();
		setListener();

		getDoViewPlanFromula();

	}

	@Override
	public void init() {

		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

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


		loadingDialog = DialogUtils.requestDialog(this);
//		listview_costName1 =(ListView)findViewById(R.id.listview_costName1);

		tv_title = (TextView) findViewById(R.id.tv_title);
		if ("1".equals(planFromulaListInfo.getFormType())) {
			tv_title.setText("查看加工计划公式 ");
		} else {
			tv_title.setText("查看种植计划公式");
		}
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		tv_cropType = (TextView) findViewById(R.id.tv_cropType);
		tv_agriName = (TextView) findViewById(R.id.tv_agriName);
		tv_formType = (TextView) findViewById(R.id.tv_formType);
		et_minProcCost = (TextView) findViewById(R.id.et_minProcCost);
		et_maxProcCost = (TextView) findViewById(R.id.et_maxProcCost);
		et_minSeedCost = (TextView) findViewById(R.id.et_minSeedCost);
		et_maxSeedCost = (TextView) findViewById(R.id.et_maxSeedCost);
		et_minPickCost = (TextView) findViewById(R.id.et_minPickCost);
		et_maxPickCost = (TextView) findViewById(R.id.et_maxPickCost);
		et_minChemCost = (TextView) findViewById(R.id.et_minChemCost);
		et_maxChemCost = (TextView) findViewById(R.id.et_maxChemCost);
		et_minManuCost = (TextView) findViewById(R.id.et_minManuCost);
		et_maxManuCost = (TextView) findViewById(R.id.et_maxManuCost);
		et_minPackCost = (TextView) findViewById(R.id.et_minPackCost);
		et_maxPackCost = (TextView) findViewById(R.id.et_maxPackCost);
		et_minPackNum = (TextView) findViewById(R.id.et_minPackNum);
		et_maxPackNum = (TextView) findViewById(R.id.et_maxPackNum);
		et_minShorTranCost = (TextView) findViewById(R.id.et_minShorTranCost);
		et_maxShorTranCost = (TextView) findViewById(R.id.et_maxShorTranCost);
		et_minLongTranCost = (TextView) findViewById(R.id.et_minLongTranCost);
		et_maxLongTranCost = (TextView) findViewById(R.id.et_maxLongTranCost);
		et_minPackagingServiceCost = (TextView) findViewById(R.id.et_minPackagingServiceCost);
		et_maxPackagingServiceCost = (TextView) findViewById(R.id.et_maxPackagingServiceCost);
		et_minPrecCost = (TextView) findViewById(R.id.et_minPrecCost);
		et_maxPrecCost = (TextView) findViewById(R.id.et_maxPrecCost);
		et_minPackMateCost = (TextView) findViewById(R.id.et_minPackMateCost);
		et_maxPackMateCost = (TextView) findViewById(R.id.et_maxPackMateCost);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
		view8 = findViewById(R.id.view8);

		linear_argricuturalname = (LinearLayout) findViewById(R.id.linear_argricuturalname);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_processcost = (LinearLayout) findViewById(R.id.linear_processcost);
		linear_seedcost = (LinearLayout) findViewById(R.id.linear_seedcost);
		linear_caizhaicost = (LinearLayout) findViewById(R.id.linear_caizhaicost);
		linear_huafeicost = (LinearLayout) findViewById(R.id.linear_huafeicost);
		linear_rengongcost = (LinearLayout) findViewById(R.id.linear_rengongcost);
		linear_baozhuangcost = (LinearLayout) findViewById(R.id.linear_baozhuangcost);


		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		setDataToView(planFromulaListInfo.getFormType());

	}

	@Override
	public void setListener() {
		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_last:
				if (pos == 0) {
					ToastUtils.show(getApplicationContext(), "当前已是第一条");
					return;
				}
				btn_next.setBackgroundColor(Color.parseColor("#009933"));
				pos--;
				planFromulaListInfo = (PlanFromulaListInfo) ServiceNameHandler.allList
						.get(pos);
				setDataToView(planFromulaListInfo.getFormType());
				getDoViewPlanFromula();
				break;
			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				if (pos == ServiceNameHandler.allList.size() - 1) {
					ToastUtils.show(getApplicationContext(), "无更多数据..");
					return;
				}
				pos++;
				planFromulaListInfo = (PlanFromulaListInfo) ServiceNameHandler.allList
						.get(pos);
				setDataToView(planFromulaListInfo.getFormType());
				getDoViewPlanFromula();
				if (pos == 0) {
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
				} else if (pos == ServiceNameHandler.allList.size() - 1) {
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}
				break;

			default:
				break;
		}
	}

	public void setDataToView(String formType) {
		if ("1".equals(formType)) {
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
			et_maxManuCost.setText(planFromulaListInfo.getMaxManuCost());
			et_minPackCost.setText(planFromulaListInfo.getMinPackCost());
			et_maxPackCost.setText(planFromulaListInfo.getMaxPackCost());
			et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_maxPackNum.setText(planFromulaListInfo.getMaxPackNum());
			et_minShorTranCost
					.setText(planFromulaListInfo.getMinShorTranCost());
			et_maxShorTranCost
					.setText(planFromulaListInfo.getMaxShorTranCost());
			et_minLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_maxLongTranCost
					.setText(planFromulaListInfo.getMaxLongTranCost());
			et_minPrecCost.setText(planFromulaListInfo.getMinPrecCost());
			et_maxPrecCost.setText(planFromulaListInfo.getMaxPrecCost());

			et_minPackMateCost.setText(planFromulaListInfo.getMinPackMateCost());
			et_maxPackMateCost.setText(planFromulaListInfo.getMaxPackMateCost());


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
			et_maxSeedCost.setText(planFromulaListInfo.getMaxSeedCost());
			et_minChemCost.setText(planFromulaListInfo.getMinChemCost());
			et_maxChemCost.setText(planFromulaListInfo.getMaxChemCost());
			et_minPickCost.setText(planFromulaListInfo.getMinPickCost());
			et_maxPickCost.setText(planFromulaListInfo.getMaxPickCost());
			et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
			et_maxPackNum.setText(planFromulaListInfo.getMaxPackNum());
			et_minShorTranCost
					.setText(planFromulaListInfo.getMinShorTranCost());
			et_maxShorTranCost
					.setText(planFromulaListInfo.getMaxShorTranCost());
			et_minLongTranCost
					.setText(planFromulaListInfo.getMinLongTranCost());
			et_maxLongTranCost
					.setText(planFromulaListInfo.getMaxLongTranCost());
			et_minPrecCost.setText(planFromulaListInfo.getMinPrecCost());
			et_maxPrecCost.setText(planFromulaListInfo.getMaxPrecCost());

			et_minPackMateCost.setText(planFromulaListInfo.getMinPackMateCost());
			et_maxPackMateCost.setText(planFromulaListInfo.getMaxPackMateCost());

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
	/**
	 * 成本公式管理查看接口
	 *
	 */
	public void getDoViewPlanFromula() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("formId",planFromulaListInfo.getFormId());
			params.put("planType",planFromulaListInfo.getFormType());
			TwitterRestClient.get(Constant.DOVIEWPLANFROMULA, params,
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
									ToastUtils.show(getApplicationContext(), "查看信息成功");
									customCostListInfoList = JSONUtils
											.getQueryFromulaSettingPages_CustomCost(response);

									chargeListInfoList = JSONUtils
											.getQueryFromulaSettingPages_ChargeList(response);

									for (int i = 0; i <customCostListInfoList.size(); i++) {

										LinearLayout linearLayout = new LinearLayout(
												CheckCostequationAc.this);
										linearLayout
												.setOrientation(LinearLayout.HORIZONTAL);
										TextView tv_chargesCollectable = new TextView(
												CheckCostequationAc.this);
										tv_chargesCollectable.setText(customCostListInfoList.get(i).getCostName()+":");
										tv_chargesCollectable.setTextColor(Color.BLACK);
										tv_chargesCollectable.setWidth(mScreenWidth/9);
										tv_chargesCollectable.setGravity(Gravity.RIGHT);
										tv_chargesCollectable.setTextSize(11);


										TextView et_minValue= new TextView(
												CheckCostequationAc.this);
										et_minValue.setTextColor(Color.BLACK);
										et_minValue.setTextSize(12);
										et_minValue.setBackgroundResource(R.drawable.login_editext_background);
										et_minValue.setText(customCostListInfoList.get(i).getMinValue());
										et_minValue.setWidth(mScreenWidth/9*3);
										et_minValue.setHeight(63);
										et_minValue.setGravity(Gravity.CENTER);
										TextView tv_seperate = new TextView(
												CheckCostequationAc.this);
										tv_seperate.setText("--");
										tv_seperate.setTextColor(Color.BLACK);
										tv_seperate.setHeight(9);
										tv_seperate.setWidth(5);

										TextView et_maxValue= new TextView(
												CheckCostequationAc.this);
										et_maxValue.setTextColor(Color.BLACK);
										et_maxValue.setTextSize(12);
										et_maxValue.setBackgroundResource(R.drawable.login_editext_background);;
										et_maxValue.setText(customCostListInfoList.get(i).getMaxValue());
										et_maxValue.setWidth(mScreenWidth/9*3);
										et_maxValue.setHeight(63);
										et_maxValue.setGravity(Gravity.CENTER);


										TextView tv_cuName = new TextView(
												CheckCostequationAc.this);
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

										mLinearLayout.addView(linearLayout,
												mScreenWidth, 100);

									}
									/*if (customCostListInfoList!=null&&customCostListInfoList.size()>0) {
										setListAll(customCostListInfoList,chargeListInfoList);
									}*/
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
}
