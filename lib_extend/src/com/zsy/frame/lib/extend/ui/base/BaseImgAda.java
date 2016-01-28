package com.zsy.frame.lib.extend.ui.base;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.zsy.frame.lib.image.imageloader.core.DisplayImageOptions;
import com.zsy.frame.lib.image.imageloader.core.ImageLoader;
import com.zsy.frame.lib.image.imageloader.core.listener.ImageLoadingListener;
import com.zsy.frame.lib.image.imageloader.core.listener.ImageLoadingProgressListener;
import com.zsy.frame.lib.image.imageloader.core.listener.PauseOnScrollListener;
import com.zsy.frame.lib.image.imageloader.utils.AnimateFirstDisplayListener;
import com.zsy.frame.lib.image.imageloader.utils.IShowImage;

/**
 * @description：这里封装图片加载类（用ImageLoad显示，而没有用Volley中的图片加载）
 * @author samy
 * @date 2015-3-24 下午5:58:17
 */
public abstract class BaseImgAda<T> extends BaseAda<T> implements IShowImage {
	/**图片自己配置默认的自己设置一个动画配置*/
	protected static AnimateFirstDisplayListener animateFirstListener;
	// protected ImageLoadingListener animateFirstListener;
	protected ImageLoader imageLoader;
	protected DisplayImageOptions options;
	/**
	 * 如果ListView或GridView,可以处理滑动时图片加载处理；
	 * 如果是Gallery传递absListView为空(因这种控件用的比较少)，viewpager不在这里处理
	 */
	protected AbsListView absListView;
	protected boolean pauseOnScroll = true;
	protected boolean pauseOnFling = true;

	/**这个直接用于构造数据
	 * @param context
	 */
	public BaseImgAda(Context context) {
		super(context);
		imageLoader = ImageLoader.getInstance();
		animateFirstListener = new AnimateFirstDisplayListener();
	}

	/**这个构造数据是，使absListView滑动加载图片时，不去加载图片处理显示；
	 * @param context
	 * @param absListView
	 */
	public BaseImgAda(Context context, AbsListView absListView) {
		super(context);
		this.absListView = absListView;
		imageLoader = ImageLoader.getInstance();
		animateFirstListener = new AnimateFirstDisplayListener();

		applyScrollListener();
	}

	public void displayImage(String imageUri, ImageView imageView) {
		imageLoader.displayImage(imageUri, imageView);
	}

	public void displayImage(String imageUri, ImageView imageView, DisplayImageOptions options) {
		imageLoader.displayImage(imageUri, imageView, options, animateFirstListener);
	}
	
	@Override
	public void displayImage(String imageUri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
		if (null != listener) {
			imageLoader.displayImage(imageUri, imageView, options, listener);
		}else {
			imageLoader.displayImage(imageUri, imageView, options, animateFirstListener);
		}
	}
	
	@Override
	public void displayImage(String imageUri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
		if (null != listener) {
			imageLoader.displayImage(imageUri, imageView, options, listener,progressListener);
		}else {
			imageLoader.displayImage(imageUri, imageView, options, animateFirstListener,progressListener);
		}
	}

	/**
	 * @description：直接在这里处理view的滑动监听，摒弃了Application的方法；
	 * @author samy
	 * @date 2015-3-25 下午2:25:31
	 */
	private void applyScrollListener() {
		if (null != absListView) {
			absListView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
		}
	}

	/**
	 * @description：这个方便应在调用new 出过这个BaseImgAda中的生命周期结束时调用；如onDestory()中
	 * 注意下这里，当按下返回键的时候清除一下在内存中的图片，大家不过要过度依赖自动内存管理，有些时候还是手动去清除掉内存比较好
	 * @author samy
	 * @date 2015-3-25 下午6:30:16
	 */
	public static void cleanShowLoading() {
		if (null != animateFirstListener) {
			AnimateFirstDisplayListener.displayedImages.clear();
		}
	}

}
