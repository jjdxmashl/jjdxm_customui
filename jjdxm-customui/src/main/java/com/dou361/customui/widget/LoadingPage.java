package com.dou361.customui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dou361.customui.R;
import com.dou361.customui.pool.ThreadManagerCUI;

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
 * 创建日期：2016/3/15 21:53
 * <p>
 * 描 述：请求网络时显示的页面，总共有四种状态，加载中，加载失败，没有数据，有数据
 * 使用：
 * mLoadingPage = new LoadingPage(mContext) {
 * <p>
 * public View createSuccessView() {
 * return CategoryActivity.this.createSuccessView();
 * }
 * public LoadResult load() {
 * return CategoryActivity.this.load();
 * }
 * };
 * v_root.addView(mLoadingPage, new ViewGroup.LayoutParams(
 * ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
 * mLoadingPage.show();
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public abstract class LoadingPage extends FrameLayout {

    /**
     * 默认状态
     */
    private final int STATE_DEFAULt = 1;
    /**
     * 加载状态
     */
    private final int STATE_LONDING = 2;
    /**
     * 错误状态
     */
    private final int STATE_ERROR = 3;
    /**
     * 空状态
     */
    private final int STATE_EMPTY = 4;
    /**
     * 成功状态
     */
    public final static int STATE_SUCCESS = 5;

    /**
     * 记录当前状态
     */
    public int mState;

    /**
     * 加载中的View
     */
    private View mLoadingView;
    /**
     * 错误的View
     */
    private View mErrorView;
    /**
     * 空的View
     */
    private View mEmptyView;
    /**
     * 成功的View
     */
    private View mSuccessView;
    private Context mContext;

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public LoadingPage(Context context, ViewGroup root) {
        super(context);
        init(context, root);
    }

    public LoadingPage(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * 初始化Page
     */
    private void init(Context context, ViewGroup root) {
        this.mContext = context;
        mState = STATE_DEFAULt;
        if (mLoadingView == null) {
            mLoadingView = createLoadingView(root);
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        if (mErrorView == null) {
            mErrorView = createErrorView(root);
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        if (mEmptyView == null) {
            mEmptyView = createEmptyView(root);
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }

        showSafePage();
    }

    /**
     * 安全显示当前状态对应的页面
     */
    private void showSafePage() {
        mLoadingView.post(new Runnable() {

            @Override
            public void run() {
                showPage();
            }

        });
    }

    /**
     * 显示当前状态对应的页面
     */
    private void showPage() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(mState == STATE_DEFAULt
                    || mState == STATE_LONDING ? View.VISIBLE : View.INVISIBLE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }

        if (mState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = createSuccessView();
            addView(mSuccessView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility(mState == STATE_SUCCESS ? View.VISIBLE
                    : View.INVISIBLE);
        }
    }

    /**
     * 创建加载中的View
     */
    public View createLoadingView(ViewGroup root) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customui_loading_page_loading, root, false);
        ProgressBar iv_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        TextView tv_loading = (TextView) view.findViewById(R.id.tv_loading);
        setRootViewBg(view);
        setLoadingView(iv_loading, tv_loading);
        return view;
    }

    /**
     * 创建加载失败的View
     */
    public View createErrorView(ViewGroup root) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customui_loading_page_error, root, false);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        view.findViewById(R.id.v_root).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show();
                    }
                });
        setRootViewBg(view);
        setErrorView(ivIcon, tvContent);
        return view;
    }

    /**
     * 创建加载成功但数据为空的View
     */
    public View createEmptyView(ViewGroup root) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customui_loading_page_empty, root, false);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        setRootViewBg(view);
        setEmptyView(ivIcon, tvContent);
        return view;
    }

    /**
     * 统一设置背景色
     */
    public void setRootViewBg(View vRoot) {
    }

    /**
     * 操作加载中页面
     */
    public void setLoadingView(ProgressBar progressBar, TextView tvContent) {
    }

    /**
     * 操作空数据页面
     */
    public void setEmptyView(ImageView ivIcon, TextView tvContent) {

    }

    /**
     * 操作错误页面
     */
    public void setErrorView(ImageView ivIcon, TextView tvContent) {

    }

    /**
     * 创建成功状态的View
     */
    public abstract View createSuccessView();

    /**
     * 加载数据
     */
    public abstract LoadResult load();

    /**
     * 显示页面
     */
    public synchronized void show() {
        if (mState == STATE_ERROR || mState == STATE_EMPTY) {
            mState = STATE_DEFAULt;
        }
        if (mState == STATE_DEFAULt) {
            mState = STATE_LONDING;
            LoadingTask task = new LoadingTask();
            ThreadManagerCUI.getLongPool().execute(task);
        }
        showSafePage();
    }

    /**
     * 不执行内部加载直接显示空白页面
     */
    public synchronized void showEmpty() {
        mState = STATE_EMPTY;
        showSafePage();
    }

    /**
     * 不执行内部加载直接显示错误页面
     */
    public synchronized void showError() {
        mState = STATE_ERROR;
        showSafePage();
    }

    /**
     * 不执行内部加载直接显示成功页面
     */
    public synchronized void showEmpty2Data() {
        mState = STATE_SUCCESS;
        showSafePage();
    }

    private class LoadingTask implements Runnable {

        @Override
        public void run() {
            final LoadResult mLoadResult = load();
            if (mLoadResult != null) {
                mState = mLoadResult.getValue();
            }
            showSafePage();
        }

    }

    /**
     * 加载数据的结果
     */
    public enum LoadResult {
        ERROR(3), EMPTY(4), SUCCESS(5);
        /**
         * 3为数据错误4为没有数据5为加载数据成功
         */
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
