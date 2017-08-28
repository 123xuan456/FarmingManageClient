package com.ruicheng.farmingmanageclient.constants;

public class Constant {

	/* 登陆界面spinner的数据 */
	public static final String PLEASECHOOSE = "请选择...";
	public static final String EASTROAD = "东路";
	public static final String HUIWEN = "会文";
	public static final String CHUNCHANG = "囤昌";
	public static final String WANNING = "万宁";

	/* 主界面导航颜色 */
	public static final String MAIIN_NAVITION_SELECT = "#C3E08E";
	public static final String MAIIN_NAVITION = "#E9F1F4";
	// 获取失败 String 类型
	public static final String FAILURE = "获取失败";
	// 获取失败 int 类型
	public static final int FAILUREINT = -1;
	public static final String TYPE = "type";
	// 用户名
	public static final String USERNAME = "userName";
	// 用户名
	public static final String SERVERADDRESS = "serverAddress";
	// 密码
	public static final String PASSWORD = "passWord";
	// 用户id
	public static final String USERID = "userId";
	// 用户登陆区域Id
	public static final String DCID = "dcId";
	// 用户登陆区域
	public static final String DCNAME = "dcName";
	// 用户手机号
	public static final String ANDROIDACCESSTYPE = "mobile";
	// 经办人
	public static final String USERCREATENAME = "userCreateName";
	// 操作类型
	public static final String OPTIONTYPE = "optionType";
	// 服务站id
	public static final int SIZE = 10;
	public static final int NUM = 1;
	public static final String STATIONID = "stationId";

	/* 服务器地址 */
	public static final String SERVIECE_CLIENT = "http://192.168.1.113:8080/clmk";
	/* 匹配服务器地址接口 */
	public static final String MATCHINGSERVER = "/clmk";
	/* 播种移栽登记接口 */
	public static final String OPTIONPLOUGH = "/record/optionPlough.action";
	/* 播种移栽查询接口 */
	public static final String QUERYRECORD = "/record/queryRecord.action";
	/* 登陆接口 */
	public static final String LOGIN = "/user/login.action";
	/* 区域列表接口 */
	public static final String ACQUIREALLDC = "/init/acquireAllDc.action";
	/* 播种移栽登记保存接口 */
	public static final String SAVERECORD = "/record/saveRecord.action";
	/* 田洋收成登记保存接口 */
	public static final String SAVEREAP = "/record/saveReap.action";
	/* 获取农作物名称的接口 */
	public static final String QUERYPRODUCT = "/record/getQueryProduct.action";
	/* 播种移栽管理修改接口 */
	public static final String UPDATERECORD = "/record/updateRecord.action";
	/* 播种移栽管理删除接口 */
	public static final String DELETERECORD = "/record/deleteRecord.action";
	/* 获取田洋信息数据列表接口 */
	public static final String QUERYPLOUGH = "/plough/queryPlough.action";
	/* 田洋收成登记明细接口 */
	public static final String PLOUGHLIST = "/plough/ploughList.action";
	/* 1.添加收货信息接口 */
	public static final String ADDGOODRECEIVE = "/goodsReceive/addGoodReceive.action";
	/* 收货信息保存接口 */
	public static final String SAVEGOODSRECEIVE = "/goodsReceive/saveGoodsReceive.action";
	/* 获取供应商信息接口*/
	public static final String QUERYCLIENT = "/goodsReceive/getQueryClient.action";
	/*3.获取商品信息接口*/
	public static	 final String QUERYGOOD = "/goodsReceive/getQueryGood.action";
	/* 收货信息管理数据获取接口*/
	public static final String QUERYGOODSRECEIVE = "/goodsReceive/queryGoodsReceive.action";
	/* 收货信息管理数据查看接口*/
	public static final String GOODRECEIVEDETAILFORPACKAGEPRINT = "/goodsReceive/goodReceiveDetailForPackagePrint.action";
	/*收货信息管理数据删除接口*/
	public static final String DELEGOODSRECEIVESHEET = "/goodsReceive/delGoodsReceiveSheet.action";
	/*收货信息管理数据修改保存接口*/
	public static final String SAVEEDIT = "/goodsReceive/saveEdit.action";
	/*获取采购单号信息接口*/
	public static final String GETALLNOTCOMPLETESHEET = "/goodsReceive/getAllNotCompleteSheet.action";
	/*采购单对应的详细信息*/
	public static final String GETALLPURCHASEINFO = "/goodsReceive/getAllPurchaseInfo.action";
	/*生产计划定义保存接口*/
	public static final String SAVEPRODUCTIONPLAN = "/productionplan/saveProductionPlan.action";

