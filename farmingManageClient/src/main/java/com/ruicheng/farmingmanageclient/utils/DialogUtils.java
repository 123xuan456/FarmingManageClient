package com.ruicheng.farmingmanageclient.utils;

import com.ruicheng.farmingmanageclient.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class DialogUtils {

	public static Dialog requestDialog(Context context) {
		Dialog dgloading;
		dgloading = null;
		dgloading = new Dialog(context, R.style.loadingDialog);
		LinearLayout layout = new LinearLayout(context);
		layout.setBackgroundColor(context.getResources().getColor(R.color.none_color));
		View view = LayoutInflater.from(context).inflate(R.layout.loading_bar,null);
		layout.addView(view);
		dgloading.setContentView(layout);
		dgloading.setCanceledOnTouchOutside(false);
		return dgloading;
	}

	/**
	 * ���������ݶԻ����ޱ��⣩
	 * @param context
	 * @return
	 */
	public static Dialog requestDialogWithNoTitle(Context context) {
		Dialog dgloading;
		dgloading = null;
		dgloading = new Dialog(context, R.style.loadingDialog);
		LinearLayout layout = new LinearLayout(context);
		layout.setBackgroundColor(context.getResources().getColor(
				R.color.none_color));
		View view = LayoutInflater.from(context).inflate(
				R.layout.loadingnotitle_bar, null);
		layout.addView(view);
		dgloading.setContentView(layout);
		dgloading.setCanceledOnTouchOutside(false);
		return dgloading;
	}
	public static Builder progressDialog(Context context,String tvTitle,String tvMessage){
		AlertDialog.Builder progressDialog = new Builder(context);
		progressDialog.setTitle(tvTitle);
		progressDialog.setMessage(tvMessage);
		progressDialog.setCancelable(false);
		return progressDialog;
	}
	
}
