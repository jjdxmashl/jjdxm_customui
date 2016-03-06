package com.dou361.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.dou361.adapter.PopuWindowAdapter;
import com.dou361.bean.Popu;
import com.dou361.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class PopuWindowView {

    View viewItem = null;
    ListView pupoListView;
    PopupWindow pullDownView;// 弹出窗口
    private List<Popu> popuLists = new ArrayList<Popu>();
    private PopuWindowAdapter mPopuWindowAdapter;
    private Context mContext;
    private TdataListener mTdataListener;
    private int maxLine = 5;

    public PopuWindowView(Context mContext, int width) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        viewItem = inflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_popu_options"), null);
        pupoListView = (ListView) viewItem.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_list"));
        mPopuWindowAdapter = new PopuWindowAdapter(mContext, popuLists);
        pupoListView.setAdapter(mPopuWindowAdapter);
        pullDownView = new PopupWindow(viewItem, width,
                LayoutParams.WRAP_CONTENT, true);
        pullDownView.setOutsideTouchable(true);
        pullDownView.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 设置下拉框的数据
     */
    public void initPupoData(TdataListener tdataListener) {
        tdataListener.initPupoData(popuLists);
        if (popuLists != null && popuLists.size() > maxLine) {
            pullDownView.setHeight(dip2px(maxLine * 40));
        }
        mPopuWindowAdapter.notifyDataSetChanged();
    }

    private int dip2px(int dip) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 设置最大行popuWindow
     */
    public void setMaxLines(int maxLines) {
        maxLine = maxLines;
    }

    /**
     * 显示popuWindow
     */
    public void showing(View v) {
        pullDownView.showAsDropDown(v, 0, -3);
    }

    /**
     * 关闭popuWindow
     */
    public void dismiss() {
        pullDownView.dismiss();
    }

    /**
     * 动态设置下拉框的数据
     */
    public interface TdataListener {
        /**
         * 初始化数据
         */
        void initPupoData(List<Popu> lists);
    }

    /**
     * 获取选择的名称
     */
    public String getTitle(int popuPosition) {
        return popuLists.get(popuPosition).getTitle();
    }

    /**
     * 获取选择的id
     */
    public int getId(int popuPosition) {
        return popuLists.get(popuPosition).getId();
    }

    /**
     * 获取选择的sid
     */
    public String getSid(int popuPosition) {
        return popuLists.get(popuPosition).getSid();
    }

}
