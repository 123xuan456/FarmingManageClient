package com.ruicheng.farmingmanageclient.adapter;

import java.util.List;

import com.ruicheng.farmingmanageclient.fragment.PromanageListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PromanageListPagerAdapter extends FragmentPagerAdapter {
	private List<PromanageListFragment> fragList ;

	public PromanageListPagerAdapter(FragmentManager fm,List<PromanageListFragment> fragList) {
		super(fm);
		this.fragList = fragList;
	}

	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragList.size();
	}

}
