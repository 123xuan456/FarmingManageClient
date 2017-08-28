package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.ProReStatisticsAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.StatisticsCropInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 生产作物履历统计
 *
 * @author zhaobeibei
 *
 */
public class ProductionRecordStatisticsActivity extends BaseActivity implements
		OnClickListener {

	private PullToRefreshListView listview_productionrecordstatistics;
	private List<Object> listAll;
	private ProReStatisticsAdapter proReStatisticsAdapter;
	private ImageView img_comment_back;
	private Dialog loadingDialog;
	private TextView tv_startTime, tv_endTime;
	private Button btn_statistics;
	private TextView tv_total_cultivatedArea, tv_total_harvestYield;
	private boolean isRefresh = false;
	private int pageNo = 1;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_productionrecordstatistics);

		init();
		setListener();
		getStatisticsCropInfo(false);

	}

	@Override
	public void init() {
		loadingDialog = DialogUtils.requestDialog(this);
		listview_productionrecordstatistics = (PullToRefreshListView) findViewById(R.id.listview_productionrecordstatistics);

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/* openOrCloseKeybd(false); */
						img_comment_back.setVisibility(View.GONE);
						finish();
						Intent i = new Intent();
						i.setClass(ProductionRecordStatisticsActivity.this,
								MainActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});
		tv_startTime = (TextView) findViewById(R.id.tv_startTime);
		tv_endTime = (TextView) findViewById(R.id.tv_endTime);

		btn_statistics = (Button) findViewById(R.id.btn_statistics);

		tv_total_cultivatedArea = (TextView) findViewById(R.id.tv_total_cultivatedArea);
		tv_total_harvestYield = (TextView) findViewById(R.id.tv_total_harvestYield);

	}

	@Override
	public void setListener() {
		tv_startTime.setOnClickListener(this);
		tv_endTime.setOnClickListener(this);
		btn_statistics.setOnClickListener(this);

		listview_productionrecordstatistics.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			/**
			 * 下拉
			 *
			 * @param refreshView
			 */
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pageNo = 1;
				getStatisticsCropInfo(false);
			}

			/**
			 * 上拉
			 *
			 * @param refreshView
			 */

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				isRefresh = true;
				pageNo = pageNo + 1;
				getStatisticsCropInfo(false);
			}
		});

	}

	public void setListDat(List<Object> listAll) {
		proReStatisticsAdapter = new ProReStatisticsAdapter(
				getApplicationContext(), listAll);
		listview_productionrecordstatistics.setAdapter(proReStatisticsAdapter);

		Double ploughArea = 0.00;
		Double receiveweight = 0.00;

		for (int i = 0; i < listAll.size(); i++) {

			StatisticsCropInfo statisticsCropInfo = (StatisticsCropInfo) listAll
					.get(i);
			if (statisticsCropInfo!=null&&!"".equals(statisticsCropInfo.getPlougharea())&&!"null".equals(statisticsCropInfo.getPlougharea())) {

			}
			ploughArea = ploughArea
					+ Double.parseDouble(statisticsCropInfo.getPlougharea());
			if (statisticsCropInfo!=null&&!"".equals(statisticsCropInfo.getReceiveweight())&&!"null".equals(statisticsCropInfo.getReceiveweight())) {
				receiveweight = receiveweight
						+ Double.parseDouble(statisticsCropInfo.getReceiveweight());
			}
		}
		tv_total_cultivatedArea.setText(String.valueOf(ploughArea));
		tv_total_harvestYield.setText(String.valueOf(receiveweight));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			startActivity(new Intent(this, MainActivity.class));
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取生产履历统计的列表
	 *
	 * @param isQuery
	 */
	public void getStatisticsCropInfo(boolean isQuery) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
			params.put("pager.num", pageNo);
			params.put("pager.size", Constant.SIZE);
			if (isQuery == true) {
				if (!"".equals(tv_startTime.getText().toString())
						&& !"null".equals(tv_startTime.getText().toString())) {
					params.put("fromDate", tv_startTime.getText().toString());

				} else {
					params.put("fromDate", "");
				}
				if (!"".equals(tv_endTime.getText().toString())
						&& !"null".equals(tv_endTime.getText().toString())) {
					params.put("toDate", tv_endTime.getText().toString());
				} else {
					params.put("toDate", "");
				}
			}
			TwitterRestClient.get(Constant.SATAISTICSCROPINFO, params,
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
									listAll = JSONUtils
											.getStatisticsCropInfo(response);
									listview_productionrecordstatistics.onRefreshComplete();
									if (pageNo == 1) {
										setListDat(listAll);
									} else {
										proReStatisticsAdapter.notifyDataSetChanged();
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_startTime:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(img_comment_back.getWindowToken(), 0);
				new SelectDateTimePopWin(ProductionRecordStatisticsActivity.this,
						"", LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						tv_startTime.setText(date);
					/*if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
					} else {
						ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
					}*/
					}
				};
				break;
			case R.id.tv_endTime:
				InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(img_comment_back.getWindowToken(), 0);
				new SelectDateTimePopWin(ProductionRecordStatisticsActivity.this,
						"", LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.activity_favorite, null)
						.findViewById(R.id.container),
						SelectDateTimePopWin.PATTERN_YMDH) {
					@Override
					public void returnDate(String date) {
						if (tv_startTime.getText().toString()==null||tv_startTime.getText().toString().equals("")) {
							ToastUtils.show(getApplicationContext(), "请先输入开始时间");
							return ;
						}
						if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(tv_startTime.getText().toString()).getTime()>0) {
							tv_endTime.setText(date);
						} else {
							ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
						}
					}
				};
				break;
			case R.id.btn_statistics:
				getStatisticsCropInfo(true);
				break;
			default:
				break;
		}

	}
}
