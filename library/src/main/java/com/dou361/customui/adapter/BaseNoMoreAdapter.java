package com.dou361.customui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dou361.customui.holder.BaseHolder;

import java.util.List;

/**
 * ========================================
 * <p>
 * 版 权：dou361 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2015/12/21 16:25
 * <p>
 * 描 述： ListView或者GridView的适配器，采用holder的形式分类和简化代码，
 * 便于代码阅读
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public abstract class BaseNoMoreAdapter<T> extends BaseAdapter {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 接收传递过来的数据
     */
    private List<T> mDatas;
    /**
     * 获得holder
     */
    private BaseHolder holder;

    public BaseNoMoreAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        setmDatas(mDatas);
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return (mDatas == null ? 0 : mDatas.size());
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (BaseHolder) convertView.getTag();
            holder.setPosition(position);
        } else {
            holder = getItemHolder(mContext, position);
        }
        holder.setData(mDatas.get(position));
        return holder.getRootView();
    }

    /**
     * 获得Holder
     */
    public abstract BaseHolder getItemHolder(Context mContext, int position);

}
