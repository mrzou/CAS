package com.graduate.childsecure.activity;

import com.graduate_design.childsecureproject.R;

import cn.bmob.im.BmobChat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends BaseActivity{

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;
	Intent goMainActivity, goLoginActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		// 初始化Bmob SDK
		BmobChat.getInstance(this).init(applicationId);
		
		goMainActivity = new Intent(this, MainActivity.class);
		goLoginActivity = new Intent(this, LoginActivity.class);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (userManager.getCurrentUser() != null) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startActivity(goMainActivity);
				finish();
				break;
			case GO_LOGIN:
				startActivity(goLoginActivity);
				finish();
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
