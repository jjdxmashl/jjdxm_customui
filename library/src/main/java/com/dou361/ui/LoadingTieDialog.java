package com.dou361.ui;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dou361.utils.ResourceUtils;

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
 * 创建日期：2015/11/11
 * <p>
 * 描 述：加载进度提示
 * 使用：
 * 显示进度条：LoadingTieDialog.showTie((Activity)mContext);
 * 关闭进度条：LoadingTieDialog.dismssTie((Activity)mContext);
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class LoadingTieDialog {

    private Activity mContext;
    private View viewItem;
    private Dialog mDialog;
    private static LoadingTieDialog mLoadingTieDialog;

    public static void showTie(Activity mContext) {
        if (mLoadingTieDialog != null) {
            mLoadingTieDialog.dismss();
        }
        mLoadingTieDialog = new LoadingTieDialog(mContext);
        mLoadingTieDialog.show();
    }


    public static void dismssTie(Activity mContext) {
        if (mLoadingTieDialog != null) {
            mLoadingTieDialog.dismss();
        }
    }


    public LoadingTieDialog(Activity mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        viewItem = inflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_progressbar_loading"), null);
        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewItem);
        mDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(params);
    }

    private void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void dismss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
