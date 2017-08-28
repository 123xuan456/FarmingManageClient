package com.ruicheng.farmingmanageclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.AlbumActivity;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.ShowAllPhotoActivity;
import com.ruicheng.farmingmanageclient.bean.CameraImage;
import com.ruicheng.farmingmanageclient.util.BitmapDisplay;
import com.ruicheng.farmingmanageclient.util.BitmapDisplay.ImageCallback;

import java.util.ArrayList;

/**
 * 图片相册所有文件夹适配器
 * @author ZhangZhaoCheng
 */
public class ImageFolderAdapter extends BaseAdapter {

	private Context mContext;
	private DisplayMetrics dm;
	BitmapDisplay cache;
	final String TAG = getClass().getSimpleName();
	public ImageFolderAdapter(Context c) {
		cache = new BitmapDisplay();
		init(c);
	}

	public void init(Context c) {
		mContext = c;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
	}



	@Override
	public int getCount() {
		return AlbumActivity.contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
							  Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	private class ViewHolder {
		public ImageView backImage;
		public ImageView imageView;
		public ImageView choose_back;
		public TextView folderName;
		public TextView fileNum;
	}
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.plugin_camera_select_folder, null);
			holder = new ViewHolder();
			holder.backImage = (ImageView) convertView
					.findViewById(R.id.file_back);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.file_image);
			holder.choose_back = (ImageView) convertView
					.findViewById(R.id.choose_back);
			holder.folderName = (TextView) convertView.findViewById(R.id.name);
			holder.fileNum = (TextView) convertView.findViewById(R.id.filenum);
			holder.imageView.setAdjustViewBounds(true);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		String path;
		if (AlbumActivity.contentList.get(position).imageList != null) {
			path = AlbumActivity.contentList.get(position).imageList.get(0).imagePath;
			holder.folderName.setText(AlbumActivity.contentList.get(position).bucketName);

			holder.fileNum.setText("" + AlbumActivity.contentList.get(position).count);

		} else{
			path = "android_hybrid_camera_default";
		}

		if (path.contains("android_hybrid_camera_default")){
			holder.imageView.setImageResource(R.drawable.plugin_camera_no_pictures);
		}
		else {
			final CameraImage item = AlbumActivity.contentList.get(position).imageList.get(0);
			holder.imageView.setTag(item.imagePath);
			cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath,
					callback);
		}
		holder.imageView.setOnClickListener(new ImageViewClickListener(
				position,holder.choose_back));

		return convertView;
	}

	private class ImageViewClickListener implements OnClickListener {
		private int position;
		private ImageView choose_back;
		public ImageViewClickListener(int position,ImageView choose_back) {
			this.position = position;
			this.choose_back = choose_back;
		}

		public void onClick(View v) {
			ShowAllPhotoActivity.dataList = (ArrayList<CameraImage>) AlbumActivity.contentList.get(position).imageList;
			Intent intent = new Intent();
			String folderName = AlbumActivity.contentList.get(position).bucketName;
			intent.putExtra("folderName", folderName);
			intent.setClass(mContext, ShowAllPhotoActivity.class);
			mContext.startActivity(intent);
			choose_back.setVisibility(v.VISIBLE);
			((Activity)mContext).finish();
		}
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

}
