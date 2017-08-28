package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.DcAdapter.ViewHolder;
import com.ruicheng.farmingmanageclient.bean.PloughListDetailInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.PurchaseOrderInfo;
import com.ruicheng.farmingmanageclient.bean.StationInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PloughDetailAdapter extends BaseAdapter {

	private List<Object> listAll ;
	private Context context ;
	
	public PloughDetailAdapter(List<Object> listAll,Context context){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_dc_item, null);
			viewHolder.tv_dc = (TextView) convertView.findViewById(R.id.tv_dc);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		PloughListDetailInfo stationInfo= (PloughListDetailInfo) listAll.get(position);
		viewHolder.tv_dc.setText(stationInfo.getPloughCode());
		return convertView;
	}
	public class ViewHolder{
		
		private TextView tv_dc ;
	}
}
