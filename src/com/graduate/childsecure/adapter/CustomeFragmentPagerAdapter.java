package com.graduate.childsecure.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

// 定义划动页面的适配器
public class CustomeFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> fragmentList;
	public CustomeFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	//得到每个页面
	@Override
	public Fragment getItem(int fragmentPage) {
		Log.v("TAG", "getItem="+String.valueOf(fragmentPage));
		return (fragmentList == null || fragmentList.size() == 0) ? null: fragmentList.get(fragmentPage);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return null;
	}

	@Override
	public int getCount() {
		return fragmentList == null ? 0 : fragmentList.size();
	}
	
	@Override
	public void destroyItem (ViewGroup container, int position, Object object){
		super.destroyItem(container, position, object);
		Log.v("TAG", "position = "+position);
	}
}
