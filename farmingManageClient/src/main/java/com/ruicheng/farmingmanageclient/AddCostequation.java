package com.ruicheng.farmingmanageclient;

import android.app.Activity;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.AddCostequationAdapter;
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

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 添加成本公式界面
 *
 * @author zhaobeibei
 *
 */
public class AddCostequation extends BaseActivity implements OnClickListener {

	private Dialog loadingDialog;
	private int type;
	private Intent intent;
	private LinearLayout linear_argricuturalname, linear_cropname,
			linear_processcost, linear_seedcost, linear_caizhaicost,
			linear_huafeicost, linear_rengongcost, linear_baozhuangcost;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view1, view2, view3, view4, view5, view6, view7, view8;
	private EditText et_minProcCost, et_maxProcCost, et_minSeedCost,
			et_maxSeedCost, et_minPickCost, et_maxPickCost, et_minChemCost,
			et_maxChemCost, et_minManuCost, et_maxManuCost, et_minPackCost,
			et_maxPackCost, et_minPackNum, et_maxPackNum, et_minShorTranCost,
			et_maxShorTranCost, et_minLongTranCost, et_maxLongTranCost,
			et_minPackagingServiceCost, et_maxPackagingServiceCost,
			et_minPrecCost, et_maxPrecCost;
	private Bundle bundle;
	private PlanFromulaListInfo planFromulaListInfo;
	private TextView tv_cropType, tv_agriName, tv_formType;
	private static final int CROPTYPENAME = 1;
	private Button tv_pro_add;
	private AddCostequationAdapter addCostequationAdapter;
	private List<CustomCostListInfo> customCostListInfoList;
	// private ListView listview_costName1 ;

	private List<ChargeListInfo> chargeListInfoList;
	private String agriId;
	private LinearLayout mLinearLayout;
	private int mScreenWidth;
	private LinearLayout linearLayout ;
	private MinAndMaxInfo minAndMaxInfo ;
	private String cid ;
	private EditText et_minValue ;
	private EditText et_maxValue ;
	int num = 0 ;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_addcostequation_dynamic);
		intent = getIntent();
		if (intent != null) {
			type = intent.getIntExtra("TYPE", -1);
			bundle = intent.getExtras();
			if (bundle != null && ServiceNameHandler.allList.size() > 0) {
				planFromulaListInfo = (PlanFromulaListInfo) bundle
						.getSerializable("planFromulaListInfo");
			}
		}
		init();

		getAddFromulaSetting();
		setListener();

	}

	@Override
	public void init() {

		loadingDialog = DialogUtils.requestDialog(this);

		// listview_costName1 =(ListView)findViewById(R.id.listview_costName1);

//		mLinearLayout_Vertical = (LinearLayout) findViewById(R.id.mLinearLayout_Vertical);
		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

		tv_pro_add = (Button) findViewById(R.id.tv_pro_add);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);

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
		et_minPackagingServiceCost = (EditText) findViewById(R.id.et_minPackagingServiceCost);
		et_maxPackagingServiceCost = (EditText) findViewById(R.id.et_maxPackagingServiceCost);
		et_minPrecCost = (EditText) findViewById(R.id.et_minPrecCost);
		et_maxPrecCost = (EditText) findViewById(R.id.et_maxPrecCost);
