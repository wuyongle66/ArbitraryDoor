package com.sunnyweather.arbitrarydoor.relaxation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

public class Fragment1_relaxation extends Fragment implements View.OnClickListener {

    private static final int VIBRATE_TIME = 3000;

    private Button match_himself;
    private Button return_himself;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_relaxation_himself,container,false);

        match_himself = (Button) view.findViewById(R.id.match_himself);
        match_himself.setOnClickListener(this);
        return_himself = (Button) view.findViewById(R.id.return_himself);
        return_himself.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_himself:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                getActivity().onBackPressed();
                break;
            case R.id.match_himself:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Fragment2_relaxation f2 = new Fragment2_relaxation();
                FragmentManager mManager = getFragmentManager();
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.replace(R.id.lv_fragment_container_relaxation,f2);
                ft1.commit();
                break;
        }

    }
}
