package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.ImageLoader;
import com.zsy.frame.lib.image.imageloader.utils.ImgCacheUtils;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.activity.SimpleImageAct;
import com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter.ImageGalleryAda;

public class ImageGalleryFragment extends BaseFragment {

	public static final int INDEX = 3;
	// DisplayImageOptions options;
	private ImageGalleryAda imageGalleryAda;
	
	private List<String> list = new ArrayList<String>();
	private String[] imageUrls = Constants.IMAGE_URLS;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		/**
		 * 数组跟List之间的转换
		 */
		// String[] array = (String[])list.toArray(new String[size]);
		for (int i = 0; i < imageUrls.length; i++) {
			list.add(imageUrls[i]);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_a19imagemechanism_imageloader_image_gallery, container, false);
		Gallery gallery = (Gallery) rootView.findViewById(R.id.gallery);
		imageGalleryAda = new ImageGalleryAda(getActivity());
		imageGalleryAda.setGroup(list);
		gallery.setAdapter(imageGalleryAda);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		return rootView;
	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(getActivity(), SimpleImageAct.class);
		intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
		intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	// private class ImageAdapter extends BaseAdapter {
	//
	// private LayoutInflater inflater;
	//
	// ImageAdapter() {
	// inflater = LayoutInflater.from(getActivity());
	// }
	//
	// @Override
	// public int getCount() {
	// return imageUrls.length;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ImageView imageView = (ImageView) convertView;
	// if (imageView == null) {
	// imageView = (ImageView) inflater.inflate(R.layout.item_a19imagemechanism_imageloader_gallery_image, parent, false);
	// }
	// ImageLoader.getInstance().displayImage(imageUrls[position], imageView, options);
	// return imageView;
	// }
	// }
}