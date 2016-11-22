package com.dou361.jjdxm_customui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dou361.customui.widget.PullToRefreshListView;
import com.dou361.customui.widget.PullToRefreshView;
import com.dou361.jjdxm_customui.R;

import java.util.Arrays;

public class PulltoRefreshActivity extends AppCompatActivity implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {

    private Context mContext;
    private PullToRefreshListView plv;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        mContext = this;
        plv = (PullToRefreshListView) findViewById(R.id.plv);
        plv.setPullDownDamp(true);
        plv.setPullUpDamp(true);
        plv.setOnFooterRefreshListener(this);
        plv.setOnHeaderRefreshListener(this);
        mListView = plv.getContentView();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList(mStrings));
        mListView.setAdapter(mAdapter);

    }

    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        plv.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                plv.onHeaderRefreshCompleteAndTime();
            }
        }, plv.delay_DURATION);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        plv.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                plv.onFooterRefreshComplete();
            }
        }, plv.delay_DURATION);
    }
}
