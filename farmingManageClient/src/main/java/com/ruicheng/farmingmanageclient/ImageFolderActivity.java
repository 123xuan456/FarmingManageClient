package com.ruicheng.farmingmanageclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.adapter.ImageFolderAdapter;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
//相册页面
public class ImageFolderActivity extends Activity {
	private ImageFolderAdapter folderAdapter;
	private TextView bt_cancel;
	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_image_file);
		mContext = this;
		bt_cancel = (TextView) findViewById(R.id.cancel);
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView textView = (TextView) findViewById(R.id.headerTitle);
		textView.setText(R.string.photo);
		folderAdapter = new ImageFolderAdapter(this);
		gridView.setAdapter(folderAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				ToastUtils.show(mContext, "选择照片");
			}
		});
	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
//			BimpHandler.tempSelectBitmap.clear();
//			Intent intent = new Intent();
//			intent.setClass(mContext, MainActivity.class);
//			startActivity(intent);
			finish();
		}
	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(mContext, MainActivity.class);
//			startActivity(intent);
//		}
//
//		return true;
//	}

}
