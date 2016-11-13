package com.dou361.customui.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dou361.customui.R;
import com.dou361.customui.utils.ResourceUtils;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/3/15 21:29
 * <p>
 * 描 述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class DateTimeSelectorDialogBuilder extends NiftyDialogBuilder implements
        View.OnClickListener {

    private Context context;
    private RelativeLayout rlCustomLayout;
    private DateSelectorWheelView dateWheelView;
    private FrameLayout flSecondeCustomLayout;
    private OnSaveListener saveListener;
    /**
     * 用来区分多个对象
     */
    private int tag;
    /**
     * 默认方向标示
     */
    private static int mOrientation = 1;
    private static DateTimeSelectorDialogBuilder instance;

    public interface OnSaveListener {
        abstract void onSaveSelectedDate(int tag, String selectedDate);
    }

    public DateTimeSelectorDialogBuilder(Context context) {
        super(context);
        this.context = context;
        initDialog();
    }

    public DateTimeSelectorDialogBuilder(int tag, Context context) {
        super(context);
        this.context = context;
        this.tag = tag;
        initDialog();
    }

    public DateTimeSelectorDialogBuilder(Context context, int theme) {
        super(context, theme);
        this.context = context;
        initDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.customui_datepick_edit_dialog_coner);
    }

    public static DateTimeSelectorDialogBuilder getInstance(Context context) {

        int ort = context.getResources().getConfiguration().orientation;
        if (mOrientation != ort) {
            mOrientation = ort;
            instance = null;
        }

        if (instance == null || ((Activity) context).isFinishing()) {
            synchronized (DateTimeSelectorDialogBuilder.class) {
                if (instance == null) {
                    instance = new DateTimeSelectorDialogBuilder(context, R.style.customui_datepick_dialog_untran);
                }
            }
        }
        return instance;

    }

    private void initDialog() {
        rlCustomLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.customui_datepick_selector_dialog_layout, null);
        dateWheelView = (DateSelectorWheelView) rlCustomLayout
                .findViewById(R.id.pdwv_date_time_selector_wheelView);
        dateWheelView.setTitleClick(this);
        flSecondeCustomLayout = (FrameLayout) rlCustomLayout
                .findViewById(R.id.fl_date_time_custom_layout);
        setDialogProperties();
    }

    private void setDialogProperties() {
        int width = ResourceUtils.getScreenWidth(context) * 4 / 5;
        this.withDialogWindows(width, LayoutParams.WRAP_CONTENT)
                .withTitleColor("#FFFFFF").withTitle("选择日期")
                .setDialogClick(this).withPreviousText("取消")
                .withPreviousTextColor("#3598da").withDuration(100)
                .setPreviousLayoutClick(this).withNextText("保存")
                .withMessageMiss(View.GONE).withNextTextColor("#3598da")
                .setNextLayoutClick(this)
                .setCustomView(rlCustomLayout, context);

    }

    /**
     * 设置自定义布局
     *
     * @param view
     * @param context
     * @return
     */
    public DateTimeSelectorDialogBuilder setSencondeCustomView(View view,
                                                               Context context) {
        if (flSecondeCustomLayout.getChildCount() > 0) {
            flSecondeCustomLayout.removeAllViews();
        }
        flSecondeCustomLayout.addView(view);
        return this;
    }

    /**
     * 设置自定义布局
     *
     * @param resId
     * @param context
     * @return
     */
    public DateTimeSelectorDialogBuilder setSencondeCustomView(int resId,
                                                               Context context) {
        View view = View.inflate(context, resId, null);
        if (flSecondeCustomLayout.getChildCount() > 0) {
            flSecondeCustomLayout.removeAllViews();
        }
        flSecondeCustomLayout.addView(view);
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == dateWheelView.getTitleId()) {
            if (dateWheelView.getDateSelectorVisibility() == View.VISIBLE) {
                dateWheelView.setDateSelectorVisiblility(View.GONE);
            } else {
                dateWheelView.setDateSelectorVisiblility(View.VISIBLE);
            }
        } else if (id == mPreviousLayout.getId()) {
            dismiss();
        } else if (id == mNextLayout.getId()) {
            if (null != saveListener) {
                saveListener.onSaveSelectedDate(tag, dateWheelView.getSelectedDate());
            }
            dismiss();
        }
    }

    /**
     * 获取日期选择器
     *
     * @return
     */
    public DateSelectorWheelView getDateWheelView() {
        return dateWheelView;
    }

    /**
     * 设置保存监听
     *
     * @param saveListener
     */
    public void setOnSaveListener(OnSaveListener saveListener) {
        this.saveListener = saveListener;
    }

    /**
     * 最初显示时是否可以可见
     *
     * @param visibility
     */
    public void setWheelViewVisibility(int visibility) {
        dateWheelView.setDateSelectorVisiblility(visibility);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        instance = null;
    }

    public static final int TYPE_YYYYMM = 3;
    public static final int TYPE_YYYYMMDD = 0;
    public static final int TYPE_YYYYMMDDHHMM = 2;
    public static final int TYPE_YYYYMMDDHHMMSS = 1;

    /**
     * 设置显示的样式
     */
    public void setShowDateType(int type) {
        dateWheelView.setShowDateType(type);
    }


}
