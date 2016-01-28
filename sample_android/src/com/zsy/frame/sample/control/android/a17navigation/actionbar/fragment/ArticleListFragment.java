package com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ArticleListFragment extends ListFragment {
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private ActionMode mActionMode;
	private FragmentManager manager;
	private FragmentTransaction transaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			data.add("samy" + i);
		}
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
		setListAdapter(adapter);
		manager = getFragmentManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getData()));
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String item = adapter.getItem(position);
		// transaction = manager.beginTransaction();
		// // Fragment直接传递值；
		// DetailFragment fragment = new DetailFragment();
		// Bundle args = new Bundle();
		// args.putString("item", item);
		// fragment.setArguments(args);
		// transaction.replace(R.id.right, fragment, "rightfragment");
		// transaction.addToBackStack("rightfragment");
		// transaction.commit();
		Toast.makeText(getActivity(), "-->>" + item, 1).show();
	}
}
