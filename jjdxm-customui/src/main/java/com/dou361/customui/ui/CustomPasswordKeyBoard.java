package com.dou361.customui.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ViewFlipper;

import com.dou361.customui.utils.ResourceUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
 * 创建日期：2016/3/15 22:07
 * <p/>
 * 描 述：自定义密码键盘
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class CustomPasswordKeyBoard extends View implements OnClickListener {

    Context mContext;
    Activity activity;

    private LayoutInflater layoutInflater;
    private ViewFlipper viewFilpper;
    public PopupWindow popup;
    private View keyboardsView;
    private View popView;
    private LinearLayout container;
    private int keyboardIndex = 0;
    private Button btn_number, btn_character, btn_symbol, btn_digital,
            btn_clear, btn_shift, btn_keyboard_ok;
    public boolean isCapital = false, isShowing = false;

    public SharedPreferences mSettings;
    public ArrayList<HashMap<String, Object>> params = new ArrayList<HashMap<String, Object>>();
    EditText et_password;

    public CustomPasswordKeyBoard(Context context) {
        super(context);
        this.mContext = context;
        activity = (Activity) context;
    }

    public void hideKeyboard() {
        final View v0 = activity.getWindow().peekDecorView();
        if (v0 != null && v0.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
        }
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void showKeyboard(EditText et_password) {

        this.et_password = et_password;

        if (popup != null && popup.isShowing()) {
            return;
        }

        keyboardIndex = 0;
        layoutInflater = activity.getLayoutInflater();
        popView = layoutInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_keyboard_popwindow_keyboard"), null);
        if (popup == null)
            popup = new PopupWindow(popView, LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT, true);

        viewFilpper = (ViewFlipper) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "viewFlipper1"));
        viewFilpper.setInAnimation(AnimationUtils.loadAnimation(mContext, ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_animation_in_from_bottom")
        ));
        viewFilpper.setFlipInterval(600000);

        popup.setFocusable(true);
        popup.setWidth(LayoutParams.FILL_PARENT);
        popup.setHeight(LayoutParams.WRAP_CONTENT);
        popup.setBackgroundDrawable(new BitmapDrawable());
        if (!popup.isShowing())
            popup.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        viewFilpper.startFlipping();

        Button btn_finish = (Button) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_finish"));
        btn_finish.setOnClickListener(this);
        btn_number = (Button) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_number"));
        btn_number.setOnClickListener(this);
        btn_character = (Button) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_character"));
        btn_character.setOnClickListener(this);
        btn_symbol = (Button) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_symbol"));
        btn_symbol.setOnClickListener(this);

        btn_keyboard_ok = (Button) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_keyboard_ok"));
        btn_keyboard_ok.setOnClickListener(this);

        container = (LinearLayout) popView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "llkeyboard"));

        switchView(0);
    }

    /**
     * 生成随机的0-9 10个数字，且值各不相同
     */
    private int[] getRandomNum() {
        Random random = new Random();
        int[] data = new int[10];
        boolean b;
        boolean b2 = false;
        boolean b3 = true;
        int x;
        for (int i = 0; i < 10; i++) {
            b = true;
            while (b) {
                x = random.nextInt(10);
                if (x == 0 && b3) {
                    b3 = false;
                    b = false;
                }
                for (int y : data) {
                    if (x == y) {
                        b2 = false;
                        break;
                    } else {
                        b2 = true;
                    }
                }
                if (b2) {
                    data[i] = x;
                    b = false;
                    break;
                }
            }

        }
        return data;
    }

    public void switchView(int index) {
        container.removeAllViews();

        if (index == 0) {
            keyboardsView = activity.getLayoutInflater().inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_keyboard_digitals")
                    , null);
            int[] digital = getRandomNum();
            btn_clear = (Button) keyboardsView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_clear"));
            btn_clear.setOnClickListener(this);

            for (int i = 0; i < 10; i++) {
                String btnStr = MessageFormat.format("btn_digital{0}",
                        Integer.toString(i + 1));
                int id = mContext.getResources().getIdentifier(btnStr, "id",
                        mContext.getPackageName());
                Button btn_digital = (Button) keyboardsView.findViewById(id);
                btn_digital.setText(Integer.toString(digital[i]));
                btn_digital.setTag(digital[i]);
                btn_digital.setOnClickListener(this);
            }
            container.addView(keyboardsView);
        } else if (index == 1) {

            if (isCapital)
                keyboardsView = activity.getLayoutInflater().inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_keyboard_character_capital")
                        , null);
            else
                keyboardsView = activity.getLayoutInflater().inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_keyboard_character")
                        , null);
            btn_clear = (Button) keyboardsView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_clear"));
            btn_clear.setOnClickListener(this);
            btn_shift = (Button) keyboardsView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_shift"));
            btn_shift.setOnClickListener(this);
            for (int i = 0; i < 26; i++) {
                String btnStr = MessageFormat.format("btn_character{0}",
                        Integer.toString(i + 1));
                int id = mContext.getResources().getIdentifier(btnStr, "id",
                        mContext.getPackageName());
                Button btn_character = (Button) keyboardsView.findViewById(id);
                btn_character.setTag(0x41 + i);
                btn_character.setOnClickListener(this);
            }
            // initKeyBoard();
            container.addView(keyboardsView);
        } else {
            keyboardsView = activity.getLayoutInflater().inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_keyboard_symbol")
                    , null);
            btn_clear = (Button) keyboardsView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_clear"));
            btn_clear.setOnClickListener(this);
            for (int i = 0; i < 10; i++) {
                String btnStr = MessageFormat.format("btn_symbol{0}",
                        Integer.toString(i + 1));
                int id = mContext.getResources().getIdentifier(btnStr, "id",
                        mContext.getPackageName());
                Button btn_symbol = (Button) keyboardsView.findViewById(id);
                btn_symbol.setTag(i);
                btn_symbol.setOnClickListener(this);
            }
            container.addView(keyboardsView);
        }
    }

    private void clearViews() {
        keyboardsView = null;
        if (container != null) {
            container.removeAllViews();
        }
        container = null;
        btn_digital = null;
        btn_character = null;
        btn_symbol = null;
        btn_clear = null;
        btn_shift = null;

        viewFilpper = null;
        popView = null;

        if (popup != null && popup.isShowing())
            popup.dismiss();
        popup = null;
        System.gc();
    }

    public void AddHashMap(String key, String value) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("value", value);
        params.add(map);
    }

    @Override
    public void onClick(View v) {
        final View v0 = activity.getWindow().peekDecorView();
        if (v0 != null && v0.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
        }

        if (v.getId() == btn_keyboard_ok.getId()) {
            // ((MainActivity) activity).doLogin();
            popup.dismiss();

        } else if (v.getId() == btn_number.getId()) {
            // switch to digital
            btn_character.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_character.setTextColor(Color.BLACK);
            btn_symbol.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_symbol.setTextColor(Color.BLACK);
            if (keyboardIndex != 0) {
                keyboardIndex = 0;
                switchView(keyboardIndex);
                v.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_keyboard_number"));
                ((Button) v).setTextColor(Color.WHITE);
            }
        } else if (v.getId() == btn_character.getId()) {
            // switch to digital
            btn_number.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_symbol.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_number.setTextColor(Color.BLACK);
            btn_symbol.setTextColor(Color.BLACK);
            if (keyboardIndex != 1) {
                keyboardIndex = 1;
                switchView(keyboardIndex);
                v.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_keyboard_character"));
                ((Button) v).setTextColor(Color.WHITE);
            }
        } else if (v.getId() == btn_symbol.getId()) {
            // switch to digital
            btn_number.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_character.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_key_btn_normal"));
            btn_number.setTextColor(Color.BLACK);
            btn_character.setTextColor(Color.BLACK);
            if (keyboardIndex != 2) {
                keyboardIndex = 2;
                switchView(keyboardIndex);
                v.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_keyboard_keyboard_symbol"));
                ((Button) v).setTextColor(Color.WHITE);
            }
        } else if (v.getId() == btn_clear.getId()) {

            et_password.setText("");

        } else if (btn_shift!=null&&v.getId() == btn_shift.getId()) {
            if (isCapital == true) {
                isCapital = false;
            } else {
                isCapital = true;
            }
            switchView(1);
        } else {
            String str = "";
            int index = et_password.getSelectionEnd();
            if (keyboardIndex == 0 || keyboardIndex == 2) {
                str = ((Button) v).getText().toString().replaceAll(" ", "");
            } else {
                if (isCapital) {
                    str = ((Button) v).getText().toString().toUpperCase();
                } else {
                    str = ((Button) v).getText().toString();
                }
            }

            StringBuffer strNew = new StringBuffer(et_password.getText()
                    .toString());
            str = strNew.insert(index, str).toString();

            index++;

            et_password.setText(str);
            et_password.setSelection(index);
        }
    }


    public boolean onTouch(View v, MotionEvent event) {
        final View v0 = ((Activity)mContext).getWindow().peekDecorView();
        if (v0 != null && v0.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
        }
        ((Activity)mContext).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (event.getAction() == MotionEvent.ACTION_UP) {

            v.setFocusableInTouchMode(true);
            v.setFocusable(true);
            v.requestFocus();

            if (popup == null
                    || popup.isShowing() == false
                    || isShowing == false) {

                clearViews();

                isCapital = false;
                isShowing = true;

                if(v instanceof EditText ){
                    EditText et = (EditText)v;
                    int oldType = et.getInputType();
                    /** 拦截系统输入法 */
                    et.setInputType(InputType.TYPE_NULL);
                    showKeyboard(et);
                    v.onTouchEvent(event);
                    /** 重新设置焦点 */
                    et.setInputType(oldType);
                    String str = et.getText().toString();
                    et.setSelection(str.length());
                }


                return true;
            } else {
                return true;
            }
        }
        return false;
    }
}
