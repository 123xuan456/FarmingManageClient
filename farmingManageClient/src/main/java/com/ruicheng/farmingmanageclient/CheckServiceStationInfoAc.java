package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.BaseStationInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.BimpHandler;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.BitmapUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 服务站管理--查看服务站信息
 *
 * @author zhaobeibei
 *
 */
public class CheckServiceStationInfoAc extends BaseActivity implements OnClickListener{

	private TextView tv_dcName, tv_stationName, tv_plantingArea, tv_cropTypes, tv_stationAddr,
			tv_postCode, tv_stationCode, tv_soilState, tv_farmAmount, tv_stationBak, tv_stationManager,
			tv_managerPhone, tv_managerSpell, tv_technicianAmount, tv_managerResume;
	private BaseStationInfo baseStationInfo;
	private Bundle bundle;
	private int position;
	private Button btn_last, btn_next;
	private ImageView iv_managerPicture ;

	private ImageView img_comment_back;
	private Dialog loadingDialog ;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_checkservicestationinfo);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			baseStationInfo = (BaseStationInfo) bundle
					.getSerializable("baseStationInfo");
			position = bundle.getInt("position");
		}
		init();
		setListener();

		getViewServerStation();

	}
	public void getViewServerStation() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("stationId",baseStationInfo.getStationId());
			TwitterRestClient.get(Constant.VIEWSERVERSTATION, params,
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
									String managerPicture = JSONUtils.getViewServerStation(response);

									Bitmap selectBitmap = BitmapUtils.getThumBitmapFromFile(managerPicture);

									iv_managerPicture.setImageBitmap(selectBitmap);

									/*ImageLoader mImageLoader = ImageLoader
											.getInstance(3, Type.LIFO);
									mImageLoader.loadImage(
											TwitterRestClient.BASE_URL
													+ managerPicture,
													iv_managerPicture, true);*/

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
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void init() {
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

		iv_managerPicture = (ImageView) findViewById(R.id.iv_managerPicture);

		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		tv_stationName = (TextView) findViewById(R.id.tv_stationName);
		tv_plantingArea = (TextView) findViewById(R.id.tv_plantingArea);
		tv_cropTypes = (TextView) findViewById(R.id.tv_cropTypes);
		tv_stationAddr = (TextView) findViewById(R.id.tv_stationAddr);
		tv_postCode = (TextView) findViewById(R.id.tv_postCode);
		tv_stationCode = (TextView) findViewById(R.id.tv_stationCode);
		tv_soilState = (TextView) findViewById(R.id.tv_soilState);
		tv_farmAmount = (TextView) findViewById(R.id.tv_farmAmount);
		tv_stationBak = (TextView) findViewById(R.id.tv_stationBak);
		tv_stationManager = (TextView) findViewById(R.id.tv_stationManager);
		tv_managerPhone = (TextView) findViewById(R.id.tv_managerPhone);
		tv_managerSpell = (TextView) findViewById(R.id.tv_managerSpell);
		tv_technicianAmount = (TextView) findViewById(R.id.tv_technicianAmount);
		tv_managerResume = (TextView) findViewById(R.id.tv_managerResume);

		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));

		setDataToView();

	}

	@Override
	public void setListener() {

		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_last:
				// 点击获取上一条数据
				if (position == 0) {
					ToastUtils.show(getApplicationContext(), "当前已是第一条数据..");
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
					return;
				}
				btn_next.setBackgroundColor(Color.parseColor("#009933"));
				position--;
				baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
						.get(position);

				setDataToView();

				break;
			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				if (position ==(ServiceNameHandler.allList.size() - 1)) {
					ToastUtils.show(getApplicationContext(), "无更多数据..");
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
					return;
				}
				position++;
				baseStationInfo = (BaseStationInfo) ServiceNameHandler.allList
						.get(position);

				setDataToView();

				if (position == 0) {
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
				} else if (position ==(ServiceNameHandler.GoodsReceiveInfoList
						.size() - 1)) {
					btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
				}
				break;
			default:
				break;
		}
	}
	public void setDataToView() {

		if (BimpHandler.listSelectBitmap!=null&&BimpHandler.listSelectBitmap.size()>0) {

			iv_managerPicture.setImageBitmap(BimpHandler.listSelectBitmap.get(0));
		} else {
			iv_managerPicture.setImageResource(R.drawable.ic_launcher);
		}

		tv_dcName.setText(baseStationInfo.getDcName());
		tv_stationName.setText(baseStationInfo.getStationName());
		tv_plantingArea.setText(baseStationInfo.getPlantingArea());
		tv_cropTypes.setText(baseStationInfo.getCropTypes());
		tv_stationAddr.setText(baseStationInfo.getStationAddr());
		tv_postCode.setText(baseStationInfo.getPostCode());
		tv_stationCode.setText(baseStationInfo.getStationCode());
		tv_soilState.setText(baseStationInfo.getSoilState());
		tv_farmAmount.setText(baseStationInfo.getFarmAmount());
		tv_stationBak.setText(baseStationInfo.getStationBak());
		tv_stationManager.setText(baseStationInfo.getStationManager());
		tv_managerPhone.setText(baseStationInfo.getManagerPhone());
		tv_managerSpell.setText(baseStationInfo.getManagerSpell());
		tv_technicianAmount.setText(baseStationInfo.getTechnicianAmount());
		tv_managerResume.setText(baseStationInfo.getManagerResume());

	}
}
