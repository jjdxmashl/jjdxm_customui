package com.dou361.customui.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/3/15 21:57
 * <p/>
 * 描 述：自定义Dialog
 * 使用：
 * myDialog = new MyDialog(UIUtils.getActivity());
 * myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 * myDialog.setContentView(R.layout.progressbar_loading);
 * myDialog.setCanceledOnTouchOutside(false);
 * myDialog.show();
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class MyDialog extends Dialog implements
        View.OnClickListener {

    Context context;

    public MyDialog(Context context) {
        super(context);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    public MyDialog(Context context, boolean cancelable,
                    OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 350;
        params.height = 200;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
    }

}
