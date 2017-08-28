package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.R.id;
import com.ruicheng.farmingmanageclient.adapter.PloughDetailAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PloughListDetailInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * 获取田洋明细的接口
 *
 * @author zhaobeibei
 *
 */
public class PloughDetailAc extends BaseActivity {
	private ListView listview_dc ;
	private Dialog loadingDialog ;
	private List<Object> listAll ;
	private Bundle bundle ;
	private String stationId ;
	private String[] ploughListInfoArrayInfo ;
	private TextView tv_title ;
	private ImageView img_comment_back;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_district);

		bundle = getIntent().getExtras();
		if (bundle !=null) {
			stationId = bundle.getString("stationId");
		}
		init();
		setListener();
		getPloughList();
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
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});
		listview_dc = (ListView) findViewById(R.id.listview_dc);
		loadingDialog = DialogUtils.requestDialog(this);

		tv_title = (TextView) findViewById(id.tv_title);
		tv_title.setText("选择田洋编号");
	}

	@Override
	public void setListener() {
		listview_dc.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(),AddDetailAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("ploughListDetailInfo",((PloughListDetailInfo)listAll.get(position)));
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});
	}
	public void getPloughList() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("plough.stationId",stationId);
			TwitterRestClient.get(Constant.PLOUGHLIST, params,
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
									listAll = JSONUtils.getPloughListDetailInfo(response);
									setListData(listAll);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}
	}
	public void setListData(List<Object> listAll){

		PloughDetailAdapter ploughDetailAdapter = new com.ruicheng.farmingmanageclient.adapter.PloughDetailAdapter(listAll, getApplicationContext());
		listview_dc.setAdapter(ploughDetailAdapter);

	}
}
