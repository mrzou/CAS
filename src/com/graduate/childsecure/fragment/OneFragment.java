package com.graduate.childsecure.fragment;

import cn.bmob.im.db.BmobDB;

import com.graduate.childsecure.activity.FriendsActivity;
import com.graduate.childsecure.activity.MainActivity;
import com.graduate.childsecure.adapter.MessageRecentAdapter;
import com.graduate.childsecure.bean.MyApplication;
import com.graduate_design.childsecureproject.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class OneFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener{
	
	private View view;
	private ListView listview;
	private Button friendsButton;
	private Context context;
	private ListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.fragment_one, null);
		context = MyApplication.getInstance();
		initView();
		return view;
	}
	
	private void initView(){
		Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "friends.ttf");
		friendsButton = (Button) view.findViewById(R.id.friends_lists);
		friendsButton.setTypeface(iconfont);
		friendsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, FriendsActivity.class));
			}
		});
		//会话列表
		listview = (ListView) view.findViewById(R.id.friendsListView);
		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);
		adapter = new MessageRecentAdapter(context, R.layout.item_conversation, BmobDB.create(context).queryRecents());
		listview.setAdapter(adapter);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setRetainInstance(true);    
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
