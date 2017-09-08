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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.EditPloughPageInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfoArrayInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
/**
 * 修改田洋管理信息
 *
 * @author zhaobeibei
 *
 */
public class UpdateTianYangManageAc extends BaseActivity {
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private EditText  et_ploughArea,
			et_soilState,et_ploughFarm;
	private Dialog loadingDialog;
	private Button btn_confirm ;
	private TextView et_station,et_ploughCode;

	private Bundle bundle ;
	private PloughListInfoArrayInfo ploughListInfoArrayInfo;

	private final int PROSERVICENAME = 1;
	private String stationId;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updatetianyangmanage);

		bundle = getIntent().getExtras();
		if (bundle!=null) {
			ploughListInfoArrayInfo = (PloughListInfoArrayInfo) bundle.getSerializable("ploughListInfoArrayInfo");
		}
		stationId = ploughListInfoArrayInfo.getPloughId() ;
		init();
		setListener();
		//初始化信息
		getEditPloughPage();
	}

	@Override
	public void init() {
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		et_station = (TextView) findViewById(R.id.et_station);
		et_ploughCode = (TextView) findViewById(R.id.et_ploughCode);
		et_ploughFarm = (EditText) findViewById(R.id.et_ploughFarm);
		et_ploughArea = (EditText) findViewById(R.id.et_ploughArea);
		et_soilState = (EditText) findViewById(R.id.et_soilState);

		loadingDialog = DialogUtils.requestDialog(this);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);

		et_station.setText(ploughListInfoArrayInfo.getStation());


	}

	@Override
	public void setListener() {
		IntentViewListener intentViewListener = new IntentViewListener();
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);

		btn_confirm.setOnClickListener(intentViewListener);

		et_station.setOnClickListener(intentViewListener);

	}
	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					finish();
					break;
				case R.id.btn_confirm:
					//提交保存数据
					getEditPlough();
					break ;
				case R.id.et_station:
					Intent intent1 = new Intent();
					intent1.setClass(UpdateTianYangManageAc.this,
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview",9);
					intent1.putExtras(bundle);
					startActivityForResult(intent1, PROSERVICENAME);
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
					break;
				default:
					break;
			}
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

//		if (keyCode == event.KEYCODE_BACK) {
//			/* android.os.Process.killProcess(android.os.Process.myPid()); */
//			startActivity(new Intent(this, TianYangManageAc.class));
//			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
//		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 田洋信息修改获取信息
	 *
	 */
	public void getEditPloughPage() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("ploughId",ploughListInfoArrayInfo.getPloughId());
			TwitterRestClient.get(Constant.EDITPLOUGHPAGE, params,
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

									EditPloughPageInfo editPloughPageInfo= JSONUtils.getEditPloughPage(response);
									et_ploughCode.setText(editPloughPageInfo.getPloughCode());
									et_ploughFarm.setText(editPloughPageInfo.getPloughFarm());
									et_ploughArea.setText(editPloughPageInfo.getPloughArea());
									et_soilState.setText(editPloughPageInfo.getSoilState());
//									getIsHaveSameCode(editPloughPageInfo.getStationId(), editPloughPageInfo.getPloughCode(), editPloughPageInfo.getPloughArea());

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	public void getIsHaveSameCode(String stationId,String ploughCode,String ploughArea) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("plough.stationId",stationId);
			params.put("plough.ploughCode", ploughCode);
			params.put("plough.ploughArea", ploughArea);
			params.put("plough.ploughFarm","");
			params.put("plough.soilState","");
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
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){
		if (et_station.getText().toString()==null||"null".equals(et_station.getText().toString())||"".equals(et_station.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "所属服务站不能为空");
			return false ;
		}
		if (et_ploughCode.getText().toString()==null||"null".equals(et_ploughCode.getText().toString())||"".equals(et_ploughCode.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "田地编号不能为空");
			return false ;
		}
		if (et_ploughArea.getText().toString()==null||"null".equals(et_ploughArea.getText().toString())||"".equals(et_ploughArea.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "田地面积不能为空");
			return false ;
		}
		return true ;
	}
	/**
	 * 田洋信息修改信息保存
	 *
	 */
	public void getEditPlough() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("plough.ploughId",ploughListInfoArrayInfo.getPloughId());
			params.put("plough.stationId",stationId);
			params.put("plough.ploughCode",et_ploughCode.getText().toString());
			params.put("plough.ploughArea",et_ploughArea.getText().toString());
			if (!"".equals(et_ploughFarm.getText().toString())&&!"null".equals(et_ploughFarm.getText().toString())) {
				params.put("plough.ploughFarm",et_ploughFarm.getText().toString());
			} else {
				params.put("plough.ploughFarm","");
			}
			params.put("plough.soilState",et_soilState.getText().toString());
			TwitterRestClient.get(Constant.EDITPLOUGH, params,
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
									ToastUtils.show(getApplicationContext(), "修改成功");
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
}
