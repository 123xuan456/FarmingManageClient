package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.PloughListInfoArrayInfo;

public class BaseInfoTiayagManageAdapter extends BaseAdapter {

	private List<Object> listAll;
	private Context context;

	public BaseInfoTiayagManageAdapter(List<Object> listAll, Context context) {
		this.listAll = listAll;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listAll.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listAll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder viewHolder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listivew_querybaseinfo_item, null);
			viewHolder.tv_item_fieldNumber = (TextView) convertView
					.findViewById(R.id.tv_item_fieldNumber);
			viewHolder.belongsToServiceStation = (TextView) convertView
					.findViewById(R.id.tv_item_belongsToServiceStation);
			viewHolder.plantingOfCrops = (TextView) convertView
					.findViewById(R.id.tv_item_plantingOfCrops);
			viewHolder.fieldArea = (TextView) convertView
					.findViewById(R.id.tv_item_fieldArea);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		PloughListInfoArrayInfo baseInfoTiayagManageData = (PloughListInfoArrayInfo) listAll
				.get(position);
		if (!"".equals(baseInfoTiayagManageData.getPloughCode())
				&& !"null".equals(baseInfoTiayagManageData.getPloughCode())) {
			viewHolder.tv_item_fieldNumber.setText(baseInfoTiayagManageData
					.getPloughCode());
		} else {
			viewHolder.tv_item_fieldNumber.setText("");
		}
		if (!"".equals(baseInfoTiayagManageData
					.getStation())&&!"null".equals(baseInfoTiayagManageData
					.getStation())) {
			viewHolder.belongsToServiceStation.setText(baseInfoTiayagManageData
					.getStation());

		} else {
			viewHolder.belongsToServiceStation.setText("");
		}
		if (!"".equals(baseInfoTiayagManageData
				.getPlantCrop())&&!"null".equals(baseInfoTiayagManageData
				.getPlantCrop())) {
			viewHolder.plantingOfCrops.setText(baseInfoTiayagManageData
					.getPlantCrop());
		} else {
			viewHolder.plantingOfCrops.setText("");
		}
		if (!"".equals(baseInfoTiayagManageData.getPloughArea())&&!"null".equals(baseInfoTiayagManageData.getPloughArea())) {
			viewHolder.fieldArea.setText(baseInfoTiayagManageData.getPloughArea());
		} else {
			viewHolder.fieldArea.setText("");
		}


		return convertView;
	}

	class ViewHolder {
		TextView tv_item_fieldNumber;
		TextView belongsToServiceStation;
		TextView plantingOfCrops;
		TextView fieldArea;

	}
}
