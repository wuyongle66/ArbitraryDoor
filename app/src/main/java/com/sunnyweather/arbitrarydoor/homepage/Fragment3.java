package com.sunnyweather.arbitrarydoor.homepage;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.User.User_person;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.mail.Mymail;
import com.sunnyweather.arbitrarydoor.personalcenter.Personalcenter;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.record.Record;
import com.sunnyweather.arbitrarydoor.relaxation.Relaxation;
import com.sunnyweather.arbitrarydoor.service.PersonService;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment3 extends Fragment implements View.OnClickListener {
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private final int VIBRATE_TIME = 3000;
    private LinearLayout summer;
    private Button summer_personal_center;
    private Button summer_my_mail;
    private Button summer_stress_relaxation;
    private Button summer_Button;
    private View view;
    private String token;
    private String tokenheader;
    private String num = null;

    private final String TAG="--Fragment1Activity--";
    private Retrofit retrofit;
    private PersonService personService;
    private User_person user_person;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_summer, container, false);
        summer = (LinearLayout) view.findViewById(R.id.summer);
        summer.setOnClickListener(this);
        Bundle bundle = this.getArguments();//得到从Activity传来的数据
        num = bundle.getString("data");

        summer_personal_center = (Button) view.findViewById(R.id.summer_personal_center);
        summer_personal_center.setOnClickListener(this);
        summer_my_mail = (Button) view.findViewById(R.id.summer_my_mail);
        summer_my_mail.setOnClickListener(this);
        summer_stress_relaxation = (Button) view.findViewById(R.id.summer_stress_relaxation);
        summer_stress_relaxation.setOnClickListener(this);
        summer_Button = (Button) view.findViewById(R.id.summer_button);
        summer_Button.setOnClickListener(this);
        return view;
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.3f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            //GET_CODE是自定义的页面跳转识别码
            if (data.getStringExtra("getEmotionalStickers").equals("开心")){
                Bundle bundle = new Bundle();
                bundle.putString("data",num);
                Fragment1 f1 = new Fragment1();
                f1.setArguments(bundle);
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.lv_fragment_container,f1);
                ft1.commit();
            }
            else
            if (data.getStringExtra("getEmotionalStickers").equals("迷茫")){

                Bundle bundle = new Bundle();
                bundle.putString("data",num);
                Fragment2 f2 = new Fragment2();
                f2.setArguments(bundle);
                FragmentManager mManager = getFragmentManager();
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.replace(R.id.lv_fragment_container,f2);
                ft1.commit();
            } else
            if (data.getStringExtra("getEmotionalStickers").equals("满足")){

                Bundle bundle = new Bundle();
                bundle.putString("data",num);
                Fragment4 f4 = new Fragment4();
                f4.setArguments(bundle);
                FragmentManager fm4 = getFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ft4.replace(R.id.lv_fragment_container,f4);
                ft4.commit();
            } else {
            }


        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.summer:
                Bundle bundle = new Bundle();
                bundle.putString("data",num);
                Fragment4 f4 = new Fragment4();
                f4.setArguments(bundle);
                FragmentManager fm4 = getFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ft4.replace(R.id.lv_fragment_container,f4);
                ft4.commit();
                break;
            case R.id.summer_personal_center:
                initButton(summer_personal_center);

                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            retrofit = new Retrofit.Builder()
                                    .baseUrl("http://122.112.141.60:8080/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            personService = retrofit.create(PersonService.class);


                            personService.getUser(num).enqueue(new Callback<User_person>() {
                                @Override
                                public void onResponse(Call<User_person> call, Response<User_person> response) {
                                    user_person=response.body();
                                    Log.d(TAG, user_person.getCode()+"");
                                    Log.d(TAG, user_person.getData().getSex());
                                    Log.d(TAG, user_person.getData().getTelephone());
                                    Intent intent1 = new Intent(getContext(), Personalcenter.class);
                                    intent1.putExtra("personalcenter_Birthday",user_person.getData().getBirthday());
                                    intent1.putExtra("personalcenter_Sex",user_person.getData().getSex());
                                    intent1.putExtra("personalcenter_Telephone",user_person.getData().getTelephone());
                                    intent1.putExtra("personalcenter_Username",user_person.getData().getUsername());
                                    intent1.putExtra("num",num);
                                    showPopupWindow();
                                    openPopWindow();
                                    enterHome(intent1);
                                }

                                @Override
                                public void onFailure(Call<User_person> call, Throwable t) {
                                    Log.d(TAG, "请求失败");
                                }
                            });
                        }catch (Exception e){e.printStackTrace();}
                    }
                }).start();
                break;
            case R.id.summer_my_mail:
                initButton(summer_my_mail);//按钮震动
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent2 = new Intent(getContext(), Mymail.class);

                showPopupWindow();
                openPopWindow();
                enterHome(intent2);

                break;
            case R.id.summer_stress_relaxation:
                initButton(summer_stress_relaxation);//按钮震动
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent3 = new Intent(getContext(), Relaxation.class);

                showPopupWindow();
                openPopWindow();
                enterHome(intent3);
                break;
            case R.id.summer_button:
                initButton(summer_Button);//按钮震动
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent4 = new Intent(getContext(), Record.class);

                intent4.putExtra("num_record",num);
                showPopupWindow();
                openPopWindow();
                enterHome(intent4);
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
                startActivityForResult(intent,1);
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