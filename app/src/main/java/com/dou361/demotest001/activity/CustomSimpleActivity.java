package com.dou361.demotest001.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dou361.bean.EmojiBean;
import com.dou361.bean.EmoticonEntity;
import com.dou361.bean.ImMsgBean;
import com.dou361.demotest001.R;
import com.dou361.listener.EmoticonClickListener;
import com.dou361.ui.CustomAppsGridView;
import com.dou361.ui.CustomSimpleEmoticonsKeyBoard;
import com.dou361.ui.EmoticonsEditText;
import com.dou361.ui.FuncLayout;
import com.dou361.utils.KeyBoardParams;
import com.dou361.utils.SimpleCommonUtils;

import java.util.ArrayList;
import java.util.List;

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
 * 创建日期：2016/5/18 11:12
 * <p/>
 * 描 述：自定义的简单emoji表情面板
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class CustomSimpleActivity extends Activity implements FuncLayout.OnFuncKeyBoardListener, View.OnClickListener {


    //    ListView lvChat;
    CustomSimpleEmoticonsKeyBoard ekBar;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customsimple);
        initView();
    }

    private void initView() {
//        lvChat = (ListView) findViewById(R.id.lv_chat);
        ekBar = (CustomSimpleEmoticonsKeyBoard) findViewById(R.id.ek_bar);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        initEmoticonsKeyBoardBar();
//        initListView();
    }

    private void initEmoticonsKeyBoardBar() {
        SimpleCommonUtils.initCustomEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCustomSimpleAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(new CustomAppsGridView(this));

        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
        ekBar.requestFocus();
    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == KeyBoardParams.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
                        OnSendImage(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    Log.e("dou361", "-----content--------" + content);
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    private void initListView() {
        List<ImMsgBean> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ImMsgBean bean = new ImMsgBean();
            bean.setContent("Test:" + i);
            beanList.add(bean);
        }
//        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case SCROLL_STATE_FLING:
//                        break;
//                    case SCROLL_STATE_IDLE:
//                        break;
//                    case SCROLL_STATE_TOUCH_SCROLL:
//                        ekBar.reset();
//                        break;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            }
//        });
    }

    private void OnSendBtnClick(String msg) {

        Log.e("dou361", "-----msg--------" + msg);
        if (!TextUtils.isEmpty(msg)) {
            ImMsgBean bean = new ImMsgBean();
            bean.setContent(msg);
            scrollToBottom();
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
//        lvChat.requestLayout();
//        lvChat.post(new Runnable() {
//            @Override
//            public void run() {
//                lvChat.setSelection(lvChat.getBottom());
//            }
//        });
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        ekBar.showUI();
    }
}
