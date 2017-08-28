package com.ruicheng.farmingmanageclient;

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
import com.ruicheng.farmingmanageclient.bean.BaseStationInfo;
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
/**
 * 基础信息--服务站管理界面
 *
 * @author zhaobeibei
 *
 */
public class ServiceManageAc extends BaseActivity {

	private Button btn_query;
	private TextView tv_pro_add;
	private  final int MODIFY = 0 ;
	private LinearLayout ImageView_Linearlayout_Back ;
	private ImageView img_comment_back ;
	private Dialog loadingDialog ;
	private boolean isQueryy ;
	private EditText et_stationAddr ;
	private TextView tv_servicename,tv_dcName;
	private final int PROSERVICENAME =0;
	private String stationId;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicemanage);

		init();
		setListener();
		/*getQueryServerStation(false);*/

	}

	@Override
	public void init() {
		btn_query = (Button) findViewById(R.id.btn_query);
		tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		loadingDialog = DialogUtils.requestDialog(this);

		et_stationAddr =(EditText)findViewById(R.id.et_stationAddr);

		tv_servicename = (TextView)findViewById(R.id.tv_servicename);
		tv_dcName = (TextView)findViewById(R.id.tv_dcName);
		tv_dcName.setText(PreferencesUtils.getString(getApplicationContext(),
				Constant.DCNAME));

	}

	@Override
	public void setListener() {
		// 跳转界面监听
		IntentViewListener intentViewListener = new IntentViewListener();
		btn_query.setOnClickListener(intentViewListener);
		tv_pro_add.setOnClickListener(intentViewListener);
		MyBackListener myBackListener = new MyBackListener();
		ImageView_Linearlayout_Back.setOnClickListener(myBackListener);

		tv_servicename.setOnClickListener(intentViewListener);
	}
	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_query:
					getQueryServerStation(true);
					break;
				case R.id.tv_pro_add:
					Intent i = new Intent();
					i.setClass(ServiceManageAc.this,AddProBaseInfoAc.class);
					startActivityForResult(i, MODIFY);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.tv_servicename:
					Intent intent = new Intent();
					intent.setClass(ServiceManageAc.this,
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview",5);
					intent.putExtras(bundle);
					startActivityForResult(intent, PROSERVICENAME);
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
					tv_servicename.setText(servicename);
					stationId = data.getStringExtra("stationId");
					break;
				default:
					break;
			}
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
	public class MyBackListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			img_comment_back.setVisibility(View.GONE);
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
	}
	public void getQueryServerStation(boolean isQuery) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("dcId",PreferencesUtils.getString(getApplicationContext(), Constant.DCID)
			);
			params.put("pager.num",Constant.NUM);
			params.put("pager.size",Constant.SIZE);
			params.put("serverStation.stationAddr",et_stationAddr.getText().toString());

			if (isQuery == true) {
				isQueryy = isQuery ;
				if (!"".equals(tv_servicename.getText().toString())) {
					params.put("serverStation.stationName",tv_servicename.getText().toString());

				} else {
					params.put("serverStation.stationName","");

				}
				params.put("serverStation.dcId",PreferencesUtils.getString(
						getApplicationContext(), Constant.DCID));
				if (!"".equals(et_stationAddr.getText().toString())) {
					params.put("serverStation.stationAddr",et_stationAddr.getText().toString());

				}else{
					params.put("serverStation.stationAddr","");
				}
			}

			TwitterRestClient.get(Constant.QUERYSERVERDTATION, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);

							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "获取数据失败");
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
									ServiceNameHandler.allList.clear();
									ServiceNameHandler.allList  = JSONUtils.getQueryServerStation(response);
									if (isQueryy == true) {
										if (ServiceNameHandler.allList!=null&&ServiceNameHandler.allList.size()>0) {
											Intent i = new Intent();
											i.setClass(getApplicationContext(), QueryProBaseInfoAc.class);
											Bundle bundle = new Bundle();
											bundle.putSerializable("baseStationInfo",(BaseStationInfo)ServiceNameHandler.allList.get(0));
											bundle.putInt("position", 0);
											i.putExtras(bundle);

											startActivity(i);
										}
									}

								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}

						}
					});
		} else {
			ToastUtils.show(getApplicationContext(), "网络不佳,请重试...");
		}
	}
}
