package com.ruicheng.farmingmanageclient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.ruicheng.farmingmanageclient.adapter.IsPackingAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.IsPackingInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * 是否包装界面
 *
 */

public class IsPackageAc extends BaseActivity {

	private ImageView img_comment_back;

	private ListView listview_dc ;
	private Dialog loadingDialog ;
	private List<Object> listAll ;
	private IsPackingAdapter isPackingAdapter ;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_district);

		init();
		setListener();

		setDataToView();

	}
	public void setDataToView(){

		listAll = new ArrayList<Object>();

		for(int i = 0 ; i< 2; i++){

			IsPackingInfo isPackingInfo = new IsPackingInfo();

			if (i == 0) {
				isPackingInfo.setIsPacking("是");

			} else {
				isPackingInfo.setIsPacking("否");

			}
			listAll.add(isPackingInfo);
		}

		isPackingAdapter = new IsPackingAdapter(listAll, getApplicationContext());

		listview_dc.setAdapter(isPackingAdapter);
	}
	@Override
	public void init() {
		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/* openOrCloseKeybd(false); */
						img_comment_back.setVisibility(View.GONE);
						finish();
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});
		listview_dc = (ListView) findViewById(R.id.listview_dc);

	}

	@Override
	public void setListener() {
		listview_dc.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(),AddCropActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("ispacking",((IsPackingInfo)(listAll.get(position))).getIsPacking());
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
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
}
