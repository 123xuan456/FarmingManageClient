package com.ruicheng.farmingmanageclient.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruicheng.farmingmanageclient.bean.AcquireAllDcInfo;

public class LoginInfoHelper {
	
	private MySQLiteOpenHelper openHelper;
	
	public LoginInfoHelper(Context context) {
		openHelper = new MySQLiteOpenHelper(context);
		
	}
	public void saveLoginfo(List<Object> listAll){
		SQLiteDatabase database = openHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 0; i <listAll.size(); i++) {
			values.put("dcId", ((AcquireAllDcInfo)listAll.get(i)).getDcId());
			values.put("dcName", ((AcquireAllDcInfo)listAll.get(i)).getDcName());
			database.insert("loginfo", null, values);
		}
		database.close();
//		LoginInfo l = selectLoginfo("123456");
	}
	
	public List<Object> selectLoginfo(){
		SQLiteDatabase database = openHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery("select * from loginfo" , null);
		List<Object> listAll = new ArrayList<Object>();
		while (cursor.moveToNext()) {
			AcquireAllDcInfo loginInfo = new AcquireAllDcInfo();
			loginInfo.setDcId(cursor.getString(cursor.getColumnIndex("dcId")));
			loginInfo.setDcName(cursor.getString(cursor.getColumnIndex("dcName")));
			listAll.add(loginInfo);
		}
		cursor.close();
		database.close();
		return listAll;
	}
	
	public void deleteLoginInfo(){
		SQLiteDatabase database = openHelper.getWritableDatabase();
		database.delete("loginfo", null, null);
		database.close();
	}
}
