package com.sunnyweather.arbitrarydoor.relaxation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

public class Fragment5_relaxation extends Fragment implements View.OnClickListener {
    private static final int VIBRATE_TIME = 3000;

    private View view;
    private Button OK_himself;
    private Button return_himself4;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listen_relaxation4, container, false);
        OK_himself = (Button) view.findViewById(R.id.OK_himself);
        OK_himself.setOnClickListener(this);
        return_himself4 = (Button) view.findViewById(R.id.return_himself4);
        return_himself4.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_himself4:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                getActivity().onBackPressed();
                break;
            case R.id.OK_himself:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                getActivity().onBackPressed();
                break;

        }
    }
}


