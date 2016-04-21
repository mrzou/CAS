package com.graduate.childsecure.activity;

import cn.bmob.im.BmobUserManager;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	BmobUserManager userManager;
	public static String applicationId = "5484c31f27bcf5e6c8fa6ebe4ce44851";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userManager = BmobUserManager.getInstance(this);
	}

	public void onDetach() {
		// TODO Auto-generated method stub
		
	}
}
