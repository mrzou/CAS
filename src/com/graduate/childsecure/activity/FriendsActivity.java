package com.graduate.childsecure.activity;

import com.graduate.childsecure.bean.MyApplication;
import com.graduate_design.childsecureproject.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendsActivity extends Activity {

	private Context context = MyApplication.getInstance();
	private Button addFriendButton;
	private TextView new_friends_p;
	private TextView forward;
	private LinearLayout searchLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		initView();
	}

	private void initView() {
		addFriendButton = (Button) findViewById(R.id.add_friends);
		new_friends_p = (TextView) findViewById(R.id.new_friends_p);
		searchLayout = (LinearLayout) findViewById(R.id.search_line);
		forward = (TextView) findViewById(R.id.goto_already_come_out);
		Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "friends.ttf");
		addFriendButton.setTypeface(iconfont);
		((TextView) new_friends_p).setTypeface(iconfont);
		forward.setTypeface(iconfont);
		
		addFriendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(searchLayout.isShown()){
					searchLayout.setVisibility(View.GONE);
				}else{
					searchLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}
}
