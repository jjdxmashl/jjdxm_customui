package com.dou361.demotest001.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dou361.demotest001.R;
import com.dou361.demotest001.adapter.VODDetailAdapter;
import com.dou361.ui.PagerTab;

import java.util.ArrayList;
import java.util.List;

public class PageTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private PagerTab mPageTabs;
    private VODDetailAdapter mAdapter;
    private ViewPager mPager;
    private String[] mTabTitles;
    private List<View> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_tab);
        mContext = this;
        mPageTabs = (PagerTab) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mTabTitles = new String[]{"我", "不知道", "他们", "为何", "离去"};
        lists = new ArrayList<View>();
        for (int i = 0; i < mTabTitles.length; i++) {
            lists.add(new TextView(this));
        }
        mAdapter = new VODDetailAdapter(lists, mTabTitles);
        mPager.setAdapter(mAdapter);
        mPageTabs.setViewPager(mPager);
        mPageTabs.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
