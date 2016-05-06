package com.dou361.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.dou361.adapter.AppsAdapter;
import com.dou361.bean.AppBean;
import com.dou361.utils.ResourceUtils;

import java.util.ArrayList;

public class SimpleAppsGridView extends RelativeLayout {

    protected View view;
    protected final Context mContext;

    public SimpleAppsGridView(Context mContext) {
        this(mContext, null);
    }

    public SimpleAppsGridView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_apps"), this);
        init();
    }

    protected void init() {
        GridView gv_apps = (GridView) view.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "gv_apps"));
        ArrayList<AppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_photo"), "图片"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_camera"), "拍照"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_audio"), "视频"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_qzone"), "空间"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_contact"), "联系人"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_file"), "文件"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_icon_loaction"), "位置"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }
}
