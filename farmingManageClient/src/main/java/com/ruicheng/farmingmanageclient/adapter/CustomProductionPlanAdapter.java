package com.ruicheng.farmingmanageclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;

import java.util.List;
/**
 * 自定义费用适配器
 *
 * @author zhaobeibei
 *
 */
public class CustomProductionPlanAdapter extends BaseAdapter {

	private Context context ;
	private List<CustomCostListInfo> listAll ;
	public CustomProductionPlanAdapter(Context context,List<CustomCostListInfo> listAll){
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

		CustomCostListInfo customCostListInfo = listAll.get(position);
		viewHolder.tv_costName.setText(customCostListInfo.getCostName()+":");
		viewHolder.et_minValue.setText(customCostListInfo.getMinValue());
		viewHolder.et_maxValue.setText(customCostListInfo.getMaxValue());
		viewHolder.tv_CuName.setText(customCostListInfo.getCuName());
		return convertView;
	}
	public class ViewHolder{

		private TextView tv_costName ;
		private TextView et_minValue ;
		private TextView et_maxValue ;
		private TextView tv_CuName ;
	}
}
