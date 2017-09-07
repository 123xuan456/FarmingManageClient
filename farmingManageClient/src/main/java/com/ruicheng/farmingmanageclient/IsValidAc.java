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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.IsValidAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.IsValidInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 成本公式是否有效界面
 *
 * @author zhaobeibei
 *
 */
public class IsValidAc extends BaseActivity implements OnClickListener{
	private ListView listview_servicename;
	private Dialog loadingDialog;
	private List<Object> listAll;
	private TextView tv_title;
	private Intent intent;
	private int pos ;
	private LinearLayout imageView_Linearlayout_Back;
	private ImageView img_comment_back;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicestationname);

		init();
		setListAll();
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
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("成本公式是否有效");

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		imageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);

		imageView_Linearlayout_Back.setOnClickListener(this);
	}

	@Override
	public void setListener() {

		listview_servicename.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(), CostequationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("isValidInfo", (IsValidInfo)listAll.get(position));
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
			}
		});

	}

	/**
	 * 成本公式是否有效
	 *
	 */
	public void getAdjustStateFromulaValid() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					Constant.USERID,
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");
			params.put(Constant.USERNAME, PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
			params.put("fromula.formId", Constant.ANDROIDACCESSTYPE);
			params.put("fromula.formType", Constant.ANDROIDACCESSTYPE);
			params.put("fromula.isValid", Constant.ANDROIDACCESSTYPE);

			TwitterRestClient.get(Constant.ADJUSTSTATEFROMULAVALID, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONArray errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "失败");
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "获取数据成功");
							/*
							 * try { listAll =
							 * JSONUtils.getCropTypeName(response);
							 * setListAll(listAll); } catch (JSONException e) {
							 * e.printStackTrace(); }
							 */
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
						}
					});
		} else {
			ToastUtils.show(getApplicationContext(), "网络不佳,请重试...");
		}
	}

	private void setListAll() {
		listAll = new ArrayList<Object>();
		listAll.clear();
		for (int i = 0; i < 3; i++) {
			IsValidInfo isValidInfo = new IsValidInfo();
			if (i == 0) {
				isValidInfo.setIsValid("全部");
			} else if (i == 1) {
				isValidInfo.setIsValid("有效");
			} else if (i == 2) {
				isValidInfo.setIsValid("无效");
			}
			listAll.add(isValidInfo);
		}

		IsValidAdapter cropTypeNameAdapter = new IsValidAdapter(listAll,
				getApplicationContext());
		listview_servicename.setAdapter(cropTypeNameAdapter);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ImageView_Linearlayout_Back:
				finish();
				break;
			default:
				break;
		}
	}
}
