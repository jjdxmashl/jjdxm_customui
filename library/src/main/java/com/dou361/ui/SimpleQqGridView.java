package com.dou361.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.dou361.adapter.AppsAdapter;
import com.dou361.bean.AppBean;
import com.dou361.utils.EmoticonsKeyboardUtils;
import com.dou361.utils.ResourceUtils;

import java.util.ArrayList;

public class SimpleQqGridView extends SimpleAppsGridView {

    public SimpleQqGridView(Context context) {
        super(context);
    }

    public SimpleQqGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        setBackgroundColor(getResources().getColor(ResourceUtils.getResourceIdByName(mContext, "color", "white")));
        GridView gv_apps = (GridView) view.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "gv_apps"));
        gv_apps.setPadding(0, 0, 0, EmoticonsKeyboardUtils.dip2px(mContext, 20));
        RelativeLayout.LayoutParams params = (LayoutParams) gv_apps.getLayoutParams();
        params.bottomMargin = EmoticonsKeyboardUtils.dip2px(mContext, 20);
        gv_apps.setLayoutParams(params);
        gv_apps.setVerticalSpacing(EmoticonsKeyboardUtils.dip2px(mContext, 18));
        ArrayList<AppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_lcw"), "QQ电话"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_lde"), "视频电话"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_ldh"), "短视频"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_lcx"), "收藏"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_ldc"), "发红包"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_ldk"), "转账"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_ldf"), "悄悄话"));
        mAppBeanList.add(new AppBean(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_lcu"), "文件"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }
}
