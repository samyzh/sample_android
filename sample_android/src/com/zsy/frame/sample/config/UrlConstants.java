package com.zsy.frame.sample.config;

import com.zsy.frame.sample.support.helps.PreferHelper;

/**
 * 接口地址常量
 */
public class UrlConstants {
	public static String ServerName = getServerDomain();
	/**正式版本、测试版本的唯一切换开关，后续务必注意，不要再引入新的开关 */
	// public static final boolean IS_RELEASE_ENVIRONMENT = isReleaseEnvironment();
	// public static final boolean IS_RELEASE_TEST_ENVIRONMENT = isReleaseTestEnvironment();

	/**正式版本的环境*/
	public static final String RELEASE_SERVER_DOMAIN = "http://interface.178pb.com";
	/**真实数据的测试环境*/
	public static final String RELEASE_TEST_SERVER_DOMAIN = "http://112.124.121.68:9500";
	/**测试版本的环境*/
	// public static final String TEST_SERVER_DOMAIN = "http://192.168.16.240:5567";
	public static final String TEST_SERVER_DOMAIN = "http://192.168.16.34:5567";

	private static final boolean isReleaseEnvironment() {
		return PreferHelper.getInstance().getBoolean(Constants.IS_RELEASE_ENVIRONMENT, true);
	}

	private static final boolean isReleaseTestEnvironment() {
		return PreferHelper.getInstance().getBoolean(Constants.IS_RELEASE_TEST_ENVIRONMENT, false);
	}

	private static final String getServerDomain() {
		// if (isReleaseEnvironment()) {
		return RELEASE_SERVER_DOMAIN;
		// } else {
		// if (isReleaseTestEnvironment()) {
		// return RELEASE_TEST_SERVER_DOMAIN;
		// } else {
		// return TEST_SERVER_DOMAIN;
		// }
		// }
	}

	public static final String HOST = ServerName + "/";

	/** shicm分享的几个固定链接***/
	/**分享的标题的链接***/
	/**分享的图片链接***/
	public static final String SHAREIMAGEURL = "http://www.178pb.com/images/pinbao.png";
	/** shicm分享的几个固定链接***/

	/**服务协议链接*/
	public static final String URL_SERVICE_AGREEMENT = HOST + "pages/app/%E7%94%A8%E6%88%B7%E6%9C%8D%E5%8A%A1%E5%8D%8F%E8%AE%AE.html";
	/**5.3订单列表*/
	public static final String ORDER_LIST = HOST + "Order/GetOrderList";
	/**5.5订单详情*/
	public static final String ORDER_DETAIL = HOST + "Order/GetOrderDetails";
	/**5.5确认订单*/
	public static final String ORDER_SURE_PAY = HOST + "Order/AffirmReceiving";

