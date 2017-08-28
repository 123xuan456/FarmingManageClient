package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.CheckProplanAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.ChargeListInfo;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.FromulaListInfo;
import com.ruicheng.farmingmanageclient.bean.PlanListInfo;
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
 * 查看生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class CheckProplanAc extends BaseActivity implements OnClickListener{

	private LinearLayout linear_agricuturalname ,linear_cropname,linear_zhongzhiliang,linear_jiagongliang ;
	private ImageView img_comment_back ;
	private View view1,view2,view3,view4,view5, view6, view7, view8,view10;
	private View view11,view12,view13,view14;
	private Dialog loadingDialog;
	private TextView et_agriName, et_planName, et_cropName, et_planAmou,
			et_allplanAmou, et_planDrawPers, et_predStarDate, et_predEndDate, et_planDrawDate,et_planType;
	private int type ;
	private Bundle bundle ;
	private PlanListInfo planListInfo ;
	private Button btn_last, btn_next;
	private int pos = 0 ;
	private CheckProplanAdapter checkProplanAdapter;
	private List<CustomCostListInfo> customCostListInfoList ;
//	private ListView listview_costName1 ;

	private List<ChargeListInfo> chargeListInfoList ;
	private List<FromulaListInfo> fromulaList ;

	private LinearLayout mLinearLayout;
	private int mScreenWidth;
	private TextView et_minProcCost, et_maxProcCost, et_minSeedCost,
			et_maxSeedCost, et_minPickCost, et_maxPickCost, et_minChemCost,
			et_maxChemCost, et_minManuCost, et_maxManuCost, et_minPackCost,
			et_maxPackCost, et_minPackNum, et_maxPackNum, et_minShorTranCost,
			et_maxShorTranCost, et_minLongTranCost, et_maxLongTranCost,
			et_minPackagingServiceCost, et_maxPackagingServiceCost,
			et_minPrecCost, et_maxPrecCost, et_minPackMateCost,
			et_maxPackMateCost,et_minTotalCost,et_maxTotalCost;
	private LinearLayout linear_argricuturalname,
			linear_processcost, linear_seedcost, linear_caizhaicost,
			linear_huafeicost, linear_rengongcost, linear_baozhuangcost;
	FromulaListInfo planFromulaListInfo ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_checkproplan);

		bundle = getIntent().getExtras();
		if (bundle !=null) {
			type = bundle.getInt("type", -1);
			planListInfo = (PlanListInfo) bundle.getSerializable("planListInfo");
		}

		init();
		setListener();

		getDoViewProductionPlan();

	}
	public void getDoViewProductionPlan() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("planId",planListInfo.getPlanId());
			params.put("planType",planListInfo.getPlanType());
			TwitterRestClient.get(Constant.DOVIEWPRODUCTIONPLAN, params,
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
									ToastUtils.show(getApplicationContext(), "获取收获信息成功");
									customCostListInfoList = JSONUtils
											.getDoViewProductionPlan(response);

									chargeListInfoList = JSONUtils
											.getQueryFromulaSettingPages_ListPi(response);
									planFromulaListInfo  = JSONUtils
											.getDoViewProductionPlan_FromulaList(response,planListInfo.getPlanType());

									if (type == 1) {
										et_minProcCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinProcCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxProcCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxProcCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minManuCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinManuCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxManuCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxManuCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minPackCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPackCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxPackCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPackCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
										et_maxPackNum.setText(planFromulaListInfo.getMaxPackNum());
										et_minShorTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinShorTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxShorTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxShorTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minLongTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinLongTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxLongTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxLongTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										if ("".equals(planFromulaListInfo.getMinPrecCost())||"null".equals(planFromulaListInfo.getMinPrecCost())) {
											et_minPrecCost.setText("0.0");

										} else {
											et_minPrecCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPrecCost())*Double.valueOf(planListInfo.getPlanAmou())));
										}
										if ("".equals(planFromulaListInfo.getMaxPrecCost())||"null".equals(planFromulaListInfo.getMaxPrecCost())) {
											et_maxPrecCost.setText("0.0");
										} else {
											et_maxPrecCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPrecCost())*Double.valueOf(planListInfo.getPlanAmou())));
										}

										et_minPackMateCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPackMateCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxPackMateCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPackMateCost())*Double.valueOf(planListInfo.getPlanAmou())));


									} else {
										et_minSeedCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinSeedCost())*Double.valueOf(planListInfo.getPlanAmou())));


										et_maxSeedCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxSeedCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minChemCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinChemCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxChemCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxChemCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minPickCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPickCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxPickCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPickCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minPackNum.setText(planFromulaListInfo.getMinPackNum());
										et_maxPackNum.setText(planFromulaListInfo.getMaxPackNum());
										et_minShorTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinShorTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxShorTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxShorTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minLongTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinLongTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxLongTranCost
												.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxLongTranCost())*Double.valueOf(planListInfo.getPlanAmou())));
										if ("".equals(planFromulaListInfo.getMinPrecCost())||"null".equals(planFromulaListInfo.getMinPrecCost())) {
											et_minPrecCost.setText("0.0");

										} else {
											et_minPrecCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPrecCost())*Double.valueOf(planListInfo.getPlanAmou())));
										}
										if ("".equals(planFromulaListInfo.getMaxPrecCost())||"null".equals(planFromulaListInfo.getMaxPrecCost())) {
											et_maxPrecCost.setText("0.0");
										} else {
											et_maxPrecCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPrecCost())*Double.valueOf(planListInfo.getPlanAmou())));
										}
										et_minPackMateCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMinPackMateCost())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxPackMateCost.setText(String.valueOf(Double.valueOf(planFromulaListInfo.getMaxPackMateCost())*Double.valueOf(planListInfo.getPlanAmou())));

									}


									for (int i = 0; i <chargeListInfoList.size(); i++) {

										LinearLayout linearLayout = new LinearLayout(
												CheckProplanAc.this);
										linearLayout
												.setOrientation(LinearLayout.HORIZONTAL);
										TextView tv_chargesCollectable = new TextView(
												CheckProplanAc.this);
										tv_chargesCollectable.setText(chargeListInfoList.get(i).getCostName()+":");
										tv_chargesCollectable.setTextColor(Color.BLACK);
										tv_chargesCollectable.setWidth(mScreenWidth/9);
										tv_chargesCollectable.setGravity(Gravity.RIGHT);
										tv_chargesCollectable.setTextScaleX(11);


										EditText et_minValue= new EditText(
												CheckProplanAc.this);
										et_minValue.setTextColor(Color.BLACK);
										et_minValue.setTextSize(12);
										et_minValue.setBackgroundResource(R.drawable.login_editext_background);
										et_minValue.setText(String.valueOf(Double.valueOf(chargeListInfoList.get(i).getMinValue())*Double.valueOf(planListInfo.getPlanAmou())));
										et_minValue.setWidth(mScreenWidth/9*3);

										TextView tv_seperate = new TextView(
												CheckProplanAc.this);
										tv_seperate.setText("--");
										tv_seperate.setTextColor(Color.BLACK);
										tv_seperate.setHeight(9);
										tv_seperate.setWidth(5);

										EditText et_maxValue= new EditText(
												CheckProplanAc.this);
										et_maxValue.setTextColor(Color.BLACK);
										et_maxValue.setTextSize(12);
										et_maxValue.setBackgroundResource(R.drawable.login_editext_background);;
										et_maxValue.setText(String.valueOf(Double.valueOf(chargeListInfoList.get(i).getMaxValue())*Double.valueOf(planListInfo.getPlanAmou())));
										et_maxValue.setWidth(mScreenWidth/9*3);


										TextView tv_cuName = new TextView(
												CheckProplanAc.this);
										tv_cuName.setTextColor(Color.BLACK);
										tv_cuName.setTextSize(13);
										tv_cuName.setWidth(mScreenWidth/9);
										tv_cuName.setText("("+chargeListInfoList.get(i).getCuName()+")");

										minTotal += Double.valueOf(chargeListInfoList.get(i).getMinValue())*Double.valueOf(planListInfo.getPlanAmou()) ;

										maxTotal += Double.valueOf(chargeListInfoList.get(i).getMaxValue())*Double.valueOf(planListInfo.getPlanAmou()) ;

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

									if (type == 1) {

										et_minTotalCost.setText(String.valueOf(Double.valueOf(et_minProcCost.getText().toString())+Double.valueOf(et_minManuCost.getText().toString())+Double.valueOf(et_minPackCost.getText().toString())+

												Double.valueOf(et_minShorTranCost.getText().toString())+Double.valueOf(et_minLongTranCost.getText().toString())+Double.valueOf(et_minPackMateCost.getText().toString())+Double.valueOf(et_minPrecCost.getText().toString())+minTotal));

										et_maxTotalCost.setText(String.valueOf(Double.valueOf(et_maxProcCost.getText().toString())+Double.valueOf(et_maxManuCost.getText().toString())+Double.valueOf(et_maxPackCost.getText().toString())+Double.valueOf(et_maxShorTranCost.getText().toString())+Double.valueOf(et_maxLongTranCost.getText().toString())+Double.valueOf(et_maxPackMateCost.getText().toString())+Double.valueOf(et_maxPrecCost.getText().toString())+maxTotal));

									} else {

										et_minTotalCost.setText(String.valueOf(Double.valueOf(et_minSeedCost.getText().toString())+Double.valueOf(et_minChemCost.getText().toString())+Double.valueOf(et_minPickCost.getText().toString())+Double.valueOf(et_minShorTranCost.getText().toString())+Double.valueOf(et_minLongTranCost.getText().toString())+Double.valueOf(et_minPackMateCost.getText().toString())+Double.valueOf(et_minPrecCost.getText().toString())+minTotal));

										et_maxTotalCost.setText(String.valueOf(Double.valueOf(et_maxSeedCost.getText().toString())+Double.valueOf(et_maxChemCost.getText().toString())+Double.valueOf(et_maxPickCost.getText().toString())+Double.valueOf(et_maxShorTranCost.getText().toString())+Double.valueOf(et_maxLongTranCost.getText().toString())+Double.valueOf(et_maxPackMateCost.getText().toString())+Double.valueOf(et_maxPrecCost.getText().toString())+maxTotal));


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
	private double minTotal ,maxTotal ;
	public void setListAll(List<CustomCostListInfo> customCostListInfoList){

//		checkProplanAdapter = new CheckProplanAdapter(customCostListInfoList, getApplicationContext());
//		listview_costName1.setAdapter(checkProplanAdapter);

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
//		listview_costName1 =(ListView)findViewById(R.id.listview_costName1);

		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		loadingDialog = DialogUtils.requestDialog(this);
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

		linear_agricuturalname = (LinearLayout) findViewById(R.id.linear_agricuturalname);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_zhongzhiliang = (LinearLayout) findViewById(R.id.linear_zhongzhiliang);
		linear_jiagongliang = (LinearLayout) findViewById(R.id.linear_jiagongliang);


		linear_processcost = (LinearLayout) findViewById(R.id.linear_processcost);
		linear_seedcost = (LinearLayout) findViewById(R.id.linear_seedcost);
		linear_caizhaicost = (LinearLayout) findViewById(R.id.linear_caizhaicost);
		linear_huafeicost = (LinearLayout) findViewById(R.id.linear_huafeicost);
		linear_rengongcost = (LinearLayout) findViewById(R.id.linear_rengongcost);
		linear_baozhuangcost = (LinearLayout) findViewById(R.id.linear_baozhuangcost);


		view11 = findViewById(R.id.view11);
		view12 = findViewById(R.id.view12);
		view13 = findViewById(R.id.view13);
		view14 = findViewById(R.id.view14);

		et_agriName = (TextView) findViewById(R.id.et_agriName);
		et_planName = (TextView)findViewById(R.id.et_planName);
		et_cropName = (TextView)findViewById(R.id.et_cropName);
		et_planAmou = (TextView)findViewById(R.id.et_planAmou);
		et_allplanAmou = (TextView)findViewById(R.id.et_allplanAmou);
		et_planDrawPers = (TextView)findViewById(R.id.et_planDrawPers);
		et_predStarDate = (TextView)findViewById(R.id.et_predStarDate);
		et_predEndDate = (TextView)findViewById(R.id.et_predEndDate);
		et_planDrawDate = (TextView)findViewById(R.id.et_planDrawDate);
		et_planType = (TextView)findViewById(R.id.et_planType);

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
		et_minTotalCost = (TextView) findViewById(R.id.et_minTotalCost);
		et_maxTotalCost = (TextView) findViewById(R.id.et_maxTotalCost);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
		view8 = findViewById(R.id.view8);
		view10 = findViewById(R.id.view10);

		if (type == 0) {
			linear_agricuturalname.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_zhongzhiliang.setVisibility(View.VISIBLE);
			linear_jiagongliang.setVisibility(View.GONE);

			linear_processcost.setVisibility(View.GONE);
			linear_seedcost.setVisibility(View.VISIBLE);
			linear_caizhaicost.setVisibility(View.VISIBLE);
			linear_huafeicost.setVisibility(View.VISIBLE);
			linear_rengongcost.setVisibility(View.GONE);
			linear_baozhuangcost.setVisibility(View.GONE);

			view4.setVisibility(View.VISIBLE);
			view5.setVisibility(View.VISIBLE);
			view6.setVisibility(View.VISIBLE);
			view7.setVisibility(View.GONE);
			view8.setVisibility(View.GONE);
			view10.setVisibility(View.GONE);

			view11.setVisibility(View.VISIBLE);
			view12.setVisibility(View.GONE);
			view13.setVisibility(View.VISIBLE);
			view14.setVisibility(View.VISIBLE);

		} else {
			linear_agricuturalname.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_zhongzhiliang.setVisibility(View.GONE);
			linear_jiagongliang.setVisibility(View.VISIBLE);

			linear_processcost.setVisibility(View.VISIBLE);
			linear_seedcost.setVisibility(View.GONE);
			linear_caizhaicost.setVisibility(View.GONE);
			linear_huafeicost.setVisibility(View.GONE);
			linear_rengongcost.setVisibility(View.VISIBLE);
			linear_baozhuangcost.setVisibility(View.VISIBLE);


			view11.setVisibility(View.GONE);
			view12.setVisibility(View.VISIBLE);
			view13.setVisibility(View.GONE);
			view14.setVisibility(View.GONE);
			view4.setVisibility(View.GONE);
			view5.setVisibility(View.GONE);
			view6.setVisibility(View.GONE);
			view7.setVisibility(View.VISIBLE);
			view8.setVisibility(View.VISIBLE);
			view10.setVisibility(View.VISIBLE);

		}
		setDataToView(type);
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
					return ;
				}
				btn_next.setBackgroundColor(Color.parseColor("#009933"));
				pos -- ;
				planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(pos);
				setDataToView(type);
				getDoViewProductionPlan();
				break;
			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				if (pos ==ServiceNameHandler.allList.size()-1) {
					ToastUtils.show(getApplicationContext(), "无更多数据..");
					return ;
				}
				pos ++ ;
				planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(pos);
				setDataToView(type);
				getDoViewProductionPlan();
				if (pos == 0) {
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}else if (pos ==ServiceNameHandler.allList.size()-1){
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}
				break;

			default:
				break;
		}
	}
	public void setDataToView(int type ){
		//初始化查看数据
		if (type == 1) {
			et_allplanAmou.setText(planListInfo.getPlanAmou());
			et_planType.setText("加工类");
			et_cropName.setText(planListInfo.getAgriName());

		} else {
			et_agriName.setText(planListInfo.getAgriName());
			et_planAmou.setText(planListInfo.getPlanAmou());
			et_planType.setText("种植类");
		}
		et_planName.setText(planListInfo.getPlanName());
		et_planDrawPers.setText(planListInfo.getPlanDrawPers());
		et_predStarDate.setText(planListInfo.getPredStarDate());
		et_predEndDate.setText(planListInfo.getPredEndDate());
		et_planDrawDate.setText(planListInfo.getPlanDrawDate());
	}
}
