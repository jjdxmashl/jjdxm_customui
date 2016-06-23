package com.dou361.customui.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2015-9-29 下午8:30:16
 * <p/>
 * 描 述：ListView上拉下拉的阻尼效果。
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class PullToRefreshListView extends PullToRefreshView {

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshListView(Context context) {
		super(context);
	}
	
	@Override
	protected void init(Context context) {
		super.init(context);
	}
	
	/** 活动内容view */
	public ListView getContentView() {
		return mListView;
	}
	
	@Override
	protected boolean hasContentView() {
		return true;
	}
	
	private ListView mListView;
	
	protected void addContentView(Context context) {
		mListView = new ListView(context);
		mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
		mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mListView.setVerticalScrollBarEnabled(false);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		addView(mListView, params);
	}


}
