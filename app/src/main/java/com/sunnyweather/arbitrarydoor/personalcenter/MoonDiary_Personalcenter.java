package com.sunnyweather.arbitrarydoor.personalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sunnyweather.arbitrarydoor.Adapter.MoodAdapter;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.Mood;
import com.sunnyweather.arbitrarydoor.User.User_mood_list;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.sunnyweather.arbitrarydoor.Calendar.bean.DateBean;
import com.sunnyweather.arbitrarydoor.Calendar.listener.OnSingleChooseListener;
import com.sunnyweather.arbitrarydoor.Calendar.listener.OnPagerChangeListener;
import com.sunnyweather.arbitrarydoor.Calendar.utils.CalendarUtil;
import com.sunnyweather.arbitrarydoor.Calendar.weiget.CalendarView;
public class MoonDiary_Personalcenter extends AppCompatActivity implements View.OnClickListener {

    private static final int VIBRATE_TIME = 3000;

    private Button return_moondiary;
    private Button return_moondiary1;

    private List<User_mood_list.Mood_List> lists;
    private ListView listView;
    private List<Mood> moodList = new ArrayList<>();

    private CalendarView calendarView;

    private int[] cDate = CalendarUtil.getCurrentDate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_diary_personalcenter);
        Intent intent=getIntent();
        Log.d("TSF",intent+"");
        if (intent!=null){
            lists = (List<User_mood_list.Mood_List>) getIntent().getSerializableExtra("User_mood_list");
            Log.i("TAG", "Person name : " + lists.get(0).getCreateTime() + " ---- age : "+lists.size() );
            initMoods();
        }


        MoodAdapter adapter=new MoodAdapter(MoonDiary_Personalcenter.this,
                R.layout.simple_list_item_1,moodList);
        listView = findViewById(R.id.list_view_mood);
        listView.setAdapter(adapter);
//        Log.d("TAG1", user_mood_list.getData().size()+"");
//        Log.d("TAG1", user_mood_list.getData().get(0).getDiaryId());
//        Log.d("TAG1", user_mood_list.getData().get(0).getEmotionalStickers());
        return_moondiary = (Button) findViewById(R.id.return_moondiary);
        return_moondiary.setOnClickListener(this);

        return_moondiary1 = (Button) findViewById(R.id.return_moondiary1);
        return_moondiary1.setOnClickListener(this);



        final Button title_calendar =(Button) findViewById(R.id.title_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        HashMap<String,String> map = new HashMap<>();
        calendarView
//                .setSpecifyMap(map)
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title_calendar.setText(date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                title_calendar.setText(date.getSolar()[1] + "月");
            }
        });
    }

    private void initMoods() {
        for (int i =0 ;i<lists.size();i++){
            String creat=lists.get(i).getCreateTime();
            creat = creat.substring(0, 10);
            String createTime=lists.get(i).getCreateTime();
            createTime = createTime.substring(11, 17);

            if (lists.get(i).getEmotionalStickers().equals("开心")) {
                Mood mood = new Mood(creat,R.drawable.emoji1,
                        lists.get(i).getEmotionalStickers(),lists.get(i).getEventRecord(),lists.get(i).getTextRecord(),createTime);
                moodList.add(mood);

            }else
            if (lists.get(i).getEmotionalStickers().equals("疑惑")) {
                Mood mood = new Mood(creat,R.drawable.return3,
                        lists.get(i).getEmotionalStickers(),lists.get(i).getEventRecord(),lists.get(i).getTextRecord(),createTime);
                moodList.add(mood);
            }else
            if (lists.get(i).getEmotionalStickers().equals("犯困")) {
                Mood mood = new Mood(creat,R.drawable.return2,
                        lists.get(i).getEmotionalStickers(),lists.get(i).getEventRecord(),lists.get(i).getTextRecord(),createTime);
                moodList.add(mood);
            }else
            if (lists.get(i).getEmotionalStickers().equals("满足")) {
                Mood mood = new Mood(creat,R.drawable.return4,
                        lists.get(i).getEmotionalStickers(),lists.get(i).getEventRecord(),lists.get(i).getTextRecord(),createTime);
                moodList.add(mood);
            }else
            if (lists.get(i).getEmotionalStickers().equals("迷茫")) {
                Mood mood = new Mood(creat,R.drawable.return5,
                        lists.get(i).getEmotionalStickers(),lists.get(i).getEventRecord(),lists.get(i).getTextRecord(),createTime);
                moodList.add(mood);
            }

            Log.i("TAG", "P:" + lists.get(0).getCreateTime() + " ---- age : "+lists.size() +"  "+ i);
        }
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_moondiary:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_moondiary);
                initButton(return_moondiary1);
                finish();
                break;
            case R.id.return_moondiary1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_moondiary);
                initButton(return_moondiary1);
                finish();
                break;
        }
    }

//    public void today(View view) {
//        calendarView.today();
//    }

    public void lastMonth(View view) {
        calendarView.lastMonth();
    }

    public void nextMonth(View view) {
        calendarView.nextMonth();
    }

//    public void start(View view) {
//        calendarView.toStart();
//    }
//
//    public void end(View view) {
//        calendarView.toEnd();
//    }
//
//    public void lastYear(View view) {
//        calendarView.lastYear();
//    }
//
//    public void nextYear(View view) {
//        calendarView.nextYear();
//    }

}