package com.zsy.frame.sample.control.android.a19imagemechanism.volley.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.zsy.frame.lib.net.http.volley.Response.ErrorListener;
import com.zsy.frame.lib.net.http.volley.Response.Listener;
import com.zsy.frame.lib.net.http.volley.VolleyError;
import com.zsy.frame.lib.net.http.volley.toolbox.ImageRequest;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.StringUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.VolleyUtil;

public class ImageRequestAdapter extends ImageBaseAdapter {

	private Context context;

	public ImageRequestAdapter(Context context, String[] imageUrlArray) {
		super(context, imageUrlArray);
		this.context = context;
	}

	@Override
	int getItemLayout() {
		return R.layout.fragment_a19imagemechanism_volley_image_request_list_item;
	}

	@Override
	void setImage(final ImageView imageView, String imageUrl) {

		// 设置空图片
		imageView.setImageResource(R.drawable.a19imagemechanism_volley_ic_empty);

		// 取消这个ImageView已有的请求
		VolleyUtil.getQueue(context).cancelAll(imageView);

		ImageRequest request = new ImageRequest(StringUtil.preUrl(imageUrl), new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}
		}, 0, 0, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				imageView.setImageResource(R.drawable.a19imagemechanism_volley_ic_empty);
			}
		});

		request.setTag(imageView);

		VolleyUtil.getQueue(this.context).add(request);

	}

}
