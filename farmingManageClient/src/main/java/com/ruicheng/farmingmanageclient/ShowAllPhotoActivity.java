package com.ruicheng.farmingmanageclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ruicheng.farmingmanageclient.adapter.AlbumGridViewAdapter;
import com.ruicheng.farmingmanageclient.bean.CameraImage;
import com.ruicheng.farmingmanageclient.util.BimpHandler;
import com.ruicheng.farmingmanageclient.util.PublicWay;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;

import java.util.ArrayList;

/**
 * 从图片文件夹ImageFolderActivity.java选择跳转到这
 *	显示一个文件夹里面的所有图片时的界面
 * @author ZhangZhaoCheng
 */
public class ShowAllPhotoActivity extends Activity {
	private GridView gridView;
	private ProgressBar progressBar;
	private AlbumGridViewAdapter gridImageAdapter;
	// 完成按钮
	private TextView okButton;
	// 预览按钮
	private TextView preview;
	// 返回按钮
	private TextView back;
	// 取消按钮
	private TextView cancel;
	// 标题
	private TextView headTitle;
	private Intent intent;
	private Context mContext;
	public static ArrayList<CameraImage> dataList = new ArrayList<CameraImage>();
	private String isAddOrUpdate;//判断是修改还是添加

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_show_all_photo);
		PublicWay.activityList.add(this);
		mContext = this;
		back = (TextView) findViewById(R.id.showallphoto_back);
		cancel = (TextView) findViewById(R.id.showallphoto_cancel);
		preview = (TextView) findViewById(R.id.showallphoto_preview);
		okButton = (TextView) findViewById(R.id.showallphoto_ok_button);
		headTitle = (TextView) findViewById(R.id.showallphoto_headtitle);
		this.intent = getIntent();
		String folderName = intent.getStringExtra("folderName");
		if (folderName.length() > 8) {
			folderName = folderName.substring(0, 9) + "...";
		}
		headTitle.setText(folderName);
		cancel.setOnClickListener(new CancelListener());
		back.setOnClickListener(new BackListener(intent));
		preview.setOnClickListener(new PreviewListener());
		init();
		initListener();
		isAddOrUpdate=PreferencesUtils.getString(getApplicationContext(),"isAddOrUpdate","");
		//isShowOkBt();
	}

//	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			gridImageAdapter.notifyDataSetChanged();
//		}
//	};

	private class PreviewListener implements OnClickListener {
		public void onClick(View v) {
			if (BimpHandler.tempSelectBitmap.size() > 0) {
				intent.putExtra("position", "2");
				intent.setClass(ShowAllPhotoActivity.this, GalleryActivity.class);
				startActivity(intent);
			}
		}

	}

	private class BackListener implements OnClickListener {// 返回按钮监听
		Intent intent;

		public BackListener(Intent intent) {
			this.intent = intent;
		}

		public void onClick(View v) {
//			intent.setClass(ShowAllPhotoActivity.this, ImageFolderActivity.class);
//			startActivity(intent);
			finish();
		}

	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
//			BimpHandler.tempSelectBitmap.clear();
//			intent.setClass(mContext, MainActivity.class);
//			startActivity(intent);

			finish();
		}
	}

	private void init() {
//		IntentFilter filter = new IntentFilter("data.broadcast.action");
//		registerReceiver(broadcastReceiver, filter);
		progressBar = (ProgressBar) findViewById(R.id.showallphoto_progressbar);
		progressBar.setVisibility(View.GONE);
		gridView = (GridView) findViewById(R.id.showallphoto_myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(this,dataList,
				BimpHandler.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		okButton = (TextView) findViewById(R.id.showallphoto_ok_button);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	//	unregisterReceiver(broadcastReceiver);
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
					@TargetApi(Build.VERSION_CODES.GINGERBREAD)
					public void onItemClick(final ToggleButton toggleButton,
											int position, boolean isChecked,
											Button button) {
						String imagePath = dataList.get(position).getImagePath();

						if (!isAddOrUpdate.isEmpty()){
							if (isAddOrUpdate.equals("UpdateService")){
								Intent intent1 = new Intent("data.broadcast.UpdateService");
								intent1.putExtra("imagePath",imagePath);
								ShowAllPhotoActivity.this.sendBroadcast(intent1);
							}else {
								Intent i = new Intent("data.broadcast.AddPro");
								i.putExtra("imagePath",imagePath);
								ShowAllPhotoActivity.this.sendBroadcast(i);
							}
							//退出页面
							PublicWay.finish();

						}
//						if (BimpHandler.tempSelectBitmap.size() >= BimpHandler.num&&isChecked) {
//							button.setVisibility(View.GONE);
//							toggleButton.setChecked(false);
//							Toast.makeText(ShowAllPhotoActivity.this, R.string.only_choose_num, Toast.LENGTH_SHORT).show();
//							return;
//						}
//
//						if (isChecked) {
//							button.setVisibility(View.VISIBLE);
//							BimpHandler.tempSelectBitmap.add(dataList.get(position));
//							okButton.setText(getResources().getString(R.string.finish)+"(" + BimpHandler.tempSelectBitmap.size()
//									+ "/"+BimpHandler.num+")");
//		0				} else {
//							button.setVisibility(View.GONE);
//							BimpHandler.tempSelectBitmap.remove(dataList.get(position));
//							okButton.setText(getResources().getString(R.string.finish)+"(" + BimpHandler.tempSelectBitmap.size() + "/"+BimpHandler.num+")");
//						}
//						isShowOkBt();
					}
				});

		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				okButton.setClickable(false);
//				if (PublicWay.photoService != null) {
//					PublicWay.selectedDataList.addAll(Bimp.tempSelectBitmap);
//					Bimp.tempSelectBitmap.clear();
//					PublicWay.photoService.onActivityResult(0, -2,
//							intent);
//				}
//				intent.setClass(mContext, MainActivity.class);
//				startActivity(intent);
//				 Intent intent = new Intent();
//				 Bundle bundle = new Bundle();
//				 bundle.putStringArrayList("selectedDataList",
//				 selectedDataList);
//				 intent.putExtras(bundle);
//				 intent.setClass(ShowAllPhoto.this, UploadPhoto.class);
//				 startActivity(intent);


				Intent intent = new Intent();
				intent.setAction("123");
				sendBroadcast(intent);
				finish();
			}
		});
	}

	public void isShowOkBt() {
		if (BimpHandler.tempSelectBitmap.size() > 0) {
			okButton.setText(getResources().getString(R.string.finish)+"(" + BimpHandler.tempSelectBitmap.size() + "/"+BimpHandler.num+")");
			preview.setPressed(true);
			okButton.setPressed(true);
			preview.setClickable(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
			preview.setTextColor(Color.WHITE);
		} else {
			okButton.setText(getResources().getString(R.string.finish)+"(" + BimpHandler.tempSelectBitmap.size() + "/"+BimpHandler.num+")");
			preview.setPressed(false);
			preview.setClickable(false);
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
			preview.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			this.finish();
//			intent.setClass(ShowAllPhotoActivity.this, ImageFolderActivity.class);
//			startActivity(intent);
//		}
//
//		return false;
//	}

	@Override
	protected void onRestart() {
	//	isShowOkBt();
		super.onRestart();
	}

}
