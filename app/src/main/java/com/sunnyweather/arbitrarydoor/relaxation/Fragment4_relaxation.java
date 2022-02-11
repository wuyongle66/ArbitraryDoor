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

public class Fragment4_relaxation extends Fragment implements View.OnClickListener {
    private static final int VIBRATE_TIME = 3000;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listen_relaxation3, container, false);
        Button send_himself = (Button) view.findViewById(R.id.send_himself);
        send_himself.setOnClickListener(this);
        Button return_himself3 = (Button) view.findViewById(R.id.return_himself3);
        return_himself3.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_himself3:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                getActivity().onBackPressed();
                break;
            case R.id.send_himself:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Fragment5_relaxation f5 = new Fragment5_relaxation();
                FragmentManager mManager = getFragmentManager();
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.replace(R.id.lv_fragment_container_relaxation,f5);
                ft1.commit();
                break;
        }
    }
}
