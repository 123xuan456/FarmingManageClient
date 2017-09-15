package com.ruicheng.farmingmanageclient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

/**
 *
 * 基础信息--添加田洋管理界面
 *
 * @author zhaobeibei
 *
 */
public class AddTianYangManageAc extends BaseActivity {
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private EditText    et_ploughFarm, et_ploughArea,
			et_ploughCode,et_soilState;
	private Dialog loadingDialog;
	private Button btn_confirm ;

	private TextView et_station ;
	private final int PROSERVICENAME = 1;
	private final int PROTIANYANGNUMBER = 2;
	private String stationId ;
	private String stationCode;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_addtianyangmanage);

		init();
		setListener();

	}

	@Override
	public void init() {
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		et_station = (TextView) findViewById(R.id.et_station);
		et_ploughCode = (EditText) findViewById(R.id.et_ploughCode);
		et_ploughFarm = (EditText) findViewById(R.id.et_ploughFarm);
		et_ploughArea = (EditText) findViewById(R.id.et_ploughArea);
		et_soilState = (EditText) findViewById(R.id.et_soilState);

		loadingDialog = DialogUtils.requestDialog(this);

		btn_confirm = (Button) findViewById(R.id.btn_confirm);

	}

	@Override
	public void setListener() {
		IntentViewListener intentViewListener = new IntentViewListener();
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);

		btn_confirm.setOnClickListener(intentViewListener);
		et_station.setOnClickListener(intentViewListener);
		/*et_ploughCode.setOnClickListener(intentViewListener);
		*/

		/*et_ploughCode.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {

				if (!arg1) {
					getIsHaveSameCode();
				}
			}
		});*/

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
//					Intent intent = new Intent();
//					intent.setClass(getApplicationContext(), TianYangManageAc.class);
//					startActivity(intent);
//					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					finish();
					break;
				case R.id.btn_confirm:
					//提交保存数据
					if (!estimateInfoIsNullUtils()) {
						return;
					}
					getIsHaveSameCode();
					break ;
				case R.id.et_station:
					Intent stationIntent = new Intent();
					stationIntent.setClass(AddTianYangManageAc.this,
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview",7);
					stationIntent.putExtras(bundle);
					startActivityForResult(stationIntent, PROSERVICENAME);
					break;
				case R.id.et_ploughCode:
				/*if ("".equals(et_station.getText().toString())||"null".equals(et_station.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "请先选择服务站");
					return ;
				}*/
				/*Intent ii = new Intent();
				ii.setClass(AddTianYangManageAc.this, TianYangNumberAc.class);
				Bundle bund = new Bundle();
//				bund.putInt("optionType", optionType);
				bund.putString("stationId", stationId);
				bund.putInt("fromwhichview", 0);
				ii.putExtras(bund);
				startActivityForResult(ii, PROTIANYANGNUMBER);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);*/
					break ;
				default:
					break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case PROSERVICENAME:
					String servicename = data.getStringExtra("servicename");
					et_station.setText(servicename);
					stationId = data.getStringExtra("stationId");
					stationCode = data.getStringExtra("stationCode");
					break;
				case PROTIANYANGNUMBER:
					String pro_tianyangnumber = data.getStringExtra("PloughCode");
					et_ploughCode.setText(pro_tianyangnumber);
//				ploughListInfo = (PloughListInfo) data.getSerializableExtra("PLOUGHLIST");
					break;
				default:
					break;
			}
		}

	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == event.KEYCODE_BACK) {
//			/* android.os.Process.killProcess(android.os.Process.myPid()); */
//			startActivity(new Intent(this, TianYangManageAc.class));
//			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
//			finish();
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils() {
		if (et_station.getText().toString() == null
				|| "null".equals(et_station.getText().toString())
				|| "".equals(et_station.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "所属服务站不能为空");
			return false;
		}
		if (et_ploughCode.getText().toString() == null
				|| "null".equals(et_ploughCode.getText().toString())
				|| "".equals(et_ploughCode.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "田地编号不能为空");
			return false;
		}
		if (et_ploughArea.getText().toString() == null
				|| "null".equals(et_ploughArea.getText().toString())
				|| "".equals(et_ploughArea.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "田地面积不能为空");
			return false;
		}
		return true;
	}

	public void getSavePlough() {

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
			params.put("plough.stationId",stationId);
			params.put("plough.ploughCode",et_ploughCode.getText().toString());
			params.put("plough.ploughArea",et_ploughArea.getText().toString());
			params.put("plough.ploughFarm",et_ploughFarm.getText().toString());
			params.put("plough.soilState",et_soilState.getText().toString());

			TwitterRestClient.get(Constant.SAVEPLOUGH, params,
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
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(),
											"保存信息失败");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
									ToastUtils.show(getApplicationContext(),
											"保存信息成功");
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
	public void getIsHaveSameCode() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));

			params.put("plough.stationId",stationId);
			params.put("plough.ploughCode",et_ploughCode.getText().toString());
			params.put("plough.ploughArea",et_ploughArea.getText().toString());
			params.put("plough.ploughFarm",et_ploughFarm.getText().toString());
			params.put("plough.soilState",et_soilState.getText().toString());

			TwitterRestClient.get(Constant.ISHAVESAMECODE, params,
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
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(),
											"田地编号重复，请重新输入");
									et_ploughCode.setText("");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
									/*ToastUtils.show(getApplicationContext(),
											"保存信息成功");*/
									getSavePlough();
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
