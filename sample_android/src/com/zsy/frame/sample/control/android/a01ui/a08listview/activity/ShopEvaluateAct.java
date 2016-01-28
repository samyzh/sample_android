package com.zsy.frame.sample.control.android.a01ui.a08listview.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.pinnedheader.PinnedHeaderPullRefreshListView;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.config.Constants;
import com.zsy.frame.sample.control.android.a01ui.a08listview.adapter.ShopEvaluteAdapter2;
import com.zsy.frame.sample.support.bean.ProductCommentArray;
import com.zsy.frame.sample.support.bean.ShopEvaluateBean2;
import com.zsy.frame.sample.support.bean.ShopEvaluateBean2.ShopEvaluate;
import com.zsy.frame.sample.support.helps.TitleBarHelper;

/**
 * @description：查看点评;1.10.查看商家及菜品评价
 * @author samy
 * @date 2014年9月16日 上午11:21:44
 */
public class ShopEvaluateAct extends BaseAct {
	private TextView comment_count_tv;
	private RatingBar average_comment_level_rb;

	private PinnedHeaderPullRefreshListView dish_evaluate_lv;
	private ShopEvaluteAdapter2 adapter;
	private RelativeLayout mTotalNumber;
	/** 商品评论列表 */
	public List<ProductCommentArray> ProductCommentArray = new ArrayList<ProductCommentArray>();
	private ProductCommentArray commentArray;
	private String productId;
	private TitleBarHelper titleBarHelper;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a08listview_shop_evaluate);
	}

	@Override
	protected void initData() {
		super.initData();
		productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
	}

	/** 模拟数据 TODO */
	private List<ShopEvaluate> fakeData() {
		return new ShopEvaluateBean2().fakeData();
	}

	/**
	 * 异常点击重试
	 */
	private View.OnClickListener refreshListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			dish_evaluate_lv.setFirstRefreshing();
		}
	};

	private void fillViewFromNet() {
		dismissLoadingDialog();
		dish_evaluate_lv.wrapOnRefreshComplete();
		mTotalNumber.setVisibility(View.VISIBLE);
		adapter.addItems(fakeData());
		// if (result != null && result.getRs() != null) {
		// if (CURRENT_PAGE == 1) {
		// adapter.clearGroup(false);
		// }
		// int totalSize = result.getTotalSize();
		// titleBarHelper.setTitleMsg(String.format(context.getString(R.string.post_evaluation_change),totalSize));
		// if(null != result.getRs().commentArray && result.getRs().commentArray.size() > 0){
		// comment_count_tv.setText("总评分  " + result.getRs().averageCommentLevel + "");
		// average_comment_level_rb.setRating(result.getRs().averageCommentLevel);
		// adapter.addItems(result.getRs().commentArray);
		//
		// changeRefreshMode(totalSize);
		// }
		// else{
		// mTotalNumber.setVisibility(View.GONE);
		// showOverLayTip(R.string.common_empty_evaluate);
		// mOverlayLayout.setOnClickListener(null);
		// }
		// }
	}

	private void refreshData() {
		fillViewFromNet();
	}

	private void changeRefreshMode(int totalSize) {
		if (adapter.getCount() >= totalSize) {
			dish_evaluate_lv.setMode(Mode.PULL_FROM_START);
		}
		else {
			dish_evaluate_lv.setMode(Mode.BOTH);
		}
	}

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);
		titleBarHelper = new TitleBarHelper(this, -1, R.string.post_evaluation);
		dish_evaluate_lv = (PinnedHeaderPullRefreshListView) findViewById(R.id.shop_evaluate_lv);
		comment_count_tv = (TextView) findViewById(R.id.comment_count_tv);
		average_comment_level_rb = (RatingBar) findViewById(R.id.average_comment_level_rb);
		mTotalNumber = (RelativeLayout) findViewById(R.id.total_rl_number);
		// adapter = new ShopEvaluteAdapter(this);TODO
		adapter = new ShopEvaluteAdapter2(this);
		// 添加悬浮的头部
		dish_evaluate_lv.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.item_a01ui_a08listview_shop_evaluate_header, dish_evaluate_lv, false));
		dish_evaluate_lv.setAdapter(adapter);
		dish_evaluate_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				CURRENT_PAGE = 1;
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				CURRENT_PAGE++;
				refreshData();
			}
		});
		dish_evaluate_lv.setMode(Mode.PULL_FROM_START);
		refreshData();
	}
}
