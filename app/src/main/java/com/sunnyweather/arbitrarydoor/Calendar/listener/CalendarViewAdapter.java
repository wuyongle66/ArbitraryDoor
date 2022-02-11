package com.sunnyweather.arbitrarydoor.Calendar.listener;

import android.view.View;
import android.widget.TextView;

import com.sunnyweather.arbitrarydoor.Calendar.bean.*;

public interface CalendarViewAdapter {
    /**
     * 返回阳历、阴历两个TextView
     *
     * @param view
     * @param date
     * @return
     */
    TextView[] convertView(View view, DateBean date);
}
