//package com.zsy.frame.sample.control.android.a20thirdparty.androidannotations;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.App;
//import org.androidannotations.annotations.Background;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.Fullscreen;
//import org.androidannotations.annotations.ItemClick;
//import org.androidannotations.annotations.NoTitle;
//import org.androidannotations.annotations.SystemService;
//import org.androidannotations.annotations.UiThread;
//import org.androidannotations.annotations.ViewById;
//import org.androidannotations.annotations.res.AnimationRes;
//import org.androidannotations.annotations.rest.RestService;
//
//import android.app.Activity;
//import android.content.ClipboardManager;
//import android.view.animation.Animation;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.zsy.frame.sample.R;
//
///**
// * @description：After
// * 
// * Fast Android Development. Easy maintainance.
//Here is a simple example of how your code can dramatically shrink, and become much easier to understand
// * @author samy
// * @date 2015-4-17 下午8:32:44
// */
//@NoTitle
//@Fullscreen
//@EActivity(R.layout.bookmarks)
//public class BookmarksToClipboardActivity extends Activity {
//  
//  BookmarkAdapter adapter;
//  
//  @ViewById
//  ListView bookmarkList;
// 
//  @ViewById
//  EditText search;
//  
//  @App
//  BookmarkApplication application;
//  
//  @RestService
//  BookmarkClient restClient;
// 
//  @AnimationRes
//  Animation fadeIn;
//  
//  @SystemService
//  ClipboardManager clipboardManager;
// 
//  @AfterViews
//  void initBookmarkList() {
//    adapter = new BookmarkAdapter(this);
//    bookmarkList.setAdapter(adapter);
//  }
//  
//  @Click({R.id.updateBookmarksButton1, R.id.updateBookmarksButton2})
//  void updateBookmarksClicked() {
//    searchAsync(search.getText().toString(), application.getUserId());
//  }
//  
//  @Background
//  void searchAsync(String searchString, String userId) {
//    Bookmarks bookmarks = restClient.getBookmarks(searchString, userId);
//    updateBookmarks(bookmarks);
//  }
// 
//  @UiThread
//  void updateBookmarks(Bookmarks bookmarks) {
//    adapter.updateBookmarks(bookmarks);
//    bookmarkList.startAnimation(fadeIn);
//  }
//  
//  @ItemClick
//  void bookmarkListItemClicked(Bookmark selectedBookmark) {
//    clipboardManager.setText(selectedBookmark.getUrl());
//  }
// 
//}
