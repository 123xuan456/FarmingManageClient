package com.ruicheng.farmingmanageclient.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ruicheng.farmingmanageclient.R;
import com.ruicheng.farmingmanageclient.adapter.NumericWheelAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;


/**
 *@Description:选择日期组件
 *@Author:杨攀
 *@Since:2015年1月5日下午3:25:17
 */
public class SelectDateTimePopWin extends PopupWindow implements OnClickListener {

    public static final String PATTERN_YM    = "yyyy-MM";
    public static final String PATTERN_YMD   = "yyyy-MM-dd";
    public static final String PATTERN_YMDH = "yyyy-MM-dd HH:mm";

    private SimpleDateFormat sdf_ym;
    private SimpleDateFormat sdf_ymd;
    private SimpleDateFormat sdf_ymdhm;

    private Activity            mContext;
    private View                mMenuView;
    private ViewFlipper         viewfipper;
    private Button              btn_submit, btn_cancel;
    private String              mCurDate;
    private DateNumericAdapter  monthAdapter, dayAdapter, yearAdapter, hourAdapter, minuteAdapter;
    private WheelView           year, month, day, hour, minute;
    private int                 mCurYear, mCurMonth, mCurDay, mCurHour, mCurMinute;
    private String[]            dateType      = new String[] { "年", "月", "日", "时", "分"};
    private int                 interval      = 80; // 设置间隔为 20 年，即：显示输入年份的上、下 20 年
    private int                 start_year;

    /**
     *<p>Description: 初始化 日期</p>
     * @param context
     * @param datetime 显示的初始日期
     * @param winParent 选择日期win的父界面
     * @param pattern 日期格式
     */
    public SelectDateTimePopWin(Context context, String datetime, View winParent, String pattern) {
        super (context);
        mContext = (Activity) context;
        // 初始日期
        if (null == datetime || "".equals (datetime)) {
            datetime = getCurrentDateTime ();
        }
        this.mCurDate = datetime;

        if (PATTERN_YM.equals (pattern)) {
            createDateYMPopWin ();
        } else if (PATTERN_YMDH.equals (pattern)) {
            createDateYMDHMPopWin ();
        } else {
            createDateYMDPopWin ();
        }

        btn_submit = (Button) mMenuView.findViewById (R.id.submit);
        btn_cancel = (Button) mMenuView.findViewById (R.id.cancel);
        btn_submit.setOnClickListener (this);
        btn_cancel.setOnClickListener (this);

        viewfipper.addView (mMenuView);
        viewfipper.setFlipInterval (6000000);
        this.setContentView (viewfipper);
        this.setWidth (LayoutParams.MATCH_PARENT);
        this.setHeight (LayoutParams.WRAP_CONTENT);
        this.setFocusable (true);
        ColorDrawable dw = new ColorDrawable (0x00000000);
        this.setBackgroundDrawable (dw);
        this.update ();

        showAtLocation (winParent, Gravity.BOTTOM, 0, 0);
    }


