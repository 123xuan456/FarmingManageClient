package com.ruicheng.farmingmanageclient.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ruicheng.farmingmanageclient.bean.CameraImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 图片保存修正类
 * @author ZhangZhaoCheng
 */
public class BimpHandler {
	public static ArrayList<CameraImage> tempSaveBitmap = new ArrayList<CameraImage>();
	public static ArrayList<Object> tempSelectPostList = new ArrayList<Object>();

	//存储所选图片的集合
	public static ArrayList<String> tempSelectFilePath = new ArrayList<String>();

	public static int max = 0;
	/**选择照片的最大张数*/
	public static int num = 6;

	/**选择的图片的临时列表*/
	public static ArrayList<CameraImage> tempSelectBitmap = new ArrayList<CameraImage>();
	public static ArrayList<Bitmap> listSelectBitmap = new ArrayList<Bitmap>();

	/**
	 * 修正图片大小 防止内存溢出
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
}
