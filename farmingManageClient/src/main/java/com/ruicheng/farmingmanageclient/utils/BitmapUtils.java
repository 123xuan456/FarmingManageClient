package com.ruicheng.farmingmanageclient.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

/**
 * bitmap工具
 * 
 * @author ZhangZhaoCheng
 */
public class BitmapUtils {

	/**
	 * Drawable转换成Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap转换成byte[]
	 * 
	 * @param bmp
	 * @param needRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 80, output);
		if (needRecycle) {
			bmp.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将图片存储到sdcard
	 * 
	 * @param colorImage
	 * @param ImageName
	 * @param path
	 */
	public static void storeImageToSDCARD(Bitmap colorImage, String ImageName,
			String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		File imagefile = new File(file, ImageName + ".jpg");
		try {
			imagefile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagefile);
			colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据路径和图片名
	 * 
	 * @param path
	 * @param picName
	 * @return
	 */
	public static Bitmap getImg(String path, String picName) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		try {
			File file = new File(path, picName + ".png");
			FileInputStream inputStream = new FileInputStream(file);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 根据路径和图片名
	 * 
	 * @param path
	 * @param picName
	 * @return
	 */
	public static Bitmap getPostImg(String path, String picName,String uploadType) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		try {
			File file = new File(path);
			FileInputStream inputStream = new FileInputStream(file);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 网络获取图片 get方法
	 * 
	 * @param urlpath
	 * @return
	 * @throws Exception
	 */
	public static Bitmap getImageByNet(String urlpath) throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		Bitmap bitmap = null;
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		return bitmap;
	}

	public static Bitmap getImage(String url) throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse res = client.execute(get);
		if (res.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = res.getEntity();
			InputStream is = entity.getContent();
			byte[] data = new byte[1024];
			int len = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while((len = is.read(data)) != -1){
				bos.write(data, 0, len);
			}
			byte[] imgData = bos.toByteArray();
			Bitmap bit = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
			return bit;
		}
		
		return null;
	}
	
	/**
	 * 获取图片文件方法的具体实
	 * 
	 * @param fName
	 * @return
	 */
	public static boolean getExtentionName(String fileName) {
		boolean re;

		/* 取得扩展*/
		String end = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length()).toLowerCase();

		/* 按扩展名的类型决定MimeType */
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	/**
	 * 将两个bitmap对象整合并保存图片
	 */
	public Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
		// 第一张图片的宽高
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		// 第二章图片的宽高
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		// 创建新的bitmao 高度等于两张高度的用来竖列拼接
		Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight + fgHeight,
				android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newmap);
		// 画上第一张图
		canvas.drawBitmap(background, 0, 0, null);
		// 从第图片的下边开始画入第二张图片
		canvas.drawBitmap(foreground, 0, bgHeight, null);
		return newmap;
	}

	/**
	 * Bitmap旋转角度
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				return b2;// 正常情况下返回旋转角度的
			} catch (OutOfMemoryError ex) {
				return b;// 内存溢出返回原图
			} finally {
				b.recycle();// 释放资源
			}
		}
		return b;
	}

	/**
	 * Bitmap画一个圆角图
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null) {
			return bitmap;
		}
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		} catch (OutOfMemoryError e) {
			System.gc();
			return null;
		}
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 添加水印
	 * 
	 * @param src
	 * @param watermark
	 * @param title
	 * @return
	 */
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
			String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// �?��处理图片太大造成的内存超过的问题,这里我的图片很小�?��不写相应代码�?
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建�?��新的和SRC长度宽度�?��的位�?
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// �?0�?坐标�?��画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(50);
			cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
		}
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// 这里是自动换行的
			StaticLayout layout = new StaticLayout(title, textPaint, w,
					Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			layout.draw(cv);
			// 文字就加左上角算�?
			// cv.drawText(title,0,40,paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 等比例压缩图�?
	 * 
	 * @param bitmap
	 * @param screenWidth
	 * @param screenHight
	 * @return
	 */
	public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
			int screenHight) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		float scale = (float) screenWidth / w;
		float scale2 = (float) screenHight / h;

		// 取比例小的�? 可以把图片完全缩放在屏幕�?
		scale = scale < scale2 ? scale : scale2;

		// 都按照宽度scale 保证图片不变�?根据宽度来确定高�?
		matrix.postScale(scale, scale);
		// w,h是原图的属�?.
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	/**
	 * 以最省内存的方式读取本地资源的图�?
	 * 
	 * @param context
	 * @param drawableId
	 * @param screenWidth
	 * @param screenHight
	 * @return
	 */
	public static Bitmap ReadBitmapById(Context context, int drawableId,
			int screenWidth, int screenHight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		options.inInputShareable = true;
		options.inPurgeable = true;
		InputStream stream = context.getResources().openRawResource(drawableId);
		Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
		return getBitmap(bitmap, screenWidth, screenHight);
	}

	public static Bitmap getThumBitmapFromFile(String imageFile) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true; // 只计算尺寸，不output
		BitmapFactory.decodeFile(imageFile, opts); // 这步的decodeFile只是为了得到opts的原始尺�?
		// opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
		// //计算合�?的输出尺寸（第三个参数是�?��能接受的像素值）
		opts.inSampleSize = 2;
		opts.inJustDecodeBounds = false; // output
		Bitmap bmp = BitmapFactory.decodeFile(imageFile, opts); // 这步decodeFile才是真的output
		return bmp;
	}
	public static Bitmap comp(Bitmap image) {  
	      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 40, baos);  
	    if( baos.toByteArray().length / 1024>40) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
	        baos.reset();  
	        image.compress(Bitmap.CompressFormat.JPEG, 40, baos);//这里压缩50%，把压缩后的数据存放到baos�? 
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    //�?��读入图片，此时把options.inJustDecodeBounds 设回true�? 
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    //现在主流手机比较多是800*480分辨率，�?��高和宽我们设置为  
	    float hh = 1280f;//这里设置高度�?00f  
	    float ww = 720f;//这里设置宽度�?80f  
	    //缩放比�?由于是固定比例缩放，只用高或者宽其中�?��数据进行计算即可  
	    int be = 1;//be=1表示不缩�? 
	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩�? 
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩�? 
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	    newOpts.inSampleSize = be;//设置缩放比例  
	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�? 
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    return compressImage(bitmap);//压缩好比例大小后再进行质量压�? 
	}  
	public static Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 40, baos);//质量压缩方法，这�?00表示不压缩，把压缩后的数据存放到baos�? 
        int options = 60;  
        while ( baos.toByteArray().length / 1024>40) {  //循环判断如果压缩后图片是否大�?00kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos�? 
            options -= 10;//每次都减�?0  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream�? 
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
	/**
	 * bitmap 转化为流
	 * 
	 * @param image
	 */
	public static byte[] getBitmapToFile(ImageView image){
		Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
    	bitmap.compress(CompressFormat.PNG, 100,os);
    	return os.toByteArray();
	}
	 /**
     * (缩放)重绘图片
     * 
     * @param context
     *            Activity
     * @param bitmap
     * @return
     */
    public static Bitmap reDrawBitMap(Activity context, Bitmap bitmap) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int rHeight = dm.heightPixels;
        int rWidth = dm.widthPixels;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        float zoomScale;
        /** 方式1 **/
        // if(rWidth/rHeight>width/height){//以高为准
        // zoomScale=((float) rHeight) / height;
        // }else{
        // //if(rWidth/rHeight<width height)="" 以宽为准="" zoomscale="((float)" rwidth)="" width;="" }="" **="" 方式2="" if(width*1.5="">= height) {//以宽为准
        // if(width >= rWidth)
        // zoomScale = ((float) rWidth) / width;
        // else
        // zoomScale = 1.0f;
        // }else {//以高为准
        // if(height >= rHeight)
        // zoomScale = ((float) rHeight) / height;
        // else
        // zoomScale = 1.0f;
        // }
        /** 方式3 **/
        if (width >= rWidth)
            zoomScale = ((float) rWidth) / width;
        else
            zoomScale = 1.0f;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(zoomScale, zoomScale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
 
}
