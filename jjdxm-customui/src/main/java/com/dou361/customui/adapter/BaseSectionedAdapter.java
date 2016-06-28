package com.dou361.customui.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dou361.customui.ui.PinnedHeaderListView.PinnedSectionedHeaderAdapter;

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
 * 创建日期：2016/3/15 21:25
 * <p>
 * 描 述：分组Listview适配器，结合PinnedHeaderListView使用
 * 如果需要添加头部，一定要在Adapter中重写方法
 *
 *  public boolean hasHeaderType() {
 * return true;
 * }
 * 默认是false的
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public abstract class BaseSectionedAdapter extends BaseAdapter implements PinnedSectionedHeaderAdapter {


    private static int FIRST_VIEW_TYPE = 0;
    private static int SECOND_VIEW_TYPE = 0;

    /**
     * 二级标题对应位置的索引 Holds the calculated values of @{link
     * getPositionInSectionForPosition}
     */
    private SparseArray<Integer> mSectionPositionCache;
    /**
     * 一级标题对应位置的索引 Holds the calculated values of @{link getSectionForPosition}
     */
    private SparseArray<Integer> mSectionCache;
    /**
     * 一级标题的索引和对应二级标题的总数的键值对 Holds the calculated values of @{link
     * getCountForSection}
     */
    private SparseArray<Integer> mSectionCountCache;

    /**
     * 总条目数量 Caches the item count
     */
    private int mCount;
    /**
     * 一级标题总数量 Caches the section count
     */
    private int mSectionCount;

    public BaseSectionedAdapter() {
        super();
        mSectionCache = new SparseArray<Integer>();
        mSectionPositionCache = new SparseArray<Integer>();
        mSectionCountCache = new SparseArray<Integer>();
        mCount = -1;
        mSectionCount = -1;
    }

    @Override
    public void notifyDataSetChanged() {
        mSectionCache.clear();
        mSectionPositionCache.clear();
        mSectionCountCache.clear();
        mCount = -1;
        mSectionCount = -1;
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mSectionCache.clear();
        mSectionPositionCache.clear();
        mSectionCountCache.clear();
        mCount = -1;
        mSectionCount = -1;
        super.notifyDataSetInvalidated();
    }

    @Override
    public final int getCount() {
        if (mCount >= 0) {
            return mCount;
        }
        int count = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            count += internalGetCountForSection(i);
            count++; // for the header view
        }
        return count;
    }

    @Override
    public final Object getItem(int position) {
        return getItem(getSectionForPosition(position),
                getPositionInSectionForPosition(position));
    }

    @Override
    public final long getItemId(int position) {
        return getItemId(getSectionForPosition(position),
                getPositionInSectionForPosition(position));
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (isSectionFirst(position)) {
            return getSectionView(getSectionForPosition(position),
                    convertView, parent);
        }
        return getChildItemView(getSectionForPosition(position),
                getPositionInSectionForPosition(position), convertView, parent);
    }

    @Override
    public final int getItemViewType(int position) {
        if (isSectionFirst(position)) {
            return getSecondViewTypeCount()
                    + getSectionFirstViewType(getSectionForPosition(position));
        }
        return getSecondViewType(getSectionForPosition(position),
                getPositionInSectionForPosition(position));
    }

    @Override
    public final int getViewTypeCount() {
        return getSecondViewTypeCount() + getSectionFirstViewTypeCount();
    }

    /**
     * 获取指定索引的一级标题对应索引
     */
    public final int getSectionForPosition(int position) {
        // first try to retrieve values from cache
        Integer cachedSection = mSectionCache.get(position);
        if (cachedSection != null) {
            return cachedSection;
        }
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            int sectionCount = internalGetCountForSection(i);
            int sectionEnd = sectionStart + sectionCount + 1;

            if (position >= sectionStart && position < sectionEnd) {
                mSectionCache.put(position, i);
                return i;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    /**
     * 获取指定索引的二级标题对应索引
     */
    public int getPositionInSectionForPosition(int position) {
        // first try to retrieve values from cache
        Integer cachedPosition = mSectionPositionCache.get(position);
        if (cachedPosition != null) {
            return cachedPosition;
        }
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            int sectionCount = internalGetCountForSection(i);
            int sectionEnd = sectionStart + sectionCount + 1;

            if (position >= sectionStart && position < sectionEnd) {
                int positionInSection = position - sectionStart - 1;
                mSectionPositionCache.put(position, positionInSection);
                return positionInSection;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    /**
     * 是否属于一级标题条目
     */
    public final boolean isSectionFirst(int position) {
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {

            if (position == sectionStart) {
                return true;
            } else if (position < sectionStart) {
                return false;
            }
            sectionStart += internalGetCountForSection(i) + 1;
        }
        return false;
    }

    public boolean hasHeaderType() {
        return false;
    }

    public int getSecondViewType(int section, int position) {
        return SECOND_VIEW_TYPE;
    }

    public int getSecondViewTypeCount() {
        return 1;
    }

    public int getSectionFirstViewType(int section) {
        return FIRST_VIEW_TYPE;
    }

    public int getSectionFirstViewTypeCount() {
        return 1;
    }

    public abstract Object getItem(int section, int position);

    public abstract long getItemId(int section, int position);

    public abstract int getSectionCount();

    public abstract int getCountForSection(int section);

    public abstract View getChildItemView(int section, int position,
                                          View convertView, ViewGroup parent);

    public abstract View getSectionView(int section, View convertView,
                                        ViewGroup parent);

    /**
     * 指定一级下的二级总数
     */
    private int internalGetCountForSection(int section) {
        Integer cachedSectionCount = mSectionCountCache.get(section);
        if (cachedSectionCount != null) {
            return cachedSectionCount;
        }
        int sectionCount = getCountForSection(section);
        mSectionCountCache.put(section, sectionCount);
        return sectionCount;
    }

    /**
     * 一级标题的总数
     */
    private int internalGetSectionCount() {
        if (mSectionCount >= 0) {
            return mSectionCount;
        }
        mSectionCount = getSectionCount();
        return mSectionCount;
    }


}
