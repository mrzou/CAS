package com.graduate.childsecure.activity;

import java.util.List;

import com.graduate.childsecure.bean.MyApplication;
import com.graduate.childsecure.bean.User;
import com.graduate.childsecure.util.ToastUtil;
import com.graduate_design.childsecureproject.R;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

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
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	Button btn_register, getValidateCodeButton;
	EditText userName, userPassword, phoneNumber, validateCode, comfirmPassword;
	Context context = MyApplication.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
    
	private void initView(){
		userName = (EditText) findViewById(R.id.acount_edit);
		userPassword = (EditText) findViewById(R.id.password_edit);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		btn_register = (Button) findViewById(R.id.sign_up);
		getValidateCodeButton = (Button) findViewById(R.id.sendValidCode);
		validateCode = (EditText) findViewById(R.id.valid_code);
		comfirmPassword = (EditText) findViewById(R.id.confirm_password_edit);
		
		getValidateCodeButton.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == btn_register) {
			valifySignUp();
		} else {
			if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
				ToastUtil.showToast("请输入手机号码!", 2);
				return;
			}
			validateUniquePhone();
		}
	}
	// 验证是否存在该手机用户
	private void validateUniquePhone(){
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("mobilePhoneNumber", phoneNumber.getText().toString());
		//执行查询方法
		query.findObjects(this, new FindListener<User>() {
		        @Override
		        public void onSuccess(List<User> object) {
		            // TODO Auto-generated method stub
		            if(object.size()==0){
		            	requestValidateCode();
		            }else{
		            	ToastUtil.showToast("已存在该手机用户", 2);
		            }
		        }
		        @Override
		        public void onError(int code, String msg) {
		            // TODO Auto-generated method stub
		        	ToastUtil.showToast("查询失败："+msg, 2);
		        }
		});
	}
	//验证输入框里的内容
	private void valifySignUp(){
		String userPass = userPassword.getText().toString();
		if (TextUtils.isEmpty(userName.getText().toString())) {
			ToastUtil.showToast("请输入用户名", 2);
			return;
		}
		if (TextUtils.isEmpty(userPassword.getText().toString())) {
			ToastUtil.showToast("请输入密码", 2);
			return;
		}
		if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
			ToastUtil.showToast("请输入手机号码", 2);
			return;
		}
		if (!userPass.equals(comfirmPassword.getText().toString()) || userPass.length() < 6 || userPass.length() > 12) {
			ToastUtil.showToast("请检查密码！", 2);
			return;
		}
		if (TextUtils.isEmpty(validateCode.getText().toString())) {
			ToastUtil.showToast("请输入短信验证吗", 2);
			return;
		}
		valifyValidateCode();
	}
	
	private void requestValidateCode(){
		String templateCode = new String("您的验证码是`%smscode%`，有效期为`%ttl%`分钟。您正在使用`%appname%`的验证码。【安全定位】");
		BmobSMS.requestSMSCode(context, phoneNumber.getText().toString(), templateCode, new RequestSMSCodeListener() {

		    @Override
		    public void done(Integer smsId,BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//验证码发送成功
		        	ToastUtil.showToast("验证码发送成功", 2);
		        }else{
		        	ToastUtil.showToast(ex.toString(), 2);
		        }
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
		bu.setMobilePhoneNumber(phoneNumber.getText().toString());
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
				Toast.makeText(getApplicationContext(), arg1, Toast.LENGTH_LONG).show();
				progress.dismiss();
			}
		});
	}
	
	private void valifyValidateCode(){
		BmobSMS.verifySmsCode(context, phoneNumber.getText().toString(), validateCode.getText().toString(), new VerifySMSCodeListener() {

		    @Override
		    public void done(BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//短信验证码已验证成功
		        	Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
		        	register();
		        }else{
		        	Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
		        	return;
		        }
		    }
		});
	}
}
