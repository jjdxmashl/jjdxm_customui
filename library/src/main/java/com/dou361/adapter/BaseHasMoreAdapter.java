package com.dou361.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dou361.holder.BaseHolder;
import com.dou361.holder.MoreHolder;
import com.dou361.pool.ThreadManagerCUI;

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
 * 创建日期：2015/12/21 16:19
 * <p>
 * 描 述： ListView的适配器包括加载更多的，如果不需要加载更多需要重新hasMore()
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public abstract class BaseHasMoreAdapter<T> extends BaseAdapter {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 接收传递过来的数据
     */
    private List<T> mDatas;
    /**
     * 加载更多
     */
    public final int MORE_VIEW_TYPE = 0;
    /**
     * 显示的条目
     */
    public final int ITEM_VIEW_TYPE = 1;
    /**
     * 获得holder
     */
    private BaseHolder holder;
    /**
     * 获得mMoreHolder
     */
    private MoreHolder mMoreHolder;
    /**
     * 分页条目数
     */
    private int pageSize = 8;

    public BaseHasMoreAdapter(Context mContext, List<T> mDatas) {
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
        /** 最后一条为加载更多 */
        return (mDatas == null ? 0 : mDatas.size()) + 1;
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
    public int getItemViewType(int position) {
        /** 最后一条 */
        if (position == getCount() - 1) {
            return MORE_VIEW_TYPE;
        } else {
            return getItemViewTypeInner(position);
        }
    }

    public int getItemViewTypeInner(int position) {
        return ITEM_VIEW_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (BaseHolder) convertView.getTag();
        } else {
            if (MORE_VIEW_TYPE == getItemViewType(position)) {
                holder = getMoreHolder();
                holder.setPosition(position);
            } else {
                holder = getItemHolder(mContext, position);
            }

        }
        /** 除了加载更多的这种类型外，其他情况都是共用mDatas数据，在后面如果有多种类型则需要区别开 */
        if (MORE_VIEW_TYPE != getItemViewType(position)) {
            holder.setData(mDatas.get(position));
        }
        return holder.getRootView();
    }

    /**
     * 加载更多
     */
    private BaseHolder getMoreHolder() {
        if (mMoreHolder == null) {
            mMoreHolder = new MoreHolder(mContext, this, hasMore());
        }
        return mMoreHolder;
    }

    /**
     * 判断页面是否需要显示加载更多条目
     */
    protected boolean hasMore() {
        return true;
    }

    /**
     * 获得Holder
     */
    public abstract BaseHolder getItemHolder(Context context, int position);

    /**
     * 加载更多的异步是否在加载中
     */
    private boolean is_loading = false;

    /**
     * 加载更多的数据
     */
    public void loadMore() {
        if (!is_loading) {
            is_loading = true;
            ThreadManagerCUI.getLongPool().execute(new Runnable() {

                @Override
                public void run() {
                    final List mList = onLoadMore();
                    mMoreHolder.getRootView().post(new Runnable() {

                        @Override
                        public void run() {
                            if (mList == null) {
                                getMoreHolder().setData(MoreHolder.ERROR);
                            } else if (mList.size() < pageSize) {
                                getMoreHolder().setData(MoreHolder.NO_MORE);
                            } else {
                                getMoreHolder().setData(MoreHolder.HAS_MORE);
                            }

                            if (mList != null) {
                                if (mDatas != null) {
                                    mDatas.addAll(mList);
                                } else {
                                    setmDatas(mList);
                                }
                            }
                            notifyDataSetChanged();
                            is_loading = false;
                        }
                    });
                }
            });
        }
    }

    /**
     * 加载更多数据
     */
    protected List onLoadMore() {
        return null;
    }

}
