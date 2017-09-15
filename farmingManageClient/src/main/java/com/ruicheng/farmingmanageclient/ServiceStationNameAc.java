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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.ServiceNameAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.fragment.TianYangFertilizerFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYangPesticidesFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYnagDailyFragment;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
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
 * 服务站名称界面
 *
 * @author zhaobeibei
 *
 */
public class ServiceStationNameAc extends BaseActivity {

	private ListView listview_servicename ;
	private Dialog loadingDialog ;
	private List<Object> listAll ;
	private int optionType ;
	private Intent intent ;
	private int fromwhichview;    //0 PromanageListActivity 界面
	//1 TianYagProAddActivity 界面
	//2 TianYnagDailyFragment 界面
	//5 ServiceManageAc
	private Bundle bundle ;
	private ImageView img_comment_back;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicestationname);
		intent = getIntent();
		bundle = intent.getExtras();
		if (bundle!=null) {
			fromwhichview = bundle.getInt("fromwhichview",Constant.FAILUREINT);
			if (fromwhichview!=5) {
				optionType = bundle.getInt("optionType",Constant.FAILUREINT);
			}
		}
		init() ;
		setListener();

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
		getOptionPlough();
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
		listview_servicename.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				if (fromwhichview == 0) {
					i.setClass(getApplicationContext(), PromanageListActivity.class);
				} else if (fromwhichview ==1) {

					i.setClass(getApplicationContext(), TianYagProAddActivity.class);
				}else if (fromwhichview ==2) {

					i.setClass(getApplicationContext(), TianYnagDailyFragment.class);
				}else if (fromwhichview ==3) {

					i.setClass(getApplicationContext(), TianYangFertilizerFragment.class);
				}else if (fromwhichview ==4) {

					i.setClass(getApplicationContext(), TianYangPesticidesFragment.class);
				}else if (fromwhichview ==5) {

					i.setClass(getApplicationContext(), ServiceManageAc.class);
				}else if (fromwhichview ==6) {

					i.setClass(getApplicationContext(), AddProBaseInfoAc.class);
				}else if (fromwhichview ==7) {

					i.setClass(getApplicationContext(), AddTianYangManageAc.class);
				}else if (fromwhichview ==8) {

					i.setClass(getApplicationContext(), TianYangManageAc.class);
				}else if (fromwhichview ==9) {

					i.setClass(getApplicationContext(), UpdateTianYangManageAc.class);
				}
				i.putExtra("servicename", ((StationData)listAll.get(position)).getStationName());
				i.putExtra("stationId", ((StationData)listAll.get(position)).getStationId());
				i.putExtra("stationCode", ((StationData)listAll.get(position)).getStationCode());
				i.putExtra("stationInfo", ((StationData)listAll.get(position)));
				setResult(RESULT_OK, i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});

	}
	public void getOptionPlough(){
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			if (fromwhichview !=5&&fromwhichview !=6&&fromwhichview !=7&&fromwhichview !=8&&fromwhichview !=9) {
				params.put("optionType",optionType+"");
			}
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put(Constant.USERNAME,PreferencesUtils.getString(getApplicationContext(), Constant.USERNAME));
			TwitterRestClient.get(Constant.OPTIONPLOUGH, params, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
									  String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);

					if (loadingDialog.isShowing()) {
						loadingDialog.dismiss();
					}
					ToastUtils.show(getApplicationContext(),"获取数据失败");
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
						listAll = JSONUtils.getStationInfo(response);
						setListAll(listAll);

					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (loadingDialog.isShowing()) {
						loadingDialog.dismiss();
					}
				}
			});
		}else{
			ToastUtils.show(getApplicationContext(), "网络不佳,请重试...");
		}
	}

	private void setListAll(List<Object> listAll) {

		ServiceNameAdapter serviceNameAdapter = new ServiceNameAdapter(listAll, getApplicationContext());
		listview_servicename.setAdapter(serviceNameAdapter);

	}
}
