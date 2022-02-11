package com.sunnyweather.arbitrarydoor.relaxation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.personalcenter.Personaldate_Personalcenter;

import java.util.Timer;
import java.util.TimerTask;

public class Relaxation extends AppCompatActivity implements View.OnClickListener{
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private final int VIBRATE_TIME = 3000;
    private ImageView return_relaxation;
    private Button return_relaxation1;
    private Button time_relaxation;
    private Button himself_relaxation;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relaxation);

        return_relaxation = (ImageView) findViewById(R.id.return_relaxation);
        return_relaxation.setOnClickListener(this);
        return_relaxation1 = (Button) findViewById(R.id.return_relaxation1);
        return_relaxation1.setOnClickListener(this);
        time_relaxation = (Button) findViewById(R.id.time_relaxation);
        time_relaxation.setOnClickListener(this);
        himself_relaxation = (Button) findViewById(R.id.himself_relaxation);
        himself_relaxation.setOnClickListener(this);
    }

    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    private void initImageView(ImageView bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_relaxation:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initImageView(return_relaxation);
                initButton(return_relaxation1);
                finish();
                break;
            case R.id.return_relaxation1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initImageView(return_relaxation);
                initButton(return_relaxation1);
                finish();
                break;
            case R.id.time_relaxation:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(time_relaxation);
                Intent intent = new Intent(this, RelaxationMyself.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent);
                break;
            case R.id.himself_relaxation:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(himself_relaxation);
                Intent intent1 = new Intent(this, RelaxationHimself.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent1);
                break;
        }
    }
    private void showPopupWindow(){
        //加载popupwindow布局
        contentView = LayoutInflater.from(this).inflate(
                R.layout.activity_pop, null);

        //设置各控件的点击事件
        RelativeLayout pop = (RelativeLayout) contentView.findViewById(R.id.pop);
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        DYLoadingView dy1 = (DYLoadingView) contentView.findViewById(R.id.dy1);
        dy1.start();
        //获取弹窗实例，其中height为弹窗高度
        int height=getWindowManager().getDefaultDisplay().getHeight();
        popupWindow=new PopupWindow(contentView, getWindowManager().getDefaultDisplay().getWidth(),height*6/5);

        //取得焦点
        popupWindow.setFocusable(true);

        //popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(false);
        //设置可以点击
        popupWindow.setTouchable(true);

        //设置进入退出动画
    }

    public void openPopWindow(){
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0);
    }
    private void enterHome(Intent intent){
        Timer time = new Timer();
        TimerTask tk = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                });
            }
        };time.schedule(tk, 2000);

    }
}