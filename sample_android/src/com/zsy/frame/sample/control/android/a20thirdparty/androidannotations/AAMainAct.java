package com.zsy.frame.sample.control.android.a20thirdparty.androidannotations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.res.BooleanRes;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * @description：
 * 注解框架---AndroidAnnotations这里强调了几点：
AndroidAnnotations简称为AA，大量的使用注解，不会对APP的造成不良影响，会影响到APP的执行性能。
分析主流注解框架：xUtils、ButterKnife、Dragger和Roboguice，说道：实现原理是一致的，都是通过反射机制实现的，在Runtime运行期去反射类中带有注解的Field和Method，然后再去执行注解相对应的逻辑代码，这样做：在APP的运行期执行的，会造成执行的效率下降，执行时间变长的缺点。
-->这里我就不发表意见，没有用过，不知道，但是查找相关的资料，可不是这么说，有点夸大AA，降低其他框架的嫌疑。
工作的原理:
在编译器中加了一层额外的自动编译步骤，用来生成基于你源码的代码。
使用AA的注解在编译期间就已经自动生成了对应的子类，运行期运行的其实就是这个子类.则不会造成任何负面的影响
使用详解参照：
Android 最火的快速开发框架AndroidAnnotations使用详解
Android开发框架androidannotations使用初步
体验AndroidAnnotations
总结：从资料的分析来看，AndroidAnnotations像是综合了其他的几个框架的优点，通过注解，减少了繁琐(R.id.btn)的使用，并在编译的时候，将相应的注解进行转换。即:加快了开发的速度，又不影响app的性能。
综合考虑：
Android依赖注入：Dagger、RoboGuice和ButterKnife 这里有三者的比较，但是仅仅是译文，没有一个很好的解释到位。
相反：高手速成android开源项目[tool篇] 这里有很好的解释
AndroidAnnotations特点：
(1) 依赖注入：包括view，extras，系统服务，资源等等
(2) 简单的线程模型，通过annotation表示方法运行在ui线程还是后台线程
(3) 事件绑定：通过annotation表示view的响应事件，不用在写内部类
(4) REST客户端：定义客户端接口，自动生成REST请求的实现
(5) 没有你想象的复杂：AndroidAnnotations只是在在编译时生成相应子类
(6) 不影响应用性能：仅50kb，在编译时完成，不会对运行时有性能影响。
PS：AndroidAnnotations与roboguice的比较：roboguice通过运行时读取annotations进行反射，所以可能影响应用性能，而AndroidAnnotations在编译时生成子类，所以对性能没有影响
roboguice 帮你处理了很多代码异常，利用annotation使得更少的代码完成项目
butterknife 利用annotation帮你快速完成View的初始化，减少代码
Dagger 依赖注入，适用于Android和Java
 * 
 * 
 * 
 * AndroidAnnotations是一个能够让你快速进行Android开发的开源框架，它能让你专注于真正重要的地方。
使代码更加精简，使项目更加容易维护，它的目标就是“Fast Android Development.Easy maintainance”。
通过一段时间的使用发现，相比原生的Android开发，确实能够让你少些很多代码，它的首页也给出了一个简单
的例子,通过例子也可以看到代码比之前几乎少写了一半。
No Magic  [不知道为什么这样称呼，直译过来就是：无魔法，它的意思是：AndroidAnnotations在编译
的时候会产生一个子类(接下来你会明白)，你查看这个子类，可以看到它是如何工作的]

 * 
 * 除了@Eactivity @ViewById@Click之外还有
@EApplication
@EBean
@EFragment
@EService
@EView
@EviewGroup
@App
@Bean
@Fullscreen
 * 
注意的一点就是：使用AndroidAnnotations千万要记得，编译的时候会生成一个子类，这个子类的名称就是在
原来的类之后加了一个下划线“_”，比如这个例子产生的子类名称为“MyActivity_”，这就需要你在注册这个Activity的时候，在
AndroidManifest.xml中将 MyActivity 改为 MyActivity_ ，使用的时候也是使用MyActivity_来表示此类，如从另一个Activity跳转
到此节目就要这样用：startActivity(new Intent(this,MyActivity_.class));  
 * @author samy
 * @date 2015-4-17 下午8:35:23
 */

@EActivity(R.layout.activity_a20thirdparty_androidannotations_aamain)
// Sets content view to R.layout.translate
public class AAMainAct extends Activity {
	@ViewById
	// Injects R.id.textInput
	// 提供id来生成控件，如果不指定ID，默认以控件名进行查找，如上面的myEditText
//	注意这里的配置有时会出现问题，我们得这样配置：
//	但是这样会有一个问题，运行时就会报出：NullPointerException的错误，我们就不能在myButton()方法中	直接使用，而是要在@AfterView注释的方法中使用
//	@AfterView  
//	void init(){  
//	    textView.setText("Hello");  
//	}  
	EditText myEditText;

	@ViewById(R.id.myTextView)
	// Injects R.id.myTextView
	TextView textView;

	@AnimationRes
	// Injects android.R.anim.fade_in
	Animation fadeIn;

	@StringRes(R.string.hello)
	String helloFormat;

	@ColorRes
	int androidColor;

	@BooleanRes
	boolean someBoolean;

	@SystemService
	NotificationManager notificationManager;

	@SystemService
	WindowManager windowManager;

