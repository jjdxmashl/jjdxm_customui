package com.dou361.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.dou361.adapter.BaseHasMoreAdapter;
import com.dou361.utils.ResourceUtils;

/**
 * @author jjdxm 2015-3-17 下午5:46:40 说明 在ListView的最后加入加载更多条目
 */
public class MoreHolder extends BaseHolder<Integer> implements OnClickListener {

    /**
     * 有更多数据
     */
    public static final int HAS_MORE = 0;
    /**
     * 没有数据
     */
    public static final int NO_MORE = 1;
    /**
     * 跟服务器交互失败
     */
    public static final int ERROR = 2;

    /**
     * 加载中
     */
    private RelativeLayout mLoading;
    /**
     * 加载失败
     */
    private RelativeLayout mError;

    private BaseHasMoreAdapter mAdapter;
    private View view;

    public MoreHolder(Context mContext, BaseHasMoreAdapter bbaseAdapter, boolean hasMore) {
        super(mContext);
        setData(hasMore ? HAS_MORE : NO_MORE);
        mAdapter = bbaseAdapter;
    }

    @Override
    public View initView() {
        view = LayoutInflater.from(mContext).inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_holder_more_loading"), null);
        mLoading = (RelativeLayout) view.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_rl_more_loading"));
        mError = (RelativeLayout) view.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_rl_more_error"));
        mError.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView() {
        Integer data = getData();
        mLoading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
        mError.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
    }

    @Override
    public View getRootView() {
        if (getData() == HAS_MORE) {
            loadMore();
        }
        return super.getRootView();
    }

    /**
     * 加载更多数据
     */
    public void loadMore() {
        mAdapter.loadMore();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "customui_rl_more_error")) {
            if (mLoading != null) {
                mLoading.setVisibility(View.VISIBLE);
            }
            if (mError != null) {
                mError.setVisibility(View.GONE);
            }
            loadMore();
        }
    }

}
