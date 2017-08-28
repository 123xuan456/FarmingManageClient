package com.ruicheng.farmingmanageclient.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.PromanageListActivity;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.ServiceStationNameAc;
import com.ruicheng.farmingmanageclient.TianYangNumberAc;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.RecordInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

/**
 * 田阳农事管理---日常农事界面
 *
 * @author zhaobeibei
 *
 */

public class TianYnagDailyFragment extends Fragment implements OnClickListener {

	private Button btn_save;
	View view;
	private EditText  et_temperValue, et_productItem,
			et_cropState, et_actionPerson, et_stationCode,
			et_weatherState, et_agrDesc;
	private TextView et_recordDate,et_registUser,tv_servicename, et_receiveDate;
	private Dialog loadingDialog;
	private TextView et_operatetianyang ;
	private final int  PLOUGHCODE =0;
	private final int SERVICESTATION =1;
	private Bundle bundle;
	private int optionType ;
	private String stationId ;
	private StationData stationData ;
	private PloughListInfo ploughListInfo ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		bundle = getArguments();
		optionType = bundle.getInt("optionType", -1);
		view = inflater.inflate(R.layout.fragment_tianynagdaily, null);

		init();
		setLisener();


		return view;
	}

	public void init() {
		loadingDialog = DialogUtils.requestDialog(getActivity());
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_servicename = (TextView) view.findViewById(R.id.tv_servicename);
		et_temperValue = (EditText) view.findViewById(R.id.et_temperValue);
		et_actionPerson = (EditText) view.findViewById(R.id.et_actionPerson);
		et_productItem = (EditText) view.findViewById(R.id.et_productItem);
		et_registUser = (TextView) view.findViewById(R.id.et_registUser);
		et_stationCode = (EditText) view.findViewById(R.id.et_stationCode);
		et_cropState = (EditText) view.findViewById(R.id.et_cropState);
		et_weatherState = (EditText) view.findViewById(R.id.et_weatherState);
		et_agrDesc = (EditText) view.findViewById(R.id.et_agrDesc);
		et_receiveDate = (TextView) view.findViewById(R.id.et_receiveDate);

		et_recordDate = (TextView) view.findViewById(R.id.et_recordDate);

		et_operatetianyang = (TextView) view.findViewById(R.id.et_operatetianyang);

		et_recordDate.setText(DateUtils.getStringDateShort());

	}

	public void setLisener() {
		btn_save.setOnClickListener(this);

		et_operatetianyang.setOnClickListener(this);
		tv_servicename.setOnClickListener(this);
		et_receiveDate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getSaveRecord();
				break;
			case R.id.et_receiveDate:
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(tv_servicename.getWindowToken(), 0);
				new SelectDateTimePopWin(getActivity(), "",
						LayoutInflater.from(getActivity())
								.inflate(R.layout.activity_favorite, null)
								.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						et_receiveDate.setText(date);
					}
				};
				break;
			case R.id.et_operatetianyang:

				Intent i = new Intent();
				i.setClass(getActivity(), TianYangNumberAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fromwhichview", 2);
				bundle.putInt("optionType",optionType);
				bundle.putString("stationId",stationId);
				i.putExtras(bundle);
				startActivityForResult(i, PLOUGHCODE);
				break ;
			case R.id.tv_servicename:
				Intent svNameIntent = new Intent();
				svNameIntent.setClass(getActivity(),
						ServiceStationNameAc.class);
				Bundle svNameBundle = new Bundle();
				svNameBundle.putInt("fromwhichview",2);
				svNameBundle.putInt("optionType",optionType);
				svNameIntent.putExtras(svNameBundle);
				startActivityForResult(svNameIntent, SERVICESTATION);
				getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			default:
				break;
		}
	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){
		if (tv_servicename.getText().toString()==null||"null".equals(tv_servicename.getText().toString())||"".equals(tv_servicename.getText().toString())) {
			ToastUtils.show(getActivity(), "服务站名称不能为空");
			return false ;
		}
		if (et_temperValue.getText().toString()==null||"null".equals(et_temperValue.getText().toString())||"".equals(et_temperValue.getText().toString())) {
			ToastUtils.show(getActivity(), "气温不能为空");
			return false ;
		}
		if (et_productItem.getText().toString()==null||"null".equals(et_productItem.getText().toString())||"".equals(et_productItem.getText().toString())) {
			ToastUtils.show(getActivity(), "农事项目不能为空");
			return false ;
		}
		if (et_registUser.getText().toString()==null||"null".equals(et_registUser.getText().toString())||"".equals(et_registUser.getText().toString())) {
			ToastUtils.show(getActivity(), "记录人不能为空");
			return false ;
		}
		if (et_stationCode.getText().toString()==null||"null".equals(et_stationCode.getText().toString())||"".equals(et_stationCode.getText().toString())) {
			ToastUtils.show(getActivity(), "服务站编号不能为空");
			return false ;
		}
		if (et_weatherState.getText().toString()==null||"null".equals(et_weatherState.getText().toString())||"".equals(et_weatherState.getText().toString())) {
			ToastUtils.show(getActivity(), "天气状况不能为空");
			return false ;
		}
		if (et_receiveDate.getText().toString()==null||"null".equals(et_receiveDate.getText().toString())||"".equals(et_receiveDate.getText().toString())) {
			ToastUtils.show(getActivity(), "实施日期不能为空");
			return false ;
		}
		if (et_receiveDate.getText().toString()==null||"null".equals(et_receiveDate.getText().toString())||"".equals(et_receiveDate.getText().toString())) {
			ToastUtils.show(getActivity(), "登记日期不能为空");
			return false ;
		}
		return true ;
	}
	/**
	 * 保存日常农事登记数据
	 *
	 */
	public void getSaveRecord() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (NetUtils.checkNetConnection(getActivity())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();

			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getActivity(), Constant.USERID,Constant.FAILUREINT)+"");
			params.put("userName", PreferencesUtils.getString(
					getActivity(), Constant.USERNAME,
					Constant.FAILURE));
			params.put("optionType",optionType);

			params.put("record.productType",2);
			params.put("record.stationId",stationData.getStationId());
			params.put("record.stationCode", stationData.getStationCode());
			params.put("record.registUser",PreferencesUtils.getInt(getActivity(),
					Constant.USERID, Constant.FAILUREINT) + "");
			params.put("record.registDate", et_recordDate.getText().toString());

			params.put("detail.temperValue", et_temperValue.getText().toString());
			params.put("detail.weatherState", et_weatherState.getText().toString());
			params.put("detail.productItem", et_productItem.getText().toString());
			params.put("detail.agrDesc", et_agrDesc.getText().toString());
			params.put("detail.cropState", et_cropState.getText().toString());


			params.put("detail.recordDate", et_recordDate.getText().toString());

			params.put("detail.receiveDate", et_receiveDate.getText().toString());

			params.put("detail.actionPerson", et_cropState.getText().toString());
			params.put("detail.actionBak", et_actionPerson.getText().toString());
			params.put("detail.productType", 2);

			params.put("ploughId",
					ploughListInfo.getPloughId());

			TwitterRestClient.get(Constant.SAVERECORD, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								ToastUtils.show(getActivity(), "保存失败");
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
									ToastUtils.show(getActivity(), "保存成功");
									getActivity().finish();
									getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case SERVICESTATION:
					String servicename = data.getStringExtra("servicename");
					tv_servicename.setText(servicename);
					stationId = data.getStringExtra("stationId");
					et_stationCode.setText(stationId);

					stationData = (StationData) data
							.getSerializableExtra("stationInfo");
					break;
				case PLOUGHCODE:
					String PloughCode = data.getStringExtra("PloughCode");
					et_operatetianyang.setText(PloughCode);
					ploughListInfo = (PloughListInfo) data.getSerializableExtra("PLOUGHLIST");
					break ;
				default:
					break;
			}
		}
	}
}
