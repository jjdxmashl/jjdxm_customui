package com.dou361.listener;

import android.view.View;
import android.view.ViewGroup;

import com.dou361.bean.PageEntity;

public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
