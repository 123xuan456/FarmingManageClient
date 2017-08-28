package com.ruicheng.farmingmanageclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.bean.AgriRecordTianYnagInfo;

import java.util.List;
/**
 * 展示田洋农事记录适配器
 *
 * @author zhaobeibei
 *
 */
public class AgriRecordTianYnagAdapter extends BaseAdapter {

	private List<Object> listAll ;
	private Context context ;
	public AgriRecordTianYnagAdapter(List<Object> listAll,Context context){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_argidrecord, null);

			viewHolder.tv_project_receiveDate = (TextView)convertView.findViewById(R.id.tv_project_receiveDate);
			viewHolder.tv_project_productType =(TextView)convertView.findViewById(R.id.tv_project_productType);
			viewHolder.tv_project_cropType =(TextView) convertView.findViewById(R.id.tv_project_cropType);
			viewHolder.tv_project_seedDate = (TextView)convertView.findViewById(R.id.tv_project_seedDate);
			viewHolder.tv_project_moveDate = (TextView)convertView.findViewById(R.id.tv_project_moveDate);
			viewHolder.tv_project_receiveDate1 =(TextView)convertView.findViewById(R.id.tv_project_receiveDate1);
			viewHolder.tv_project_actionPerson =(TextView)convertView.findViewById(R.id.tv_project_actionPerson);
			viewHolder.tv_project_actionBak =(TextView)convertView.findViewById(R.id.tv_project_actionBak);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		AgriRecordTianYnagInfo agriRecordTianYnagInfo= (AgriRecordTianYnagInfo) listAll.get(position);
		viewHolder.tv_project_receiveDate.setText(agriRecordTianYnagInfo.getReceiveDate());
		viewHolder.tv_project_actionBak.setText("备注:"+agriRecordTianYnagInfo.getActionBak());
		if ("1".equals(agriRecordTianYnagInfo.getProductType())) {
			viewHolder.tv_project_productType.setText("播种移栽");
		} else if ("2".equals(agriRecordTianYnagInfo.getProductType())){
			viewHolder.tv_project_productType.setText("日常农事");
		}else if ("3".equals(agriRecordTianYnagInfo.getProductType())){
			viewHolder.tv_project_productType.setText("肥料使用");
		}else if ("4".equals(agriRecordTianYnagInfo.getProductType())){
			viewHolder.tv_project_productType.setText("农药使用");
		}else if ("5".equals(agriRecordTianYnagInfo.getProductType())){
			viewHolder.tv_project_productType.setText("田洋收获");
		}
		if ("1".equals(agriRecordTianYnagInfo.getProductType())) {
			if (!"".equals(agriRecordTianYnagInfo.getCropType())&&!"null".equals(agriRecordTianYnagInfo.getCropType())) {
				viewHolder.tv_project_cropType.setText("作物名称:"+agriRecordTianYnagInfo.getCropType());
			} else {
				viewHolder.tv_project_cropType.setText("作物名称:"+"<null>");
			}
			if (!"".equals(agriRecordTianYnagInfo.getSeedDate())&&!"null".equals(agriRecordTianYnagInfo.getSeedDate())) {
				viewHolder.tv_project_seedDate.setText("播种日期:"+agriRecordTianYnagInfo.getSeedDate());
			} else {
				viewHolder.tv_project_seedDate.setText("播种日期:"+"<null>");
			}
			if (!"".equals(agriRecordTianYnagInfo.getMoveDate())&&!"null".equals(agriRecordTianYnagInfo.getMoveDate())) {
				viewHolder.tv_project_moveDate.setText("移栽日期:"+agriRecordTianYnagInfo.getMoveDate());
			} else {
				viewHolder.tv_project_moveDate.setText("移栽日期:"+"<null>");
			}

			viewHolder.tv_project_receiveDate1.setText("实施日期:"+agriRecordTianYnagInfo.getReceiveDate());
			viewHolder.tv_project_actionPerson.setText("实施人员:"+agriRecordTianYnagInfo.getActionPerson());

		} else if ("2".equals(agriRecordTianYnagInfo.getProductType())){
//			viewHolder.tv_project_productType.setText("日常农事");
			if (!"null".equals(agriRecordTianYnagInfo.getTemperValue())&&!"".equals(agriRecordTianYnagInfo.getTemperValue())) {
				viewHolder.tv_project_cropType.setText("气温:"+agriRecordTianYnagInfo.getTemperValue());

			} else {
				viewHolder.tv_project_cropType.setText("气温:"+"");

			}
			if (!"null".equals(agriRecordTianYnagInfo.getProductItem())&&!"".equals(agriRecordTianYnagInfo.getProductItem())) {

				viewHolder.tv_project_seedDate.setText("农事项目:"+agriRecordTianYnagInfo.getProductItem());
			} else {
				viewHolder.tv_project_seedDate.setText("农事项目:"+"");

			}
			if (!"null".equals(agriRecordTianYnagInfo.getAgrDesc())&&!"".equals(agriRecordTianYnagInfo.getAgrDesc())) {
				viewHolder.tv_project_moveDate.setText(" 农资名称及用量:"+agriRecordTianYnagInfo.getAgrDesc());

			} else {
				viewHolder.tv_project_moveDate.setText(" 农资名称及用量:"+"");

			}
			viewHolder.tv_project_receiveDate1.setText("实施日期:"+agriRecordTianYnagInfo.getReceiveDate());
			viewHolder.tv_project_actionPerson.setText("实施人员:"+agriRecordTianYnagInfo.getActionPerson());


		}else if ("3".equals(agriRecordTianYnagInfo.getProductType())){
//			viewHolder.tv_project_productType.setText("肥料使用");
			if (!"null".equals(agriRecordTianYnagInfo.getManureName())&&!"".equals(agriRecordTianYnagInfo.getManureName())) {
				viewHolder.tv_project_cropType.setText("肥料名称:"+agriRecordTianYnagInfo.getManureName());

			} else {
				viewHolder.tv_project_cropType.setText("肥料名称:"+"");

			}
			if (!"null".equals(agriRecordTianYnagInfo.getManureAmountUnit())&&!"".equals(agriRecordTianYnagInfo.getManureAmountUnit())) {
				viewHolder.tv_project_seedDate.setText("亩用量:"+agriRecordTianYnagInfo.getManureAmountUnit());

			} else {
				viewHolder.tv_project_seedDate.setText("亩用量:"+"");
			}
			if (!"null".equals(agriRecordTianYnagInfo.getManureUse())&&!"".equals(agriRecordTianYnagInfo.getManureUse())) {

				viewHolder.tv_project_moveDate.setText("用途:"+agriRecordTianYnagInfo.getManureUse());
			} else {
				viewHolder.tv_project_moveDate.setText("用途:"+"");

			}
			viewHolder.tv_project_receiveDate1.setText("实施日期:"+agriRecordTianYnagInfo.getReceiveDate());
			viewHolder.tv_project_actionPerson.setText("实施人员:"+agriRecordTianYnagInfo.getActionPerson());
		}else if ("4".equals(agriRecordTianYnagInfo.getProductType())){
//			viewHolder.tv_project_productType.setText("农药使用");
			if (!"null".equals(agriRecordTianYnagInfo.getManureName())&&!"".equals(agriRecordTianYnagInfo.getManureName())) {
				viewHolder.tv_project_cropType.setText("农药名称:"+agriRecordTianYnagInfo.getManureName());

			} else {
				viewHolder.tv_project_cropType.setText("农药名称:"+"");

			}
			if (!"null".equals(agriRecordTianYnagInfo.getManureAmountUnit())) {
				viewHolder.tv_project_seedDate.setText("亩用量:"+agriRecordTianYnagInfo.getManureAmountUnit());

			} else {
				viewHolder.tv_project_seedDate.setText("亩用量:"+"");

			}
			if (!"null".equals(agriRecordTianYnagInfo.getManureUse())&&!"".equals(agriRecordTianYnagInfo.getManureUse())) {

				viewHolder.tv_project_moveDate.setText("用途:"+agriRecordTianYnagInfo.getManureUse());
			} else {
				viewHolder.tv_project_moveDate.setText("用途:"+"");

			}
			viewHolder.tv_project_actionPerson.setText("实施日期:"+agriRecordTianYnagInfo.getActionPerson());
			viewHolder.tv_project_receiveDate1.setText("实施人员:"+agriRecordTianYnagInfo.getActionPerson());
		}else if ("5".equals(agriRecordTianYnagInfo.getProductType())){
//			viewHolder.tv_project_productType.setText("田洋收获");
			viewHolder.tv_project_cropType.setText("作物名称:"+agriRecordTianYnagInfo.getManureName());
			viewHolder.tv_project_seedDate.setText("播种日期:"+agriRecordTianYnagInfo.getManureAmountUnit());
			viewHolder.tv_project_moveDate.setText("移栽日期:"+agriRecordTianYnagInfo.getManureUse());
			viewHolder.tv_project_actionPerson.setText("实施人员:"+agriRecordTianYnagInfo.getActionPerson());
			viewHolder.tv_project_receiveDate1.setVisibility(View.GONE);
		}

		return convertView;
	}
	public class ViewHolder{

		TextView tv_project_receiveDate ;
		TextView tv_project_productType ;
		TextView tv_project_cropType ;
		TextView tv_project_seedDate ;
		TextView tv_project_moveDate ;
		TextView tv_project_receiveDate1 ;
		TextView tv_project_actionPerson ;
		TextView tv_project_actionBak ;
	}
}
