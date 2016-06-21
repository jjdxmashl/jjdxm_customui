package com.dou361.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dou361.adapter.PageSetAdapter;
import com.dou361.bean.PageSetEntity;
import com.dou361.utils.EmoticonsKeyboardUtils;
import com.dou361.utils.ResourceUtils;

public class CustomSimpleEmoticonsKeyBoard extends AutoHeightLayout implements View.OnClickListener, EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener {

    public static final int FUNC_TYPE_EMOTION = -1;
    public static final int FUNC_TYPE_APPPS = -2;

    protected LayoutInflater mInflater;

    protected EmoticonsEditText mEtChat;
    protected ImageView mBtnFace;
    protected Button mBtnSend;
    protected FuncLayout mLyKvml;
    protected View vLine;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;

    protected boolean mDispatchKeyEventPreImeLock = false;
    private LinearLayout ll_keyboard;
    private boolean isReset = true;

    public CustomSimpleEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    protected void inflateKeyboardBar() {
        mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_keyboard_custom_simple"), this);
    }

    protected View inflateFunc() {
        return mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_func_emoticon_simple"), null);
    }

    protected void initView() {
        ll_keyboard = ((LinearLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ll_keyboard")));
        mEtChat = ((EmoticonsEditText) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "et_chat")));
        mBtnFace = ((ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_face")));
        mBtnSend = ((Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_send")));
        mLyKvml = ((FuncLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_kvml")));
        vLine = findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "v_line"));
        mBtnFace.setOnClickListener(this);
        mEtChat.setOnBackKeyClickListener(this);
    }

    public void setKeyBarVisibility(boolean flag) {
        if (ll_keyboard != null) {
            ll_keyboard.setVisibility(flag ? VISIBLE : GONE);
        }
        if (!flag) {
            reset();
        }
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        View keyboardView = inflateFunc();
        mLyKvml.addFuncView(FUNC_TYPE_EMOTION, keyboardView);
        mEmoticonsFuncView = ((EmoticonsFuncView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_epv")));
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_eiv")));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mLyKvml.setOnFuncChangeListener(this);
    }

    protected void initEditView() {
        mEtChat.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mEtChat.isFocused()) {
                    mEtChat.setFocusable(true);
                    mEtChat.setFocusableInTouchMode(true);
                }
                if (vLine.isShown()) {
                    vLine.setVisibility(VISIBLE);
                    if (isReset) {
                        toggleFuncView(FUNC_TYPE_EMOTION);
                        isReset = false;
                    }
                }
                return false;
            }
        });

        mEtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mBtnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg"));
                } else {
                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        mLyKvml.addFuncView(FUNC_TYPE_APPPS, view);
    }

    public void reset() {
        isReset = true;
        EmoticonsKeyboardUtils.closeSoftKeyboard(this);
        mLyKvml.hideAllFuncView();
        vLine.setVisibility(VISIBLE);
        mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_emoji_or_text"));
    }

    protected void toggleFuncView(int key) {
        mLyKvml.toggleFuncView(key, isSoftKeyboardPop(), mEtChat);
    }

    @Override
    public void onFuncChange(int key) {
        if (FUNC_TYPE_EMOTION == key) {
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text_keyboard"));
        } else {
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_emoji_or_text"));
        }
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLyKvml.getLayoutParams();
        params.height = height;
        mLyKvml.setLayoutParams(params);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        mLyKvml.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        mLyKvml.setVisibility(true);
        onFuncChange(mLyKvml.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(mLyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        mLyKvml.addOnKeyBoardListener(l);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == ResourceUtils.getResourceIdByName(mContext, "id", "btn_face")) {
            togglePanl();
        }
    }

    /**
     * 点击开关
     */
    private void togglePanl() {
        if (vLine.isShown()) {
            /**表情*/
            vLine.setVisibility(GONE);
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text_keyboard"));
        } else {
            /**软键盘*/
            vLine.setVisibility(VISIBLE);
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_emoji_or_text"));
        }
        toggleFuncView(FUNC_TYPE_EMOTION);
        isReset = false;
    }

    /**
     * 显示面板
     */
    public void showUI() {
        if (vLine == null) {
            return;
        }
        togglePanl();
    }

    /**
     * 判断是否显示面板
     */
    public boolean isPanlShow() {
        return (mLyKvml != null && mLyKvml.isShown());
    }

    @Override
    public void onBackKeyClick() {
        if (mLyKvml.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (mLyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext()) && mLyKvml.isShown()) {
                    reset();
                    return true;
                }
            default:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    boolean isFocused;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        isFocused = mEtChat.getShowSoftInputOnFocus();
                    } else {
                        isFocused = mEtChat.isFocused();
                    }
                    if (isFocused) {
                        mEtChat.onKeyDown(event.getKeyCode(), event);
                    }
                }
                return false;
        }
    }

    public EmoticonsEditText getEtChat() {
        return mEtChat;
    }

    public Button getBtnSend() {
        return mBtnSend;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return mEmoticonsIndicatorView;
    }
}
