package com.ruicheng.farmingmanageclient.util;

import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveInfo;
import com.ruicheng.farmingmanageclient.bean.LoginInfo;
import com.ruicheng.farmingmanageclient.bean.MinAndMaxInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListDetailInfo;
import com.ruicheng.farmingmanageclient.bean.PurchasePartOfInfo;

import java.util.ArrayList;
import java.util.List;

public class ServiceNameHandler {

	public static  String BASE_URL_INTERFACE = "";
	public static  List<Object> stationList = new ArrayList<Object>();
	public static  List<Object> recordList = new ArrayList<Object>();
	public static  List<Object> ploughList = new ArrayList<Object>();
	/*农作物名称本地存储*/
	public static  List<CropTypeNameInfo> cropTypeNameList = new ArrayList<CropTypeNameInfo>();
	/*田洋明细本地存储*/
	public static  List<PloughListDetailInfo>  ploughListDetailList = new ArrayList<PloughListDetailInfo>();
	/*作物交售信息本地存储*/
	public static  List<GoodsReceiveInfo>  GoodsReceiveInfoList = new ArrayList<GoodsReceiveInfo>();
	/*添加物品信息本地存储*/
	public static  List<PurchasePartOfInfo>  purchaseInfoList = new ArrayList<PurchasePartOfInfo>();
	/*加工成本本地存储*/
	public static  List<Object> allList = new ArrayList<Object>();
	public static  List<CustomCostListInfo> customCostListInfoList = new ArrayList<CustomCostListInfo>();
	//存放动态数据
	public static  List<MinAndMaxInfo> minAndMaxInfoList = new ArrayList<MinAndMaxInfo>();
	//存放动态数据
	public static  LoginInfo loginInfo = new LoginInfo();


}
