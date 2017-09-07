package com.ruicheng.farmingmanageclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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
import com.ruicheng.farmingmanageclient.adapter.TianYangProListViewAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.DataListInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.fragment.PromanageListFragment;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产管理的二级列表
 *
 * @author zhaobeibei
 *
 */

public class PromanageListActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout promanage_Layout_Navigation_title1,
			promanage_Layout_Navigation_title2;
	private TextView tv_bozhongyizaidengji, tv_bozhongyizaiguanli,
			tv_navigation_one, tv_navigation_two;

	private Bundle bundle;
	private int kind;   // 0 播种移栽 1  田洋农事 2 田洋收成
	private int state;
	private ViewPager vp_viewpager;
	private List<PromanageListFragment> fragList = new ArrayList<PromanageListFragment>();
	private PullToRefreshListView listview_project;
	private List<Object> listAll;
	private List<CropTypeNameInfo> listcTName;
	private TianYangProListViewAdapter tianYangProListViewAdapter;
	private TextView tv_pro_add;
	protected static final int ADDPROJECT = 0;

	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private ImageView img_comment_back;
	private LinearLayout ImageView_Linearlayout_Back;
	private TianYagProListener tianYagProListener;
	private TextView textView_Title_Left;
	private TextView tv_handlestarttime;
	private TextView tv_handleendtime;
	private ModifyProListener modifyProListener;
	private TextView tv_servicename;
	private int optionType; // 田洋播种移栽:1,田洋农事项目:2,田洋收成: 5
	private Dialog loadingDialog;
	private final int PROSERVICENAME = 1;
	private final int PROTIANYANGNUMBER = 2;
	private final int SEEDMODIFY = 3; //播种移栽修改
	private LinearLayout linear_tianYangNumber;
	private TextView tv_tianyangnumber;
	private Button btn_query;
	private String stationId;
	private int num = 1;
	private int pos;
	private PloughListInfo ploughListInfo;
	private String ploughId ;
	private boolean isRefresh = false;
	private int pageNo = 1;
	private int optionTyppe ;
	private String servicename;//服务站名称

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_productionmanagement);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			kind = bundle.getInt("kind");
			state = bundle.getInt("state", -1);
		}
		optionType = PreferencesUtils.getInt(getApplicationContext(), Constant.OPTIONTYPE);
		init();
		initPopupWindow();
		setListener();
		getQueryRecord(0);
		getQueryProduct();
	}

	private void setTitle() {
		// private TextView tv_bozhongyizaidengji,tv_bozhongyizaiguanli
		// ,tv_navigation_one,tv_navigation_two;
		if (kind == 0) {
			tv_bozhongyizaidengji.setText(R.string.tv_bozhongyizaidengji);
			tv_bozhongyizaiguanli.setText(R.string.tv_bozhongyizaiguanli);
		} else if (kind == 1) {
			tv_bozhongyizaidengji.setText(R.string.tv_tianyangnongshidengji);
			tv_bozhongyizaiguanli.setText(R.string.tv_tianyangnongshiguanli);
		} else if (kind == 2) {
			tv_bozhongyizaidengji.setText(R.string.tv_tianyangnongshidengji);
			tv_bozhongyizaiguanli.setText(R.string.tv_tianyangnongshiguanli);
		}

	}

	private void setViewPagerDataSource() {
		for (int i = 0; i < 2; i++) {
			PromanageListFragment promanageListFragment = new PromanageListFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("index", i);
			bundle.putInt("kind", kind);
			promanageListFragment.setArguments(bundle);
			fragList.add(promanageListFragment);
		}

	}

	public void setHuaDondColor(int textColor, int backColor) {
		tv_bozhongyizaidengji.setTextColor(textColor);
		tv_navigation_one.setBackgroundColor(textColor);
		tv_bozhongyizaiguanli.setTextColor(backColor);
		tv_navigation_two.setBackgroundColor(backColor);
	}

	private class TianYagProListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_pro_add:
					Intent intent = new Intent(PromanageListActivity.this,
							TianYagProAddActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("MODIFYITEM", 0); // 添加农事项目
					intent.putExtra("KIND", kind);
					intent.putExtra("optionType", optionType);
					intent.putExtra("servicename",servicename);
					intent.putExtra("stationId",stationId);//服务站编号

					startActivityForResult(intent, ADDPROJECT);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.tv_handlestarttime:
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(tv_servicename.getWindowToken(), 0);
					new SelectDateTimePopWin(PromanageListActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
//						if (DateUtils.strToDateLongModify(date).getTime()-(new Date(System.currentTimeMillis())).getTime()>0) {
//							tv_handlestarttime.setText(date);
//						}
							tv_handlestarttime.setText(date);
//						else {
//							ToastUtils.show(getApplicationContext(), "开始时间不能小于当前时间");
//						}
						}
					};
					break;
				case R.id.tv_handleendtime:
					InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					immm.hideSoftInputFromWindow(tv_servicename.getWindowToken(), 0);
					new SelectDateTimePopWin(PromanageListActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (tv_handlestarttime.getText().toString()==null||tv_handlestarttime.getText().toString().equals("")) {
								ToastUtils.show(getApplicationContext(), "请先输入开始时间");
								return ;
							}
							if (DateUtils.strToDateLongModify(date).getTime()-DateUtils.strToDateLongModify(tv_handlestarttime.getText().toString()).getTime()>0) {
								tv_handleendtime.setText(date);
							} else {
								ToastUtils.show(getApplicationContext(), "结束时间不能小于开始时间");
							}
						}

					};
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case PROSERVICENAME:
					servicename = data.getStringExtra("servicename");
					tv_servicename.setText(servicename);
					stationId = data.getStringExtra("stationId");
					break;
				case PROTIANYANGNUMBER:
					String pro_tianyangnumber = data.getStringExtra("PloughCode");
					tv_tianyangnumber.setText(pro_tianyangnumber);
					ploughListInfo = (PloughListInfo) data.getSerializableExtra("PLOUGHLIST");
					break;
				case SEEDMODIFY:
					//修改播种移栽后
					break;
				default:
					break;
			}
		}

	}

	private class Promanage_Layout_NavigationListener implements
			OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.promanage_Layout_Navigation_title1:
				/*
				 * setHuaDondColor(Color.BLUE, Color.BLACK);
				 * vp_viewpager.setCurrentItem(0);
				 */
					break;
				case R.id.promanage_Layout_Navigation_title2:
				/*
				 * setHuaDondColor(Color.BLACK, Color.BLUE);
				 * vp_viewpager.setCurrentItem(1);
				 */
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void init() {
		textView_Title_Left = (TextView) findViewById(R.id.textView_Title_Left);
		tv_handlestarttime = (TextView) findViewById(R.id.tv_handlestarttime);
		tv_handleendtime = (TextView) findViewById(R.id.tv_handleendtime);

		loadingDialog = DialogUtils.requestDialog(this);

		linear_tianYangNumber = (LinearLayout) findViewById(R.id.linear_tianYangNumber);
		tv_servicename = (TextView) findViewById(R.id.tv_servicename);
		tv_tianyangnumber = (TextView) findViewById(R.id.tv_tianyangnumber);
		btn_query = (Button) findViewById(R.id.btn_query);

		if (kind == 0) {
			textView_Title_Left.setText("查询田洋播种");
			listview_project = (PullToRefreshListView) findViewById(R.id.listview_project);
			tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
			img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
			findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							/* openOrCloseKeybd(false); */
							img_comment_back.setVisibility(View.GONE);
							Intent i = new Intent();
							i.setClass(getApplicationContext(),
									MainActivity.class);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.zoomout,
									R.anim.zoomin);
						}
					});

			tv_servicename.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setClass(PromanageListActivity.this,
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview", 0);
					bundle.putInt("optionType",optionType);
					intent.putExtras(bundle);
					startActivityForResult(intent, PROSERVICENAME);
				}
			});

		} else if (kind == 1) {
			textView_Title_Left.setText("田洋农事项目");
			listview_project = (PullToRefreshListView) findViewById(R.id.listview_project);
			tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
			img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
			findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							/* openOrCloseKeybd(false); */
							img_comment_back.setVisibility(View.GONE);
							Intent i = new Intent();
							i.setClass(getApplicationContext(),
									MainActivity.class);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.zoomout,
									R.anim.zoomin);
						}
					});
			tv_servicename.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setClass(PromanageListActivity.this,
							ServiceStationNameAc.class);
					intent.putExtra("optionType", optionType);
					startActivityForResult(intent, PROSERVICENAME);
				}
			});
		} else if (kind == 2) {
			textView_Title_Left.setText("田洋收成");
			listview_project = (PullToRefreshListView) findViewById(R.id.listview_project);
			tv_pro_add = (TextView) findViewById(R.id.tv_pro_add);
			img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
			findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							/* openOrCloseKeybd(false); */
							img_comment_back.setVisibility(View.GONE);
							Intent i = new Intent();
							i.setClass(getApplicationContext(),
									MainActivity.class);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.zoomout,
									R.anim.zoomin);
						}
					});
			tv_servicename.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setClass(PromanageListActivity.this,
							ServiceStationNameAc.class);
					intent.putExtra("optionType", optionType);
					startActivityForResult(intent, PROSERVICENAME);
				}
			});
		}
	}

	@Override
	public void setListener() {
		tianYagProListener = new TianYagProListener();
		modifyProListener = new ModifyProListener();
		tv_handlestarttime.setOnClickListener(tianYagProListener);
		tv_handleendtime.setOnClickListener(tianYagProListener);
		btn_query.setOnClickListener(this);
		linear_tianYangNumber.setOnClickListener(this);
		if (kind == 1) {
			tv_pro_add.setOnClickListener(tianYagProListener);
			listview_project.setOnItemClickListener(modifyProListener);
		} else if (kind == 0) {
			tv_pro_add.setOnClickListener(tianYagProListener);
			listview_project.setOnItemClickListener(modifyProListener);
		} else if (kind == 2) {
			tv_pro_add.setOnClickListener(tianYagProListener);
			listview_project.setOnItemClickListener(modifyProListener);

		}

		listview_project.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			/**
			 * 下拉
			 *
			 * @param refreshView
			 */
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pageNo = 1;
				getQueryRecord(0);
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
				getQueryRecord(0);
			}
		});



	}

	public void setListData(List<Object> listAll,int optionType) {
		tianYangProListViewAdapter = new TianYangProListViewAdapter(listAll,
				getApplicationContext(),optionType);
		listview_project.setAdapter(tianYangProListViewAdapter);
	}

	/**
	 * 点击田洋农事项目Item的点击事件
	 *
	 * @author zhaobeibei
	 *
	 */
	public class ModifyProListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
					PromanageListActivity.this, R.anim.activity_translate_in));
			pop.showAtLocation(listview_project, Gravity.BOTTOM, 0, 0);

			pos = position;

		}
	}

	private void initPopupWindow() {
		pop = new PopupWindow(PromanageListActivity.this);
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
		LinearLayout linear_equippopup_query =(LinearLayout) view.findViewById(R.id.linear_equippopup_query);
		linear_equippopup_query.setVisibility(View.GONE);
		// 点击父布局消失框pop
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pop消失 清除动画
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				Intent i = new Intent();
				i.setClass(getApplicationContext(), UpdateRecordAc.class);
				Bundle bundle = new Bundle();
				bundle.putString("androidAccessType",PreferencesUtils.getString(getApplicationContext(), Constant.ANDROIDACCESSTYPE));
				bundle.putInt("position", pos);
				bundle.putInt("kind",kind);
				bundle.putSerializable("dataListInfo",((DataListInfo)(listAll.get(pos-1))));
				bundle.putInt("optionType", optionType);
				i.putExtras(bundle);
				startActivityForResult(i, SEEDMODIFY);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);

			}
		});
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				new AlertDialog.Builder(PromanageListActivity.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										getDeleteRecord();
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
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
			}
		});
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
	 * 判断提交信息是否为空
	 *
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){

		if (tv_servicename.getText().toString()==null||"null".equals(tv_servicename.getText().toString())||"".equals(tv_servicename.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "服务站名称不能为空");
			return false ;
		}
		if (tv_tianyangnumber.getText().toString()==null||"null".equals(tv_tianyangnumber.getText().toString())||"".equals(tv_tianyangnumber.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "田洋编号不能为空");
			return false ;
		}
		if (tv_handlestarttime.getText().toString()==null||"null".equals(tv_handlestarttime.getText().toString())||"".equals(tv_handlestarttime.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "经办开始时间不能为空");
			return false ;
		}
		if (tv_handleendtime.getText().toString()==null||"null".equals(tv_handleendtime.getText().toString())||"".equals(tv_handleendtime.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "经办结束时间不能为空");
			return false ;
		}

		return true ;
	}
	/**
	 * 获取播种移栽查询数据
	 *
	 */
	public void getQueryRecord(int query) {

		/*if (query == 1) {
			if (!estimateInfoIsNullUtils()) {
				return ;
			}
		}*/
		// query 0 查询全部 1 查询部分
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");//1
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));//admin
			params.put("dcId", PreferencesUtils.getString(
					getApplicationContext(), Constant.DCID, Constant.FAILURE));//
			//optionType (田洋播种移栽:1,田洋农事项目:2,田洋收成: 5)
			params.put("optionType", optionType + "");//
			params.put("pager.num", pageNo+"");
			params.put("pager.size", Constant.SIZE+"");
			if (query == 1) {
				if (!"".equals(tv_handlestarttime.getText().toString())&&!"null".equals(tv_handlestarttime.getText().toString())) {
					params.put("fromDate", tv_handlestarttime.getText().toString()+" "+DateUtils.getTimeShort());
				} else {
					params.put("fromDate","");
				}
				if (!"".equals(tv_handleendtime.getText().toString())&&!"null".equals(tv_handleendtime.getText().toString())) {
					params.put("toDate", tv_handleendtime.getText().toString()+" "+DateUtils.getTimeShort());

				} else {
					params.put("toDate","");
				}
				if (ploughListInfo!=null&&!"".equals(ploughListInfo.getPloughId())&&!"null".equals(ploughListInfo.getPloughId())) {
					params.put("detail.ploughId",ploughListInfo.getPloughId());
				} else {
					params.put("detail.ploughId","");
				}
				if (!"".equals(stationId)&&!"null".equals(stationId)) {

					params.put("stationId",stationId);
				} else {
					params.put("stationId","");

				}
			}
			TwitterRestClient.get(Constant.QUERYRECORD, params,
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
											.getQueryRecordDataList(response,optionType);
									listview_project.onRefreshComplete();
									if (pageNo == 1) {
										setListData(listAll,optionType);
									} else {
										tianYangProListViewAdapter.notifyDataSetChanged();
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

	/**
	 * 播种移栽管理删除接口
	 *
	 */
	public void getDeleteRecord() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {

			tianYangProListViewAdapter.notifyDataSetChanged();
			pop.dismiss();
			tianynagmenu_popup.clearAnimation();
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put(
					"detailId",
					((DataListInfo)listAll.get(pos-1)).getDetailId());
			listAll.remove(pos-1);
			TwitterRestClient.get(Constant.DELETERECORD, params,
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
//								if ("failure".equals(JSONUtils
//										.getResultMsg(errorResponse))) {
//								}
								ToastUtils.show(getApplicationContext(), "删除失败");
							} catch (Exception e) {
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



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.linear_tianYangNumber:
				Intent i = new Intent();
				if ("".equals(tv_servicename.getText().toString())||"null".equals(tv_servicename.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "请先选择服务站");
					return ;
				}
				i.setClass(PromanageListActivity.this, TianYangNumberAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("optionType", optionType);
				bundle.putString("stationId", stationId);
				bundle.putInt("fromwhichview", 0);
				i.putExtras(bundle);
				startActivityForResult(i, PROTIANYANGNUMBER);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.btn_query:
				// 1 查询部分
				getQueryRecord(1);
				break;
			default:
				break;
		}
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
			//获取农作物名称的接口
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
								listcTName = JSONUtils.getCropTypeName(response);
								for (int i = 0; i <listcTName.size(); i++) {
									ServiceNameHandler.cropTypeNameList.add(listcTName.get(i));
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getQueryRecord(0);
	}
	public void getOptionPlough(){
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put(Constant.USERNAME,PreferencesUtils.getString(getApplicationContext(), Constant.USERNAME));
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
					ToastUtils.show(getApplicationContext(),"获取数据成功");
					try {
						listAll = JSONUtils.getStationInfo(response);

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
	public void getOptionPlough(String stationId){
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
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
					ToastUtils.show(getApplicationContext(),"获取数据成功");
					try {

						listAll  = JSONUtils.getploughListInfo(response);
						/*setListAll(listAll);

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
						}*/
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
