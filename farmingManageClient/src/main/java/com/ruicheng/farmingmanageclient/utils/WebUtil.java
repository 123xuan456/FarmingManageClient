package com.ruicheng.farmingmanageclient.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WebUtil {
	public static HttpResponse httpPostSend(String action,
											List<NameValuePair> parameters) throws Exception {
		HttpPost httpPost = new HttpPost("http://www." + action);
		httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpClient httpClient = new DefaultHttpClient();
		// 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		// 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
		return httpClient.execute(httpPost);
	}

	/**
	 * 获取网络图片的数据
	 * @param path  网络图片路径
	 * @return
	 */
	public static Bitmap getImages(String path) throws Exception {

		Bitmap bitmap = null;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 基于HTTP协议连接对象
		conn.setConnectTimeout(30000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inStream = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(inStream);
		}
		return bitmap;
	}

	/**
	 * 获取网络图片的数据
	 *
	 * @param path
	 *            网络图片路径,如果网络中没有图片，默认显示本地一个图片
	 * @return
	 */
	public static Bitmap getImage(String path,Context context) throws Exception {

		Bitmap bitmap = null;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 基于HTTP协议连接对象
		conn.setConnectTimeout(30000);
		File file=new File(context.getCacheDir(), path.substring(path.lastIndexOf("/")+1));
		if (file.exists()) {
			Date date = new Date(file.lastModified());			// 获取最后修改时间, 创建日期对象
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);	// 指定日期的格式和语言
			format.setTimeZone(TimeZone.getTimeZone("GMT"));	// 设置时区
			conn.setRequestProperty("If-Modified-Since", format.format(date));	// 将图片最后修改时间写到服务端
		}
		int rcode=conn.getResponseCode();
		if ( rcode== 200) {
			byte[] data = load(conn.getInputStream());
			writeCache(file, data);
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		}else if(rcode==404){
			ApplicationInfo appInfo=context.getApplicationInfo();
			int resID = context.getResources().getIdentifier("zwtp", "drawable", appInfo.packageName);
			bitmap=BitmapFactory.decodeResource(context.getResources(), resID);

		}else if (rcode==304) {
//			Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
//			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
			bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
		}{

		}
		return bitmap;
	}
	public static void writeCache(File file, byte[] data) throws IOException{
		FileOutputStream fos =null;
		try {
			fos= new FileOutputStream(file);
			fos.write(data);
		} catch (Exception e) {
		}finally{
			fos.close();
		}


	}
	public static byte[] load(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1)
			baos.write(buffer, 0, len);			// 将服务端写出的数据存到内存中
		baos.close();
		return baos.toByteArray();
	}


	/**
	 * 获取服务器端xml文件信息
	 *
	 * @param path
	 *            服务器端url地址
	 * @return
	 */
	public static InputStream getInputStream(String path) throws Exception {

		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 基于HTTP协议连接对象
		conn.setConnectTimeout(30000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inStream = conn.getInputStream();
			return inStream;
		}
		return null;
	}
	/**
	 * 获得网络响应
	 * @param uri
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static HttpResponse getHttpResponse(String uri){
		HttpGet get=new HttpGet(uri);
		HttpClient client=new DefaultHttpClient();
		HttpResponse res = null;
		try {
			res = client.execute(get);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
