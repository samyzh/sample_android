package com.zsy.frame.sample.control.android.a06fourcomponents.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

public class StartMethodAct extends BaseAct {
	@BindView(id = R.id.button1, click = true)
	private Button button1;
	@BindView(id = R.id.button2, click = true)
	private Button button2;
	@BindView(id = R.id.button3, click = true)
	private Button button3;
	@BindView(id = R.id.button4, click = true)
	private Button button4;
	@BindView(id = R.id.button5, click = true)
	private Button button5;
	@BindView(id = R.id.button6, click = true)
	private Button button6;
	@BindView(id = R.id.button7, click = true)
	private Button button7;
	@BindView(id = R.id.button8, click = true)
	private Button button8;
	@BindView(id = R.id.button9, click = true)
	private Button button9;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a06fourcomponents_activity_startmethod);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		Intent intent = null;
		if (v.getId() == R.id.button5 || v.getId() == R.id.button6 || v.getId() == R.id.button7 || v.getId() == R.id.button8 || v.getId() == R.id.button9) {
			intent = new Intent();
			intent.setClass(aty, StartMethodTestAAct.class);
		}
		switch (v.getId()) {
			case R.id.button1:
				showActivity(aty, StartMethodTestAAct.class);
				break;
			case R.id.button2:
				showActivity(aty, StartMethodTestBAct.class);
				break;
			case R.id.button3:
				showActivity(aty, StartMethodTestCAct.class);
				break;
			case R.id.button4:
				showActivity(aty, StartMethodTestDAct.class);
				break;
			case R.id.button5:
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 和standard一样
				startActivity(intent);
				break;
			case R.id.button6:
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 和singleTop一样
				startActivity(intent);
				break;
			case R.id.button7:
				// 这个FLAG启动的Activity会把要启动的Activity之上（包含自身）的Activity全部弹出栈空间。
				// 例如：原来栈中的结构是A B C D ，从D中跳转到B，释放顺序为D C B，
				// 然后重新创建B置于栈顶，栈中的结构就变为了A B了。（这个方法可以用来关闭多个Activity）
				// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 和singleTask 有点不一样
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);// 这样设置和singleTask 有点一样
				startActivity(intent);
				break;
			case R.id.button8:
				// api11以上用到；
				// 例如栈中原有A B C D，需要从D跳转到B，依次释放D C B A,然后创建B，置于栈顶
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				break;
			case R.id.button9:// 从当前跳转一个界面的角度来看的：
				// 这里一般设置Login登录界面处理。被启动界面已经在栈里面的,而且还有就是第二个；
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				startActivity(intent);
				break;
		}
	}
}

/**
 * 当使用Launch Mode 来改变系统默认的任务调度的时候，如果是用到Single Task或者Single Instance的时候，
 * 还要注意到Affinity的使用，要跟Affinity配合使用，可能才能达到我们期望中的效果。而Affinity，其实是Android提供的一个表从属意义的参数，类似于一个Tag值，
 * 它表明当前Activity属于哪一个Tag，相当的Affinity值的Activity，如果不使用其他的标志，
 * 如Single Instance之类，那么都会在存在于同一个task中。一般情况下，我们并不定义Task Affinity值，则其默认的值就是当前App的包名。
 */

