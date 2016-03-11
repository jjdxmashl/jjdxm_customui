package com.dou361.ui;

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
 * 版 权：深圳市晶网电子科技有限公司 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2015/11/11
 * <p/>
 * 描 述：自定义Dialog
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
