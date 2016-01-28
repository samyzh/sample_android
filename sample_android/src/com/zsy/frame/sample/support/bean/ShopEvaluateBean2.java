package com.zsy.frame.sample.support.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 详细描述：1.10.查看商家及菜品评价
 * @author samy
 * @date 2014年9月18日 下午11:33:25
 */
public class ShopEvaluateBean2 {
	/**商品总评论星级 1-5*/
	public float averageCommentLevel;
	/**商品评论列表*/
	public List<ShopEvaluate> commentArray = new ArrayList<ShopEvaluate>();

	public class ShopEvaluate {
		public String period;// 期数
		public String periodCount;// 每期评价数
		public List<ProductCommentArray> comments = new ArrayList<ProductCommentArray>();// 每期评价列表
	}

	public List<ShopEvaluate> fakeData() {
		ArrayList<ShopEvaluate> list = new ArrayList<ShopEvaluate>();
		ArrayList<ProductCommentArray> lis2t;
		ShopEvaluate shopEvaluate;
		ProductCommentArray comment;
		for (int i = 0; i < 3; i++) {
			shopEvaluate = new ShopEvaluate();
			lis2t = new ArrayList<ProductCommentArray>();
			shopEvaluate.period = i + "";// 期数
			for (int j = 0; j < 4; j++) {
				comment = new ProductCommentArray();
				comment.commentContent = "safsafsa";
				comment.commentDt = 4561786;
				comment.commentId = "1546";
				comment.commentLevel = 3;
				comment.commentUser = "sfdsf";
				comment.isAnonymous = 1;
				comment.phone = "461316464";
				lis2t.add(comment);
			}
			shopEvaluate.comments = lis2t;
			list.add(shopEvaluate);
		}

		return list;
	}
}
