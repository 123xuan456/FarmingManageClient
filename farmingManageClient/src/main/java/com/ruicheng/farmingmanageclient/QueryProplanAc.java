package com.ruicheng.farmingmanageclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PlanListInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class QueryProplanAc extends BaseActivity {

	private Intent intent;
	private int type;
	private LinearLayout linear_agricuturename, linear_cropname,
			linear_zhongzhiliang, linear_jiagongliang;
	private LinearLayout ImageView_Linearlayout_Back;
	private ImageView img_comment_back;
	private View view1, view2, view3, view4;
	private Bundle bundle;
	private PlanListInfo planListInfo;
	private TextView et_agriName, et_planName, et_cropName, et_planAmou,
			et_allPlanAmou, et_planDrawPers, et_predStarDate, et_predEndDate, et_planDrawDate;
	private Button btn_last ,btn_next;
	private TextView tv_more ;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private int pos =0;
	private Dialog loadingDialog ;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_queryproplan);
		intent = getIntent();
		bundle = intent.getExtras();
		if (bundle != null) {
			type = bundle.getInt("TYPE", -1);
			planListInfo = (PlanListInfo) bundle
					.getSerializable("planListInfo");
		}
		init();
		setListener();
		initPopupWindow();
	}

	@Override
	public void init() {

		tv_more = (TextView) findViewById(R.id.tv_more);
		loadingDialog = DialogUtils.requestDialog(this);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		ImageView_Linearlayout_Back = (LinearLayout) findViewById(R.id.ImageView_Linearlayout_Back);
		linear_agricuturename = (LinearLayout) findViewById(R.id.linear_agricuturename);
		linear_cropname = (LinearLayout) findViewById(R.id.linear_cropname);
		linear_zhongzhiliang = (LinearLayout) findViewById(R.id.linear_zhongzhiliang);
		linear_jiagongliang = (LinearLayout) findViewById(R.id.linear_jiagongliang);

		et_agriName = (TextView) findViewById(R.id.et_agriName);
		et_planName = (TextView)findViewById(R.id.et_planName);
		et_cropName = (TextView)findViewById(R.id.et_cropName);
		et_planAmou = (TextView)findViewById(R.id.et_planAmou);
		et_allPlanAmou = (TextView)findViewById(R.id.et_allPlanAmou);
		et_planDrawPers = (TextView)findViewById(R.id.et_planDrawPers);
		et_predStarDate = (TextView)findViewById(R.id.et_predStarDate);
		et_predEndDate = (TextView)findViewById(R.id.et_predEndDate);
		et_planDrawDate = (TextView)findViewById(R.id.et_planDrawDate);

		setDataToView(type);
		btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
	}

	@Override
	public void setListener() {
		IntentViewListener intentViewListener = new IntentViewListener();
		ImageView_Linearlayout_Back.setOnClickListener(intentViewListener);
		tv_more.setOnClickListener(intentViewListener);

		btn_last.setOnClickListener(intentViewListener);
		btn_next.setOnClickListener(intentViewListener);

	}

	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ImageView_Linearlayout_Back:
					img_comment_back.setVisibility(View.GONE);
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), ProplanActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					break;
				case R.id.btn_last:
					if (pos == 0) {
						ToastUtils.show(getApplicationContext(), "当前已是第一条");
						btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
						return ;
					}
					btn_next.setBackgroundColor(Color.parseColor("#009933"));
					pos -- ;
					planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(pos);
					setDataToView(Integer.parseInt(planListInfo.getPlanType()));

					break ;
				case R.id.btn_next:
					if (pos ==ServiceNameHandler.allList.size()-1) {
						ToastUtils.show(getApplicationContext(), "无更多数据..");
						return ;
					}
					btn_last.setBackgroundColor(Color.parseColor("#009933"));
					pos ++ ;
					planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(pos);
					setDataToView(Integer.parseInt(planListInfo.getPlanType()));
					if (pos == 0) {
						btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
					}else if (pos ==ServiceNameHandler.allList.size()-1){
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
					}
					break ;
				case R.id.tv_more:

					tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
							QueryProplanAc.this, R.anim.activity_translate_in));
					pop.showAtLocation(tv_more, Gravity.BOTTOM, 0, 0);

					break ;
				default:
					break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			startActivity(new Intent(this, ProplanActivity.class));
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	private void initPopupWindow() {
		pop = new PopupWindow(QueryProplanAc.this);
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
		final Button bt1 = (Button) view.findViewById(R.id.item_equippopup_delete);
		Button bt2 = (Button) view.findViewById(R.id.item_equippopup_modify);
		Button bt3 = (Button) view.findViewById(R.id.item_equippopup_cancel);
		Button bt4 = (Button) view.findViewById(R.id.item_equippopup_query);
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
				new AlertDialog.Builder(QueryProplanAc.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										if (pos<0) {
											ToastUtils.show(getApplicationContext(), "已无数据");
											bt1.setBackgroundColor(Color.parseColor("#F2F2F2"));
											return ;
										}else{
											getDeleteProductionPlan(pos);
										}
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
				i.setClass(getApplicationContext(),UpdateProplanAc.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("planListInfo", planListInfo);
				bundle.putInt("type", type);
				i.putExtras(bundle);
				startActivity(i);
				finish();
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
				i.setClass(getApplicationContext(), CheckProplanAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("type", type);
				bundle.putSerializable("planListInfo", planListInfo);
				i.putExtras(bundle);
				startActivity(i);
				finish();
			}
		});
	}
	/**
	 * 生产计划管理数据删除
	 *
	 */
	public void getDeleteProductionPlan(final int position) {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType",Constant.ANDROIDACCESSTYPE);
			params.put("userId",PreferencesUtils.getInt(getApplicationContext(), Constant.USERID)+"");
			params.put("userName",PreferencesUtils.getString(getApplicationContext(),Constant.USERNAME));
			params.put("plan.planId",((PlanListInfo)ServiceNameHandler.allList.get(pos)).getPlanId());
			ServiceNameHandler.allList.remove(pos);
			TwitterRestClient.get(Constant.DELETEPRODUCTIONPLN, params,
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
							ToastUtils.show(getApplicationContext(), "删除失败");
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
									if (pos >0) {
										pos-- ;
										planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(pos);
										setDataToView(Integer.parseInt(planListInfo.getPlanType()));

									} else if(pos==0){
										setClearDataToView(Integer.parseInt(planListInfo.getPlanType()));
										if (ServiceNameHandler.allList.size()>0) {
											planListInfo = (PlanListInfo) ServiceNameHandler.allList.get(0);
											setDataToView(Integer.parseInt(planListInfo.getPlanType()));
											pos = (ServiceNameHandler.allList.size()-1);
											pos-- ;
										} else {
											pos -- ;
											if (pos<0) {
												ToastUtils.show(getApplicationContext(), "已无数据");
												finish();
											}
										}
									}else {
										if (pos -- <0) {
											ToastUtils.show(getApplicationContext(), "已无数据");
										}
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
	public void setDataToView(int type){
		//显示查询出来的数据
		et_planName.setText(planListInfo.getPlanName());
		et_planDrawPers.setText(planListInfo.getPlanDrawPers());
		et_predStarDate.setText(planListInfo.getPredStarDate());
		et_predEndDate.setText(planListInfo.getPredEndDate());
		et_planDrawDate.setText(DateUtils.strDateFormatSh(planListInfo.getPlanDrawDate()));
		if (type == 0) {
			et_planAmou.setText(planListInfo.getPlanAmou());
			et_cropName.setText(planListInfo.getAgriName());
			linear_agricuturename.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_zhongzhiliang.setVisibility(View.VISIBLE);
			linear_jiagongliang.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
		} else {
			et_allPlanAmou.setText(planListInfo.getPlanAmou());
			et_agriName.setText(planListInfo.getAgriName());
			linear_agricuturename.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_zhongzhiliang.setVisibility(View.GONE);
			linear_jiagongliang.setVisibility(View.VISIBLE);
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);
		}
	}
	public void setClearDataToView(int type){
		//显示查询出来的数据
		et_planName.setText(planListInfo.getPlanName());
		et_planDrawPers.setText("");
		et_predStarDate.setText("");
		et_predEndDate.setText("");
		et_planDrawDate.setText("");
		//btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		if (type == 0) {
			et_planAmou.setText("");
			et_cropName.setText("");
			linear_agricuturename.setVisibility(View.GONE);
			linear_cropname.setVisibility(View.VISIBLE);
			linear_zhongzhiliang.setVisibility(View.VISIBLE);
			linear_jiagongliang.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.GONE);
		} else {
			et_allPlanAmou.setText("");
			et_agriName.setText("");
			linear_agricuturename.setVisibility(View.VISIBLE);
			linear_cropname.setVisibility(View.GONE);
			linear_zhongzhiliang.setVisibility(View.GONE);
			linear_jiagongliang.setVisibility(View.VISIBLE);
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.VISIBLE);
		}
	}
}
