package com.sunnyweather.arbitrarydoor.relaxation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.homepage.Fragment1;

import java.util.ArrayList;
import java.util.List;

public class RelaxationMyself extends AppCompatActivity  {
    private TabLayout tab_title_myself;
    private ViewPager vp_pager_myself;

    private List<String> list_title;                                      //tab名称列表
    private List<Fragment> listViews;

    private NewsFragment_myself nFragment;
    private SportsFragment_myself sFragment;
    private FunFragment_myself fFragment;
    //定义以view为切换的adapter
    private viewAdapter_myself vAdapter;


    private int[] tabImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        setContentView(R.layout.activity_relaxation_myself);


        initControls();

        viewChanage();

    }
    private void initControls()
    {
        tab_title_myself = findViewById(R.id.tab_title_myself);
        vp_pager_myself = findViewById(R.id.vp_pager_myself);

        tabImg = new int[]{R.drawable.relaxation_myself1,R.drawable.relaxation_myself2,R.drawable.relaxation_myself3};
    }
    private void viewChanage()
    {
        listViews = new ArrayList<>();//fragment
        LayoutInflater mInflater = getLayoutInflater();

        nFragment = new NewsFragment_myself();
        sFragment = new SportsFragment_myself();
        fFragment = new FunFragment_myself();


        listViews.add(nFragment);
        listViews.add(sFragment);
        listViews.add(fFragment);

        list_title = new ArrayList<>();
        list_title.add("音乐");
        list_title.add("放松");
        list_title.add("阅读");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tab_title_myself.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        tab_title_myself.addTab(tab_title_myself.newTab().setText(list_title.get(0)));
        tab_title_myself.addTab(tab_title_myself.newTab().setText(list_title.get(1)));
        tab_title_myself.addTab(tab_title_myself.newTab().setText(list_title.get(2)));



        vAdapter = new viewAdapter_myself(this,getSupportFragmentManager(),listViews,list_title,tabImg);
        vp_pager_myself.setAdapter(vAdapter);

        //将tabLayout与viewpager连起来
        tab_title_myself.setupWithViewPager(vp_pager_myself);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }//点击切换View的焦点事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}