package com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.widget.BadgeView;

/**
 * @description：veiws界面
 * @author samy
 * @date 2014年7月30日 上午12:25:54
 */
public class ViewsFragment extends Fragment implements OnClickListener {
	private BadgeView backgroundDrawableBadge;
	private BadgeView backgroundShapeBadge;
	private BadgeView backgroundDefaultBadge;
	private BadgeView counterBadge;
	private BadgeView gravityBadge;
	private BadgeView textStyleBadge;
	private BadgeView visibilityBadgeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_a17navigation_actionbar_views, null);

		View backgroundDefaultView = layout.findViewById(R.id.backgroundDefault);
		backgroundDefaultBadge = new BadgeView(getActivity());
		backgroundDefaultBadge.setBadgeCount(42);
		backgroundDefaultBadge.setTargetView(backgroundDefaultView);

		View backgroundDrawableView = layout.findViewById(R.id.backgroundDrawable);
		backgroundDrawableBadge = new BadgeView(getActivity());
		backgroundDrawableBadge.setBadgeCount(88);
		backgroundDrawableBadge.setBackgroundResource(R.drawable.badge_blue);
		backgroundDrawableBadge.setTargetView(backgroundDrawableView);

		View backgroundShapeView = layout.findViewById(R.id.backgroundShape);
		backgroundShapeBadge = new BadgeView(getActivity());
		backgroundShapeBadge.setBadgeCount(47);
		backgroundShapeBadge.setBackground(12, Color.parseColor("#9b2eef"));
		backgroundShapeBadge.setTargetView(backgroundShapeView);

		View counterView = layout.findViewById(R.id.counter);
		counterView.setOnClickListener(this);
		counterBadge = new BadgeView(getActivity());
		counterBadge.setBadgeCount(-2);
		counterBadge.setTargetView(counterView);

		View gravityView = layout.findViewById(R.id.gravity);
		gravityView.setOnClickListener(this);
		gravityBadge = new BadgeView(getActivity());
		gravityBadge.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
		gravityBadge.setBadgeCount(4);
		gravityBadge.setTargetView(gravityView);

		View textStyleView = layout.findViewById(R.id.textStyle);
		textStyleView.setOnClickListener(this);
		textStyleBadge = new BadgeView(getActivity());
		textStyleBadge.setBadgeCount(18);
		textStyleBadge.setTargetView(textStyleView);
		textStyleBadge.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
		textStyleBadge.setShadowLayer(2, -1, -1, Color.GREEN);

		View visibilityView = layout.findViewById(R.id.visibility);
		visibilityView.setOnClickListener(this);
		visibilityBadgeView = new BadgeView(getActivity());
		visibilityBadgeView.setBadgeCount(1);
		visibilityBadgeView.setTargetView(visibilityView);
		return layout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.counter: {
				counterBadge.incrementBadgeCount(1);
				break;
			}
			case R.id.gravity: {
				gravityBadge.incrementBadgeCount(1);
				Log.d(getClass().getName(), String.valueOf(gravityBadge.getBadgeCount() % 9));
				if (gravityBadge.getBadgeCount() % 9 == 0) {
					gravityBadge.setBadgeGravity(Gravity.LEFT | Gravity.TOP);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 1) {
					gravityBadge.setBadgeGravity(Gravity.RIGHT | Gravity.BOTTOM);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 2) {
					gravityBadge.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 3) {
					gravityBadge.setBadgeGravity(Gravity.RIGHT | Gravity.TOP);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 4) {
					gravityBadge.setBadgeGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 5) {
					gravityBadge.setBadgeGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 6) {
					gravityBadge.setBadgeGravity(Gravity.CENTER);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 7) {
					gravityBadge.setBadgeGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
				}
				else if (gravityBadge.getBadgeCount() % 9 == 8) {
					gravityBadge.setBadgeGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
				}
				break;
			}
			case R.id.visibility: {
				visibilityBadgeView.setVisibility(visibilityBadgeView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
				break;
			}
		}
	}
}
