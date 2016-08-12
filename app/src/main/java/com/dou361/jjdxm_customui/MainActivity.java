package com.dou361.jjdxm_customui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dou361.jjdxm_customui.activity.AlertViewActivity;
import com.dou361.jjdxm_customui.activity.DatePickActivity;
import com.dou361.jjdxm_customui.activity.HorizontalListViewActivity;
import com.dou361.jjdxm_customui.activity.PageTabActivity;
import com.dou361.jjdxm_customui.activity.PhotoActivity;
import com.dou361.jjdxm_customui.activity.PulltoRefreshActivity;
import com.dou361.jjdxm_customui.activity.StickEditTextActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.btn_alertview)
    Button btnAlertview;
    @Bind(R.id.btn_childviewpager)
    Button btnChildviewpager;
    @Bind(R.id.btn_datetimeymdselectordialogbuilder)
    Button btnDatetimeymdselectordialogbuilder;
    @Bind(R.id.btn_horizontallistview)
    Button btnHorizontallistview;
    @Bind(R.id.btn_indicatorview)
    Button btnIndicatorview;
    @Bind(R.id.btn_loadingpage)
    Button btnLoadingpage;
    @Bind(R.id.btn_loadingtiedialog)
    Button btnLoadingtiedialog;
    @Bind(R.id.btn_mydialog)
    Button btnMydialog;
    @Bind(R.id.btn_mygridview)
    Button btnMygridview;
    @Bind(R.id.btn_mylistview)
    Button btnMylistview;
    @Bind(R.id.btn_pagertab)
    Button btnPagertab;
    @Bind(R.id.btn_pinnedheaderlistview)
    Button btnPinnedheaderlistview;
    @Bind(R.id.btn_popuwindowview)
    Button btnPopuwindowview;
    @Bind(R.id.btn_pulldoorview)
    Button btnPulldoorview;
    @Bind(R.id.btn_pulltorefreshview)
    Button btnPulltorefreshview;
    @Bind(R.id.btn_ratiolayout)
    Button btnRatiolayout;
    @Bind(R.id.btn_roundimageview)
    Button btnRoundimageview;
    @Bind(R.id.btn_selectpicpopupwindow)
    Button btnSelectpicpopupwindow;
    @Bind(R.id.btn_stickedittext)
    Button btnStickedittext;
    @Bind(R.id.main)
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void startActivity(Class<?> clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }


    @OnClick({R.id.btn_alertview, R.id.btn_childviewpager, R.id.btn_datetimeymdselectordialogbuilder, R.id.btn_horizontallistview, R.id.btn_indicatorview, R.id.btn_loadingpage, R.id.btn_loadingtiedialog, R.id.btn_mydialog, R.id.btn_mygridview, R.id.btn_mylistview, R.id.btn_pagertab, R.id.btn_pinnedheaderlistview, R.id.btn_popuwindowview, R.id.btn_pulldoorview, R.id.btn_pulltorefreshview, R.id.btn_ratiolayout, R.id.btn_roundimageview, R.id.btn_selectpicpopupwindow, R.id.btn_stickedittext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alertview:
                startActivity(AlertViewActivity.class);
                break;
            case R.id.btn_childviewpager:
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
}
