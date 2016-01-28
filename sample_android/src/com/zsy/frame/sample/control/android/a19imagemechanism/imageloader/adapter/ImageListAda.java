package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseImgAda;
import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.display.RoundedBitmapDisplayer;
import com.zsy.frame.sample.R;

public class ImageListAda extends BaseImgAda<String> {

	public ImageListAda(Context context) {
		super(context);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a19imagemechanism_imageloader_ic_stub).showImageForEmptyUri(R.drawable.a19imagemechanism_imageloader_ic_empty).showImageOnFail(R.drawable.a19imagemechanism_imageloader_ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(100)).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_a19imagemechanism_imageloader_list_image, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}

		holder.text.setText("Item " + (position + 1));

		displayImage(group.get(position), holder.image, options);

		return view;
	}
	
	
	
	private static class ViewHolder {
		TextView text;
		ImageView image;
	}

}
