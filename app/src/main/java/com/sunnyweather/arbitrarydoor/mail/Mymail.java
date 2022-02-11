package com.sunnyweather.arbitrarydoor.mail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.sunnyweather.arbitrarydoor.R;

import java.util.ArrayList;
import java.util.List;

public class Mymail extends AppCompatActivity implements View.OnClickListener{
    private TabLayout tab_title;
    private ViewPager vp_pager;

    private List<String> list_title;                                      //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;

    private View newsView;                                                //定义系统页面
    private View sportView;                                               //定义余音页面
    private View funView;                                                 //定义我的信件页面
                                       //定义以view为切换的adapter
    private fragmentAdapter_mymail fAdapter;                                     //定义以fragment为切换的adapter

    private NewsFragment_mymail nFragment;
    private SportsFragment_mymail sFragment;
    private FunFragment_mymail fFragment;

    private Button return_mymail;
    private Button return_mymail1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        setContentView(R.layout.activity_mymail);

        return_mymail = (Button) findViewById(R.id.return_mymail);
        return_mymail.setOnClickListener(this);
        return_mymail1 = (Button) findViewById(R.id.return_mymail1);
        return_mymail1.setOnClickListener(this);
        initControls();


        fragmentChange();
    }
    private void initControls()
    {
        tab_title = (TabLayout) findViewById(R.id.tab_title);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);

    }

    private void fragmentChange()
    {
        list_fragment = new ArrayList<>();

        nFragment = new NewsFragment_mymail();
        sFragment = new SportsFragment_mymail();
        fFragment = new FunFragment_mymail();


        list_fragment.add(nFragment);
        list_fragment.add(sFragment);
        list_fragment.add(fFragment);

        list_title = new ArrayList<>();
        list_title.add("系统消息");
        list_title.add("收到余音");
        list_title.add("我的信件");

        tab_title.setTabMode(TabLayout.MODE_FIXED);

        fAdapter = new fragmentAdapter_mymail(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_mymail:
                initButton(return_mymail);
                initButton(return_mymail1);

                finish();
                break;
            case R.id.return_mymail1:
                initButton(return_mymail);
                initButton(return_mymail1);
                finish();
                break;
        }
    }
}