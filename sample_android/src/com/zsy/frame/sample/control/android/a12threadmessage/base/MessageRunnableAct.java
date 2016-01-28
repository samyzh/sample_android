package com.zsy.frame.sample.control.android.a12threadmessage.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

/**
 * @description：
 * 这些队列中的内容（无论Message还是Runnable）可以要求马上执行，延迟一定时间执行或者指定某个时刻执行，如果将他们放置在队列头，则表示具有最高有限级别，立即执行。
 * 这些函数包括有:sendMessage(), sendMessageAtFrontOfQueue(), sendMessageAtTime(),
 *  sendMessageDelayed()以及用于在队列中加入Runnable的post(), postAtFrontOfQueue(), postAtTime(),postDelay()。
 * 一般而言，推荐是Messge方式，这样程序设计得可以更为灵活，而Runnable在某些简单明确的方式中使用。我们将通过三种方法编写一个小例子来学习。
 * 这个例子是一个进度条，每隔1秒，进度条步进5，如果acvity停止时，进度条归零。
 * @author samy
 * @date 2015-4-6 下午11:09:22
 */
public class MessageRunnableAct extends BaseAct {
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

	private ProgressBar bar = null;
	private boolean isRunning = true;
	private int step = 0;

	// private HandlerThread ht;

	/* 我们为这个Acivity创建一个用于和后台程序通信的handler，简单地，只要一收到message，就将progressbar进度增加5。 */
	/* 步骤1：创建Handler，并通过handleMessage()给出当收到消息是UI需要进行如何处理，例子简单不对msg的内容进行分析 */
	// 1和3用到
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			bar.incrementProgressBy(5);
		}
	};

	/* 步骤1.1：定义处理动作，采用Runnable的实例，通过implements run（）来定制处理，这里是简单将进度条步进5。由于我们将在Thread中使用这个实例，所以考虑采用final的方式 */
	// 2用到
	Runnable running = new Runnable() {
		// final Runnable running = new Runnable() {
		public void run() {
			bar.incrementProgressBy(5);
		}
	};

	// 由于Toast的显示和隐藏需要一定的时间，而间隔1秒显然不够，我们将例子1的间隔时间1000ms，改为5000ms这样会比较清晰，当然可以采用Log.d的方式来替代。
	// 4用到
	Runnable runAction = new Runnable() {
		public void run() {
			// 注意，我们不能使用Toast.makeText(this,....)，因为我们无法确定Runnable具体运行的context
			Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();
			// Log.d("WEI","runAction .... is called");
		}
	};

	// // 步骤3：创建handler中，带上Looper的参数，即handlerThread.getLooper()。注意，此处理必须在HandlerThread启动后才能调用，否则会报错 ，getLooper()会返回null，则程序异常出错
	// // 5用到
	// Handler handler2 = new Handler(ht.getLooper()) {
	// public void handleMessage(Message msg) {
	// /* 这里的处理，将不在主线程中执行，而在HandlerThread线程中执行，可以通过Thread.currentThread().getId()或者Thread.currentThread().getName()来确定 */
	// showToastMsg(Thread.currentThread().getName() + "=useHandlerThreadUpdateUI");
	// }
	// };

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a12threadmessage_base_messagerunnable);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		bar = (ProgressBar) findViewById(R.id.c15_progress);
	}

	/* on Start是UI初始化并显示时调用 */
	protected void onStart() {
		super.onStart();
	}

	/* onStop是UI停止显示时调用，例如我们按了返回键 */
	protected void onStop() {
		super.onStop();
		isRunning = false;
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				// 线程开启，采用Message传递后台线程和UI主线程之间的信息
				useMessageUpdateUI();
				break;
			case R.id.button2:
				// 采用Runnable
				useRunningUpdateUI();
				break;
			case R.id.button3:
				// 可以用延迟处理实现定时触发，让程序更为简单
				usePostUpdateUI();
				break;
			case R.id.button4:
				// 不知道在UI主线程还是在后台线程
				useRunOnUiThreadUpdateUI();
				break;
			case R.id.button5:
				useHandlerThreadUpdateUI();
				break;
		}
	}

	private void useMessageUpdateUI() {
		bar.setProgress(0);
		/* 步骤2：建立后台线程处理，采用Thread，其中run()的内容，就是线程并行处理的内容，Thread是Runnable的implements */
		Thread background = new Thread(new Runnable() {
			public void run() {
				try {
					for (int i = 0; i < 20 && isRunning; i++) {
						Thread.sleep(1000);
						/* 步骤2.1：发送Message到队列中，参数中的obtainMessage()是用于给出一个新Message，本例无参数，对应的在handler在队列中收到这条消息时，则通过handleMessage()进行处理 */
						handler.sendMessage(handler.obtainMessage());
					}
				}
				catch (Throwable t) {
					// jest end the thread
				}
			}

		});
		isRunning = true;
		/* 步骤3：启动线程 */
		background.start();
	}

	private void useRunningUpdateUI() {
		/* ... ...在onStart()中的步骤2：线程的处理，和提供message不同，对于runnable方式，采用post */
		Thread background = new Thread(new Runnable() {
			public void run() {
				try {
					for (int i = 0; i < 20 && isRunning; i++) {
						Thread.sleep(1000);
						handler.post(running);
					}
				}
				catch (Throwable t) {
					// jest end the thread
				}
			}
		});
		background.start();
	}

	/**
	 * @description：
	 * 在这里例子，事实我们是进行定时的处理，利用Handler队列可以设置延期处理的方式，我们并不需要创建一个后台运行的线程，也可以实现
	（记住这里没有创建一个新的线程）
	 * @author samy
	 * @date 2015-4-6 下午11:07:50
	 */
	private void usePostUpdateUI() {
		handler.postDelayed(new Runnable() {
			public void run() {
				if (isRunning && step < 20) {
					step++;
					bar.incrementProgressBy(5);
					handler.postDelayed(this, 1000);
					showToastMsg(Thread.currentThread().getName() + "=usePostUpdateUI");
				}
			}
		}, 1000);
	}

	/**
	 * @description：
	 * 有时候，我们并不清楚代码将在UI线程还是后台线程运行，例如这些代码封装为一个JAR，提供给其他人调用，我们并不清楚其他人如何使用这些代码。
	 * 为了解决这个问题Android在activity中提供了runOnUiThread()，如果在UI线程，则马上执行，如果在后台线程，
	 * 则将Runnable的执行内容加入到主线程的队列中，这样无论代码在UI线程还是后台线程都能安全地执行。
	 * @author samy
	 * @date 2015-4-6 下午11:12:52
	 */
	private void useRunOnUiThreadUpdateUI() {
		Thread background = new Thread(new Runnable() {
			public void run() {
				try {
					for (int i = 0; i < 20 && isRunning; i++) {
						Thread.sleep(1000);
						handler.sendMessage(handler.obtainMessage());
						runOnUiThread(runAction);
					}
				}
				catch (Throwable t) {
					// jest end the thread
				}
			}
		});

		isRunning = true;
		background.start();
		runOnUiThread(runAction);
	}

	/**
	 * @description：
	 * 之前我们讨论过为何UI的归UI，处理的处理，然而，可能有这样的需求，举个例子，
	 * 在某些情况下，Handler收到消息触发的处理中可能会有Sleep()，这会导致main线程进入sleep状态，不是我们期待的。
	 * 因此我们希望通过一个线程专门处理Hanlder的消息，这个线程也是依次从Handler的队列中获取信息，
	 * 逐个进行处理，保证安全，不会出现混乱引发的异常。
	 * @author samy
	 * @date 2015-4-6 下午11:15:20
	 */
	private void useHandlerThreadUpdateUI() {
		// 步骤1：创新HandlerThread的一个对象，并开启这个线程，HandlerThread将通过Looper来处理Handler对来中的消息，也就是如果发现Handler中有消息，将在HandlerThread这个线程中进行处理。
		// hander_thread这个就是线程名字
		HandlerThread ht;
		ht = new HandlerThread("hander_thread");
		// 步骤2：启动handerhandler这个线程;
		ht.start();
		// 步骤3：创建handler中，带上Looper的参数，即handlerThread.getLooper()。注意，此处理必须在HandlerThread启动后才能调用，否则会报错 ，getLooper()会返回null，则程序异常出错
		Handler handler2 = new Handler(ht.getLooper()) {
			public void handleMessage(Message msg) {
				/* 这里的处理，将不在主线程中执行，而在HandlerThread线程中执行，可以通过Thread.currentThread().getId()或者Thread.currentThread().getName()来确定 */
				showToastMsg(Thread.currentThread().getName() + "=useHandlerThreadUpdateUI");
			}
		};
		handler2.sendEmptyMessage(1);
	}

}
