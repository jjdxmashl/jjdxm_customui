package com.dou361.customui.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

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
 * 创建日期：2016/5/3 10:20
 * <p/>
 * 描 述：软键盘高度监听
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class SoftKeyboardSizeWatchLayout extends RelativeLayout {

    protected Context mContext;
    /**软键盘是否弹出*/
    private boolean mIsSoftKeyboardPop = false;
    /**内容高度*/
    private int mScreenHeight = 0;
    /**上一次高度*/
    private int mOldh = -1;
    /**当前高度*/
    private int mNowh = -1;
    /**界面大小监听*/
    private List<OnResizeListener> mListenerList;

    public SoftKeyboardSizeWatchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        /**注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
         *参数 listener    将要被添加的回调函数
         *异常 IllegalStateException       如果isAlive() 返回false
         */
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                if (mScreenHeight == 0) {
                    mScreenHeight = r.bottom;
                }
                mNowh = mScreenHeight - r.bottom;
                if (mOldh != -1 && mNowh != mOldh) {
                    if (mNowh > 0) {
                        mIsSoftKeyboardPop = true;
                        if (mListenerList != null) {
                            for (OnResizeListener l : mListenerList) {
                                l.OnSoftPop(mNowh);
                            }
                        }
                    } else {
                        mIsSoftKeyboardPop = false;
                        if (mListenerList != null) {
                            for (OnResizeListener l : mListenerList) {
                                l.OnSoftClose();
                            }
                        }
                    }
                }
                mOldh = mNowh;
            }
        });
    }

    public boolean isSoftKeyboardPop() {
        return mIsSoftKeyboardPop;
    }

    public void addOnResizeListener(OnResizeListener l) {
        if (mListenerList == null) {
            mListenerList = new ArrayList<>();
        }
        mListenerList.add(l);
    }

    public interface OnResizeListener {
        /**
         * 软键盘弹起
         */
        void OnSoftPop(int height);

        /**
         * 软键盘关闭
         */
        void OnSoftClose();
    }
}