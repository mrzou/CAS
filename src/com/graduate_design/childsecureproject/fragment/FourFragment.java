package com.graduate_design.childsecureproject.fragment;

import cn.bmob.v3.BmobUser;

import com.graduate_design.childsecureproject.LoginActivity;
import com.graduate_design.childsecureproject.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FourFragment extends Fragment{
	
	private View view;
	private Button loginButton;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		context = getActivity();
		this.view = inflater.inflate(R.layout.fragment_four, null);
		loginButton = (Button) view.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				BmobUser.logOut(context);   //清除缓存用户对象
				BmobUser currentUser = BmobUser.getCurrentUser(context); // 现在的currentUser是null了
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			
		});
		return view;
	}
	@Override
	public void onDetach(){
		super.onDetach();
		Log.v("TAG", "onDetach");
	}
}
