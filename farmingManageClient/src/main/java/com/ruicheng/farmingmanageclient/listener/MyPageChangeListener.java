package com.ruicheng.farmingmanageclient.listener;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class MyPageChangeListener implements OnPageChangeListener {

	private TextView tv_bozhongyizaidengji,tv_bozhongyizaiguanli ,tv_navigation_one,tv_navigation_two;
	public MyPageChangeListener(TextView tv_bozhongyizaidengji,TextView tv_bozhongyizaiguanli ,TextView tv_navigation_one,TextView tv_navigation_two){
		this.tv_bozhongyizaidengji = tv_bozhongyizaidengji;
		this.tv_bozhongyizaiguanli = tv_bozhongyizaiguanli;
		this.tv_navigation_one = tv_navigation_one;
		this.tv_navigation_two = tv_navigation_two;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 0) {
			tv_bozhongyizaidengji.setTextColor(Color.BLUE);
			tv_navigation_one.setBackgroundColor(Color.BLUE);
			tv_bozhongyizaiguanli.setTextColor(Color.BLACK);
			tv_navigation_two.setBackgroundColor(Color.BLACK);
		} else {
			tv_bozhongyizaidengji.setTextColor(Color.BLACK);
			tv_navigation_one.setBackgroundColor(Color.BLACK);
			tv_bozhongyizaiguanli.setTextColor(Color.BLUE);
			tv_navigation_two.setBackgroundColor(Color.BLUE);
		}
	}

}
