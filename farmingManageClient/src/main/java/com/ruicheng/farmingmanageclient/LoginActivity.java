package com.ruicheng.farmingmanageclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;
import com.ruicheng.farmingmanageclient.bean.LoginInfo;
import com.ruicheng.farmingmanageclient.bean.VersionNameInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.db.LoginInfoHelper;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.Code;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.StringUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;
import com.ruicheng.farmingmanageclient.utils.WebUtil;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private Spinner sp_strict;
	private ArrayAdapter<String> spAdapter;
	private String[] spData = null;
	private EditText et_username, et_password, et_authcode, tv_server;
	private Button btn_commit, btn_cancel;
	private String sp_clickData = "东路";
	private Dialog loadingDialog;
	private ImageView tv_showauthcode;
	// 产生的验证码
	private String realCode;
	private int pos;
	private TextView tv_register;
	private LoginInfo loginInfo;

	private PackageInfo packageInfo;
	private VersionNameInfo versionNameInfo;
	private final int TAGHandle = 1;
	private Handler mHandler;
	private String TAG = "LoginActivity";
	private TextView sp_test ;
	protected static final int DISTRICT = 0;
	private String dcId ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);

		PackageManager packageManager = getPackageManager();

		try {
			packageInfo = packageManager.getPackageInfo(getApplicationContext()
					.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init();
		tv_showauthcode.setImageBitmap(Code.getInstance().createBitmap());
		realCode = Code.getInstance().getCode();
		// openOrCloseKeybd(false);
		setListener();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == RESULT_OK) {
			switch (requestCode) {
				case DISTRICT:
					Bundle bundle = data.getExtras();

					AcquireAllDcInfo acquireAllDcInfo = (AcquireAllDcInfo) bundle.getSerializable("acquireAllDcInfo");
					sp_test.setText(acquireAllDcInfo.getDcName());
					dcId = acquireAllDcInfo.getDcId();
					break;
				default:
					break;
			}
		}
	}
	public void setListener() {
		btn_commit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		tv_showauthcode.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		et_username.addTextChangedListener(et_usernameTextWatcher);
		sp_strict.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

				sp_clickData = "";
				sp_clickData = spData[position];
				pos = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		tv_server.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_GO
						|| actionId == EditorInfo.IME_ACTION_NEXT
						|| actionId == EditorInfo.IME_ACTION_DONE) {
					getMatchingServer();
					return true;
				}
				return false;
			}
		});
		sp_test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("".equals(tv_server.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "填写服务器");
					return;
				}else {
					getMatchingServer();
				}
			}
		});
	}

	public void init() {

		sp_test = (TextView)findViewById(R.id.sp_test);

		loadingDialog = DialogUtils.requestDialog(this);

		sp_strict = (Spinner) findViewById(R.id.sp_strict);

		btn_commit = (Button) findViewById(R.id.btn_commit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);

		tv_showauthcode = (ImageView) findViewById(R.id.tv_showauthcode);
		et_authcode = (EditText) findViewById(R.id.et_authcode);

		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_server = (EditText) findViewById(R.id.tv_server);

		if (!"".equals(PreferencesUtils.getString(getApplicationContext(),
				Constant.USERNAME))
				&& !"null".equals(PreferencesUtils.getString(
				getApplicationContext(), Constant.USERNAME))) {
			et_username.setText(PreferencesUtils.getString(
					getApplicationContext(), Constant.USERNAME));
		}

		if (!"".equals(PreferencesUtils.getString(getApplicationContext(),
				Constant.PASSWORD))
				&& !"null".equals(PreferencesUtils.getString(
				getApplicationContext(), Constant.PASSWORD))) {
			et_password.setText(PreferencesUtils.getString(
					getApplicationContext(), Constant.PASSWORD));
		}
		if (!"".equals(PreferencesUtils.getString(getApplicationContext(),
				Constant.SERVERADDRESS))
				&& !"null".equals(PreferencesUtils.getString(
				getApplicationContext(), Constant.SERVERADDRESS))) {
			tv_server.setText(PreferencesUtils.getString(
					getApplicationContext(), Constant.SERVERADDRESS));

			/*ServiceNameHandler.allList.clear();

			LoginInfoHelper loginInfoHelper = new LoginInfoHelper(
					getApplicationContext());

			ServiceNameHandler.allList = loginInfoHelper.selectLoginfo();

			if (ServiceNameHandler.allList.size() > 0
					&& ServiceNameHandler.allList != null) {
				spData = new String[ServiceNameHandler.allList.size()];
				for (int i = 0; i < ServiceNameHandler.allList.size(); i++) {
					spData[i] = ((AcquireAllDcInfo) ServiceNameHandler.allList
							.get(i)).getDcName();
				}

				 * spAdapter = new ArrayAdapter<String>(
				 * getApplicationContext(),
				 * android.R.layout.simple_spinner_dropdown_item, spData);

				spAdapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.spinner_style, spData);

				sp_strict.setAdapter(spAdapter);
			} */
			if (!"".equals(PreferencesUtils.getString(getApplicationContext(),
					Constant.DCNAME))
					&& !"null".equals(PreferencesUtils.getString(
					getApplicationContext(), Constant.DCNAME))) {
				sp_test.setText(PreferencesUtils.getString(
						getApplicationContext(), Constant.DCNAME));
			}
		}

		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.arg1) {
					case TAGHandle:

						res = (HttpResponse) msg.obj;
						if (res ==null) {
							ToastUtils.show(getApplicationContext(), "请输入正确的服务器地址");
							return ;

						}
						if (res.getStatusLine().getStatusCode() == 200) {
							loadingDialog.dismiss();
							// 获取区域数据
							ServiceNameHandler.BASE_URL_INTERFACE = "";
							ServiceNameHandler.BASE_URL_INTERFACE = tv_server
									.getText().toString();
							TwitterRestClient.BASE_URL = "http://"
									+ ServiceNameHandler.BASE_URL_INTERFACE
									+ "/clmk";
							String context = TwitterRestClient.BASE_URL;
							getVersion();
						getAcquireAllDc();
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(),LoginDistrictAc.class);
							startActivityForResult(intent, DISTRICT);
						} else {
							loadingDialog.dismiss();
							ToastUtils.show(getApplicationContext(), "请输入正确的服务器地址");
						}

						break;

					default:
						break;
				}

			};

		};

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_commit:
			/*
			 * String phoneCode = et_authcode.getText().toString(); if
			 * (phoneCode.equals(realCode)) { Toast.makeText(LoginActivity.this,
			 * phoneCode + "验证码正确", Toast.LENGTH_SHORT).show(); } else {
			 * Toast.makeText(LoginActivity.this, phoneCode + "验证码错误",
			 * Toast.LENGTH_SHORT).show(); return; }
			 */

				if ("".equals(et_username.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "请输入账号");
					return;
				}
				if ("".equals(et_password.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "请输入密码");
					return;
				}
				// 点击登陆时，保存该用户个人信息
			/*if (!"192.168.1.113:8080".equals(tv_server.getText().toString())) {
				ToastUtils.show(getApplicationContext(), "请输入正确的服务器地址");
				return;
			}  */
				if ("".equals(sp_test.getText().toString())) {
					ToastUtils.show(getApplicationContext(), "请选择区域");
					return;
				}
			/*
			 * if (PreferencesUtils.getString(getApplicationContext(),
			 * Constant.SERVERADDRESS
			 * )==null||"".equals(PreferencesUtils.getString
			 * (getApplicationContext(), Constant.SERVERADDRESS))) {
			 * TwitterRestClient.BASE_URL = } else { TwitterRestClient.BASE_URL
			 * = PreferencesUtils.getString(getApplicationContext(),
			 * Constant.SERVERADDRESS); }
			 */
				ServiceNameHandler.BASE_URL_INTERFACE = PreferencesUtils.getString(
						getApplicationContext(), Constant.SERVERADDRESS);
				String str = TwitterRestClient.BASE_URL;
				logign();
				break;
			case R.id.btn_cancel:
				new AlertDialog.Builder(this)
						.setTitle("提示：")
						.setMessage("确认退出应用？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										PreferencesUtils.remove(
												getApplicationContext(),
												Constant.USERNAME);
										PreferencesUtils.remove(
												getApplicationContext(),
												Constant.DCID);
										PreferencesUtils.remove(
												getApplicationContext(),
												Constant.USERID);
										PreferencesUtils.remove(
												getApplicationContext(),
												Constant.DCNAME);
										quitApp();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {

									}
								}).show();
				break;
			case R.id.tv_showauthcode:
				tv_showauthcode.setImageBitmap(Code.getInstance().createBitmap());
				break;
			case R.id.tv_register:
				Intent i = new Intent();
				i.setClass(this, MainActivity.class);
				PreferencesUtils.remove(getApplicationContext(), Constant.USERNAME);
				PreferencesUtils.remove(getApplicationContext(), Constant.DCID);
				PreferencesUtils.remove(getApplicationContext(), Constant.USERID);
				PreferencesUtils.remove(getApplicationContext(), Constant.DCNAME);
				PreferencesUtils.remove(getApplicationContext(),
						Constant.USERCREATENAME);
				PreferencesUtils.putString(getApplicationContext(),
						Constant.USERNAME, "admin");
				PreferencesUtils.putString(getApplicationContext(), Constant.DCID,
						"1");
				PreferencesUtils
						.putInt(getApplicationContext(), Constant.USERID, 1);
				PreferencesUtils.putString(getApplicationContext(),
						Constant.DCNAME, sp_clickData);
				PreferencesUtils.putString(getApplicationContext(),
						Constant.USERCREATENAME, "管理员");
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
				break;
			default:
				break;
		}
	}

	private void quitApp() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != KeyEvent.ACTION_UP) {
			//键盘显示则隐藏键盘，并且判断输入的地址。否则提示是否退出应用
			if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
			{
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
				//判断服务器地址
				getMatchingServer();
			}else{

				quiteApp();
			}

			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	long endTime;

	/**
	 * 退出程序
	 */
	private void quiteApp() {
		if ((System.currentTimeMillis() - endTime) > 2000) {
			ToastUtils.show(this, "再按一次退出程序");
			endTime = System.currentTimeMillis();
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 登陆
	 *
	 */
	public void logign() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", "mobile");
			params.put("logName", et_username.getText().toString());
			params.put("logPsw", et_password.getText().toString());

			if ("".equals(dcId)||"null".equals(dcId)||dcId == null) {
				params.put("logDcId",
						PreferencesUtils.getString(getApplicationContext(), Constant.DCID));
			} else {
				params.put("logDcId",
						dcId);
			}
			/*if (pos == 0) {
				params.put("logDcId",
						((AcquireAllDcInfo) ServiceNameHandler.allList.get(0))
								.getDcId());
			} else if (pos == 1) {
				params.put("logDcId",
						((AcquireAllDcInfo) ServiceNameHandler.allList.get(1))
								.getDcId());
			} else if (pos == 2) {
				params.put("logDcId",
						((AcquireAllDcInfo) ServiceNameHandler.allList.get(2))
								.getDcId());
			} else if (pos == 3) {
				params.put("logDcId",
						((AcquireAllDcInfo) ServiceNameHandler.allList.get(3))
								.getDcId());
			} else if ("东路".equals(sp_test)) {
				params.put("logDcId",
						((AcquireAllDcInfo) ServiceNameHandler.allList.get(0))
								.getDcId());
			}*/
			TwitterRestClient.get(Constant.LOGIN, params,
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
								if ("1".equals(JSONUtils.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"用户名为空");
								} else if ("2".equals(JSONUtils
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"密码为空");
								} else if ("3".equals(JSONUtils
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"区域为空");
								} else if ("4".equals(JSONUtils
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"用户名错误");
								} else if ("5".equals(JSONUtils
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"密码错误");
								} else if ("6".equals(JSONUtils
										.getResult(response))) {
									ToastUtils.show(getApplicationContext(),
											"该用户不能选择此区域");
								} else if ("7".equals(JSONUtils
										.getResult(response))) {
									loginInfo = JSONUtils
											.getLoginInfo(response);
									saveLogInfo(loginInfo);
									ToastUtils.show(getApplicationContext(),
											"登录成功");
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
	 *
	 * 获取区域列表
	 */
	public void getAcquireAllDc() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("androidAccessType", Constant.ANDROIDACCESSTYPE);
			ServiceNameHandler.BASE_URL_INTERFACE = tv_server.getText()
					.toString();
			TwitterRestClient.get(Constant.ACQUIREALLDC, params,
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
							ServiceNameHandler.allList.clear();
							ServiceNameHandler.allList = JSONUtils
									.getAcquireAllDcInfo(response);
							LoginInfoHelper loginInfoHelper = new LoginInfoHelper(
									getApplicationContext());
							loginInfoHelper.deleteLoginInfo();
							loginInfoHelper
									.saveLoginfo(ServiceNameHandler.allList);

							spData = new String[ServiceNameHandler.allList
									.size()];
							for (int i = 0; i < ServiceNameHandler.allList
									.size(); i++) {
								spData[i] = ((AcquireAllDcInfo) ServiceNameHandler.allList
										.get(i)).getDcName();
							}
							spAdapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.spinner_style, spData);
							sp_strict.setAdapter(spAdapter);
						}
					});
		}

	}

	/**
	 * 获取版本号
	 *
	 */
	public void getVersion() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			loadingDialog.show();
			RequestParams params = new RequestParams();
			params.put("versionNum", packageInfo.versionName);
			TwitterRestClient.get(Constant.VERSION, params,
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
							ToastUtils.show(getApplicationContext(),
									"数据获取失败...");
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
											  final JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if (loadingDialog.isShowing()) {
								loadingDialog.dismiss();
							}
							versionNameInfo = JSONUtils.getVersion(response);
							if (!packageInfo.versionName.equals(versionNameInfo
									.getVERSION_NUM())) {
								new AlertDialog.Builder(LoginActivity.this)
										.setTitle("提示：")
										.setMessage("需要更新当前版本")
										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface arg0,
															int arg1) {

														Intent intent = new Intent();
														intent.setAction("android.intent.action.VIEW");
														Uri content_url = Uri
																.parse(versionNameInfo
																		.getVERSION_URL_ANDROID());
														intent.setData(content_url);
														startActivity(intent);

													}
												}).show();
							}
						}
					});
		}
	}

	/**
	 * 匹配服务器
	 *
	 */
	private HttpResponse res = null;

	public void getMatchingServer() {
		if (NetUtils.checkNetConnection(getApplicationContext())) {
			new Thread() {
				@Override
				public void run() {

					res = WebUtil.getHttpResponse("http://"
							+StringUtils.replaceBlank(tv_server.getText().toString())+ "/clmk");

					Message msg = Message.obtain();

					msg.arg1 = TAGHandle;

					msg.obj = res;
					mHandler.sendMessage(msg);
				}
			}.start();
		}
	}

	private void saveLogInfo(LoginInfo loginInfo) {
		PreferencesUtils.remove(getApplicationContext(), Constant.USERNAME);
		PreferencesUtils.remove(getApplicationContext(), Constant.DCID);
		PreferencesUtils.remove(getApplicationContext(), Constant.DCNAME);
		PreferencesUtils.remove(getApplicationContext(), Constant.USERID);
		PreferencesUtils.remove(getApplicationContext(),
				Constant.USERCREATENAME);
		PreferencesUtils.remove(getApplicationContext(), Constant.PASSWORD);
		PreferencesUtils
				.remove(getApplicationContext(), Constant.SERVERADDRESS);

		PreferencesUtils.putString(getApplicationContext(), Constant.USERNAME,
				loginInfo.getUserLoginName());
		PreferencesUtils.putString(getApplicationContext(), Constant.DCID,
				loginInfo.getDcId());
		PreferencesUtils.putString(getApplicationContext(), Constant.DCNAME,
				sp_test.getText().toString());
		PreferencesUtils.putInt(getApplicationContext(), Constant.USERID, 1);
		PreferencesUtils.putString(getApplicationContext(),
				Constant.USERCREATENAME, loginInfo.getUserCreateName());
		PreferencesUtils.putString(getApplicationContext(), Constant.PASSWORD,
				et_password.getText().toString());
		PreferencesUtils.putString(getApplicationContext(),
				Constant.SERVERADDRESS, tv_server.getText().toString());

		Intent i = new Intent();
		i.setClass(this, MainActivity.class);
		startActivity(i);
		finish();
		overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
	}

	private boolean checkInfoIsNull() {
		if (et_username.getText().toString() == null
				|| "".equals(et_username.getText().toString())) {
			ToastUtils.show(this, "用户名不能为空");
			return false;
		}
		if (et_password.getText().toString() == null
				|| "".equals(et_password.getText().toString())) {
			ToastUtils.show(this, "密码不能为空");
			return false;
		}
		if (sp_clickData == null || "".equals(sp_clickData)) {
			ToastUtils.show(this, "区域不能为空");
			return false;
		}
		return true;
	}

	TextWatcher et_usernameTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart;
		private int editEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {

			if (et_username.getText().toString() == null
					|| "".equals(et_username.getText().toString())) {
			}
			return;

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			editStart = et_password.getSelectionStart();
			editEnd = et_password.getSelectionEnd();
			if (temp.length() > 4) {
				/*
				 * s.delete(editStart-1, editEnd); int tempSelection =
				 * editStart; edit_share_count.setText(s);
				 * edit_share_count.setSelection(tempSelection);
				 */
			}
		}
	};

	// 弹出或者关闭键盘
	private void openOrCloseKeybd(boolean isOpen) {
		if (isOpen) {
			et_username.setFocusable(true);
			et_username.setFocusableInTouchMode(true);
			et_username.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(et_username, InputMethodManager.RESULT_SHOWN);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
					InputMethodManager.HIDE_IMPLICIT_ONLY);

		} else {
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_username.getWindowToken(), 0);
		}
	}
}
