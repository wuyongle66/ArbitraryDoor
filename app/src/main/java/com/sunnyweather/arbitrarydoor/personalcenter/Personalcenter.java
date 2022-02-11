package com.sunnyweather.arbitrarydoor.personalcenter;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.User_mood;
import com.sunnyweather.arbitrarydoor.User.User_mood_list;
import com.sunnyweather.arbitrarydoor.User.User_person;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.service.MoodDiaryService;
import com.sunnyweather.arbitrarydoor.service.MoodListService;
import com.sunnyweather.arbitrarydoor.service.PersonService;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Personalcenter extends AppCompatActivity implements View.OnClickListener{

    private static final int VIBRATE_TIME = 3000;
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private Button return_personalcenter;
    private Button return_personalcenter1;

    private LinearLayout personaldata_personalcenter;
    private Button personaldata_personalcenter1;
    private Button personaldata_personalcenter2;

    private LinearLayout mooddiary_personalcenter;
    private Button mooddiary_personalcenter1;
    private Button mooddiary_personalcenter2;
    private Button mooddiary_personalcenter3;


    private LinearLayout problemfeedback_personalcenter;
    private Button problemfeedback_personalcenter1;
    private Button problemfeedback_personalcenter2;

    private LinearLayout arbitarydoor_personalcenter;
    private Button arbitarydoor_personalcenter1;
    private Button arbitarydoor_personalcenter2;

    private String personalcenter_Birthday;
    private String personalcenter_Birthday1;
    private String personalcenter_Sex;
    private String personalcenter_Telephone;
    private String personalcenter_Username;
    private String num;

    private TextView UserID;
    private TextView WaitingAlbee;
    private TextView age_personal;
    private ImageView sex_personal;
    int year1;


    private final String TAG="--PersonalcenterActivity--";
    private Retrofit retrofit;
    private PersonService personService;
    private User_person user_person;

    private Retrofit retrofit_mood_list;
    private MoodListService moodListService;
    private User_mood_list user_mood_list;
    int year;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalcenter);


        Intent i =getIntent();
        personalcenter_Birthday1 = i.getStringExtra("personalcenter_Birthday");
        personalcenter_Birthday = i.getStringExtra("personalcenter_Birthday");
        personalcenter_Sex = i.getStringExtra("personalcenter_Sex");
        personalcenter_Telephone = i.getStringExtra("personalcenter_Telephone");
        personalcenter_Username = i.getStringExtra("personalcenter_Username");
        num = i.getStringExtra("num");

        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        year = calendar.get(Calendar.YEAR);
        WaitingAlbee = findViewById(R.id.WaitingAlbee);
        UserID = findViewById(R.id.UserID);
        age_personal = findViewById(R.id.age_personal);
        sex_personal = findViewById(R.id.sex_personal);
        if (personalcenter_Username==null){
            WaitingAlbee.setText("任意门");
        } else {
            WaitingAlbee.setText(personalcenter_Username);
        }
        if (personalcenter_Birthday!=null){

            personalcenter_Birthday= personalcenter_Birthday.substring(0,4);

            year1 = year-Integer.parseInt(personalcenter_Birthday);
        }
        if (personalcenter_Birthday==null){
            age_personal.setText("0岁");
        } else {
            age_personal.setText( String.valueOf(year1)+"岁");
        }
        if (personalcenter_Sex.equals("男")){
            sex_personal.setImageResource(R.drawable.main_interface5);
        } else {
            sex_personal.setImageResource(R.drawable.main_interface8);
        }
        UserID.setText("User ID: "+personalcenter_Telephone);


        return_personalcenter = (Button) findViewById(R.id.return_personalcenter);
        return_personalcenter.setOnClickListener(this);

        return_personalcenter1 = (Button) findViewById(R.id.return_personalcenter1);
        return_personalcenter1.setOnClickListener(this);

        personaldata_personalcenter = (LinearLayout) findViewById(R.id.personaldata_personalcenter);
        personaldata_personalcenter.setOnClickListener(this);

        personaldata_personalcenter1 = (Button) findViewById(R.id.personaldata_personalcenter1);
        personaldata_personalcenter1.setOnClickListener(this);

        personaldata_personalcenter2 = (Button) findViewById(R.id.personaldata_personalcenter2);
        personaldata_personalcenter2.setOnClickListener(this);

        mooddiary_personalcenter = (LinearLayout) findViewById(R.id.mooddiary_personalcenter);
        mooddiary_personalcenter.setOnClickListener(this);

        mooddiary_personalcenter1 = (Button) findViewById(R.id.mooddiary_personalcenter1);
        mooddiary_personalcenter1.setOnClickListener(this);

        mooddiary_personalcenter2 = (Button) findViewById(R.id.mooddiary_personalcenter2);
        mooddiary_personalcenter2.setOnClickListener(this);

        mooddiary_personalcenter3 = (Button) findViewById(R.id.mooddiary_personalcenter3);
        mooddiary_personalcenter3.setOnClickListener(this);

        problemfeedback_personalcenter = (LinearLayout) findViewById(R.id.problemfeedback_personalcenter);
        problemfeedback_personalcenter.setOnClickListener(this);

        problemfeedback_personalcenter1 = (Button) findViewById(R.id.problemfeedback_personalcenter1);
        problemfeedback_personalcenter1.setOnClickListener(this);

        problemfeedback_personalcenter2 = (Button) findViewById(R.id.problemfeedback_personalcenter2);
        problemfeedback_personalcenter2.setOnClickListener(this);

        arbitarydoor_personalcenter = (LinearLayout) findViewById(R.id.arbitarydoor_personalcenter);
        arbitarydoor_personalcenter.setOnClickListener(this);

        arbitarydoor_personalcenter1 = (Button) findViewById(R.id.arbitarydoor_personalcenter1);
        arbitarydoor_personalcenter1.setOnClickListener(this);

        arbitarydoor_personalcenter2 = (Button) findViewById(R.id.arbitarydoor_personalcenter2);
        arbitarydoor_personalcenter2.setOnClickListener(this);
    }


    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    private void initLinearLayout(LinearLayout bt){
        Animation animation=new AlphaAnimation(1.0f,0.8f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }

    /**
     * 页面跳转回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            //GET_CODE是自定义的页面跳转识别码

            personalcenter_Birthday = data.getStringExtra("getBirthday");
            personalcenter_Sex = data.getStringExtra("getSex");
            personalcenter_Username = data.getStringExtra("getUsername");

            Log.d("initView", "xcnb");
            if (personalcenter_Username==null){
                WaitingAlbee.setText("任意门");
            } else {
                WaitingAlbee.setText(personalcenter_Username);
            }
            if (personalcenter_Birthday!=null){

                personalcenter_Birthday= personalcenter_Birthday.substring(0,4);

                year1 = year-Integer.parseInt(personalcenter_Birthday);
            }
            if (personalcenter_Birthday==null){
                age_personal.setText("0岁");
            } else {
                age_personal.setText( String.valueOf(year1)+"岁");
            }
            if (personalcenter_Sex.equals("男")){
                sex_personal.setImageResource(R.drawable.main_interface5);
            } else {
                sex_personal.setImageResource(R.drawable.main_interface8);
            }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_personalcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_personalcenter);
                initButton(return_personalcenter1);
                finish();
                break;
            case R.id.return_personalcenter1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_personalcenter);
                initButton(return_personalcenter1);
                finish();
                break;
            case R.id.personaldata_personalcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(personaldata_personalcenter);
                initButton(personaldata_personalcenter1);
                initButton(personaldata_personalcenter2);

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
                                    Log.d("TAG", user_person.getCode()+"");
                                    Log.d("TAG", user_person.getData().getSex());
                                    Log.d("TAG", user_person.getData().getTelephone());
                                    Intent intent1 = new Intent(Personalcenter.this, Personaldate_Personalcenter.class);
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
                                    Log.d("TAG", "请求失败");
                                }
                            });
                        }catch (Exception e){e.printStackTrace();}
                    }
                }).start();

                break;
            case R.id.personaldata_personalcenter1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(personaldata_personalcenter);
                initButton(personaldata_personalcenter1);
                initButton(personaldata_personalcenter2);
                Intent intent11 = new Intent(this, Personaldate_Personalcenter.class);
                intent11.putExtra("personalcenter_Birthday",personalcenter_Birthday1);
                intent11.putExtra("personalcenter_Sex",personalcenter_Sex);
                intent11.putExtra("personalcenter_Telephone",personalcenter_Telephone);
                intent11.putExtra("personalcenter_Username",personalcenter_Username);
                showPopupWindow();
                openPopWindow();
                enterHome(intent11);
                break;
            case R.id.personaldata_personalcenter2:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(personaldata_personalcenter);
                initButton(personaldata_personalcenter1);
                initButton(personaldata_personalcenter2);
                Intent intent12 = new Intent(this, Personaldate_Personalcenter.class);
                intent12.putExtra("personalcenter_Birthday",personalcenter_Birthday1);
                intent12.putExtra("personalcenter_Sex",personalcenter_Sex);
                intent12.putExtra("personalcenter_Telephone",personalcenter_Telephone);
                intent12.putExtra("personalcenter_Username",personalcenter_Username);
                showPopupWindow();
                openPopWindow();
                enterHome(intent12);
                break;
            case R.id.mooddiary_personalcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(mooddiary_personalcenter);
                initButton(mooddiary_personalcenter1);
                initButton(mooddiary_personalcenter2);
                initButton(mooddiary_personalcenter3);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            retrofit_mood_list = new Retrofit.Builder()
                                    .baseUrl("http://122.112.141.60:8080/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            moodListService = retrofit_mood_list.create(MoodListService.class);


                            Log.d("TAG", num);
                            Log.d("TAG", "Mood");
                            moodListService.postObjectParamUser(num).enqueue(new Callback<User_mood_list>() {
                                @Override
                                public void onResponse(Call<User_mood_list> call, Response<User_mood_list> response) {
                                    User_mood_list user_mood_list1 =response.body();
                                    Log.d("TAG",user_mood_list1.getCode()+"wuyongle"+user_mood_list1.getMessage()+"132");
                                    Log.d("TAG", user_mood_list1.getData().get(0).getEmotionalStickers());
                                    Log.d("TAG", user_mood_list1.getData().get(0).getDiaryId()+"");
                                    Log.d("TAG", user_mood_list1.getData().get(0).getEmotionalStickers()+"");
                                    Intent intent2 = new Intent(Personalcenter.this, MoonDiary_Personalcenter.class);
                                    Bundle bundle =new Bundle();
                                    bundle.putSerializable("User_mood_list",(Serializable) user_mood_list1.getData());
                                    intent2.putExtras(bundle);
                                    showPopupWindow();
                                    openPopWindow();
                                    Timer time = new Timer();
                                    TimerTask tk = new TimerTask() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            startActivity(intent2); // 这里请求码唯一就可以，我这里是 1
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    popupWindow.dismiss();
                                                }
                                            });
                                        }
                                    };time.schedule(tk, 2000);

                                    Log.d("TAG", "moodlist请求成功");
                                }

                                @Override
                                public void onFailure(Call<User_mood_list> call, Throwable t) {
                                    Log.d("TAG", "请求失败");
                                }
                            });
                        }catch (Exception e){e.printStackTrace();}
                    }
                }).start();

                break;
            case R.id.mooddiary_personalcenter1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(mooddiary_personalcenter);
                initButton(mooddiary_personalcenter1);
                initButton(mooddiary_personalcenter2);
                initButton(mooddiary_personalcenter3);
                Intent intent21 = new Intent(this, MoonDiary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent21);
                break;
            case R.id.mooddiary_personalcenter2:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(mooddiary_personalcenter);
                initButton(mooddiary_personalcenter1);
                initButton(mooddiary_personalcenter2);
                initButton(mooddiary_personalcenter3);
                Intent intent22 = new Intent(this, MoonDiary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent22);
                break;
            case R.id.mooddiary_personalcenter3:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(mooddiary_personalcenter);
                initButton(mooddiary_personalcenter1);
                initButton(mooddiary_personalcenter2);
                initButton(mooddiary_personalcenter3);
                Intent intent23 = new Intent(this, MoonDiary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent23);
                break;
            case R.id.problemfeedback_personalcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(problemfeedback_personalcenter);
                initButton(problemfeedback_personalcenter1);
                initButton(problemfeedback_personalcenter1);
                Intent intent3 = new Intent(this, Problem_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent3);
                break;
            case R.id.problemfeedback_personalcenter1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(problemfeedback_personalcenter);
                initButton(problemfeedback_personalcenter1);
                initButton(problemfeedback_personalcenter1);
                Intent intent31 = new Intent(this, Problem_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent31);
                break;
            case R.id.problemfeedback_personalcenter2:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(problemfeedback_personalcenter);
                initButton(problemfeedback_personalcenter1);
                initButton(problemfeedback_personalcenter1);
                Intent intent32 = new Intent(this, Problem_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent32);
                break;
            case R.id.arbitarydoor_personalcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(arbitarydoor_personalcenter);
                initButton(arbitarydoor_personalcenter1);
                initButton(arbitarydoor_personalcenter2);
                Intent intent4 = new Intent(this, Arbitrary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent4);
                break;
            case R.id.arbitarydoor_personalcenter1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(arbitarydoor_personalcenter);
                initButton(arbitarydoor_personalcenter1);
                initButton(arbitarydoor_personalcenter2);
                Intent intent41 = new Intent(this, Arbitrary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent41);
                break;
            case R.id.arbitarydoor_personalcenter2:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initLinearLayout(arbitarydoor_personalcenter);
                initButton(arbitarydoor_personalcenter1);
                initButton(arbitarydoor_personalcenter2);
                Intent intent42 = new Intent(this, Arbitrary_Personalcenter.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent42);
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
                startActivityForResult(intent, 1); // 这里请求码唯一就可以，我这里是 1
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