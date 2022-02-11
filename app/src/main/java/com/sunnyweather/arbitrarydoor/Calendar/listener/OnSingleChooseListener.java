package com.sunnyweather.arbitrarydoor.Calendar.listener;

import android.view.View;

import com.sunnyweather.arbitrarydoor.Calendar.bean.*;

/**
 * 日期点击接口
 */
public interface OnSingleChooseListener {
    /**
     * @param view
     * @param date
     */
    void onSingleChoose(View view, DateBean date);
}
