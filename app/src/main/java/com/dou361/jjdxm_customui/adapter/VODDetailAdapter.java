package com.dou361.jjdxm_customui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class VODDetailAdapter extends PagerAdapter {

    private List<View> viewLists;
    private String[] mTabTitle;

    public VODDetailAdapter(List<View> views, String[] mTabTitle) {
        this.viewLists = views;
        this.mTabTitle = mTabTitle;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(viewLists.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}