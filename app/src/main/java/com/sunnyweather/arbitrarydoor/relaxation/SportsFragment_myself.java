package com.sunnyweather.arbitrarydoor.relaxation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.personalcenter.Personalcenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cg on
 */
public class SportsFragment_myself extends Fragment implements View.OnClickListener{
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private Button return_relaxation_myself2;
    private ImageView breathing_relaxation_method;
    private ImageView Asymptotic_relaxation;
    private ImageView Butterfly_hugs;
    private ImageView Mindfulness_meditation;
    private static final int VIBRATE_TIME = 3000;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_relaxation_myself2, container, false);

        return_relaxation_myself2 = (Button) view.findViewById(R.id.return_relaxation_myself2);
        return_relaxation_myself2.setOnClickListener(this);
        breathing_relaxation_method = (ImageView) view.findViewById(R.id.breathing_relaxation_method);
        breathing_relaxation_method.setOnClickListener(this);
        Asymptotic_relaxation = (ImageView) view.findViewById(R.id.Asymptotic_relaxation);
        Asymptotic_relaxation.setOnClickListener(this);
        Butterfly_hugs = (ImageView) view.findViewById(R.id.Butterfly_hugs);
        Butterfly_hugs.setOnClickListener(this);
        Mindfulness_meditation = (ImageView) view.findViewById(R.id.Mindfulness_meditation);
        Mindfulness_meditation.setOnClickListener(this);
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
            case R.id.return_relaxation_myself2:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_relaxation_myself2);
                getActivity().finish();
                break;
            case R.id.breathing_relaxation_method:

                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent = new Intent(getContext(), Relaxation_Two.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent);
                break;
            case R.id.Asymptotic_relaxation:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent1 = new Intent(getContext(), Asymptotic_Relaxation.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent1);
                break;
            case R.id.Butterfly_hugs:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent2 = new Intent(getContext(), Butterfly_Hugs.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent2);
                break;
            case R.id.Mindfulness_meditation:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent3 = new Intent(getContext(), Mindfulness_Meditation.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent3);
                break;
        }

    }
    private void showPopupWindow(){
        //加载popupwindow布局
        contentView = LayoutInflater.from(getContext()).inflate(
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
        int height=getActivity().getWindowManager().getDefaultDisplay().getHeight();
        popupWindow=new PopupWindow(contentView, getActivity().getWindowManager().getDefaultDisplay().getWidth(),height*6/5);

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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                });
            }
        };time.schedule(tk, 2000);

    }
}