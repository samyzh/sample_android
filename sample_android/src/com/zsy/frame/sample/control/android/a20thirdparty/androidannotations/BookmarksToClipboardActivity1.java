//package com.zsy.frame.sample.control.android.a20thirdparty.androidannotations;
//package org.androidannotations.helloworldeclipse;
//
//import java.util.HashMap;
//
//import org.apache.http.HttpEntity;
//
//import android.app.Activity;
//import android.content.ClipboardManager;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.EditText;
//import android.widget.ListView;
//
///**
// * @description：Before
// * Fast Android Development. Easy maintainance.
//Here is a simple example of how your code can dramatically shrink, and become much easier to understand
// * @author samy
// * @date 2015-4-17 下午8:32:21
// */
//public class BookmarksToClipboardActivity1 extends Activity {
//  
//  BookmarkAdapter adapter;
// 
//  ListView bookmarkList;
// 
//  EditText search;
// 
//  BookmarkApplication application;
// 
//  Animation fadeIn;
// 
//  ClipboardManager clipboardManager;
// 
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
// 
//    requestWindowFeature(Window.FEATURE_NO_TITLE);
//    getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
// 
//    setContentView(R.layout.bookmarks);
// 
//    bookmarkList = (ListView) findViewById(R.id.bookmarkList);
//    search = (EditText) findViewById(R.id.search);
//    application = (BookmarkApplication) getApplication();
//    fadeIn = AnimationUtils.loadAnimation(this, anim.fade_in);
//    clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
// 
//    View updateBookmarksButton1 = findViewById(R.id.updateBookmarksButton1);
//    updateBookmarksButton1.setOnClickListener(new OnClickListener() {
// 
//      @Override
//      public void onClick(View v) {
//        updateBookmarksClicked();
//      }
//    });
// 
//    View updateBookmarksButton2 = findViewById(R.id.updateBookmarksButton2);
//    updateBookmarksButton2.setOnClickListener(new OnClickListener() {
// 
//      @Override
//      public void onClick(View v) {
//        updateBookmarksClicked();
//      }
//    });
// 
//    bookmarkList.setOnItemClickListener(new OnItemClickListener() {
// 
//      @Override
//      public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
//        Bookmark selectedBookmark = (Bookmark) p.getAdapter().getItem(pos);
//        bookmarkListItemClicked(selectedBookmark);
//      }
//    });
// 
//    initBookmarkList();
//  }
// 
//  void initBookmarkList() {
//    adapter = new BookmarkAdapter(this);
//    bookmarkList.setAdapter(adapter);
//  }
// 
//  void updateBookmarksClicked() {
//    UpdateBookmarksTask task = new UpdateBookmarksTask();
// 
//    task.execute(search.getText().toString(), application.getUserId());
//  }
//  
//  private static final String BOOKMARK_URL = //
//  "http://www.bookmarks.com/bookmarks/{userId}?search={search}";
//  
//  
//  class UpdateBookmarksTask extends AsyncTask<String, Void, Bookmarks> {
// 
//    @Override
//    protected Bookmarks doInBackground(String... params) {
//      String searchString = params[0];
//      String userId = params[1];
// 
//      RestTemplate client = new RestTemplate();
//      HashMap<String, Object> args = new HashMap<String, Object>();
//      args.put("search", searchString);
//      args.put("userId", userId);
//      HttpHeaders httpHeaders = new HttpHeaders();
//      HttpEntity<Bookmarks> request = new HttpEntity<Bookmarks>(httpHeaders);
//      ResponseEntity<Bookmarks> response = client.exchange( //
//          BOOKMARK_URL, HttpMethod.GET, request, Bookmarks.class, args);
//      Bookmarks bookmarks = response.getBody();
// 
//      return bookmarks;
//    }
// 
//    @Override
//    protected void onPostExecute(Bookmarks result) {
//      adapter.updateBookmarks(result);
//      bookmarkList.startAnimation(fadeIn);
//    }
//    
//  }
// 
//  void bookmarkListItemClicked(Bookmark selectedBookmark) {
//    clipboardManager.setText(selectedBookmark.getUrl());
//  }
// 
//}