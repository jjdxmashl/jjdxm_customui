package com.dou361.customui.holder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.customui.R;
import com.dou361.customui.adapter.HomePicturePagerAdapter;
import com.dou361.customui.bean.AdvBean;
import com.dou361.customui.listener.AdvClickListener;
import com.dou361.customui.listener.AdvLoadingListener;
import com.dou361.customui.widget.ChildViewPager;
import com.dou361.customui.widget.IndicatorView;

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
 * 创建日期：2016/3/16 0:07
 * <p>
 * 描 述：轮播图，首页广告位
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class HomeAdvHolder extends BaseHolder<List<AdvBean>>
        implements OnPageChangeListener {

    private final Handler mHanlder;
    private HomePicturePagerAdapter mPagerAdapter;
    private AutoPlayRunnable mAutoPlayRunnable;
    private View view;
    ChildViewPager mViewPager;
    TextView tv_title;
    LinearLayout ll;
    private IndicatorView mIndicator;
    private int currentIndex;

    public HomeAdvHolder(Context mContext, Handler mHanlder) {
        super(mContext);
        this.mHanlder = mHanlder;
    }


    public void setOnAdvLoadingListener(AdvLoadingListener l) {
        if (mPagerAdapter != null) {
            mPagerAdapter.setOnAdvLoadingListener(l);
        }
    }


    public void setAdvClickListener(AdvClickListener l) {
        if (mPagerAdapter != null) {
            mPagerAdapter.setAdvClickListener(l);
        }
    }

    @Override
    public View initView() {

        view = LayoutInflater.from(mContext).inflate(R.layout.customui_holder_home_picture, null);
        mViewPager = (ChildViewPager) view.findViewById(R.id.view_pager);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        mPagerAdapter = new HomePicturePagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator = new IndicatorView(mContext);
        // 设置点和点之间的间隙
        mIndicator.setInterval(5);
        mIndicator.setSelection(0);

        ll.addView(mIndicator);
        mViewPager.setOnPageChangeListener(this);

        mAutoPlayRunnable = new AutoPlayRunnable();
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mAutoPlayRunnable.stop();
                } else if (event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mAutoPlayRunnable.start();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void refreshView() {
        List<AdvBean> datas = getData();
        mPagerAdapter.setList(datas);
        mIndicator.setCount(datas.size());
        mViewPager.setCurrentItem(datas.size() * 1000, false);
        mAutoPlayRunnable.start();
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int positon) {
        currentIndex = positon % getData().size();
        mIndicator.setSelection(currentIndex);
        tv_title.setText(getData().get(currentIndex).getTitle());
    }

    private class AutoPlayRunnable implements Runnable {
        private int AUTO_PLAY_INTERVAL = 6000;
        private boolean mShouldAutoPlay;

        public AutoPlayRunnable() {
            mShouldAutoPlay = false;
        }

        public void start() {
            if (!mShouldAutoPlay) {
                mShouldAutoPlay = true;
                mHanlder.removeCallbacks(this);
                mHanlder.postDelayed(this, AUTO_PLAY_INTERVAL);
            }
        }

        public void stop() {
            if (mShouldAutoPlay) {
                mHanlder.removeCallbacks(this);
                mShouldAutoPlay = false;
            }
        }

        @Override
        public void run() {
            if (mShouldAutoPlay) {
                mHanlder.removeCallbacks(this);
                int position = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(position + 1, true);
                mHanlder.postDelayed(this, AUTO_PLAY_INTERVAL);
            }
        }
    }
}
