package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.EditPloughPageInfo;
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
/**
 * 查看服务站田洋信息
 *
 * @author zhaobeibei
 *
 */
public class CheckServiceTianYnagInfoAc extends BaseActivity {

	private Bundle bundle ;
	private PloughListInfoArrayInfo ploughListInfoArrayInfo ;
	private int pos ;
	private Button btn_last ,btn_next;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private TextView  et_ploughArea,
			et_soilState,et_ploughFarm;
	private TextView et_station,et_ploughCode;
	private Dialog loadingDialog;
	private EditPloughPageInfo editPloughPageInfo ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_checkservicetianynaginfo);

		bundle = getIntent().getExtras();
		if (bundle!=null) {
			ploughListInfoArrayInfo = (PloughListInfoArrayInfo) bundle.getSerializable("ploughListInfoArrayInfo");
			pos = bundle.getInt("position");
		}
		init();
		setListener();
		getEditPloughPage();
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
		et_station = (TextView) findViewById(R.id.et_station);
		et_ploughCode = (TextView) findViewById(R.id.et_ploughCode);
		et_ploughFarm = (TextView) findViewById(R.id.et_ploughFarm);
		et_ploughArea = (TextView) findViewById(R.id.et_ploughArea);
		et_soilState = (TextView) findViewById(R.id.et_soilState);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		loadingDialog = DialogUtils.requestDialog(this);
	}

	@Override
	public void setListener() {
		IntentViewListener intentViewListener = new IntentViewListener();
		btn_last.setOnClickListener(intentViewListener);
		btn_next.setOnClickListener(intentViewListener);
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);

	}
	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					finish();
					break;
				case R.id.btn_last:
					if (pos == 0) {
						ToastUtils.show(getApplicationContext(), "当前已是第一条");
						btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));

						return ;
					}else{
						btn_next.setBackgroundColor(Color.parseColor("#009933"));
						pos -- ;
						getEditPloughPage();
					}

					break ;
				case R.id.btn_next:
					btn_last.setBackgroundColor(Color.parseColor("#009933"));
					if (pos ==ServiceNameHandler.allList.size()-1) {
						ToastUtils.show(getApplicationContext(), "无更多数据..");
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						return ;
					}else{
						pos ++ ;
						getEditPloughPage();
						if (pos == 0) {
							btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
						}else if (pos ==ServiceNameHandler.allList.size()-1){
							btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						}
					}
					break ;
				default:
					break;
			}
		}
	}
	public void setDataToView(){
		et_ploughCode.setText(editPloughPageInfo.getPloughCode());
		et_ploughFarm.setText(editPloughPageInfo.getPloughFarm());
		et_ploughArea.setText(editPloughPageInfo.getPloughArea());
		et_soilState.setText(editPloughPageInfo.getSoilState());
		et_station.setText(ploughListInfoArrayInfo.getStation());

	}
	public void getEditPloughPage() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));

			params.put("ploughId",((PloughListInfoArrayInfo)ServiceNameHandler.allList.get(pos)).getPloughId());
			TwitterRestClient.get(Constant.EDITPLOUGHPAGE, params,
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
									editPloughPageInfo= JSONUtils.getEditPloughPage(response);
									setDataToView();

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
}
