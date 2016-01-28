package com.zsy.frame.sample.control.android.a01ui.a17advanceview.infater;

import com.zsy.frame.lib.SYLoger;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * @description：LayoutInflater技术广泛应用于需要动态添加View;
inflate(int resource, ViewGroup root, boolean attachToRoot)  
那么这第三个参数attachToRoot又是什么意思呢？其实如果你仔细去阅读上面的源码应该可以自己分析出答案，这里我先将结论说一下吧，感兴趣的朋友可以再阅读一下源码，校验我的结论是否正确。
1. 如果root为null，attachToRoot将失去作用，设置任何值都没有意义。
2. 如果root不为null，attachToRoot设为true，则会在加载的布局文件的最外层再嵌套一层root布局。
3. 如果root不为null，attachToRoot设为false，则root参数失去作用。
4. 在不设置attachToRoot参数的情况下，如果root不为null，attachToRoot参数默认为true。
 * @author samy
 * @date 2015-3-29 下午5:12:10
 */
public class InfaterAct extends Activity {
	public static final String TAG = "InfaterAct";
	public static final String CONTENT_VIEW = "content_view";
	protected DisplayMetrics display;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(CONTENT_VIEW)) {
			int layout = savedInstanceState.getInt(CONTENT_VIEW);
			View view = LayoutInflater.from(this).inflate(layout, null);
			// View view = LayoutInflater.from(this).inflate(resource, root, attachToRoot);
			XmlResourceParser parser = getResources().getLayout(layout);
			// 确实，这主要是因为，在setContentView()方法中，Android会自动在布局文件的最外层再嵌套一个FrameLayout，所以layout_width和layout_height属性才会有效果。
			setContentView(view);
			display = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(display);

			resize(this, parser, view);
		}
		else {
			Log.e(TAG, "You must set the content view form the children activity");
		}
	}

	/**
	 * @description：这里是自己设计自定义加载布局，重复划，可以达到多屏幕的适配作用
	 * @author samy
	 * @date 2015-3-29 下午5:23:31
	 */
	public static void resize(Context context, XmlResourceParser parser, View view) {
		try {
			int tag;
			DisplayMetrics display = new DisplayMetrics();
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(display);
			while ((tag = parser.next()) != XmlResourceParser.START_TAG) {
				if (tag == XmlResourceParser.END_DOCUMENT) { return; }
			}
			if ("include".equals(parser.getName())) {
				int layout = parser.getAttributeResourceValue(null, "layout", 0);
				XmlResourceParser include = context.getResources().getLayout(layout);
				resize(context, include, view);
			}
			else {
				String nameSpace = "http://schemas.android.com/apk/res/android";
				String command = parser.getAttributeValue(nameSpace, "tag");
				if (command != null && command.trim().startsWith("resize")) {
					String[] list = command.split("\\|");
					float width = Float.parseFloat(list[1]);
					float height = Float.parseFloat(list[2]);
					float left = Float.parseFloat(list[3]);
					float top = Float.parseFloat(list[4]);
					float right = Float.parseFloat(list[5]);
					float bottom = Float.parseFloat(list[6]);
					ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
					if (width > 0) {
						params.width = (int) (width * display.widthPixels);
					}
					if (height > 0) {
						params.height = (int) (height * display.widthPixels);
					}
					params.leftMargin = (int) (left * display.widthPixels);
					params.topMargin = (int) (top * display.widthPixels);
					params.rightMargin = (int) (right * display.widthPixels);
					params.bottomMargin = (int) (bottom * display.widthPixels);
					view.setLayoutParams(params);
					if (view instanceof TextView && list.length == 8) {
						((TextView) view).setTextSize(Float.parseFloat(list[7]) * display.widthPixels);
					}
				}
				if (view instanceof ViewGroup) {
					if (view instanceof TimePicker || view instanceof DatePicker || view instanceof NumberPicker) { return; }
					ViewGroup group = (ViewGroup) view;
					for (int i = 0; i < group.getChildCount(); i++) {
						resize(context, parser, group.getChildAt(i));
					}
				}
			}
		}
		catch (Exception e) {
			SYLoger.print(e.toString());
		}
	}

}
