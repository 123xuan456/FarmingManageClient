package com.ruicheng.farmingmanageclient.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.ServiceStationNameAc;
import com.ruicheng.farmingmanageclient.TianYangNumberAc;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.EditTextUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 田阳农事--肥料使用界面
 *
 * @author zhaobeibei
 *
 */
public class TianYangFertilizerFragment extends Fragment implements OnClickListener {
	private Dialog loadingDialog;
	private Button btn_save;
	View view;
	private TextView tv_serviceName,tv_operatetianyang, tv_receiveDate, tv_registUser;
	private EditText tv_manureName, tv_manureAmountUnit, tv_manureUse,
			tv_actionPerson, tv_stationCode,
			tv_recordDate, et_actionBak;
	private final int  PLOUGHCODE =0;
	private final int SERVICESTATION =1;
	private int indecator;
	private Bundle bundle;
	private int optionType ;
	private String stationId ;
	private StationData stationData ;
	private PloughListInfo ploughListInfo ;
	private String servicename;
	private String stationCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		bundle = getArguments();
		optionType = bundle.getInt("optionType", -1);
		servicename = bundle.getString("servicename");
		stationId = bundle.getString("stationId");
		stationCode = bundle.getString("stationCode");
		view = inflater.inflate(R.layout.fragment_tianyangfertilizer, null);

		init();
		setLisener();

		return view;
	}

	private void setLisener() {
		btn_save.setOnClickListener(this);
		tv_receiveDate.setOnClickListener(this);
		tv_operatetianyang.setOnClickListener(this);
		tv_serviceName.setOnClickListener(this);

	}

	private void init() {

		tv_manureName = (EditText) view.findViewById(R.id.tv_manureName);
		tv_manureAmountUnit = (EditText) view.findViewById(R.id.tv_manureAmountUnit);
		tv_manureUse = (EditText) view.findViewById(R.id.tv_manureUse);
		tv_actionPerson = (EditText) view.findViewById(R.id.tv_actionPerson);
		tv_registUser = (TextView) view.findViewById(R.id.tv_registUser);
		tv_stationCode = (EditText) view.findViewById(R.id.tv_stationCode);
		tv_receiveDate = (TextView) view.findViewById(R.id.tv_receiveDate);
		tv_operatetianyang = (TextView) view.findViewById(R.id.tv_operatetianyang);
		tv_recordDate = (EditText) view.findViewById(R.id.tv_recordDate);
		et_actionBak = (EditText) view.findViewById(R.id.et_actionBak);


		tv_serviceName = (TextView) view.findViewById(R.id.tv_serviceName);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		loadingDialog = DialogUtils.requestDialog(getActivity());

		tv_recordDate.setText(DateUtils.getStringDateShort());
		tv_serviceName.setText(servicename);
		tv_stationCode.setText(stationCode);

		EditTextUtils.setEditTextInhibitInputSpeChat(tv_manureName);
		EditTextUtils.setEditTextInhibitInputSpeChat(tv_manureAmountUnit);
		EditTextUtils.setEditTextInhibitInputSpeChat(tv_manureUse);
		EditTextUtils.setEditTextInhibitInputSpeChat(tv_actionPerson);
		EditTextUtils.setEditTextInhibitInputSpeChat(tv_stationCode);
		EditTextUtils.setEditTextInhibitInputSpeChat(tv_recordDate);
		EditTextUtils.setEditTextInhibitInputSpeChat(et_actionBak);

	}

	/**
	 * 保存肥料使用登记数据
	 *
	 */
	public void getSaveRecord() {
		if (NetUtils.checkNetConnection(getActivity())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();

			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getActivity(), Constant.USERID,
							Constant.FAILUREINT) + "");
			params.put("userName", PreferencesUtils.getString(getActivity(),
					Constant.USERNAME, Constant.FAILURE));
			params.put("optionType",optionType);

			params.put("record.productType",3);
			if (stationData==null){
				params.put("record.stationId",stationId);
				params.put("record.stationCode", servicename);
			}else {
				params.put("record.stationId",stationData.getStationId());
				params.put("record.stationCode", stationData.getStationCode());
			}
			params.put("record.registUser",PreferencesUtils.getInt(getActivity(),
					Constant.USERID, Constant.FAILUREINT) + "");
			params.put("record.registDate", tv_recordDate.getText().toString()+" "+DateUtils.getTimeShort());

			params.put("detail.manureName", tv_manureName.getText().toString());
			params.put("detail.manureAmountUnit", tv_manureAmountUnit.getText()
					.toString());
			params.put("detail.manureUse", tv_manureUse.getText().toString());
			params.put("detail.recordDate", tv_recordDate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("detail.receiveDate", tv_receiveDate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("detail.actionPerson", tv_actionPerson.getText().toString());
			params.put("detail.actionBak", et_actionBak.getText().toString());
			params.put("detail.productType",3);

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
									getActivity().overridePendingTransition(
											R.anim.zoomout, R.anim.zoomin);
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getSaveRecord();
				break;
			case R.id.tv_receiveDate:
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(tv_serviceName.getWindowToken(), 0);
				new SelectDateTimePopWin(getActivity(), "",
						LayoutInflater.from(getActivity())
								.inflate(R.layout.activity_favorite, null)
								.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_receiveDate.setText(date);
					}
				};
				break;
			case R.id.tv_operatetianyang:
				Intent i = new Intent();
				i.setClass(getActivity(), TianYangNumberAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fromwhichview",3);
				bundle.putInt("optionType",optionType);
				bundle.putString("stationId",stationId);
				i.putExtras(bundle);
				startActivityForResult(i, PLOUGHCODE);
				break ;
			case R.id.tv_serviceName:
				Intent svNameIntent = new Intent();
				svNameIntent.setClass(getActivity(),
						ServiceStationNameAc.class);
				Bundle svNameBundle = new Bundle();
				svNameBundle.putInt("fromwhichview",3);
				svNameBundle.putInt("optionType",optionType);
				svNameIntent.putExtras(svNameBundle);
				startActivityForResult(svNameIntent, SERVICESTATION);
				getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			default:
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case SERVICESTATION:
					String servicename = data.getStringExtra("servicename");
					tv_serviceName.setText(servicename);
					stationId = data.getStringExtra("stationId");
					stationCode = data.getStringExtra("stationCode");
					tv_stationCode.setText(stationCode);

					stationData = (StationData) data
							.getSerializableExtra("stationInfo");
					break;
				case PLOUGHCODE:
					String PloughCode = data.getStringExtra("PloughCode");
					tv_operatetianyang.setText(PloughCode);
					ploughListInfo = (PloughListInfo) data.getSerializableExtra("PLOUGHLIST");
					break ;
				default:
					break;
			}
		}
	}
}
