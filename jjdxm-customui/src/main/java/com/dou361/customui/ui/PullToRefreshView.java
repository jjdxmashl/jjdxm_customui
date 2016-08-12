package com.dou361.customui.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dou361.customui.utils.ResourceUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 描 述：LinearLayout上拉下拉的阻尼效果。 如果需要上拉刷新和下拉加载更多，则需要设置 使用流程，
 * 在xml布局中添加类作为View使用，里面一定要添加列表View或滚动view
 * mCreateInCode用来判断该view是否使用代码创建，true为在代码中创建，false为在xml中创建。
 * 默认为在xml使用，默认使用该构造函数PullToRefreshView(Context context)在代码中创建
 * 如果需要主动刷新，可以调用以下方法：
 * headerRefreshing();
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class PullToRefreshView extends LinearLayout {

    /**
     * 上拉状态
     */
    private static final int PULL_UP_STATE = 0;
    /**
     * 下拉状态
     */
    private static final int PULL_DOWN_STATE = 1;
    /**
     * 上下拉转刷新状态
     */
    private static final int PULL_TO_REFRESH = 2;
    /**
     * 刷新转刷新状态
     */
    private static final int RELEASE_TO_REFRESH = 3;
    /**
     * 刷新中状态
     */
    private static final int REFRESHING = 4;
    /**
     * 延迟加载的时间
     */
    public static final int delay_DURATION = 1500;
    /**
     * 回滚的时间
     */
    private static final int SCROLL_DURATION = 150;
    /**
     * 阻尼系数 小于1的滑动顺畅，大于1的相对阻碍
     */
    private static final float OFFSET_RADIO = 0.6f;
    /**
     * 头部view
     */
    private View mHeaderView;
    /**
     * 脚部view
     */
    private View mFooterView;
    /**
     * header view的height
     */
    private int mHeaderViewHeight;
    /**
     * footer view的height
     */
    private int mFooterViewHeight;
    /**
     * header view的image
     */
    private ImageView mHeaderImageView;
    /**
     * footer view的image
     */
    private ImageView mFooterImageView;
    /**
     * header tip的text
     */
    private TextView mHeaderTextView;
    /**
     * footer tip的text
     */
    private TextView mFooterTextView;
    /**
     * header refresh time
     */
    private TextView mHeaderUpdateTextView;
    /**
     * footer refresh time
     */
    private TextView mFooterUpdateTextView;
    /**
     * header progress的bar
     */
    private ProgressBar mHeaderProgressBar;
    /**
     * footer progress的bar
     */
    private ProgressBar mFooterProgressBar;
    /**
     * header view current state
     */
    private int mHeaderState;
    /**
     * footer view current state
     */
    private int mFooterState;
    /**
     * 禁止下拉true为禁止false不禁止
     */
    private boolean mHeaderLock;
    /**
     * 禁止上拉true为禁止false不禁止
     */
    private boolean mFooterLock;
    /**
     * true为在代码创建view，false在xml中创建
     */
    private boolean mCreateInCode;
    /**
     * 默认是有上拉下拉的阻尼效果fale。如果需要上拉刷新和下拉加载更多true，则需要设置
     */
    private boolean mPullDownDamp;
    /**
     * 默认是有上拉下拉的阻尼效果fale。如果需要上拉刷新和下拉加载更多true，则需要设置
     */
    private boolean mPullUpDamp;
    /**
     * footer refresh listener
     */
    private OnFooterRefreshListener mOnFooterRefreshListener;
    /**
     * header refresh listener
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;

    /**
     * list or grid列表或网格view
     */
    private AdapterView<?> mAdapterView;
    /**
     * scrollview可滚动的view
     */
    private ScrollView mScrollView;
    /**
     * 变为逆向的箭头,旋转
     */
    private RotateAnimation mReverseFlipAnimation;
    /**
     * 用户当前操作的状态 pull state,pull up or pull down;PULL_UP_STATE or
     * PULL_DOWN_STATE
     */
    private int mPullState;
    /**
     * 布局加载器layout inflater
     */
    private LayoutInflater mInflater;
    /**
     * 上一次触摸屏幕的的y坐标 last y
     */
    private int mLastMotionY;
    private Context mContext;

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("NewApi")
    public PullToRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PullToRefreshView(Context context) {
        super(context);
        /** 默认使用该构造函数为代码创建 */
        mCreateInCode = true;
        init(context);
    }

    protected void init(Context context) {
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        /** 初始化动画对象 */
        mReverseFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        /** header view 在此添加,保证是第一个添加到linearlayout的最上端 */
        addHeaderView();
        if (hasContentView()) {
            addContentView(context);
        }
        if (mCreateInCode) {
            /** 如果在代码中创建一个本view，则需要重写hasCreateInCode方法 */
            addFooterView();
            checkPullDownDamp();
            checkPullUpDamp();
            initContentAdapterView();
        }
    }

    /**
     * 添加头部view
     */
    private void addHeaderView() {
        /** header view */
        mHeaderView = mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_pfv_refresh_header"), this,
                false);

        mHeaderImageView = (ImageView) mHeaderView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_refresh_image"));
        mHeaderTextView = (TextView) mHeaderView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_refresh_text"));
        mHeaderUpdateTextView = (TextView) mHeaderView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_refresh_updated_at"));
        mHeaderProgressBar = (ProgressBar) mHeaderView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_refresh_progress"));
        /** header layout */
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                mHeaderViewHeight);
        /** 设置topMargin的值为负的header View高度,即将其隐藏在最上方 */
        params.topMargin = -(mHeaderViewHeight);
        /** 添加头view到布局中，并设置位置 */
        addView(mHeaderView, params);

    }

    /**
     * 是否自定义内容view true为在代码创建view，false在xml中创建
     */
    protected boolean hasContentView() {
        return false;
    }

    protected void addContentView(Context context) {
    }

    /**
     * 添加脚部view
     */
    private void addFooterView() {
        /** footer view */
        mFooterView = mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_pfv_refresh_footer"), this,
                false);
        mFooterImageView = (ImageView) mFooterView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_load_image"));
        mFooterTextView = (TextView) mFooterView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_load_text"));
        mFooterUpdateTextView = (TextView) mFooterView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_load_updated_at"));
        mFooterProgressBar = (ProgressBar) mFooterView
                .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "pull_to_load_progress"));
        /** footer layout */
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                mFooterViewHeight);
        /**
         * int top = getHeight(); params.topMargin =getHeight();
         * 在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
         * getHeight()什么时候会赋值,稍候再研究一下
         * 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
         */
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /** footer view 在此添加保证添加到linearlayout中的最后 */
        addFooterView();
        checkPullDownDamp();
        checkPullUpDamp();
        initContentAdapterView();
    }

    /**
     * 初始化布局内部的内容，即AdapterView或者是ScrollView里面的内容 init AdapterView like
     * ListView,GridView and so on;or init ScrollView
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                /** finish later */
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    /**
     * 测量view
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        /**
         * MeasureSpec 三种模式： ①UNSPECIFIED(未指定),父元素部队自元素施加任何束缚， 子元素可以得到任意想要的大小；
         * ②EXACTLY(完全)， 父元素决定自元素的确切大小， 子元素将被限定在给定的边界里而忽略它本身大小；
         * ③AT_MOST(至多)，子元素至多达到指定大小的值。
         */

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            /** 脚部的view */
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 此方法返回false，则手势事件会向子控件传递；返回true，则调用onTouchEvent方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /** 首先拦截down事件,记录y坐标 */
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                /** deltaY > 0 是向下运动,< 0是向上运动 */
                int deltaY = y - mLastMotionY;
                if (isRefreshViewScroll(deltaY)) {
                    /** 如果满足上下拉，则把事件给处理了 */
                    if (Math.abs(deltaY) < 10) {
                        /**如果滑动距离较小则不拦截，交个子类*/
                        return false;
                    } else {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;
     * 否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 * onInterceptTouchEvent已经记录,不需要重新拿 mLastMotionY = y;
                 */
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE && !mHeaderLock) {
                    /** PullToRefreshView执行下拉 */
                    headerPrepareToRefresh((int) (deltaY / OFFSET_RADIO));
                } else if (mPullState == PULL_UP_STATE && !mFooterLock) {
                    /** PullToRefreshView执行上拉 */
                    footerPrepareToRefresh((int) (deltaY / OFFSET_RADIO));
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0 && mPullDownDamp) {
                        /** 开始刷新 */
                        headerRefreshing();
                    } else {
                        /** 下拉头部没有漏出来，取消刷新。还没有执行刷新，重新隐藏 */
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mHeaderViewHeight
                            + mFooterViewHeight
                            && mPullUpDamp) {
                        /** 开始执行footer 刷新 */
                        footerRefreshing();
                    } else {
                        /** 上拉脚部没有漏出来，还没有执行刷新，重新隐藏 */
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * true父view把事件处理，不交给子view，false则不拦截。
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        /** 对于ListView和GridView */
        if (mAdapterView != null) {
            /** 子view(ListView or GridView)滑动到最顶端 */
            if (deltaY > 0) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    /** 如果mAdapterView中没有数据,不拦截 */
                    if (mAdapterView.getFirstVisiblePosition() == 0) {
                        /** 当前在顶部，可以进行下拉 */
                        mPullState = PULL_DOWN_STATE;
                        return true;
                    } else {
                        return false;
                    }
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    /** 当前在顶部，可以进行下拉 */
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 8) {
                    /** 这里之前用3可以判断,但现在不行,还没找到原因 */
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
            } else if (deltaY < 0) {
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    /** 如果mAdapterView中没有数据,不拦截 */
                    return false;
                }
                /**
                 * 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                 * 等于父View的高度说明mAdapterView已经滑动到最后
                 */
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    /** 数据不足，没有填满，或者滑到底部，可以上拉 */
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        /** 对于ScrollView */
        if (mScrollView != null) {
            /** 子scroll view滑动到最顶端 */
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                /** 在顶部或者滑到顶部了，可以下拉 */
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                /** 数据不足，没有填满，或者滑到底部，可以上拉 */
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            /** 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态 */
            mHeaderTextView.setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_release_label"));
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mReverseFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
            /** 拖动时没有释放 */
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mReverseFlipAnimation);
            mHeaderTextView.setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_pull_label"));
            mHeaderState = PULL_TO_REFRESH;
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
     * 高度是一样，都是通过修改header view的topmargin的值来达到
     *
     * @param deltaY ,手指滑动的距离
     */
    private void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
                && mFooterState != RELEASE_TO_REFRESH) {
            /**
             * 如果header view topMargin 的绝对值大于或等于header + footer 的高度 说明footer
             * view 完全显示出来了，修改footer view 的提示状态
             */
            mFooterTextView
                    .setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_footer_release_label"));
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mReverseFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mReverseFlipAnimation);
            mFooterTextView
                    .setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_footer_pull_label"));
            mFooterState = PULL_TO_REFRESH;
        }
    }

    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     * @return hylin 2012-7-31下午1:14:31
     * @description
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE
                && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE
                && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * 刷新中header refreshing
     */
    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_refreshing_label"));
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(PullToRefreshView.this);
        }
    }

    /**
     * 刷新中footer refreshing
     */
    private void footerRefreshing() {
        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mFooterImageView.setVisibility(View.GONE);
        mFooterImageView.clearAnimation();
        mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView
                .setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_footer_refreshing_label"));
        if (mOnFooterRefreshListener != null) {
            mOnFooterRefreshListener.onFooterRefresh(PullToRefreshView.this);
        }
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     *                  hylin 2012-7-31上午11:24:06
     * @description
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderImageView.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_ic_pulltorefresh_arrow"));
        mHeaderTextView.setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_pull_label"));
        mHeaderProgressBar.setVisibility(View.GONE);
        mHeaderState = PULL_TO_REFRESH;
    }

    /**
     * Resets the list to a normal state after a refresh.
     */
    public void onHeaderRefreshCompleteAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        setLastUpdated("上次更新："
                + sdf.format(new Date(System.currentTimeMillis())));
        onHeaderRefreshComplete();
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_ic_pulltorefresh_arrow_up"));
        mFooterTextView.setText(ResourceUtils.getResourceIdByName(mContext, "string", "pfv_pull_to_refresh_footer_pull_label"));
        mFooterProgressBar.setVisibility(View.GONE);
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * 上次更新时间 Set a text to represent when the list was last updated.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            mHeaderUpdateTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取当前header view 的topMargin
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    /**
     * 设置上拉禁止状态
     */
    public void setFooterLock(boolean lock) {
        mFooterLock = lock;
    }

    /**
     * 设置下拉禁止状态
     */
    public void setHeaderLock(boolean lock) {
        mHeaderLock = lock;
    }

    /**
     * 设置阻尼效果或者是刷新true为刷新false只有阻尼
     */
    public void setPullDownDamp(boolean damp) {
        mPullDownDamp = damp;
        checkPullDownDamp();
    }

    /**
     * 设置阻尼效果或者是刷新true为刷新false只有阻尼
     */
    public void setPullUpDamp(boolean damp) {
        mPullUpDamp = damp;
        checkPullUpDamp();
    }

    /**
     * 判断下拉阻尼效果或者是刷新true为刷新false只有阻尼
     */
    private void checkPullDownDamp() {
        if (mPullDownDamp && mHeaderView != null) {
            mHeaderView.setVisibility(View.VISIBLE);
        } else {
            mHeaderView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 判断上拉阻尼效果或者是刷新true为刷新false只有阻尼
     */
    private void checkPullUpDamp() {
        if (mPullUpDamp && mFooterView != null) {
            mFooterView.setVisibility(View.VISIBLE);
        } else {
            mFooterView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * set headerRefreshListener
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    /**
     * set footerRefreshListener
     */
    public void setOnFooterRefreshListener(
            OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnFooterRefreshListener {
        void onFooterRefresh(PullToRefreshView view);
    }

    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener {
        void onHeaderRefresh(PullToRefreshView view);
    }

    /** 下拉刷新监听开启异步线程，因此加载在刷新监听中不能做修改view操作，适合做加载网络操作 */
    // private class PullDownToRefreshTask implements Runnable {
    //
    // @Override
    // public void run() {
    // mOnHeaderRefreshListener.onHeaderRefresh(PullToRefreshView.this);
    // }
    //
    // }

    /** 上拉加载监听开启异步线程，因此加载在刷新监听中不能做修改view操作，适合做加载网络操作 */
    // private class PullUpToRefreshTask implements Runnable {
    //
    // @Override
    // public void run() {
    // mOnFooterRefreshListener.onFooterRefresh(PullToRefreshView.this);
    // }
    //
    // }

}
