package com.ruicheng.farmingmanageclient.utils;

import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;
import com.ruicheng.farmingmanageclient.bean.AgriRecordTianYnagInfo;
import com.ruicheng.farmingmanageclient.bean.BaseStationInfo;
import com.ruicheng.farmingmanageclient.bean.ChargeListInfo;
import com.ruicheng.farmingmanageclient.bean.CnameInfo;
import com.ruicheng.farmingmanageclient.bean.CropTypeNameInfo;
import com.ruicheng.farmingmanageclient.bean.CustomCostListInfo;
import com.ruicheng.farmingmanageclient.bean.CustomProductionPlanInfo;
import com.ruicheng.farmingmanageclient.bean.DataListInfo;
import com.ruicheng.farmingmanageclient.bean.EditPloughPageInfo;
import com.ruicheng.farmingmanageclient.bean.FromulaListInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveDetailListInfo;
import com.ruicheng.farmingmanageclient.bean.GoodsReceiveInfo;
import com.ruicheng.farmingmanageclient.bean.LoginInfo;
import com.ruicheng.farmingmanageclient.bean.MapServerInfo;
import com.ruicheng.farmingmanageclient.bean.PlanFromulaListInfo;
import com.ruicheng.farmingmanageclient.bean.PlanInfo;
import com.ruicheng.farmingmanageclient.bean.PlanListInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListDetailInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfo;
import com.ruicheng.farmingmanageclient.bean.PloughListInfoArrayInfo;
import com.ruicheng.farmingmanageclient.bean.ProductionPlanStatisticsInfo;
import com.ruicheng.farmingmanageclient.bean.PurchaseInfo;
import com.ruicheng.farmingmanageclient.bean.PurchaseOrderInfo;
import com.ruicheng.farmingmanageclient.bean.PurchasePartOfInfo;
import com.ruicheng.farmingmanageclient.bean.RecordInfo;
import com.ruicheng.farmingmanageclient.bean.StationData;
import com.ruicheng.farmingmanageclient.bean.StationInfo;
import com.ruicheng.farmingmanageclient.bean.StatisticsCropInfo;
import com.ruicheng.farmingmanageclient.bean.UpdateDetailInfo;
import com.ruicheng.farmingmanageclient.bean.VersionNameInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

	private static String ploughId;
	private static String ploughCode;

	public static String getResult(JSONObject jsonResult) throws JSONException {

		return jsonResult.getString("result");
	}

	public static String getResultMsg(JSONObject jsonResult)
			throws JSONException {

		return jsonResult.getString("result");
	}
	/**
	 * 获取登陆用户信息
	 *
	 * @param jsonInfo
	 * @return
	 */
	public static LoginInfo getLoginInfo(JSONObject jsonInfo) {
		LoginInfo loginInfo = new LoginInfo();
		try {

			loginInfo.setDcId(jsonInfo.getString("dcId"));
			loginInfo.setUserId(jsonInfo.getString("userId"));
			loginInfo.setUserLoginName(jsonInfo.getString("userLoginName"));
			loginInfo.setUserCreateName(jsonInfo.getString("userCreateName"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginInfo;
	}

	public static List<Object> getAcquireAllDcInfo(JSONObject jsonInfo) {
		List<Object> listAll = new ArrayList<Object>();
		try {
			JSONArray jsonArr = jsonInfo.getJSONArray("allDc");
			for (int i = 0; i < jsonArr.length(); i++) {
				AcquireAllDcInfo acquireAllDcInfo = new AcquireAllDcInfo();
				JSONObject object = jsonArr.getJSONObject(i);
				acquireAllDcInfo.setDcId(object.getString("dcId"));
				acquireAllDcInfo.setDcName(object.getString("dcName"));
				listAll.add(acquireAllDcInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listAll;
	}
	public static GoodsReceiveInfo getAddGoodReceive(JSONObject jsonInfo) {
		GoodsReceiveInfo goodsReceiveInfo = new GoodsReceiveInfo();
		try {
			JSONObject jsonArr = jsonInfo.getJSONObject("goodsReceive");
			goodsReceiveInfo.setTime(jsonArr.getString("time"));
			goodsReceiveInfo.setUserId(jsonArr.getString("userId"));
			goodsReceiveInfo.setUserName(jsonArr.getString("userName"));
			goodsReceiveInfo.setTime(jsonArr.getString("time"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goodsReceiveInfo;
	}
	public static VersionNameInfo getVersion(JSONObject jsonInfo) {
		VersionNameInfo versionNameInfo = new VersionNameInfo();
		try {
			versionNameInfo.setVERSION_NUM(jsonInfo.getString("VERSION_NUM"));
			versionNameInfo.setVERSION_URL_ANDROID(jsonInfo.getString("VERSION_URL_ANDROID"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return versionNameInfo;
	}

	public static List<Object> getStationInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> stationList = new ArrayList<Object>();

		JSONArray stationJArrary = jsonStationInfo.getJSONArray("stationList");

		for (int i = 0; i < stationJArrary.length(); i++) {

			JSONObject obejct = stationJArrary.getJSONObject(i);
			StationData stationData = new StationData();
			stationData.setDcId(obejct.getString("dcId"));
			stationData.setDcName(obejct.getString("dcName"));
			stationData.setStationCode(obejct.getString("stationCode"));
			stationData.setStationName(obejct.getString("stationName"));
			stationData.setStationId(obejct.getString("stationId"));
			stationList.add(stationData);
		}
		return stationList;
	}

	public static List<StationInfo> getStationAllInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<StationInfo> stationList = new ArrayList<StationInfo>();

		JSONArray stationJArrary = jsonStationInfo.getJSONArray("stationList");

		for (int i = 0; i < stationJArrary.length(); i++) {

			JSONObject obejct = stationJArrary.getJSONObject(i);
			StationInfo stationInfo = new StationInfo();
			stationInfo.setDcId(obejct.getString("dcId"));
			stationInfo.setTechnicianAmount(obejct
					.getString("technicianAmount"));
			stationInfo.setStationCode(obejct.getString("stationCode"));
			stationInfo.setFarmAmount(obejct.getString("farmAmount"));
			stationInfo.setDcName(obejct.getString("dcName"));
			stationInfo.setStationBak(obejct.getString("stationBak"));
			stationInfo.setPlantingArea(obejct.getString("plantingArea"));
			stationInfo.setPostCode(obejct.getString("postCode"));
			stationInfo.setStationId(obejct.getString("stationId"));
			stationInfo.setStationLon(obejct.getString("stationLon"));
			stationInfo.setStationLat(obejct.getString("stationLat"));
			stationInfo.setSoilState(obejct.getString("soilState"));
			stationInfo.setManagerPicture(obejct.getString("managerPicture"));
			stationInfo.setStationManager(obejct.getString("stationManager"));
			stationInfo.setStationAddr(obejct.getString("stationAddr"));
			stationInfo.setManagerPhone(obejct.getString("managerPhone"));
			stationInfo.setManagerSpell(obejct.getString("managerSpell"));
			stationInfo.setUName(obejct.getString("UName"));
			stationInfo.setStationName(obejct.getString("stationName"));
			stationInfo.setCropTypes(obejct.getString("cropTypes"));
			stationInfo.setIsRegisted(obejct.getString("isRegisted"));
			stationInfo.setManagerResume(obejct.getString("managerResume"));
			stationList.add(stationInfo);
		}

		return stationList;
	}

	public static List<Object> getploughListInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> ploughListList = new ArrayList<Object>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("ploughList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONObject obejct = ploughListJArrary.getJSONObject(i);
			PloughListInfo ploughListInfo = new PloughListInfo();
			ploughListInfo.setSoilState(obejct.getString("soilState"));
			ploughListInfo.setPloughId(obejct.getString("ploughId"));
			ploughListInfo.setPloughCode(obejct.getString("ploughCode"));
			ploughListInfo.setPloughName(obejct.getString("ploughName"));
			ploughListInfo.setStationId(obejct.getString("stationId"));
			ploughListInfo.setPloughPoints(obejct.getString("ploughPoints"));
			ploughListInfo.setStationName(obejct.getString("stationName"));
			ploughListInfo.setPloughArea(obejct.getString("ploughArea"));
			ploughListInfo.setPloughFarm(obejct.getString("ploughFarm"));
			ploughListList.add(ploughListInfo);
		}
		return ploughListList;
	}

	public static List<RecordInfo> getRecordInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<RecordInfo> recordList = new ArrayList<RecordInfo>();
		JSONObject obejct = jsonStationInfo.getJSONObject("record");
		RecordInfo recordInfo = new RecordInfo();
		recordInfo.setRecordId(obejct.getString("recordId"));
		recordInfo.setStationCode(obejct.getString("stationCode"));
		recordInfo.setStationId(obejct.getString("stationId"));
		recordInfo.setRegistDate(obejct.getString("registDate"));
		recordInfo.setRegistUser(obejct.getString("registUser"));
		recordInfo.setUploadDate(obejct.getString("uploadDate"));
		recordInfo.setDelFlag(obejct.getString("delFlag"));
		recordList.add(recordInfo);
		return recordList;
	}

	public static List<Object> getQueryRecord(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> stationList = new ArrayList<Object>();
		JSONArray stationJArrary = jsonStationInfo.getJSONArray("stationList");
		for (int i = 0; i < stationJArrary.length(); i++) {
			JSONObject obejct = stationJArrary.getJSONObject(i);
			StationData stationData = new StationData();
			stationData.setDcId(obejct.getString("dcId"));
			stationData.setDcName(obejct.getString("dcName"));
			stationData.setStationCode(obejct.getString("stationCode"));
			stationData.setStationName(obejct.getString("stationName"));
			stationData.setReceiveDate(obejct.getString("receiveDate"));
			stationData.setProductType(obejct.getString("productType"));
			stationData.setCropType(obejct.getString("cropType"));
			stationData.setRecordDate(obejct.getString("recordDate"));
			stationData.setMoveDate(obejct.getString("moveDate"));
			stationData.setActionPerson(obejct.getString("actionPerson"));
			stationData.setSeedDate(obejct.getString("seedDate"));
			stationData.setActionBak(obejct.getString("actionBak"));
			stationList.add(stationData);
		}
		return stationList;
	}

	/**
	 * 通过管理接口获取的信息
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryRecordDataList(
			JSONObject jsonStationInfo,int optionType) throws JSONException {
		List<Object> stationList = new ArrayList<Object>();
		JSONArray stationJsonArr = jsonStationInfo.getJSONArray("dataList");
		JSONArray ploughListJsonArr = jsonStationInfo.getJSONArray("ploughList");

		for (int i = 0; i < stationJsonArr.length(); i++) {
			JSONObject stationJson = stationJsonArr.getJSONObject(i);
			DataListInfo dataListInfo = new DataListInfo();
			if (optionType == 1) {//查询天阳播种
				String ploughId1 = stationJson.getString("ploughId");
					for (int j=0;j<ploughListJsonArr.length();j++){
						JSONObject ploughListJson = ploughListJsonArr.getJSONObject(j);
						ploughCode = ploughListJson.getString("ploughCode");
						ploughId = ploughListJson.getString("ploughId");
						if (ploughId1.equals(ploughId)){
							dataListInfo.setPloughCode(ploughCode);
							dataListInfo.setPloughghIdId(ploughId1);
						}
					}
					dataListInfo.setDetailId(stationJson.getString("detailId"));
					dataListInfo.setSysId(stationJson.getString("sysId"));
					dataListInfo.setProductType(stationJson.getString("productType"));
					dataListInfo.setRecordDate(stationJson.getString("recordDate"));
					dataListInfo.setCropCode(stationJson.getString("cropCode"));
					dataListInfo.setCropType(stationJson.getString("cropType"));
					dataListInfo.setCropPtype(stationJson.getString("cropPtype"));
					dataListInfo.setSeedDate(stationJson.getString("seedDate"));
					dataListInfo.setMoveDate(stationJson.getString("moveDate"));
					dataListInfo.setTemperValue(stationJson.getString("temperValue"));
					dataListInfo.setProductItem(stationJson.getString("productItem"));
					dataListInfo.setReceiveDate(stationJson.getString("receiveDate"));
					dataListInfo.setActionPerson(stationJson.getString("actionPerson"));
					dataListInfo.setActionBak(stationJson.getString("actionBak"));




			} else if (optionType ==2){//田洋农事项目:2
				String ploughId1 = stationJson.getString("ploughId");
				for (int j=0;j<ploughListJsonArr.length();j++){
					JSONObject ploughListJson = ploughListJsonArr.getJSONObject(j);
					ploughCode = ploughListJson.getString("ploughCode");
					ploughId = ploughListJson.getString("ploughId");
					if (ploughId1.equals(ploughId)){
						dataListInfo.setPloughCode(ploughCode);
					}
				}

				dataListInfo.setPloughghIdId(ploughId1);
				dataListInfo.setSysId(stationJson.getString("sysId"));
//				dataListInfo.setPloughCode(stationJson.getString("ploughCode"));
				dataListInfo.setDetailId(stationJson.getString("detailId"));
				dataListInfo.setRecordId(stationJson.getString("recordId"));
				dataListInfo.setReceiveDate(stationJson.getString("receiveDate"));
				dataListInfo.setProductType(stationJson.getString("productType"));
				dataListInfo.setTemperValue(stationJson.getString("temperValue"));
				dataListInfo.setProductItem(stationJson.getString("productItem"));
				dataListInfo.setAgrDesc(stationJson.getString("agrDesc"));
				dataListInfo.setActionPerson(stationJson.getString("actionPerson"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setManureName(stationJson.getString("manureName"));

				dataListInfo.setManureUse(stationJson.getString("manureUse"));
				dataListInfo.setPesticideAmountUnit(stationJson.getString("pesticideAmountUnit"));
				dataListInfo.setManureAmountUnit(stationJson.getString("manureAmountUnit"));

				dataListInfo.setPreventObj(stationJson.getString("preventObj"));
				dataListInfo.setPesticideName(stationJson.getString("pesticideName"));

				dataListInfo.setDiluteScale(stationJson.getString("diluteScale"));
				dataListInfo.setCropState(stationJson.getString("cropState"));

				/*


				dataListInfo.setActionBak(stationJson.getString("productAddr"));
				dataListInfo.setActionBak(stationJson.getString("diluteScale"));
				dataListInfo.setActionBak(stationJson.getString("temperValue"));
				dataListInfo.setActionBak(stationJson.getString("seedDate"));
				dataListInfo.setActionBak(stationJson.getString("cropLevel"));
				dataListInfo.setActionBak(stationJson.getString("pesticideAmountUnit"));
				dataListInfo.setActionBak(stationJson.getString("manureName"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));*/

			}else if (optionType == 5){//田洋收成: 5
				String ploughId1 = stationJson.getString("ploughId");
				for (int j=0;j<ploughListJsonArr.length();j++){
					JSONObject ploughListJson = ploughListJsonArr.getJSONObject(j);
					ploughCode = ploughListJson.getString("ploughCode");
					ploughId = ploughListJson.getString("ploughId");
					if (ploughId1.equals(ploughId)){
						dataListInfo.setPloughCode(ploughCode);
					}
				}

				dataListInfo.setPloughghIdId(ploughId1);

				dataListInfo.setSysId(stationJson.getString("sysId"));
//				dataListInfo.setPloughCode(stationJson.getString("ploughCode"));
				dataListInfo.setDetailId(stationJson.getString("detailId"));
				dataListInfo.setRecordId(stationJson.getString("recordId"));
				dataListInfo.setReceiveDate(stationJson.getString("receiveDate"));
				dataListInfo.setRecordDate(stationJson.getString("recordDate"));
				dataListInfo.setProductType(stationJson.getString("productType"));
				dataListInfo.setCropType(stationJson.getString("cropType"));
				dataListInfo.setReceiveDate(stationJson.getString("receiveDate"));
				dataListInfo.setReceiveWeight(stationJson.getString("receiveWeight"));
				dataListInfo.setCropLevel(stationJson.getString("cropLevel"));
				dataListInfo.setActionPerson(stationJson.getString("actionPerson"));
				dataListInfo.setCropPtype(stationJson.getString("cropPtype"));
				dataListInfo.setActionBak(stationJson.getString("actionBak"));
			}
			stationList.add(dataListInfo);
		}





		return stationList;
	}

	/**
	 * 获取农作物名称
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<CropTypeNameInfo> getCropTypeName(JSONArray jsonStationInfo)
			throws JSONException {
		List<CropTypeNameInfo> cropTypeList = new ArrayList<CropTypeNameInfo>();
		for (int i = 0; i < jsonStationInfo.length(); i++) {
			JSONObject obejct = jsonStationInfo.getJSONObject(i);
			CropTypeNameInfo cropTypeNameInfo = new CropTypeNameInfo();
			cropTypeNameInfo.setDicValue(obejct.getString("dicValue"));
			cropTypeNameInfo.setDicCode(obejct.getString("dicCode"));
			cropTypeNameInfo.setCropPtype(obejct.getString("cropPtype"));
			cropTypeNameInfo.setDicId(obejct.getString("dicId"));
			cropTypeList.add(cropTypeNameInfo);
		}
		return cropTypeList;
	}
	/**
	 * 获取收货信息--区域
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getAddGoodReceiveDc(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dcList = new ArrayList<Object>();
		JSONArray stationJsonArr = jsonStationInfo.getJSONArray("dcList");
		for (int i = 0; i <stationJsonArr.length(); i++) {
			JSONObject obejct = stationJsonArr.getJSONObject(i);
			StationInfo stationInfo = new StationInfo();
			stationInfo.setDcId(obejct.getString("id"));
			stationInfo.setDcName(obejct.getString("name"));
			dcList.add(stationInfo);
		}
		return dcList;
	}
	/**
	 * 获取收货信息--GoodReceive
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static GoodsReceiveInfo getAddGoodReceiveInfo(JSONObject jsonStationInfo)
			throws JSONException {
		GoodsReceiveInfo goodReceiveInfo = new GoodsReceiveInfo();
		/*JSONObject stationJsonArr = jsonStationInfo.JSONObject("goodsReceive");
		goodReceiveInfo.setTime(stationJsonArr.getString("time"));
		goodReceiveInfo.setUserId(stationJsonArr.getString("userId"));
		goodReceiveInfo.setUserName(stationJsonArr.getString("userName"));*/



		return goodReceiveInfo;
	}
	/**
	 * 获取供应商
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryClient(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dcList = new ArrayList<Object>();
		JSONArray stationJsonArr = jsonStationInfo.getJSONArray("clientList");
		for (int i = 0; i <stationJsonArr.length(); i++) {
			JSONObject obe = stationJsonArr.getJSONObject(i);
			JSONObject obejct = obe.getJSONObject("id");
			CnameInfo cnameInfo = new CnameInfo();
			cnameInfo.setId(obejct.getString("id"));
			cnameInfo.setCname(obejct.getString("cname"));
			cnameInfo.setNameSpell(obejct.getString("nameSpell"));
			cnameInfo.setPhone(obejct.getString("phone"));
			cnameInfo.setType(obejct.getString("type"));
			cnameInfo.setDcId(obejct.getString("dcId"));
			dcList.add(cnameInfo);
		}
		return dcList;
	}
	/**
	 * 获取供应商
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryFromulaSettingPages(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dcList = new ArrayList<Object>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("planFromulaList");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			PlanFromulaListInfo planFromulaListInfo  = new PlanFromulaListInfo();
			planFromulaListInfo.setFormId(object.getString("formId"));
			planFromulaListInfo.setAgriName(object.getString("agriName"));
			planFromulaListInfo.setAgriId(object.getString("agriId"));
			planFromulaListInfo.setFormType(object.getString("formType"));

			planFromulaListInfo.setMinSeedCost(object.getString("minSeedCost"));
			planFromulaListInfo.setMaxSeedCost(object.getString("maxSeedCost"));
			planFromulaListInfo.setMaxChemCost(object.getString("maxChemCost"));
			planFromulaListInfo.setMinChemCost(object.getString("minChemCost"));
			planFromulaListInfo.setMaxPickCost(object.getString("maxPickCost"));
			planFromulaListInfo.setMinPickCost(object.getString("minPickCost"));
			planFromulaListInfo.setMaxShorTranCost(object.getString("maxShorTranCost"));
			planFromulaListInfo.setMinShorTranCost(object.getString("minShorTranCost"));
			planFromulaListInfo.setMaxLongTranCost(object.getString("maxLongTranCost"));
			planFromulaListInfo.setMinLongTranCost(object.getString("minLongTranCost"));
			planFromulaListInfo.setMinPackNum(object.getString("minPackNum"));
			planFromulaListInfo.setMaxPackNum(object.getString("maxPackNum"));
			planFromulaListInfo.setMaxPackCost(object.getString("maxPackCost"));
			planFromulaListInfo.setMinPackCost(object.getString("minPackCost"));
			planFromulaListInfo.setMaxPrecCost(object.getString("maxPrecCost"));
			planFromulaListInfo.setMaxProcCost(object.getString("maxProcCost"));
			planFromulaListInfo.setMinProcCost(object.getString("minProcCost"));
			planFromulaListInfo.setMaxManuCost(object.getString("maxManuCost"));
			planFromulaListInfo.setMinManuCost(object.getString("minManuCost"));
			planFromulaListInfo.setMaxPackMateCost(object.getString("maxPackMateCost"));
			planFromulaListInfo.setMinPackMateCost(object.getString("minPackMateCost"));
			planFromulaListInfo.setIsValid(object.getString("isValid"));
			planFromulaListInfo.setDelFlag(object.getString("delFlag"));
			planFromulaListInfo.setMinPrecCost(object.getString("minPrecCost"));

			dcList.add(planFromulaListInfo);

		}
		return dcList;
	}
	public static List<CustomCostListInfo> getQueryFromulaSettingPages_CustomCost(JSONObject jsonStationInfo)
			throws JSONException {
		List<CustomCostListInfo> dcList = new ArrayList<CustomCostListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("customCostList");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			CustomCostListInfo customCostListInfo= new CustomCostListInfo();

			customCostListInfo.setIsValid(object.getString("isValid"));
			customCostListInfo.setCostName(object.getString("costName"));
			customCostListInfo.setCustomId(object.getString("customId"));
			customCostListInfo.setPlanInfoId(object.getString("planInfoId"));
			customCostListInfo.setFormType(object.getString("formType"));
			customCostListInfo.setFormId(object.getString("formId"));
			customCostListInfo.setMinValue(object.getString("minValue"));
			customCostListInfo.setCuCode(object.getString("cuCode"));
			customCostListInfo.setMaxValue(object.getString("maxValue"));

			dcList.add(customCostListInfo);
		}
		return dcList;
	}
	public static List<CustomCostListInfo> getListPlanInfoCostValue(JSONObject jsonStationInfo)
			throws JSONException {
		List<CustomCostListInfo> dcList = new ArrayList<CustomCostListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("listPlanInfoCostValue");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject obj = arrayJson.getJSONObject(i);

			JSONArray array = obj.getJSONArray("listTd");
			for (int j = 0; j <array.length(); j++) {
				JSONObject object = array.getJSONObject(j);

				CustomCostListInfo customCostListInfo= new CustomCostListInfo();

				customCostListInfo.setCustomId(object.getString("customId"));
				customCostListInfo.setPlanInfoId(object.getString("planInfoId"));
				customCostListInfo.setFormType(object.getString("formType"));
				customCostListInfo.setFormId(object.getString("formId"));
				customCostListInfo.setMinValue(object.getString("minValue"));
				customCostListInfo.setCuCode(object.getString("cuCode"));
				customCostListInfo.setIsValid(object.getString("isValid"));
				customCostListInfo.setCostName(object.getString("costName"));
				customCostListInfo.setMaxValue(object.getString("maxValue"));

				dcList.add(customCostListInfo);

			}

		}
		return dcList;
	}
	public static List<CustomCostListInfo> getListPlanInfoCostValue_queryProplan(JSONObject jsonStationInfo)
			throws JSONException {
		List<CustomCostListInfo> dcList = new ArrayList<CustomCostListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("listPlanInfoCostValue");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject obj = arrayJson.getJSONObject(i);

			JSONArray array = obj.getJSONArray("listTd");
			for (int j = 0; j <array.length(); j++) {
				JSONObject object = array.getJSONObject(j);

				CustomCostListInfo customCostListInfo= new CustomCostListInfo();

				customCostListInfo.setPlanAmou(object.getString("planAmou"));
				customCostListInfo.setCustomId(object.getString("customId"));
				customCostListInfo.setPlanInfoId(object.getString("planInfoId"));
				customCostListInfo.setFormType(object.getString("formType"));
				customCostListInfo.setFormId(object.getString("formId"));
				customCostListInfo.setMinValue(object.getString("minValue"));
				customCostListInfo.setCuName(object.getString("cuName"));
				customCostListInfo.setCuCode(object.getString("cuCode"));
				customCostListInfo.setIsValid(object.getString("isValid"));
				customCostListInfo.setCostName(object.getString("costName"));
				customCostListInfo.setMaxValue(object.getString("maxValue"));

				dcList.add(customCostListInfo);

			}

		}
		return dcList;
	}
	/**
	 * 生产计划管理数据查看接口
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<CustomCostListInfo> getDoViewProductionPlan(JSONObject jsonStationInfo)
			throws JSONException {
		List<CustomCostListInfo> dcList = new ArrayList<CustomCostListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("listPi");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			CustomCostListInfo customCostListInfo= new CustomCostListInfo();

			customCostListInfo.setCustomId(object.getString("customId"));
			customCostListInfo.setPlanInfoId(object.getString("planInfoId"));
			customCostListInfo.setFormType(object.getString("formType"));
			customCostListInfo.setFormId(object.getString("formId"));
			customCostListInfo.setMinValue(object.getString("minValue"));
			customCostListInfo.setCuCode(object.getString("cuCode"));
			customCostListInfo.setIsValid(object.getString("isValid"));
			customCostListInfo.setCostName(object.getString("costName"));
			customCostListInfo.setMaxValue(object.getString("maxValue"));
			customCostListInfo.setCuName(object.getString("cuName"));

			dcList.add(customCostListInfo);
		}
		return dcList;
	}
	/**
	 * 成本公式设定接口
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<CustomCostListInfo> getAddFromulaSetting(JSONObject jsonStationInfo,int type)
			throws JSONException {
		List<CustomCostListInfo> dcList = new ArrayList<CustomCostListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("customCostList");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			CustomCostListInfo customCostListInfo= new CustomCostListInfo();
			if (type == 1) {
				customCostListInfo.setChargesCollectable(object.getString("chargesCollectable"));
				customCostListInfo.setUnit(object.getString("unit"));
				customCostListInfo.setIsValid(object.getString("isValid"));
				customCostListInfo.setCid(object.getString("cid"));
			} else {
				customCostListInfo.setChargesCollectable(object.getString("zchargesCollectable"));
				customCostListInfo.setUnit(object.getString("zunit"));
				customCostListInfo.setIsValid(object.getString("zisValid"));
				customCostListInfo.setCid(object.getString("zid"));
			}

			dcList.add(customCostListInfo);
		}
		return dcList;
	}
	public static List<ChargeListInfo> getQueryFromulaSettingPages_ChargeList(JSONObject jsonStationInfo)
			throws JSONException {
		List<ChargeListInfo> dcList = new ArrayList<ChargeListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("chargeList");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			ChargeListInfo chargeListInfo= new ChargeListInfo();

			chargeListInfo.setCuName(object.getString("cuName"));
			chargeListInfo.setCuCode(object.getString("cuCode"));
			chargeListInfo.setDelFlag(object.getString("delFlag"));

			dcList.add(chargeListInfo);
		}
		return dcList;
	}
	public static List<ChargeListInfo> getQueryFromulaSettingPages_ListPi(JSONObject jsonStationInfo)
			throws JSONException {
		List<ChargeListInfo> dcList = new ArrayList<ChargeListInfo>();
		JSONArray arrayJson = jsonStationInfo.getJSONArray("listPi");
		for (int i = 0; i <arrayJson.length(); i++) {
			JSONObject object = arrayJson.getJSONObject(i);
			ChargeListInfo chargeListInfo= new ChargeListInfo();

			chargeListInfo.setCuName(object.getString("cuName"));
			chargeListInfo.setCuCode(object.getString("cuCode"));
			chargeListInfo.setCostName(object.getString("costName"));
			chargeListInfo.setMinValue(object.getString("minValue"));
			chargeListInfo.setMaxValue(object.getString("maxValue"));

			dcList.add(chargeListInfo);
		}
		return dcList;
	}
	public static FromulaListInfo getDoViewProductionPlan_FromulaList(JSONObject jsonStationInfo,String type)
			throws JSONException {
		JSONObject arrayJson = jsonStationInfo.getJSONObject("fromulaList");
		FromulaListInfo fromulaListInfo = new FromulaListInfo();
		fromulaListInfo.setAgriName(arrayJson.getString("agriName"));
		fromulaListInfo.setAgriName(arrayJson.getString("agriId"));
		fromulaListInfo.setAgriName(arrayJson.getString("formType"));

		if ("1".equals(type)) {
			fromulaListInfo.setMaxPackCost(arrayJson.getString("maxPackCost"));
			fromulaListInfo.setMinPackCost(arrayJson.getString("minPackCost"));
			fromulaListInfo.setMaxProcCost(arrayJson.getString("maxProcCost"));
			fromulaListInfo.setMinProcCost(arrayJson.getString("minProcCost"));
			fromulaListInfo.setMaxManuCost(arrayJson.getString("maxManuCost"));
			fromulaListInfo.setMinManuCost(arrayJson.getString("minManuCost"));

		} else {

			fromulaListInfo.setMinSeedCost(arrayJson.getString("minSeedCost"));
			fromulaListInfo.setMaxSeedCost(arrayJson.getString("maxSeedCost"));
			fromulaListInfo.setMaxChemCost(arrayJson.getString("maxChemCost"));
			fromulaListInfo.setMinChemCost(arrayJson.getString("minChemCost"));
			fromulaListInfo.setMaxPickCost(arrayJson.getString("maxPickCost"));
			fromulaListInfo.setMinPickCost(arrayJson.getString("minPickCost"));

		}
		fromulaListInfo.setMaxShorTranCost(arrayJson.getString("maxShorTranCost"));
		fromulaListInfo.setMinShorTranCost(arrayJson.getString("minShorTranCost"));
		fromulaListInfo.setMaxLongTranCost(arrayJson.getString("maxLongTranCost"));
		fromulaListInfo.setMinLongTranCost(arrayJson.getString("minLongTranCost"));

		fromulaListInfo.setMinPackNum(arrayJson.getString("minPackNum"));
		fromulaListInfo.setMaxPackNum(arrayJson.getString("maxPackNum"));
		fromulaListInfo.setMinPrecCost(arrayJson.getString("minPrecCost"));
		fromulaListInfo.setMaxPrecCost(arrayJson.getString("maxPrecCost"));
		fromulaListInfo.setMaxPackMateCost(arrayJson.getString("maxPackMateCost"));
		fromulaListInfo.setMinPackMateCost(arrayJson.getString("minPackMateCost"));


		return fromulaListInfo;
	}
	/**
	 * 收货信息管理数据获取
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<GoodsReceiveInfo> getqueryGoodsReceive(JSONObject jsonStationInfo)
			throws JSONException {
		List<GoodsReceiveInfo> dcList = new ArrayList<GoodsReceiveInfo>();
		JSONArray jArray = jsonStationInfo.getJSONArray("goodsReceiveList");
		for (int i = 0; i <jArray.length(); i++) {
			GoodsReceiveInfo goodsReceiveInfo  =new GoodsReceiveInfo();
			JSONObject obj = jArray.getJSONObject(i);
			goodsReceiveInfo.setReceiveId(obj.getString("receiveId"));
			goodsReceiveInfo.setUserName(obj.getString("userName"));
			goodsReceiveInfo.setUserId(obj.getString("userId"));
			goodsReceiveInfo.setTime(obj.getString("time"));
			goodsReceiveInfo.setAreaName(obj.getString("areaName"));
			goodsReceiveInfo.setAreaId(obj.getString("areaId"));
			goodsReceiveInfo.setReceiveCode(obj.getString("receiveCode"));
			goodsReceiveInfo.setReceiveState(obj.getString("receiveState"));
			goodsReceiveInfo.setDelFlag(obj.getString("delFlag"));
			goodsReceiveInfo.setTotalMoney(obj.getString("totalMoney"));
			goodsReceiveInfo.setPrintState(obj.getString("printState"));
			goodsReceiveInfo.setVendorId(obj.getString("vendorId"));
			goodsReceiveInfo.setVendorName(obj.getString("vendorName"));
			goodsReceiveInfo.setVendorType(obj.getString("vendorType"));
			goodsReceiveInfo.setPurId(obj.getString("purId"));
			goodsReceiveInfo.setUploadState(obj.getString("uploadState"));
			goodsReceiveInfo.setLoadCode(obj.getString("loadCode"));
			goodsReceiveInfo.setRemark(obj.getString("remark"));

			dcList.add(goodsReceiveInfo);
		}
		return dcList;
	}
	/**
	 * 获取采购单号信息
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getAllNotCompleteSheet(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> stationList = new ArrayList<Object>();

		JSONArray stationJArrary = jsonStationInfo.getJSONArray("dataList");

		for (int i = 0; i < stationJArrary.length(); i++) {
			JSONObject obejct = stationJArrary.getJSONObject(i);
			PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
			purchaseOrderInfo.setPurId(obejct.getString("purId"));
			purchaseOrderInfo.setPrintCode(obejct.getString("printCode"));
			stationList.add(purchaseOrderInfo);
		}
		return stationList;
	}
	/**
	 * 生产计划管理列表数据获取
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryProductionPlanPage(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> stationList = new ArrayList<Object>();
		JSONArray stationJArrary = jsonStationInfo.getJSONArray("planList");
		for (int i = 0; i < stationJArrary.length(); i++) {
			JSONObject obejct = stationJArrary.getJSONObject(i);
			PlanListInfo planListInfo = new PlanListInfo();
			planListInfo.setPlanId(obejct.getString("planId"));
			planListInfo.setPlanName(obejct.getString("planName"));
			planListInfo.setPlanType(obejct.getString("planType"));
			planListInfo.setPredStarDate(obejct.getString("predStarDate"));
			planListInfo.setPredEndDate(obejct.getString("predEndDate"));
			planListInfo.setAgriName(obejct.getString("agriName"));
			planListInfo.setAgriId(obejct.getString("agriId"));
			planListInfo.setPlanAmou(obejct.getString("planAmou"));
			planListInfo.setPlanDrawPers(obejct.getString("planDrawPers"));
			planListInfo.setPlanDrawPersId(obejct.getString("planDrawPersId"));
			planListInfo.setPlanDrawDate(obejct.getString("planDrawDate"));

			stationList.add(planListInfo);
		}
		return stationList;
	}
	/**
	 * 生产计划统计分析获取数据列表
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryProductionPlanStatisticsPageInfo(JSONObject jsonStationInfo,int type)
			throws JSONException {
		List<Object> stationList = new ArrayList<Object>();
		JSONArray stationJArrary = jsonStationInfo.getJSONArray("planList");
		for (int i = 0; i < stationJArrary.length(); i++) {
			JSONObject obejct = stationJArrary.getJSONObject(i);
			ProductionPlanStatisticsInfo productionPlanStatisticsInfo= new ProductionPlanStatisticsInfo();
			productionPlanStatisticsInfo.setPlanName(obejct.getString("planName"));
			productionPlanStatisticsInfo.setAgriName(obejct.getString("agriName"));

			productionPlanStatisticsInfo.setMinSeedCost(obejct.getString("minSeedCost"));
			productionPlanStatisticsInfo.setMaxPackNum(obejct.getString("maxPackNum"));
			productionPlanStatisticsInfo.setMaxProcCost(obejct.getString("maxProcCost"));
			productionPlanStatisticsInfo.setMinPackMateCost(obejct.getString("minPackMateCost"));
			productionPlanStatisticsInfo.setMaxPackMateCost(obejct.getString("maxPackMateCost"));
			productionPlanStatisticsInfo.setMaxShorTranCost(obejct.getString("maxShorTranCost"));
			productionPlanStatisticsInfo.setMaxChemCost(obejct.getString("maxChemCost"));
			productionPlanStatisticsInfo.setMaxManuCost(obejct.getString("maxManuCost"));
			productionPlanStatisticsInfo.setMinPackNum(obejct.getString("minPackNum"));
			productionPlanStatisticsInfo.setMinManuCost(obejct.getString("minManuCost"));
			productionPlanStatisticsInfo.setFormId(obejct.getString("formId"));
			productionPlanStatisticsInfo.setMaxLongTranCost(obejct.getString("maxLongTranCost"));
			productionPlanStatisticsInfo.setMinLongTranCost(obejct.getString("minLongTranCost"));
			productionPlanStatisticsInfo.setMaxSeedCost(obejct.getString("maxSeedCost"));
			productionPlanStatisticsInfo.setMinPackCost(obejct.getString("minPackCost"));
			productionPlanStatisticsInfo.setMaxPickCost(obejct.getString("maxPickCost"));
			productionPlanStatisticsInfo.setPlanAmou(obejct.getString("planAmou"));
			productionPlanStatisticsInfo.setMinShorTranCost(obejct.getString("minShorTranCost"));
			productionPlanStatisticsInfo.setMinPickCost(obejct.getString("minPickCost"));
			productionPlanStatisticsInfo.setMinProcCost(obejct.getString("minProcCost"));

			productionPlanStatisticsInfo.setMinChemCost(obejct.getString("minChemCost"));
			productionPlanStatisticsInfo.setMaxPackCost(obejct.getString("maxPackCost"));
			productionPlanStatisticsInfo.setMinPrecCost(obejct.getString("minPrecCost"));
			productionPlanStatisticsInfo.setMaxPrecCost(obejct.getString("maxPrecCost"));

			stationList.add(productionPlanStatisticsInfo);
		}
		return stationList;
	}
	public static List<Object> getQueryPlough(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dataList =new ArrayList<Object>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("ploughList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONArray obejct = ploughListJArrary.getJSONArray(i);
			PloughListInfoArrayInfo ploughListInfoArrayInfo = new PloughListInfoArrayInfo();

			ploughListInfoArrayInfo.setPloughId(obejct.getString(0));
			ploughListInfoArrayInfo.setPloughCode(obejct.getString(1));
			ploughListInfoArrayInfo.setPloughArea(obejct.getString(2));
			ploughListInfoArrayInfo.setStation(obejct.getString(3));
			ploughListInfoArrayInfo.setPlantCrop(obejct.getString(4));
			dataList.add(ploughListInfoArrayInfo);
		}
		return dataList;
	}
	/**
	 * 获取田洋明细信息
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getPloughListDetailInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> ploughList = new ArrayList<Object>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("ploughList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONArray array = ploughListJArrary.getJSONArray(i);

			PloughListDetailInfo ploughListDetailInfo = new PloughListDetailInfo();

			ploughListDetailInfo.setPloughId(array.getString(Integer.parseInt("0")));
			ploughListDetailInfo.setDicId(array.getString(Integer.parseInt("0")));
			ploughListDetailInfo.setPloughCode(array.getString(Integer.parseInt("1")));
			ploughListDetailInfo.setPloughArea(array.getString(Integer.parseInt("2")));
			ploughListDetailInfo.setSoilState(array.getString(Integer.parseInt("3")));
			ploughListDetailInfo.setStationId(array.getString(Integer.parseInt("4")));
			ploughListDetailInfo.setDicCode(array.getString(Integer.parseInt("5")));
			ploughListDetailInfo.setDicValue(array.getString(Integer.parseInt("6")));
			ploughListDetailInfo.setCropPtype(array.getString(Integer.parseInt("7")));

			ploughList.add(ploughListDetailInfo);
		}

		return ploughList;
	}
	/**
	 * 收货信息管理从表数据查看
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<GoodsReceiveDetailListInfo> getGoodReceiveDetailForPackagePrint(JSONObject jsonStationInfo)
			throws JSONException {
		List<GoodsReceiveDetailListInfo> ploughList = new ArrayList<GoodsReceiveDetailListInfo>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("goodsReceiveDetailList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONObject object = ploughListJArrary.getJSONObject(i);

			GoodsReceiveDetailListInfo goodsReceiveDetailListInfo = new GoodsReceiveDetailListInfo();

			goodsReceiveDetailListInfo.setDetailId(object.getString("detailId"));
			goodsReceiveDetailListInfo.setGoodId(object.getString("goodId"));
			goodsReceiveDetailListInfo.setGoodName(object.getString("goodName"));
			goodsReceiveDetailListInfo.setTotalGoodWeight(object.getString("totalGoodWeight"));
			goodsReceiveDetailListInfo.setPackageWeight(object.getString("packageWeight"));
			goodsReceiveDetailListInfo.setGoodPrice(object.getString("goodPrice"));
			goodsReceiveDetailListInfo.setGoodMoney(object.getString("goodMoney"));
			goodsReceiveDetailListInfo.setGoodUnit(object.getString("goodUnit"));
			goodsReceiveDetailListInfo.setReceivePackages(object.getString("receivePackages"));
			goodsReceiveDetailListInfo.setDelFlag(object.getString("delFlag"));
			goodsReceiveDetailListInfo.setCropLevel(object.getString("cropLevel"));
			goodsReceiveDetailListInfo.setCropDirection(object.getString("cropDirection"));
			goodsReceiveDetailListInfo.setProductAddr(object.getString("productAddr"));
			goodsReceiveDetailListInfo.setSysId(object.getString("sysId"));
			goodsReceiveDetailListInfo.setPurId(object.getString("purId"));
			goodsReceiveDetailListInfo.setPurInfoId(object.getString("purInfoId"));
			goodsReceiveDetailListInfo.setReceiveId(object.getString("receiveId"));
			goodsReceiveDetailListInfo.setCurCount(object.getString("curCount"));
			goodsReceiveDetailListInfo.setCurWeight(object.getString("curWeight"));

			ploughList.add(goodsReceiveDetailListInfo);
		}

		return ploughList;
	}
	/**
	 * 采购单对应的详细信息
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static PurchaseInfo getAllPurchaseInfo(JSONObject jsonStationInfo)
			throws JSONException {
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		JSONArray infosJArrary = jsonStationInfo
				.getJSONArray("infos");
		List<PurchasePartOfInfo> urchasePartOfInfoList = new ArrayList<PurchasePartOfInfo>();
		for (int i = 0; i < infosJArrary.length(); i++) {
			JSONObject object = infosJArrary.getJSONObject(i);
			PurchasePartOfInfo purchasePartOfInfo = new PurchasePartOfInfo();
			purchasePartOfInfo.setPurId(object.getString("purId"));
			purchasePartOfInfo.setPurInfoId(object.getString("purInfoId"));
			purchasePartOfInfo.setGoodId(object.getString("goodId"));
			purchasePartOfInfo.setGoodName(object.getString("goodName"));
			purchasePartOfInfo.setGoodNumber(object.getString("goodNumber"));
			purchasePartOfInfo.setGoodUnit(object.getString("goodUnit"));
			purchasePartOfInfo.setPackageWeight(object.getString("packageWeight"));
			purchasePartOfInfo.setTotalGoodWeight(object.getString("totalGoodWeight"));
			purchasePartOfInfo.setGoodPrice(object.getString("goodPrice"));

			urchasePartOfInfoList.add(purchasePartOfInfo);
		}
		purchaseInfo.setProviderId(jsonStationInfo.getString("providerId"));
		purchaseInfo.setProviderName(jsonStationInfo.getString("providerName"));
		purchaseInfo.setUrchasePartOfInfoList(urchasePartOfInfoList);

		return purchaseInfo;
	}
	/**
	 * 获取生产履历统计信息
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getStatisticsCropInfo(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dcList = new ArrayList<Object>();
		JSONArray stationJsonArr = jsonStationInfo.getJSONArray("dataList");
		for (int i = 0; i <stationJsonArr.length(); i++) {
			JSONArray obejct = stationJsonArr.getJSONArray(i);
			StatisticsCropInfo statisticsCropInfo = new StatisticsCropInfo();
			statisticsCropInfo.setAcode(obejct.getString(0));
			statisticsCropInfo.setCrop_type(obejct.getString(1));
			statisticsCropInfo.setPlougharea(obejct.getString(2));
			statisticsCropInfo.setReceiveweight(obejct.getString(3));

			dcList.add(statisticsCropInfo);
		}
		return dcList;
	}
	/**
	 * 获取服务站数据列表
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryServerStation(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dcList = new ArrayList<Object>();
		JSONArray stationJsonArr = jsonStationInfo.getJSONArray("dataList");
		for (int i = 0; i <stationJsonArr.length(); i++) {
			JSONObject obejct = stationJsonArr.getJSONObject(i);

			BaseStationInfo baseStationInfo = new BaseStationInfo();
			baseStationInfo.setDcId(obejct.getString("dcId"));
			baseStationInfo.setCreateTime(obejct.getString("createTime"));
			baseStationInfo.setTechnicianAmount(obejct.getString("technicianAmount"));
			baseStationInfo.setFarmAmount(obejct.getString("farmAmount"));
			baseStationInfo.setStationCode(obejct.getString("stationCode"));
			baseStationInfo.setDcName(obejct.getString("dcName"));
			baseStationInfo.setStationBak(obejct.getString("stationBak"));
			baseStationInfo.setPostCode(obejct.getString("postCode"));
			baseStationInfo.setPlantingArea(obejct.getString("plantingArea"));
			baseStationInfo.setStationId(obejct.getString("stationId"));
			baseStationInfo.setStationLon(obejct.getString("stationLon"));
			baseStationInfo.setStationLat(obejct.getString("stationLat"));
			baseStationInfo.setManagerPicture(obejct.getString("managerPicture"));
			baseStationInfo.setSoilState(obejct.getString("soilState"));
			baseStationInfo.setStationManager(obejct.getString("stationManager"));
			baseStationInfo.setStationAddr(obejct.getString("stationAddr"));
			baseStationInfo.setManagerSpell(obejct.getString("managerSpell"));
			baseStationInfo.setManagerPhone(obejct.getString("managerPhone"));
			baseStationInfo.setUName(obejct.getString("UName"));
			baseStationInfo.setCropTypes(obejct.getString("cropTypes"));
			baseStationInfo.setStationName(obejct.getString("stationName"));
			baseStationInfo.setManagerResume(obejct.getString("managerResume"));
			baseStationInfo.setIsRegisted(obejct.getString("isRegisted"));

			dcList.add(baseStationInfo);
		}
		return dcList;
	}
	public static List<Object> getQueryBasePlough(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dataList =new ArrayList<Object>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("ploughList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONArray obejct = ploughListJArrary.getJSONArray(i);
			PloughListInfoArrayInfo ploughListInfoArrayInfo = new PloughListInfoArrayInfo();

			ploughListInfoArrayInfo.setPloughId(obejct.getString(0));
			ploughListInfoArrayInfo.setPloughCode(obejct.getString(1));
			ploughListInfoArrayInfo.setPloughArea(obejct.getString(2));
			ploughListInfoArrayInfo.setStation(obejct.getString(3));
			ploughListInfoArrayInfo.setPlantCrop(obejct.getString(4));
			dataList.add(ploughListInfoArrayInfo);

		}
		return dataList;
	}
	public static EditPloughPageInfo getEditPloughPage(JSONObject jsonInfo) {
		EditPloughPageInfo editPloughPageInfo = new EditPloughPageInfo();
		try {
			JSONObject object = jsonInfo.getJSONObject("plough");
			editPloughPageInfo.setPloughId(object.getString("ploughId"));
			editPloughPageInfo.setPloughName(object.getString("ploughName"));
			editPloughPageInfo.setPloughArea(object.getString("ploughArea"));
			editPloughPageInfo.setPloughCode(object.getString("ploughCode"));
			editPloughPageInfo.setStationId(object.getString("stationId"));
			editPloughPageInfo.setSoilState(object.getString("soilState"));
			editPloughPageInfo.setPloughFarm(object.getString("ploughFarm"));
			editPloughPageInfo.setPloughPoints(object.getString("ploughPoints"));
			editPloughPageInfo.setDelFlag(object.getString("delFlag"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return editPloughPageInfo;
	}
	/**
	 * 田洋信息农事记录
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getQueryAllRecord(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dataList =new ArrayList<Object>();
		JSONArray ploughListJArrary = jsonStationInfo
				.getJSONArray("dataList");
		for (int i = 0; i < ploughListJArrary.length(); i++) {
			JSONObject obejct = ploughListJArrary.getJSONObject(i);
			AgriRecordTianYnagInfo agriRecordTianYnagInfo = new AgriRecordTianYnagInfo();

			agriRecordTianYnagInfo.setRecordId(obejct.getString("recordId"));
			agriRecordTianYnagInfo.setReceiveDate(obejct.getString("receiveDate"));
			agriRecordTianYnagInfo.setActionBak(obejct.getString("actionBak"));
			agriRecordTianYnagInfo.setSeedDate(obejct.getString("seedDate"));
			agriRecordTianYnagInfo.setCropPtype(obejct.getString("cropPtype"));
			agriRecordTianYnagInfo.setProductType(obejct.getString("productType"));
			agriRecordTianYnagInfo.setRecordDate(obejct.getString("recordDate"));
			agriRecordTianYnagInfo.setCropCode(obejct.getString("cropCode"));
			agriRecordTianYnagInfo.setPloughCode(obejct.getString("ploughCode"));
			agriRecordTianYnagInfo.setMoveDate(obejct.getString("moveDate"));
			agriRecordTianYnagInfo.setPloughId(obejct.getString("ploughId"));
			agriRecordTianYnagInfo.setCropType(obejct.getString("cropType"));
			agriRecordTianYnagInfo.setActionPerson(obejct.getString("actionPerson"));
			agriRecordTianYnagInfo.setDetailId(obejct.getString("detailId"));
			agriRecordTianYnagInfo.setPhasesId(obejct.getString("phasesId"));

			dataList.add(agriRecordTianYnagInfo);
		}
		return dataList;
	}
	public static PlanInfo getAddProductionPlanForProcess(JSONObject jsonStationInfo)
			throws JSONException {
		PlanInfo planInfo = new PlanInfo();
		JSONObject arrayJson = jsonStationInfo.getJSONObject("plan");

		planInfo.setPlanId(arrayJson.getString("planId"));
		planInfo.setPlanName(arrayJson.getString("planName"));
		planInfo.setPlanType(arrayJson.getString("planType"));
		planInfo.setPredStarDate(arrayJson.getString("predStarDate"));
		planInfo.setPredEndDate(arrayJson.getString("predEndDate"));
		planInfo.setAgriName(arrayJson.getString("agriName"));
		planInfo.setAgriId(arrayJson.getString("agriId"));
		planInfo.setPlanAmou(arrayJson.getString("planAmou"));
		planInfo.setPlanDrawPers(arrayJson.getString("planDrawPers"));
		planInfo.setPlanDrawPersId(arrayJson.getString("planDrawPersId"));
		planInfo.setPlanDrawDate(arrayJson.getString("planDrawDate"));
		planInfo.setDelFlag(arrayJson.getString("delFlag"));

		return planInfo;
	}
	/**
	 * 加工量/种植量费用接口（生产计划定义
	 *
	 * @param jsonStationInfo
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getAjaxQueryFromulaValue(JSONObject jsonStationInfo)
			throws JSONException {
		List<Object> dataList = new ArrayList<Object>();
		CustomProductionPlanInfo customProductionPlanInfo = new CustomProductionPlanInfo();
		customProductionPlanInfo.setPlanAmou(jsonStationInfo.getString("planAmou"));
		customProductionPlanInfo.setMaxPackNum(jsonStationInfo.getString("maxPackNum"));
		customProductionPlanInfo.setMinShorTranCost(jsonStationInfo.getString("minShorTranCost"));
		customProductionPlanInfo.setMaxProcCost(jsonStationInfo.getString("maxProcCost"));
		customProductionPlanInfo.setMaxPackMateCost(jsonStationInfo.getString("maxPackMateCost"));
		customProductionPlanInfo.setMinPackMateCost(jsonStationInfo.getString("minPackMateCost"));
		customProductionPlanInfo.setMaxShorTranCost(jsonStationInfo.getString("maxShorTranCost"));
		customProductionPlanInfo.setAllMaxMoney(jsonStationInfo.getString("allMaxMoney"));
		customProductionPlanInfo.setMaxManuCost(jsonStationInfo.getString("maxManuCost"));
		customProductionPlanInfo.setMinProcCost(jsonStationInfo.getString("minProcCost"));
		customProductionPlanInfo.setMinPackNum(jsonStationInfo.getString("minPackNum"));
		customProductionPlanInfo.setMinManuCost(jsonStationInfo.getString("minManuCost"));
		customProductionPlanInfo.setMaxLongTranCost(jsonStationInfo.getString("maxLongTranCost"));
		customProductionPlanInfo.setMinLongTranCost(jsonStationInfo.getString("minLongTranCost"));
		customProductionPlanInfo.setMinPrecCost(jsonStationInfo.getString("minPrecCost"));
		customProductionPlanInfo.setMaxPackCost(jsonStationInfo.getString("maxPackCost"));
		customProductionPlanInfo.setMinPackCost(jsonStationInfo.getString("minPackCost"));
		customProductionPlanInfo.setMaxPrecCost(jsonStationInfo.getString("maxPrecCost"));
		customProductionPlanInfo.setAllMinMoney(jsonStationInfo.getString("allMinMoney"));

		JSONArray listpiArray = jsonStationInfo.getJSONArray("listPi");
		List<CustomCostListInfo> customCostList = new ArrayList<CustomCostListInfo>();
		for (int i = 0; i <listpiArray.length(); i++) {
			JSONObject obejct = listpiArray.getJSONObject(i);
			CustomCostListInfo customCostListInfo = new CustomCostListInfo();
			customCostListInfo.setCustomId(obejct.getString("customId"));
			customCostListInfo.setPlanInfoId(obejct.getString("planInfoId"));
			customCostListInfo.setFormType(obejct.getString("formType"));
			customCostListInfo.setFormId(obejct.getString("formId"));
			customCostListInfo.setMinValue(obejct.getString("minValue"));
			customCostListInfo.setCuName(obejct.getString("cuName"));
			customCostListInfo.setCuCode(obejct.getString("cuCode"));
			customCostListInfo.setIsValid(obejct.getString("isValid"));
			customCostListInfo.setCostName(obejct.getString("costName"));
			customCostListInfo.setMaxValue(obejct.getString("maxValue"));
			customCostList.add(customCostListInfo);
		}

		customProductionPlanInfo.setCustomCostList(customCostList);
		dataList.add(customProductionPlanInfo);
		return dataList;
	}
	public static UpdateDetailInfo getUpdateRecordPage(JSONObject jsonStationInfo)
			throws JSONException {
		UpdateDetailInfo planInfo = new UpdateDetailInfo();
		JSONObject arrayJson = jsonStationInfo.getJSONObject("detail");

		planInfo.setRecordDate(arrayJson.getString("recordDate"));
		planInfo.setWeatherState(arrayJson.getString("weatherState"));
		planInfo.setActionBak(arrayJson.getString("actionBak"));

		return planInfo;
	}
	public static MapServerInfo getEditServerStationPage(JSONObject jsonStationInfo)
			throws JSONException {
		MapServerInfo planInfo = new MapServerInfo();
		JSONObject arrayJson = jsonStationInfo.getJSONObject("mapServer");

		planInfo.setManagerPicture(arrayJson.getString("managerPicture"));

		return planInfo;
	}
	public static String getViewServerStation(JSONObject jsonStationInfo)
			throws JSONException {
		MapServerInfo planInfo = new MapServerInfo();
		String fileName = jsonStationInfo.getString("fileName");

		return fileName;
	}

}