/**
 *  《Android深入透析》之 浅析Activity启动模式
前言：
      Activity的启动模式是一个既基础又容易忽视的问题，但是这个问题有个深刻的认识，对程序员写一个稳定高效的Android程序帮助很大，今天，在B哥引导下，我们对Activity启动模式、Intent Flags做了一番很好的探究，可以这么说，如果你不熟悉或了解Activity的启动模式或者Flags怎么用，今后你在实际开发中，绝对会被困扰，回过头来重新学习这一节，举个例子：有人写出的客户端，为什么崩溃了，底下仍然有一个乃至N个该应用的界面，如果你熟读并且准确理解此章，必然不会出此错误。

探究历程：
     ①   什么是栈

     ②   Activity栈

     ③   Task

     ④  Activity启动模式

     ⑤  Activity栈和Task联系

     ⑥  Intent Flags 

     ⑦  Activity相关属性taskAffinity

 1.    什么是栈

1.1   栈

       栈是一种常用的数据结构，栈只允许访问栈顶的元素，栈就像一个子弹梭子（如图所示），每次都只能取栈顶上的东西，而对于栈就只能每次访问它的栈顶元素，从而可以达到保护栈顶元素以下的其他元素．”先进后出”或”后进先出”就是栈的一大特点，先进栈的元素总是要等到后进栈的元素出栈以后才能出栈．递归就是利用到了系统栈，暂时保存临时结果，对临时结果进行保护．

 

1.2   定义栈（Stack）

      栈的定义栈（Stack）是限制仅在表的一端进行插入和删除运算的线性表。

(1)  通常称插入、删除的这一端为栈顶（Top），另一端称为栈底（Bottom）。

(2)  当表中没有元素时称为空栈。

(3)  栈为后进先出（Last In First Out）的线性表，简称为LIFO表。栈的修改是按后进先出的原则进行。每次删除（退栈）的总是当前栈中"最新"的元素，即最后插入（进栈）的元素，而最先插入的是被放在栈的底部，要到最后才能弹出。

 

1.3  栈的操作：压栈、弹栈

 

2.   Activity中的栈

       Android的管理主要是通过Activity栈来进行，当一个Activity启动时，系统会根据其配置将它压入到一个特定的栈中，系统处于运行状态。当用户点击返回或则FINISH（）了该Activity，那么它便会被从栈中压出，随之摧毁，按照Activity的生命周期可以知道，如果当前显示的栈中的Activity没有被弹出栈（即调用Activity的ondestory方法），那么通过Intent打开一个新的Activity时候，会将新打开的Activity压入到栈顶.  如下 图（1）



 

                                                                   图（1）

3.   Task

       Task就是Activity的任务栈，它简单的说，就是一组以栈的模式聚集在一起的Activity组件集合,记录着用户的行为。（这里只提它和Activity的启动模式来讲）

 

4.   Activity启动模式

属性：android:launchMode  

作用：通过主配置文件AndroidManifest.xml中activity的launchMode  属性决定Activity如何启动。

描述：这里有四种模式，与Intent对象中的Activity Flags的属性（FLAG_ACTIVITY_*变量）共同作用，来决定Activity如何启动来处理Intent。

四种模式：

    ①   "standard"  --默认模式
    ②   "singleTop"
    ③   "singleTask"
    ④   "singleInstance"

以下举例说明它们的区别：

A.    standard：

       Activity的默认加载方法，该方法会启动创建一个新的activity实例，同时将该实例压入到栈中（不管该activity是否已经存在在Task栈中，都是采用new操作），并调用这个新Activity的OnCreate()方法。。

例如： 栈中顺序是A B C D ，此时D通过Intent跳转到A，那么栈中结构就变成 A B C D A ，点击返回按钮的 显示顺序是 D C B A，依次摧毁。如下图（2）

       一句话记忆：无论栈中是否已经创建过，它都会创建一个新的并置于栈顶并显示，调用oncreate方法。

       使用场景：默认的Activity的启动模式。



                                                       图（2） 

B.    singleTop：

       假设栈内已存在A B C D

       singleTop模式下，分以下两种情况。

1)    当前App应用展示的是D或者栈顶是D，如果这个时候通过Intent需要打开D，那么不会重新创建一个新的D实例，而是调用栈顶的D，并调用它的onNewIntent方法（注意不是oncreate方法，只有创建一个新的实例Activity才会调用Oncreate方法）所以栈中的结构依旧为A B C D。如下图（3）



                                                      图（3）

       思考： 那么，让我们想想，这种情况什么时候会发生呢？好像有点矛盾，当前的显示的或者栈顶的activity，通过Intent或者打开同样的activity，有这种用法和必要吗？

      古语有云：万物皆有其法，android提供了这种用法，那么一定有其道理，至于如何应用和怎样应用，那么有待读者验证，在这里我们只是抛砖引玉，不过可以提供一个这样的场景论证其有效性。

      比如：当前的app栈顶为A，这时候，突然来了个消息通知，这个消息通知需要通过Intent去打开A，那么我们点击这个消息通知打开A的时候，此场景就复现了，它将不会创建一个新的A，而是复用栈顶的A，并执行onNewIntent方法。

2)     当前App应用展示的是D或者栈顶是D，如果这个时候需要打开B，那么由于B不处于栈顶，所以会新建一个B实例并压入到栈中,并调用新创建B的onCreate方法，结构就变成了A B C D B。如下图（4）



                                                     图（4）

      一句话记忆：只有需要打开的A在栈顶，那么不会创建一个新的A，并调用onNewIntent方法，如果需要打开的A不在栈顶，那么不论A在栈中有还是没有，都会创建一个新的A放入栈顶，并执行onCreate方法。

 C.    singleTask：

       singleTask模式下，Task栈中有且只能有一个对应Activity的实例。

例如：当前栈的结构为：A B C D。

       此时D通过Intent打开B，则栈的结构变成了：A B。其中的C和D被栈弹出销毁了，也就是说位于B之上的实例都被销毁了。而不会创建一个新的B实例，而是使用栈中原有的B，此时调用原B的onNewIntent()方法。如下图（5），（6）



                                                  图（5）



                                                    图（6）

       经验谈：此模式较为常见，在activity栈中，因为应用需要，通常需要打开多个相同或者不同的Activity，那么这样，Activity栈会越来越大，从而消耗的内存也会越来越大，如果Activity配置了这个属性就无敌了，它会怎么做呢?如果在已经打开A后打开了B C D E…等等，如果这个时候你需要打开A，但又想销毁B C D E…的时候，此属性就满足需求了，这时候你会发现你内存使用就下降了，因为B C D E…已经被销毁。

        经验谈：当然，并不是说设置了singleTask就通用一切了，前面说过了，每种用法都有其自身道理，singleTask想做到的是，显示当前堆栈中已存在的Activity并不重新创建，而是复用，如果堆栈中已存在需要打开的Activity，那么先将此栈中Activity之上的其它Activity销毁，露出已存在的Activity，并执行它的onNewIntent方法。

      当然，如果当前栈中不存在，那就创建一个新的置于栈顶。

      一句话记忆：只要A存在栈内，那么就将A之上的全部销毁（不包含A），同时显示并复用A，执行onNewIntent方法。否则，创建一个新A置于栈顶。

D.   singleInstance：

      singleInstance模式下，会创建一个新的Task栈。

      例如：当前栈的结构为：A B C D。

      在D中通过Intent打开E（E的模式为singleInstance），那么会新建一个Task 栈2，栈Task 1中结构依旧为A B C D，栈2中结构为E，此时屏幕中显示E。

      此类模式，通常会在打开另一个App才会使用。比如：打电话，使用平率高，耗资源的应用。在应用中打开微信、新浪微博等客户端。如下图（7）



                                                       图（7）

5.   Activity栈和Task联系

      Task简单的就是一组以栈的模式聚集在一起的Activity组件集合，类似于一个填充了Activity的容器，最先加入的Activity会处于容器最下面，最后加入的处于容器最上面，而从Task中取出Activity是从最顶端先取出，最后取出的是最开始添加Activity，这就是后进先出（Last In First Out）模式，而Activity在Task中的顺序是可以控制的，在Activity跳转时用到Intent Flag可以设置新建activity的创建方式（这里就涉及到了Intent Flag的使用）。

 

6.    Intent Flags 

       Flags: 表示Intent的标志位，常用于Activity的场景中，它和Activity的启动模式有着密切的联系，简单说，flag的有效组合（通常用“|“组合使用）决定如何打开Activity。

   ①   "standard"  --默认模式
    ②   "singleTop"
    ③   "singleTask"
    ④   "singleInstance"

下面列举的是和本文主题相关的Flags属性：

（1）——  Intent.FLAG_ACTIVITY_NEW_TASK （默认）与standard

      默认的跳转类型,它会重新创建一个新的Activity。 


（2）——  FLAG_ACTIVITY_SINGLE_TOP 与singleTop

      这个FLAG就相当于启动模式中的singletop，请参考singletop说明。

（3）——  FLAG_ACTIVITY_CLEAR_TOP与 singleTask 有点不一样

       这个FLAG启动的Activity会把要启动的Activity之上（包含自身）的Activity全部弹出栈空间。例如：原来栈中的结构是A B C D ，从D中跳转到B，释放顺序为D C B，然后重新创建B置于栈顶，栈中的结构就变为了A B了。（这个方法可以用来关闭多个Activity）

       经验：需要销毁栈中A之前的多个activity，但并不想销毁A，就需要FLAG_ACTIVITY_CLEAR_TOP和FLAG_ACTIVITY_SINGLE_TOP组合使用

（从上一个界面角度来看的：）FLAG_ACTIVITY_CLEAR_TOP和FLAG_ACTIVITY_SINGLE_TOP

（从当前跳转一个界面的角度来看的：）

toIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
toIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
（4）——  FLAG_ACTIVITY_CLEAR_TASK

       这个Flag使用的前置条件为：API level 11 以上版本，并且需要与FLAG_ACTIVITY_NEW_TASK一起使用。

此标识，用于释放当前栈中所有的activity，然后再创建新的Activity对象置于栈顶。

       例如栈中原有A B C D，需要从D跳转到B，依次释放D C B A,然后创建B，置于栈顶。 

7.    Activity相关属性taskAffinity

       Activity 中的 android:taskAffinity 这个属性介绍：

       Activity为Task拥有的一个affinity。拥有相同的affinity的Activity理论上属于相同的Task（在用户的角度是相同的“应用程序”）。Task的affinity是由它的根Activity决定的。 
       affinity决定两件事情——Activity重新宿主的Task（参考allowTaskReparenting特性）和使用FLAG_ACTIVITY_NEW_TASK标志启动的Activity宿主的Task。
       默认情况，一个应用程序中的所有Activity都拥有相同的affinity。捏可以设定这个特性来重组它们，甚至可以把不同应用程序中定义的Activity放置到相同的Task中。为了明确Activity不宿主特定的Task，设定该特性为空的字符串。
       如果这个特性没有设置，Activity将从应用程序的设定那里继承下来（参考<application>元素的taskAffinity特性）。应用程序默认的affinity的名字是<manifest>元素中设定的package名。

       android:taskAffinity只有通过标志位为FLAG_ACTIVITY_NEW_TASK的Intent启动Activity时，该Activity的这个属性才会生效，系统才会将具有相同Task亲和力的Task切换到前台，然后启动该Activity，否则该Activity仍然运行在启动它的Task中。

来源： <http://www.cnblogs.com/duoduohuakai/p/3973094.html>

 
 */
