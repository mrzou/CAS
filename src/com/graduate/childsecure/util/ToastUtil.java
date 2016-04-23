package com.graduate.childsecure.util;

import com.graduate.childsecure.bean.MyApplication;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Context context = MyApplication.getInstance();
	
	public static void showToast(String message, boolean shortTime){
		if(shortTime){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
}
