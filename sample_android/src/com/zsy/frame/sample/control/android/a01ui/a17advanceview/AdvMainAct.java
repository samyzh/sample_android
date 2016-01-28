package com.zsy.frame.sample.control.android.a01ui.a17advanceview;

import com.zsy.frame.lib.extend.ui.base.BaseAct;

/**
 * @description：Android抽象布局——include、merge 、ViewStub
 * 
 * 1、布局重用<include />
<include />标签能够重用布局文件，简单的使用如下：
  1)<include />标签可以使用单独的layout属性，这个也是必须使用的。
    2)可以使用其他属性。<include />标签若指定了ID属性，而你的layout也定义了ID，则你的layout的ID会被覆盖，解决方案。
    3)在include标签中所有的android:layout_*都是有效的，前提是必须要写layout_width和layout_height两个属性。
    4)布局中可以包含两个相同的include标签，引用时可以使用如下方法解决（参考）:（设置他两的id一样）
	View bookmarks_container_2 = findViewById(R.id.bookmarks_favourite);   

2、减少视图层级<merge />
    <merge/>标签在UI的结构优化中起着非常重要的作用，它可以删减多余的层级，优化UI。
    <merge/>多用于替换FrameLayout或者当一个布局包含另一个时，<merge/>标签消除视图层次结构中多余的视图组。
        例如你的主布局文件是垂直布局，引入了一个垂直布局的include，这是如果include布局使用的LinearLayout就没意义了，
        使用的话反而减慢你的UI表现。这时可以使用<merge/>标签优化。
  现在，当你添加该布局文件时(使用<include />标签)，系统忽略<merge />节点并且直接添加两个Button。
  更多<merge />介绍可以参考《Android Layout Tricks #3: Optimize by merging》
  
  3、需要时使用<ViewStub />
    <ViewStub />标签最大的优点是当你需要时才会加载，使用他并不会影响UI初始化时的性能。
          各种不常用的布局想进度条、显示错误消息等可以使用<ViewStub />标签，以减少内存使用量，加快渲染速度。
    <ViewStub />是一个不可见的，大小为0的View。<ViewStub />标签使用如下：
    当你想加载布局时，可以使用下面其中一种方法：【两种加载方式】
    ((ViewStub) findViewById(R.id.stub_import)).setVisibility(View.VISIBLE);  
    // or  
    View importPanel = ((ViewStub) findViewById(R.id.stub_import)).inflate();  
  
  当调用inflate()函数的时候，ViewStub被引用的资源替代，并且返回引用的view。 
  这样程序可以直接得到引用的view而不用再次调用函数findViewById()来查找了。
注：ViewStub目前有个缺陷就是还不支持 <merge /> 标签。

ViewStub实例：
       <ViewStub
                android:id="@+id/stub_pay_result_fail"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:inflatedId="@+id/subTree"
                android:layout="@layout/mystub_pay_result_fail" />

       <ViewStub
                android:id="@+id/stub_pay_result_success"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:inflatedId="@+id/subTree"
                android:layout="@layout/mystub_pay_result_success" />
      在代码中加载的显示：
      	if (isPaySuccess) {
			viewStub = (ViewStub) findViewById(R.id.stub_pay_result_success);
		} else {
			viewStub = (ViewStub) findViewById(R.id.stub_pay_result_fail);
		}
		View view = viewStub.inflate();
		bindView(view);
 * @author samy
 * @date 2015-3-29 下午5:52:17
 */
public class AdvMainAct extends BaseAct {

	@Override
	public void setRootView() {

	}

}
