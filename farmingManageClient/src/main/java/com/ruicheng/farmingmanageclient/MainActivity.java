package com.ruicheng.farmingmanageclient;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.ruicheng.farmingmanageclient.adapter.DistrictAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.db.LoginInfoHelper;
import com.ruicheng.farmingmanageclient.fragment.ProBaseInfoFragment;
import com.ruicheng.farmingmanageclient.fragment.ProductionManageFragment;
import com.ruicheng.farmingmanageclient.fragment.ProductionPlanFragment;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;

public class MainActivity extends BaseActivity {
	private Fragment fragment;
//	private FragmentManager fm;
	private ProductionManageFragment productionManageFragment = new ProductionManageFragment();
	private ProductionPlanFragment productionPlanFragment = new ProductionPlanFragment();
	private ProBaseInfoFragment proBaseInfoFragment = new ProBaseInfoFragment();
	private Button btn_promanage, btn_proplan, btn_probaseinfo;
	private TextView tv_more;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private LinearLayout linear_more ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化控件
		//推送
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, "dkw5FWRPYGGwZujaRd4vg4az");

/*		ChangeViewBroadCastReceiver changeViewBroadCastReceiver = new ChangeViewBroadCastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("CostequationActivity");
		registerReceiver(changeViewBroadCastReceiver, intentFilter);*/

		init();
		setListener();
		initPopupWindow();
//		fm = getSupportFragmentManager();
//		FragmentTransaction ft = fm.beginTransaction();


		fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
		if (fragment == null) {
			fragment =new  ProductionManageFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.main_container,new  ProductionManageFragment()).commit();
		}

//		ft.add(R.id.main_container, productionManageFragment);
//		ft.add(R.id.main_container, productionPlanFragment);
//		ft.add(R.id.main_container, proBaseInfoFragment);
//
//		ft.show(productionManageFragment);
//		ft.hide(productionPlanFragment);
//		ft.hide(proBaseInfoFragment);
//		//提交
//		ft.commit();

	}

	private Button.OnClickListener btnClickManager = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_promanage:

					linear_more.setVisibility(View.VISIBLE);

				/*
				 * btn_promanage.setBackgroundColor(Color.parseColor(Constant.
				 * MAIIN_NAVITION_SELECT));
				 * btn_proplan.setBackgroundColor(Color.
				 * parseColor(Constant.MAIIN_NAVITION));
				 * btn_probaseinfo.setBackgroundColor
				 * (Color.parseColor(Constant.MAIIN_NAVITION));
				 */

					btn_promanage
							.setBackgroundResource(R.drawable.pro_manage_naomal_press);
					btn_proplan.setBackgroundResource(R.drawable.plan_task_normal);
					btn_probaseinfo
							.setBackgroundResource(R.drawable.base_info_normal);
					fragment=new ProductionManageFragment();
//					FragmentTransaction ft = fm.beginTransaction();
//					ft.show(productionManageFragment);
//					ft.hide(productionPlanFragment);
//					ft.hide(proBaseInfoFragment);
//					ft.commit();
					break;
				case R.id.btn_proplan:

					linear_more.setVisibility(View.GONE);

				/*
				 * btn_promanage.setBackgroundColor(Color.parseColor(Constant.
				 * MAIIN_NAVITION));
				 * btn_proplan.setBackgroundColor(Color.parseColor
				 * (Constant.MAIIN_NAVITION_SELECT));
				 * btn_probaseinfo.setBackgroundColor
				 * (Color.parseColor(Constant.MAIIN_NAVITION));
				 */

					btn_promanage
							.setBackgroundResource(R.drawable.pro_manage_naomal);
					btn_proplan.setBackgroundResource(R.drawable.plan_task_press);
					btn_probaseinfo
							.setBackgroundResource(R.drawable.base_info_normal);
					fragment=new ProductionPlanFragment();

