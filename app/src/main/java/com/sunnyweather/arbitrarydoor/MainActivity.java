package com.sunnyweather.arbitrarydoor;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sunnyweather.arbitrarydoor.homepage.Fragment1;
import com.sunnyweather.arbitrarydoor.homepage.Fragment2;
import com.sunnyweather.arbitrarydoor.homepage.Fragment3;
import com.sunnyweather.arbitrarydoor.homepage.Fragment4;

public class MainActivity extends FragmentActivity {
    private Fragment f1;

    private String token;
    private String tokenheader;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        mWindow.setContentView(R.layout.fragment);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        Intent i =getIntent();
        token = i.getStringExtra("date");
        tokenheader = i.getStringExtra("date1");

        Bundle bundle = new Bundle();
        bundle.putString("data",tokenheader+token);
        f1 = new Fragment1();
        f1.setArguments(bundle);//数据传递到fragment中
        getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container,f1,token+" "+tokenheader).commit();
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount()<=0){
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("MainActivity","213");
//        if (resultCode == 1) {
//
//            Log.d("MainActivity","2132");
//            if (data.getStringExtra("getEmotionalStickers").equals("迷茫")){
//                Fragment f2;
//                Bundle bundle = new Bundle();
//                bundle.putString("data",tokenheader+token);
//                f2 = new Fragment2();
//                f2.setArguments(bundle);//数据传递到fragment中
//                getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container,f2,token+" "+tokenheader).commit();
//            }
//            else
//            if (data.getStringExtra("getEmotionalStickers").equals("犯困")||data.getStringArrayExtra("getEmotionalStickers").equals("疑惑")){
//
//                Fragment f3;
//                Bundle bundle = new Bundle();
//                bundle.putString("data",tokenheader+token);
//                f3 = new Fragment3();
//                f3.setArguments(bundle);//数据传递到fragment中
//                getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container,f3,token+" "+tokenheader).commit();
//
//            } else
//            if (data.getStringExtra("getEmotionalStickers").equals("满足")){
//
//                Fragment f4;
//                Bundle bundle = new Bundle();
//                bundle.putString("data",tokenheader+token);
//                f4 = new Fragment4();
//                f4.setArguments(bundle);//数据传递到fragment中
//                getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container,f4,token+" "+tokenheader).commit();
//
//            } else {
//                Fragment f1;
//                Bundle bundle = new Bundle();
//                bundle.putString("data",tokenheader+token);
//                f1 = new Fragment1();
//                f1.setArguments(bundle);//数据传递到fragment中
//                getSupportFragmentManager().beginTransaction().add(R.id.lv_fragment_container,f1,token+" "+tokenheader).commit();
//
//            }
//        }
//    }

//    private void go(View v) {
//        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
//        v.findViewById(R.id.spring_button).startAnimation(shake);
//    }

}