/*
 * Copyright 2012 - 2014 Benjamin Weiss
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zsy.frame.sample.control.android.a01ui.a22notification.crouton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;
import com.zsy.frame.sample.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class CroutonDemo extends FragmentActivity {
	// public class CroutonDemo extends SherlockFragmentActivity {

	ViewPager croutonPager;

	enum PageInfo {

		Crouton(R.string.crouton), About(R.string.about);

		int titleResId;

		PageInfo(int titleResId) {
			this.titleResId = titleResId;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a22notification_crouton_main);
		croutonPager = (ViewPager) findViewById(R.id.crouton_pager);
		croutonPager.setAdapter(new CroutonPagerAdapter(getSupportFragmentManager()));
		((TitlePageIndicator) findViewById(R.id.titles)).setViewPager(croutonPager);
	}

	@Override
	protected void onDestroy() {
		// Workaround until there's a way to detach the Activity from Crouton while
		// there are still some in the Queue.
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}

	class CroutonPagerAdapter extends FragmentPagerAdapter {

		public CroutonPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			if (PageInfo.Crouton.ordinal() == position) {
				return new CroutonFragment();
			}
			else if (PageInfo.About.ordinal() == position) { return new AboutFragment(); }
			return null;
		}

		@Override
		public int getCount() {
			return PageInfo.values().length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CroutonDemo.this.getString(PageInfo.values()[position].titleResId);
		}
	}
}
