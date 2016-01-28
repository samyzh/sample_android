package com.zsy.frame.sample.control.android.a01ui.a03textview;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

public class HtmlTextViewAct extends BaseAct {
	@BindView(id = R.id.a01ui_a03textview_htmltext1_tv)
	private TextView a01ui_a03textview_htmltext1_tv;
	@BindView(id = R.id.a01ui_a03textview_htmltext2_tv)
	private TextView a01ui_a03textview_htmltext2_tv;
	@BindView(id = R.id.a01ui_a03textview_htmltext3_tv, click = true)
	private TextView a01ui_a03textview_htmltext3_tv;

	public HtmlTextViewAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a03textview_htmltext);
	}

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);

		String html = "<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>" + "<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1" + "</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" + "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/></body></html>";
		a01ui_a03textview_htmltext1_tv.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
		a01ui_a03textview_htmltext1_tv.setText(Html.fromHtml(html));

		/**
		 * font标签只实现了两个属性就是：color和face。没有实现size属性。所以不能精确设置文字大小。只能通过big，small这样的相对大小设置。
		 */

		String content = "fdsafdsa  fdsafdsafds  afdsafd  sfads  afdsafdfrt retre";
		if (content.split(" ").length > 1) {
			// 用span style的方式不起作用--无法解析 <font size=\"1\" 也不起作用
			content = content.split(" ")[0] + " <small><font size=\"1\" color=\"#66ccff\">" + content.split(" ")[1] + "</font></small>";
		}
		a01ui_a03textview_htmltext2_tv.setText(android.text.Html.fromHtml(content));
	}
}
