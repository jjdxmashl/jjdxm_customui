package com.dou361.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.bean.Popu;
import com.dou361.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ========================================
 * <p>
 * 版 权：深圳市晶网电子科技有限公司 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2015/11/11
 * <p>
 * 描 述：PopuWindow适配器
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class PopuWindowAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 供下拉的集合包括id
     */
    List<Popu> list;
    private LayoutInflater inflater;

    public PopuWindowAdapter(Context mContext, List<Popu> lists) {
        this.mContext = mContext;
        this.list = lists;
        if (list == null) {
            list = new ArrayList<Popu>();
        }
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_popu_option_item"), null);

            holder.textView = (TextView) convertView
                    .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_item_text"));

            holder.imageView = (ImageView) convertView
                    .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_delImage"));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getTitle());


        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

}