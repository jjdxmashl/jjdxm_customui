package com.dou361.jjdxm_customui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dou361.customui.widget.LoadingPage;
import com.dou361.jjdxm_customui.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoadingPagerActivity extends AppCompatActivity {


    @Bind(R.id.ll_content)
    LinearLayout ll_content;
    private LoadingPage mLoadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_pager);
        ButterKnife.bind(this);
        mLoadingPage = new LoadingPage(this) {
            @Override
            public View createSuccessView() {
                return LoadingPagerActivity.this.createSuccessView();
            }

            @Override
            public LoadingPage.LoadResult load() {
                return LoadingPagerActivity.this.load();
            }

        };
        ll_content.addView(mLoadingPage, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLoadingPage.show();
    }


    private LoadingPage.LoadResult load() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        return LoadingPage.LoadResult.ERROR;
    }


    private View createSuccessView() {
        return null;
    }


}