    /**
     *@Description: 创建 年月 的选择控件  yyyy-MM
     *@Author:杨攀
     *@Since: 2015年1月5日下午3:27:28
     */
    private void createDateYMPopWin(){

        sdf_ym = new SimpleDateFormat ("yyyy-MM", Locale.getDefault ());

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate (R.layout.wheel_date_ym, null);
        viewfipper = new ViewFlipper (mContext);
        viewfipper.setLayoutParams (new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

        year = (WheelView) mMenuView.findViewById (R.id.year);
        month = (WheelView) mMenuView.findViewById (R.id.month);

        Date date_time = parseDateTime (mCurDate, "yyyy-MM");
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (date_time);
        //初始化
        mCurYear = calendar.get (Calendar.YEAR);
        mCurMonth = calendar.get (Calendar.MONDAY);

        OnWheelChangedListener listener = new OnWheelChangedListener (){
            @Override
            public void onChanged(WheelView wheel,int oldValue,int newValue){
                changeYmListener(year, month);
            }

        };

        //初始化年
        initYear (listener);
        //初始化月
        initMonth (listener);

    }


    /**
     *@Description: changeYmListener
     *@Author:杨攀
     *@Since: 2015年1月5日下午3:46:38
     *@param year
     *@param month
     */
    private void changeYmListener(WheelView year,WheelView month) {
        Calendar calendar = Calendar.getInstance ();
        calendar.set (Calendar.YEAR, year.getCurrentItem () + start_year);
        calendar.set (Calendar.MONTH, month.getCurrentItem ());
        //返回日期
        mCurDate = sdf_ym.format (calendar.getTime ());
    }


    /**
     *@Description: yyyy-MM-dd
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:00:59
     */
    private void createDateYMDPopWin(){
        sdf_ymd = new SimpleDateFormat ("yyyy-MM-dd", Locale.getDefault ());

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate (R.layout.wheel_date_ymd, null);
        viewfipper = new ViewFlipper (mContext);
        viewfipper.setLayoutParams (new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

        year = (WheelView) mMenuView.findViewById (R.id.year);
        month = (WheelView) mMenuView.findViewById (R.id.month);
        day = (WheelView) mMenuView.findViewById (R.id.day);

        Date date_time = parseDateTime (mCurDate, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (date_time);
        //初始化
        mCurYear = calendar.get (Calendar.YEAR);
        mCurMonth = calendar.get (Calendar.MONDAY);
        mCurDay = calendar.get (Calendar.DAY_OF_MONTH);

        OnWheelChangedListener listener = new OnWheelChangedListener (){
            @Override
            public void onChanged(WheelView wheel,int oldValue,int newValue){
                changeYmdListener (year, month, day);
            }

        };

        //初始化年
        initYear (listener);
        //初始化month
        initMonth (listener);
        //day
        initDay(listener);
    }


    /**
     *@Description: 初始化  年
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:06:39
     *@param listener
     */
    private void initYear(OnWheelChangedListener listener){
        start_year = mCurYear - interval;
        yearAdapter = new DateNumericAdapter (mContext, start_year, mCurYear + interval);
        yearAdapter.setTextType (dateType[0]);
        year.setViewAdapter (yearAdapter);
        year.setCurrentItem (mCurYear - start_year);
        year.addChangingListener (listener);
    }

    /**
     *@Description: 初始化 月
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:06:47
     *@param listener
     */
    private void initMonth(OnWheelChangedListener listener){
        monthAdapter = new DateNumericAdapter (mContext, 1, 12);
        monthAdapter.setTextType (dateType[1]);
        month.setViewAdapter (monthAdapter);
        month.setCurrentItem (mCurMonth);
        month.addChangingListener (listener);
    }

    /**
     *@Description: 初始化 天
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:08:22
     *@param listener
     */
    private void initDay(OnWheelChangedListener listener){
        changeYmdListener (year, month, day);
        day.setCurrentItem (mCurDay - 1);
        changeYmdListener (year, month, day);
        day.addChangingListener (listener);
    }

    /**
     *@Description: 初始化 天 yyyy-MM-dd HH:mm
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:08:22
     *@param listener
     */
    private void initDayByYMDHM(OnWheelChangedListener listener){
        changeYmdhmListener (year, month, day, hour, minute);
        day.setCurrentItem (mCurDay - 1);
        changeYmdhmListener (year, month, day, hour, minute);
        day.addChangingListener (listener);
    }

    /**
     *@Description: changeYmdListener
     *@Author:杨攀
     *@Since: 2015年1月5日下午3:59:31
     *@param year
     *@param month
     *@param day
     */
    private void changeYmdListener(WheelView year,WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance ();
        calendar.set (Calendar.YEAR, year.getCurrentItem () + start_year);//当前下标+开始年份
        calendar.set (Calendar.MONTH, month.getCurrentItem ());
        /* 为了计算当前月 的最大天数 ，避免闰年、大月、小月   */
        calendar.set (Calendar.DATE, 1);//设置为当前月第一天
        calendar.roll (Calendar.DATE, -1);//设置为当前月最后一天
        int maxDays = calendar.get (Calendar.DATE);//获取最大天数

        dayAdapter = new DateNumericAdapter (mContext, 1,  maxDays);
        dayAdapter.setTextType (dateType[2]);
        day.setViewAdapter (dayAdapter);
        //日期是从1 开始，下标是从 0 开始
        int curDay = Math.min (maxDays, day.getCurrentItem () + 1);
        day.setCurrentItem (curDay - 1, true);

        calendar.set (Calendar.DAY_OF_MONTH, day.getCurrentItem () + 1);
        //返回日期
        mCurDate = sdf_ymd.format (calendar.getTime ());
    }

    /**
     *@Description: yyyy-MM-dd HH:mm
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:24:05
     */
    private void createDateYMDHMPopWin(){
        sdf_ymdhm = new SimpleDateFormat ("yyyy-MM-dd HH:mm", Locale.getDefault ());

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate (R.layout.wheel_date_ymdhm, null);
        viewfipper = new ViewFlipper (mContext);
        viewfipper.setLayoutParams (new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

        year = (WheelView) mMenuView.findViewById (R.id.year);
        month = (WheelView) mMenuView.findViewById (R.id.month);
        day = (WheelView) mMenuView.findViewById (R.id.day);
        hour = (WheelView) mMenuView.findViewById (R.id.hour);
        minute = (WheelView) mMenuView.findViewById (R.id.minute);

        Date date_time = parseDateTime (mCurDate, "yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (date_time);
        //初始化
        mCurYear = calendar.get (Calendar.YEAR);
        mCurMonth = calendar.get (Calendar.MONDAY);
        mCurDay = calendar.get (Calendar.DAY_OF_MONTH);
        mCurHour = calendar.get (Calendar.HOUR_OF_DAY);
        mCurMinute = calendar.get (Calendar.MINUTE);

        OnWheelChangedListener listener = new OnWheelChangedListener (){
            @Override
            public void onChanged(WheelView wheel,int oldValue,int newValue){
                changeYmdhmListener (year, month, day, hour, minute);
            }

        };

        //初始化年
        initYear (listener);
        //初始化month
        initMonth (listener);
        //day
        initDayByYMDHM(listener);

        //hour
        hourAdapter = new DateNumericAdapter (mContext, 0, 23);
        hourAdapter.setTextType (dateType[3]);
        hour.setViewAdapter (hourAdapter);
        hour.setCurrentItem (mCurHour);
        hour.addChangingListener (listener);

        //minute
        minuteAdapter = new DateNumericAdapter (mContext, 0, 59);
        minuteAdapter.setTextType (dateType[4]);
        minute.setViewAdapter (minuteAdapter);
        minute.setCurrentItem (mCurMinute);
        minute.addChangingListener (listener);
    }


    /**
     *@Description: changeYmdhmListener
     *@Author:杨攀
     *@Since: 2015年1月5日下午4:31:38
     *@param year
     *@param month
     *@param day
     *@param hour
     *@param minute
     */
    private void changeYmdhmListener(WheelView year,WheelView month, WheelView day, WheelView hour, WheelView minute){
        Calendar calendar = Calendar.getInstance ();
        calendar.set (Calendar.YEAR, year.getCurrentItem () + start_year);//当前下标+开始年份
        calendar.set (Calendar.MONTH, month.getCurrentItem ());
        calendar.set (Calendar.HOUR_OF_DAY, hour.getCurrentItem ());
        calendar.set (Calendar.MINUTE, minute.getCurrentItem ());

        /* 为了计算当前月 的最大天数 ，避免闰年、大月、小月   */
        calendar.set (Calendar.DATE, 1);//设置为当前月第一天
        calendar.roll (Calendar.DATE, -1);//设置为当前月最后一天
        int maxDays = calendar.get (Calendar.DATE);//获取最大天数

        dayAdapter = new DateNumericAdapter (mContext, 1,  maxDays);
        dayAdapter.setTextType (dateType[2]);
        day.setViewAdapter (dayAdapter);
        //日期是从1 开始，下标是从 0 开始
        int curDay = Math.min (maxDays, day.getCurrentItem () + 1);
        day.setCurrentItem (curDay - 1, true);

        calendar.set (Calendar.DAY_OF_MONTH, day.getCurrentItem () + 1);
        //返回日期
        mCurDate = sdf_ymdhm.format (calendar.getTime ());
    }

    @Override
    public void showAtLocation(View parent,int gravity,int x,int y){
        setAnimationStyle (R.style.wheel_dialogAnimation);
        super.showAtLocation (parent, gravity, x, y);
        viewfipper.startFlipping ();
    }


    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {

        /*
         * // Index of current item int currentItem; // Index of item to be
         * highlighted int currentValue;
         */

        public DateNumericAdapter(Context context, int minValue, int maxValue) {
            super (context, minValue, maxValue);
            setTextSize (24);
        }

        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super (context, minValue, maxValue);
            // this.currentValue = current;
            setTextSize (24);
        }



        protected void configureTextView(TextView view){
            super.configureTextView (view);
            view.setTypeface (Typeface.SANS_SERIF);
        }

        public CharSequence getItemText(int index){
            // currentItem = index;
            return super.getItemText (index);
        }

    }

    public void onClick(View v){
        switch (v.getId ()) {
            case R.id.submit:
                returnDate (mCurDate);
                break;

            default:
                break;
        }
        this.dismiss ();
    }

    /**
     * 返回日期
     *
     * @param date
     */
    public void returnDate(String date){

    }

    /**
     *@Description: 得到"yyyy-MM-dd"格式的现在时间
     *@Author:杨攀
     *@Since: 2015年1月5日下午2:58:24
     *@return
     */
    private static String getCurrentDate(){
        // 格式
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd",Locale.getDefault ());
        String date = df.format (new Date ());
        return date;
    }

    /**
     *@Description: 得到"yyyy-MM-dd HH:mm:ss"格式的现在时间
     *@Author:杨攀
     *@Since: 2015年1月6日下午2:47:09
     *@return
     */
    private static String getCurrentDateTime(){
        // 格式
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm",Locale.getDefault ());
        String date = df.format (new Date ());
        return date;
    }

    /**
     *@Description: 转为 日期时间
     *@Author:杨攀
     *@Since: 2014年12月31日上午8:58:21
     *@param datetime
     *@return
     */
    private static Date parseDateTime(String datetime,String pattern){
        try {
            DateFormat df = new SimpleDateFormat (pattern, Locale.getDefault ());
            return df.parse (datetime);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return null;
    }



}
