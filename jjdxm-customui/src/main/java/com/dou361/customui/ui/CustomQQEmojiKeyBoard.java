package com.dou361.customui.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dou361.customui.utils.EmoticonsKeyboardUtils;
import com.dou361.customui.utils.ResourceUtils;

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
 * 创建日期：2016/6/22 11:46
 * <p/>
 * 描 述：自定义表情键盘
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class CustomQQEmojiKeyBoard extends CustomEmojiKeyBoard {


    ImageView mBtnVoice;
    ImageView mBtnPtv;
    ImageView mBtnImage;
    ImageView mBtnCamera;
    ImageView mBtnHongbao;
    ImageView mBtnEmoticon;
    ImageView mBtnPlug;

    public CustomQQEmojiKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void inflateKeyboardBar() {
        mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_keyboard_qq"), this);
    }

    @Override
    protected View inflateFunc() {
        return mInflater.inflate(ResourceUtils.getResourceIdByName(mContext, "layout", "customui_view_func_emoticon"), null);
    }

    @Override
    protected void initView() {
        mEtChat = (EmoticonsEditText) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "et_chat"));
        mBtnSend = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_send"));
        mBtnVoice = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice"));
        mBtnPtv = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_ptv"));
        mBtnImage = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_image"));
        mBtnCamera = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_camera"));
        mBtnHongbao = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_hongbao"));
        mBtnEmoticon = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_emoticon"));
        mBtnPlug = (ImageView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_plug"));
        mLyKvml = (FuncLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ly_kvml"));
        mEtChat.setOnBackKeyClickListener(this);
        mBtnVoice.setOnClickListener(this);
        mBtnPtv.setOnClickListener(this);
        mBtnImage.setOnClickListener(this);
        mBtnCamera.setOnClickListener(this);
        mBtnHongbao.setOnClickListener(this);
        mBtnEmoticon.setOnClickListener(this);
        mBtnPlug.setOnClickListener(this);
    }

    @Override
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
                    mBtnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg"));
                } else {
                    mBtnSend.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_btn_send_bg_disable"));
                }
            }
        });
    }

    @Override
    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
        mLyKvml.hideAllFuncView();
        resetIcon();
    }

    @Override
    protected void showVoice() {

    }

    @Override
    protected void showText() {
    }

    @Override
    protected void checkVoice() {

    }

    public void resetIcon() {
        mBtnVoice.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_ptt"));
        mBtnPtv.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_ptv"));
        mBtnImage.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_image"));
        mBtnCamera.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_camera"));
        mBtnHongbao.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_hongbao"));
        mBtnEmoticon.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_emotion"));
        mBtnPlug.setImageResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "customui_qq_skin_aio_panel_plus"));
    }

    @Override
    public void onFuncChange(int key) {
        resetIcon();
        switch (key) {
            case FUNC_TYPE_PTT:
                mBtnVoice.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_ptt_press"));
                break;
            case FUNC_TYPE_PTV:
                mBtnPtv.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_ptv_press"));
                break;
            case FUNC_TYPE_IMAGE:
                mBtnImage.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_image_press"));
                break;
            case FUNC_TYPE_CAMERA:
                mBtnCamera.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_camera_press"));
                break;
            case FUNC_TYPE_HONGBAO:
                mBtnHongbao.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_hongbao_press"));
                break;
            case FUNC_TYPE_EMOTION:
                mBtnEmoticon.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_emotion_press"));
                break;
            case FUNC_TYPE_PLUG:
                mBtnPlug.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "customui_qq_skin_aio_panel_plus_press"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_voice")) {
            toggleFuncView(FUNC_TYPE_PTT);
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_ptv")) {
            toggleFuncView(FUNC_TYPE_PTV);
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_image")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_camera")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_hongbao")) {
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_emoticon")) {
            toggleFuncView(FUNC_TYPE_EMOTION);
        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "btn_plug")) {
            toggleFuncView(FUNC_TYPE_PLUG);
        }
    }

}
