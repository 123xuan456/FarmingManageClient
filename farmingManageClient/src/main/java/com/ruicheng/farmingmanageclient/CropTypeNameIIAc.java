package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.CropTypeNameAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class CropTypeNameIIAc extends BaseActivity{
	private ListView listview_servicename;
	private Dialog loadingDialog;
	private List<CropTypeNameInfo> listAll;
	private TextView tv_title ;
	private boolean isNullcropTypeNameList ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicestationname);
		init();
		setListener();
		if (ServiceNameHandler.cropTypeNameList!=null&&ServiceNameHandler.cropTypeNameList.size()>=0) {
			setListAll(ServiceNameHandler.cropTypeNameList);
			isNullcropTypeNameList = true ;
		} else {
			getQueryProduct();
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
		listview_servicename = (ListView) findViewById(R.id.listview_servicename);
		loadingDialog = DialogUtils.requestDialog(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("农作物名称");
	}

	@Override
	public void setListener() {
		listview_servicename.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
//				i.setClass(CropTypeNameAc.this, TianYagProAddActivity.class);
				if (isNullcropTypeNameList == true) {
					i.putExtra("cropType",((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position-1)).getDicValue());
					i.putExtra("dicCode",((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position-1)).getDicCode());
				} else {
					i.putExtra("cropType",((CropTypeNameInfo)listAll.get(position-1)).getDicValue());
					i.putExtra("dicCode",((CropTypeNameInfo)listAll.get(position-1)).getDicCode());
				}
				setResult(RESULT_OK, i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});
	}
	public void getQueryProduct() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType","mobile");
			params.put(
					Constant.USERID,
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");
			params.put(Constant.USERNAME, PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));

			TwitterRestClient.get(Constant.QUERYPRODUCT, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONArray errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "获取数据失败");
						}
						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							try {
								listAll = JSONUtils.getCropTypeName(response);
								setListAll(listAll);
								isNullcropTypeNameList = false ;
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
	private void setListAll(List<CropTypeNameInfo> listAll) {

		CropTypeNameAdapter cropTypeNameAdapter = new CropTypeNameAdapter(listAll,
				getApplicationContext());
		listview_servicename.setAdapter(cropTypeNameAdapter);

	}

}
