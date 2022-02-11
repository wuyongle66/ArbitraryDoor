package com.sunnyweather.arbitrarydoor.personalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.MainActivity;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.User_login;
import com.sunnyweather.arbitrarydoor.User.User_mood_list;
import com.sunnyweather.arbitrarydoor.User.User_person;
import com.sunnyweather.arbitrarydoor.User.User_update;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.login.Login;
import com.sunnyweather.arbitrarydoor.service.LoginService;
import com.sunnyweather.arbitrarydoor.service.MoodListService;
import com.sunnyweather.arbitrarydoor.service.UpdateService;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Personaldate_Personalcenter extends AppCompatActivity implements View.OnClickListener{

    private static final int VIBRATE_TIME = 3000;
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private String realStr = "";
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private Button return_personaldate;
    private Button return_personaldate1;
    private Button keep_personlcenter;
    private TextView UserID_personalcenter;

    private String personalcenter_Birthday;
    private String personalcenter_Sex;
    private String personalcenter_Telephone;
    private String personalcenter_Username;
    private String num;

    private int resultCode=0;

    Intent xcnb=new Intent();
    private Retrofit retrofit;
    private UpdateService updateService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldate_personalcenter);
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Intent i = getIntent();

        Log.d("TSF",i+"132");
        num = i.getStringExtra("num");
        personalcenter_Birthday = i.getStringExtra("personalcenter_Birthday");
        personalcenter_Sex = i.getStringExtra("personalcenter_Sex");
        personalcenter_Telephone = i.getStringExtra("personalcenter_Telephone");
        personalcenter_Username = i.getStringExtra("personalcenter_Username");

        keep_personlcenter =findViewById(R.id.keep_personlcenter);
        UserID_personalcenter = findViewById(R.id.UserID_personalcenter);
        keep_personlcenter.setOnClickListener(this);
        UserID_personalcenter.setOnClickListener(this);

        UserID_personalcenter.setText("User ID: "+personalcenter_Telephone);
        et1 = (EditText) findViewById(R.id.name_edittext_personalcenter);

        et2 = (EditText) findViewById(R.id.sex_edittext_personalcenter);

        et3 = (EditText) findViewById(R.id.birthday_edittext_personalcenter);

        et4 = (EditText) findViewById(R.id.phone_edittext_personalcenter);
        et4.addTextChangedListener(new MyTextWatcher());//隐藏中间4位

        if (personalcenter_Username==null)
        {
            et1.setText("任意门");
        }
        else{
            et1.setText(personalcenter_Username);
        }

        if (personalcenter_Sex.equals("男"))
        {
            et2.setText("男");
        }
        else{
            et2.setText("女");
        }

        if (personalcenter_Birthday==null)
        {
            et3.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
        }
        else{
            et3.setText(personalcenter_Birthday);
        }

            et4.setText(personalcenter_Telephone);



        return_personaldate = (Button) findViewById(R.id.return_personaldate);
        return_personaldate.setOnClickListener(this);

        return_personaldate1 = (Button) findViewById(R.id.return_personaldate1);
        return_personaldate1.setOnClickListener(this);
        init1();
        init2();
        init3();
        init4();
    }


    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_personaldate:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_personaldate);
                initButton(return_personaldate1);
                setResult(resultCode,xcnb);
                finish();
                break;
            case R.id.return_personaldate1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_personaldate);
                initButton(return_personaldate1);
                setResult(resultCode,xcnb);
                finish();
                break;
            case R.id.keep_personlcenter:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(keep_personlcenter);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            retrofit = new Retrofit.Builder()
                                    .baseUrl("http://122.112.141.60:8080/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            updateService = retrofit.create(UpdateService.class);

                            User_update user_update = new User_update();
                            Log.d("nb",et1.getText().toString().trim());
                            Log.d("nb1",et2.getText().toString().trim());
                            Log.d("nb2",et3.getText().toString().trim());

                            Log.d("nb3",num);
                            user_update.setUsername(et1.getText().toString().trim());
                            user_update.setSex(et2.getText().toString().trim());
                            user_update.setBirthday(et3.getText().toString().trim());

                            Log.d("nb5",user_update.getUsername());
                            Log.d("nb5",user_update.getBirthday());
                            Log.d("nb5",user_update.getSex());
                            updateService.update(num,user_update).enqueue(new Callback<User_update>() {
                                @Override
                                public void onResponse(Call<User_update> call, Response<User_update> response) {
                                    Log.d("TAG",response.body().getMessage());
                                    User_update user_update1=response.body();
                                    xcnb.putExtra("getUsername",user_update.getUsername());
                                    xcnb.putExtra("getBirthday",user_update.getBirthday());
                                    xcnb.putExtra("getSex",user_update.getSex());

                                    Log.d("xcnb1",et1.getText().toString().trim()+"+"+et2.getText().toString().trim()+"+"
                                            +et3.getText().toString().trim());
                                    Toast.makeText(Personaldate_Personalcenter.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    resultCode=1;
                                }

                                @Override
                                public void onFailure(Call<User_update> call, Throwable t) {
                                    Log.d("TAG", "修改失败");
                                }
                            });
                        }catch (Exception e){e.printStackTrace();}
                    }
                }).start();
                break;
        }
    }

    public void init1(){
        et1.postDelayed(new Runnable() {
            @Override
            public void run() {
                et1.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(et1,0);
            }
        },1000);

        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    public void init2(){
        et2.postDelayed(new Runnable() {
            @Override
            public void run() {
                et1.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(et1,0);
            }
        },1000);

        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    public void init3(){
        et3.postDelayed(new Runnable() {
            @Override
            public void run() {
                et1.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(et1,0);
            }
        },1000);

        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    public void init4(){
        et4.postDelayed(new Runnable() {
            @Override
            public void run() {
                et1.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(et1,0);
            }
        },1000);

        et4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    class MyTextWatcher implements TextWatcher {
        private String displayStr = "";//显示在editext上的字符串

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().trim().equals(displayStr.trim())){//这个判断一定要写不然要陷入死循环（et.setText会触发onTextChanged我又在onTextChanged里使用了setText方法）
                System.out.println("字符重复了！！！！！！！！");
                return;
            }
            System.out.println("start="+start+" before="+before+"  count="+count);
            System.out.println("s="+s);
            if(before > 0){
                String delStr = realStr.substring(start, start+before);
                System.out.println("删除的字符串="+delStr);
                String result = realStr.substring(0, start)+realStr.substring(start+before);
                System.out.println("删除后result="+result);
                realStr = result;
            }
            if(count>0){
                CharSequence tmp = s.subSequence(start, start+count);
                System.out.println("增加的sub str ="+tmp);
                StringBuilder sb = new StringBuilder(realStr);
                sb.insert(start, tmp);
                realStr = sb.toString();
                //realStr += tmp;
            }

            System.out.println("realStr= "+realStr+ "---length="+realStr.length());

            displayStr = getDisplayStr(realStr);
            String old = et4.getText().toString().trim();
            System.out.println("old ="+old+" displayStr = "+displayStr);
            et4.setText(displayStr);
            et4.setSelection(displayStr.length());
            System.out.println("displayStr = "+displayStr);
            System.out.println("------------------------------------------");
        }
        /**
         获取用于显示的带星花的字符，不提交后台
         */
        private String getDisplayStr(String realStr) {
            String result = new String(realStr);
            char[] cs = result.toCharArray();
            for(int i = 0;i < cs.length;i++){
                if(i >= 3&& i <= 6){//把3和6区间的字符隐藏
                    cs[i] = '*';
                }
            }
            return new String(cs);
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            /*System.out.println("---afterTextChanged---");
             */
        }
    }

}