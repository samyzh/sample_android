package com.zsy.frame.sample.control.android.a19imagemechanism.volley.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zsy.frame.lib.net.http.volley.toolbox.ImageLoader;
import com.zsy.frame.lib.net.http.volley.toolbox.NetworkImageView;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.LruImageCache;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.StringUtil;
import com.zsy.frame.sample.control.android.a19imagemechanism.volley.util.VolleyUtil;

public class NetworkImageViewAdapter extends ImageBaseAdapter {

	private ImageLoader imageLoader;

	public NetworkImageViewAdapter(Context context, String[] imageUrlArray) {
		super(context, imageUrlArray);
		this.imageLoader = new ImageLoader(VolleyUtil.getQueue(context), new LruImageCache());
	}

	@Override
	int getItemLayout() {
		return R.layout.fragment_a19imagemechanism_volley_network_image_view_list_item;
	}

	@Override
	void setImage(ImageView imageView, String imageUrl) {
		NetworkImageView networkImageView = (NetworkImageView) imageView;
		networkImageView.setDefaultImageResId(R.drawable.a19imagemechanism_volley_ic_empty);
		networkImageView.setErrorImageResId(R.drawable.a19imagemechanism_volley_ic_empty);
		networkImageView.setImageUrl(StringUtil.preUrl(imageUrl), imageLoader);
	}

}
