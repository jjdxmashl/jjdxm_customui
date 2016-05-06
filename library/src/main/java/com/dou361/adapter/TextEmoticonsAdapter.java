package com.dou361.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.adapter.EmoticonsAdapter;
import com.dou361.bean.EmoticonEntity;
import com.dou361.bean.EmoticonPageEntity;
import com.dou361.listener.EmoticonClickListener;
import com.dou361.utils.KeyBoardParams;
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
 * 创建日期：2016/4/29 17:41
 * <p/>
 * 描 述：颜文字适配器
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class TextEmoticonsAdapter extends EmoticonsAdapter<EmoticonEntity> {

    public TextEmoticonsAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        super(context, emoticonPageEntity, onEmoticonClickListener);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_item_emoticon_text"), null);
            viewHolder.rootView = convertView;
            viewHolder.ly_root = (LinearLayout) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_root"));
            viewHolder.tv_content = (TextView) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_content"));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final boolean isDelBtn = isDelBtn(position);
        final EmoticonEntity emoticonEntity = mData.get(position);
        if (isDelBtn) {
            viewHolder.ly_root.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "bg_emoticon"));
        } else {
            viewHolder.tv_content.setVisibility(View.VISIBLE);
            if (emoticonEntity != null) {
                viewHolder.tv_content.setText(emoticonEntity.getContent());
                viewHolder.ly_root.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "bg_emoticon"));
            }
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEmoticonClickListener != null) {
                    mOnEmoticonClickListener.onEmoticonClick(emoticonEntity, KeyBoardParams.EMOTICON_CLICK_TEXT, isDelBtn);
                }
            }
        });

        updateUI(viewHolder, parent);
        return convertView;
    }

    protected void updateUI(ViewHolder viewHolder, ViewGroup parent) {
        if (mDefalutItemHeight != mItemHeight) {
            viewHolder.tv_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mItemHeight));
        }
        mItemHeightMax = this.mItemHeightMax != 0 ? this.mItemHeightMax : (int) (mItemHeight * mItemHeightMaxRatio);
        mItemHeightMin = this.mItemHeightMin != 0 ? this.mItemHeightMin : mItemHeight;
        int realItemHeight = ((View) parent.getParent()).getMeasuredHeight() / mEmoticonPageEntity.getLine();
        realItemHeight = Math.min(realItemHeight, mItemHeightMax);
        realItemHeight = Math.max(realItemHeight, mItemHeightMin);
        viewHolder.ly_root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, realItemHeight));
    }

    public static class ViewHolder {
        public View rootView;
        public LinearLayout ly_root;
        public TextView tv_content;
    }
}