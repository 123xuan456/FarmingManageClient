package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.CnameAdapter;
import com.ruicheng.farmingmanageclient.adapter.DistrictAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.db.LoginInfoHelper;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoginDistrictAc extends BaseActivity {

	private PullToRefreshListView listview_dc ;
	private Dialog loadingDialog ;
	private CnameAdapter cnameAdapter ;
	private ImageView img_comment_back;
	LoginInfoHelper loginInfoHelper ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.item_listview);

		init();
		setListener();
		ServiceNameHandler.allList.clear();
		loginInfoHelper = new LoginInfoHelper(
				getApplicationContext());

		ServiceNameHandler.allList = loginInfoHelper.selectLoginfo();
		getAcquireAllDc();
//		if (ServiceNameHandler.allList.size()>0) {
//			DistrictAdapter DistrictAdapter = new DistrictAdapter(ServiceNameHandler.allList, getApplicationContext());
//			listview_dc.setAdapter(DistrictAdapter);
//		} else {
//			getAcquireAllDc();
//		}

	}

	@Override
	public void init() {
		listview_dc = (PullToRefreshListView) findViewById(R.id.listview_dc);
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
	}

	@Override
	public void setListener() {
		listview_dc.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(),LoginDistrictAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("acquireAllDcInfo",((AcquireAllDcInfo)ServiceNameHandler.allList.get(position-1)));
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
			}
		});
		listview_dc.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getAcquireAllDc();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getAcquireAllDc();
			}
		});

	}
	/**
	 *
	 * 获取区域列表
	 */
	public void getAcquireAllDc() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			String url = TwitterRestClient.BASE_URL ;
			TwitterRestClient.get(Constant.ACQUIREALLDC, params,
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
											  JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							ToastUtils.show(getApplicationContext(), "数据获取...");
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ServiceNameHandler.allList.clear();
							ServiceNameHandler.allList = JSONUtils
									.getAcquireAllDcInfo(response);
							loginInfoHelper = new LoginInfoHelper(
									getApplicationContext());
							loginInfoHelper.deleteLoginInfo();
							loginInfoHelper
									.saveLoginfo(ServiceNameHandler.allList);
							DistrictAdapter DistrictAdapter = new DistrictAdapter(ServiceNameHandler.allList, getApplicationContext());
							listview_dc.setAdapter(DistrictAdapter);
							listview_dc.onRefreshComplete();
						}
					});
		}

	}
}
