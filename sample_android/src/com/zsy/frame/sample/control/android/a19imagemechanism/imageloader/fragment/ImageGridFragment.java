package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.ImageLoader;
import com.zsy.frame.lib.image.imageloader.core.assist.FailReason;
import com.zsy.frame.lib.image.imageloader.core.listener.ImageLoadingProgressListener;
import com.zsy.frame.lib.image.imageloader.core.listener.SimpleImageLoadingListener;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter.ImageGridAda;
import com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter.ImageListAda;

public class ImageGridFragment extends AbsListViewBaseFragment {

	public static final int INDEX = 1;
	DisplayImageOptions options;
	private List<String> list = new ArrayList<String>();
	private String[] imageUrls = Constants.IMAGE_URLS;
	private ImageGridAda imageGridAda;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_a19imagemechanism_imageloader_image_grid, container, false);
		listView = (GridView) rootView.findViewById(R.id.grid);
		imageGridAda = new ImageGridAda(getActivity(), listView);
		imageGridAda.setGroup(list);
		((GridView) listView).setAdapter(imageGridAda);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		return rootView;
	}

	// public class ImageAdapter extends BaseAdapter {
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
	// return null;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// final ViewHolder holder;
	// View view = convertView;
	// if (view == null) {
	// view = inflater.inflate(R.layout.item_a19imagemechanism_imageloader_grid_image, parent, false);
	// holder = new ViewHolder();
	// assert view != null;
	// holder.imageView = (ImageView) view.findViewById(R.id.image);
	// holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
	// view.setTag(holder);
	// }
	// else {
	// holder = (ViewHolder) view.getTag();
	// }
	//
	// ImageLoader.getInstance().displayImage(imageUrls[position], holder.imageView, options, new SimpleImageLoadingListener() {
	// @Override
	// public void onLoadingStarted(String imageUri, View view) {
	// holder.progressBar.setProgress(0);
	// holder.progressBar.setVisibility(View.VISIBLE);
	// }
	//
	// @Override
	// public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
	// holder.progressBar.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	// holder.progressBar.setVisibility(View.GONE);
	// }
	// }, new ImageLoadingProgressListener() {
	// @Override
	// public void onProgressUpdate(String imageUri, View view, int current, int total) {
	// holder.progressBar.setProgress(Math.round(100.0f * current / total));
	// }
	// });
	//
	// return view;
	// }
	// }
	//
	// static class ViewHolder {
	// ImageView imageView;
	// ProgressBar progressBar;
	// }
}