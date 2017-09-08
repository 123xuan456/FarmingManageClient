package com.ruicheng.farmingmanageclient;
/*田洋生产二级界面后点击添加按钮跳转 的三级界面：添加田洋播种界面、添加田洋农事项目、添加田洋收成*/

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.adapter.TianYnagFragAdapter;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.fragment.TianYangFertilizerFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYangPesticidesFragment;
import com.ruicheng.farmingmanageclient.fragment.TianYnagDailyFragment;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.view.SelectDateTimePopWin;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TianYagProAddActivity extends BaseActivity implements
		OnClickListener {

	private ViewPager vp_tianyangadd_viewpager;
	private List<Fragment> listFrag;
	private TianYnagFragAdapter tianYnagFragAdapter;
	private Bundle bundle,addetailBundle;
	private Button btn_tianyangadd_daily, btn_tianyangadd_fertilizeruse,
			btn_tianyangadd_pesticideuse;
	private ImageView img_comment_back;
	private TextView tv_title;
	private int kind;
	private TextView tv_pro_save;
	private Button btn_adddetail;
	private Intent intent;
	private TextView et_servicenumber;
	private TextView tv_harvestdate;
	private Dialog loadingDialog;
	private int optionType;
	private String TAG = "TianYagProAddActivity";
	private List<Object> listAll;
	private TextView tv_servicestation;
	private final int SERVICESTATION = 0;
	private final int CROPTYPENAME = 1;
	private final int ADDDETAIL = 2;
	private final int CROPPTYPE = 3;
	private final int PLOUGHID = 4;
	private TextView tv_transplantdate, tv_seeddate,tv_ploughId;
	private TextView tv_registUser, tv_registdate, tv_cropType;
	private EditText et_cropPtype, et_actionPerson, tv_actionBak,tv_actionPerson;
	private String dicCode;// 农作物编号
	private Button btn_save;
	private StationData stationData;

	private String ploughCode, productName, productPName,
			harvestNum, cropLevel,tianYangArea,soilState,dicCodee;
	private CropTypeNameInfo cropTypeNameInfo ;
	private String stationId ;
	private PloughListInfo ploughListInfo ;
	private TextView tv_servicenam;
	private String servicename;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		intent = getIntent();
		if (intent != null) {
			kind = intent.getIntExtra("KIND", -1);
			optionType = intent.getIntExtra("optionType", -1);
			servicename=intent.getStringExtra("servicename");
			stationId=intent.getStringExtra("stationId");
			if (kind == 0) {
				// 播种移栽界面
				setContentView(R.layout.activity_seedandharvest);
			} else if (kind == 2) {
				//添加田洋收成界面
				setContentView(R.layout.activity_harvest);
			} else {
				// 田洋农事项目界面
				setContentView(R.layout.activity_tianyagproadd);
			}
			init();
			if (kind == 1) {

				setVPagerData();
			}
			setListener();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode ==event.KEYCODE_BACK) {
			/*android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		loadingDialog = DialogUtils.requestDialog(this);
		tv_servicestation = (TextView) findViewById(R.id.tv_servicestation);
		if (kind == 0) {

			tv_ploughId = (TextView) findViewById(R.id.tv_ploughId);

			//tv_title.setText("添加田洋播种");
			tv_pro_save = (TextView) findViewById(R.id.tv_pro_save);

			tv_transplantdate = (TextView) findViewById(R.id.tv_transplantdate);
			tv_seeddate = (TextView) findViewById(R.id.tv_seeddate);
			et_servicenumber = (TextView) findViewById(R.id.et_servicenumber);

			tv_registUser = (TextView) findViewById(R.id.tv_registUser);
			tv_registdate = (TextView) findViewById(R.id.tv_registdate);
			tv_actionBak = (EditText) findViewById(R.id.tv_actionBak);

			et_cropPtype = (EditText) findViewById(R.id.et_cropPtype);
			et_actionPerson = (EditText) findViewById(R.id.et_actionPerson);
			//
			setEditTextInhibitInputSpeChat(et_cropPtype);
			setEditTextInhibitInputSpeChat(et_actionPerson);

			tv_cropType = (TextView) findViewById(R.id.tv_cropType);

			tv_registUser.setText("记录人");
			btn_save = (Button) findViewById(R.id.btn_save);
			tv_registdate.setText(DateUtils.getStringDateShort());
//			 getOptionPlough();

			tv_servicestation.setText(servicename);
			et_servicenumber.setText(stationId);

		} else if (kind == 1) {
//			tv_title.setText("添加田洋农事项目");
			vp_tianyangadd_viewpager = (ViewPager) findViewById(R.id.vp_tianyangadd_viewpager);
			btn_tianyangadd_daily = (Button) findViewById(R.id.btn_tianyangadd_daily);
			btn_tianyangadd_fertilizeruse = (Button) findViewById(R.id.btn_tianyangadd_fertilizeruse);
			btn_tianyangadd_pesticideuse = (Button) findViewById(R.id.btn_tianyangadd_pesticideuse);

			btn_tianyangadd_daily.setBackgroundColor(Color.GREEN);
			btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GRAY);
			btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GRAY);
		} else if (kind == 2) {
//			tv_title.setText("添加田洋收成");
			btn_adddetail = (Button) findViewById(R.id.btn_adddetail);

			tv_harvestdate = (TextView) findViewById(R.id.tv_harvestdate);
			et_servicenumber = (TextView) findViewById(R.id.et_servicenumber);
			btn_save = (Button) findViewById(R.id.btn_save);

			tv_registdate = (TextView) findViewById(R.id.tv_registdate);
			tv_registdate.setText(DateUtils.getStringDateShort());

			tv_actionPerson =(EditText)findViewById(R.id.tv_actionPerson);
			tv_registUser = (TextView) findViewById(R.id.tv_registUser);
			tv_servicestation.setText(servicename);
			et_servicenumber.setText(stationId);

		}

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
	}

	/**
	 * 禁止EditText输入特殊字符
	 * @param editText
	 */
	public static void setEditTextInhibitInputSpeChat(EditText editText){

		InputFilter filter=new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
				Pattern pattern = Pattern.compile(speChat);
				Matcher matcher = pattern.matcher(source.toString());
				if(matcher.find()){
					return "";
				}
				else{
					return null;
				}
			}
		};
		editText.setFilters(new InputFilter[]{filter});
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case SERVICESTATION:
					servicename = data.getStringExtra("servicename");
					tv_servicestation.setText(servicename);
					stationId = data.getStringExtra("stationId");
					et_servicenumber.setText(stationId);

					stationData = (StationData) data
							.getSerializableExtra("stationInfo");
					break;
				case CROPTYPENAME:
					String cropType = data.getStringExtra("cropType");
					tv_cropType.setText(cropType);
					dicCode = data.getStringExtra("dicCode");
					break;
				case ADDDETAIL:
					// 增加明细数据
					addetailBundle = data.getExtras();
					if (addetailBundle!=null) {
						ploughCode = addetailBundle.getString("ploughCode");
						productName = addetailBundle.getString("productName");
						productPName = addetailBundle.getString("productPName");
						harvestNum = addetailBundle.getString("harvestNum");
						cropLevel = addetailBundle.getString("cropLevel");
						soilState = addetailBundle.getString("soilState");
						tianYangArea = addetailBundle.getString("tianYangArea");
						dicCodee = addetailBundle.getString("dicCode");
//						btn_adddetail.setBackgroundColorss(Color.GRAY);
//						btn_adddetail.setClickable(false);
					}else {

					}
					break;
				case CROPPTYPE:
					bundle = data.getExtras();
					cropTypeNameInfo = (CropTypeNameInfo) bundle.getSerializable("cropPtype");
					et_cropPtype.setText(cropTypeNameInfo.getCropPtype());
					break ;
				case PLOUGHID:
					String PloughCode = data.getStringExtra("PloughCode");
					tv_ploughId.setText(PloughCode);
					ploughListInfo = (PloughListInfo) data.getSerializableExtra("PLOUGHLIST");
					break ;
				default:
					break;
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void setListener() {

		if (kind == 0) {
			tv_ploughId.setOnClickListener(this);
			et_cropPtype.setOnClickListener(this);
			btn_save.setOnClickListener(this);
			tv_cropType.setOnClickListener(this);
			tv_servicestation.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(),
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview",1);
					bundle.putInt("optionType",optionType);
					i.putExtras(bundle);
					startActivityForResult(i, SERVICESTATION);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				}
			});
			tv_transplantdate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							et_servicenumber.getWindowToken(), 0);
					new SelectDateTimePopWin(TianYagProAddActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (tv_transplantdate.getText().toString() == null) {
								ToastUtils.show(getApplicationContext(),
										"请输入移栽时间");
								return;
							}
							tv_transplantdate.setText(date);
						}

					};
				}
			});
			tv_seeddate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							et_servicenumber.getWindowToken(), 0);
					new SelectDateTimePopWin(TianYagProAddActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							tv_seeddate.setText(date);
						}

					};
				}
			});

		} else if (kind == 1) {
			TianYnagVPagerListener tianYnagVPagerListener = new TianYnagVPagerListener();
			vp_tianyangadd_viewpager
					.setOnPageChangeListener(tianYnagVPagerListener);
			btn_tianyangadd_daily.setOnClickListener(btnClickManager);
			btn_tianyangadd_fertilizeruse.setOnClickListener(btnClickManager);
			btn_tianyangadd_pesticideuse.setOnClickListener(btnClickManager);
		} else if (kind == 2) {//收成
			btn_save.setOnClickListener(btnClickAgriculture);
			btn_adddetail.setOnClickListener(btnClickAgriculture);
			tv_harvestdate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							et_servicenumber.getWindowToken(), 0);
					new SelectDateTimePopWin(TianYagProAddActivity.this, "",
							LayoutInflater.from(getApplicationContext())
									.inflate(R.layout.activity_favorite, null)
									.findViewById(R.id.container),
							SelectDateTimePopWin.PATTERN_YMDH) {
						@Override
						public void returnDate(String date) {
							if (tv_harvestdate.getText().toString() == null) {
								ToastUtils.show(getApplicationContext(),
										"请输入播种时间");
								return;
							}
							tv_harvestdate.setText(date);
						}

					};
				}
			});
			tv_servicestation.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(),
							ServiceStationNameAc.class);
					Bundle bundle = new Bundle();
					bundle.putInt("fromwhichview",1);
					bundle.putInt("optionType",optionType);
					i.putExtras(bundle);
					startActivityForResult(i, SERVICESTATION);
					overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				}
			});
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void setVPagerData() {
		listFrag = new ArrayList<Fragment>();
		TianYnagDailyFragment tianYnagDailyFragment = new TianYnagDailyFragment();
		bundle = new Bundle();
		bundle.putInt("optionType",optionType);//
		bundle.putString("servicename",servicename);//服务站名称
		bundle.putString("stationId",stationId);//服务站编号

		tianYnagDailyFragment.setArguments(bundle);
		listFrag.add(tianYnagDailyFragment);

		TianYangFertilizerFragment tianYangFertilizerFragment = new TianYangFertilizerFragment();
		bundle = new Bundle();
		bundle.putInt("optionType",optionType);
		bundle.putString("servicename",servicename);//服务站名称
		bundle.putString("stationId",stationId);//服务站编号
		tianYangFertilizerFragment.setArguments(bundle);
		listFrag.add(tianYangFertilizerFragment);

		TianYangPesticidesFragment tianYangPesticidesFragment = new TianYangPesticidesFragment();
		bundle = new Bundle();
		bundle.putInt("optionType",optionType);
		bundle.putString("servicename",servicename);//服务站名称
		bundle.putString("stationId",stationId);//服务站编号
		tianYangPesticidesFragment.setArguments(bundle);
		listFrag.add(tianYangPesticidesFragment);

		tianYnagFragAdapter = new TianYnagFragAdapter(
				getSupportFragmentManager(), listFrag);
		vp_tianyangadd_viewpager.setAdapter(tianYnagFragAdapter);
	}

	private Button.OnClickListener btnClickManager = new OnClickListener() {
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.btn_tianyangadd_daily:
					btn_tianyangadd_daily.setBackgroundColor(Color.GREEN);
					btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GRAY);
					btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GRAY);
					vp_tianyangadd_viewpager.setCurrentItem(0);

					break;
				case R.id.btn_tianyangadd_fertilizeruse:
					btn_tianyangadd_daily.setBackgroundColor(Color.GRAY);
					btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GREEN);
					btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GRAY);
					vp_tianyangadd_viewpager.setCurrentItem(1);
					break;
				case R.id.btn_tianyangadd_pesticideuse:
					btn_tianyangadd_daily.setBackgroundColor(Color.GRAY);
					btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GRAY);
					btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GREEN);
					vp_tianyangadd_viewpager.setCurrentItem(2);
					break;
				default:
					break;
			}
		}

	};
	private Button.OnClickListener btnClickAgriculture = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.tv_pro_save:
					getSaveRecord();
					break;
				case R.id.btn_adddetail:
					if ("null".equals(tv_servicestation.getText().toString())||"".equals(tv_servicestation.getText().toString())||tv_servicestation==null) {
						ToastUtils.show(getApplicationContext(), "请选择服务站");
						return ;
					}
					// 跳转到增加明细界面
					Intent i = new Intent();
					i.setClass(getApplicationContext(), AddDetailAc.class);
					Bundle bundle = new Bundle();
					bundle.putString("stationId", stationId);
					i.putExtras(bundle);
					startActivityForResult(i, ADDDETAIL);
					break;
				case R.id.btn_save:
					//天阳收成
					getSaveReap();

					break ;
				default:
					break;
			}
		}

	};

	public class TianYnagVPagerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == 0) {
				btn_tianyangadd_daily.setBackgroundColor(Color.GREEN);
				btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GRAY);
				btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GRAY);
			} else if (arg0 == 1) {
				btn_tianyangadd_daily.setBackgroundColor(Color.GRAY);
				btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GREEN);
				btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GRAY);
			} else if (arg0 == 2) {
				btn_tianyangadd_daily.setBackgroundColor(Color.GRAY);
				btn_tianyangadd_fertilizeruse.setBackgroundColor(Color.GRAY);
				btn_tianyangadd_pesticideuse.setBackgroundColor(Color.GREEN);
			}
		}
	}
	/**
	 * 田杨收成登记接口
	 *
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void getSaveReap() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (ploughCode==null&&productName==null&&productPName==null&&harvestNum==null&&cropLevel==null&&
			tianYangArea==null&&soilState==null&&dicCodee==null){
			Toast.makeText(this,"请添加明细",Toast.LENGTH_SHORT).show();
			return;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();

			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME,
					Constant.FAILURE));
			params.put("optionType", optionType);

			params.put("record.productType",optionType);
			if (stationData==null){
				params.put("record.stationId",stationId);
				params.put("record.stationCode", servicename);
			}else {
				params.put("record.stationId",stationData.getStationId());
				params.put("record.stationCode", stationData.getStationCode());
			}
			params.put("record.registUser",PreferencesUtils.getInt(getApplicationContext(),
					Constant.USERID, Constant.FAILUREINT) + "");
			params.put("record.registDate", tv_registdate.getText().toString()+" "+DateUtils.getTimeShort());

			//田洋收成
			params.put("detail.receiveDate", tv_harvestdate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("detail.actionPerson", tv_actionPerson.getText().toString());
			params.put("detail.recordDate", tv_registdate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("detail.productType",optionType);

			params.put("blockNumber",ploughCode);
			params.put("productName",productName);
			params.put("productPName",productPName);
			params.put("productId",dicCodee);
			params.put("productNum",harvestNum);
			params.put("grade",cropLevel);

			TwitterRestClient.get(Constant.SAVEREAP, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								ToastUtils
										.show(getApplicationContext(), "保存失败");
							}
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							try {
								if ("success".equals(JSONUtils
										.getResultMsg(response))) {
									ToastUtils.show(getApplicationContext(),
											"保存成功");
									finish();
								}else {
									ToastUtils
											.show(getApplicationContext(), "保存失败");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	/**
	 * 保存播种移栽登记数据
	 *
	 */
	public void getSaveRecord() {
		if (!estimateInfoIsNullUtils()) {
			return ;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();

			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID, Constant.FAILUREINT) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME,
					Constant.FAILURE));

			params.put("record.productType",optionType);
			if (stationData==null){
				params.put("record.stationId",stationId);
				params.put("record.stationCode", servicename);
			}else {
				params.put("record.stationId",stationData.getStationId());
				params.put("record.stationCode", stationData.getStationCode());
			}
			params.put("record.registUser",PreferencesUtils.getInt(getApplicationContext(),
					Constant.USERID, Constant.FAILUREINT) + "");
			params.put("record.registDate", tv_registdate.getText().toString()+" "+DateUtils.getTimeShort());

			//田洋播种
			params.put("detail.seedDate", tv_seeddate.getText().toString()+" "+DateUtils.getTimeShort());
			params.put("detail.moveDate", tv_transplantdate.getText()
					.toString()+" "+DateUtils.getTimeShort());
			params.put("detail.cropType", tv_cropType.getText().toString());
			params.put("detail.cropCode", dicCode);
			params.put("detail.cropPtype", et_cropPtype.getText().toString());
			params.put("detail.recordDate", tv_registdate.getText().toString());
			params.put("detail.actionPerson", et_actionPerson.getText()
					.toString());
			params.put("detail.actionBak", tv_actionBak.getText().toString());
			params.put("detail.productType",optionType);
			params.put("ploughId",
					ploughListInfo.getPloughId());

			TwitterRestClient.get(Constant.SAVERECORD, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								ToastUtils
										.show(getApplicationContext(), "保存失败");
							}
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							try {
								if ("success".equals(JSONUtils
										.getResultMsg(response))) {
									ToastUtils.show(getApplicationContext(),
											"保存成功");
									finish();
									overridePendingTransition(R.anim.zoomout,
											R.anim.zoomin);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @return
	 */
	public boolean estimateInfoIsNullUtils(){
		if (kind == 0) {
			if (tv_servicestation.getText().toString()==null||"null".equals(tv_servicestation.getText().toString())||"".equals(tv_servicestation.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "服务站名称不能为空");
				return false ;
			}
			if (et_servicenumber.getText().toString()==null||"null".equals(et_servicenumber.getText().toString())||"".equals(et_servicenumber.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "服务站编号不能为空");
				return false ;
			}
			if (tv_seeddate.getText().toString()==null||"null".equals(tv_seeddate.getText().toString())||"".equals(tv_seeddate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "播种日期不能为空");
				return false ;
			}
			if (tv_transplantdate.getText().toString()==null||"null".equals(tv_transplantdate.getText().toString())||"".equals(tv_transplantdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "移栽日期不能为空");
				return false ;
			}
			if (tv_cropType.getText().toString()==null||"null".equals(tv_cropType.getText().toString())||"".equals(tv_cropType.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农作物名称不能为空");
				return false ;
			}
			if (et_cropPtype.getText().toString()==null||"null".equals(et_cropPtype.getText().toString())||"".equals(et_cropPtype.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "农作物品种不能为空");
				return false ;
			}
			if (tv_registUser.getText().toString()==null||"null".equals(tv_registUser.getText().toString())||"".equals(tv_registUser.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "记录人不能为空");
				return false ;
			}
			if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "登记日期不能为空");
				return false ;
			}
			if (et_actionPerson.getText().toString()==null||"null".equals(et_actionPerson.getText().toString())||"".equals(et_actionPerson.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "实施人员不能为空");
				return false ;
			}
		} else if (kind ==2){
			if (tv_servicestation.getText().toString()==null||"null".equals(tv_servicestation.getText().toString())||"".equals(tv_servicestation.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "服务站名称不能为空");
				return false ;
			}
			if (et_servicenumber.getText().toString()==null||"null".equals(et_servicenumber.getText().toString())||"".equals(et_servicenumber.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "服务站编号不能为空");
				return false ;
			}
			if (tv_actionPerson.getText().toString()==null||"null".equals(tv_actionPerson.getText().toString())||"".equals(tv_actionPerson.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "收获人员不能为空");
				return false ;
			}
			if (tv_harvestdate.getText().toString()==null||"null".equals(tv_harvestdate.getText().toString())||"".equals(tv_harvestdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "收获日期不能为空");
				return false ;
			}
			if (tv_registUser.getText().toString()==null||"null".equals(tv_registUser.getText().toString())||"".equals(tv_registUser.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "记录人不能为空");
				return false ;
			}
			if (tv_registdate.getText().toString()==null||"null".equals(tv_registdate.getText().toString())||"".equals(tv_registdate.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "登记日期不能为空");
				return false ;
			}
			//btn_adddetail.setBackgroundColor(Color.parseColor("#009933"));
			if (ServiceNameHandler.ploughListDetailList==null||ServiceNameHandler.ploughListDetailList.size()==0) {
				ToastUtils.show(getApplicationContext(), "请添加明细");
				return false ;
			}
		}
		return true ;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_cropType:
				Intent i = new Intent();
				i.setClass(TianYagProAddActivity.this, CropTypeNameAc.class);
				i.putExtra("fromWhichView", 0);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			case R.id.btn_save:
				getSaveRecord();
				break;
			case R.id.et_cropPtype:
			/*Intent cropPtypeIntent = new Intent();
			cropPtypeIntent.setClass(getApplicationContext(),CropPtypeNameAc.class);
			startActivityForResult(cropPtypeIntent, CROPPTYPE);*/
				break ;
			case R.id.tv_ploughId:
				Intent ploughIdIntent = new Intent();
				ploughIdIntent.setClass(getApplicationContext(),TianYangNumberAc.class);
				Bundle bundle = new Bundle();
				bundle.putInt("optionType", optionType);
				bundle.putString("stationId", stationId);
				bundle.putInt("fromwhichview",1);
				ploughIdIntent.putExtras(bundle);
				startActivityForResult(ploughIdIntent, PLOUGHID);
				break ;
			default:
				break;
		}
	}

}
