package com.zsy.frame.sample.control.android.a06fourcomponents.theyrelation.intent;

import android.content.Intent;
import android.os.Bundle;

import com.zsy.frame.lib.extend.ui.base.BaseAct;

/**
 * @description：
1、要弄清楚这个问题，首先需要弄明白什么是implicit(隐藏) intent什么是explicit(明确) intent。 
Explicit Intent明确的指定了要启动的Acitivity ，比如以下Java代码： 
Intent intent= new Intent(this, B.class) 
Implicit Intent没有明确的指定要启动哪个Activity ，而是通过设置一些Intent Filter来让系统去筛选合适的Acitivity去启动。 
2、intent到底发给哪个activity，需要进行三个匹配，一个是action，一个是category，一个是data。 
理论上来说，如果intent不指定category，那么无论intent filter的内容是什么都应该是匹配的。
但是，如果是implicit intent，Android默认给加上一个CATEGORY_DEFAULT，这样的话如果intent filter中没有android.intent.category.DEFAULT这个category的话，匹配测试就会失败。
所以，如果你的 activity支持接收implicit intent的话就一定要在intent filter中加入android.intent.category.DEFAULT。 
例外情况是：android.intent.category.MAIN和android.intent.category.LAUNCHER的filter中没有必要加入android.intent.category.DEFAULT，当然加入也没有问题。 
我们定义的activity如果接受implicit intent的话，intent filer就一定要加上android.intent.category.DEFAULT这个category。 



2 Intent.Action_CALL
Stirng: android.intent.action.CALL
呼叫指定的电话号码。
Input:电话号码。数据格式为：tel:+phone number 
Output:Nothing 
Intent intent=new Intent(); 
intent.setAction(Intent.ACTION_CALL);   
intent.setData(Uri.parse("tel:1320010001");
startActivity(intent);

3 Intent.Action.DIAL
String: action.intent.action.DIAL
调用拨号面板
Intent intent=new Intent();
intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
intent.setData(Uri.parse("tel:1320010001");
startActivity(intent); 


Input:电话号码。数据格式为：tel:+phone number 
Output:Nothing
说明：打开Android的拨号UI。如果没有设置数据，则打开一个空的UI，如果设置数据，action.DIAL则通过调用getData()获取电话号码。
但设置电话号码的数据格式为 tel:+phone number. 

4 Intent.Action.ALL_APPS
String: andriod.intent.action.ALL_APPS
列出所有的应用。
Input：Nothing.
Output:Nothing.


Intent 传递值问题处理；
getSerializableExtra可以传递对象和数组对象；
private List<ShopTakeoutBean> beans;
beans = (List<ShopTakeoutBean>) getIntent().getSerializableExtra(Constant.LIST_SHOP_TAKEOUT_BEAN);
 * @author samy
 * @date 2015-3-21 下午8:20:57
 */
public class IntentIntroduceAct extends BaseAct {

	@Override
	public void setRootView() {

	}

	@Override
	public void initialize(Bundle savedInstanceState) {
		super.initialize(savedInstanceState);
		// 可以对应配置文件中的信息配置
		// 就是说如果category和action都相同的话，会跳出一个对话框让用户选择要启动哪一个activity;
		Intent intent = new Intent();
		intent.setAction("");
		// 系统默认配置的是这个
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.addCategory("");
	}

}
