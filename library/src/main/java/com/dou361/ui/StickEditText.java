package com.dou361.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dou361.utils.ResourceUtils;

import java.util.Timer;
import java.util.TimerTask;

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
 * 创建日期：2016/3/15 22:04
 * <p/>
 * 描 述：编辑框，编辑文本使用
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class StickEditText extends PopupWindow {


    private EditText et_content;
    private ImageView iv_opereator;
    private View mMenuView;
    private OnSendListener ff;
    private OnDismissListener dl;

    /**
     * 点击软件盘完成按钮或者发送按钮执行发送
     */
    public void setOnSendListener(OnSendListener l, String strDes) {
        ff = l;
        if (strDes != null) {
            et_content.setText(strDes);
        }
    }

    /**
     * 关闭窗口监听
     */
    public void setOnDismissListener(OnDismissListener l) {
        dl = l;
    }

    public StickEditText(final Activity mContext) {
        super(mContext);
        mMenuView = LayoutInflater.from(mContext).inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_popu_stickedittext"), null);
        et_content = (EditText) mMenuView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_et_content"));
        iv_opereator = (ImageView) mMenuView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_iv_opereator"));
        // 取消按钮
        iv_opereator.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ff.updateData(et_content.getText().toString()
                        .trim());
            }
        });
        et_content
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
                                || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                                ) {
                            ff.updateData(et_content.getText().toString()
                                    .trim());
                            return true;
                        }
                        return false;
                    }

                });
        et_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (et_content.length() > 0) {
                        et_content.setSelection(et_content.length());
                    }
                }
            }
        });
        /** 拦截输入法的表情 */
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = et_content.getSelectionStart() - 1;
                if (index >= 0) {
                    if (isEmojiCharacter(s.charAt(index))) {
                        Editable edit = et_content.getText();
                        edit.delete(index, index + 1);
                    }
                }
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //防止虚拟软键盘被弹出菜单遮住
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setAnimationStyle(ResourceUtils.getResourceIdByName(mContext,"style","Animations_GrowFromBottom"));

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "customui_pop_layout")).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

        /** 调起输入法 */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) et_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(et_content, 0);
                           }

                       },
                300);

    }

    /**
     * 判断是否是输入法的表情
     */
    private boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dl != null && et_content != null) {
            dl.updateData(et_content.getText().toString()
                    .trim());
        }
    }

    public void setMaxLength(int maxlength) {
        et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
    }

    public interface OnSendListener {
        void updateData(String trim);
    }

    public interface OnDismissListener {
        void updateData(String trim);
    }

}
