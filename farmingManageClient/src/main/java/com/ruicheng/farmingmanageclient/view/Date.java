package com.ruicheng.farmingmanageclient.view;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

public class Date {
	private static int mYear;  
    private static int mMonth;  
    private static int mDay;
    private static final int DATE_DIALOG_ID = 1;  
    
    private static final int SHOW_DATAPICK = 0;
    private static Context context ;
    private static TextView tvShowData ;
    
    public Date(Context context,TextView tvShowData){
    	this.context = context;
    	this.tvShowData = tvShowData;
    }
    
    public static void getDate(){
    	final Calendar c = Calendar.getInstance();  
  	  
	       mYear = c.get(Calendar.YEAR);  
	  
	       mMonth = c.get(Calendar.MONTH);  
	  
	       mDay = c.get(Calendar.DAY_OF_MONTH);
	       
	       
    }
    public static void setDateTime() {  
		  
	       final Calendar c = Calendar.getInstance();  
	  
	       mYear = c.get(Calendar.YEAR);  
	  
	       mMonth = c.get(Calendar.MONTH);  
	  
	       mDay = c.get(Calendar.DAY_OF_MONTH);  
	  
	   
	  
	       updateDisplay();  
	  
	    }  
    public static void updateDisplay() {  
  
       tvShowData.setText(new StringBuilder().append(mYear).append(  
  
              (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(  
  
              (mDay < 10) ? "0" + mDay : mDay));  
  
    }
    public static DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
  	  
	       public void onDateSet(DatePicker view, int year, int monthOfYear,  
	  
	              int dayOfMonth) {  
	  
	           mYear = year;  
	  
	           mMonth = monthOfYear;  
	  
	           mDay = dayOfMonth;  
	  
	           updateDisplay();  
	  
	       }  
	  
	    };  
		  
	    public static  Dialog onCreateDialog(int id) {  
	  
	       switch (id) {  
	  
	       case DATE_DIALOG_ID:  
	  
	           return new DatePickerDialog(context, mDateSetListener, mYear, mMonth,  
	  
	                  mDay);  
	       }  
	       return null;  
	    }  
	    public static void  onPrepareDialog(int id, Dialog dialog) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
	           break;  
	       }  
	  
	    }  
}
