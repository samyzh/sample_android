package com.zsy.frame.sample.control.android.a19imagemechanism.volley.fragment;

import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseFra;
import com.zsy.frame.lib.net.http.volley.Request.Method;
import com.zsy.frame.lib.net.http.volley.Response.ErrorListener;
import com.zsy.frame.lib.net.http.volley.Response.Listener;
import com.zsy.frame.lib.net.http.volley.VolleyError;
import com.zsy.frame.lib.net.http.volley.toolbox.StringRequest;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.ApiParams;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.StringUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.ToastUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.VolleyUtil;

public class StringRequestFragment extends BaseFra {
	// public class StringRequestFragment extends Fragment {
	public static final int INDEX = 11;

	private EditText etUrl;
	private Button btnSend;
	private TextView tvResult;

	private void initView(View view) {
		etUrl = (EditText) view.findViewById(R.id.et_url);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		tvResult = (TextView) view.findViewById(R.id.tv_result);

		etUrl.setText(Constants.DEFAULT_STRING_REQUEST_URL);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isEmpty(etUrl.getText().toString())) {
					ToastUtil.showToast(getActivity(), "请输入请求地址");
					return;
				}
				// 请求之前，先取消之前的请求（取消还没有进行完的请求）
				VolleyUtil.getQueue(getActivity()).cancelAll(this);
				tvResult.setText("");
				// 这里直接演示的Post,其实Get和Post
				// new StringRequest(Method.GET, "www.baidu.com", responseListener(), baseAct);

				// 提交参数类型：
				// executeRequest(new StringRequest(Method.POST, Constants.POST_TEST, responseListener(), errorListener()) {
				// protected Map<String, String> getParams() {
				// return new ApiParams().with("param1", "02").with("param2", "14");
				// }
				// });
				// 单独请求的类型：
				StringRequest request = new StringRequest(StringUtil.preUrl(etUrl.getText().toString().trim()), new Listener<String>() {

					@Override
					public void onResponse(String response) {
						tvResult.setText(response);

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));

					}
				});
				// 请求加上Tag,用于取消请求
				request.setTag(this);

				VolleyUtil.getQueue(getActivity()).add(request);

			}
		});
	}

	@Override
	public void onDestroyView() {
		VolleyUtil.getQueue(getActivity()).cancelAll(this);
		super.onDestroyView();
	}

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

		View view = inflater.inflate(R.layout.fragment_a19imagemechanism_volley_string_request, container, false);

		initView(view);

		return view;
	}

}
