package com.ruicheng.farmingmanageclient.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 绍轩 on 2017/9/14.
 */

public class PublicWay {
    //存放所有的list在最后退出时一起关闭
    public static List<Activity> activityList = new ArrayList<Activity>();
    public static void finish(){
        for (int i = 0; i < PublicWay.activityList.size(); i++) {
            if (null != PublicWay.activityList.get(i)) {
                // 关闭存放在activityList集合里面的所有activity
                PublicWay.activityList.get(i).finish();
            }
        }
    }

}
