package com.sunnyweather.arbitrarydoor.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.Mood;

import java.util.List;

public class MoodAdapter extends ArrayAdapter<Mood> {

    private int resourceId;

    private ImageView star;
    private TextView creattime;
    private TextView emotionalStickers;
    private TextView eventRecord;
    private TextView textRecord;
    private TextView creattime1;
    public MoodAdapter(Context context, int textViewResourceId,
                       List<Mood> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mood mood = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        star = view.findViewById(R.id.star);

        Log.d("MAD",mood.getImageId()+" "+mood.getCreattime()+" "+mood.getEmotionalStickers());
        creattime =  view.findViewById(R.id.diary_mood);
        emotionalStickers =  view.findViewById(R.id.emotionalStickers_mood);
        eventRecord =  view.findViewById(R.id.eventRecord_mood);
        textRecord =  view.findViewById(R.id.textRecord_mood);
        creattime1 =  view.findViewById(R.id.creattime1);

        creattime.setText(mood.getCreattime());
        star.setImageResource(mood.getImageId());
        emotionalStickers.setText("今日份 "+mood.getEmotionalStickers());
        eventRecord.setText(mood.getEventRecord());
        textRecord.setText(mood.getTextRecord());
        creattime1.setText(mood.getCreattime1());
        ListView.LayoutParams param = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,700);
        view.setLayoutParams(param);
        return view;
    }
}
