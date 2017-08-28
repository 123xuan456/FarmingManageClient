/**
 *
 */
package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.AgriRecordTianYnagAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PloughListInfoArrayInfo;
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

import java.util.List;

/**
 * 农事记录界面
 *
 * @author zhaobeibei
 *
 */
public class AgriRecordTianYnagInfoAc extends BaseActivity implements OnClickListener{

	private Bundle bundle ;
	private int pos ;
	private PloughListInfoArrayInfo ploughListInfoArrayInfo;
	private Dialog loadingDialog ;
	private ListView listview_dc ;
	private AgriRecordTianYnagAdapter agriRecordTianYnagAdapter;
	private LinearLayout ImageView_Linearlayout_Back ;
	private ImageView img_comment_back ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_agrirecordtianynaginfo);

		bundle = getIntent().getExtras();
		if (bundle!=null) {
			ploughListInfoArrayInfo = (PloughListInfoArrayInfo) bundle.getSerializable("ploughListInfoArrayInfo");
			pos = bundle.getInt("pos");

		}
		init();
		setListener();
		getQueryAllRecord();
	}

	@Override
	public void init() {
		loadingDialog = DialogUtils.requestDialog(this);
		listview_dc = (ListView) findViewById(R.id.listview_dc);

		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);

	}

	/* (non-Javadoc)
	 * @see com.ruicheng.farmingmanageclient.base.BaseActivity#setListener()
	 */
	@Override
	public void setListener() {
		ImageView_Linearlayout_Back.setOnClickListener(this);
	}
	public void getQueryAllRecord() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("ploughId",ploughListInfoArrayInfo.getPloughId());
			params.put("pager.num", Constant.NUM+"");
			params.put("pager.size", Constant.SIZE+"");

			TwitterRestClient.get(Constant.QUERYALLRECORD, params,
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
									ServiceNameHandler.allList = JSONUtils.getQueryAllRecord(response);

									if (ServiceNameHandler.allList.size()==0) {
										ToastUtils.show(getApplicationContext(), "没有农事记录数据");
									} else {
										setListData(ServiceNameHandler.allList) ;
									}



								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode ==event.KEYCODE_BACK) {
			/*android.os.Process.killProcess(android.os.Process.myPid()); */ 
			/*startActivity(new Intent(this, MainActivity.class));
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);*/
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void setListData(List<Object> listAll){
		agriRecordTianYnagAdapter = new AgriRecordTianYnagAdapter(listAll, getApplicationContext());
		listview_dc.setAdapter(agriRecordTianYnagAdapter);
	}

	@Override
	public void onClick(View v) {
		img_comment_back.setVisibility(View.GONE);
		finish();
		overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
	}
}
