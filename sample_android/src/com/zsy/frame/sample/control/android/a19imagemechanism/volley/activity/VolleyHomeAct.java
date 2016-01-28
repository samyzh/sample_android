package com.zsy.frame.sample.control.android.a19imagemechanism.volley.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.GsonRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.ImageLoaderFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.ImageRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.JsonRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.NetworkImageViewFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.PostRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.StringRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment.XmlRequestFragment;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.VolleyUtil;

/**
 * @description：这里演示用Volley中最原始的方法处理请求数据;和图片显示数据
 * 处理的不好是，在这里的时刻去控制VolleyUtil.getQueue(getActivity()).add(request);的数据加载
 * @author samy
 * @date 2015-3-27 下午3:52:36
 */
public class VolleyHomeAct extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actitvity_a19imagemechanism_volley_home_main);
		initView();

	}

	private void initView() {
		// String请求
		findViewById(R.id.btn_string_request).setOnClickListener(this);

		// Json请求
		findViewById(R.id.btn_json_request).setOnClickListener(this);

		// Image请求
		findViewById(R.id.btn_image_request).setOnClickListener(this);

		// ImageLoader
		findViewById(R.id.btn_image_loader).setOnClickListener(this);

		// NetworkImageView
		findViewById(R.id.btn_network_image_view).setOnClickListener(this);

		// Xml请求
		findViewById(R.id.btn_xml_request).setOnClickListener(this);
		// gson请求
		findViewById(R.id.btn_gson_request).setOnClickListener(this);

		// post请求
		findViewById(R.id.btn_post_request).setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.action_about:
		// startActivity(new Intent(VolleyHomeAct.this, VolleyAboutAct.class));
		// return true;

			default:
				return false;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(VolleyHomeAct.this, VolleyRequestAct.class);
		switch (v.getId()) {
			case R.id.btn_string_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, StringRequestFragment.INDEX);
				break;
			case R.id.btn_json_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, JsonRequestFragment.INDEX);
				break;
			case R.id.btn_image_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageRequestFragment.INDEX);
				break;
			case R.id.btn_image_loader:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageLoaderFragment.INDEX);
				break;
			case R.id.btn_network_image_view:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, NetworkImageViewFragment.INDEX);
				break;
			case R.id.btn_xml_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, XmlRequestFragment.INDEX);
				break;
			case R.id.btn_gson_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, GsonRequestFragment.INDEX);
				break;
			case R.id.btn_post_request:
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, PostRequestFragment.INDEX);
				break;
			default:
				break;
		}

		startActivity(intent);

	}

}
