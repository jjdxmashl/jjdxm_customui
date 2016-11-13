package com.dou361.customui.holder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.customui.R;
import com.dou361.customui.bean.AdvChart;
import com.dou361.customui.ui.ChildViewPager;
import com.dou361.customui.ui.IndicatorView;
import com.dou361.customui.utils.ResourceUtils;

import java.util.ArrayList;
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
public class HomePictureHolder extends BaseHolder<List<AdvChart>>
        implements OnPageChangeListener {

    private final Handler mHanlder;
    private MyPagerAdapter mPagerAdapter;
    private AutoPlayRunnable mAutoPlayRunnable;
    private View view;
    ChildViewPager mViewPager;
    TextView tv_title;
    LinearLayout ll;
    private IndicatorView mIndicator;
    private int currentIndex;
    private TypeClickListener mTypeClickListener;
    private ImageloaderListener mImageloaderListener;

    public HomePictureHolder(Context mContext, Handler mHanlder) {
        super(mContext);
        this.mHanlder = mHanlder;
    }

    public interface ImageloaderListener {
        void onImageloaderListener(ImageView iamgeView, String url);
    }

    public void setOnImageloaderListener(ImageloaderListener l) {
        mImageloaderListener = l;
    }

    public interface TypeClickListener {
        void onTypeClickListener(int type, int vodType, String content);
    }

    public void setTypeClickListener(TypeClickListener l) {
        mTypeClickListener = l;
    }

    @Override
    public View initView() {

        view = LayoutInflater.from(mContext).inflate(R.layout.customui_holder_home_picture, null);
        mViewPager = (ChildViewPager) view.findViewById(R.id.view_pager);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        mPagerAdapter = new MyPagerAdapter();
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
        List<AdvChart> datas = getData();
        mIndicator.setCount(datas.size());
        mViewPager.setCurrentItem(datas.size() * 1000, false);
        mAutoPlayRunnable.start();
    }

    class MyPagerAdapter extends PagerAdapter {
        List<ImageView> mViewCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null && object instanceof ImageView) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
                mViewCache.add(imageView);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView;
            if (mViewCache.size() > 0) {
                imageView = mViewCache.remove(0);
            } else {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            int index = position % (getData().size());
            String url = getData().get(index).getUrl();
            mImageloaderListener.onImageloaderListener(imageView, url);
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        AdvChart data = getData().get(currentIndex);
                        if (data != null) {
                            mTypeClickListener.onTypeClickListener(data.getType(), data.getSubType(), data.getContent());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            container.addView(imageView, 0);
            return imageView;
        }
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
