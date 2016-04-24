package com.graduate.childsecure.activity;

import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.task.BRequest;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.FindListener;

import com.graduate.childsecure.adapter.AddFriendAdapter;
import com.graduate.childsecure.bean.MyApplication;
import com.graduate.childsecure.util.CustomProgress;
import com.graduate.childsecure.util.ToastUtil;
import com.graduate_design.childsecureproject.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FriendsActivity extends Activity implements OnClickListener{

	private Context context = MyApplication.getInstance();
	private Button addFriendButton, searchFriends, backButton;
	private TextView new_friends_p;
	private TextView forward;
	private EditText search_world;
	private LinearLayout searchLayout, new_friends_linear_layout;
	private ListView searchUserListView;
	private BmobUserManager userManager;
	private String search_name;
	private AddFriendAdapter newFriendsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		userManager = BmobUserManager.getInstance(this);
		searchUserListView = (ListView) findViewById(R.id.list_newfriend);
	    
		initView();
	}

	private void initView() {
		addFriendButton = (Button) findViewById(R.id.add_friends);
		searchFriends = (Button) findViewById(R.id.btn_search);
		backButton = (Button) findViewById(R.id.back_main);
		search_world = (EditText) findViewById(R.id.search_world);
		
		new_friends_p = (TextView) findViewById(R.id.new_friends_p);
		searchLayout = (LinearLayout) findViewById(R.id.search_line);
		new_friends_linear_layout = (LinearLayout) findViewById(R.id.personal_linearlayout1);
		forward = (TextView) findViewById(R.id.goto_already_come_out);
		Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "friends.ttf");
		addFriendButton.setTypeface(iconfont);
		((TextView) new_friends_p).setTypeface(iconfont);
		forward.setTypeface(iconfont);
		backButton.setTypeface(iconfont);
		
		backButton.setOnClickListener(this);
		addFriendButton.setOnClickListener(this);
		searchFriends.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_friends://搜索
			if(searchLayout.isShown()){
				searchLayout.setVisibility(View.GONE);
				new_friends_linear_layout.setVisibility(View.VISIBLE);
			}else{
				searchLayout.setVisibility(View.VISIBLE);
				new_friends_linear_layout.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_search:
			search_name = search_world.getText().toString();
			if(search_name!=null && !search_name.equals("")){
				initSearchList();
			}else{
				ToastUtil.showToast("请输入用户名", 2);
			}
			break;
		case R.id.back_main:
			FriendsActivity.this.finish();
			break;
		default:
			break;
		}
	}
	public void initSearchList(){
		CustomProgress.show(this, "正在搜索...", false, null);
		userManager.queryUserByPage(false, 0, search_name, new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				ToastUtil.showToast("用户不存在a!"+arg1,	 2);
				CustomProgress.hideDialog();
			}

			@Override
			public void onSuccess(List<BmobChatUser> users) {
				// TODO Auto-generated method stub
				if (isNotNull(users)) {
					if(users.size() < BRequest.QUERY_LIMIT_COUNT){
						ToastUtil.showToast("用户搜索完成!", 2);
						setUsers(users);
					}else{
						ToastUtil.showToast("数据大于搜索限制!", 2);
					}
				}else{
					ToastUtil.showToast("用户不存在", 2);
					users.clear();
					newFriendsAdapter.onDateChange(users);
				}
				CustomProgress.hideDialog();
			}
		});
	}
	//搜索完成更新listview
	private void setUsers(List<BmobChatUser> users) {
		newFriendsAdapter = new AddFriendAdapter(context, users);
		searchUserListView.setAdapter(newFriendsAdapter);
	}
	
	private boolean isNotNull(List<BmobChatUser> users) {
		if (users != null && users.size() > 0) {
			return true;
		}
		return false;
	}
}
