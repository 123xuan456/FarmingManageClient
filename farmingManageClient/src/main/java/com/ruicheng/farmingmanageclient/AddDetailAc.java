package com.ruicheng.farmingmanageclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PloughListDetailInfo;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;

/**
 * 增加明细界面
 *
 * @author zhaobeibei
 *
 */
public class AddDetailAc extends BaseActivity implements OnClickListener {
	private Button btn_adddetail;
	public TextView tv_ploughCode, et_recordDate, et_tianYangArea,
			et_soilState, et_cropLevel, et_productName, et_productPName;
	private EditText et_harvestNum,et_cropPtype;
	private final int CROPTYPENAME = 0;
	private final int PLOUGHCODE = 1;
	private final int CROPLEVEL = 2;
	private String stationId;
	private Bundle bundle;
	private PloughListDetailInfo ploughListDetailInfo;
	private String dicCode; // 作物编号

	private String arr = "";
	private ImageView img_comment_back;
	private String dicId ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_adddetail);

		bundle = getIntent().getExtras();
		if (bundle != null) {
			stationId = bundle.getString("stationId");
		}
		init();
		setListener();
	}

	@Override
	public void init() {

		img_comment_back = (ImageView) findViewById(R.id.img_comment_back);
		findViewById(R.id.ImageView_Linearlayout_Back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						/* openOrCloseKeybd(false); */
						img_comment_back.setVisibility(View.GONE);
						finish();
						overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
					}
				});

		btn_adddetail = (Button) findViewById(R.id.btn_adddetail);

		tv_ploughCode = (TextView) findViewById(R.id.tv_ploughCode);
		et_recordDate = (TextView) findViewById(R.id.et_recordDate);

		et_productName = (TextView) findViewById(R.id.et_productName);
		et_harvestNum = (EditText) findViewById(R.id.et_harvestNum);
		et_cropLevel = (TextView) findViewById(R.id.et_cropLevel);

		et_soilState = (TextView) findViewById(R.id.et_soilState);
		et_tianYangArea = (TextView) findViewById(R.id.et_tianYangArea);

		et_cropPtype = (EditText) findViewById(R.id.et_cropPtype);

		ServiceNameHandler.ploughListDetailList.clear();
		ploughListDetailInfo = new PloughListDetailInfo();
		ServiceNameHandler.ploughListDetailList.add(ploughListDetailInfo);
	}

	@Override
	public void setListener() {
		btn_adddetail.setOnClickListener(this);
		et_productName.setOnClickListener(this);
		tv_ploughCode.setOnClickListener(this);
		et_cropLevel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_adddetail:
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getPloughCode() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getPloughId())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getPloughId())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setPloughCode(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getPloughId()
											+ ","
											+ ploughListDetailInfo.getPloughId());
				} else {
					arr = ploughListDetailInfo.getPloughId();
					ServiceNameHandler.ploughListDetailList
							.get(0).setPloughId(ploughListDetailInfo.getPloughId());
				}

				if (ServiceNameHandler.ploughListDetailList
						.get(0).getDicValue() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getDicValue())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getDicValue())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setDicValue(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getDicValue()
											+ ","
											+ et_productName
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setDicValue(
							et_productName
									.getText()
									.toString());
				}
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getCropPtype() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getCropPtype())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getCropPtype())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setCropPtype(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getCropPtype()
											+ ","
											+ et_cropPtype
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setCropPtype(
							et_cropPtype
									.getText()
									.toString());
				}
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getHarvestNum() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getHarvestNum())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getHarvestNum())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setHarvestNum(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getHarvestNum()
											+ ","
											+ et_harvestNum
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setHarvestNum(
							et_harvestNum.getText()
									.toString());
				}
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getCropLevel() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getCropLevel())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getCropLevel())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setCropLevel(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getCropLevel()
											+ ","
											+ et_cropLevel
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setCropLevel(
							et_cropLevel.getText()
									.toString());
				}
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getPloughArea() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getPloughArea())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getPloughArea())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setPloughArea(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getPloughArea()
											+ ","
											+ et_tianYangArea
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setPloughArea(
							et_tianYangArea
									.getText()
									.toString());
				}
				if (ServiceNameHandler.ploughListDetailList
						.get(0).getSoilState() != null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getSoilState())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getSoilState())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setSoilState(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getSoilState()
											+ ","
											+ et_soilState
											.getText()
											.toString());
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setSoilState(
							et_soilState.getText()
									.toString());
				}

				if (ServiceNameHandler.ploughListDetailList
						.get(0).getDicId()!= null
						&& !"".equals(ServiceNameHandler.ploughListDetailList
						.get(0).getDicId())
						&& !"null"
						.equals(ServiceNameHandler.ploughListDetailList
								.get(0)
								.getDicId())) {
					ServiceNameHandler.ploughListDetailList
							.get(0)
							.setDicId(
									ServiceNameHandler.ploughListDetailList
											.get(0)
											.getDicId()
											+ "," + dicId);
				} else {
					ServiceNameHandler.ploughListDetailList
							.get(0).setDicId(dicId);
				}
				new AlertDialog.Builder(this)
						.setTitle("提示：")
						.setMessage("是否继续添加明细？")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										tv_ploughCode.setText("");
										et_productName.setText("");// 作物名称
										et_cropPtype.setText(""); // 作物品种
										et_harvestNum.setText("");
										et_cropLevel.setText("");
										et_tianYangArea.setText("");
										et_soilState.setText("");
									}
								})
						.setNegativeButton("否",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										Intent i = new Intent();
										Bundle bundle = new Bundle();
										bundle.putString(
												"ploughCode",
												ServiceNameHandler.ploughListDetailList
														.get(0).getPloughId());
										bundle.putString(
												"productName",
												ServiceNameHandler.ploughListDetailList
														.get(0).getDicValue());
										bundle.putString(
												"productPName",
												ServiceNameHandler.ploughListDetailList
														.get(0).getCropPtype());
										bundle.putString(
												"harvestNum",
												ServiceNameHandler.ploughListDetailList
														.get(0).getHarvestNum());
										bundle.putString(
												"cropLevel",
												ServiceNameHandler.ploughListDetailList
														.get(0).getCropLevel());
										bundle.putString(
												"tianYangArea",
												ServiceNameHandler.ploughListDetailList
														.get(0).getPloughArea());
										bundle.putString(
												"soilState",
												ServiceNameHandler.ploughListDetailList
														.get(0).getSoilState());
										bundle.putString(
												"dicCode",
												ServiceNameHandler.ploughListDetailList
														.get(0).getDicId());
										i.putExtras(bundle);
										setResult(RESULT_OK, i);
										finish();
									}
								}).show();
				break;

			case R.id.et_productName:

				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), CropTypeNameAc.class);
				intent.putExtra("fromWhichView", 8);
				startActivityForResult(intent,CROPTYPENAME);
				break ;

			case R.id.tv_ploughCode:

				Intent ploughIntent = new Intent();
				ploughIntent
						.setClass(getApplicationContext(), PloughDetailAc.class);
				Bundle ploughBundle = new Bundle();
				ploughBundle.putString("stationId", stationId);
				ploughIntent.putExtras(ploughBundle);
				startActivityForResult(ploughIntent, PLOUGHCODE);
				break;
			case R.id.et_cropLevel:
				Intent cropLevelIntent = new Intent();
				cropLevelIntent.setClass(AddDetailAc.this, CropLevelAc.class);
				Bundle cropLevelBundle = new Bundle();
				cropLevelBundle.putInt("fromWhichView", 1);
				cropLevelIntent.putExtras(cropLevelBundle);
				startActivityForResult(cropLevelIntent, CROPLEVEL);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);

				break;
			default:
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case CROPTYPENAME:
					String cropType = data.getStringExtra("cropType");
					et_productName.setText(cropType);
					String cropPtype = data.getStringExtra("cropPtype");
					if (!"".equals(cropPtype)&&!"null".equals(cropPtype)) {
						et_cropPtype.setText(cropPtype);

					} else {
						et_cropPtype.setText("");
					}
					dicId = data.getStringExtra("dicId");

					break;
				case PLOUGHCODE:
					ploughListDetailInfo = (PloughListDetailInfo) data
							.getSerializableExtra("ploughListDetailInfo");
					tv_ploughCode.setText(ploughListDetailInfo.getPloughCode());
//				et_productName.setText(ploughListDetailInfo.getDicValue());
//				et_cropPtype.setText(ploughListDetailInfo.getCropPtype());
					et_tianYangArea.setText(ploughListDetailInfo.getPloughArea());
					et_soilState.setText(ploughListDetailInfo.getSoilState());
//				dicCode = ploughListDetailInfo.getDicCode();
					break;
				case CROPLEVEL:
					String croplevel = data.getStringExtra("croplevel");
					et_cropLevel.setText(croplevel);
					break;
				default:
					break;
			}
		}
	}

}
