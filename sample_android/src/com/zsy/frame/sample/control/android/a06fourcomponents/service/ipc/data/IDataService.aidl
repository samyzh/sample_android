package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data;
import  com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.Person;

//aidl为android接口描述语言;AIDL通信，实质：代理模式
//1:android支持String和CharSequence直接跨进程传送
//2:如果需要在aidl中使用其他aidl接口类型，需要import，即使是在相同包结构下；
//3:android允许传递实现Parcelable接口的类，需要import；
//4:android支持集合接口类型List和Map，但是有一些限制，元素必须是基本型或者上述三种情况，不需要import集合接口类，但是需要对元素涉及到的类型import
//5:非基本数据类型，也不是String和CharSequence类型的，需要有方向指示，包括in、out和inout，in表示由客户端设置，out表示由服务端设置，inout是两者均可设置。

interface IDataService{
    int getData(String name,int age,double score,float salary,boolean isExit,char sex,long id);
    boolean getList( in List<String> list, out List<String> list2);
    
    void showPerson( in Person person);
    Person getPerson();
}