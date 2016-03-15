package com.dou361.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dou361.utils.ResourceUtils;

/**
 * 项目名称: QYH 类名称: DateTimeSelectorDialogBuilder 创建人: xhl 创建时间: 2014-12-24
 * 下午2:15:14 版本: v1.0 类描述: 选择生日日期的Dialog
 */
public class DateTimeYMDSelectorDialogBuilder extends NiftyDialogBuilder implements
        View.OnClickListener {

    private Context context;
    private RelativeLayout rlCustomLayout;
    private DateSelectorWheelViewYMD dateWheelView;
    private FrameLayout flSecondeCustomLayout;
    private OnSaveListener saveListener;
    /**
     * 默认方向标示
     */
    private static int mOrientation = 1;
    private static DateTimeYMDSelectorDialogBuilder instance;

    public interface OnSaveListener {
        abstract void onSaveSelectedDate(String selectedDate);
    }

    public DateTimeYMDSelectorDialogBuilder(Context context) {
        super(context);
        this.context = context;
        initDialog();
    }

    public DateTimeYMDSelectorDialogBuilder(Context context, int theme) {
        super(context, theme);
        this.context = context;
        initDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(ResourceUtils.getResourceIdByName(context, "drawable", "customui_datepick_edit_dialog_coner"));
    }

    public static DateTimeYMDSelectorDialogBuilder getInstance(Context context) {

        int ort = context.getResources().getConfiguration().orientation;
        if (mOrientation != ort) {
            mOrientation = ort;
            instance = null;
        }

        if (instance == null || ((Activity) context).isFinishing()) {
            synchronized (DateTimeYMDSelectorDialogBuilder.class) {
                if (instance == null) {
                    instance = new DateTimeYMDSelectorDialogBuilder(context, ResourceUtils.getResourceIdByName(context, "style", "customui_datepick_dialog_untran"));
                }
            }
        }
        return instance;

    }

    private void initDialog() {
        rlCustomLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
                ResourceUtils.getResourceIdByName(context, "layout", "customui_datepick_ymd_selector_dialog_layout"), null);
        dateWheelView = (DateSelectorWheelViewYMD) rlCustomLayout
                .findViewById(ResourceUtils.getResourceIdByName(context, "id", "pdwv_date_time_selector_wheelView"));
        dateWheelView.setTitleClick(this);
        flSecondeCustomLayout = (FrameLayout) rlCustomLayout
                .findViewById(ResourceUtils.getResourceIdByName(context, "id", "fl_date_time_custom_layout"));
        setDialogProperties();
    }

    private void setDialogProperties() {
        int width = ResourceUtils.getScreenWidth(context) * 3 / 4;
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
    public DateTimeYMDSelectorDialogBuilder setSencondeCustomView(View view,
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
    public DateTimeYMDSelectorDialogBuilder setSencondeCustomView(int resId,
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
                saveListener.onSaveSelectedDate(dateWheelView.getSelectedDate());
            }
            dismiss();
        }
    }

    /**
     * 获取日期选择器
     *
     * @return
     */
    public DateSelectorWheelViewYMD getDateWheelView() {
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

}
