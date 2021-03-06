package com.graduate.childsecure.activity;

import java.util.ArrayList;
import java.util.List;

import com.graduate.childsecure.adapter.CustomeFragmentPagerAdapter;
import com.graduate.childsecure.fragment.FourFragment;
import com.graduate.childsecure.fragment.OneFragment;
import com.graduate.childsecure.fragment.ThreeFragment;
import com.graduate.childsecure.fragment.TwoFragmnet;
import com.graduate_design.childsecureproject.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
//import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{

	private ViewPager viewPager;
	private List<Fragment> fragments;
	private int selectedColor, unSelectedColor;
	private static int indexPager = 0;
	
	public static void setIndexPager(int index){
		indexPager = index;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initView() {
		selectedColor = getResources().getColor(R.color.tab_title_pressed_color);
		unSelectedColor = getResources().getColor(R.color.tab_title_normal_color);

		InitTextView();
		InitViewPager(0);
	}

	private void InitTextView() {
		
		makeMenuOfBottom(R.id.linearLayout1, "会话", 0);
		makeMenuOfBottom(R.id.linearLayout2, "位置", 1);
		makeMenuOfBottom(R.id.linearLayout3, "分享", 2);
		makeMenuOfBottom(R.id.linearLayout4, "设置", 3);
	}
	//对应设置图标颜色和监听事件
	private void makeMenuOfBottom(int linearLayoutId, String text, int index){
		Typeface iconfont = Typeface.createFromAsset(getAssets(), "mainbar.ttf");
		
		LinearLayout linearLayout = (LinearLayout) findViewById(linearLayoutId);
		//分别设置对应图标的监听事件
		linearLayout.setOnClickListener(new MyOnClickListener(index));
		
		((TextView) linearLayout.getChildAt(0)).setTypeface(iconfont);
		((TextView) linearLayout.getChildAt(1)).setText(text);
		
		if(index==0){
			((TextView) linearLayout.getChildAt(0)).setTextColor(selectedColor);
			((TextView) linearLayout.getChildAt(1)).setTextColor(selectedColor);
		}else{
			((TextView) linearLayout.getChildAt(0)).setTextColor(unSelectedColor);
			((TextView) linearLayout.getChildAt(1)).setTextColor(unSelectedColor);
		}
	}
	/**
	 * 
	 */
	private void InitViewPager(int page) {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new OneFragment());
		fragments.add(new TwoFragmnet());
		fragments.add(new ThreeFragment());
		fragments.add(new FourFragment());
		//划动页面容器
		viewPager.setAdapter(new CustomeFragmentPagerAdapter(getSupportFragmentManager(), fragments));
		viewPager.setCurrentItem(page);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener()); //监听viewpager切换事件
	}
	//点击图标的事件
	private class MyOnClickListener implements OnClickListener {
		
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index, true);
		}
	}
	//手滑fragment触发事件
	public class MyOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageScrollStateChanged(int index) {
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageSelected(int index) {
			int i;
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
			//
			for(i=0; i<4; i++){
				if(i<index || i>index){
					TextView textView1 = (TextView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(0);
					TextView textView2 = (TextView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(1);
					textView1.setTextColor(unSelectedColor);
					textView2.setTextColor(unSelectedColor);
				}else{
					TextView textView1 = (TextView) ((LinearLayout) linearLayout.getChildAt(index)).getChildAt(0);
					TextView textView2 = (TextView) ((LinearLayout) linearLayout.getChildAt(index)).getChildAt(1);
					textView1.setTextColor(selectedColor);
					textView2.setTextColor(selectedColor);
				}
			}
			indexPager = index;
			viewPager.setCurrentItem(index, false);
		}
	}
	
	@Override  
	protected void onResume() {  
	    super.onResume();
	    viewPager.setCurrentItem(indexPager);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}