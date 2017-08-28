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
import com.ruicheng.farmingmanageclient.adapter.CropPtypeNameAdapter;
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
/**
 * 作物品种界面
 *
 * @author zhaobeibei
 *
 */
public class CropPtypeNameAc extends BaseActivity {

	private ListView listview_dc ;
	private Dialog loadingDialog ;
	private CropPtypeNameAdapter cropPtypeNameAdapter ;
	private TextView tv_title ;
	private List<CropTypeNameInfo> listAll;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_district);
		init();
		getQueryProduct();
		setListener();



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
								ServiceNameHandler.cropTypeNameList.clear();
								for (int i = 0; i <listAll.size(); i++) {
									ServiceNameHandler.cropTypeNameList.add(listAll.get(i));
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

	private void setListAll(List<CropTypeNameInfo> listAll) {

		CropPtypeNameAdapter cropTypeNameAdapter = new CropPtypeNameAdapter(listAll,
				getApplicationContext());
		listview_dc.setAdapter(cropTypeNameAdapter);

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
		listview_dc = (ListView) findViewById(R.id.listview_dc);
		loadingDialog = DialogUtils.requestDialog(this);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("选择作物品种");

	}

	@Override
	public void setListener() {
		listview_dc.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(),TianYagProAddActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("cropPtype",((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position-1)));
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
			}
		});

	}
	public void setListData(List<CropTypeNameInfo> listAll){
		cropPtypeNameAdapter = new CropPtypeNameAdapter(listAll, getApplicationContext());
		listview_dc.setAdapter(cropPtypeNameAdapter);
	}

}
