package com.ruicheng.farmingmanageclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.DataListInfo;

import java.util.List;

public class TianYangProListViewAdapter extends BaseAdapter {

	private List<Object> listAll  ;
	private Context context ;
	private int optionType ;

	public TianYangProListViewAdapter(List<Object> listAll,Context context,int optionType){
		this.context = context;
		this.listAll = listAll;
		this.optionType = optionType;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_project_item
					, null);
			viewHolder.tv_project_receiveDate = (TextView) convertView.findViewById(R.id.tv_project_receiveDate);
			viewHolder.tv_project_productType = (TextView) convertView.findViewById(R.id.tv_project_productType);
			viewHolder.tv_project_cropType = (TextView) convertView.findViewById(R.id.tv_project_cropType);
			viewHolder.tv_project_receiveDate1 = (TextView) convertView.findViewById(R.id.tv_project_receiveDate1);
			viewHolder.tv_project_moveDate = (TextView) convertView.findViewById(R.id.tv_project_moveDate);
			viewHolder.tv_project_actionPerson = (TextView) convertView.findViewById(R.id.tv_project_actionPerson);
			viewHolder.tv_project_seedDate = (TextView) convertView.findViewById(R.id.tv_project_seedDate);
			viewHolder.tv_project_actionBak = (TextView) convertView.findViewById(R.id.tv_project_actionBak);
			viewHolder.tv_item_agrDesc = (TextView) convertView.findViewById(R.id.tv_item_agrDesc);
			viewHolder.tv_item_preventObj = (TextView) convertView.findViewById(R.id.tv_item_preventObj);



			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		DataListInfo tianYangProData = (DataListInfo) listAll.get(position);
		viewHolder.tv_project_receiveDate.setText(tianYangProData.getReceiveDate());
		if (optionType ==1) {
			viewHolder.tv_project_productType.setText("播种移栽");

			viewHolder.tv_project_cropType.setText("作物名称:"+tianYangProData.getCropType());
			viewHolder.tv_project_receiveDate1.setText("实施日期:"+tianYangProData.getReceiveDate());
			viewHolder.tv_project_moveDate.setText("移栽日期:"+tianYangProData.getMoveDate());
			viewHolder.tv_project_actionPerson.setText("实施人员:"+tianYangProData.getActionPerson());
			viewHolder.tv_project_seedDate.setText("播种日期:"+tianYangProData.getSeedDate());
			viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
			if ("null".equals(tianYangProData.getActionBak())) {
				viewHolder.tv_item_agrDesc.setText("备注:"+"");
			} else {
				viewHolder.tv_item_agrDesc.setText("备注:"+tianYangProData.getActionBak());
			}
			viewHolder.tv_item_preventObj.setVisibility(View.GONE);

		} else if (optionType ==2){

			if ("2".equals(tianYangProData.getProductType())) {
				viewHolder.tv_project_productType.setText("日常农事");

				viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
				viewHolder.tv_project_seedDate.setText("气温:"+tianYangProData.getTemperValue());
				viewHolder.tv_item_preventObj.setText("农事项目:"+tianYangProData.getProductItem());
				viewHolder.tv_project_moveDate.setText("农资名称及用量:"+tianYangProData.getAgrDesc());
				viewHolder.tv_project_receiveDate1.setText("实施日期:"+tianYangProData.getReceiveDate());
				viewHolder.tv_project_actionPerson.setText("实施人员:"+tianYangProData.getActionPerson());
				if (!"null".equals(tianYangProData.getActionBak())) {
					viewHolder.tv_item_agrDesc.setText("备注:"+tianYangProData.getActionBak());
				} else {
					viewHolder.tv_item_agrDesc.setText("备注:"+"");
				}
				viewHolder.tv_project_cropType.setVisibility(View.GONE);
			} else if("3".equals(tianYangProData.getProductType())){
				viewHolder.tv_project_productType.setText("肥料使用");

				viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
				viewHolder.tv_project_seedDate.setText("肥料名称:"+tianYangProData.getManureName());
				viewHolder.tv_item_preventObj.setText("亩用量:"+tianYangProData.getManureAmountUnit());
				viewHolder.tv_project_moveDate.setText("用途:"+tianYangProData.getManureUse());
				viewHolder.tv_project_receiveDate1.setText("实施日期:"+tianYangProData.getReceiveDate());
				viewHolder.tv_project_actionPerson.setText("实施人员:"+tianYangProData.getActionPerson());
				if (!"null".equals(tianYangProData.getActionBak())) {
					viewHolder.tv_item_agrDesc.setText("备注:"+tianYangProData.getActionBak());
				} else {
					viewHolder.tv_item_agrDesc.setText("备注:"+"");
				}
				viewHolder.tv_project_cropType.setVisibility(View.GONE);

			}else if("4".equals(tianYangProData.getProductType())){
				viewHolder.tv_project_productType.setText("农药使用");
				/*
				public TextView tv_project_cropType ;
				public TextView tv_project_actionPerson ;*/
				viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
				viewHolder.tv_project_seedDate.setText("农药名称:"+tianYangProData.getPesticideName());
				viewHolder.tv_project_cropType.setText("亩用量:"+tianYangProData.getPesticideAmountUnit());
				viewHolder.tv_project_moveDate.setText("稀释倍数:"+tianYangProData.getDiluteScale());
				viewHolder.tv_item_preventObj.setText("防治对象:"+tianYangProData.getPreventObj());
				viewHolder.tv_project_receiveDate1.setText("实施日期:"+tianYangProData.getReceiveDate());
				viewHolder.tv_project_actionPerson.setText("实施人员:"+tianYangProData.getActionPerson());
				if (!"null".equals(tianYangProData.getActionBak())) {
					viewHolder.tv_item_agrDesc.setText("备注:"+tianYangProData.getActionBak());
				} else {
					viewHolder.tv_item_agrDesc.setText("备注:"+"");
				}

			}

		}else if (optionType ==5){
			viewHolder.tv_project_productType.setText("田洋收成");

		//	viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
			viewHolder.tv_project_seedDate.setText("作物名称:"+tianYangProData.getCropType());
			viewHolder.tv_project_receiveDate1.setText("收获日期:"+tianYangProData.getReceiveDate());
			viewHolder.tv_project_moveDate.setText("货品重量:"+tianYangProData.getReceiveWeight());
			viewHolder.tv_project_actionPerson.setText("货品等级:"+tianYangProData.getCropLevel());
			viewHolder.tv_project_cropType.setText("实施人员:"+tianYangProData.getActionPerson());
			viewHolder.tv_project_actionBak.setText("田洋编号:"+tianYangProData.getPloughCode());
			if (!"null".equals(tianYangProData.getActionBak())) {
				viewHolder.tv_item_agrDesc.setText("备注:"+tianYangProData.getActionBak());
			} else {
				viewHolder.tv_item_agrDesc.setText("备注:"+"");
			}
			viewHolder.tv_item_preventObj.setVisibility(View.GONE);

		}
		return convertView;
	}
	public class ViewHolder{

		public TextView tv_project_receiveDate ;
		public TextView tv_project_productType ;
		public TextView tv_project_cropType ;
		public TextView tv_project_receiveDate1 ;
		public TextView tv_project_moveDate ;
		public TextView tv_project_actionPerson ;
		public TextView tv_project_seedDate ;
		public TextView tv_project_actionBak ;
		public TextView tv_item_agrDesc;
		public TextView tv_item_preventObj;
	}
}
