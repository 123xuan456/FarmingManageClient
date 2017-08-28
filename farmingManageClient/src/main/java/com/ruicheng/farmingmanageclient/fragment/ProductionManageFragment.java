package com.ruicheng.farmingmanageclient.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.CropSellActivity;
import com.ruicheng.farmingmanageclient.ProductionRecordStatisticsActivity;
import com.ruicheng.farmingmanageclient.PromanageListActivity;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;

import java.util.List;

/**
 * 生产管理界面
 *
 * @author zhaobeibei
 *
 */
public class ProductionManageFragment extends Fragment implements
		OnClickListener {

	private ImageView tv_promanage_seed, tv_promanage_agricultrueproject,
			tv_promanage_harvest, tv_promanage_zuowujiaoshou,
			tv_promanage_lvlitongji;
	private View view;
	private Dialog loadingDialog ;
	private List<Object> listAll ;
	//创建view，可以直接在onCreateView中获取view中的控件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		//LayoutInflater作用是将layout的xml布局文件实例化为View类对象
		view = inflater.inflate(R.layout.fragment_productionmanage, null);

		initView();
		setLisener();

		return view;
	}

	private void setLisener() {
		tv_promanage_seed.setOnClickListener(this);
		tv_promanage_agricultrueproject.setOnClickListener(this);
		tv_promanage_harvest.setOnClickListener(this);
		tv_promanage_zuowujiaoshou.setOnClickListener(this);
		tv_promanage_lvlitongji.setOnClickListener(this);
	}

	private void initView() {

		tv_promanage_seed = (ImageView) view
				.findViewById(R.id.tv_promanage_seed);
		tv_promanage_agricultrueproject = (ImageView) view
				.findViewById(R.id.tv_promanage_agricultrueproject);
		tv_promanage_harvest = (ImageView) view
				.findViewById(R.id.tv_promanage_harvest);
		tv_promanage_zuowujiaoshou = (ImageView) view
				.findViewById(R.id.tv_promanage_zuowujiaoshou);
		tv_promanage_lvlitongji = (ImageView) view
				.findViewById(R.id.tv_promanage_lvlitongji);
		loadingDialog = DialogUtils.requestDialog(getActivity());
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		switch (v.getId()) {
			case R.id.tv_promanage_seed:
				intent.setClass(getActivity(), PromanageListActivity.class);
				bundle.putInt("kind",0);
				bundle.putInt("optionType", 1);
				PreferencesUtils.remove(getActivity(),Constant.OPTIONTYPE);
				PreferencesUtils.putInt(getActivity(),Constant.OPTIONTYPE,1);
				break;
			case R.id.tv_promanage_agricultrueproject:
				intent.setClass(getActivity(), PromanageListActivity.class);
				bundle.putInt("kind",1);
				bundle.putInt("optionType", 2);
				PreferencesUtils.remove(getActivity(),Constant.OPTIONTYPE);
				PreferencesUtils.putInt(getActivity(),Constant.OPTIONTYPE,2);
				break;
			case R.id.tv_promanage_harvest:
				intent.setClass(getActivity(), PromanageListActivity.class);
				bundle.putInt("kind", 2);
				bundle.putInt("optionType", 5);
				PreferencesUtils.remove(getActivity(),Constant.OPTIONTYPE);
				PreferencesUtils.putInt(getActivity(),Constant.OPTIONTYPE,5);
				break;
			case R.id.tv_promanage_zuowujiaoshou:
				intent.setClass(getActivity(), CropSellActivity.class);
				bundle.putInt("kind", 3);
				break;
			case R.id.tv_promanage_lvlitongji:
				intent.setClass(getActivity(), ProductionRecordStatisticsActivity.class);
				bundle.putInt("kind", 4);
				break;

			default:
				break;
		}
		intent.putExtras(bundle);
		startActivity(intent);

		// 实现zoommin 和 zoomout (自定义的动画)

		getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
	}
}
