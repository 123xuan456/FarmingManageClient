package com.ruicheng.farmingmanageclient.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public abstract class BaseActivity extends FragmentActivity {
	/** 广播action */
	public static final String SYSTEM_EXIT = "com.example.exitsystem.system_exit";
	/** 接收器 */
	private MyReceiver receiver;
	@Override
	public void setContentView(int layoutResID) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 强制为横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		super.setContentView(layoutResID);
		//注册广播，用于退出程序
		IntentFilter filter = new IntentFilter();
		filter.addAction(SYSTEM_EXIT);
		receiver = new MyReceiver();
		this.registerReceiver(receiver, filter);

	}
	@Override
	protected void onDestroy() {
		//记得取消广播注册
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}
	public abstract void init();
	public abstract void setListener();
}
