package com.ruicheng.farmingmanageclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.BaseInfoTiayagManageAdapter;
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

import java.util.ArrayList;
import java.util.List;
/**
 *
 * 基础信息--田洋管理界面
 *
 * @author zhaobeibei
 *
 */
public class TianYangManageAc extends BaseActivity implements OnClickListener{

	private PullToRefreshListView listview_basemanage ;
	private TextView tv_pro_add ;
	private List<Object> listAll;
	private BaseInfoTiayagManageAdapter baseInfoTiayagManageAdapter;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private LinearLayout ImageView_Linearlayout_Back ;
	private ImageView img_comment_back ;
	private Dialog loadingDialog ;
	private int pos;
	private Button btn_query;
	private TextView tv_servicename;
	private final int PROSERVICENAME = 1;
	private String stationId;

	private boolean isRefresh = false;
	private int pageNo = 1;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getQueryPlough(false);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tianyangmanage);

		init();
		getQueryPlough(false);
		initPopupWindow();
		setListener();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		listview_basemanage = (PullToRefreshListView) findViewById(R.id.listview_basemanage);
		tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);

		loadingDialog = DialogUtils.requestDialog(this);

		btn_query = (Button) findViewById(R.id.btn_query);
		tv_servicename = (TextView) findViewById(R.id.tv_servicename);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		tv_pro_add.setOnClickListener(this);
		ModifyProListener modifyProListener = new ModifyProListener();
		listview_basemanage.setOnItemClickListener(modifyProListener);
		ImageView_Linearlayout_Back.setOnClickListener(this);

		btn_query.setOnClickListener(this);
		tv_servicename.setOnClickListener(this);

		listview_basemanage.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			/**
			 * 下拉
			 *
			 * @param refreshView
			 */
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pageNo = 1;
				listAll = new ArrayList<Object>();
				getQueryPlough(false);
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
				getQueryPlough(false);
			}
		});


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_pro_add:
				Intent i = new Intent();
				i.setClass(TianYangManageAc.this, AddTianYangManageAc.class);
				startActivity(i);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.ImageView_Linearlayout_Back:
				img_comment_back.setVisibility(View.GONE);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			case R.id.btn_query:
				getQueryPlough(true);
				break ;
			case R.id.tv_servicename:

				Intent intentt = new Intent();
				intentt.setClass(TianYangManageAc.this,
						ServiceStationNameAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fromwhichview",8);
		/*bundle.putInt("optionType",optionType);*/
				intentt.putExtras(bundle);
				startActivityForResult(intentt, PROSERVICENAME);

				break ;
			default:
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case PROSERVICENAME:
					String servicename = data.getStringExtra("servicename");
					tv_servicename.setText(servicename);
					stationId = data.getStringExtra("stationId");
					break;
				default:
					break;
			}
		}

	}

	/**
	 *
	 *
	 * @author zhaobeibei
	 *
	 */
	public class ModifyProListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
					TianYangManageAc.this, R.anim.activity_translate_in));
			pop.showAtLocation(listview_basemanage, Gravity.BOTTOM, 0, 0);
			pos = position-1;

		}
	}

	private void initPopupWindow() {
		pop = new PopupWindow(TianYangManageAc.this);
		View view = getLayoutInflater().inflate(
				R.layout.item_tianyangpopupmenu, null);
		tianynagmenu_popup = (LinearLayout) view
				.findViewById(R.id.equip_popup_menu);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_equippopup_delete);
		Button bt2 = (Button) view.findViewById(R.id.item_equippopup_modify);
		Button bt3 = (Button) view.findViewById(R.id.item_equippopup_cancel);
		Button bt4 = (Button) view.findViewById(R.id.item_equippopup_query);
		Button bt5 = (Button) view.findViewById(R.id.item_equippopup_agrimRecord);

		LinearLayout linear__equippopup_agrimRecord = (LinearLayout) view.findViewById(R.id.linear__equippopup_agrimRecord);

		linear__equippopup_agrimRecord.setVisibility(View.VISIBLE);

		// 点击父布局消失框pop
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pop消失 清除动画
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//删除
				new AlertDialog.Builder(TianYangManageAc.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										pop.dismiss();
										tianynagmenu_popup.clearAnimation();
										if (pos<0) {
											ToastUtils.show(getApplicationContext(), "已无数据");
											return ;
										}
										getDeletePlough();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {

									}
								}).show();

			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//修改
				Intent i = new Intent();
				i.setClass(getApplicationContext(),UpdateTianYangManageAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("ploughListInfoArrayInfo",((PloughListInfoArrayInfo)ServiceNameHandler.allList.get(pos)));
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();

			}
		});
		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//查看
				Intent i = new Intent();
				i.setClass(getApplicationContext(), CheckServiceTianYnagInfoAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("ploughListInfoArrayInfo", ((PloughListInfoArrayInfo)ServiceNameHandler.allList.get(pos)));
				bundle.putInt("position", pos);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		bt5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//农事记录
				Intent i = new Intent();
				i.setClass(getApplicationContext(), AgriRecordTianYnagInfoAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("ploughListInfoArrayInfo", ((PloughListInfoArrayInfo)ServiceNameHandler.allList.get(pos)));
				bundle.putInt("position", pos);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
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
	/**
	 * 田洋信息删除接口
	 *
	 */
	public void getDeletePlough() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			pop.dismiss();
			tianynagmenu_popup.clearAnimation();
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("ploughId",((PloughListInfoArrayInfo)ServiceNameHandler.allList.get(pos)).getPloughId());
			ServiceNameHandler.allList.remove(pos);
			baseInfoTiayagManageAdapter.notifyDataSetChanged();
			pos -- ;
			TwitterRestClient.get(Constant.DELETEPLOUGH, params,
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
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(), "删除失败");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
									ToastUtils.show(getApplicationContext(), "删除成功");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	public void getQueryPlough(boolean isQuery) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("dcId",PreferencesUtils.getString(getApplicationContext(), Constant.DCID)
			);
			params.put("pager.num",pageNo);
			params.put("pager.size",Constant.SIZE);
			if (isQuery == true) {
				if (!"".equals(stationId)) {

					params.put("station",stationId);
				} else {
					params.put("station","");

				}
			}

			TwitterRestClient.get(Constant.QUERYPLOUGH, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);

							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							ToastUtils.show(getApplicationContext(), "获取数据失败");
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
									if (pageNo==1) {
										ServiceNameHandler.allList.clear();
										ServiceNameHandler.allList  = JSONUtils.getQueryPlough(response);
									}

									listview_basemanage.onRefreshComplete();
									if (pageNo == 1) {
										setListData(ServiceNameHandler.allList);
									} else {
										baseInfoTiayagManageAdapter.notifyDataSetChanged();
									}

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
	public void setListData(List<Object> stationList) {
		baseInfoTiayagManageAdapter = new BaseInfoTiayagManageAdapter(stationList,
				TianYangManageAc.this);
		listview_basemanage.setAdapter(baseInfoTiayagManageAdapter);
	}
}
