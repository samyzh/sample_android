package com.zsy.frame.sample.control.android.a06fourcomponents.activity;

import com.zsy.frame.lib.extend.ui.base.BaseAct;

/**
 * @description：
1、请说一下android的四大组件？他们的作用？
    Activity：Activity是Android程序与用户交互的窗口，是Android构造块中最基本的一种，它需要为保持各界面的状态，做很多持久化的事情，妥善管理生命周期以及一些跳转逻辑。 
    service：后台服务于Activity，封装有一个完整的功能逻辑实现，接受上层指令，完成相关的事务，定义好需要接受的Intent提供同步和异步的接口。 
    Content Provider：是Android提供的第三方应用数据的访问方案，可以派生Content Provider类，对外提供数据，可以像数据库一样进行选择排序，屏蔽内部数据的存储细节，向外提供统一的接口模型，大大简化上层应用，对数据的整合提供了更方便的途径。 
    BroadCast Receiver：接受一种或者多种Intent作触发事件，接受相关消息，做一些简单处理，转换成一条Notification，统一了Android的事件广播模型。
 
 2、请简单解释一下Activity、Intent、intentFilter、BroadcastReceiver
    Activity:相当于程序的脸，用来加载布局
    Intent：意图，用于不同Activity间跳转和数据传递
    intentFilter：
    BroadcastReceiver：广播接受者，当程序接收到广播是作出相应处理。
    
 3、说说mvc模式的原理，它在android中的运用
    M模型层：对数据库操作，网络操作。
    V视图层：xml布局文件
    C控制层：activity
 
 * @author samy
 * @date 2015-3-8 下午7:21:56
 */
public class BaseInfoAct extends BaseAct {

	@Override
	public void setRootView() {

	}

}
