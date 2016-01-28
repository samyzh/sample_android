package com.zsy.frame.sample.control.android.a20thirdparty.qq;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;
/**这个可以跳转的应用的QQ客服
 * @description：
 * @author samy
 * @date 2015-3-5 下午1:51:28
 */
public class SkipQQChatAct extends BaseAct {
	@BindView(id = R.id.button1 , click = true)
	private Button button1;
	private String QQ="853169778";
	
	public SkipQQChatAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a20thirdparty_qq_skipqqchat);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				String url11 = "mqqwpa://im/chat?chat_type=wpa&uin="+QQ+"&version=1";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
				break;
		}
	}

}
