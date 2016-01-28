package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zsy.frame.lib.extend.ui.base.BaseImgAda;
import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.ImageLoader;
import com.zsy.frame.lib.image.imageloader.core.display.FadeInBitmapDisplayer;
import com.zsy.frame.sample.R;

public class ImageGalleryAda extends BaseImgAda<String> {

	public ImageGalleryAda(Context context) {
		super(context);
		// options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(300)).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = (ImageView) convertView;
		if (imageView == null) {
			imageView = (ImageView) mInflater.inflate(R.layout.item_a19imagemechanism_imageloader_gallery_image, parent, false);
		}

		ImageLoader.getInstance().displayImage(group.get(position), imageView, options);
		return imageView;
	}

}
