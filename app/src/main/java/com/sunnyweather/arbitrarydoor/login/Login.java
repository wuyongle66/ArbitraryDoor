package com.sunnyweather.arbitrarydoor.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.MainActivity;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.User_login;
import com.sunnyweather.arbitrarydoor.User.User_register;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.personalcenter.Personalcenter;
import com.sunnyweather.arbitrarydoor.service.LoginService;
import com.sunnyweather.arbitrarydoor.service.RegisterService;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private final int VIBRATE_TIME = 3000;
    private Button login;
    private ImageView user_photo;
    private EditText name_edittext_login;
    private EditText pwd_edittext_login;
    private Button register;

    private final String TAG="--LoginActivity--";
    private Retrofit retrofit;
    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        user_photo = findViewById(R.id.user_photo);
        user_photo.setOnClickListener(this);
        pwd_edittext_login = findViewById(R.id.pwd_edittext_login);
        pwd_edittext_login.setOnClickListener(this);
        name_edittext_login = findViewById(R.id.name_edittext_login);
        name_edittext_login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        init2();
        init1();
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.3f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                initButton(login);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            retrofit = new Retrofit.Builder()
                                    .baseUrl("http://122.112.141.60:8080/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            loginService = retrofit.create(LoginService.class);

                            User_login user_login = new User_login();
                            user_login.setPsd(name_edittext_login.getText().toString().trim());
                            user_login.setTelephone(pwd_edittext_login.getText().toString().trim());
                            Log.d(TAG, "12 "+user_login.getTelephone());
                            Log.d(TAG, "13 "+user_login.getPsd());
                            loginService.postObjectParamUser(user_login).enqueue(new Callback<User_login>() {
                                @Override
                                public void onResponse(Call<User_login> call, Response<User_login> response) {
                                    User_login user_login1=response.body();
                                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();

                                    Log.d(TAG, "登录成功");
                                    Intent intent1 = new Intent(Login.this, MainActivity.class);
                                    intent1.putExtra("date",user_login1.getData().getToken());
                                    intent1.putExtra("date1",user_login1.getData().getTokenHead());
                                    showPopupWindow();
                                    openPopWindow();
                                    enterHome(intent1);
                                }

                                @Override
                                public void onFailure(Call<User_login> call, Throwable t) {
                                    Log.d(TAG, "登录失败");
                                }
                            });
                        }
                        catch (Exception e){e.printStackTrace();}
                    }
                }).start();

                break;
            case R.id.user_photo:
                break;
            case R.id.name_edittext_login:
                break;
            case R.id.pwd_edittext_login:
                break;
            case R.id.register:
                initButton(register);
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                Intent intent1 = new Intent(this, Register.class);
                showPopupWindow();
                openPopWindow();
                enterHome1(intent1);
                break;
        }
    }
    public void init2(){
        pwd_edittext_login.postDelayed(new Runnable() {
            @Override
            public void run() {
                pwd_edittext_login.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(pwd_edittext_login,0);
            }
        },100);

        pwd_edittext_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    public void init1(){
        name_edittext_login.postDelayed(new Runnable() {
            @Override
            public void run() {
                name_edittext_login.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(name_edittext_login,0);
            }
        },1000);

        name_edittext_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                } else {

                }
            }
        });
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
        popupWindow=new PopupWindow(contentView,getWindowManager().getDefaultDisplay().getWidth(),height*6/5);

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
    private void enterHome1(Intent intent){
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
                        finish();
                    }
                });
            }
        };time.schedule(tk, 2000);

    }
}