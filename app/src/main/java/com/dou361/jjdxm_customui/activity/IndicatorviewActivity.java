package com.dou361.jjdxm_customui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dou361.customui.bean.AdvBean;
import com.dou361.customui.holder.HomeAdvHolder;
import com.dou361.customui.listener.AdvLoadingListener;
import com.dou361.jjdxm_customui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IndicatorviewActivity extends AppCompatActivity {


    @Bind(R.id.ll_adv)
    LinearLayout llAdv;
    List<AdvBean> list = new ArrayList<AdvBean>();
    Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_view);
        ButterKnife.bind(this);
        HomeAdvHolder holder = new HomeAdvHolder(this, mHanlder);
        AdvBean advBean1 = new AdvBean();
        advBean1.setTitle("读万卷书，行万里路。");
        advBean1.setUrl("http://k.zol-img.com.cn/dcbbs/19967/a19966725_s.jpg");
        AdvBean advBean2 = new AdvBean();
        advBean2.setTitle("黑发不知勤学早，白首方悔读书迟。");
        advBean2.setUrl("http://img5.imgtn.bdimg.com/it/u=1599832793,1765741380&fm=23&gp=0.jpg");
        AdvBean advBean3 = new AdvBean();
        advBean3.setTitle("书卷多情似故人，晨昏忧乐每相亲。");
        advBean3.setUrl("http://image.tianjimedia.com/uploadImages/2014/064/HTB8E065G43S.jpg");
        AdvBean advBean4 = new AdvBean();
        advBean4.setTitle("书犹药也，善读之可以医愚。");
        advBean4.setUrl("http://image.tianjimedia.com/uploadImages/2014/064/6029835I81E1.jpg");
        list.add(advBean1);
        list.add(advBean2);
        list.add(advBean3);
        list.add(advBean4);
        holder.setData(list);
        holder.setOnAdvLoadingListener(new AdvLoadingListener() {

            @Override
            public void onAdvLoadingListener(ImageView iamgeView, String url) {
                Glide.with(IndicatorviewActivity.this).load(url).into(iamgeView);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        llAdv.addView(holder.getRootView(), params);
    }

}
