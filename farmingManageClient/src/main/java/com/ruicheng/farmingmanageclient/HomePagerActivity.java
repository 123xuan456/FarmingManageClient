package com.ruicheng.farmingmanageclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

public class HomePagerActivity extends BaseActivity {

	private ImageView iv_welcome ;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_homepager);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					Intent intent  = new Intent(HomePagerActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != KeyEvent.ACTION_UP) {
			quiteApp();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	long endTime;

	/**
	 * 退出程序
	 */
	private void quiteApp() {
		if ((System.currentTimeMillis() - endTime) > 2000) {
			ToastUtils.show(this, "再按一次退出程序");
			endTime = System.currentTimeMillis();
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}
}
