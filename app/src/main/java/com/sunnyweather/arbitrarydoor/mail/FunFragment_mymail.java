package com.sunnyweather.arbitrarydoor.mail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunnyweather.arbitrarydoor.R;

/**
 * Created by cg on 2015/10/21.
 */
public class FunFragment_mymail extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mymail3, container, false);
        return view;
    }
}