package com.graduate.childsecure.activity;

import com.graduate.childsecure.bean.User;
import com.graduate.childsecure.util.CustomProgress;
import com.graduate.childsecure.util.ToastUtil;
import com.graduate_design.childsecureproject.R;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private EditText userName, password;
	private TextView signUp;
	private Button btn_login;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		init();
	}
	// 初始化按钮事件
	private void init() {
		userName = (EditText) findViewById(R.id.log_in_acount_edit);
		password = (EditText) findViewById(R.id.log_in_password_edit);
		btn_login = (Button) findViewById(R.id.btn_login);
		signUp = (TextView) findViewById(R.id.logUpUser);
		btn_login.setOnClickListener(this);
		signUp.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == signUp) {
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
		} else {
			userLogin();
		}
	}
	
	public void userLogin(){
		String name = userName.getText().toString();
		String passw = Base64.encodeToString(password.getText().toString().getBytes(), Base64.DEFAULT);
		
		
		ToastUtil.showToast(passw, 2);
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_LONG).show();
			return;
		}

		if (TextUtils.isEmpty(passw)) {
			Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG).show();
			return;
		}

		CustomProgress.show(this, "登录中...", true, null);
		User user = new User();
		user.setUsername(name);
		user.setPassword(passw);
		userManager.login(user,new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.v("TAG", "on success");
					}
				});
				//更新用户的地理位置以及好友的资料
				CustomProgress.hideDialog();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int errorcode, String arg0) {
				// TODO Auto-generated method stub
				ToastUtil.showToast("用户名或密码错误!", 2);
				CustomProgress.hideDialog();
			}
		});
	}
}
