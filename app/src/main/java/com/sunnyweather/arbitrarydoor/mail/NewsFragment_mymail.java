package com.sunnyweather.arbitrarydoor.mail;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.relaxation.RelaxationMyself;
import com.sunnyweather.arbitrarydoor.relaxation.Relaxation_Two;

/**
 * Created by cg on 2015/10/21.
 */
public class NewsFragment_mymail extends Fragment {
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mymail1, container, false);
        textView = (TextView) view. findViewById(R.id.textxiong);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Relaxation_Two.class);
                startActivity(intent);
            }
        });

        return view;
    }
}