package com.sunnyweather.arbitrarydoor.relaxation;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Window;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.relaxation.Fragment1_relaxation;

public class RelaxationHimself extends FragmentActivity {
    private Fragment1_relaxation f1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        mWindow.setContentView(R.layout.fragment_relaxation);
        f1 = new Fragment1_relaxation();
        getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container_relaxation,f1,"first").commit();
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount()<=0){
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}