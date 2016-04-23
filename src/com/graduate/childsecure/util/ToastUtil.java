package com.graduate.childsecure.util;

import com.graduate.childsecure.bean.MyApplication;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Context context = MyApplication.getInstance();
	
	public static void showToast(String message, int time){
		if(time == 1){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
}
