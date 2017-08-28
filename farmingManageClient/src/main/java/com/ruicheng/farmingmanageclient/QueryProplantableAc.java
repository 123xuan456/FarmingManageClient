package com.ruicheng.farmingmanageclient;

import android.content.Intent;
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

import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.ProductionPlanStatisticsInfo;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import java.util.List;

/**
 * 查询生产计划统计分析界面
 *
 * @author zhaobeibei
 *
 */
public class QueryProplantableAc extends BaseActivity {

	private Intent intent;
	private int type;
	private LinearLayout imageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private Bundle bundle;
	private ProductionPlanStatisticsInfo productionPlanStatisticsInfo;

	// private ListView listview_costName1;
	private CustomCostListInfo customCostListInfo;
	// private ProplantableAdapter updateCostequationAdapter;
	private LinearLayout linear_argricuturalname, linear_cropname,
			linear_processcost, linear_seedcost, linear_caizhaicost,
			linear_huafeicost, linear_rengongcost, linear_baozhuangcost,
			linear_allPlanAmou, linear_planAmou;
	private View view1, view2, view3, view4, view5, view6, view7, view8,
			allPlanAmou, planAmou;
	private TextView et_minProcCost, et_maxProcCost, et_minSeedCost,
			et_maxSeedCost, et_minPickCost, et_maxPickCost, et_minChemCost,
			et_maxChemCost, et_minManuCost, et_maxManuCost, et_minPackCost,
			et_maxPackCost, et_minPackNum, et_maxPackNum, et_minShorTranCost,
			et_maxShorTranCost, et_minLongTranCost, et_maxLongTranCost,
			et_minPackagingServiceCost, et_maxPackagingServiceCost,
			et_minPrecCost, et_maxPrecCost, et_minPackMateCost,
			et_maxPackMateCost, tv_allPlanAmou, tv_planAmou, et_minSubtotal,
			et_maxSubtotal;

	private Button btn_last, btn_next;
	private TextView tv_title, tv_cropType, tv_agriName, tv_planName;

