package com.dou361.listener;

public interface EmoticonClickListener<T> {

    void onEmoticonClick(T t, int actionType, boolean isDelBtn);
}
