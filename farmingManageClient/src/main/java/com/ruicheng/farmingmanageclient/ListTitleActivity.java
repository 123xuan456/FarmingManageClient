package com.ruicheng.farmingmanageclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.base.BaseActivity;

/**
 * 复用二级标题界面
 *
 * @author zhaobeibei
 *
 */
public class ListTitleActivity extends BaseActivity {

	private LinearLayout layout_title1, layout_title2;
	private TextView tv_title1, tv_title2;
	private Bundle bundle ;
	private int kind ;
	private ImageView img_comment_back ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_listtitle);
		bundle = getIntent().getExtras();
		init();
		setListener();
		if (bundle!=null) {
			kind = bundle.getInt("kind", -1);
			if (kind == 2) {
				tv_title1.setText("收成登记");
				tv_title2.setText("收成登记管理");
			} else if (kind == 3){
				tv_title1.setText("添加收货信息");
				tv_title2.setText("添加信息管理");
			}
			else if (kind == 4){
				tv_title1.setText("生产履历统计");
				layout_title2.setVisibility(View.GONE);
			}
		}
	}
	@Override
	public void init() {

		layout_title1 = (LinearLayout) findViewById(R.id.layout_title1);
		layout_title2 = (LinearLayout) findViewById(R.id.layout_title2);

		tv_title1 = (TextView) findViewById(R.id.tv_title1);
		tv_title2 = (TextView) findViewById(R.id.tv_title2);

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode ==event.KEYCODE_BACK) {
			/*android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void setListener() {
		LayoutListener layoutListener = new LayoutListener();
		layout_title1.setOnClickListener(layoutListener);
		layout_title2.setOnClickListener(layoutListener);
	}
	public class LayoutListener implements OnClickListener {
		Intent intent = new Intent();
		@Override
		public void onClick(View v) {
			if (R.id.layout_title1 == v.getId()) {
				layout_title1.setBackgroundColor(R.color.gray);
				if (kind == 2) {
					intent.setClass(getApplicationContext(), TianYangHarvestActivity.class);
				} else if(kind == 3){
					//添加收货信息界面
					intent.setClass(getApplicationContext(), CropSellActivity.class);
				}else if(kind == 4){
					//生产履历统计界面
					intent.setClass(getApplicationContext(), ProductionRecordStatisticsActivity.class);
				}
			} else {
				layout_title1.setBackgroundColor(R.color.gray);
				if (kind == 2) {
					intent.setClass(getApplicationContext(), TianYnagRigisterAdminActivity.class);
				} else if(kind == 3){
					//收货信息管理界面
					intent.setClass(getApplicationContext(), ReceivingInfoManageActivity.class);
				}
			}
			startActivity(intent);
			layout_title1.setBackgroundColor(R.color.white);
		}

	}

}
