package com.ruicheng.farmingmanageclient.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

/**
 * 网络连接工具类
 *
 * @author ZhangZhaoCheng
 */
public class NetUtils {

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

	/**
	 * 检查wifi是否可用
	 *
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnection(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 检查mobile是否可用
	 *
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 检查是否有网络
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkNetConnection(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivity.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.isConnected()) {
			if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}
	public static void CancleDialog(final Context context,final Dialog dialog){
		boolean net = NetUtils.checkNetConnection(context);
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.arg1 ==0) {
					ToastUtils.show(context, "网络不可用");
					dialog.dismiss();
				}
			}
		};
		if (!net) {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			}, 3000);
		}
	}
}

