package com.ruicheng.farmingmanageclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.base.BaseActivity;
import com.ruicheng.farmingmanageclient.bean.PurchaseInfo;
import com.ruicheng.farmingmanageclient.bean.PurchasePartOfInfo;
import com.ruicheng.farmingmanageclient.constants.Constant;
import com.ruicheng.farmingmanageclient.net.TwitterRestClient;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;
import com.ruicheng.farmingmanageclient.utils.DialogUtils;
import com.ruicheng.farmingmanageclient.utils.JSONUtils;
import com.ruicheng.farmingmanageclient.utils.NetUtils;
import com.ruicheng.farmingmanageclient.utils.PreferencesUtils;
import com.ruicheng.farmingmanageclient.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加作物交售详细界面
 *
 * @author zhaobeibei
 *
 */
public class AddCropActivity extends BaseActivity implements OnClickListener {

	private Button tv_modify, btn_last, btn_next;

	private EditText  et_goodPricem,
			et_purchasingWeight, et_receivePackagesm, et_packageWeight
			;
	private TextView et_goodName,et_goodMoney;
	private TextView et_goodUnit;
	private Bundle bundle;
	private String purId;
	private Dialog loadingDialog;
	private PurchaseInfo purchaseInfo;
	/** pop窗口 */
	private PopupWindow pop;
	/** pop布局 */
	private LinearLayout tianynagmenu_popup;
	private int position = 0;
	private PurchasePartOfInfo purchasePartOfInfo;
	private ImageView img_comment_back;

