package com.zsy.frame.sample.support.bean;

public class ProductCommentArray {
	/**评论ID*/
	public String commentId;
	/**当前评论内容*/
	public String commentContent;
	/**当前评论星级 1-5*/
	public int commentLevel;
	/**当前评论时间(时间戳)*/
	public long commentDt;
	/**当前评论是否匿名	 1-是 0-否*/
	public int isAnonymous;
	/**当前评论用户昵称(没有昵称显示电话号码)*/
	public String commentUser;
	/**评论的手机号码*/
	public String phone;

	@Override
	public String toString() {
		return "ProductCommentArray [commentId=" + commentId + ", commentContent=" + commentContent + ", commentLevel=" + commentLevel + ", commentDt=" + commentDt + ", isAnonymous=" + isAnonymous + ", commentUser=" + commentUser + ", phone=" + phone + "]";
	}

}
