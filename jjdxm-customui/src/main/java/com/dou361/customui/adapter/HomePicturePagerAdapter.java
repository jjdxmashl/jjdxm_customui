package com.dou361.customui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dou361.customui.bean.AdvBean;
import com.dou361.customui.listener.AdvClickListener;
import com.dou361.customui.listener.AdvLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class HomePicturePagerAdapter extends PagerAdapter {
    List<ImageView> mViewCache = new ArrayList<ImageView>();
    List<AdvBean> list = new ArrayList<AdvBean>();

    private AdvClickListener mAdvClickListener;
    private AdvLoadingListener mAdvLoadingListener;

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
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        if (mViewCache.size() > 0) {
            imageView = mViewCache.remove(0);
        } else {
            imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        int index = position % (list.size());
        String url = list.get(index).getUrl();
        if (mAdvLoadingListener != null) {
            mAdvLoadingListener.onAdvLoadingListener(imageView, url);
        }
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    AdvBean data = list.get(position % list.size());
                    if (data != null) {
                        if (mAdvClickListener != null) {
                            mAdvClickListener.onAdvClickListener(data.getType(), data.getContent());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        container.addView(imageView, 0);
        return imageView;
    }


    public void setOnAdvLoadingListener(AdvLoadingListener l) {
        mAdvLoadingListener = l;
    }

    public void setAdvClickListener(AdvClickListener l) {
        mAdvClickListener = l;
    }

    public void setList(List<AdvBean> list) {
        this.list.clear();
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
}