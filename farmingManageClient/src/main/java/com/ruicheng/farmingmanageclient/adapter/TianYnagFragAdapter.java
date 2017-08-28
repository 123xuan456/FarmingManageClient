package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import com.ruicheng.farmingmanageclient.fragment.TianYnagDailyFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TianYnagFragAdapter extends FragmentPagerAdapter {

	private List<Fragment> listFrag ;
	
	public TianYnagFragAdapter(FragmentManager fm,List<Fragment> listFrag) {
		super(fm);
		this.listFrag = listFrag;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return listFrag.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFrag.size();
	}

}
