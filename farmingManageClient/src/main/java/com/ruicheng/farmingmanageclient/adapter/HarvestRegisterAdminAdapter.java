package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.QueryListViewBaseAdapter.ViewHolder;
import com.ruicheng.farmingmanageclient.bean.QueryData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HarvestRegisterAdminAdapter extends BaseAdapter {

	private List<Object> listAll ;
	private Context context ;
	public HarvestRegisterAdminAdapter(List<Object> listAll,Context context){
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
			convertView =LayoutInflater.from(context).inflate(R.layout.listivew_query_item, null);
			/*viewHolder.tv_item_operate = (TextView) convertView.findViewById(R.id.tv_item_operate);*/
			viewHolder.tv_item_operatedate = (TextView) convertView.findViewById(R.id.tv_item_operatedate);
			viewHolder.tv_item_type = (TextView) convertView.findViewById(R.id.tv_item_type);
			viewHolder.tv_item_describtion = (TextView) convertView.findViewById(R.id.tv_item_describtion);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		QueryData queryData = (QueryData) listAll.get(position);
		/*viewHolder.tv_item_operate.setText(queryData.getOperate());*/
		viewHolder.tv_item_operatedate.setText(queryData.getOperateDate());
		viewHolder.tv_item_type.setText(queryData.getType());
		viewHolder.tv_item_describtion.setText(queryData.getDescribtion());
		return convertView;
	}
	public class ViewHolder{
		/*public TextView tv_item_operate ;*/
		public TextView tv_item_operatedate ;
		public TextView tv_item_type ;
		public TextView tv_item_describtion ;
	}

}
