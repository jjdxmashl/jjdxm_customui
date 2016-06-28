package com.dou361.customui.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2015-9-29 下午8:30:16
 * <p>
 * 描 述：GridView上拉下拉的阻尼效果。
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class PullToRefreshGridView extends PullToRefreshView {

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshGridView(Context context) {
		super(context);
	}
	
	@Override
	protected void init(Context context) {
		super.init(context);
	}

	/** 活动内容view */
	public GridView getContentView() {
		return mGridView;
	}
	
	@Override
	protected boolean hasContentView() {
		return true;
	}
	
	private GridView mGridView;
	
	protected void addContentView(Context context) {
		mGridView = new GridView(context);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mGridView.setVerticalScrollBarEnabled(false);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		addView(mGridView, params);
	}


}
