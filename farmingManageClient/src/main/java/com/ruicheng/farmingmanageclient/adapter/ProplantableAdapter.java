package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.UpdateCostequationAdapter.ViewHolder;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;

public class ProplantableAdapter extends BaseAdapter {
	private List<CustomCostListInfo> customCostListInfoList;
	private Context context ;
	
	public ProplantableAdapter(List<CustomCostListInfo> customCostListInfoList,Context context){
		this.customCostListInfoList = customCostListInfoList;
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customCostListInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customCostListInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

ViewHolder viewHolder ;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_updatecostequation, null);
			viewHolder.tv_costName = (TextView) convertView.findViewById(R.id.tv_costName);
			viewHolder.et_minValue = (TextView) convertView.findViewById(R.id.et_minValue);
			viewHolder.et_maxValue = (TextView) convertView.findViewById(R.id.et_maxValue);
			viewHolder.tv_CuName = (TextView) convertView.findViewById(R.id.tv_CuName);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		CustomCostListInfo customCostListInfo = customCostListInfoList.get(position);
		
		viewHolder.tv_costName.setText(customCostListInfo.getCostName()+":");
		viewHolder.et_minValue.setText(customCostListInfo.getMinValue());
		viewHolder.et_maxValue.setText(customCostListInfo.getMaxValue());
		viewHolder.tv_CuName.setText(customCostListInfo.getCuName());
		
		/*for (int i = 0; i <chargeListInfoList.size(); i++) {
			if (!"".equals(chargeListInfoList.get(i).getCuCode())&&!"null".equals(chargeListInfoList.get(i).getCuCode())&&!"".equals(customCostListInfo.getCuCode())&&!"null".equals(customCostListInfo.getCuCode())) {
				if (chargeListInfoList.get(i).getCuCode().equals(customCostListInfo.getCuCode())) {
				}
			}
		}*/
		
		
		
		
		return convertView;
	}
	public class ViewHolder{
		
		private TextView tv_costName ;
		private TextView et_minValue ;
		private TextView et_maxValue ;
		private TextView tv_CuName ;
	}

}