	private StringBuffer goodNamePaths;
	private StringBuffer receivePackagesmPaths;
	private StringBuffer goodUnitmPaths;
	private StringBuffer packageWeightmPaths;
	private StringBuffer totalGoodWeightmPaths;
	private StringBuffer goodPricemPaths;
	private StringBuffer goodMoneymPaths;
	private StringBuffer goodIdmPaths;
	private StringBuffer purIdmPaths;
	private StringBuffer purInfoIdmPaths;
	private final int  IAPACKING = 0 ;
	private final int CROPTYPENAME = 1;
	private String dicId ;
	private boolean isEmpty ;
	private Button btn_save;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_addcropcrop);

		bundle = getIntent().getExtras();
		if (bundle != null) {

			isEmpty = bundle.getBoolean("isEmpty");


		}
		init();
		initPopupWindow();
		if (isEmpty == false) {
			purId = bundle.getString("PurId");
			getAllPurchaseInfo();
		}
		setListener();
	}

	@Override
	public void init() {
		loadingDialog = DialogUtils.requestDialog(this);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		tv_modify = (Button) findViewById(R.id.tv_modify);
		et_goodName = (TextView) findViewById(R.id.et_goodName);
		et_goodPricem = (EditText) findViewById(R.id.et_goodPricem);
		et_goodUnit = (TextView) findViewById(R.id.et_goodUnit);
		et_purchasingWeight = (EditText) findViewById(R.id.et_purchasingWeight);
		et_receivePackagesm = (EditText) findViewById(R.id.et_receivePackagesm);
		et_packageWeight = (EditText) findViewById(R.id.et_packageWeight);
		et_goodMoney = (TextView) findViewById(R.id.et_goodMoney);
		btn_last = (Button) findViewById(R.id.btn_last);
		btn_next = (Button) findViewById(R.id.btn_next);
		if (isEmpty == true) {
			btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
			btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
		} else {
			btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
		}

		ServiceNameHandler.purchaseInfoList.clear();
		purchasePartOfInfo = new PurchasePartOfInfo();

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
		/*if (et_goodPricem.getText().toString().contains(".")) {
			et_goodPricem.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
		}*/
		setPricePoint(et_goodPricem);
	}

	@Override
	public void setListener() {
		tv_modify.setOnClickListener(this);
		btn_last.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		et_goodUnit.setOnClickListener(this);
		et_goodUnit.addTextChangedListener(et_goodPricemTextWatcher);

		//单价
		et_goodPricem.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					et_goodMoney.setText(String.valueOf(Double.parseDouble(et_goodPricem.getText().toString())*Double.parseDouble(et_purchasingWeight.getText().toString())));
				}
			}
		});
		//收货重量
		et_purchasingWeight.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					et_goodMoney.setText(String.valueOf(Double.parseDouble(et_goodPricem.getText().toString())*Double.parseDouble(et_purchasingWeight.getText().toString())));
				}
			}
		});
		//收货件数
		et_receivePackagesm.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {

					if (Double.valueOf(et_receivePackagesm.getText().toString()) == 0) {
						et_packageWeight.setText("0");
					} else {
						et_packageWeight.setText(String.valueOf(Double.parseDouble(et_purchasingWeight.getText().toString())/Double.parseDouble(et_receivePackagesm.getText().toString())));
					}
				}
			}
		});
		//件重(斤)
		et_packageWeight.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {

					if (Double.valueOf(et_packageWeight.getText().toString())==0) {
						et_receivePackagesm.setText("0");
					} else {
						et_receivePackagesm.setText(String.valueOf(Double.parseDouble(et_purchasingWeight.getText().toString())/Double.parseDouble(et_packageWeight.getText().toString())));
					}
				}
			}
		});
		et_goodName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(AddCropActivity.this, CropTypeNameAc.class);
				i.putExtra("fromWhichView", 9);
				startActivityForResult(i, CROPTYPENAME);
				overridePendingTransition(R.anim.zoomout, R.anim.zoomin);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case IAPACKING:
					if (data!=null) {

						Bundle bundle =data.getExtras();
						String ispacking = bundle.getString("ispacking");

						et_goodUnit.setText(ispacking);
					}
					break;
				case CROPTYPENAME:
					String cropType = data.getStringExtra("cropType");
					et_goodName.setText(cropType);
					dicId = data.getStringExtra("dicId");
					break;
				default:
					break;
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_modify:
				tianynagmenu_popup.startAnimation(AnimationUtils.loadAnimation(
						AddCropActivity.this, R.anim.activity_translate_in));
				pop.showAtLocation(btn_last, Gravity.BOTTOM, 0, 0);

				break;
			case R.id.et_goodUnit:

				Intent i = new Intent();
				i.setClass(getApplicationContext(),IsPackageAc.class);
				startActivityForResult(i, IAPACKING);

				break;
			//保存按钮
			case R.id.btn_save:
				save();
				break;
			case R.id.btn_last:
				// 点击获取上一条数据
				if (position == 0) {
					ToastUtils.show(getApplicationContext(), "当前已是第一条数据..");
					btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
					btn_next.setBackgroundColor(Color.parseColor("#009933"));
					return;
				}
				position--;
				if (purchaseInfo !=null) {
					setDataToView();
				}

				break;
			case R.id.btn_next:
				btn_last.setBackgroundColor(Color.parseColor("#009933"));
				// 点击获取下一条数据ServiceNameHandler.GoodsReceiveInfoList
				if (purchaseInfo!=null) {
					if (position == purchaseInfo.getUrchasePartOfInfoList().size() - 1) {
						ToastUtils.show(getApplicationContext(), "无更多数据..");
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
						return;
					}
					position++;
					setDataToView();

					if (position == 0) {
						btn_last.setBackgroundColor(Color.parseColor("#F2F2F2"));
					} else if (position == purchaseInfo.getUrchasePartOfInfoList()
							.size() - 1) {
						btn_next.setBackgroundColor(Color.parseColor("#F2F2F2"));
					}
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 采购单对应的详细信息
	 *
	 */
	public void getAllPurchaseInfo() {
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
			params.put("purId", purId);
			TwitterRestClient.get(Constant.GETALLPURCHASEINFO, params,
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
									purchaseInfo = JSONUtils
											.getAllPurchaseInfo(response);
									setDataToView();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}

	public void setDataToView() {
		et_goodName.setText(purchaseInfo.getUrchasePartOfInfoList()
				.get(position).getGoodName());
		if (!"".equals(purchaseInfo.getUrchasePartOfInfoList().get(position)
				.getGoodPrice())
				&& !"null".equals(purchaseInfo.getUrchasePartOfInfoList()
				.get(position).getGoodPrice())) {
			et_goodPricem.setText(purchaseInfo.getUrchasePartOfInfoList()
					.get(position).getGoodPrice());
		} else {
			et_goodPricem.setText("1.0");
		}
		if (!"".equals(purchaseInfo.getUrchasePartOfInfoList().get(position)
				.getGoodUnit())
				&& !"null".equals(purchaseInfo.getUrchasePartOfInfoList()
				.get(position).getGoodUnit())) {
			if ("1".equals(purchaseInfo.getUrchasePartOfInfoList()
					.get(position).getGoodUnit())) {
				et_goodUnit.setText("是");
			} else {
				et_goodUnit.setText("否");
			}
		} else {
			et_goodUnit.setText("");
		}
		if (!"".equals(purchaseInfo.getUrchasePartOfInfoList().get(position)
				.getGoodNumber())
				&& !"null".equals(purchaseInfo.getUrchasePartOfInfoList()
				.get(position).getGoodNumber())) {
			et_purchasingWeight.setText(purchaseInfo.getUrchasePartOfInfoList()
					.get(position).getGoodNumber());
		} else {
			et_purchasingWeight.setText("1.0");
		}
		if (!"".equals(purchaseInfo.getUrchasePartOfInfoList().get(position)
				.getPackageWeight())
				&& !"null".equals(purchaseInfo.getUrchasePartOfInfoList()
				.get(position).getPackageWeight())) {
			et_packageWeight.setText(purchaseInfo.getUrchasePartOfInfoList()
					.get(position).getPackageWeight());
		} else {
			et_packageWeight.setText("0.0");
		}
		et_receivePackagesm.setText("0.0");

	}

	private void initPopupWindow() {
		pop = new PopupWindow(AddCropActivity.this);
		View view = getLayoutInflater().inflate(
				R.layout.item_tianyangpopupmenu_query, null);
		tianynagmenu_popup = (LinearLayout) view
				.findViewById(R.id.equip_popup_menu);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_equippopup_delete);
		Button bt2 = (Button) view.findViewById(R.id.item_equippopup_modify);
		Button bt3 = (Button) view.findViewById(R.id.item_equippopup_cancel);
		Button bt4 = (Button) view.findViewById(R.id.item_equippopup_query);
		bt2.setText("继续添加货物");
		bt4.setText("添加完成");
		bt1.setTextColor(Color.RED);
		// 点击父布局消失框pop
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();

			}
		});
		bt4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 添加完成
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				save();


			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				/* "继续添加货物 */
				purchasePartOfInfo
						.setGoodName(et_goodName.getText().toString());

				purchasePartOfInfo.setGoodPrice(et_goodPricem.getText()
						.toString());
				if ("是".equals(et_goodUnit.getText().toString())) {
					purchasePartOfInfo
							.setGoodUnit("1");
				} else {
					purchasePartOfInfo
							.setGoodUnit("0");
				}
				purchasePartOfInfo.setGoodNumber(et_purchasingWeight.getText()
						.toString());

				purchasePartOfInfo.setPackageWeight(et_packageWeight.getText()
						.toString());
				purchasePartOfInfo.setReceivePackagesm(et_receivePackagesm
						.getText().toString());
				purchasePartOfInfo.setGoodMoney(et_goodMoney.getText()
						.toString());
				if (isEmpty) {

				} else {

				}
				if (!"".equals(dicId)&&dicId!=null) {
					purchasePartOfInfo.setGoodId(dicId);
				} else {
					purchasePartOfInfo.setGoodId(purchaseInfo
							.getUrchasePartOfInfoList().get(position).getGoodId());
				}
				if (isEmpty ==true) {
					purchasePartOfInfo.setPurId("");
					purchasePartOfInfo.setPurInfoId("");
				} else{
					purchasePartOfInfo.setPurId(purchaseInfo
							.getUrchasePartOfInfoList().get(position).getPurId());
					purchasePartOfInfo.setPurInfoId(purchaseInfo
							.getUrchasePartOfInfoList().get(position)
							.getPurInfoId());
				}
				// 将所添加的商品实例添加到集合中
				ServiceNameHandler.purchaseInfoList.add(purchasePartOfInfo);
				et_goodName.setText("");
				et_goodPricem.setText("0.0");
				et_goodUnit.setText("");
				et_purchasingWeight.setText("0");
				et_receivePackagesm.setText("0");
				et_packageWeight.setText("0");
				et_goodMoney.setText("0.0");
				//重新创建新的商品实例
				purchasePartOfInfo = new PurchasePartOfInfo();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(AddCropActivity.this)
						.setTitle("提示：")
						.setMessage("确认删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										if (ServiceNameHandler.purchaseInfoList.size()>0) {
											ServiceNameHandler.purchaseInfoList
													.remove(ServiceNameHandler.purchaseInfoList.size()-1);
										}

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {

									}
								}).show();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				tianynagmenu_popup.clearAnimation();
				// 取消

			}
		});
	}

	private void save() {
		if (!estimateInfoIsNullUtils()) {
			return;
		}

		purchasePartOfInfo
				.setGoodName(et_goodName.getText().toString());

		purchasePartOfInfo.setGoodPrice(et_goodPricem.getText()
				.toString());
		if ("是".equals(et_goodUnit.getText().toString())) {
			purchasePartOfInfo
					.setGoodUnit("1");
		} else {
			purchasePartOfInfo
					.setGoodUnit("0");
		}
		purchasePartOfInfo.setGoodNumber(et_purchasingWeight.getText()
				.toString());

		purchasePartOfInfo.setPackageWeight(et_packageWeight.getText()
				.toString());
		purchasePartOfInfo.setReceivePackagesm(et_receivePackagesm
				.getText().toString());
		purchasePartOfInfo.setGoodMoney(et_goodMoney.getText()
				.toString());
		if (!"".equals(dicId)&&dicId!=null) {
			purchasePartOfInfo.setGoodId(dicId);
		}else{
			purchasePartOfInfo.setGoodId(purchaseInfo
					.getUrchasePartOfInfoList().get(position).getGoodId());
		}
		if (isEmpty ==true) {
			purchasePartOfInfo.setPurId("");
			purchasePartOfInfo.setPurInfoId("");
		} else {
			purchasePartOfInfo.setPurId(purchaseInfo
					.getUrchasePartOfInfoList().get(position).getPurId());
			purchasePartOfInfo.setPurInfoId(purchaseInfo
					.getUrchasePartOfInfoList().get(position)
					.getPurInfoId());
		}
		// 将所添加的商品实例添加到集合中
		ServiceNameHandler.purchaseInfoList.add(purchasePartOfInfo);

		Intent i = new Intent();
		Bundle bundle = new Bundle();

		goodNamePaths = new StringBuffer();
		receivePackagesmPaths = new StringBuffer();
		goodUnitmPaths = new StringBuffer();
		packageWeightmPaths = new StringBuffer();
		totalGoodWeightmPaths = new StringBuffer();
		goodPricemPaths = new StringBuffer();
		goodMoneymPaths = new StringBuffer();
		goodIdmPaths = new StringBuffer();
		purIdmPaths = new StringBuffer();
		purInfoIdmPaths = new StringBuffer();
		for (int j = 0; j < ServiceNameHandler.purchaseInfoList.size(); j++) {
			if (ServiceNameHandler.purchaseInfoList.get(j)
					!= null) {

				if (ServiceNameHandler.purchaseInfoList.size() ==1) {
					goodNamePaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getGoodName());
					receivePackagesmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getReceivePackagesm());
					goodUnitmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getGoodUnit());
					packageWeightmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getPackageWeight());
					totalGoodWeightmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getGoodNumber());
					goodPricemPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getGoodPrice());
					goodMoneymPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getGoodMoney());
					if (!"".equals(dicId)&&dicId!=null) {
						goodIdmPaths
								.append(dicId);
					} else {
						goodIdmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodId());
					}
					purIdmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getPurId());
					purInfoIdmPaths
							.append(ServiceNameHandler.purchaseInfoList
									.get(0).getPurInfoId());
				} else {
					if (goodNamePaths.length() !=0) {
						goodNamePaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getGoodName());
					} else {
						goodNamePaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodName());
					}
					if (receivePackagesmPaths.length()!=0) {
						receivePackagesmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getReceivePackagesm());
					} else {
						receivePackagesmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getReceivePackagesm());
					}
					if (goodUnitmPaths.length() !=0) {
						goodUnitmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getGoodUnit());
					} else {
						goodUnitmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodUnit());
					}
					if (packageWeightmPaths.length() !=0) {

						packageWeightmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getPackageWeight());
					} else {
						packageWeightmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getPackageWeight());
					}

					if (totalGoodWeightmPaths.length() !=0) {

						totalGoodWeightmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getGoodNumber());
					} else {
						totalGoodWeightmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodNumber());
					}

					if (goodPricemPaths.length()!=0) {
						goodPricemPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getGoodPrice());

					} else {
						goodPricemPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodPrice());
					}
					if (goodMoneymPaths.length() !=0) {
						goodMoneymPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getGoodMoney());
					} else {
						goodMoneymPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getGoodMoney());
					}
					if (goodIdmPaths.length()!=0) {
						if (!"".equals(dicId)&&dicId!=null) {
							goodIdmPaths.append(",").append(dicId);

						} else {
							goodIdmPaths.append(",").append(
									ServiceNameHandler.purchaseInfoList.get(j)
											.getGoodId());
						}
					} else {
						if (!"".equals(dicId)&&dicId!=null) {
							goodIdmPaths
									.append(dicId);
						} else {
							goodIdmPaths
									.append(ServiceNameHandler.purchaseInfoList
											.get(0).getGoodId());
						}
					}
					if (purIdmPaths.length()!=0) {
						purIdmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getPurId());
					} else {
						purIdmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getPurId());
					}
					if (purInfoIdmPaths.length()!=0) {
						purInfoIdmPaths.append(",").append(
								ServiceNameHandler.purchaseInfoList.get(j)
										.getPurInfoId());
					} else {
						purInfoIdmPaths
								.append(ServiceNameHandler.purchaseInfoList
										.get(0).getPurInfoId());
					}
				}
			}
		}
		bundle.putString("goodIdm", goodIdmPaths.toString());
		bundle.putString("purIdm", purIdmPaths.toString());
		bundle.putString("purInfoIdm", purInfoIdmPaths.toString());
		bundle.putString("goodNamem", goodNamePaths.toString()); // 名称
		bundle.putString("receivePackagesm",
				receivePackagesmPaths.toString());// 收货件数
		bundle.putString("goodUnitm", goodUnitmPaths.toString()); // 是否包装
		bundle.putString("packageWeightm",
				packageWeightmPaths.toString());// 件重
		bundle.putString("totalGoodWeightm",
				totalGoodWeightmPaths.toString());// 收货重量
		bundle.putString("goodPricem", goodPricemPaths.toString()); // 单价
		bundle.putString("goodMoneym", goodMoneymPaths.toString()); // 总价
		i.putExtras(bundle);
		setResult(RESULT_OK, i);
		finish();
	}

	TextWatcher et_goodPricemTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart ;
		private int editEnd ;
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {
//	          mTextView.setText(s);//将输入的内容实时显示
		}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			editStart = et_goodPricem.getSelectionStart();
			editEnd = et_goodPricem.getSelectionEnd();
			if (temp.length() > 4) {
				Toast.makeText(getApplicationContext(),
						"输入的位数已经超过了限制！", Toast.LENGTH_SHORT)
						.show();
				et_goodPricem.setText(s);
	                /*s.delete(editStart-1, editEnd);
	                int tempSelection = editStart;
	                edit_share_count.setText(s);
	                edit_share_count.setSelection(tempSelection);  */
			}
		}
	};
	public static void setPricePoint(final EditText editText) {

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}
				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}
	/**
	 * 判断提交信息是否为空
	 *
	 * @return
	 */
	public boolean estimateInfoIsNullUtils() {
		if ("0.0".equals(et_goodPricem.getText().toString())||"0".equals(et_goodPricem.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "单价不能为零");
			return false;
		}
		if ("0.0".equals(et_purchasingWeight.getText().toString())||"0".equals(et_purchasingWeight.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "收货重量不能为零");
			return false;
		}
		if ("0.0".equals(et_receivePackagesm.getText().toString())||"0".equals(et_receivePackagesm.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "收货件数不能为零");
			return false;
		}
		if ("0.0".equals(et_packageWeight.getText().toString())||"0".equals(et_packageWeight.getText().toString())) {
			ToastUtils.show(getApplicationContext(), "件重不能为零");
			return false;
		}
		return true;
	}
}
