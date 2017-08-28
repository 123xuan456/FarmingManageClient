package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;

public class DistrictAdapter extends BaseAdapter {
	public List<Object> listAll ;
	public Context context ;
	public DistrictAdapter(List<Object> listAll,Context context){
		this.context = context;
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

	public ViewHolder viewHolder ;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_dc_item, null);
			viewHolder.tv_dc = (TextView) convertView.findViewById(R.id.tv_dc);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		AcquireAllDcInfo stationInfo= (AcquireAllDcInfo) listAll.get(position);
		viewHolder.tv_dc.setText(stationInfo.getDcName());
		return convertView;
	}
	public class ViewHolder{
		
		public TextView tv_dc ;
	}
}
