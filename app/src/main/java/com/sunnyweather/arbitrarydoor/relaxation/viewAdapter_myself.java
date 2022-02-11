package com.sunnyweather.arbitrarydoor.relaxation;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sunnyweather.arbitrarydoor.homepage.Fragment1;

import java.util.ArrayList;
import java.util.List;

/**
 * 此adpter是在一个fragment中切换不同的view，好处是在一个activity中可以取得所有view中的值
 */
public class viewAdapter_myself extends FragmentPagerAdapter {
    public List<Fragment> list_fragment;
    private List<String> list_Title;                              //tab名的列表
    private int[] tabImg;
    private int VISIBLE;
    private Context context;
    public viewAdapter_myself(Context context,FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title, int[] tabImg) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        this.tabImg = tabImg;
        this.context= context;
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
        Drawable dImage = context.getResources().getDrawable(tabImg[position]);
        dImage.setBounds(0, 10, dImage.getIntrinsicWidth(), dImage.getIntrinsicHeight());
        //这里前面加的空格就是为图片显示
        SpannableString sp = new SpannableString("  "+ list_Title.get(position));
        sp.setSpan(new RelativeSizeSpan(1.6f),2,4,sp.SPAN_INCLUSIVE_INCLUSIVE);//设置文字大小
        ImageSpan imageSpan = new ImageSpan(dImage, ImageSpan.ALIGN_BASELINE);
        sp.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return  sp;

    }



}