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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.R.id;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.DataListInfo;
import com.ruicheng.farmingmanageclient.bean.UpdateDetailInfo;
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
 * 播种移栽、田洋农事修改界面
 *
 * @author zhaobeibei
 *
 */
public class UpdateRecordAc extends BaseActivity implements OnClickListener {

	private Bundle bundle;
	private int pos;
	private String androidAccessType;
	private String detailId;
	private int kind;
	private Dialog loadingDialog;
	private TextView tv_pro_save;
	private TextView tv_seeddate, tv_transplantdate,et_cropPtype,tv_registdate, tv_cropType, tv_cropLevel;
	private EditText  tv_actionPerson, tv_actionBak;
	private DataListInfo dataListInfo;

	private EditText tv_temperValue, tv_weatherState, tv_productItem,
			tv_agrDesc, tv_cropState, tv_manureName, tv_manureAmountUnit,
			tv_manureUse, tv_pesticideName, tv_pesticideAmountUnit,
			tv_diluteScale, tv_preventObj, tv_receiveWeight,tv_receiveDate;

	private LinearLayout linearlayout_cropPtype, linearlayout_cropType,
			linearlayout_seeddate, linearlayout_transplantdate,
			linearlayout_recordDate, linearlayout_actionPerson,
			linearlayout_actionBak, linearlayout_temperValue,
			linearlayout_weatherState, linearlayout_productItem,
			linearlayout_agrDesc, linearlayout_cropState,
			linearlayout_manureName, linearlayout_manureAmountUnit,
			linearlayout_manureUse, linearlayout_pesticideName,
			linearlayout_pesticideAmountUnit, linearlayout_diluteScale,
			linearlayout_preventObj, linearlayout_receiveWeight,linearlayout_cropLevel,linearlayout_receiveDate;

