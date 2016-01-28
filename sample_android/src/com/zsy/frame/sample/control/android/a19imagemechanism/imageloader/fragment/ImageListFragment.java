package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.Constants;
import com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter.ImageListAda;

public class ImageListFragment extends AbsListViewBaseFragment {
	public static final int INDEX = 0;
	// DisplayImageOptions options;
	private ImageListAda imageListAda;
	private List<String> list = new ArrayList<String>();
	private String[] imageUrls = Constants.IMAGE_URLS;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(100)).build();
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
		View rootView = inflater.inflate(R.layout.fragment_a19imagemechanism_imageloader_image_list, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		imageListAda = new ImageListAda(getActivity());
		((ListView) listView).setAdapter(imageListAda);
		imageListAda.setGroup(list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// AnimateFirstDisplayListener.displayedImages.clear();
		ImageListAda.cleanShowLoading();
	}
	//
	// private static class ViewHolder {
	// TextView text;
	// ImageView image;
	// }
	//
	// class ImageAdapter extends BaseAdapter {
	//
	// private LayoutInflater inflater;
	// private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
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
	// public View getView(final int position, View convertView, ViewGroup parent) {
	// View view = convertView;
	// final ViewHolder holder;
	// if (convertView == null) {
	// view = inflater.inflate(R.layout.item_a19imagemechanism_imageloader_list_image, parent, false);
	// holder = new ViewHolder();
	// holder.text = (TextView) view.findViewById(R.id.text);
	// holder.image = (ImageView) view.findViewById(R.id.image);
	// view.setTag(holder);
	// }
	// else {
	// holder = (ViewHolder) view.getTag();
	// }
	//
	// holder.text.setText("Item " + (position + 1));
	//
	// ImageLoader.getInstance().displayImage(imageUrls[position], holder.image, options, animateFirstListener);
	//
	// return view;
	// }
	// }
	//
	// private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
	//
	// static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	//
	// @Override
	// public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	// if (loadedImage != null) {
	// ImageView imageView = (ImageView) view;
	// boolean firstDisplay = !displayedImages.contains(imageUri);
	// if (firstDisplay) {
	// FadeInBitmapDisplayer.animate(imageView, 500);
	// displayedImages.add(imageUri);
	// }
	// }
	// }
	// }
}