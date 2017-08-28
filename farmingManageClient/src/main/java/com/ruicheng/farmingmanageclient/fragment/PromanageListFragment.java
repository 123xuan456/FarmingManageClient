package com.ruicheng.farmingmanageclient.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.QueryListViewBaseAdapter;
import com.ruicheng.farmingmanageclient.bean.QueryData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 显示滑动界面的子界面
 *
 * @author zhaobeibei
 *
 */
public class PromanageListFragment extends DialogFragment  implements OnClickListener,DatePickerDialog.OnDateSetListener{

	private View viewPromanageList;
	private int index;
	private int kind;
	private Bundle bundle;
	private TextView tv_promanagelista_title ;
	private Spinner spinner_promaangelist_table ;
	private static final String [] spinnerList = {"222","111","333"};

	private ArrayAdapter<String> spAdapter ;
	private Button btn_save ;
	private int state ;
	private LinearLayout layout_datagriculture,layout_fertilizeruse,layout_pesticideuse ;
	private TextView tv_selectseedtime,tv_transplantdatetime ;
	int _year=1970;
	int _month=0;
	int _day=0;
	private PullToRefreshListView listview_query ;
	private List<QueryData> queryDataList = new ArrayList<QueryData>();
	private QueryListViewBaseAdapter queryListViewBaseAdapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		bundle = getArguments();
		if (bundle != null) {
			index = bundle.getInt("index", -1);
			kind = bundle.getInt("kind", -1);
			state = bundle.getInt("state", -1);
			if (kind ==0&&index==0) {
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelista,
						null);
				initView();
				setListener();
				spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerList);
				spinner_promaangelist_table.setAdapter(spAdapter);
			} else if(kind ==0&&index==1){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelistb,
						null);
				initView();
				setListener();
				setListData();
			}else if(kind ==1&&index==0){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelistc,
						null);
				initView();
				setListener();
			}else if(kind ==1&&index==1){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelistd,
						null);
				initView();
				setListener();
			}else if(kind ==2&&index==0){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanageliste,
						null);
				initView();
				setListener();
			}else if(kind ==2&&index==1){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelistf,
						null);
				initView();
				setListener();
			}else if(kind ==3&&index==0){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelistg,
						null);
				initView();
				setListener();
			}else if(kind ==3&&index==1){
				viewPromanageList = inflater.inflate(R.layout.fragment_promanagelisth,
						null);
				initView();
				setListener();
			}
		}
		return viewPromanageList;
	}

	private void setListener() {
		if (kind ==0&&index==0) {
			/*btn_save.setOnClickListener(this);*/
			tv_selectseedtime.setOnClickListener(this);
			tv_transplantdatetime.setOnClickListener(this);
		} else if(kind ==0&&index==1){

		}else if(kind ==1&&index==0){
			LayoutDatagriculture layoutDatagriculture = new LayoutDatagriculture();
			layout_datagriculture.setOnClickListener(layoutDatagriculture);
			layout_fertilizeruse.setOnClickListener(layoutDatagriculture);
			layout_pesticideuse.setOnClickListener(layoutDatagriculture);

		}else if(kind ==1&&index==1){

		}else if(kind ==2&&index==0){

		}else if(kind ==2&&index==1){

		}else if(kind ==3&&index==0){

		}else if(kind ==3&&index==1){

		}
	}

	private void initView() {
		if (kind ==0&&index==0) {
			spinner_promaangelist_table = (Spinner) viewPromanageList.findViewById(R.id.spinner_promaangelist_table);
			tv_selectseedtime = (TextView) viewPromanageList.findViewById(R.id.tv_selectseedtime);
			tv_transplantdatetime = (TextView) viewPromanageList.findViewById(R.id.tv_transplantdatetime);
		} else if(kind ==0&&index==1){
			listview_query = (PullToRefreshListView) viewPromanageList.findViewById(R.id.listview_query);

		}else if(kind ==1&&index==0){
			layout_datagriculture = (LinearLayout) viewPromanageList.findViewById(R.id.layout_datagriculture);
			layout_fertilizeruse = (LinearLayout) viewPromanageList.findViewById(R.id.layout_fertilizeruse);
			layout_pesticideuse = (LinearLayout) viewPromanageList.findViewById(R.id.layout_pesticideuse);
		}else if(kind ==1&&index==1){

		}else if(kind ==2&&index==0){

		}else if(kind ==2&&index==1){

		}else if(kind ==3&&index==0){

		}else if(kind ==3&&index==1){

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_save:
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.tv_selectseedtime:
		/*handler.sendEmptyMessage(0);*/
				showDatePickerFragemnt();
				break;
			case R.id.tv_transplantdatetime:
		/*handler.sendEmptyMessage(0);*/
				showDatePickerFragemnt();
				break;
			default:
				break;
		}
	}
	private class LayoutDatagriculture implements OnClickListener{

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(),TianYangAgricultureActiivity.class);
			switch (v.getId()) {
				case R.id.layout_datagriculture:
					layout_datagriculture.setBackgroundColor(Color.GRAY);
					break;
				case R.id.layout_fertilizeruse:
					layout_datagriculture.setBackgroundColor(Color.GRAY);
					break;
				case R.id.layout_pesticideuse:
					layout_datagriculture.setBackgroundColor(Color.GRAY);
					break;
			}
//			startActivity(intent);
		}
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
		int day=c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
						  int dayOfMonth) {
		_year=year;
		_month=monthOfYear+1;
		_day=dayOfMonth;
		Log.i("TAG", "year="+year+",monthOfYear="+monthOfYear+",dayOfMonth="+dayOfMonth);
	}
	public String getValue(){
		return ""+_year+_month+_day;
	}
	public void showDatePickerFragemnt(){
		PromanageListFragment fragment=new PromanageListFragment();
		fragment.show(getChildFragmentManager(), "datePicker");

	}
	/*绑定查询listview的数据*/
	public void setListData(){
		for (int i = 0; i < 10; i++) {
			QueryData queryData = new QueryData();
			queryData.setOperate("种树"+i);
			queryData.setOperateDate("2015-08-"+i);
			queryData.setType("类型"+i);
			queryData.setDescribtion("种树好"+i);
			queryDataList.add(queryData);
		}
		queryListViewBaseAdapter = new QueryListViewBaseAdapter(getActivity(), queryDataList);
		listview_query.setAdapter(queryListViewBaseAdapter);
	}
}
