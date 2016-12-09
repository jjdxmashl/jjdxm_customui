package com.dou361.jjdxm_customui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dou361.jjdxm_customui.activity.IndicatorviewActivity;
import com.dou361.jjdxm_customui.activity.LoadingPagerActivity;
import com.dou361.jjdxm_customui.activity.PageTabActivity;
import com.dou361.jjdxm_customui.activity.PulltoRefreshActivity;
import com.dou361.jjdxm_customui.activity.StickEditTextActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


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

    @OnClick({R.id.btn_indicatorview, R.id.btn_loadingpage, R.id.btn_pagertab, R.id.btn_pinnedheaderlistview, R.id.btn_pulldoorview, R.id.btn_pulltorefreshview, R.id.btn_ratiolayout, R.id.btn_stickedittext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_indicatorview:
                startActivity(IndicatorviewActivity.class);
                break;
            case R.id.btn_loadingpage:
                startActivity(LoadingPagerActivity.class);
                break;
            case R.id.btn_pagertab:
                startActivity(PageTabActivity.class);
                break;
            case R.id.btn_pinnedheaderlistview:
                break;
            case R.id.btn_pulldoorview:
                break;
            case R.id.btn_pulltorefreshview:
                startActivity(PulltoRefreshActivity.class);
                break;
            case R.id.btn_ratiolayout:
                break;
            case R.id.btn_stickedittext:
                startActivity(StickEditTextActivity.class);
                break;
        }
    }
}