	/**
	 * AndroidAnnotations gracefully handles support for onBackPressed, whether you use ECLAIR (2.0), or pre ECLAIR android version.
	 */
	public void onBackPressed() {
		super.onBackPressed();
		Toast.makeText(this, "Back key pressed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// windowManager should not be null
		windowManager.getDefaultDisplay();
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}

	// //事件控制，可以以按钮的id作为方法名,同时支持的事件还有onLongClick，onTextChange等
	// @LongClick
	// When R.id.myButtonClicked button is clicked
	// android:id="@+id/myButton"
	// 两种点击事件方法都可以的；myButton（） myButtonClicked（）
	@Click
	void myButton() {
		// void myButtonClicked() {
		String name = myEditText.getText().toString();
		setProgressBarIndeterminateVisibility(true);
		someBackgroundWork(name, 5);
	}

	/**
	 * 对于@Click，方法名和xml文件中的id一样就可以这样写，AndroidAnnotations会自动识别，对于
	 * 多个Button，可以写多个@Click，也可以在这样
	 */
	// @Click({R.id.button1,R.id.button2,R.id.button3})
	// void buttonClicked(Button bt){
	// switch(bt.getId()){
	// case R.id.button1: //
	// break;
	// ...
	// }
	// }

	@Background
	// Executed in a background thread
	// 开启新线程后台运行，注意不要引用UI控件,而且返回值类型一定是void
	void someBackgroundWork(String name, long timeToDoSomeLongComputation) {
		try {
			TimeUnit.SECONDS.sleep(timeToDoSomeLongComputation);
		}
		catch (InterruptedException e) {
		}

		String message = String.format(helloFormat, name);

		updateUi(message, androidColor);

		showNotificationsDelayed();
	}

	@UiThread
	// Executed in the ui thread//UI线程
	void updateUi(String message, int color) {
		setProgressBarIndeterminateVisibility(false);
		textView.setText(message);
		textView.setTextColor(color);
	}

	@UiThread(delay = 2000)
	// 可以设置延时时间，以毫秒为单位
	void showNotificationsDelayed() {
		Notification notification = new Notification(R.drawable.ic_launcher, "Hello !", 0);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		notification.setLatestEventInfo(getApplicationContext(), "aa notification", "aa_test!", contentIntent);
		notificationManager.notify(1, notification);
	}

	// android:id="@+id/startExtraActivity"
	@LongClick
	void startExtraActivity() {
		// 这个传值有点不好弄
		Intent intent = AAWithExtraAct_.intent(this).myDate(new Date()).myMessage("hello !").classCastExceptionExtra("42").get();
//		Intent intent = ActivityWithExtra_.intent(this).myDate(new Date()).myMessage("hello !").get();
//		intent.putExtra(ActivityWithExtra_.MY_INT_EXTRA, 42);
		startActivity(intent);
	}

	@Click
	void startListActivity(View v) {
		startActivity(new Intent(this, AAListAct_.class));
	}

	@Touch
	void myTextView(MotionEvent event) {
		Toast.makeText(this, "myTextView was touched!: " , Toast.LENGTH_SHORT).show();
	}

	@Transactional
	int transactionalMethod(SQLiteDatabase db, int someParam) {
		return 42;
	}

}

/**
 * AndroidAnnotations工作在一个非常简单的方式。它会使用标准的Java注解处理工具自动添加一个额外的编译步骤生成的源代码。(译者注：即生成一个原有类名加“_”的类，这个类才是真正运行用的类)
 * 源代码是什么样子的？
例如每个使用@EActivity注解的activity，将生成这个activity的一个子类，它的名字是在activity名称末尾追加一个下划线。
例如，下面的类：
@EActivity
public class MyActivity extends Activity{
  // ...
}

将生成以下子类，在同一个包路径中，但在另一个源文件夹：（译者注:在测试过程中，并未在src目录下发现生成的类。但此类确实生成了，它在哪里呢？实际是在项目根目录的.apt_generated文件夹下，在eclipse中无法查看到，需要到资源管理其相应文件夹中查看。）
public final class MyActivity_ extends MyActivity{
  // ...
}

该子类重载一些方法（例如的onCreate（）），通过【委托方式】调用了你的activity的相关方法。这就是为什么你必须在AndroidManifest.xml文件中为你的activity名称添加“_”的原因：
<activityandroid:name=".MyListActivity_"/>

 * 
 * 启动一个使用注解的activity
在原始的Android开发中，你通常是这样启动activity的：
startActivity(this,MyListActivity.class);

然而，如果使用AndroidAnnotations，真正被启动的activity是MyListActivity_：
startActivity(this,MyListActivity_.class);

创建Intent
1.AndroidAnnotations2.4及以上版本
我们提供了一个静态的助手方法，让你启动真正的activity：
// 启动 activity
MyListActivity_.intent(context).start();

// 创建一个intent使用 activity
Intentintent=MyListActivity_.intent(context).get();

// 你也可以设置flags
MyListActivity_.intent(context).flags(FLAG_ACTIVITY_CLEAR_TOP).start();

//你甚至可以在activity中使用@Extra定义扩展数据并作为activity的启动参数
MyListActivity_.intent(context).myDateExtra(someDate).start();

2.AndroidAnnotations2.7及以上版本
你还可以使用startActivityForResult（）方法：
MyListActivity_.intent(context).startForResult();

启动一个使用注解的Service
在原始的Android开发中，你通常是这样启动一个服务的：
startService(this,MyService.class);
然而，如果使用AndroidAnnotations，则真正的服务MyService_将被启动：
startService(this,MyService_.class);

创建Intent
1.AndroidAnnotations2.7及以上版本
我们提供了一个静态的方法，让你启动生成的service：

// 启动service
MyService_.intent(context).start();

// 通过service创建一个intent
Intentintent=MyService_.intent(context).build();
// 你能在启动service时使用flags

MyService_.intent(context).flags(Intent.FLAG_GRANT_READ_URI_PERMISSION).start();
 */
