package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zsy.frame.lib.extend.ui.base.BaseImgAda;
import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.assist.FailReason;
import com.zsy.frame.lib.image.imageloader.core.listener.ImageLoadingProgressListener;
import com.zsy.frame.lib.image.imageloader.core.listener.SimpleImageLoadingListener;
import com.zsy.frame.sample.R;

public class ImageGridAda extends BaseImgAda<String> {

	public ImageGridAda(Context context, AbsListView absListView) {
		super(context, absListView);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = mInflater.inflate(R.layout.item_a19imagemechanism_imageloader_grid_image, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.image);
			holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
			view.setTag(holder);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}

		displayImage(group.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				holder.progressBar.setProgress(0);
				holder.progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				holder.progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				holder.progressBar.setVisibility(View.GONE);
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				holder.progressBar.setProgress(Math.round(100.0f * current / total));
			}
		});

		return view;
	}

	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}

}
