package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.CnameAdapter.ViewHolder;
import com.ruicheng.farmingmanageclient.bean.CnameInfo;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CropPtypeNameAdapter extends BaseAdapter {
	private List<CropTypeNameInfo> listAll ;
	private Context context ;
	public CropPtypeNameAdapter(List<CropTypeNameInfo> listAll,Context context){
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

	ViewHolder viewHolder ;
	
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
		CropTypeNameInfo stationInfo= (CropTypeNameInfo) listAll.get(position);
		viewHolder.tv_dc.setText(stationInfo.getCropPtype());
		return convertView;
	}
	public class ViewHolder{
		
		private TextView tv_dc ;
	}
}
