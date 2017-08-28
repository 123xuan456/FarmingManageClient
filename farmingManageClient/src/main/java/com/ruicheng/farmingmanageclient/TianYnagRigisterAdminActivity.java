package com.ruicheng.farmingmanageclient;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruicheng.farmingmanageclient.adapter.HarvestRegisterAdminAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.QueryData;

import java.util.ArrayList;
import java.util.List;
/**
 * 田洋收成登记管理界面
 *
 * @author zhaobeibei
 *
 */
public class TianYnagRigisterAdminActivity extends BaseActivity {

	private PullToRefreshListView listview_harvestregisteradmin ;
	private List<Object> listAll ;
	private HarvestRegisterAdminAdapter harvestRegisterAdminAdapter;
	private ImageView img_comment_back ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tianynagrigisteradmin);
		init() ;
		setListener() ;
		setListData();
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

		listview_harvestregisteradmin = (PullToRefreshListView) findViewById(R.id.listview_harvestregisteradmin);
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back) ;
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/*openOrCloseKeybd(false);*/
						img_comment_back.setVisibility(View.GONE);
						finish();
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});

	}

	@Override
	public void setListener() {

	}
	public void setListData() {
		listAll = new ArrayList<Object>() ;
		for (int i = 0; i < 10; i++) {
			QueryData queryData = new QueryData();
			queryData.setOperateDate("2015-08-"+i);
			queryData.setType("类型"+i);
			queryData.setDescribtion("描述"+i);
			listAll.add(queryData);
		}
		harvestRegisterAdminAdapter = new HarvestRegisterAdminAdapter(listAll, getApplicationContext());
		listview_harvestregisteradmin.setAdapter(harvestRegisterAdminAdapter);
	}

}