	private View view_cropPtype, view_cropType,
			view_seeddate, view_transplantdate, view_actionPerson,
			view_actionBak, view_temperValue,
			view_weatherState, view_productItem,
			view_agrDesc, view_cropState,
			view_manureName, view_manureAmountUnit,
			view_manureUse, view_pesticideName,
			view_pesticideAmountUnit, view_diluteScale,
			view_preventObj, view_receiveWeight,view_cropLevel,view_registdate,view_receiveDate;
	private final int CROPTYPENAME = 0 ;
	private final int CROPLEVEL = 1 ;
	private TextView textView_Title_Left ;
	private ImageView img_comment_back;
	private int optionType ;
	private UpdateDetailInfo  updateDetailInfo ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updaerecord);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			pos = bundle.getInt("pos");
			androidAccessType = bundle.getString("androidAccessType");
			kind = bundle.getInt("kind");
			optionType = bundle.getInt("optionType");
			dataListInfo = (DataListInfo) bundle
					.getSerializable("dataListInfo");
		}
		init();
		setListener();

		getupdateRecordPage();

	}
	public void getupdateRecordPage() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("detailId",dataListInfo.getDetailId());
			TwitterRestClient.get(Constant.UPADATERECORDPAGE, params,
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
								updateDetailInfo = JSONUtils.getUpdateRecordPage(response);
								tv_registdate.setText(updateDetailInfo.getRecordDate());
								tv_weatherState.setText(updateDetailInfo.getWeatherState());
								if (!"null".equals(updateDetailInfo.getActionBak())) {

									tv_actionBak.setText(updateDetailInfo.getActionBak());
								} else {
									tv_actionBak.setText("未添加");

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

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/* openOrCloseKeybd(false); */
						img_comment_back.setVisibility(View.GONE);
						finish();
						Intent i = new Intent();
						i.setClass(UpdateRecordAc.this,
								PromanageListActivity.class);
						startActivity(i);
						finish();
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});

		loadingDialog = DialogUtils.requestDialog(this);
		tv_pro_save = (TextView) findViewById(id.tv_pro_save);

		textView_Title_Left = (TextView) findViewById(R.id.textView_Title_Left);

		if (kind == 0) {
			textView_Title_Left.setText("修改播种移栽");
		} else if (kind ==1){
			textView_Title_Left.setText("修改日常农事");
		}else if (kind ==2){
			textView_Title_Left.setText("修改收成登记");
		}
		et_cropPtype = (TextView) findViewById(R.id.et_cropPtype);
		tv_cropType = (TextView) findViewById(R.id.tv_cropType);
		tv_seeddate = (TextView) findViewById(R.id.tv_seeddate);
		tv_transplantdate = (TextView) findViewById(R.id.tv_transplantdate);
		tv_registdate = (TextView) findViewById(R.id.tv_registdate);
		tv_actionPerson = (EditText) findViewById(R.id.tv_actionPerson);
		tv_actionBak = (EditText) findViewById(R.id.et_actionBak);

		tv_temperValue = (EditText) findViewById(R.id.tv_temperValue);
		tv_weatherState = (EditText) findViewById(R.id.tv_weatherState);
		tv_productItem = (EditText) findViewById(R.id.tv_productItem);
		tv_agrDesc = (EditText) findViewById(R.id.tv_agrDesc);
		tv_cropState = (EditText) findViewById(R.id.tv_cropState);
		tv_manureName = (EditText) findViewById(R.id.tv_manureName);
		tv_manureAmountUnit = (EditText) findViewById(R.id.tv_manureAmountUnit);
		tv_manureUse = (EditText) findViewById(R.id.tv_manureUse);
		tv_pesticideName = (EditText) findViewById(R.id.tv_pesticideName);
		tv_pesticideAmountUnit = (EditText) findViewById(R.id.tv_pesticideAmountUnit);
		tv_diluteScale = (EditText) findViewById(R.id.tv_diluteScale);
		tv_preventObj = (EditText) findViewById(R.id.tv_preventObj);
		tv_receiveWeight = (EditText) findViewById(R.id.tv_receiveWeight);
		tv_cropLevel = (TextView) findViewById(R.id.tv_cropLevel);

		tv_receiveDate = (EditText) findViewById(R.id.tv_receiveDate);

		linearlayout_cropPtype = (LinearLayout) findViewById(R.id.linearlayout_cropPtype);
		linearlayout_cropType = (LinearLayout) findViewById(R.id.linearlayout_cropType);
		linearlayout_seeddate = (LinearLayout) findViewById(R.id.linearlayout_seeddate);
		linearlayout_transplantdate = (LinearLayout) findViewById(R.id.linearlayout_transplantdate);
		linearlayout_actionPerson = (LinearLayout) findViewById(R.id.linearlayout_actionPerson);
		linearlayout_actionBak = (LinearLayout) findViewById(R.id.linearlayout_actionBak);
		linearlayout_temperValue = (LinearLayout) findViewById(R.id.linearlayout_temperValue);
		linearlayout_weatherState = (LinearLayout) findViewById(R.id.linearlayout_weatherState);
		linearlayout_productItem = (LinearLayout) findViewById(R.id.linearlayout_productItem);
		linearlayout_agrDesc = (LinearLayout) findViewById(R.id.linearlayout_agrDesc);
		linearlayout_cropState = (LinearLayout) findViewById(R.id.linearlayout_cropState);
		linearlayout_manureName = (LinearLayout) findViewById(R.id.linearlayout_manureName);
		linearlayout_manureAmountUnit = (LinearLayout) findViewById(R.id.linearlayout_manureAmountUnit);
		linearlayout_manureUse = (LinearLayout) findViewById(R.id.linearlayout_manureUse);
		linearlayout_pesticideName = (LinearLayout) findViewById(R.id.linearlayout_pesticideName);
		linearlayout_pesticideAmountUnit = (LinearLayout) findViewById(R.id.linearlayout_pesticideAmountUnit);
		linearlayout_diluteScale = (LinearLayout) findViewById(R.id.linearlayout_diluteScale);
		linearlayout_preventObj = (LinearLayout) findViewById(R.id.linearlayout_preventObj);
		linearlayout_receiveWeight = (LinearLayout) findViewById(R.id.linearlayout_receiveWeight);
		linearlayout_cropLevel = (LinearLayout) findViewById(R.id.linearlayout_cropLevel);
		linearlayout_recordDate = (LinearLayout) findViewById(R.id.linearlayout_recordDate);
		linearlayout_receiveDate = (LinearLayout) findViewById(R.id.linearlayout_receiveDate);

		view_cropPtype = (View) findViewById(R.id.view_cropPtype);
		view_cropType = (View) findViewById(R.id.view_cropType);
		view_seeddate = (View) findViewById(R.id.view_seeddate);
		view_transplantdate = (View) findViewById(R.id.view_transplantdate);
		view_actionPerson = (View) findViewById(R.id.view_actionPerson);
		view_actionBak = (View) findViewById(R.id.view_actionBak);
		view_temperValue = (View) findViewById(R.id.view_temperValue);
		view_weatherState = (View) findViewById(R.id.view_weatherState);
		view_productItem = (View) findViewById(R.id.view_productItem);
		view_agrDesc = (View) findViewById(R.id.view_agrDesc);
		view_cropState = (View) findViewById(R.id.view_cropState);
		view_manureName = (View) findViewById(R.id.view_manureName);
		view_manureAmountUnit = (View) findViewById(R.id.view_manureAmountUnit);
		view_manureUse = (View) findViewById(R.id.view_manureUse);
		view_pesticideName = (View) findViewById(R.id.view_pesticideName);
		view_pesticideAmountUnit = (View) findViewById(R.id.view_pesticideAmountUnit);
		view_diluteScale = (View) findViewById(R.id.view_diluteScale);
		view_preventObj = (View) findViewById(R.id.view_preventObj);
		view_receiveWeight = (View) findViewById(R.id.view_receiveWeight);
		view_cropLevel = (View) findViewById(R.id.view_cropLevel);
		view_registdate = (View) findViewById(R.id.view_registdate);
		view_receiveDate = (View) findViewById(R.id.view_receiveDate);
		view_cropLevel = (View) findViewById(R.id.view_cropLevel);

		linearlayout_actionPerson.setVisibility(View.VISIBLE);
		linearlayout_actionBak.setVisibility(View.VISIBLE);
		linearlayout_recordDate.setVisibility(View.VISIBLE);
		view_actionPerson.setVisibility(View.VISIBLE);
		view_actionBak.setVisibility(View.VISIBLE);
		tv_actionBak.setText(dataListInfo.getActionBak());
		if (optionType == 1) {
			linearlayout_cropPtype.setVisibility(View.VISIBLE);
			linearlayout_cropType.setVisibility(View.VISIBLE);
			linearlayout_seeddate.setVisibility(View.VISIBLE);
			linearlayout_transplantdate.setVisibility(View.VISIBLE);
			linearlayout_temperValue.setVisibility(View.VISIBLE);
			linearlayout_weatherState.setVisibility(View.GONE);
			linearlayout_productItem.setVisibility(View.GONE);
			linearlayout_agrDesc.setVisibility(View.GONE);
			linearlayout_cropState.setVisibility(View.GONE);
			linearlayout_manureName.setVisibility(View.GONE);
			linearlayout_manureAmountUnit.setVisibility(View.GONE);
			linearlayout_manureUse.setVisibility(View.GONE);
			linearlayout_pesticideName.setVisibility(View.GONE);
			linearlayout_pesticideAmountUnit.setVisibility(View.GONE);
			linearlayout_diluteScale.setVisibility(View.GONE);
			linearlayout_preventObj.setVisibility(View.GONE);
			linearlayout_receiveWeight.setVisibility(View.GONE);
			linearlayout_receiveDate.setVisibility(View.GONE);
			linearlayout_cropLevel.setVisibility(View.GONE);
			linearlayout_temperValue.setVisibility(View.GONE);

			view_cropPtype.setVisibility(View.VISIBLE);
			view_cropType.setVisibility(View.VISIBLE);
			view_seeddate.setVisibility(View.VISIBLE);
			view_transplantdate.setVisibility(View.VISIBLE);
			view_temperValue.setVisibility(View.GONE);
			view_weatherState.setVisibility(View.GONE);
			view_productItem.setVisibility(View.GONE);
			view_agrDesc.setVisibility(View.GONE);
			view_cropState.setVisibility(View.GONE);
			view_manureName.setVisibility(View.GONE);
			view_manureAmountUnit.setVisibility(View.GONE);
			view_manureUse.setVisibility(View.GONE);
			view_pesticideName.setVisibility(View.GONE);
			view_pesticideAmountUnit.setVisibility(View.GONE);
			view_diluteScale.setVisibility(View.GONE);
			view_preventObj.setVisibility(View.GONE);
			view_receiveWeight.setVisibility(View.GONE);
			view_receiveDate.setVisibility(View.GONE);
			view_cropLevel.setVisibility(View.GONE);

			et_cropPtype.setText(dataListInfo.getCropPtype());
			tv_cropType.setText(dataListInfo.getCropType());
			tv_seeddate.setText(dataListInfo.getSeedDate());
			tv_transplantdate.setText(dataListInfo.getMoveDate());
			tv_registdate.setText(dataListInfo.getReceiveDate());
			tv_actionPerson.setText(dataListInfo.getActionPerson());


		} else if (optionType == 2) {


			if ("2".equals(dataListInfo.getProductType())) {

				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_cropType.setVisibility(View.GONE);
				linearlayout_seeddate.setVisibility(View.GONE);
				linearlayout_transplantdate.setVisibility(View.GONE);
				linearlayout_actionPerson.setVisibility(View.VISIBLE);
				linearlayout_actionBak.setVisibility(View.VISIBLE);
				linearlayout_temperValue.setVisibility(View.VISIBLE);
				linearlayout_weatherState.setVisibility(View.VISIBLE);
				linearlayout_productItem.setVisibility(View.VISIBLE);
				linearlayout_agrDesc.setVisibility(View.VISIBLE);
				linearlayout_cropState.setVisibility(View.VISIBLE);
				linearlayout_manureName.setVisibility(View.GONE);
				linearlayout_manureAmountUnit.setVisibility(View.GONE);
				linearlayout_manureUse.setVisibility(View.GONE);
				linearlayout_pesticideName.setVisibility(View.GONE);
				linearlayout_pesticideAmountUnit.setVisibility(View.GONE);
				linearlayout_diluteScale.setVisibility(View.GONE);
				linearlayout_preventObj.setVisibility(View.GONE);
				linearlayout_receiveWeight.setVisibility(View.GONE);
				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_receiveDate.setVisibility(View.VISIBLE);
				linearlayout_cropLevel.setVisibility(View.GONE);


				view_cropPtype.setVisibility(View.GONE);
				view_cropType.setVisibility(View.GONE);
				view_seeddate.setVisibility(View.GONE);
				view_transplantdate.setVisibility(View.GONE);
				view_actionPerson.setVisibility(View.VISIBLE);
				view_actionBak.setVisibility(View.VISIBLE);
				view_temperValue.setVisibility(View.VISIBLE);
				view_weatherState.setVisibility(View.VISIBLE);
				view_productItem.setVisibility(View.VISIBLE);
				view_agrDesc.setVisibility(View.VISIBLE);
				view_cropState.setVisibility(View.VISIBLE);
				view_manureName.setVisibility(View.GONE);
				view_manureAmountUnit.setVisibility(View.GONE);
				view_manureUse.setVisibility(View.GONE);
				view_pesticideName.setVisibility(View.GONE);
				view_pesticideAmountUnit.setVisibility(View.GONE);
				view_diluteScale.setVisibility(View.GONE);
				view_preventObj.setVisibility(View.GONE);
				view_receiveWeight.setVisibility(View.GONE);
				view_cropPtype.setVisibility(View.GONE);
				view_receiveDate.setVisibility(View.VISIBLE);
				view_cropLevel.setVisibility(View.GONE);



				textView_Title_Left.setText("修改日常农事");
				tv_temperValue.setText(dataListInfo.getTemperValue());
				tv_productItem.setText(dataListInfo.getProductItem());
				tv_cropState.setText(dataListInfo.getCropState());
				tv_agrDesc.setText(dataListInfo.getAgrDesc());
				tv_receiveDate.setText(dataListInfo.getReceiveDate());
				tv_actionPerson.setText(dataListInfo.getActionPerson());
				tv_registdate.setText(dataListInfo.getRecordDate());
				tv_actionBak.setText(dataListInfo.getActionBak());

			} else if ("3".equals(dataListInfo.getProductType())){

				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_cropType.setVisibility(View.GONE);
				linearlayout_seeddate.setVisibility(View.GONE);
				linearlayout_transplantdate.setVisibility(View.GONE);
				linearlayout_actionPerson.setVisibility(View.VISIBLE);
				linearlayout_actionBak.setVisibility(View.VISIBLE);
				linearlayout_temperValue.setVisibility(View.GONE);
				linearlayout_weatherState.setVisibility(View.GONE);
				linearlayout_productItem.setVisibility(View.GONE);
				linearlayout_agrDesc.setVisibility(View.GONE);
				linearlayout_cropState.setVisibility(View.GONE);
				linearlayout_manureName.setVisibility(View.VISIBLE);
				linearlayout_manureAmountUnit.setVisibility(View.VISIBLE);
				linearlayout_manureUse.setVisibility(View.VISIBLE);
				linearlayout_pesticideName.setVisibility(View.GONE);
				linearlayout_pesticideAmountUnit.setVisibility(View.GONE);
				linearlayout_diluteScale.setVisibility(View.GONE);
				linearlayout_preventObj.setVisibility(View.GONE);
				linearlayout_receiveWeight.setVisibility(View.GONE);
				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_receiveDate.setVisibility(View.VISIBLE);
				linearlayout_cropLevel.setVisibility(View.GONE);


				view_cropPtype.setVisibility(View.GONE);
				view_cropType.setVisibility(View.GONE);
				view_seeddate.setVisibility(View.GONE);
				view_transplantdate.setVisibility(View.GONE);
				view_actionPerson.setVisibility(View.VISIBLE);
				view_actionBak.setVisibility(View.VISIBLE);
				view_temperValue.setVisibility(View.GONE);
				view_weatherState.setVisibility(View.GONE);
				view_productItem.setVisibility(View.GONE);
				view_agrDesc.setVisibility(View.GONE);
				view_cropState.setVisibility(View.GONE);
				view_manureName.setVisibility(View.VISIBLE);
				view_manureAmountUnit.setVisibility(View.VISIBLE);
				view_manureUse.setVisibility(View.VISIBLE);
				view_pesticideName.setVisibility(View.GONE);
				view_pesticideAmountUnit.setVisibility(View.GONE);
				view_diluteScale.setVisibility(View.GONE);
				view_preventObj.setVisibility(View.GONE);
				view_receiveWeight.setVisibility(View.GONE);
				view_cropPtype.setVisibility(View.GONE);
				view_receiveDate.setVisibility(View.VISIBLE);
				view_cropLevel.setVisibility(View.GONE);

				textView_Title_Left.setText("修改肥料使用");
				tv_manureName.setText(dataListInfo.getManureName());
				tv_manureUse.setText(dataListInfo.getManureUse());
				tv_receiveDate.setText(dataListInfo.getReceiveDate());
				tv_actionPerson.setText(dataListInfo.getActionPerson());
				tv_registdate.setText(dataListInfo.getRecordDate());
				tv_actionBak.setText(dataListInfo.getActionBak());
				tv_manureAmountUnit.setText(dataListInfo.getManureAmountUnit());

			}else if ("4".equals(dataListInfo.getProductType())){

				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_cropType.setVisibility(View.GONE);
				linearlayout_seeddate.setVisibility(View.GONE);
				linearlayout_transplantdate.setVisibility(View.GONE);
				linearlayout_actionPerson.setVisibility(View.VISIBLE);
				linearlayout_actionBak.setVisibility(View.VISIBLE);
				linearlayout_temperValue.setVisibility(View.GONE);
				linearlayout_weatherState.setVisibility(View.GONE);
				linearlayout_productItem.setVisibility(View.GONE);
				linearlayout_agrDesc.setVisibility(View.GONE);
				linearlayout_cropState.setVisibility(View.GONE);
				linearlayout_manureName.setVisibility(View.GONE);
				linearlayout_manureAmountUnit.setVisibility(View.GONE);
				linearlayout_manureUse.setVisibility(View.GONE);
				linearlayout_pesticideName.setVisibility(View.VISIBLE);
				linearlayout_pesticideAmountUnit.setVisibility(View.VISIBLE);
				linearlayout_diluteScale.setVisibility(View.VISIBLE);
				linearlayout_preventObj.setVisibility(View.VISIBLE);
				linearlayout_receiveWeight.setVisibility(View.GONE);
				linearlayout_cropPtype.setVisibility(View.GONE);
				linearlayout_receiveDate.setVisibility(View.VISIBLE);
				linearlayout_cropLevel.setVisibility(View.GONE);

				view_cropPtype.setVisibility(View.GONE);
				view_cropType.setVisibility(View.GONE);
				view_seeddate.setVisibility(View.GONE);
				view_transplantdate.setVisibility(View.GONE);
				view_actionPerson.setVisibility(View.VISIBLE);
				view_actionBak.setVisibility(View.VISIBLE);
				view_temperValue.setVisibility(View.GONE);
				view_weatherState.setVisibility(View.GONE);
				view_productItem.setVisibility(View.GONE);
				view_agrDesc.setVisibility(View.GONE);
				view_cropState.setVisibility(View.GONE);
				view_manureName.setVisibility(View.GONE);
				view_manureAmountUnit.setVisibility(View.GONE);
				view_manureUse.setVisibility(View.GONE);
				view_pesticideName.setVisibility(View.VISIBLE);
				view_pesticideAmountUnit.setVisibility(View.VISIBLE);
				view_diluteScale.setVisibility(View.VISIBLE);
				view_preventObj.setVisibility(View.VISIBLE);
				view_receiveWeight.setVisibility(View.GONE);
				view_cropPtype.setVisibility(View.GONE);
				view_receiveDate.setVisibility(View.VISIBLE);
				view_cropLevel.setVisibility(View.GONE);

				textView_Title_Left.setText("修改农药使用");

				tv_pesticideName.setText(dataListInfo.getPesticideName());
				tv_diluteScale.setText(dataListInfo.getDiluteScale());
				tv_pesticideAmountUnit.setText(dataListInfo.getPesticideAmountUnit());
				tv_preventObj.setText(dataListInfo.getPreventObj());
				tv_registdate.setText(dataListInfo.getRecordDate());
				tv_manureAmountUnit.setText(dataListInfo.getManureAmountUnit());
				tv_receiveDate.setText(dataListInfo.getReceiveDate());
				tv_actionPerson.setText(dataListInfo.getActionPerson());
				tv_registdate.setText(dataListInfo.getRecordDate());
				tv_actionBak.setText(dataListInfo.getActionBak());
			}

		} else if (optionType == 3) {


		} else if (optionType == 4) {


		} else if (optionType == 5) {

			linearlayout_cropType.setVisibility(View.VISIBLE);	//作物品种
			linearlayout_cropPtype.setVisibility(View.VISIBLE); //作物名称
			linearlayout_seeddate.setVisibility(View.GONE);
			linearlayout_transplantdate.setVisibility(View.GONE);
			linearlayout_actionPerson.setVisibility(View.VISIBLE);  //实施人员
			linearlayout_actionBak.setVisibility(View.VISIBLE);		 //备注
			linearlayout_temperValue.setVisibility(View.GONE);
			linearlayout_weatherState.setVisibility(View.GONE);
			linearlayout_productItem.setVisibility(View.GONE);
			linearlayout_agrDesc.setVisibility(View.GONE);
			linearlayout_cropState.setVisibility(View.GONE);
			linearlayout_manureName.setVisibility(View.GONE);
			linearlayout_manureAmountUnit.setVisibility(View.GONE);
			linearlayout_manureUse.setVisibility(View.GONE);
			linearlayout_pesticideName.setVisibility(View.GONE);
			linearlayout_pesticideAmountUnit.setVisibility(View.GONE);
			linearlayout_diluteScale.setVisibility(View.GONE);
			linearlayout_preventObj.setVisibility(View.GONE);
			linearlayout_receiveWeight.setVisibility(View.VISIBLE);  //收获重量
			linearlayout_receiveDate.setVisibility(View.VISIBLE);  //收获日期
			linearlayout_cropLevel.setVisibility(View.VISIBLE);    //货品等级
			linearlayout_recordDate.setVisibility(View.VISIBLE);    //登记日期


			view_cropPtype.setVisibility(View.VISIBLE);
			view_cropType.setVisibility(View.VISIBLE);
			view_seeddate.setVisibility(View.GONE);
			view_transplantdate.setVisibility(View.GONE);
			view_actionPerson.setVisibility(View.VISIBLE);
			view_actionBak.setVisibility(View.VISIBLE);
			view_temperValue.setVisibility(View.GONE);
			view_weatherState.setVisibility(View.GONE);
			view_productItem.setVisibility(View.GONE);
			view_agrDesc.setVisibility(View.GONE);
			view_cropState.setVisibility(View.GONE);
			view_manureName.setVisibility(View.GONE);
			view_manureAmountUnit.setVisibility(View.GONE);
			view_manureUse.setVisibility(View.GONE);
			view_pesticideName.setVisibility(View.GONE);
			view_pesticideAmountUnit.setVisibility(View.GONE);
			view_diluteScale.setVisibility(View.GONE);
			view_preventObj.setVisibility(View.GONE);
			view_receiveWeight.setVisibility(View.VISIBLE);
			view_cropPtype.setVisibility(View.VISIBLE);
			view_receiveDate.setVisibility(View.VISIBLE);
			view_cropLevel.setVisibility(View.VISIBLE);


			et_cropPtype.setText(dataListInfo.getCropPtype());
			tv_cropType.setText(dataListInfo.getCropType());
			tv_receiveDate.setText(dataListInfo.getReceiveDate());
			tv_actionPerson.setText(dataListInfo.getActionPerson());
			tv_receiveWeight.setText(dataListInfo.getReceiveWeight());
			tv_cropLevel.setText(dataListInfo.getCropLevel());
			tv_registdate.setText(dataListInfo.getRecordDate());
		}
	}

	@Override
	public void setListener() {
		tv_seeddate.setOnClickListener(this);
		tv_transplantdate.setOnClickListener(this);
		tv_registdate.setOnClickListener(this);
		tv_pro_save.setOnClickListener(this);
		tv_cropType.setOnClickListener(this);
		tv_receiveDate.setOnClickListener(this);

		et_cropPtype.setOnClickListener(this);

		tv_cropLevel.setOnClickListener(this);

	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){
		if (optionType ==1) {
			//播种移栽
			if (tv_seeddate.getText().toString()==null||"null".equals(tv_seeddate.getText().toString())||"".equals(tv_seeddate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "播种日期不能为空");
				return false ;
			}
			if (tv_transplantdate.getText().toString()==null||"null".equals(tv_transplantdate.getText().toString())||"".equals(tv_transplantdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "移栽日期不能为空");
				return false ;
			}
			/*if (tv_cropType.getText().toString()==null||"null".equals(tv_cropType.getText().toString())||"".equals(tv_cropType.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农作物名称不能为空");
				return false ;
			}
			if (et_cropPtype.getText().toString()==null||"null".equals(et_cropPtype.getText().toString())||"".equals(et_cropPtype.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农作物品种不能为空");
				return false ;
			}*/
			if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "实施人员不能为空");
				return false ;
			}
			if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "登记日期不能为空");
				return false ;
			}
		} else if (optionType ==2) {
			//田洋农事
			if ("2".equals(dataListInfo.getProductType())) {
				if (tv_temperValue.getText().toString()==null||"null".equals(tv_temperValue.getText().toString())||"".equals(tv_temperValue.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "气温不能为空");
					return false ;
				}
				if (tv_weatherState.getText().toString()==null||"null".equals(tv_weatherState.getText().toString())||"".equals(tv_weatherState.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "天气状况不能为空");
					return false ;
				}
				if (tv_productItem.getText().toString()==null||"null".equals(tv_productItem.getText().toString())||"".equals(tv_productItem.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "农事项目不能为空");
					return false ;
				}
				if (tv_receiveDate.getText().toString()==null||"null".equals(tv_receiveDate.getText().toString())||"".equals(tv_receiveDate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施日期不能为空");
					return false ;
				}
				if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施人员不能为空");
					return false ;
				}
				if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "登记日期不能为空");
					return false ;
				}
			} else if ("3".equals(dataListInfo.getProductType())){
				if (tv_manureName.getText().toString()==null||"null".equals(tv_manureName.getText().toString())||"".equals(tv_manureName.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "肥料名称不能为空");
					return false ;
				}
				if (tv_manureAmountUnit.getText().toString()==null||"null".equals(tv_manureAmountUnit.getText().toString())||"".equals(tv_manureAmountUnit.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "亩用量不能为空");
					return false ;
				}
				if (tv_manureUse.getText().toString()==null||"null".equals(tv_manureUse.getText().toString())||"".equals(tv_manureUse.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "用途不能为空");
					return false ;
				}
				if (tv_receiveDate.getText().toString()==null||"null".equals(tv_receiveDate.getText().toString())||"".equals(tv_receiveDate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施日期不能为空");
					return false ;
				}
				if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施人员不能为空");
					return false ;
				}
				if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "登记日期不能为空");
					return false ;
				}

			}else if ("4".equals(dataListInfo.getProductType())){
				if (tv_pesticideName.getText().toString()==null||"null".equals(tv_pesticideName.getText().toString())||"".equals(tv_pesticideName.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "农药名称不能为空");
					return false ;
				}
				if (tv_pesticideAmountUnit.getText().toString()==null||"null".equals(tv_pesticideAmountUnit.getText().toString())||"".equals(tv_pesticideAmountUnit.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "亩用量不能为空");
					return false ;
				}
				if (tv_diluteScale.getText().toString()==null||"null".equals(tv_diluteScale.getText().toString())||"".equals(tv_diluteScale.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "稀释倍数不能为空");
					return false ;
				}
				if (tv_diluteScale.getText().toString()==null||"null".equals(tv_diluteScale.getText().toString())||"".equals(tv_diluteScale.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "稀释倍数不能为空");
					return false ;
				}
				if (tv_receiveDate.getText().toString()==null||"null".equals(tv_receiveDate.getText().toString())||"".equals(tv_receiveDate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施日期不能为空");
					return false ;
				}
				if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "实施人员不能为空");
					return false ;
				}
				if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "登记日期不能为空");
					return false ;
				}
			}

		} else if (optionType ==5) {
			//田洋收成
			if (tv_receiveDate.getText().toString()==null||"null".equals(tv_receiveDate.getText().toString())||"".equals(tv_receiveDate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "收获日期不能为空");
				return false ;
			}
			/*if (tv_cropType.getText().toString()==null||"null".equals(tv_cropType.getText().toString())||"".equals(tv_cropType.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "作物名称不能为空");
				return false ;
			}
			if (et_cropPtype.getText().toString()==null||"null".equals(et_cropPtype.getText().toString())||"".equals(et_cropPtype.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "作物品种不能为空");
				return false ;
			}*/
			if (tv_receiveWeight.getText().toString()==null||"null".equals(tv_receiveWeight.getText().toString())||"".equals(tv_receiveWeight.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "收获重量不能为空");
				return false ;
			}
			if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "实施人员不能为空");
				return false ;
			}
			if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "登记日期不能为空");
				return false ;
			}
		}
		return true ;
	}
	/**
	 * 播种移栽管理修改完成接口
	 *
	 * @param position
	 * @param detailId
	 */
	public void getUpdateRecord(int productType, DataListInfo dataListInfo) {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put("userId", PreferencesUtils.getInt(
					getApplicationContext(), Constant.USERID)+"");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME,
					Constant.FAILURE));
			params.put("detail.detailId", dataListInfo.getDetailId());
			params.put("detail.actionPerson", tv_actionPerson.getText()
					.toString());
			params.put("detail.actionBak", tv_actionBak.getText().toString());
			params.put("detail.recordDate", tv_registdate.getText()
					.toString());
			if (productType == 1) {
				params.put("detail.seedDate", tv_seeddate.getText().toString());
				params.put("detail.moveDate", tv_transplantdate.getText()
						.toString());
				if (!"".equals(tv_cropType.getText().toString())&&!"".equals(tv_cropType.getText().toString())) {
					params.put("detail.cropType", tv_cropType.getText().toString());

				} else {
					params.put("detail.cropType", "");

				}
				params.put("detail.cropCode", dataListInfo.getCropCode());
				if (!"".equals(et_cropPtype.getText().toString())&&!"null".equals(et_cropPtype.getText()
						.toString())) {

					params.put("detail.cropPtype", et_cropPtype.getText()
							.toString());
				} else {
					params.put("detail.cropPtype","");
				}
			} else if (productType == 2) {
				params.put("detail.temperValue", tv_temperValue.getText()
						.toString());
				params.put("detail.weatherState", tv_weatherState.getText()
						.toString());
				params.put("detail.productItem", tv_productItem.getText()
						.toString());
				params.put("detail.agrDesc", tv_agrDesc.getText().toString());
				params.put("detail.cropState", tv_cropState.getText().toString());
				params.put("detail.receiveDate", tv_receiveDate.getText()
						.toString()+" "+DateUtils.getTimeShort());
			} else if (productType == 3) {
				params.put("detail.manureName", tv_manureName.getText()
						.toString());
				params.put("detail.manureAmountUnit", tv_manureAmountUnit.getText()
						.toString());
				params.put("detail.manureUse", tv_manureUse.getText().toString());
				params.put("detail.receiveDate", tv_receiveDate.getText()
						.toString()+" "+DateUtils.getTimeShort());
			} else if (productType == 4) {
				params.put("detail.pesticideName", tv_pesticideName.getText()
						.toString());
				params.put("detail.pesticideAmountUnit", tv_pesticideAmountUnit.getText()
						.toString());
				params.put("detail.diluteScale", tv_diluteScale.getText()
						.toString());
				params.put("detail.preventObj", tv_preventObj.getText()
						.toString());
				params.put("detail.receiveDate", tv_receiveDate.getText()
						.toString()+" "+DateUtils.getTimeShort());
			} else if (productType == 5) {
				params.put("detail.receiveDate", tv_receiveDate.getText()
						.toString());
				if (!"".equals(tv_cropType.getText().toString())&&!"null".endsWith(tv_cropType.getText().toString())) {
					params.put("detail.cropType", tv_cropType.getText().toString());

				}else{
					params.put("detail.cropType","");

				}
				if (!"".equals(et_cropPtype.getText().toString())&&!"null".equals(et_cropPtype.getText().toString())) {
					params.put("detail.cropPtype", et_cropPtype.getText().toString());

				} else {
					params.put("detail.cropPtype","");

				}
				if (!"".equals(tv_receiveWeight.getText().toString())&&!"null".equals(tv_receiveWeight.getText().toString())) {
					params.put("detail.receiveWeight", tv_receiveWeight.getText().toString());

				} else {
					params.put("detail.receiveWeight","");

				}
				params.put("detail.cropLevel", tv_cropLevel.getText().toString());
			}

			TwitterRestClient.get(Constant.UPDATERECORD, params,
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
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"保存成功");
									finish();
									overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
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
			case R.id.tv_seeddate:
				InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				immm.hideSoftInputFromWindow(tv_pro_save.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateRecordAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_seeddate.setText(date);
					}

				};
				break;
			case R.id.tv_transplantdate:
				InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(tv_pro_save.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateRecordAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_transplantdate.setText(date);
					}
				};
				break;
			case R.id.tv_registdate:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(tv_pro_save.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateRecordAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_registdate.setText(date);
					}
				};
				break;
			case R.id.tv_receiveDate:
				InputMethodManager immmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				immmm.hideSoftInputFromWindow(tv_pro_save.getWindowToken(), 0);
				new SelectDateTimePopWin(UpdateRecordAc.this, "", LayoutInflater
						.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_receiveDate.setText(date);
					}
				};
				break ;
			case R.id.tv_pro_save:
				getUpdateRecord(Integer.parseInt(dataListInfo.getProductType()), dataListInfo);
				break ;
			case R.id.tv_cropType:
				if (optionType == 5) {
					return ;
				}
				Intent i = new Intent();
				i.setClass(UpdateRecordAc.this, CropTypeNameIIAc.class);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			case R.id.tv_cropLevel:

				Intent cropLevelIntent = new Intent();
				cropLevelIntent.setClass(UpdateRecordAc.this, CropLevelAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fromWhichView", 0);
				cropLevelIntent.putExtras(bundle);
				startActivityForResult(cropLevelIntent, CROPLEVEL);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);

				break ;
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
					tv_cropType.setText(cropType);
					break;
				case CROPLEVEL:
					String croplevel = data.getStringExtra("croplevel");
					tv_cropLevel.setText(croplevel);
					break ;
				default:
					break;
			}

		}

	}

}
