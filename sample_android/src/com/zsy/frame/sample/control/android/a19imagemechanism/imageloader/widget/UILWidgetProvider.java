package com.zsy.frame.sample.control.android.a19imagemechanism.imageloader.widget;

import static com.zsy.frame.sample.config.Constants.IMAGE_URLS;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.ImageLoader;
import com.zsy.frame.lib.image.imageloader.core.assist.ImageSize;
import com.zsy.frame.lib.image.imageloader.core.listener.SimpleImageLoadingListener;
import com.zsy.frame.sample.R;

/**
 * Example widget provider
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILWidgetProvider extends AppWidgetProvider {

	private static DisplayImageOptions displayOptions;

	static {
		displayOptions = DisplayImageOptions.createSimple();
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// GlobalApp.getInstance().initImageLoader();

		final int widgetCount = appWidgetIds.length;
		for (int i = 0; i < widgetCount; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_a19imagemechanism_imageloader);

		ImageSize minImageSize = new ImageSize(70, 70); // 70 - approximate size of ImageView in widget
		ImageLoader.getInstance().loadImage(IMAGE_URLS[0], minImageSize, displayOptions, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				views.setImageViewBitmap(R.id.image_left, loadedImage);
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
		});
		ImageLoader.getInstance().loadImage(IMAGE_URLS[1], minImageSize, displayOptions, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				views.setImageViewBitmap(R.id.image_right, loadedImage);
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
		});
	}
}
