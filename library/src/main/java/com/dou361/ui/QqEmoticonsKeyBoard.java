package com.dou361.ui;

import android.content.Context;
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

import java.util.ArrayList;

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
 * 创建日期：2016/5/3 9:55
 * <p/>
 * 描 述：仿qq面板
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class QqEmoticonsKeyBoard extends AutoHeightLayout implements EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener, View.OnClickListener {

    public final int APPS_HEIGHT = 256;

    public static final int FUNC_TYPE_PTT = 1;
    public static final int FUNC_TYPE_PTV = 2;
    public static final int FUNC_TYPE_IMAGE = 3;
    public static final int FUNC_TYPE_CAMERA = 4;
    public static final int FUNC_TYPE_HONGBAO = 5;
    public static final int FUNC_TYPE_EMOTICON = 6;
    public static final int FUNC_TYPE_PLUG = 7;

    protected LayoutInflater mInflater;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;

    protected boolean mDispatchKeyEventPreImeLock = false;

    EmoticonsEditText etChat;
    Button btnSend;
    ImageView btnVoice;
    ImageView btnPtv;
    ImageView btnImage;
    ImageView btnCamera;
    ImageView btnHongbao;
    ImageView btnEmoticon;
    ImageView btnPlug;
    FuncLayout lyKvml;

    public QqEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_keyboard_qq"), this);
        initView();
        initFuncView();
    }

    protected void initView() {
        etChat = (EmoticonsEditText) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "et_chat"));
        btnSend = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_send"));
        btnVoice = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice"));
        btnPtv = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_ptv"));
        btnImage = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_image"));
        btnCamera = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_camera"));
        btnHongbao = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_hongbao"));
        btnEmoticon = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_emoticon"));
        btnPlug = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_plug"));
        lyKvml = (FuncLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_kvml"));
        etChat.setOnBackKeyClickListener(this);
        btnVoice.setOnClickListener(this);
        btnPtv.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnHongbao.setOnClickListener(this);
        btnEmoticon.setOnClickListener(this);
        btnPlug.setOnClickListener(this);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        View keyboardView = inflateFunc();
        lyKvml.addFuncView(FUNC_TYPE_EMOTICON, keyboardView);
        mEmoticonsFuncView = ((EmoticonsFuncView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_epv")));
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_eiv")));
        mEmoticonsToolBarView = ((EmoticonsToolBarView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "view_etv")));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        lyKvml.setOnFuncChangeListener(this);
    }

    protected View inflateFunc() {
        return mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_func_emoticon"), null);
    }

    protected void initEditView() {
        etChat.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!etChat.isFocused()) {
                    etChat.setFocusable(true);
                    etChat.setFocusableInTouchMode(true);
                }
                return false;
            }
        });

        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg"));
                } else {
                    btnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg_disable"));
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

    public void addFuncView(int type, View view) {
        lyKvml.addFuncView(type, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
        lyKvml.hideAllFuncView();
        resetIcon();
    }

    public void resetIcon() {
        btnVoice.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_ptt"));
        btnPtv.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_ptv"));
        btnImage.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_image"));
        btnCamera.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_camera"));
        btnHongbao.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_hongbao"));
        btnEmoticon.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_emotion"));
        btnPlug.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_plus"));
    }

    protected void toggleFuncView(int key) {
        lyKvml.toggleFuncView(key, isSoftKeyboardPop(), etChat);
    }

    @Override
    public void onFuncChange(int key) {
        resetIcon();
        switch (key) {
            case FUNC_TYPE_PTT:
                btnVoice.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_ptt_press"));
                break;
            case FUNC_TYPE_PTV:
                btnPtv.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_ptv_press"));
                break;
            case FUNC_TYPE_IMAGE:
                btnImage.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_image_press"));
                break;
            case FUNC_TYPE_CAMERA:
                btnCamera.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_camera_press"));
                break;
            case FUNC_TYPE_HONGBAO:
                btnHongbao.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_hongbao_press"));
                break;
            case FUNC_TYPE_EMOTICON:
                btnEmoticon.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_emotion_press"));
                break;
            case FUNC_TYPE_PLUG:
                btnPlug.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_plus_press"));
                break;
        }
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyKvml.getLayoutParams();
        params.height = height;
        lyKvml.setLayoutParams(params);
        super.OnSoftPop(height);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        lyKvml.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        lyKvml.setVisibility(true);
        onFuncChange(lyKvml.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (lyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(lyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        lyKvml.addOnKeyBoardListener(l);
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
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override
    public void onBackKeyClick() {
        if (lyKvml.isShown()) {
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
                if (lyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    public EmoticonsEditText getEtChat() {
        return etChat;
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


    public Button getBtnSend() {
        return btnSend;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice")) {
            toggleFuncView(FUNC_TYPE_PTT);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_ptv")) {
            toggleFuncView(FUNC_TYPE_PTV);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_image")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_camera")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_hongbao")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_emoticon")) {
            toggleFuncView(FUNC_TYPE_EMOTICON);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_plug")) {
            toggleFuncView(FUNC_TYPE_PLUG);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
    }
}
