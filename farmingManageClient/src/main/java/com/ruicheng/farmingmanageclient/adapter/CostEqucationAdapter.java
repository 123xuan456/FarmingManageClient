package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.CostEquationData;

public class CostEqucationAdapter extends BaseAdapter {

	public Context context ;
	private List<Object> listAll ;
	
	public CostEqucationAdapter(Context context,List<Object> listAll){
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
			convertView =LayoutInflater.from(context).inflate(R.layout.listivew_query_costequation_item, null);
			viewHolder.farmProductsName = (TextView) convertView.findViewById(R.id.tv_farmproductsname);
			viewHolder.equationType = (TextView) convertView.findViewById(R.id.tv_equationtype);
			viewHolder.isValid = (TextView) convertView.findViewById(R.id.tv_isvalid);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CostEquationData costEquationData = (CostEquationData) listAll.get(position);
		viewHolder.farmProductsName.setText(costEquationData.getFarmProductsName());
		viewHolder.equationType.setText(costEquationData.getEquationType());
		viewHolder.isValid.setText(costEquationData.getIsValid());
		
		return convertView;
	}
	public class ViewHolder{
		private TextView farmProductsName ;
		private TextView equationType ;
		private TextView isValid ;
	}
}
