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
import com.ruicheng.farmingmanageclient.adapter.TianYangNumberAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.RecordInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.bean.StationInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.fragment.TianYangFertilizerFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYangPesticidesFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYnagDailyFragment;
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
 * 选择田洋编号的界面
 *
 * @author zhaobeibei
 *
 */
public class TianYangNumberAc extends BaseActivity {

	private ListView listview_tianyangnumber ;
	private StationData stationData ;
	private List<Object> listAll ;
	private List<StationInfo> listStationInfo ;
	private List<RecordInfo> listRecordInfo ;
	private Dialog loadingDialog ;
	private Bundle bundle ;
	private int optionType ;
	private String stationId ;
	private ImageView img_comment_back;
	private int fromwhichview ;  //0 PromanageListActivity界面
	//1 TianYagProAddActivity界面
	//2 TianYnagDailyFragment界面
	//5 AddTianYangManageAc界面
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tianyangnumber);
		bundle = getIntent().getExtras();
		if (bundle!=null) {
			optionType = bundle.getInt("optionType");
			stationId = bundle.getString("stationId");
			fromwhichview = bundle.getInt("fromwhichview");
		}
		init();
		setListener();
		getOptionPlough(stationId);


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
		listview_tianyangnumber = (ListView) findViewById(R.id.listview_tianyangnumber);

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
		listview_tianyangnumber.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				if (fromwhichview == 0) {
					i.setClass(getApplicationContext(), PromanageListActivity.class);
				} else if (fromwhichview ==1) {
					i.setClass(getApplicationContext(), TianYagProAddActivity.class);
				} else if (fromwhichview ==2) {
					i.setClass(getApplicationContext(), TianYnagDailyFragment.class);
				}else if (fromwhichview ==3) {
					i.setClass(getApplicationContext(), TianYangFertilizerFragment.class);
				}else if (fromwhichview ==4) {
					i.setClass(getApplicationContext(), TianYangPesticidesFragment.class);
				}else if (fromwhichview ==5) {
					i.setClass(getApplicationContext(), AddTianYangManageAc.class);
				}
				i.putExtra("PloughCode", ((PloughListInfo)listAll.get(position)).getPloughCode());
				i.putExtra("PLOUGHLIST", ((PloughListInfo)listAll.get(position)));
				setResult(RESULT_OK, i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});
	}
	private void setListAll(List<Object> listAll) {

		TianYangNumberAdapter serviceNameAdapter = new TianYangNumberAdapter(listAll, getApplicationContext());
		listview_tianyangnumber.setAdapter(serviceNameAdapter);

	}
	public void getOptionPlough(String stationId){
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			if (fromwhichview != 5) {
				params.put("optionType",optionType+"");
			}
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put(Constant.USERID, PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put(Constant.USERNAME,PreferencesUtils.getString(getApplicationContext(), Constant.USERNAME));
			params.put("stationId",stationId);
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

						listAll  = JSONUtils.getploughListInfo(response);
						setListAll(listAll);

						listStationInfo = JSONUtils.getStationAllInfo(response);
						for (int i = 0; i <listStationInfo.size(); i++) {
							ServiceNameHandler.stationList.add(listStationInfo.get(i));
						}
						for (int j = 0; j < listAll.size(); j++) {
							ServiceNameHandler.ploughList.add(listAll.get(j));
						}
						listRecordInfo  = JSONUtils.getRecordInfo(response);
						for (int h = 0; h <listRecordInfo.size(); h++) {
							ServiceNameHandler.recordList.add(listRecordInfo.get(h));
						}
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
}
