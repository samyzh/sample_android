package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description：数据传递部分
 四 调用与被调用：我们的通信使者 - Intent 传递对象； 
Bundle.putSerializable(Key,Object);  //实现Serializable接口的对象
Bundle.putParcelable(Key, Object); //实现Parcelable接口的对象
常用serializable;
传：
Bundle bundle = new Bundle();
bundle.putSerializable("user", user);
intent.putExtras(bundle);
接：
Intent intent = this.getIntent(); 
user=(User)intent.getSerializableExtra("user");

五：Serializable 和 Parcelable 区别
1 Serializable的实现，只需要继承implements Serializable即可这只是给对象打了一个标记，系统会自动将其序列化。
2 Parcelabel 的实现，需要在类中添加一个静态成员变量 CREATOR，这个变量需要继承 Parcelable.Creator 接口。
一 序列化原因：

1.永久性保存对象，保存对象的字节序列到本地文件中；
2.通过序列化对象在网络中传递对象；
3.通过序列化在进程间传递对象。 


二 至于选取哪种可参考下面的原则：
1.在使用内存的时候，Parcelable 类比Serializable性能高，所以推荐使用Parcelable类。
2.Serializable在序列化的时候会产生大量的临时变量，从而引起频繁的GC。
3.Parcelable不能使用在要将数据存储在磁盘上的情况，因为Parcelable不能很好的保证数据的持续性在外界有变化的情况下。尽管Serializable效率低点， 也不提倡用，但在这种情况下，还是建议你用Serializable 。


三：Intent数组的传递；
	private ArrayList<DefectPhoto> defects_list;
	1:传递数据（数组）
		i.putExtra("defects_list", defects_list);
	2：接收数据（数组）
		// defects_list = getIntent().getParcelableArrayListExtra("defects_list");
		defects_list = (ArrayList<DefectPhoto>) getIntent().getSerializableExtra("defects_list");
 * @author samy
 * @date 2015-3-8 下午4:12:45
 */
public class Person implements Parcelable {
	private int age;
	private String name;

	public Person() {
	}

	public Person(Parcel parcel) {
		readFromParcel(parcel);
	}

	public void readFromParcel(Parcel parcel) {
		age = parcel.readInt();
		name = parcel.readString();
	}

	@Override
	// 必须实现的
	public int describeContents() {
		return 0;
	}

	@Override
	// 必须实现的
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(age);
		dest.writeString(name);
	}

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel in) {
			// 从这一点就可以看出Parcelable比Serializable好多了；
			Person person = new Person();
			person.setAge(in.readInt() + 90);
			person.setName(in.readString() + "samy");
			return person;
			// return new Person(in);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}

}
