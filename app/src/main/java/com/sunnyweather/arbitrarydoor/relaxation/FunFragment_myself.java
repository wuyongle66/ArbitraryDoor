package com.sunnyweather.arbitrarydoor.relaxation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

/**
 * Created by cg on 2015/10/21.
 */
public class FunFragment_myself extends Fragment implements View.OnClickListener{
    private static final int VIBRATE_TIME = 3000;

    private View view;
    private Button return_relaxation_myself3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_relaxation_myself3, container, false);
        return_relaxation_myself3 = (Button) view.findViewById(R.id.return_relaxation_myself3);
        return_relaxation_myself3.setOnClickListener(this);
        return view;
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_relaxation_myself3:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_relaxation_myself3);
                getActivity().finish();
                break;
        }
    }
}