package com.ruicheng.farmingmanageclient.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
	private ToastUtils() {
		throw new AssertionError();
	}

	public static void show(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getText(resId), duration);
	}

	public static void show(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, CharSequence text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	public static void show(Context context, int resId, Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				Toast.LENGTH_SHORT);
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration,
							Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				duration);
	}

	public static void show(Context context, String format, int duration,
							Object... args) {
		show(context, String.format(format, args), duration);
	}

	/**
	 * 锟斤拷司锟斤拷锟叫硷拷
	 *
	 * @param context
	 * @param text
	 */
	public static void showCenter(Context context, CharSequence text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		TextView textView = (TextView) toastView.getChildAt(0);
		textView.setTextSize(22);
		toast.show();
	}

	public static void showCenter(Context context, int resId) {
		Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		TextView textView = (TextView) toastView.getChildAt(0);
		textView.setTextSize(22);
		toast.show();
	}


	public static void showAlertDialog(Context context) {
		/*new AlertDialog.Builder(context).setTitle("鎻愮ず锛?).setMessage("纭鏇存敼鎺掑悕鍚楋紵").
		setPositiveButton("纭畾",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		}).setNegativeButton("鍙栨秷",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			
			}
		}).show();*/
	}
}
