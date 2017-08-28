package com.ruicheng.farmingmanageclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.BaseStationInfo;
import com.ruicheng.farmingmanageclient.bean.CameraImage;
import com.ruicheng.farmingmanageclient.bean.MapServerInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.BimpHandler;
import com.ruicheng.farmingmanageclient.util.ImageFileUtils;
import com.ruicheng.farmingmanageclient.utils.BitmapUtils;
import com.ruicheng.farmingmanageclient.utils.DateUtils;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

/**
 *
 * 修改服务站管理界面
 *
 * @author zhaobeibei
 *
 */
public class UpdateServiceManageAc extends BaseActivity implements
		OnClickListener {

	private TextView tv_dcName, tv_stationName, tv_cropTypes;
	private ImageView img_comment_back, iv_managerPicture;
	private Dialog loadingDialog;
	private List<Object> listAll = null;
	private Button btn_save;
	private Bundle bundle;
	private BaseStationInfo baseStationInfo;
	private EditText tv_plantingArea, tv_stationAddr, tv_postCode,
			tv_stationCode, tv_soilState, tv_farmAmount, tv_stationBak,
			tv_stationManager, tv_managerPhone, tv_managerSpell,
			tv_technicianAmount, tv_managerResume;
	/** 选择系统相机的请求码 */
	public static final int TAKE_PICTURE = 1;

	/** 选择相册的请求码 */
	public static final int Album_PICTURE = 2;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private String imagePath;// 选择要上传的头像的路径
	private MapServerInfo mapServerInfo;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_updateservicemanage);

		bundle = getIntent().getExtras();
		if (bundle != null) {
			baseStationInfo = (BaseStationInfo) bundle
					.getSerializable("baseStationInfo");
		}

		init();
		getEditServerStationPage();
		setListener();
		initPop();
	}

	/**
	 * 修改数据
	 *
	 */
	public void getEditServerStationPage() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
			params.put("stationId", baseStationInfo.getStationId());
			params.put("dcId", baseStationInfo.getDcId());

			TwitterRestClient.get(Constant.EDITSERVERSTATIONPAGE, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
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
									/*
									 * listAll =
									 * JSONUtils.getQueryClient(response);
									 * setListData(listAll);
									 */

									mapServerInfo = JSONUtils
											.getEditServerStationPage(response);

									/*
									 * Bitmap bitmap = BitmapUtils.getImg(
									 * mapServerInfo.getManagerPicture(),
									 * mapServerInfo .getManagerPicture()
									 * .substring( mapServerInfo
									 * .getManagerPicture() .lastIndexOf( "/") +
									 * 1, mapServerInfo .getManagerPicture()
									 * .length() - 4));
									 * iv_managerPicture.setImageBitmap(bitmap);
									 */

									Bitmap selectBitmap = BitmapUtils
											.getThumBitmapFromFile(mapServerInfo
													.getManagerPicture());

									iv_managerPicture
											.setImageBitmap(selectBitmap);

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			finish();
			overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void init() {

		btn_save = (Button) findViewById(R.id.btn_save);

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

		tv_dcName = (TextView) findViewById(R.id.tv_dcName);
		tv_stationName = (TextView) findViewById(R.id.tv_stationName);
		tv_plantingArea = (EditText) findViewById(R.id.tv_plantingArea);
		tv_cropTypes = (TextView) findViewById(R.id.tv_cropTypes);
		tv_stationAddr = (EditText) findViewById(R.id.tv_stationAddr);
		tv_postCode = (EditText) findViewById(R.id.tv_postCode);
		tv_stationCode = (EditText) findViewById(R.id.tv_stationCode);
		tv_soilState = (EditText) findViewById(R.id.tv_soilState);
		tv_farmAmount = (EditText) findViewById(R.id.tv_farmAmount);
		tv_stationBak = (EditText) findViewById(R.id.tv_stationBak);
		tv_stationManager = (EditText) findViewById(R.id.tv_stationManager);
		tv_managerPhone = (EditText) findViewById(R.id.tv_managerPhone);
		tv_managerSpell = (EditText) findViewById(R.id.tv_managerSpell);
		tv_technicianAmount = (EditText) findViewById(R.id.tv_technicianAmount);
		tv_managerResume = (EditText) findViewById(R.id.tv_managerResume);

		loadingDialog = DialogUtils.requestDialog(this);

		tv_dcName.setText(baseStationInfo.getDcName());
		tv_stationName.setText(baseStationInfo.getStationName());
		tv_cropTypes.setText(baseStationInfo.getCropTypes());
		tv_plantingArea.setText(baseStationInfo.getPlantingArea());
		tv_stationAddr.setText(baseStationInfo.getStationAddr());
		tv_postCode.setText(baseStationInfo.getPostCode());
		tv_stationCode.setText(baseStationInfo.getStationCode());
		tv_soilState.setText(baseStationInfo.getSoilState());
		tv_farmAmount.setText(baseStationInfo.getFarmAmount());
		tv_stationBak.setText(baseStationInfo.getStationBak());
		tv_stationManager.setText(baseStationInfo.getStationManager());
		tv_managerPhone.setText(baseStationInfo.getManagerPhone());
		tv_managerSpell.setText(baseStationInfo.getManagerSpell());
		tv_technicianAmount.setText(baseStationInfo.getTechnicianAmount());
		tv_managerResume.setText(baseStationInfo.getManagerResume());

		iv_managerPicture = (ImageView) findViewById(R.id.iv_managerPicture);

		if (BimpHandler.listSelectBitmap != null
				&& BimpHandler.listSelectBitmap.size() > 0) {

			iv_managerPicture.setImageBitmap(BimpHandler.listSelectBitmap
					.get(0));
		} else {
			iv_managerPicture.setImageResource(R.drawable.ic_launcher);
		}

	}

	@Override
	public void setListener() {
		btn_save.setOnClickListener(this);
		iv_managerPicture.setOnClickListener(this);
	}

	/**
	 * 判断提交信息是否为空
	 *
	 * @param v
	 * @return
	 */
	public boolean estimateInfoIsNullUtils() {
		if (tv_dcName.getText().toString() == null
				|| "null".equals(tv_dcName.getText().toString())
				|| "".equals(tv_dcName.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "所属区域不能为空");
			return false;
		}
		if (tv_stationName.getText().toString() == null
				|| "null".equals(tv_stationName.getText().toString())
				|| "".equals(tv_stationName.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "服务站名称不能为空");
			return false;
		}
		if (tv_plantingArea.getText().toString() == null
				|| "null".equals(tv_plantingArea.getText().toString())
				|| "".equals(tv_plantingArea.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "种植面积不能为空");
			return false;
		}
		if (tv_cropTypes.getText().toString() == null
				|| "null".equals(tv_cropTypes.getText().toString())
				|| "".equals(tv_cropTypes.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "生产品种不能为空");
			return false;
		}
		if (tv_stationCode.getText().toString() == null
				|| "null".equals(tv_stationCode.getText().toString())
				|| "".equals(tv_stationCode.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "编号不能为空");
			return false;
		}
		if (tv_soilState.getText().toString() == null
				|| "null".equals(tv_soilState.getText().toString())
				|| "".equals(tv_soilState.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "土壤情况不能为空");
			return false;
		}
		if (tv_farmAmount.getText().toString() == null
				|| "null".equals(tv_farmAmount.getText().toString())
				|| "".equals(tv_farmAmount.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "种植农户数量不能为空");
			return false;
		}
		if (tv_stationManager.getText().toString() == null
				|| "null".equals(tv_stationManager.getText().toString())
				|| "".equals(tv_stationManager.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "站长姓名不能为空");
			return false;
		}
		if (tv_managerPhone.getText().toString() == null
				|| "null".equals(tv_managerPhone.getText().toString())
				|| "".equals(tv_managerPhone.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "站长联系电话不能为空");
			return false;
		}
		if (tv_managerSpell.getText().toString() == null
				|| "null".equals(tv_managerSpell.getText().toString())
				|| "".equals(tv_managerSpell.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "站长姓名拼音不能为空");
			return false;
		}
		if (tv_technicianAmount.getText().toString() == null
				|| "null".equals(tv_technicianAmount.getText().toString())
				|| "".equals(tv_technicianAmount.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "技术员人数不能为空");
			return false;
		}
		return true;
	}

	/**
	 * 服务站管理修改数据保存
	 *
	 */
	public void getEditServerStation() {
		if (!estimateInfoIsNullUtils()) {
			return;
		}
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));

			params.put("serverStation.stationId",
					baseStationInfo.getStationId());
			params.put("serverStation.stationName",
					baseStationInfo.getStationName());
			params.put("serverStation.stationManager", tv_stationManager
					.getText().toString());
			params.put("serverStation.stationAddr", tv_stationAddr.getText()
					.toString());
			params.put("serverStation.plantingArea", tv_plantingArea.getText()
					.toString());
			params.put("serverStation.dcId", baseStationInfo.getDcId());
			params.put("serverStation.stationCode", tv_stationCode.getText()
					.toString());
			params.put("serverStation.soilState", tv_soilState.getText()
					.toString());
			params.put("serverStation.cropTypes", tv_cropTypes.getText()
					.toString());
			params.put("serverStation.farmAmount", tv_farmAmount.getText()
					.toString());
			params.put("serverStation.managerResume", tv_managerResume
					.getText().toString());
			params.put("serverStation.managerPhone", tv_managerPhone.getText()
					.toString());
			params.put("serverStation.technicianAmount", tv_technicianAmount
					.getText().toString());
			params.put("serverStation.managerPicture", imagePath);
			params.put("serverStation.stationLon", 0);
			params.put("serverStation.stationLat", 0);
			params.put("serverStation.stationBak", tv_stationBak.getText()
					.toString());
			params.put("serverStation.postCode", tv_postCode.getText()
					.toString());
			params.put("serverStation.managerSpell", tv_managerSpell.getText()
					.toString());
			params.put("serverStation.createTime", DateUtils.getStringDate());

			TwitterRestClient.get(Constant.EDITESERVERSTATION, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							try {
								if ("failure".equals(JSONUtils
										.getResultMsg(errorResponse))) {
									ToastUtils.show(getApplicationContext(),
											"更改信息成功");
									finish();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
											"更改信息成功");
									finish();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				getEditServerStation();
				break;
			case R.id.iv_managerPicture:
				ll_popup.startAnimation(AnimationUtils.loadAnimation(
						UpdateServiceManageAc.this, R.anim.activity_translate_in));
				pop.showAtLocation(findViewById(R.id.parent), Gravity.BOTTOM, 0,
						150);
				break;
			default:
				break;
		}

	}

	private void initPop() {
		pop = new PopupWindow(getApplicationContext());
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		// 点击父布局消失框pop
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pop消失 清除动画
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		// 选择相机拍照
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				systemCamera();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		// 选择相册
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						AlbumActivity.class);
				intent.putExtra("pic", 1);
				startActivityForResult(intent, Album_PICTURE);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		// 取消
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}

	/**
	 * 调用系统相机拍照
	 */
	public void systemCamera() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case TAKE_PICTURE:
					// 选择系统相机拍照照片
					if (BimpHandler.tempSelectBitmap.size() < 9) {
						String fileName = String
								.valueOf(System.currentTimeMillis());

						// 获取照片
						Bitmap bm = (Bitmap) data.getExtras().get("data");

						Uri uriImageData;
						// 获取拍照返回图片路径 两种方式获取
						if (data.getData() != null) {
							uriImageData = data.getData();
						} else {
							uriImageData = Uri.parse(MediaStore.Images.Media
									.insertImage(getApplicationContext()
											.getContentResolver(), bm, null, null));
						}
						Cursor cursor = getApplicationContext()
								.getContentResolver().query(uriImageData, null,
										null, null, null);

						String filePath = "";
						if (cursor.moveToFirst()) {
							filePath = cursor.getString(cursor
									.getColumnIndex("_data"));// 获取绝对路径
							// System.out.println("拍照返回路径:"+filePath);
						}
						cursor.close();
						// 保存到文件夹
						ImageFileUtils.saveBitmap(bm, fileName);

						// 保存到照片列表里
						CameraImage takePhoto = new CameraImage();
						takePhoto.setBitmap(bm);
						takePhoto.setImagePath(filePath);
						BimpHandler.tempSelectBitmap.add(takePhoto);
						getFileUpLoadPosition();
					}
					break;
				case Album_PICTURE:
					// 选择相册的请求码
					imagePath = BimpHandler.tempSelectBitmap.get(0).getImagePath();
					Bitmap selectBitmap = BitmapUtils
							.getThumBitmapFromFile(imagePath);
					BimpHandler.listSelectBitmap.clear();
					BimpHandler.listSelectBitmap.add(selectBitmap);
					iv_managerPicture.setImageBitmap(selectBitmap);
					getFileUpLoadPosition();
					break;
			}
		}
	}

	/**
	 * 头像上传接口
	 *
	 */
	public void getFileUpLoadPosition() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			params.put(
					"userId",
					PreferencesUtils.getInt(getApplicationContext(),
							Constant.USERID) + "");
			params.put("userName", PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));

			// imagePath = BimpHandler.tempSelectBitmap.get(0).getImagePath();

			try {
				params.put("uploads", new File(imagePath)); // 文件
				params.put("uploadsFileName", imagePath.substring(
						imagePath.lastIndexOf("/") + 1, imagePath.length())); // 文件名称
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			TwitterRestClient.post(Constant.FILEUPLOADPOSITION, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
											  Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								ToastUtils.show(getApplicationContext(),
										"头像上传失败");
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

									Bitmap selectBitmap = BitmapUtils
											.getThumBitmapFromFile(imagePath);
									BimpHandler.listSelectBitmap.clear();
									BimpHandler.listSelectBitmap
											.add(selectBitmap);
									iv_managerPicture
											.setImageBitmap(selectBitmap);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}

}
