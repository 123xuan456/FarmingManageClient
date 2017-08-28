package com.ruicheng.farmingmanageclient;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.base.BaseActivity;

/**
 * 田阳农事项目子界面
 *
 * @author zhaobeibei
 *
 */

public class TianYangAgricultureActiivity extends BaseActivity {
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(R.layout.activity_tianyangagriculture);
	}
	private ImageView img_comment_back ;
	@Override
	public void init() {
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
		// TODO Auto-generated method stub

	}

}
