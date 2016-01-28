package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.zsy.frame.sample.GlobalApp;
import com.zsy.frame.sample.R;

/**
 * 有两种写法
 * 第一种是在xml配置写动画；（适用于固定位置的）
 * 第二种直接代码中写；（适用于动态位置的，如Listview控件上写动画）
 * @description： 
 * 模仿蘑菇街购物车动画效果 使用Tween动画 ；
 * 第一次执行动画效果图片放大效果明显，之后放大效果不明显，蘑菇街也有这样的问题。
 * @author samy
 * @date 2015-3-7 下午2:55:55
 */
public class TweenAnimationAct extends Activity {

	private ImageView mAnimImageView;
	private TextView mTextView;
	private TextView mNumTextView;
	private Animation mAnimation;
	private PopupWindow mPopupWindow;
	private int goodsNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_tweenanimation_detail_frame);

		mAnimImageView = (ImageView) findViewById(R.id.cart_anim_icon);
		mTextView = (TextView) findViewById(R.id.detail_cart_btn);
		mNumTextView = (TextView) findViewById(R.id.detail_shopping_new);
		mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mAnimImageView.setVisibility(View.VISIBLE);
				mAnimImageView.startAnimation(mAnimation);
			}
		});
		mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_base_tweenanimation_cart_anim);
		mAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				goodsNum++;
				mNumTextView.setText(goodsNum + "");
				mAnimImageView.setVisibility(View.INVISIBLE);
				createPopbWindow();
				mPopupWindow.showAtLocation(mAnimImageView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mAnimImageView.setVisibility(View.VISIBLE);
				mAnimImageView.startAnimation(mAnimation);
			}
		}, 1500);
	}

	private void createPopbWindow() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentview = inflater.inflate(R.layout.veiw_a01ui_a20animation_base_tweenanimation_cart_popup, null);
		contentview.setFocusable(true);
		contentview.setFocusableInTouchMode(true);
		mPopupWindow = new PopupWindow(this);
		mPopupWindow.setContentView(contentview);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
		mPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(false);
		mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
	}

	private View shopCart;
	private ViewGroup anim_mask_layout;

	/**
	 * 方法二：第二种；用在Listview，会执行回调在Adapter中
	 */
	// //加入到购物车按钮的点击事件监听
	// holder.shopping_card_item.setOnClickListener(new View.OnClickListener() {//点击事件的设置应该放到这里来，不然会重复设置多次
	// @Override
	// public void onClick(View v) {
	// int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
	// v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
	// buyImg = new ImageView(mContext);// buyImg是动画的图片
	// buyImg.setImageResource(R.drawable.icon_shopping_cart);// 设置buyImg的图片
	// Log.i(null, start_location.length+"");
	// if(oneGoodsListCallBack!=null)
	// oneGoodsListCallBack.setAnim(buyImg, start_location,(String)v.getTag());// 开始执行动画
	// }
	// });

	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			anim_mask_layout.removeView((View) msg.obj);

		};
	};

	/**
	 * @Description: 执行动画
	 * @throws
	 */
	// @Override
	public void setAnim(final View v, int[] start_location, String onePieceId) {
		// if( GlobalApp.getInstance().getUserModel()==null){
		// Bundle bundle = new Bundle();
		// bundle.putBoolean(LoginActivity.IS_FINISH, true);
		// showActivity(getActivity(), LoginActivity.class,bundle);
		// return;
		// }
		// executeAddGoods(onePieceId);

		if (anim_mask_layout == null) anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);// 把动画视图添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = 0;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标

		AlphaAnimation alph = new AlphaAnimation(0.5f, 1.0f);
		alph.setDuration(300);
		alph.setFillAfter(true);

		ScaleAnimation scale = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(300);
		scale.setFillAfter(true);

		AlphaAnimation alph2 = new AlphaAnimation(1.0f, 0.3f);
		alph2.setDuration(800);
		alph2.setStartOffset(300);
		alph2.setFillAfter(true);

		TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, endY);
		translateAnimationX.setStartOffset(300);
		translateAnimationX.setInterpolator(new AccelerateInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);
		translateAnimationX.setDuration(800);

		AnimationSet set = new AnimationSet(false);
		set.addAnimation(alph);
		set.addAnimation(alph2);
		set.addAnimation(scale);
		set.addAnimation(translateAnimationX);

		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				Message msg = mHandler.obtainMessage();
				msg.obj = view;
				mHandler.sendMessageDelayed(msg, 500);
				// buyNum++;//让购买数量加1
				// numberTv.setText(0 + "");
			}
		});
		view.startAnimation(set);
	}

	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	public ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
		FrameLayout animLayout = new FrameLayout(this);
		// FrameLayout animLayout = new FrameLayout(getActivity());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	/**
	 * @Description: 添加视图到动画层
	 * @param
	 * @return void
	 * @throws
	 */
	public View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
		int x = location[0];
		int y = location[1];
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}
}
