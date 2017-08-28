package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.StatisticsCropInfo;

public class ProReStatisticsAdapter extends BaseAdapter {

	private Context context ;
	private List<Object> listAll ;
	public ProReStatisticsAdapter(Context context,List<Object> listAll){
		this.context = context ;
		this.listAll = listAll;
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
	ViewHolder viewHolder ;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listivew_prorestatistics_item, null);
			viewHolder.tv_item_cropName = (TextView) convertView.findViewById(R.id.tv_item_cropName);
			viewHolder.tv_item_cultivatedArea = (TextView) convertView.findViewById(R.id.tv_item_cultivatedArea);
			viewHolder.tv_item_harvestYield = (TextView) convertView.findViewById(R.id.tv_item_harvestYield);
		
			convertView.setTag(viewHolder);
		
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		StatisticsCropInfo proReStatisticsData = (StatisticsCropInfo) listAll.get(position);
		if (!"".equals(proReStatisticsData.getCrop_type())&&!"null".equals(proReStatisticsData.getCrop_type())) {
			viewHolder.tv_item_cropName.setText(proReStatisticsData.getCrop_type());
		}else{
			viewHolder.tv_item_cropName.setText("");
		}
		if (!"".equals(proReStatisticsData.getPlougharea())&&!"null".equals(proReStatisticsData.getPlougharea())) {
			viewHolder.tv_item_cultivatedArea.setText(proReStatisticsData.getPlougharea());
			
		} else {
			viewHolder.tv_item_cultivatedArea.setText("");
		}
		if (!"".equals(proReStatisticsData.getReceiveweight())&&!"null".equals(proReStatisticsData.getReceiveweight())) {
			viewHolder.tv_item_harvestYield.setText(proReStatisticsData.getReceiveweight());
		} else {
			viewHolder.tv_item_harvestYield.setText("");
		}
		return convertView;
	}
	public class ViewHolder {
		private TextView tv_item_cropName ;
		private TextView tv_item_cultivatedArea ;
		private TextView tv_item_harvestYield ;
	}
}
