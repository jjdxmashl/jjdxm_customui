package com.dou361.customui.listener;

import android.view.ViewGroup;

import com.dou361.customui.adapter.EmoticonsAdapter;

public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
