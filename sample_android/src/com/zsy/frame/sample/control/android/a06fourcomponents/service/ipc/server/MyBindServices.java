package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.IDataService;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.Person;

public class MyBindServices extends Service {

	class MyBind extends IDataService.Stub{
		Person person = null;
		public void setPerson(Person person) {
			this.person = person;
		}
		
		@Override
		public int getData(String name, int age, double score, float salary, boolean isExit, char sex, long id) throws RemoteException {
			return 0;
		}

		@Override
		public boolean getList(List<String> list, List<String> list2) throws RemoteException {
			return false;
		}

		@Override
		public void showPerson(Person person) throws RemoteException {
			
		}

		@Override
		public Person getPerson() throws RemoteException {
			return person;
		}}

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBind();
	}
}
