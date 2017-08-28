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

/**
 * 农作物名称
 *
 * @author zhaobeibei
 *
 */
public class CropTypeNameAc extends BaseActivity {

	private ListView listview_servicename;
	private Dialog loadingDialog;
	private List<CropTypeNameInfo> listAll;
	private TextView tv_title ;
	private Intent intent ;
	private int fromWhichView ; //0 田洋播种界面  1成本公式界面  2 添加成本公式 3 生产计划
	// 4  UpdateProplanAc.class 5 ProplantableActivity.class
	// 6 AddProBaseInfoAc
	private int nameType ;
	private ImageView img_comment_back;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicestationname);
		intent = getIntent();
		if (intent!=null) {
			fromWhichView = intent.getIntExtra("fromWhichView", -1);
			nameType = intent.getIntExtra("nameType", -1);

		}
		init();
		setListener();
		getQueryProduct();
		/*if (ServiceNameHandler.cropTypeNameList!=null&&ServiceNameHandler.cropTypeNameList.size()>0) {
			setListAll(ServiceNameHandler.cropTypeNameList);
		} else {
		}*/

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
				if (fromWhichView == 0) {
					i.setClass(CropTypeNameAc.this, TianYagProAddActivity.class);
				} else if (fromWhichView == 1){
					i.setClass(CropTypeNameAc.this, CostequationActivity.class);
				}else if(fromWhichView == 2){
					i.setClass(CropTypeNameAc.this, AddCostequation.class);
				}else if(fromWhichView ==3){
					i.setClass(CropTypeNameAc.this, ProplanActivity.class);
					if (nameType ==0) {
						i.putExtra("nameType",0);
					} else {
						i.putExtra("nameType",1);
					}
				}else if(fromWhichView ==4){
					i.setClass(CropTypeNameAc.this, UpdateProplanAc.class);
				}else if(fromWhichView ==5){
					i.setClass(CropTypeNameAc.this, ProplantableActivity.class);
				}else if(fromWhichView ==6){
					i.setClass(CropTypeNameAc.this, AddProBaseInfoAc.class);
				}else if(fromWhichView ==7){
					i.setClass(CropTypeNameAc.this, AddProplanAc.class);
				}else if(fromWhichView ==8){
					i.setClass(CropTypeNameAc.this, AddDetailAc.class);
				}else if(fromWhichView ==8){
					i.setClass(CropTypeNameAc.this, AddCropActivity.class);
				}
				i.putExtra("cropType",((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position)).getDicValue());
				i.putExtra("dicCode",((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position)).getDicCode());
				i.putExtra("dicValue", ((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position)).getDicValue());
				i.putExtra("dicId", ((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position)).getDicId());
				i.putExtra("cropPtype", ((CropTypeNameInfo)ServiceNameHandler.cropTypeNameList.get(position)).getCropPtype());

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

		CropTypeNameAdapter cropTypeNameAdapter = new CropTypeNameAdapter(listAll,
				getApplicationContext());
		listview_servicename.setAdapter(cropTypeNameAdapter);

	}
}
