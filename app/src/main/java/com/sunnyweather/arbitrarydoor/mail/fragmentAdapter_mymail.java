package com.sunnyweather.arbitrarydoor.mail;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/**
 * 这里使用多个fragment进行切换，好处是每个fragment 都有自己独立的代码
 * Created by cg on 2015/10/21.
 */

public class fragmentAdapter_mymail extends FragmentPagerAdapter {



    private List<Fragment> list_fragment;                         //fragment列表

    private List<String> list_Title;                              //tab名的列表



    public fragmentAdapter_mymail(FragmentManager fm, List<Fragment>list_fragment, List<String> list_Title) {

        super(fm);

        this.list_fragment = list_fragment;

        this.list_Title = list_Title;

    }



    @Override

    public Fragment getItem(int i) {

        return list_fragment.get(i);

    }



    @Override

    public int getCount() {

        return list_fragment.size();

    }



    /**
     * 此方法是给tablayout中的tab赋值的，就是显示名称
     * @param position
     * @return
     */

    @Override

    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());

    }

}
