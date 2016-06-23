package com.dou361.customui.holder;

import android.content.Context;
import android.view.View;

import com.dou361.customui.ui.LoadingPage;

import java.util.List;

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
 * 创建日期：2016/3/15 21:30
 * <p>
 * 描 述：自定义基类holder，相当于一个View
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public abstract class BaseHolder<T> {


    /**
     * view对象
     */
    private View view;
    /**
     * 加载得到的数据
     */
    public T mDatas;
    /**
     * 当前调用类的类标识
     */
    protected String tag = this.getClass().getSimpleName();
    /**
     * 只有当该holder作为item使用，并且使用带参构造函数实例化position才有意义，使用无参构造函数则position没有意义
     */
    protected int position;
    /**
     * 上下文
     */
    protected Context mContext;

    public BaseHolder(Context mContext) {
        this.mContext = mContext;
        view = initView();
        view.setTag(this);
    }

    public BaseHolder(Context mContext, int position) {
        this(mContext);
        this.position = position;
    }

    /**
     * 初int始化View
     */
    public abstract View initView();

    /**
     * 获得View
     */
    public View getRootView() {
        return view;
    }

    /**
     * 设置数据
     */
    public void setData(T data) {
        this.mDatas = data;
        refreshView();
    }

    /**
     * 刷新数据
     */
    public abstract void refreshView();

    /**
     * 获得数据
     */
    public T getData() {
        return mDatas;
    }

    /**
     * 当复用holder的时候，需要调用该方法来同步holder对应的索引位置
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 验证数据状态
     */
    protected <T> LoadingPage.LoadResult checkData(List<T> list) {
        if (list == null) {
            return LoadingPage.LoadResult.ERROR;
        }
        if (list.size() > 0) {
            return LoadingPage.LoadResult.SUCCESS;
        } else {
            return LoadingPage.LoadResult.EMPTY;
        }
    }

}
