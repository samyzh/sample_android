package com.zsy.frame.sample.control.android.a08net.parse;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a08net.parse.helps.HttpUtils;
import com.zsy.frame.sample.control.android.a08net.parse.helps.JsoupUtil;
import com.zsy.frame.sample.support.bean.Blogger;

public class JsoapHtmlAct extends Activity {
	private Button button1; 
	private TextView visitText; // 访问
	private TextView jifenText; // 积分
	private TextView rankText; // 排名
	private TextView originalText; // 原创
	private TextView transportText; // 转载
	private TextView translationText; // 翻译
	private TextView commentText; // 评论

	// 任务类型
	public class DEF_TASK_TYPE {
		public static final String FIRST = "first";
		public static final String NOR_FIRST = "not_first";
		public static final String REFRESH = "REFRESH";
		public static final String LOAD = "LOAD";
	}

	public static String URL = "http://blog.csdn.net/hongliuxing";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a08net_parse_jsoaphtml);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MainTask().execute(URL, DEF_TASK_TYPE.REFRESH);
			}
		});
		visitText = (TextView) findViewById(R.id.tv1);
		jifenText = (TextView) findViewById(R.id.tv2);
		rankText = (TextView) findViewById(R.id.tv3);
		originalText = (TextView) findViewById(R.id.tv4);
		transportText = (TextView) findViewById(R.id.tv5);
		translationText = (TextView) findViewById(R.id.tv6);
		commentText = (TextView) findViewById(R.id.tv7);

	}
	

	private class MainTask extends AsyncTask<String, Void, Blogger> {

		@Override
		protected Blogger doInBackground(String... params) {
			String temp = HttpUtils.httpGet(params[0]);
			Blogger blogger = JsoupUtil.getBloggerInfo(temp);
			return blogger;
		}

		@Override
		protected void onPostExecute(Blogger result) {
			super.onPostExecute(result);
			if (result == null) { return; }
			String[] rank = result.getRank().split("\\|");
			String visitNum = rank[0];
			String jifenNum = rank[1];
			String rankNum = rank[2];

			String[] statics = result.getStatistics().split("\\|");
			String originalNum = statics[0];
			String transportNum = statics[1];
			String translationNum = statics[2];
			String commentNum = statics[3];
			visitText.setText(visitNum);
			jifenText.setText(jifenNum);
			rankText.setText(rankNum);
			originalText.setText(originalNum);
			transportText.setText(transportNum);
			translationText.setText(translationNum);
			commentText.setText(commentNum);
		}
	}
}
