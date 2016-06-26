package com.dou361.customui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.customui.bean.EmoticonEntity;
import com.dou361.customui.bean.EmoticonPageEntity;
import com.dou361.customui.listener.EmoticonClickListener;
import com.dou361.customui.utils.KeyBoardParams;
import com.dou361.customui.utils.ResourceUtils;
import com.dou361.customui.utils.imageloader.ImageLoader;

import java.io.IOException;

public class BigEmoticonsAdapter extends EmoticonsAdapter<EmoticonEntity> {

    protected final double DEF_HEIGHTMAXTATIO = 1.6;

    public BigEmoticonsAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        super(context, emoticonPageEntity, onEmoticonClickListener);
        this.mContext = context;
        this.mItemHeight = (int) context.getResources().getDimension(ResourceUtils.getResourceIdByName(context, "dimen", "item_emoticon_size_big"));
        this.mItemHeightMaxRatio = DEF_HEIGHTMAXTATIO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_item_emoticon_big"), null);
            viewHolder.rootView = convertView;
            viewHolder.ly_root = (LinearLayout) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_root"));
            viewHolder.iv_emoticon = (ImageView) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "iv_emoticon"));
            viewHolder.tv_content = (TextView) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_content"));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindView(position, viewHolder);
        updateUI(viewHolder, parent);
        return convertView;
    }

    protected void updateUI(ViewHolder viewHolder, ViewGroup parent) {
        if (mDefalutItemHeight != mItemHeight) {
            viewHolder.iv_emoticon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mItemHeight));
        }
        mItemHeightMax = this.mItemHeightMax != 0 ? this.mItemHeightMax : (int) (mItemHeight * mItemHeightMaxRatio);
        mItemHeightMin = this.mItemHeightMin != 0 ? this.mItemHeightMin : mItemHeight;
        int realItemHeight = ((View) parent.getParent()).getMeasuredHeight() / mEmoticonPageEntity.getLine();
        realItemHeight = Math.min(realItemHeight, mItemHeightMax);
        realItemHeight = Math.max(realItemHeight, mItemHeightMin);
        viewHolder.ly_root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, realItemHeight));
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

    public static class ViewHolder {
        public View rootView;
        public LinearLayout ly_root;
        public ImageView iv_emoticon;
        public TextView tv_content;
    }
}