package com.graduate.childsecure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.graduate.childsecure.bean.MyApplication;
import com.graduate.childsecure.util.CustomProgress;
import com.graduate.childsecure.util.ToastUtil;
import com.graduate.childsecure.util.ViewHolder;
import com.graduate_design.childsecureproject.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.listener.PushListener;

public class AddFriendAdapter extends BaseAdapter {

	private Context context = MyApplication.getInstance();
	private List<BmobChatUser> user_list;
	public AddFriendAdapter(Context context, List<BmobChatUser> list) {
		super();
		this.user_list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return user_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return user_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(context);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_add_friend, null);
		}
		final BmobChatUser contract = user_list.get(position);
		TextView name = ViewHolder.get(convertView, R.id.name);
		ImageView iv_avatar = ViewHolder.get(convertView, R.id.avatar);
		Button btn_add = ViewHolder.get(convertView, R.id.btn_add);
		String avatar = contract.getAvatar();
		iv_avatar.setImageResource(R.drawable.default_head);
		
		name.setText(contract.getUsername());
		btn_add.setText("添加");
		btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CustomProgress.show(context, "正在添加...", false, null);
				//发送tag请求
				BmobChatManager.getInstance(context).sendTagMessage(BmobConfig.TAG_ADD_CONTACT, contract.getObjectId(),new PushListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						CustomProgress.hideDialog();
						ToastUtil.showToast("发送请求成功，等待对方验证!", 2);
					}
					
					@Override
					public void onFailure(int arg0, final String arg1) {
						// TODO Auto-generated method stub
						CustomProgress.hideDialog();
						ToastUtil.showToast("发送请求失败，请重新添加!", 2);
					}
				});
			}
		});
		return convertView;
	}
	
	public void onDateChange(List<BmobChatUser> users) {
		this.user_list = users;
		this.notifyDataSetChanged();
	}
}
