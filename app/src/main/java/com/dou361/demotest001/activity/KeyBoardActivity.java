package com.dou361.demotest001.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.dou361.demotest001.R;
import com.dou361.ui.CustomPasswordKeyBoard;

public class KeyBoardActivity extends AppCompatActivity implements View.OnTouchListener {

    private Context mContext;
    private EditText et_system;
    private EditText et_custom;
    private CustomPasswordKeyBoard customKeyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);
        mContext = this;
        et_system = (EditText) findViewById(R.id.et_system);
        et_custom = (EditText) findViewById(R.id.et_custom);
        et_custom.setOnTouchListener(this);
        customKeyBoard = new CustomPasswordKeyBoard(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return customKeyBoard.onTouch(v, event);
    }

}
