package com.dou361.jjdxm_customui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dou361.customui.bean.Popu;
import com.dou361.customui.ui.AlertView;
import com.dou361.jjdxm_customui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertViewActivity extends AppCompatActivity {


    @Bind(R.id.btn_test)
    Button btnTest;
    @Bind(R.id.btn_array)
    Button btnArray;
    private Context mContext;
    private List<Popu> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertview);
        ButterKnife.bind(this);
        mContext = this;
        lists = new ArrayList<>();
        Popu p = new Popu();
        p.setTitle("我");
        p.setId(1);
        lists.add(p);
        Popu p2 = new Popu();
        p2.setTitle("不知道");
        p2.setId(2);
        lists.add(p2);
        Popu p3 = new Popu();
        p3.setTitle("他们");
        p3.setId(3);
        lists.add(p3);
    }

    @OnClick({R.id.btn_test, R.id.btn_array})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                showCategorySelect();
                break;
            case R.id.btn_array:
                selectTime();
                break;
        }
    }

    private void showCategorySelect() {
        new AlertView(null, null, null, null, lists, mContext, AlertView.Style.ActionCenter, new AlertView.OnItemClickListener() {

            @Override
            public void onItemClick(Object o, int position) {
                Toast.makeText(mContext, lists.get(position).getId() + "----" + lists.get(position).getTitle(), Toast.LENGTH_LONG).show();

            }
        }).setCancelable(true).show();
    }

    /**
     * 弹出提示框
     */
    private void selectTime() {
        new AlertView(null, null, null, null, new String[]{"当前时间", "选择时间"}, mContext, AlertView.Style.ActionCenter, new AlertView.OnItemClickListener() {

            @Override
            public void onItemClick(Object o, int position) {
                Toast.makeText(mContext, "----" + position, Toast.LENGTH_LONG).show();
            }
        }).setCancelable(true).show();
    }
}