	private int pos;
	private LinearLayout mLinearLayout;
	private int mScreenWidth;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_queryproplantable);
		intent = getIntent();
		bundle = intent.getExtras();
		if (bundle != null) {
			type = bundle.getInt("TYPE", -1);
			productionPlanStatisticsInfo = (ProductionPlanStatisticsInfo) bundle
					.getSerializable("productionPlanStatisticsInfo");

			customCostListInfo = (CustomCostListInfo) bundle
					.getSerializable("customCostListInfo");
			pos = bundle.getInt("pos");
		}
		init();
		// setListAll(ServiceNameHandler.customCostListInfoList);
		setListener();
	}

	public void setListAll(List<CustomCostListInfo> customCostListInfoList) {

		// updateCostequationAdapter = new ProplantableAdapter(
		// customCostListInfoList, getApplicationContext());
		// listview_costName1.setAdapter(updateCostequationAdapter);

	}

	@Override
	public void init() {

		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

		tv_title = (TextView) findViewById(R.id.tv_title);
		if ("1".equals(String.valueOf(type))) {
			tv_title.setText("加工生产计划统计分析");
		} else {
			tv_title.setText("种植生产计划统计分析");
		}
		// listview_costName1 = (ListView)
		// findViewById(R.id.listview_costName1);

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		imageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);

		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);

		IntentViewListener intentViewListener = new IntentViewListener();
		imageView_Linearlayout_Back.setOnClickListener(intentViewListener);

		btn_last.setOnClickListener(intentViewListener);
		btn_next.setOnClickListener(intentViewListener);

		tv_cropType = (TextView) findViewById(R.id.tv_cropType);
		tv_agriName = (TextView) findViewById(R.id.tv_agriName);
		tv_planName = (TextView) findViewById(R.id.tv_planName);
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
		tv_allPlanAmou = (TextView) findViewById(R.id.tv_allPlanAmou);
		tv_planAmou = (TextView) findViewById(R.id.tv_planAmou);
		et_minSubtotal = (TextView) findViewById(R.id.et_minSubtotal);
		et_maxSubtotal = (TextView) findViewById(R.id.et_maxSubtotal);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
		view8 = findViewById(R.id.view8);
		allPlanAmou = findViewById(R.id.allPlanAmou);
		planAmou = findViewById(R.id.planAmou);

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
		linear_allPlanAmou = (LinearLayout) findViewById(R.id.linear_allPlanAmou);
		linear_planAmou = (LinearLayout) findViewById(R.id.linear_planAmou);

		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		setDataToView(String.valueOf(type));

		for (int i = 0; i < ServiceNameHandler.customCostListInfoList.size(); i++) {


			if (ServiceNameHandler.customCostListInfoList.get(i).getFormId().equals(productionPlanStatisticsInfo.getFormId())) {
				LinearLayout linearLayout = new LinearLayout(
						QueryProplantableAc.this);
				linearLayout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv_chargesCollectable = new TextView(
						QueryProplantableAc.this);
				tv_chargesCollectable
						.setText(((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getCostName() + ":");
				tv_chargesCollectable.setTextColor(Color.BLACK);
				tv_chargesCollectable.setWidth(mScreenWidth / 9);
				tv_chargesCollectable.setGravity(Gravity.RIGHT);

				tv_chargesCollectable.setTextSize(11);

				EditText et_minValue = new EditText(QueryProplantableAc.this);
				et_minValue.setTextColor(Color.BLACK);
				et_minValue.setTextSize(12);
				et_minValue
						.setBackgroundResource(R.drawable.login_editext_background);
				et_minValue.setText(((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getMinValue());
				et_minValue.setWidth(mScreenWidth / 9 * 3);

				TextView tv_seperate = new TextView(QueryProplantableAc.this);
				tv_seperate.setText("--");
				tv_seperate.setTextColor(Color.BLACK);
				tv_seperate.setHeight(9);
				tv_seperate.setWidth(5);

				EditText et_maxValue = new EditText(QueryProplantableAc.this);
				et_maxValue.setTextColor(Color.BLACK);
				et_maxValue.setTextSize(12);
				et_maxValue
						.setBackgroundResource(R.drawable.login_editext_background);
				;
				et_maxValue.setText(((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getMaxValue());
				et_maxValue.setWidth(mScreenWidth / 9 * 3);

				TextView tv_cuName = new TextView(QueryProplantableAc.this);
				tv_cuName.setTextColor(Color.BLACK);
				tv_cuName.setTextSize(13);
				tv_cuName.setWidth(mScreenWidth / 9);
				tv_cuName.setText("("
						+ ((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getCuName() + ")");


				minTotal += Double.valueOf(((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getMinValue())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou());
				maxTotal += Double.valueOf(((CustomCostListInfo)ServiceNameHandler.customCostListInfoList.get(i)).getMaxValue())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou());



				LinearLayout.LayoutParams lParams = new LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lParams.setMargins(3, 5, 3, 5);
				linearLayout.addView(tv_chargesCollectable, lParams);
				linearLayout.addView(et_minValue, lParams);
				linearLayout.addView(tv_seperate, lParams);
				linearLayout.addView(et_maxValue, lParams);
				linearLayout.addView(tv_cuName, lParams);

				mLinearLayout.addView(linearLayout, mScreenWidth, 100);
			}
		}

	}
	private double minTotal =0,maxTotal=0 ;
	public void setDataToView(String formType) {
		tv_planName.setText(productionPlanStatisticsInfo.getPlanName());
		if ("1".equals(formType)) {
			linear_argricuturalname.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_processcost.setVisibility(View.VISIBLE);
			linear_seedcost.setVisibility(View.GONE);
			linear_caizhaicost.setVisibility(View.GONE);
			linear_huafeicost.setVisibility(View.GONE);
			linear_rengongcost.setVisibility(View.VISIBLE);
			linear_baozhuangcost.setVisibility(View.VISIBLE);
			linear_allPlanAmou.setVisibility(View.VISIBLE);
			linear_planAmou.setVisibility(View.GONE);

			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
			view5.setVisibility(View.GONE);
			view6.setVisibility(View.GONE);
			view7.setVisibility(View.VISIBLE);
			view8.setVisibility(View.VISIBLE);
			allPlanAmou.setVisibility(View.VISIBLE);
			planAmou.setVisibility(View.GONE);

			tv_cropType.setText(productionPlanStatisticsInfo.getAgriName());
			tv_allPlanAmou.setText(productionPlanStatisticsInfo.getPlanAmou());

			et_minProcCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinProcCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxProcCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxProcCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minManuCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinManuCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxManuCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxManuCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPackCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinPackCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPackCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxPackCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPackNum.setText(productionPlanStatisticsInfo
					.getMinPackNum());
			et_maxPackNum.setText(productionPlanStatisticsInfo
					.getMaxPackNum());
			et_minShorTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinShorTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxShorTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxShorTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minLongTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinLongTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxLongTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxLongTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPrecCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinPrecCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPrecCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxPrecCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPackMateCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinPackMateCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPackMateCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxPackMateCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));

			et_minSubtotal.setText(String.valueOf(Double.parseDouble(et_minProcCost.getText().toString())
					+ Double.parseDouble(et_minManuCost.getText().toString())
					+ Double.parseDouble(et_minPackCost.getText().toString())
					+ Double.parseDouble(et_minShorTranCost.getText()
					.toString())
					+ Double.parseDouble(et_minLongTranCost.getText()
					.toString())
					+ Double.parseDouble(et_minPackMateCost.getText()
					.toString())
					+ Double.parseDouble(et_minPrecCost.getText().toString())+minTotal));

			et_maxSubtotal.setText(String.valueOf(Double.parseDouble(et_maxProcCost.getText().toString())
					+ Double.parseDouble(et_maxManuCost.getText().toString())
					+ Double.parseDouble(et_maxPackCost.getText().toString())
					+ Double.parseDouble(et_maxShorTranCost.getText()
					.toString())
					+ Double.parseDouble(et_maxLongTranCost.getText()
					.toString())
					+ Double.parseDouble(et_maxPrecCost.getText().toString())
					+ Double.parseDouble(et_maxPackMateCost.getText()
					.toString())+maxTotal));
		} else {
			tv_agriName.setText(productionPlanStatisticsInfo.getAgriName());
			linear_argricuturalname.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_processcost.setVisibility(View.GONE);
			linear_seedcost.setVisibility(View.VISIBLE);
			linear_caizhaicost.setVisibility(View.VISIBLE);
			linear_huafeicost.setVisibility(View.VISIBLE);
			linear_rengongcost.setVisibility(View.GONE);
			linear_baozhuangcost.setVisibility(View.GONE);
			linear_allPlanAmou.setVisibility(View.GONE);
			linear_planAmou.setVisibility(View.VISIBLE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);
			view5.setVisibility(View.VISIBLE);
			view6.setVisibility(View.VISIBLE);
			view7.setVisibility(View.GONE);
			view8.setVisibility(View.GONE);
			allPlanAmou.setVisibility(View.GONE);
			planAmou.setVisibility(View.VISIBLE);

			tv_planAmou.setText(productionPlanStatisticsInfo.getPlanAmou());


			et_minSeedCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinSeedCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxSeedCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxSeedCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minChemCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinChemCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxChemCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxChemCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPickCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinPickCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPickCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxPickCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPackNum.setText(productionPlanStatisticsInfo
					.getMinPackNum());
			et_maxPackNum.setText(productionPlanStatisticsInfo
					.getMaxPackNum());
			et_minShorTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinShorTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxShorTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxShorTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minLongTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinLongTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxLongTranCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxLongTranCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPrecCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinProcCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPrecCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxProcCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_minPackMateCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMinPackMateCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));
			et_maxPackMateCost.setText(String.valueOf(Double.valueOf(productionPlanStatisticsInfo
					.getMaxPackMateCost())*Double.valueOf(productionPlanStatisticsInfo.getPlanAmou())));


			et_minSubtotal.setText(String.valueOf(Float.parseFloat(et_minChemCost.getText().toString())
					+ Float.parseFloat(et_minPickCost.getText().toString())
					+ Float.parseFloat(et_minShorTranCost.getText()
					.toString())
					+ Float.parseFloat(et_minLongTranCost.getText()
					.toString())
					+ Float.parseFloat(et_minPackMateCost.getText()
					.toString())
					+ Float.parseFloat(et_minPrecCost.getText().toString())+minTotal));

			et_maxSubtotal.setText(String.valueOf(Double.parseDouble(et_maxSeedCost.getText()
					.toString())
					+ Float.parseFloat(et_minChemCost.getText().toString())
					+ Float.parseFloat(et_maxPickCost.getText().toString())
					+ Float.parseFloat(et_maxShorTranCost.getText()
					.toString())
					+ Float.parseFloat(et_maxLongTranCost.getText()
					.toString())
					+ Float.parseFloat(et_maxPackMateCost.getText()
					.toString())
					+ Float.parseFloat(et_maxPrecCost.getText().toString())+maxTotal));
		}
	}

	@Override
	public void setListener() {

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(),
							ProplantableActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("type", type);
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.btn_last:
					if (pos == 0) {
						ToastUtils.show(getApplicationContext(), "当前已是第一条");
						btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
						return;
					}
					btn_next.setBackgroundColor(Color.parseColor("#009933"));
					pos--;
					productionPlanStatisticsInfo = (ProductionPlanStatisticsInfo) ServiceNameHandler.allList
							.get(pos);
					setDataToView(String.valueOf(type));
					break;
				case R.id.btn_next:
					btn_last.setBackgroundColor(Color.parseColor("#009933"));
					if (pos == ServiceNameHandler.allList.size() - 1) {
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						ToastUtils.show(getApplicationContext(), "无更多数据..");
						return;
					}
					pos++;
					productionPlanStatisticsInfo = (ProductionPlanStatisticsInfo) ServiceNameHandler.allList
							.get(pos);
					setDataToView(String.valueOf(type));
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
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			Intent intent = new Intent(this, ProplantableActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", type);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

}
