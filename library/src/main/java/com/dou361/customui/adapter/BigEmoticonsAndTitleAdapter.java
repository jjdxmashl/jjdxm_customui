package com.dou361.customui.adapter;

import android.content.Context;
import android.view.View;

import com.dou361.customui.adapter.BigEmoticonsAdapter;
import com.dou361.customui.bean.EmoticonEntity;
import com.dou361.customui.bean.EmoticonPageEntity;
import com.dou361.customui.listener.EmoticonClickListener;
import com.dou361.customui.utils.KeyBoardParams;
import com.dou361.customui.utils.ResourceUtils;
import com.dou361.customui.utils.imageloader.ImageLoader;

import java.io.IOException;

public class BigEmoticonsAndTitleAdapter extends BigEmoticonsAdapter {

    protected final double DEF_HEIGHTMAXTATIO = 1.6;

    public BigEmoticonsAndTitleAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        super(context, emoticonPageEntity, onEmoticonClickListener);
        this.mContext = context;
        this.mItemHeight = (int) context.getResources().getDimension(ResourceUtils.getResourceIdByName(context, "dimen", "item_emoticon_size_big"));
        this.mItemHeightMaxRatio = DEF_HEIGHTMAXTATIO;
    }

    protected void bindView(int position, ViewHolder viewHolder) {
        final boolean isDelBtn = isDelBtn(position);
        final EmoticonEntity emoticonEntity = mData.get(position);
        if (isDelBtn) {
            viewHolder.iv_emoticon.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_del"));
            viewHolder.iv_emoticon.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "bg_emoticon"));
        } else {
            if (emoticonEntity != null) {
                try {
                    ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    viewHolder.tv_content.setVisibility(View.VISIBLE);
                    viewHolder.tv_content.setText(emoticonEntity.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewHolder.iv_emoticon.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "bg_emoticon"));
            }
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEmoticonClickListener != null) {
                    mOnEmoticonClickListener.onEmoticonClick(emoticonEntity, KeyBoardParams.EMOTICON_CLICK_BIGIMAGE, isDelBtn);
                }
            }
        });
    }
}