//		et_minPackMateCost = (EditText) findViewById(R.id.et_minPackMateCost);
//		et_maxPackMateCost = (EditText) findViewById(R.id.et_maxPackMateCost);

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

		if (type == 1) {
			tv_formType.setText("加工类");
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

		} else {
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

		}
	}
	private StringBuffer minValueBuffer = new StringBuffer();
	private StringBuffer maxValueBuffer = new StringBuffer();
	private StringBuffer cidBuffer = new StringBuffer();

	@Override
	public void setListener() {
		ImageView_Linearlayout_Back.setOnClickListener(this);
		tv_pro_add.setOnClickListener(this);
		tv_cropType.setOnClickListener(this);
		tv_agriName.setOnClickListener(this);
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
						tv_cropType.setText(cropType);
					} else {
						// 作物名称数据
						String cropType = data.getStringExtra("cropType");
						tv_agriName.setText(cropType);
					}
					agriId = data.getStringExtra("dicId");
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ImageView_Linearlayout_Back:
				img_comment_back.setVisibility(View.GONE);
				Intent i = new Intent();
				i.setClass(AddCostequation.this, CostequationActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				finish();
				break;
			case R.id.tv_cropType:
				Intent intent = new Intent();
				intent.setClass(AddCostequation.this, CropTypeNameAc.class);
				intent.putExtra("fromWhichView", 2);
				startActivityForResult(intent, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.tv_agriName:
				Intent ii = new Intent();
				ii.setClass(AddCostequation.this, CropTypeNameAc.class);
				ii.putExtra("fromWhichView", 2);
				startActivityForResult(ii, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.tv_pro_add:
				getSaveFromulaSetting();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			startActivity(new Intent(this, CostequationActivity.class));
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 判断提交信息是否为空
	 *
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
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&Double.valueOf(et_minPackagingServiceCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackagingServiceCost.getText().toString())&&Double.valueOf(et_maxPackagingServiceCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&"".equals(et_maxPackagingServiceCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&!"".equals(et_maxPackagingServiceCost.getText().toString())&&Double.valueOf(et_minPackagingServiceCost.getText().toString())>Double.valueOf(et_maxPackagingServiceCost.getText().toString())) {
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
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&Double.valueOf(et_minPackagingServiceCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最小值不能小于0");
				return false;
			}
			if (!"".equals(et_maxPackagingServiceCost.getText().toString())&&Double.valueOf(et_maxPackagingServiceCost.getText().toString())<0) {
				ToastUtils.show(getApplicationContext(), "包装业务费最大值不能小于0");
				return false;
			}
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&"".equals(et_maxPackagingServiceCost.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "包装业务费设定最小值，必须填写最大值");
				return false;
			}
			if (!"".equals(et_minPackagingServiceCost.getText().toString())&&!"".equals(et_maxPackagingServiceCost.getText().toString())&&Double.valueOf(et_minPackagingServiceCost.getText().toString())>Double.valueOf(et_maxPackagingServiceCost.getText().toString())) {
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

	public void getSaveFromulaSetting() {
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
			params.put("planType", type);

			if (type == 1) {
				params.put("fromula.agriName",tv_cropType.getText().toString());
			} else {
				params.put("fromula.agriName",tv_agriName.getText().toString());
			}

			params.put("fromula.agriId", agriId);
			params.put("fromula.formType", type);

			for (int i = 0; i <ServiceNameHandler.minAndMaxInfoList.size(); i++) {
//					minAndMaxInfo = (MinAndMaxInfo)linearLayout.getTag();
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

			params.put("minValue",minValueBuffer.toString());
			params.put("customId",cidBuffer.toString());
			params.put("maxValue", maxValueBuffer.toString());

			if (type ==0 ) {

				if (!"".equals(et_minSeedCost
						.getText().toString())) {
					params.put("fromula.minSeedCost", et_minSeedCost
							.getText().toString());
				} else {
					params.put("fromula.minSeedCost", "0");
				}
				if (!"".equals(et_maxSeedCost
						.getText().toString())) {
					params.put("fromula.maxSeedCost", et_maxSeedCost
							.getText().toString());

				} else {
					params.put("fromula.maxSeedCost","0");

				}
				if (!"".equals(et_maxChemCost
						.getText().toString())) {
					params.put("fromula.maxChemCost", et_maxChemCost
							.getText().toString());

				} else {
					params.put("fromula.maxChemCost","0");

				}
				if (!"".equals(et_minChemCost
						.getText().toString())) {
					params.put("fromula.minChemCost", et_minChemCost
							.getText().toString());
				} else {
					params.put("fromula.minChemCost","0");
				}
				if (!"".equals(et_maxPickCost
						.getText().toString())) {
					params.put("fromula.maxPickCost", et_maxPickCost
							.getText().toString());
				} else {
					params.put("fromula.maxPickCost","0");
				}
				if (!"".equals(et_minPickCost
						.getText().toString())) {
					params.put("fromula.minPickCost", et_minPickCost
							.getText().toString());
				} else {
					params.put("fromula.minPickCost", "0");
				}
			}else{
				//加工费
				if (!"".equals(et_minProcCost.getText().toString())
						&& !"null".equals(et_minProcCost.getText().toString())) {

					params.put("fromula.minProcCost", et_minProcCost.getText()
							.toString());
				} else {
					params.put("fromula.minProcCost", "0");

				}
				if (!"".equals(et_maxProcCost.getText().toString())
						&& !"null".equals(et_maxProcCost.getText().toString())) {
					params.put("fromula.maxProcCost", et_maxProcCost.getText()
							.toString());

				} else {
					params.put("fromula.maxProcCost", "0");

				}
				//人工费
				if (!"".equals(et_minManuCost.getText().toString())
						&& !"null".equals(et_minManuCost.getText().toString())) {
					params.put("fromula.minManuCost", et_minManuCost.getText()
							.toString());

				} else {
					params.put("fromula.minManuCost", "0");
				}
				if (!"".equals(et_maxManuCost.getText().toString())
						&& !"null".equals(et_maxManuCost.getText().toString())) {
					params.put("fromula.maxManuCost", et_maxManuCost.getText()
							.toString());

				} else {
					params.put("fromula.maxManuCost", "0");

				}
				//包装费
				if (!"".equals(et_minPackCost.getText().toString())
						&& !"null".equals(et_minPackCost.getText().toString())) {

					params.put("fromula.minPackCost", et_minPackCost.getText()
							.toString());
				} else {
					params.put("fromula.minPackCost", "0");

				}
			}

			if (!"".equals(et_maxShorTranCost.getText().toString())
					&& !"null".equals(et_maxShorTranCost.getText().toString())) {
				params.put("fromula.maxShorTranCost", et_maxShorTranCost
						.getText().toString());
			} else {
				params.put("fromula.maxShorTranCost", "0");

			}
			if (!"".equals(et_minShorTranCost.getText().toString())
					&& !"null".equals(et_minShorTranCost.getText().toString())) {

				params.put("fromula.minShorTranCost", et_minShorTranCost
						.getText().toString());
			} else {
				params.put("fromula.minShorTranCost", "0");

			}
			if (!"".equals(et_maxLongTranCost.getText().toString())
					&& !"null".equals(et_maxLongTranCost.getText().toString())) {
				params.put("fromula.maxLongTranCost", et_maxLongTranCost
						.getText().toString());

			} else {
				params.put("fromula.maxLongTranCost", "0");

			}
			if (!"".equals(et_minLongTranCost.getText().toString())
					&& !"null".equals(et_minLongTranCost.getText().toString())) {
				params.put("fromula.minLongTranCost", et_minLongTranCost
						.getText().toString());

			} else {
				params.put("fromula.minLongTranCost", "0");

			}
			if (!"".equals(et_minPackNum.getText().toString())
					&& !"null".equals(et_minPackNum.getText().toString())) {

				params.put("fromula.minPackNum", et_minPackNum.getText()
						.toString());
			} else {
				params.put("fromula.minPackNum", "0");

			}
			if (!"".equals(et_maxPackNum.getText().toString())
					&& !"null".equals(et_maxPackNum.getText().toString())) {

				params.put("fromula.maxPackNum", et_maxPackNum.getText()
						.toString());
			} else {
				params.put("fromula.maxPackNum", "0");

			}
			if (!"".equals(et_maxPackCost.getText().toString())
					&& !"null".equals(et_maxPackCost.getText().toString())) {
				params.put("fromula.maxPackCost", et_maxPackCost.getText()
						.toString());

			} else {
				params.put("fromula.maxPackCost", "0");

			}
			if (!"".equals(et_maxPrecCost.getText().toString())
					&& !"null".equals(et_maxPrecCost.getText().toString())) {

				params.put("fromula.maxPrecCost", et_maxPrecCost.getText()
						.toString());
			} else {
				params.put("fromula.maxPrecCost", "0");

			}
			if (!"".equals(et_minPrecCost.getText().toString())
					&& !"null".equals(et_minPrecCost.getText().toString())) {

				params.put("fromula.minPrecCost", et_minPrecCost.getText()
						.toString());
			} else {
				params.put("fromula.minPrecCost", "0");

			}

			if (!"".equals(et_maxPackagingServiceCost.getText().toString())
					&& !"null".equals(et_maxPackagingServiceCost.getText()
					.toString())) {
				params.put("fromula.maxPackMateCost",
						et_maxPackagingServiceCost.getText().toString());

			} else {
				params.put("fromula.maxPackMateCost", "0");

			}
			if (!"".equals(et_minPackagingServiceCost.getText().toString())
					&& !"null".equals(et_minPackagingServiceCost.getText()
					.toString())) {
				params.put("fromula.minPackMateCost",
						et_minPackagingServiceCost.getText().toString());

			} else {
				params.put("fromula.minPackMateCost", "0");

			}

			TwitterRestClient.get(Constant.SAVEFROMULASETTING, params,
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
							ToastUtils.show(getApplicationContext(), "添加失败");
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
											"添加成功");
									Intent i=new Intent(AddCostequation.this,CostequationActivity.class);
									startActivity(i);
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

	/**
	 *
	 * 成本公式设定接口
	 *
	 */

	public void getAddFromulaSetting() {
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
			params.put("planType", type);
			String str = TwitterRestClient.BASE_URL;
			TwitterRestClient.get(Constant.ADDFROMULASETTING, params,
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
											.getAddFromulaSetting(response,type);

									chargeListInfoList = JSONUtils
											.getQueryFromulaSettingPages_ChargeList(response);
									ServiceNameHandler.minAndMaxInfoList.clear();
									for (int i = 0; i <customCostListInfoList.size(); i++) {
										num = i ;
										linearLayout = new LinearLayout(
												AddCostequation.this);
										linearLayout
												.setOrientation(LinearLayout.HORIZONTAL);
										TextView tv_chargesCollectable = new TextView(
												AddCostequation.this);
										tv_chargesCollectable.setText(customCostListInfoList.get(i).getChargesCollectable()+":");
										tv_chargesCollectable.setTextColor(Color.BLACK);
										tv_chargesCollectable.setWidth(mScreenWidth/9);
										tv_chargesCollectable.setGravity(Gravity.RIGHT);
										tv_chargesCollectable.setTextSize(11);

										et_minValue= new EditText(
												AddCostequation.this);
										et_minValue.setTextColor(Color.BLACK);
										et_minValue.setTextSize(12);
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
												AddCostequation.this);
										tv_seperate.setText("--");
										tv_seperate.setTextColor(Color.GREEN);
										tv_seperate.setHeight(9);
										tv_seperate.setWidth(3);

										et_maxValue= new EditText(
												AddCostequation.this);
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
														ToastUtils.show(getApplicationContext(), "最大值不能为空");
														return ;
													}
													if (Double.valueOf(et_maxValue.getText().toString())<0) {
														ToastUtils.show(getApplicationContext(), "最大值不能小于0");
														et_maxValue.setText("");
														return ;
													}
													if ("".equals(et_maxValue.getText().toString())&&!"".equals(et_minValue.getText().toString())) {
														ToastUtils.show(getApplicationContext(),customCostListInfoList.get(num).getChargesCollectable()+"设定最小值，必须填写最大值");
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
												AddCostequation.this);
										tv_cuName.setTextColor(Color.BLACK);
										tv_cuName.setTextSize(11);
										tv_cuName.setWidth(mScreenWidth/9);
										for (int j = 0; j <chargeListInfoList.size(); j++) {
											if (customCostListInfoList.get(i).getUnit().equals(chargeListInfoList.get(j).getCuCode())) {
												tv_cuName.setText("("+chargeListInfoList.get(j).getCuName()+")");
											}
										}
										tv_cuName.setGravity(Gravity.CENTER_HORIZONTAL);
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
										minAndMaxInfo.setCid(customCostListInfoList.get(num).getCid());

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

	public void setListAll(List<CustomCostListInfo> customCostListInfoList,
						   List<ChargeListInfo> chargeListInfoList) {

		/*
		 * addCostequationAdapter = new
		 * AddCostequationAdapter(customCostListInfoList,
		 * getApplicationContext(),chargeListInfoList);
		 * listview_costName1.setAdapter(addCostequationAdapter);
		 */

	}
}
