package com.dou361.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.dou361.utils.ResourceUtils;

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
 * 创建日期：2016/5/3 9:33
 * <p/>
 * 描 述：表情包面板
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class EmoticonPageView extends RelativeLayout {

    private GridView mGvEmotion;

    public GridView getEmoticonsGridView() {
        return mGvEmotion;
    }

    public EmoticonPageView(Context context) {
        this(context, null);
    }

    public EmoticonPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(ResourceUtils.getResourceIdByName(context, "layout", "customui_item_emoticonpage"), this);
        mGvEmotion = (GridView) view.findViewById(ResourceUtils.getResourceIdByName(context, "id", "gv_emotion"));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            mGvEmotion.setMotionEventSplittingEnabled(false);
        }
        mGvEmotion.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mGvEmotion.setCacheColorHint(0);
        mGvEmotion.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGvEmotion.setVerticalScrollBarEnabled(false);
    }

    public void setNumColumns(int row) {
        mGvEmotion.setNumColumns(row);
    }
}
