package com.graduate_design.childsecureproject;

import com.graduate_design.childsecure.bean.User;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	Button btn_register;
	EditText userName, userPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
    
	private void initView(){
		userName = (EditText) findViewById(R.id.acount_edit);
		userPassword = (EditText) findViewById(R.id.password_edit);
		btn_register = (Button) findViewById(R.id.sign_up);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register();
			}
		});
	}
	
	private void register(){
		String name = userName.getText().toString();
		String password = userPassword.getText().toString();
		
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final User bu = new User();
		bu.setUsername(name);
		bu.setPassword(password);
		//将user和设备id进行绑定aa
		bu.setSex(true);
		bu.setDeviceType("android");
		bu.setInstallId(BmobInstallation.getInstallationId(this));
		bu.signUp(RegisterActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_LONG).show();
				// 将设备与username进行绑定
				userManager.bindInstallationForRegister(bu.getUsername());
				// 启动主页
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				BmobLog.i(arg1);
				Toast.makeText(getApplicationContext(), "注册失败!", Toast.LENGTH_LONG).show();
				progress.dismiss();
			}
		});
	}
}
