package com.dou361.customui.bean;

import android.view.View;
import android.view.ViewGroup;

import com.dou361.customui.listener.PageViewInstantiateListener;
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
 * 创建日期：2016/4/29 17:46
 * <p/>
 * 描 述：表情页面实体
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class PageEntity<T extends PageEntity> implements PageViewInstantiateListener<T> {

    protected View mRootView;

    protected PageViewInstantiateListener mPageViewInstantiateListener;

    public void setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) { this.mPageViewInstantiateListener = pageViewInstantiateListener; }

    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    public PageEntity(){ }

    public PageEntity(View view){
        this.mRootView = view;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position, T pageEntity) {
        if(mPageViewInstantiateListener != null){
            return mPageViewInstantiateListener.instantiateItem(container, position, this);
        }
        return getRootView();
    }
}
