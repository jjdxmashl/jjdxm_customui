package com.dou361.customui.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.GridView;

import com.dou361.customui.adapter.AppsAdapter;
import com.dou361.customui.bean.AppBean;
import com.dou361.customui.utils.ResourceUtils;

import java.util.ArrayList;

public class CustomAppsGridView extends SimpleAppsGridView {

    public CustomAppsGridView(Context context) {
        super(context);
    }

    public CustomAppsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        GridView gv_apps = (GridView) view.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "gv_apps"));
        gv_apps.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv_apps.setNumColumns(2);
        ArrayList<AppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_chatting_photo"), "图片"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_chatting_camera"), "拍照"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }
}
