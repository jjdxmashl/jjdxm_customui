package com.dou361.demotest001.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dou361.demotest001.R;
import com.dou361.ui.CustomQQEmojiKeyBoard;
import com.dou361.ui.CustomSimpleHideEmojiKeyBoard;
import com.dou361.ui.FuncLayout;
import com.dou361.ui.SimpleQqGridView;
import com.dou361.utils.SimpleCommonUtils;

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

    CustomSimpleHideEmojiKeyBoard ekBar;
    private Context mContext;
    private Button btn_ok;
    private ScrollView sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customsimple);
        mContext = this;
        initView();
    }

    private void initView() {
        ekBar = (CustomSimpleHideEmojiKeyBoard) findViewById(R.id.ek_bar);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        sl = (ScrollView) findViewById(R.id.sl);
        btn_ok.setOnClickListener(this);
        initSimpleEmojiKeyBoardBar();
        sl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ekBar.hideUI();
                return false;
            }
        });
    }

    private void initQQEmojiKeyBoardBar() {
        SimpleCommonUtils.initQqEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getQqAdapter(this, SimpleCommonUtils.getCommonEmoticonClickListener(ekBar.getEtChat())));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(CustomQQEmojiKeyBoard.FUNC_TYPE_PLUG, new SimpleQqGridView(this));
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
        ekBar.getEmoticonsToolBarView().addFixedToolItemView(true, R.drawable.customui_icon_add_nomal, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "ADD", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initSimpleEmojiKeyBoardBar() {
        SimpleCommonUtils.initCustomEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCustomSimpleAdapter(this, SimpleCommonUtils.getCommonEmoticonClickListener(ekBar.getEtChat())));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
    }


    private void initEmojiKeyBoardBar() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCustomAdapter(this, SimpleCommonUtils.getCommonEmoticonClickListener(ekBar.getEtChat())));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
        ekBar.getEmoticonsToolBarView().addToolItemView(R.drawable.customui_icon_add_nomal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "TEST", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void OnSendBtnClick(String msg) {

    }


    @Override
    public void OnFuncPop(int height) {

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
    public void onClick(View v) {
        ekBar.showUI();
    }
}