//					FragmentTransaction ft1 = fm.beginTransaction();
//					ft1.show(productionPlanFragment);
//					ft1.hide(productionManageFragment);
//					ft1.hide(proBaseInfoFragment);
//					ft1.commit();
					break;
				case R.id.btn_probaseinfo:

					linear_more.setVisibility(View.GONE);

				/*
				 * btn_promanage.setBackgroundColor(Color.parseColor(Constant.
				 * MAIIN_NAVITION));
				 * btn_proplan.setBackgroundColor(Color.parseColor
				 * (Constant.MAIIN_NAVITION));
				 * btn_probaseinfo.setBackgroundColor
				 * (Color.parseColor(Constant.MAIIN_NAVITION_SELECT));
				 */

					btn_promanage
							.setBackgroundResource(R.drawable.pro_manage_naomal);
					btn_proplan.setBackgroundResource(R.drawable.plan_task_normal);
					btn_probaseinfo
							.setBackgroundResource(R.drawable.base_info_press);
					fragment=new ProBaseInfoFragment();
//					FragmentTransaction ft2 = fm.beginTransaction();
//					ft2.show(proBaseInfoFragment);
//					ft2.hide(productionPlanFragment);
//					ft2.hide(productionManageFragment);
//					ft2.commit();

					break;
				case R.id.linear_more:

					Log.i("","ddddddddddd");
					tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.translate_in));
					pop.showAtLocation(btn_probaseinfo, Gravity.TOP, 120, 200);
					break;
				default:
					break;
			}
			if (fragment != null) {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
			}
		}
	};

	private long mExitTime;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				Intent intent = new Intent();
				intent.setAction(BaseActivity.SYSTEM_EXIT);
				sendBroadcast(intent);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	public void init() {

		linear_more = (LinearLayout)findViewById(R.id.linear_more);

		btn_promanage = (Button) findViewById(R.id.btn_promanage);
		btn_proplan = (Button) findViewById(R.id.btn_proplan);
		btn_probaseinfo = (Button) findViewById(R.id.btn_probaseinfo);

		tv_more = (TextView) findViewById(R.id.tv_more);

		/*
		 * btn_promanage.setBackgroundColor(Color.parseColor(Constant.
		 * MAIIN_NAVITION_SELECT));
		 * btn_proplan.setBackgroundColor(Color.parseColor
		 * (Constant.MAIIN_NAVITION));
		 * btn_probaseinfo.setBackgroundColor(Color.parseColor
		 * (Constant.MAIIN_NAVITION));
		 */
	}

	public void setListener() {
		btn_promanage.setOnClickListener(btnClickManager);
		btn_proplan.setOnClickListener(btnClickManager);
		btn_probaseinfo.setOnClickListener(btnClickManager);
		linear_more.setOnClickListener(btnClickManager);
	}

	// 解绑、推送
	private void unBindForApp() {
		// Push: 解绑
		PushManager.stopWork(getApplicationContext());
	}

	/*	public class ChangeViewBroadCastReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("CostequationActivity".equals(intent.getAction())) {
                }
            }
        }*/
	private DistrictAdapter districtAdapter ;
	private void initPopupWindow() {
		pop = new PopupWindow(MainActivity.this);
		View view = getLayoutInflater().inflate(
				R.layout.item_tianyangpopupmenu_main, null);
		tianynagmenu_popup = (LinearLayout) view
				.findViewById(R.id.equip_popup_menu);
		pop.setWidth(LayoutParams.FILL_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);

		ListView listview_district = (ListView) view.findViewById(R.id.listview_district);
		//从数据库中取数据
		LoginInfoHelper loginInfoHelper = new LoginInfoHelper(
				getApplicationContext());

		ServiceNameHandler.allList = loginInfoHelper.selectLoginfo();

		districtAdapter = new DistrictAdapter(ServiceNameHandler.allList, getApplicationContext());
		listview_district.setAdapter(districtAdapter);

		listview_district.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();

				PreferencesUtils.remove(getApplicationContext(), Constant.DCID);
				PreferencesUtils.remove(getApplicationContext(), Constant.DCNAME);

				PreferencesUtils.putString(getApplicationContext(), Constant.DCID,
						((AcquireAllDcInfo)ServiceNameHandler.allList.get(position)).getDcId());
				PreferencesUtils.putString(getApplicationContext(), Constant.DCNAME,
						((AcquireAllDcInfo)ServiceNameHandler.allList.get(position)).getDcName());

			}
		});
	}
}