	/* 获取生产履历统计的列表*/
	public static final String SATAISTICSCROPINFO = "/record/statisticsCropInfo.action";

	/*成本公式管理获取数据列表接口*/
	public static final String QUERYFROMULASETTINGPAGE = "/productionplan/queryFromulaSettingPage.action";
	/*成本公式是否有效接口*/
	public static final String ADJUSTSTATEFROMULAVALID = "/productionplan/adjustStateFromulaValid.action";
	/*成本公式管理删除接口*/
	public static final String DELETEFROMULASETTINGPAGE = "/productionplan/deleteFromulaSetting.action";
	/*成本公式管理修改保存接口*/
	public static final String DOUPDATEFROMULASETTINGPAGE = "/productionplan/doUpdateFromulaSetting.action";
	/*成本公式管理修改保存接口*/
	public static final String UPDATEFROMULASETTINGPAGE = "/productionplan/updatePlanFromulaPage.action";
	/*成本公式设定接口*/
	public static final String ADDFROMULASETTING = "/productionplan/addFromulaSetting.action";
	/*验证农作物是否定义成本公式*/
	public static final String AJAXQUERYISVALIDFROMULA = "/productionplan/ajaxQueryIsValidFromula.action";

	/*成本公式设定保存接口*/
	public static final String SAVEFROMULASETTING = "/productionplan/saveFromulaSetting.action";
	/*成本公式管理查看接口*/
	public static final String DOVIEWPLANFROMULA = "/productionplan/doViewPlanFromula.action";
	/*生产计划管理列表数据获取接口*/
	public static final String QUERYPRODUCTIONPLANPAGE = "/productionplan/queryProductionPlanPage.action";
	/*生产计划管理数据删除接口*/
	public static final String DELETEPRODUCTIONPLN = "/productionplan/deleteProductionPlan.action";
	/*生产计划管理数据修改接口*/
	public static final String DOUPDATEPRODUCTIONPLAN = "/productionplan/doUpdateProductionPlan.action";
	/*生产计划统计分析获取数据列表接口*/
	public static final String QUERYPRODUCTIONPLANSTATISTICSPAGE = "/productionplan/queryProductionPlanStatisticsPage.action";
	/*生产计划管理数据查看接口*/
	public static final String DOVIEWPRODUCTIONPLAN = "/productionplan/doViewProductionPlan.action";
	/*生产计划定义接口*/
	public static final String ADDPRODUCTIONPLANFORPROCESS = "/productionplan/addProductionPlanForProcess.action";
	/*加工量/种植量费用接口（生产计划定义）*/
	public static final String AJAXQUERYFROMULAVALUE = "/productionplan/ajaxQueryFromulaValue.action";


	/*获取服务站数据列表接口*/
	public static final String QUERYSERVERDTATION = "/serverStation/queryServerStation.action";

	/*服务站管理修改数据保存接口*/
	public static final String EDITESERVERSTATION = "/serverStation/editServerStation.action";

	/*服务站管理删除接口*/
	public static final String DELETESERVERSTATION = "/serverStation/deleteServerStation.action";
	/*田洋信息删除接口*/
	public static final String DELETEPLOUGH = "/plough/deletePlough.action";
	/*保存添加服务站信息接口*/
	public static final String SAVESERVERSTATION = "/serverStation/saveServerStation.action";
	/*田洋信息保存接口*/
	public static final String SAVEPLOUGH = "/plough/savePlough.action";
	/*图片上传接口*/
	public static final String FILEUPLOADPOSITION = "/serverStation/fileUpLoadPosition.action";
	/*田洋信息修改获取信息接口*/
	public static final String EDITPLOUGHPAGE = "/plough/editPloughPage.action";
	/*田洋信息修改信息保存接口*/
	public static final String EDITPLOUGH = "/plough/editPlough.action";
	/*田洋信息农事记录接口*/
	public static final String QUERYALLRECORD = "/record/queryAllRecord.action";
	/*版本信息接口*/
	public static final String VERSION = "/android/version.action";
	/*播种移栽管理修改接口*/
	public static final String UPADATERECORDPAGE = "/record/updateRecordPage.action";
	/*播种移栽管理修改接口*/
	public static final String ISHAVESAMECODE = "/plough/isHaveSameCode.action";
	/*服务站管理获取修改数据接口*/
	public static final String EDITSERVERSTATIONPAGE = "/serverStation/editServerStationPage.action";
	/*服务站管理查看接口*/
	public static final String VIEWSERVERSTATION = "/serverStation/viewServerStation.action";



}
