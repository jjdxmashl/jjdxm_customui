package com.dou361.jjdxm_customui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dou361.jjdxm_customui.activity.CustomSimpleActivity;
import com.dou361.jjdxm_customui.activity.DatePickActivity;
import com.dou361.jjdxm_customui.activity.HorizontalListViewActivity;
import com.dou361.jjdxm_customui.activity.KeyBoardActivity;
import com.dou361.jjdxm_customui.activity.PageTabActivity;
import com.dou361.jjdxm_customui.activity.PhotoActivity;
import com.dou361.jjdxm_customui.activity.PulltoRefreshActivity;
import com.dou361.jjdxm_customui.activity.StickEditTextActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout main;
    private Button btn_emoji;
    private Button btn_alertview;
    private Button btn_childviewpager;
    private Button btn_customkeyboard;
    private Button btn_datetimeymdselectordialogbuilder;
    private Button btn_horizontallistview;
    private Button btn_indicatorview;
    private Button btn_loadingpage;
    private Button btn_loadingtiedialog;
    private Button btn_mydialog;
    private Button btn_mygridview;
    private Button btn_mylistview;
    private Button btn_pagertab;
    private Button btn_pinnedheaderlistview;
    private Button btn_popuwindowview;
    private Button btn_pulldoorview;
    private Button btn_pulltorefreshview;
    private Button btn_ratiolayout;
    private Button btn_roundimageview;
    private Button btn_selectpicpopupwindow;
    private Button btn_stickedittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_emoji = (Button) findViewById(R.id.btn_emoji);
        btn_alertview = (Button) findViewById(R.id.btn_alertview);
        btn_childviewpager = (Button) findViewById(R.id.btn_childviewpager);
        btn_customkeyboard = (Button) findViewById(R.id.btn_customkeyboard);
        btn_datetimeymdselectordialogbuilder = (Button) findViewById(R.id.btn_datetimeymdselectordialogbuilder);
        btn_horizontallistview = (Button) findViewById(R.id.btn_horizontallistview);
        btn_indicatorview = (Button) findViewById(R.id.btn_indicatorview);
        btn_loadingpage = (Button) findViewById(R.id.btn_loadingpage);
        btn_loadingtiedialog = (Button) findViewById(R.id.btn_loadingtiedialog);
        btn_mydialog = (Button) findViewById(R.id.btn_mydialog);
        btn_mygridview = (Button) findViewById(R.id.btn_mygridview);
        btn_mylistview = (Button) findViewById(R.id.btn_mylistview);
        btn_pagertab = (Button) findViewById(R.id.btn_pagertab);
        btn_pinnedheaderlistview = (Button) findViewById(R.id.btn_pinnedheaderlistview);
        btn_popuwindowview = (Button) findViewById(R.id.btn_popuwindowview);
        btn_pulldoorview = (Button) findViewById(R.id.btn_pulldoorview);
        btn_pulltorefreshview = (Button) findViewById(R.id.btn_pulltorefreshview);
        btn_ratiolayout = (Button) findViewById(R.id.btn_ratiolayout);
        btn_roundimageview = (Button) findViewById(R.id.btn_roundimageview);
        btn_selectpicpopupwindow = (Button) findViewById(R.id.btn_selectpicpopupwindow);
        btn_stickedittext = (Button) findViewById(R.id.btn_stickedittext);
        btn_emoji.setOnClickListener(this);
        btn_alertview.setOnClickListener(this);
        btn_childviewpager.setOnClickListener(this);
        btn_customkeyboard.setOnClickListener(this);
        btn_datetimeymdselectordialogbuilder.setOnClickListener(this);
        btn_horizontallistview.setOnClickListener(this);
        btn_indicatorview.setOnClickListener(this);
        btn_loadingpage.setOnClickListener(this);
        btn_loadingtiedialog.setOnClickListener(this);
        btn_mydialog.setOnClickListener(this);
        btn_mygridview.setOnClickListener(this);
        btn_mylistview.setOnClickListener(this);
        btn_pagertab.setOnClickListener(this);
        btn_pinnedheaderlistview.setOnClickListener(this);
        btn_popuwindowview.setOnClickListener(this);
        btn_pulldoorview.setOnClickListener(this);
        btn_pulltorefreshview.setOnClickListener(this);
        btn_ratiolayout.setOnClickListener(this);
        btn_roundimageview.setOnClickListener(this);
        btn_selectpicpopupwindow.setOnClickListener(this);
        btn_stickedittext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_emoji:
                startActivity(CustomSimpleActivity.class);
                break;
            case R.id.btn_alertview:
                break;
            case R.id.btn_childviewpager:
                break;
            case R.id.btn_customkeyboard:
                startActivity(KeyBoardActivity.class);
                break;
            case R.id.btn_datetimeymdselectordialogbuilder:
                startActivity(DatePickActivity.class);
                break;
            case R.id.btn_horizontallistview:
                startActivity(HorizontalListViewActivity.class);
                break;
            case R.id.btn_indicatorview:
                break;
            case R.id.btn_loadingpage:
                break;
            case R.id.btn_loadingtiedialog:
                break;
            case R.id.btn_mydialog:
                break;
            case R.id.btn_mygridview:
                break;
            case R.id.btn_mylistview:
                break;
            case R.id.btn_pagertab:
                startActivity(PageTabActivity.class);
                break;
            case R.id.btn_pinnedheaderlistview:
                break;
            case R.id.btn_popuwindowview:
                break;
            case R.id.btn_pulldoorview:
                break;
            case R.id.btn_pulltorefreshview:
                startActivity(PulltoRefreshActivity.class);
                break;
            case R.id.btn_ratiolayout:
                break;
            case R.id.btn_roundimageview:
                break;
            case R.id.btn_selectpicpopupwindow:
                startActivity(PhotoActivity.class);
                break;
            case R.id.btn_stickedittext:
                startActivity(StickEditTextActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }


}
