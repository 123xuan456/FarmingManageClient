package com.ruicheng.farmingmanageclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ruicheng.farmingmanageclient.adapter.CropLevelAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CropLevelInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * 货品等级选择界面
 *
 * @author zhaobeibei
 *
 */
public class CropLevelAc extends BaseActivity {

	private ListView listview_servicename ;
	private List<Object> listAll ;
	private Bundle bundle ;
	private int fromWhichView;    // 0  UpdateRecordAc界面
	//1 AddDetailAc界面
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_servicestationname);
		bundle = getIntent().getExtras();
		if (bundle!=null) {
			fromWhichView = bundle.getInt("fromWhichView");
		}

		init() ;
		setListAll();
		setListener();
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
		listview_servicename = (ListView) findViewById(R.id.listview_servicename);
	}

	@Override
	public void setListener() {
		listview_servicename.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				if (fromWhichView == 0) {
					i.setClass(getApplicationContext(), UpdateRecordAc.class);
				} else if (fromWhichView ==1){
					i.setClass(getApplicationContext(), AddDetailAc.class);
				}
				i.putExtra("croplevel", ((CropLevelInfo)listAll.get(position)).getCropLevel());
				setResult(RESULT_OK, i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});
	}
	public void setListAll() {
		listAll = new ArrayList<Object>();
		for (int i = 0; i < 3; i++) {
			CropLevelInfo cropLevelInfo = new CropLevelInfo();
			if (i ==0 ) {
				cropLevelInfo.setCropLevel("A");
			} else if (i ==1){
				cropLevelInfo.setCropLevel("AA");
			}else if (i ==2){
				cropLevelInfo.setCropLevel("AAA");
			}
			listAll.add(cropLevelInfo);
		}

		CropLevelAdapter serviceNameAdapter = new CropLevelAdapter(listAll, getApplicationContext());
		listview_servicename.setAdapter(serviceNameAdapter);

	}
}
