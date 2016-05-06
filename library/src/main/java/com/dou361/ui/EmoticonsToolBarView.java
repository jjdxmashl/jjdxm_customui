package com.dou361.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dou361.bean.PageSetEntity;
import com.dou361.utils.ResourceUtils;
import com.dou361.utils.imageloader.ImageLoader;
import com.nineoldandroids.view.ViewHelper;

import java.io.IOException;
import java.util.ArrayList;

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
 * 创建日期：2016/5/3 9:39
 * <p>
 * 描 述：表情集的toolbar
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class EmoticonsToolBarView extends RelativeLayout {

    private final Context context;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected ArrayList<View> mToolBtnList = new ArrayList<>();
    protected int mBtnWidth;

    protected HorizontalScrollView hsv_toolbar;
    protected LinearLayout ly_tool;

    public EmoticonsToolBarView(Context context) {
        this(context, null);
    }

    public EmoticonsToolBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(ResourceUtils.getResourceIdByName(context, "layout", "customui_view_emoticonstoolbar"), this);
        this.mContext = context;
        mBtnWidth = (int) context.getResources().getDimension(ResourceUtils.getResourceIdByName(context, "dimen", "bar_tool_btn_width"));
        hsv_toolbar = (HorizontalScrollView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "hsv_toolbar"));
        ly_tool = (LinearLayout) findViewById(ResourceUtils.getResourceIdByName(context, "id", "ly_tool"));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 3) {
            throw new IllegalArgumentException("can host only two direct child");
        }
    }

    public void addFixedToolItemView(View view, boolean isRight) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        LayoutParams hsvParams = (LayoutParams) hsv_toolbar.getLayoutParams();
        if (view.getId() <= 0) {
            view.setId(isRight ? ResourceUtils.getResourceIdByName(context, "id", "id_toolbar_right") : ResourceUtils.getResourceIdByName(context, "id", "id_toolbar_left"));
        }
        if (isRight) {
            params.addRule(ALIGN_PARENT_RIGHT);
            hsvParams.addRule(LEFT_OF, view.getId());
        } else {
            params.addRule(ALIGN_PARENT_LEFT);
            hsvParams.addRule(RIGHT_OF, view.getId());
        }
        addView(view, params);
        hsv_toolbar.setLayoutParams(hsvParams);
    }

    protected View getCommonItemToolBtn() {
        return mInflater == null ? null : mInflater.inflate(ResourceUtils.getResourceIdByName(context, "layout", "customui_item_toolbtn"), null);
    }

    protected void initItemToolBtn(View toolBtnView, int rec, final PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        ImageView iv_icon = (ImageView) toolBtnView.findViewById(ResourceUtils.getResourceIdByName(context, "id", "iv_icon"));
        if (rec > 0) {
            iv_icon.setImageResource(rec);
        }
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(mBtnWidth, LayoutParams.MATCH_PARENT);
        iv_icon.setLayoutParams(imgParams);
        if (pageSetEntity != null) {
            iv_icon.setTag(ResourceUtils.getResourceIdByName(context, "id", "id_tag_pageset"), pageSetEntity);
            try {
                ImageLoader.getInstance(mContext).displayImage(pageSetEntity.getIconUri(), iv_icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toolBtnView.setOnClickListener(onClickListener != null ? onClickListener : new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListeners != null && pageSetEntity != null) {
                    mItemClickListeners.onToolBarItemClick(pageSetEntity);
                }
            }
        });
    }

    protected View getToolBgBtn(View parentView) {
        return parentView.findViewById(ResourceUtils.getResourceIdByName(context, "id", "iv_icon"));
    }

    public void addFixedToolItemView(boolean isRight, int rec, final PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        View toolBtnView = getCommonItemToolBtn();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        LayoutParams hsvParams = (LayoutParams) hsv_toolbar.getLayoutParams();
        if (toolBtnView.getId() <= 0) {
            toolBtnView.setId(isRight ? ResourceUtils.getResourceIdByName(context, "id", "id_toolbar_right") : ResourceUtils.getResourceIdByName(context, "id", "id_toolbar_left"));
        }
        if (isRight) {
            params.addRule(ALIGN_PARENT_RIGHT);
            hsvParams.addRule(LEFT_OF, toolBtnView.getId());
        } else {
            params.addRule(ALIGN_PARENT_LEFT);
            hsvParams.addRule(RIGHT_OF, toolBtnView.getId());
        }
        addView(toolBtnView, params);
        hsv_toolbar.setLayoutParams(hsvParams);
        initItemToolBtn(toolBtnView, rec, pageSetEntity, onClickListener);
    }

    public void addToolItemView(PageSetEntity pageSetEntity) {
        addToolItemView(0, pageSetEntity, null);
    }

    public void addToolItemView(int rec, OnClickListener onClickListener) {
        addToolItemView(rec, null, onClickListener);
    }

    public void addToolItemView(int rec, final PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        View toolBtnView = getCommonItemToolBtn();
        initItemToolBtn(toolBtnView, rec, pageSetEntity, onClickListener);
        ly_tool.addView(toolBtnView);
        mToolBtnList.add(getToolBgBtn(toolBtnView));
    }

    public void setToolBtnSelect(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            return;
        }
        int select = 0;
        for (int i = 0; i < mToolBtnList.size(); i++) {
            Object object = mToolBtnList.get(i).getTag(ResourceUtils.getResourceIdByName(context, "id", "id_tag_pageset"));
            if (object != null && object instanceof PageSetEntity && uuid.equals(((PageSetEntity) object).getUuid())) {
                mToolBtnList.get(i).setBackgroundColor(getResources().getColor(ResourceUtils.getResourceIdByName(context, "color", "toolbar_btn_select")));
                select = i;
            } else {
                mToolBtnList.get(i).setBackgroundResource(ResourceUtils.getResourceIdByName(context, "drawable", "customui_btn_toolbtn_bg"));
            }
        }
        scrollToBtnPosition(select);
    }

    protected void scrollToBtnPosition(final int position) {
        int childCount = ly_tool.getChildCount();
        if (position < childCount) {
            hsv_toolbar.post(new Runnable() {
                @Override
                public void run() {
                    int mScrollX = hsv_toolbar.getScrollX();

                    int childX = (int) ViewHelper.getX(ly_tool.getChildAt(position));

                    if (childX < mScrollX) {
                        hsv_toolbar.scrollTo(childX, 0);
                        return;
                    }

                    int childWidth = ly_tool.getChildAt(position).getWidth();
                    int hsvWidth = hsv_toolbar.getWidth();
                    int childRight = childX + childWidth;
                    int scrollRight = mScrollX + hsvWidth;

                    if (childRight > scrollRight) {
                        hsv_toolbar.scrollTo(childRight - scrollRight, 0);
                        return;
                    }
                }
            });
        }
    }

    public void setBtnWidth(int width) {
        mBtnWidth = width;
    }

    protected OnToolBarItemClickListener mItemClickListeners;

    public interface OnToolBarItemClickListener {
        void onToolBarItemClick(PageSetEntity pageSetEntity);
    }

    public void setOnToolBarItemClickListener(OnToolBarItemClickListener listener) {
        this.mItemClickListeners = listener;
    }
}

