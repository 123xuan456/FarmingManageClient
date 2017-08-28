package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.CustomProductionPlanAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.CustomProductionPlanInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * 自定义费用界面
 *
 * @author zhaobeibei
 *
 */
public class CustomProductionPlanAc extends BaseActivity {

	private ListView listview_costName1 ;
	private Dialog loadingDialog ;
	private String agriId,formType,planAmou ;
	private Bundle bundle ;
	private TextView tv_title ;
	private CustomProductionPlanAdapter customProductionPlanAdapter;

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
	private CustomProductionPlanInfo planFromulaListInfo;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_district);

		bundle = getIntent().getExtras();
		agriId = bundle.getString("agriId");
		formType = bundle.getString("formType");
		planAmou = bundle.getString("planAmou");

		init();
		setListener();
		getAjaxQueryFromulaValue();

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
//		listview_costName1 = (ListView) findViewById(R.id.listview_costName1);
		loadingDialog = DialogUtils.requestDialog(this);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("自定义费用");

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

		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
		view8 = findViewById(R.id.view8);

		linear_processcost = (LinearLayout) findViewById(R.id.linear_processcost);
		linear_seedcost = (LinearLayout) findViewById(R.id.linear_seedcost);
		linear_caizhaicost = (LinearLayout) findViewById(R.id.linear_caizhaicost);
		linear_huafeicost = (LinearLayout) findViewById(R.id.linear_huafeicost);
		linear_rengongcost = (LinearLayout) findViewById(R.id.linear_rengongcost);
		linear_baozhuangcost = (LinearLayout) findViewById(R.id.linear_baozhuangcost);


	}
	public void setDataToView(String formType) {
		if ("1".equals(formType)) {
			linear_processcost.setVisibility(View.VISIBLE);
			linear_seedcost.setVisibility(View.GONE);
			linear_caizhaicost.setVisibility(View.GONE);
			linear_huafeicost.setVisibility(View.GONE);
			linear_rengongcost.setVisibility(View.VISIBLE);
			linear_baozhuangcost.setVisibility(View.VISIBLE);

			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
			view5.setVisibility(View.GONE);
			view6.setVisibility(View.GONE);
			view7.setVisibility(View.VISIBLE);
			view8.setVisibility(View.VISIBLE);

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
			et_minPackMateCost.setText(planFromulaListInfo.getMinPackMateCost());
			et_maxPackMateCost.setText(planFromulaListInfo.getMaxPackMateCost());

		} else {
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

			/*et_minSeedCost.setText(planFromulaListInfo.getMinSeedCost());
			et_maxSeedCost.setText(planFromulaListInfo.getMinSeedCost());
			et_minChemCost.setText(planFromulaListInfo.getMinChemCost());
			et_maxChemCost.setText(planFromulaListInfo.getMinChemCost());
			et_minPickCost.setText(planFromulaListInfo.getMinPickCost());
			et_maxPickCost.setText(planFromulaListInfo.getMinPickCost());*/
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
			et_minPrecCost.setText(planFromulaListInfo.getMinProcCost());
			et_maxPrecCost.setText(planFromulaListInfo.getMinProcCost());
		}
	}
	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}
	public void getAjaxQueryFromulaValue() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("fromula.agriId",agriId);
			params.put("fromula.formType",formType);
			params.put("plan.planAmou",planAmou);


			TwitterRestClient.get(Constant.AJAXQUERYFROMULAVALUE, params,
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
									ServiceNameHandler.allList = JSONUtils.getAjaxQueryFromulaValue(response);
									planFromulaListInfo = (CustomProductionPlanInfo) ServiceNameHandler.allList.get(0);
									setListData(planFromulaListInfo.getCustomCostList());
									setDataToView(formType);

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	public void setListData(List<CustomCostListInfo> listAll){
		customProductionPlanAdapter = new CustomProductionPlanAdapter(getApplicationContext(), listAll);

		listview_costName1.setAdapter(customProductionPlanAdapter);
	}
}
