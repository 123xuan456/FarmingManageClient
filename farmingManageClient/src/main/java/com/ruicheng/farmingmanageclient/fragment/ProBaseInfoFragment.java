package com.ruicheng.farmingmanageclient.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.ServiceManageAc;
import com.ruicheng.farmingmanageclient.TianYangManageAc;
/**
 *
 * 基础信息界面
 *
 * @author zhaobeibei
 *
 */

public class ProBaseInfoFragment extends Fragment {

	private View view ;
	private ImageView tv_servicemanage,tv_tianyangmanage ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_probaseinfo, null);

		init();
		setListener();
		return view;



	}

	private void setListener() {
		// 跳转界面监听
		IntentViewListener intentViewListener = new IntentViewListener();
		tv_servicemanage.setOnClickListener(intentViewListener);
		tv_tianyangmanage.setOnClickListener(intentViewListener);
	}

	public void init() {
		tv_servicemanage = (ImageView) view.findViewById(R.id.tv_servicemanage);
		tv_tianyangmanage = (ImageView) view.findViewById(R.id.tv_tianyangmanage);

	}
	public class IntentViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent i = new Intent();
			switch (v.getId()) {
				case R.id.tv_servicemanage:
					i.setClass(getActivity(), ServiceManageAc.class);
					break;
				case R.id.tv_tianyangmanage:
					i.setClass(getActivity(), TianYangManageAc.class);
					break;

				default:
					break;
			}
			startActivity(i);
			getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
	}
}
