package com.ruicheng.farmingmanageclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruicheng.farmingmanageclient.CostequationActivity;
import com.ruicheng.farmingmanageclient.ProplanActivity;
import com.ruicheng.farmingmanageclient.ProplantableActivity;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;


/**
 * 生产计划界面
 *
 * @author zhaobeibei
 *
 */
public class ProductionPlanFragment extends Fragment{

	private ImageView iv_costequation,//成本公式
			iv_proplan,//生产计划
			iv_proplantable ;//生产计划统计
	private View view ;
	private ImageView iv_costequation_plant ,iv_proplan_plant,iv_proplantable_plant;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_productionplan, null) ;
		init();
		setListener();


		return view;
	}
	public void init(){
		iv_costequation = (ImageView) view.findViewById(R.id.iv_costequation);
		iv_proplan = (ImageView) view.findViewById(R.id.iv_proplan);
		iv_proplantable = (ImageView) view.findViewById(R.id.iv_proplantable);

		iv_costequation_plant = (ImageView) view.findViewById(R.id.iv_costequation_plant);
		iv_proplan_plant = (ImageView) view.findViewById(R.id.iv_proplan_plant);
		iv_proplantable_plant = (ImageView) view.findViewById(R.id.iv_proplantable_plant);

	}
	public void setListener(){
		MainClick mainClick = new MainClick();
		iv_costequation.setOnClickListener(mainClick);
		iv_proplan.setOnClickListener(mainClick);
		iv_proplantable.setOnClickListener(mainClick);

		iv_costequation_plant.setOnClickListener(mainClick);
		iv_proplan_plant.setOnClickListener(mainClick);
		iv_proplantable_plant.setOnClickListener(mainClick);

	}
	public class MainClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent i = new Intent();
			switch (v.getId()) {
				case R.id.iv_costequation:
					i.setClass(getActivity(), CostequationActivity.class);
					i.putExtra("TYPE",1); // 1 加工类型  0 种植类型
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 1);
					break;
				case R.id.iv_proplan:
					i.setClass(getActivity(), ProplanActivity.class);
					i.putExtra("TYPE", 1);
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 1);
					break;
				case R.id.iv_proplantable:
					i.setClass(getActivity(), ProplantableActivity.class);
					i.putExtra("TYPE", 1);
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 1);
					break;
				case R.id.iv_costequation_plant:
					i.setClass(getActivity(), CostequationActivity.class);
					i.putExtra("TYPE",0);
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 0);
					break;
				case R.id.iv_proplan_plant:
					i.setClass(getActivity(), ProplanActivity.class);
					i.putExtra("TYPE",0);
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 0);
					break;
				case R.id.iv_proplantable_plant:
					i.setClass(getActivity(), ProplantableActivity.class);
					i.putExtra("TYPE",0);
					PreferencesUtils.remove(getActivity(), Constant.TYPE);
					PreferencesUtils.putInt(getActivity(), Constant.TYPE, 0);
					break;
				default:
					break;
			}
			startActivity(i);
			getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
	}
}
