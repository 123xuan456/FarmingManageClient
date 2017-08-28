package com.ruicheng.farmingmanageclient.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 *
 * @version V1.0
 */
public class AsynImageLoader {

	private HashMap<String, SoftReference<Bitmap>> imageCache;
	private HashMap<String, ImageView> imageViews;
	private Context context;
	public AsynImageLoader(Context context) {// ????
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		imageViews = new HashMap<String, ImageView>();
		this.context=context;
	}

	/**
	 */
	public Bitmap loadDrawableFromNet(final ImageView imageView,
			final String imageUrl) {
		return loadDrawable(imageView, imageUrl, new LoadCallBack() {
			public Bitmap load(String uri) {
				return loadImageFromNet(uri);
			}
		});
	}

	/**
	 *
	 * @param imageView
	 *            ???????ImageView
	 * @param imageUrl
	 * @return ??
	 */
	public Bitmap loadDrawableFromLocal(final ImageView imageView,
			final String imageUrl) {
		return loadDrawable(imageView, imageUrl, new LoadCallBack() {
			public Bitmap load(String uri) {
				return loadImageFromLocal(uri);
			}
		});
	}

	/**
	 * @param imageView
	 */
	private Bitmap loadDrawable(final ImageView imageView,
			final String imageUrl, final LoadCallBack load) {

		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			Bitmap bitmap = softReference.get();
			if (bitmap != null) {
				return bitmap;//
			}
		}

		if (!imageViews.containsKey(imageUrl)) {
			imageViews.put(imageUrl, imageView);
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageViews.get(imageUrl).setImageBitmap((Bitmap) message.obj);
			}
		};

		new Thread() {
			public void run() {
				Bitmap bitmap = load.load(imageUrl);// ?
				imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
				Message message = handler.obtainMessage(0, bitmap);
			
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	private interface LoadCallBack {
		public Bitmap load(String uri);
	}

	/**
	 *
	 * @param url
	 * @return
	 */
	public Bitmap loadImageFromNet(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			return BitmapFactory.decodeStream(i);
		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
			return BitmapFactory.decodeResource(context
					.getResources(), R.drawable.ic_launcher);
			
		} catch (IOException e) {
			e.printStackTrace();
			return BitmapFactory.decodeResource(context
					.getResources(), R.drawable.ic_launcher);
			
		}
		
	}

	/**
	 * @param path
	 * @return
	 */
	public Bitmap loadImageFromLocal(String path) {
		return BitmapFactory.decodeFile(path);
	}
}
