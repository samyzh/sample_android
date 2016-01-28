package com.zsy.frame.sample.control.android.a17navigation.actionbar.widget;

import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * @description： 自定义一个视窗操作器，实现构造函数和onCreateActionView即可 
 * @author samy
 * @date 2014年10月26日 下午6:06:32
 */
public class SamyActionProvider extends ActionProvider {

	private Context context;
	private LayoutInflater inflater;
	private View view;
	private ImageView button;

	public SamyActionProvider(Context context) {
		super(context);
		this.context = context;
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.view_a17navigation_actionbar_samy_action_privider, null);
	}

	@Override
	public View onCreateActionView() {
		button = (ImageView) view.findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "samy_action_privider", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}

}