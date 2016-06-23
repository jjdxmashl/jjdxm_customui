package com.dou361.customui.ui;

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
import android.widget.RelativeLayout;

import com.dou361.customui.adapter.PageSetAdapter;
import com.dou361.customui.bean.PageSetEntity;
import com.dou361.customui.utils.EmoticonsKeyboardUtils;
import com.dou361.customui.utils.ResourceUtils;

import java.util.ArrayList;

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
 * 创建日期：2016/6/22 11:46
 * <p>
 * 描 述：自定义表情键盘，子view只能是一个或零个
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class CustomEmojiKeyBoard extends AutoHeightLayout implements View.OnClickListener, EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener {

    public static final int FUNC_TYPE_EMOTION = -1;
    public static final int FUNC_TYPE_APPPS = -2;
    public static final int FUNC_TYPE_PTT = 1;
    public static final int FUNC_TYPE_PTV = 2;
    public static final int FUNC_TYPE_IMAGE = 3;
    public static final int FUNC_TYPE_CAMERA = 4;
    public static final int FUNC_TYPE_HONGBAO = 5;
    public static final int FUNC_TYPE_PLUG = 6;

    protected LayoutInflater mInflater;

    protected ImageView mBtnVoiceOrText;
    protected Button mBtnVoice;
    protected EmoticonsEditText mEtChat;
    protected ImageView mBtnFace;
    protected RelativeLayout mRlInput;
    protected ImageView mBtnMultimedia;
    protected Button mBtnSend;
    protected FuncLayout mLyKvml;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    /**物理按键返回键监听*/
    protected boolean mDispatchKeyEventPreImeLock = false;

    public CustomEmojiKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        inflateKeyboardBar();
        initView();
        initFuncView();
    }


    protected void inflateKeyboardBar() {
        mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_keyboard_custom"), this);
    }

    protected View inflateFunc() {
        return mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_func_emoticon"), null);
    }

    protected void initView() {
        mBtnVoiceOrText = ((ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice_or_text")));
        mBtnVoice = ((Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice")));
        mEtChat = ((EmoticonsEditText) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "et_chat")));
        mBtnFace = ((ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_face")));
        mRlInput = ((RelativeLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "rl_input")));
        mBtnMultimedia = ((ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_multimedia")));
        mBtnSend = ((Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_send")));
        mLyKvml = ((FuncLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_kvml")));

        mBtnVoiceOrText.setOnClickListener(this);
        mBtnFace.setOnClickListener(this);
        mBtnMultimedia.setOnClickListener(this);
        mEtChat.setOnBackKeyClickListener(this);
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
        mEmoticonsToolBarView = ((EmoticonsToolBarView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_etv")));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
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
                    mBtnSend.setVisibility(VISIBLE);
                    mBtnMultimedia.setVisibility(GONE);
                    mBtnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg"));
                } else {
                    mBtnMultimedia.setVisibility(VISIBLE);
                    mBtnSend.setVisibility(GONE);
                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        mLyKvml.addFuncView(FUNC_TYPE_APPPS, view);
    }


    public void addFuncView(int type, View view) {
        mLyKvml.addFuncView(type, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(this);
        mLyKvml.hideAllFuncView();
        mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_icon_face_nomal"));
    }

    protected void showVoice() {
        mRlInput.setVisibility(GONE);
        mBtnVoice.setVisibility(VISIBLE);
        reset();
    }

    protected void checkVoice() {
        if (mBtnVoice.isShown()) {
            mBtnVoiceOrText.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text_keyboard"));
        } else {
            mBtnVoiceOrText.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text"));
        }
    }

    protected void showText() {
        mRlInput.setVisibility(VISIBLE);
        mBtnVoice.setVisibility(GONE);
    }

    protected void toggleFuncView(int key) {
        showText();
        mLyKvml.toggleFuncView(key, isSoftKeyboardPop(), mEtChat);
    }

    @Override
    public void onFuncChange(int key) {
        if (FUNC_TYPE_EMOTION == key) {
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_icon_face_pop"));
        } else {
            mBtnFace.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_icon_face_nomal"));
        }
        checkVoice();
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
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
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
        if (i == ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice_or_text")) {
            if (mRlInput.isShown()) {
                mBtnVoiceOrText.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text_keyboard"));
                showVoice();
            } else {
                showText();
                mBtnVoiceOrText.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_voice_or_text"));
                EmoticonsKeyboardUtils.openSoftKeyboard(mEtChat);
            }
        } else if (i == ResourceUtils.getResourceIdByName(mContext, "id", "btn_face")) {
            toggleFuncView(FUNC_TYPE_EMOTION);
        } else if (i == ResourceUtils.getResourceIdByName(mContext, "id", "btn_multimedia")) {
            toggleFuncView(FUNC_TYPE_APPPS);
        }
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
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

    public Button getBtnVoice() {
        return mBtnVoice;
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

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return mEmoticonsToolBarView;
    }
}
