package com.ruicheng.farmingmanageclient;

import android.app.Activity;
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
import com.ruicheng.farmingmanageclient.adapter.CostEqucationAdapter;
import com.ruicheng.farmingmanageclient.adapter.TypeCostEqucationAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.IsValidInfo;
import com.ruicheng.farmingmanageclient.bean.PlanFromulaListInfo;
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
 * 成本公式界面
 *
 * @author zhaobeibei
 *
 */
public class CostequationActivity extends BaseActivity implements
		OnClickListener {

	private PullToRefreshListView listview_proplan;
	private CostEqucationAdapter costEqucationAdapter;
	private TypeCostEqucationAdapter typeCostEqucationAdapter;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private TextView tv_add;
	private static final int ADDCOSTEQUATION = 0;
	private static final int CROPTYPENAME = 1;
	private static final int ISVALID = 2;
	private static final int MODIFY = 3;
	private int type;
	private Intent intent;
	private LinearLayout linear_cropname, linear_argiculturename;
	private LinearLayout imageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view1, view2;
	private Dialog loadingDialog;
	private Button btn_query;
	private TextView tv_cropType,tv_agriName,tv_isValid,tv_farmproductsname;
	private String dicCode;
	private int pos ;
	private TextView tv_title ;
	private String dicId ;

	private boolean isRefresh = false;
	private int pageNo = 1;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getQueryFromulaSettingPage(false);
	}
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_costequation);
		intent = getIntent();
		if (intent != null) {
		}
		type = PreferencesUtils.getInt(getApplicationContext(),Constant.TYPE);
		init();
		initPopupWindow();
		setListener();
		getQueryFromulaSettingPage(false);
	}

	private void setListData(List<Object> listAll) {
		typeCostEqucationAdapter = new TypeCostEqucationAdapter(
				getApplicationContext(), listAll);
		listview_proplan.setAdapter(typeCostEqucationAdapter);
	}

	@Override
	public void init() {
		view2 = findViewById(R.id.view2);
		view1 = findViewById(R.id.view1);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		imageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.imageView_Linearlayout_Back);
		listview_proplan = (PullToRefreshListView) findViewById(R.id.listview_proplan);
		tv_add = (TextView) findViewById(R.id.tv_add);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_argiculturename = (LinearLayout) findViewById(R.id.linear_argiculturename);
		btn_query = (Button) findViewById(R.id.btn_query);
		tv_cropType = (TextView) findViewById(R.id.tv_cropType);
		tv_agriName = (TextView) findViewById(R.id.tv_agriName);
		tv_isValid = (TextView) findViewById(R.id.tv_isValid);
		tv_farmproductsname = (TextView) findViewById(R.id.tv_farmproductsname);
		tv_title = (TextView) findViewById(R.id.tv_title);
		if (type == 1) {
			linear_argiculturename.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			tv_farmproductsname.setText("农产品名称");
			tv_title.setText("加工计划公式管理");
		} else {
			linear_argiculturename.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			tv_farmproductsname.setText("作物名称");
			tv_title.setText("种植计划公式管理");
		}
		loadingDialog = DialogUtils.requestDialog(this);
	}

	@Override
	public void setListener() {
		ModifyProListener modifyProListener = new ModifyProListener();
		listview_proplan.setOnItemClickListener(modifyProListener);
		tv_add.setOnClickListener(this);
		imageView_Linearlayout_Back.setOnClickListener(this);

		btn_query.setOnClickListener(this);
		tv_cropType.setOnClickListener(this);
		tv_agriName.setOnClickListener(this);
		tv_isValid.setOnClickListener(this);


		listview_proplan.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			/**
			 * 下拉
			 *
			 * @param refreshView
			 */
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pageNo = 1;
				getQueryFromulaSettingPage(false);
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
				getQueryFromulaSettingPage(false);
			}
		});

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
					CostequationActivity.this, R.anim.activity_translate_in));
			pop.showAtLocation(listview_proplan, Gravity.BOTTOM, 0, 0);
			pos = position-1;

		}
	}

	private void initPopupWindow() {
		pop = new PopupWindow(CostequationActivity.this);
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
		bt5.setText("是否有效");
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
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//删除
				new AlertDialog.Builder(CostequationActivity.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										getDeleteFromulaSetting(pos);
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
				i.setClass(getApplicationContext(),UpdateCostequationAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("planFromulaListInfo", (PlanFromulaListInfo)ServiceNameHandler.allList.get(pos));

				/*if (ServiceNameHandler.customCostList!=null&&ServiceNameHandler.customCostList.size()>0&&ServiceNameHandler.chargeList!=null&&ServiceNameHandler.chargeList.size()>0) {
					bundle.putSerializable("customCostListInfo", (CustomCostListInfo)ServiceNameHandler.customCostList.get(pos));
					bundle.putSerializable("chargeListInfo", (ChargeListInfo)ServiceNameHandler.chargeList.get(pos));

				}*/
				i.putExtras(bundle);
				startActivityForResult(i, MODIFY);
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//取消
			}
		});
		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//查看
				Intent i = new Intent();
				i.setClass(getApplicationContext(), CheckCostequationAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("planFromulaListInfo", (PlanFromulaListInfo)ServiceNameHandler.allList.get(pos));
				bundle.putInt("pos", pos);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		bt5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				//设置成本公式的有效

				getAdjustStateFromulaValid();
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		switch (v.getId()) {
			case R.id.tv_add:
				i.setClass(CostequationActivity.this, AddCostequation.class);
				i.putExtra("TYPE", type);
				Bundle bundle = new Bundle();
				if (ServiceNameHandler.allList!=null&&ServiceNameHandler.allList.size()>0) {
					bundle.putSerializable("planFromulaListInfo", (PlanFromulaListInfo)ServiceNameHandler.allList.get(pos));
				}

				i.putExtras(bundle);
				startActivityForResult(i, ADDCOSTEQUATION);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				finish();
				break;
			case R.id.imageView_Linearlayout_Back:
				img_comment_back.setVisibility(View.GONE);
				finish();
				break;
			case R.id.btn_query:
				getQueryFromulaSettingPage(true);
				break;
			case R.id.tv_cropType:
				i.setClass(CostequationActivity.this, CropTypeNameAc.class);
				i.putExtra("fromWhichView",1);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.tv_agriName:
				i.setClass(CostequationActivity.this, CropTypeNameAc.class);
				i.putExtra("fromWhichView",1);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			case R.id.tv_isValid:
				i.setClass(CostequationActivity.this, IsValidAc.class);
				startActivityForResult(i, ISVALID);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break ;
			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 *
	 * @param isQuery
	 *            是否查询
	 */
	public void getQueryFromulaSettingPage(boolean isQuery) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("pager.num",pageNo);
			params.put("pager.size",Constant.SIZE);
			params.put("pvo.formType",type);
			if (isQuery == true) {
				if (!"null".equals(dicId)&&!"".equals(dicId)&&dicId!=null) {
					params.put("pvo.agriId",dicId);

				} else {
					params.put("pvo.agriId","");

				}
				if (!"".equals(tv_isValid.getText().toString())&&!"null".equals(tv_isValid.getText().toString())) {
					if ("有效".equals(tv_isValid.getText().toString())) {
						params.put("pvo.isValid",0);
					} else if ("无效".equals(tv_isValid.getText().toString())){
						params.put("pvo.isValid",1);
					}else{
						params.put("pvo.isValid","");
					}
				}
			}

			TwitterRestClient.get(Constant.QUERYFROMULASETTINGPAGE, params,
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

//									ServiceNameHandler.allList.clear();
									ServiceNameHandler.allList = JSONUtils
											.getQueryFromulaSettingPages(response);

									listview_proplan.onRefreshComplete();
									if (pageNo == 1) {
										setListData(ServiceNameHandler.allList);
									} else {
										typeCostEqucationAdapter.notifyDataSetChanged();
									}

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}else{
			ToastUtils.show(getApplicationContext(), "网络情况不佳，请稍后重试...");
		}
	}
	public void getAdjustStateFromulaValid() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			/*loadingDialog.show();*/

			if ("有效".equals(typeCostEqucationAdapter.viewHolder.isValid.getText())) {
				typeCostEqucationAdapter.viewHolder.isValid.setText("无效");
			} else {
				typeCostEqucationAdapter.viewHolder.isValid.setText("有效");
			}
			listview_proplan.setAdapter(typeCostEqucationAdapter);
			typeCostEqucationAdapter.notifyDataSetChanged();

			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("fromula.formId",((PlanFromulaListInfo)ServiceNameHandler.allList.get(pos)).getFormId());
			params.put("fromula.formType",type);
			params.put("fromula.isValid",((PlanFromulaListInfo)ServiceNameHandler.allList.get(pos)).getIsValid());
			TwitterRestClient.get(Constant.ADJUSTSTATEFROMULAVALID, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							/*if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}*/
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
									getQueryFromulaSettingPage(false);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}else{
			ToastUtils.show(getApplicationContext(), "网络情况不佳，请稍后重试...");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case CROPTYPENAME:
					if (type == 1) {
						// 农产品名称数据
						String cropType = data.getStringExtra("cropType");
						tv_cropType.setText(cropType);
					} else {
						// 作物名称数据
						String cropType = data.getStringExtra("cropType");
						tv_agriName.setText(cropType);
					}
					dicCode = data.getStringExtra("dicCode");
					dicId = data.getStringExtra("dicId");
					break;
				case ISVALID:
					Bundle bundle = data.getExtras();
					if (bundle!=null) {
						IsValidInfo isValidInfo = (IsValidInfo) bundle.getSerializable("isValidInfo");
						tv_isValid.setText(isValidInfo.getIsValid());
					}
					break ;
				default:
					break;
			}
		}
	}
	/*成本公式管理删除*/
	public void getDeleteFromulaSetting(int position) {
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
			params.put("fromula.formId",((PlanFromulaListInfo)ServiceNameHandler.allList.get(position)).getFormId());
			ServiceNameHandler.allList.remove(position);
			typeCostEqucationAdapter.notifyDataSetChanged();
			TwitterRestClient.get(Constant.DELETEFROMULASETTINGPAGE, params,
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
									ToastUtils.show(getApplicationContext(),
											"删除失败");
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
									ToastUtils.show(getApplicationContext(),
											"删除成功");
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
