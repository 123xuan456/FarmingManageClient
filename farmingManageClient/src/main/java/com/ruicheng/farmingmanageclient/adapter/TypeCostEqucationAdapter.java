package com.ruicheng.farmingmanageclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.PlanFromulaListInfo;

import java.util.List;

public class TypeCostEqucationAdapter extends BaseAdapter {
	public Context context ;
	public List<Object> listAll ;

	public TypeCostEqucationAdapter(Context context,List<Object> listAll){
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
			convertView =LayoutInflater.from(context).inflate(R.layout.listivew_query_costequation_item, null);
			viewHolder.farmProductsName = (TextView) convertView.findViewById(R.id.tv_farmproductsname);
			viewHolder.equationType = (TextView) convertView.findViewById(R.id.tv_equationtype);
			viewHolder.isValid = (TextView) convertView.findViewById(R.id.tv_isvalid);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		PlanFromulaListInfo typeCostEquationData = (PlanFromulaListInfo) listAll.get(position);
		viewHolder.farmProductsName.setText(typeCostEquationData.getAgriName());
		if ("1".equals(typeCostEquationData.getFormType())) {
			viewHolder.equationType.setText("加工类");
		} else if ("0".equals(typeCostEquationData.getFormType())) {
			viewHolder.equationType.setText("种植类");
		}
		if ("0".equals(typeCostEquationData.getIsValid())) {
			viewHolder.isValid.setText("有效");
		} else if ("1".equals(typeCostEquationData.getIsValid())){
			viewHolder.isValid.setText("无效");
		}else if ("".equals(typeCostEquationData.getIsValid())){
			viewHolder.isValid.setText("全部");
		}
		return convertView;
	}
	public class ViewHolder{
		public TextView farmProductsName ;
		public TextView equationType ;
		public TextView isValid ;
	}
}