	/** 3.32.	设为默认用户收货地址 */
	public static final String ADDRESS_DEFAULT = HOST + "User/SetDefaultReceiverAddress";
	/** 3.31.	删除用户收货地址 */
	public static final String ADDRESS_DELETE = HOST + "User/DeleteReceiverAddress";
	/** 3.30.	新增/修改用户收货地址 */
	public static final String ADDRESS_EDIT = HOST + "User/AddOrUpdateReceiverAddress";
	/** 3.25. 地址列表 */
	public static final String ADDRESS_LIST = HOST + "User/GetReceiverAddressList";
	/** 3.4. 修改昵称*/
	public static final String UPDATE_NICKNAME = HOST + "User/UserUpdate";
	/**3.28.	上传修改用户头像*/
	public static final String UPDATE_USER_IMG = HOST + "User/UploadUserImg";
	/** 3.2. 登录验证 */
	public static final String LOGINVALIDATE = HOST + "User/LoginValidate";
	/** 3.3. 注销登陆(第一期在本地处理登出) */
	public static final String LOGINOUT = HOST + "User/LoginOut";
	/** 3.5.获取用户信息 */
	public static final String GET_USERINFO = HOST + "User/GetUserInfo";
	/** 3.5.	找回密码 -验证识别码 */
	public static final String FIND_PASSWORD_VARIFY = HOST + "User/FindPasswordCheckCode";
	/** 3.6. 找回登录密码验证消息 */
	public static final String MSG_FIND_LOGINPWD = HOST + "User/GetFindPasswordSmsCode";
	/** 3.1.注册-检验邀请码 */
	public static final String REGISTE_VARY_REC = HOST + "User/CheckRecommendCode";
	/**3.2.	注册-验证短信识别码 */
	public static final String REGISTE_CHECK = HOST + "User/RegisterCheckCode";
	/** 3.6. 注册验证消息 */
	public static final String MSG_REGISTE = HOST + "User/GetSMSverification";
	/** 3.6. 验证手机注册消息 */
	public static final String PHONE_ISREGISTER_REGISTE = HOST + "User/CheckPhoneIsExist";
	/** 3.7.绑定手机*/
	public static final String BANDINGPHONE = HOST + "User/BandingPhone";
	/** 3.8.绑定手机发送验证码 */
	public static final String GETMSGBINDNEWPHONEGETCODEREQ = HOST + "User/GetMsgBindNewPhoneGetCodeReq";
	/** 3.9.更绑手机发送验证码 */
	public static final String GETMSGBINDCHANGEPHONEGETCODEREQ = HOST + "User/GetMsgBindChangePhoneGetCodeReq";
	/** 3.10.更绑手机验证 */
	public static final String CHANGEPHONECHECK = HOST + "User/ChangePhoneCheck";
	/** 3.12修改密码-(登陆密码\支付密码)*/
	public static final String CHANGEPASSWORD = HOST + "User/ChangePassword";
	/** 3.13. 验证密码 */
	public static final String VERIFICATIONPASSWORD = HOST + "User/VerificationPassword";
	/** 3.14修改密码获取短信验证码(交易密码\支付密码) */
	public static final String GETFINDPASSWORDSMSCODE = HOST + "User/GetFindPasswordSmsCode";
	/** 3.19  获取我的银行卡列表 */
	public static final String GETMYBANKCARDLIST = HOST + "User/GetMyBankCardList";
	/**3.24.设置默认银行卡*/
	public static final String SETDEFAULTBANKCARD = HOST + "User/SetDefaultBankCard";
	/** 3.20  添加我的银行卡 */
	public static final String ADDBANKCARD = HOST + "User/AddBankCard";
	/** 3.27  修改我的银行卡 */
	public static final String UPDATEBANKCARD = HOST + "User/UpdateBankCard";
	/**3.21删除银行卡*/
	public static final String DELETEBANKCARD = HOST + "User/DeleteBankCard";
	/**3.21.	提现-获取验证码*/
	public static final String GETWITHDRAWALSMSCODE = HOST + "User/GetWithdrawalSmsCode";
	/**3.22.	提现 [转出到银行卡]*/
	public static final String WITHDRAWAL = HOST + "User/Withdrawal";
	/**3.21.	连连支付转账（转入）    */
	public static final String LIANLIANPAY = HOST + "User/LianLianPay";
	/**3.21.	连连支付转账（转入）    */
	public static final String YINLIANPAY = HOST + "User/YinLianPay";
	/**获取开户银行信息*/
	public static final String GETBANKLIST = HOST + "User/GetBankList";
	/**获取我的邀请信息*/
	public static final String GET_MY_INVITE_INFO = HOST + "User/GetMyRecommend";
	/**3.22实名认证*/
	public static final String REALNAMEAUTHENTICTION = HOST + "User/RealNameAuthentication";
	/**3.34.意见反馈*/
	public static final String PUBLISHFEEDBACK = HOST + "User/PublishFeedback";
	/**4.2获取商品列表*/
	public static final String GETPRODUCTLIST = HOST + "Product/GetProductList";
	/**4.3获取商家主页面商品*/
	public static final String GETMERCHANTPRODUCTLIST = HOST + "Product/GetMerchantProductList";
	/**4.4删除商品浏览记录*/
	public static final String CLEARBROWSERECORD = HOST + "Product/ClearBrowseRecord";
	/**4.5.	获取商品详情*/
	public static final String GETPRODUCTDETAILS = HOST + "Product/GetProductDetails";
	/**4.8.	商品优惠价格列表*/
	public static final String GETFAVORABLEPRICELIST = HOST + "Product/GetFavorablePriceList";
	/**4.10.	商品评价列表*/
	public static final String GETPRODUCTCOMMENTLIST = HOST + "Product/GetProductCommentList";
	/**5.1.	下单中获取商品价格信息（确认商品价格准确性）*/
	public static final String GETPRODUCTPRICEFORORDER = HOST + "Order/GetProductPriceForOrder";
	/**5.2.	创建订单*/
	public static final String PAYVALIDATE = HOST + "Order/PayValidate";
	/**5.3.	获取订单处理状态*/
	public static final String GETORDERSTATUS = HOST + "Order/GetOrderStatus";
	/**5.4.	获取订单处理状态及拼购结束时间*/
	public static final String GETORDERSTATUSANDENDDT = HOST + "Order/GetOrderStatusAndEndDt";
	/**5.3.	确定支付*/
	public static final String ORDERPAY = HOST + "Order/OrderPay";
	/**账户资金明细*/
	public static final String ACCOUNT_MONEY_DETAIL = HOST + "User/GetCapitalRecordList";
	/** 注册：提交注册信息 */
	public static final String REGISTER = HOST + "User/Register";
	/**获取分类*/
	public static final String GET_PRODUCT_CATEGORY_LIST = HOST + "Product/GetProductCategoryList";
	/**获取热门搜索词*/
	public static final String GET_HOT_PRODUCT_KEYWORD = HOST + "Product/GetHotProductKeywordList";
	/**获取广告列表*/
	public static final String GET_ADVERTISEMENTINFO = HOST + "Product/GetAdvertisementInfo";
	/**商品热门搜索关键字列表*/
	public static final String GET_HOT_PRODUCT_KEYWORD_LIST = HOST + "Product/GetHotProductKeywordList";
	/**商品热门搜索关键字列表*/
	public static final String GET_VERSION_INFO = HOST + "Support/GetVersionInfo";
	/**获取拼宝动态新闻列表*/
	public static final String GET_DYNAMIC_NEWSLIST = HOST + "Product/ GetDynamicNewsList";
	/**我的邀请明细*/
	public static final String MY_INVITE_DEAL_DETAIL = HOST + "User/GetMyRecommendDetailed";
	/**获取成交价*/
	public static final String GET_TURNOVER = HOST + "Order/GetTurnover";
	/**充值提前验证**/
	public static final String POST_AMOUNT_ISSUBMIT = HOST + "User/AmountIsSubmit";
	/******拼宝第二期新增接口***********************************************************/
	/**获取提醒列表**/
	public static final String GET_REMIND_LIST = HOST + "Product/GetRemindList";
	/**删除提醒**/
	public static final String DELETE_REMIND = HOST + "Product/DeleteRemind";
	/**删除提醒**/
	public static final String ADD_REMIND = HOST + "Product/AddRemind";
	/**获取推荐新产品列表**/
	public static final String PRODUCT_RECOMMENDLIST = HOST + "Product/ProductRecommendList";
	/**销售快报功能中获取销售额统计列表**/
	public static final String GETTURNOVERLIST = HOST + "Order/GetTurnoverList";
}
