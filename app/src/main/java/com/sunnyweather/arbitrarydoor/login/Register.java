package com.sunnyweather.arbitrarydoor.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mob.MobSDK;
import com.sunnyweather.arbitrarydoor.User.User_register;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.service.RegisterService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;
import static com.mob.wrappers.SMSSDKWrapper.getSupportedCountries;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private EditText name_edittext_register;//手机号
    private EditText send_edittext_register;//输入验证码
    private Button send;//获取验证码
    private EditText pwd_edittext_register;
    private Button register_sure;//注册

    private Retrofit retrofit;
    private RegisterService registerService;

    private final String TAG="--MainActivity--";
    private TimerTask tt;
    private Timer tm;
    //你们自己的
    private final String appKey="m34155e6b858d8";
    private final String appSercret="c32cc3c10897b6ba94dcd1cd44cb3302";
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.arg1){
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    //Toast.makeText(Register.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //获取验证码成功
                    //Toast.makeText(Register.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private boolean isChange;
    private boolean tag=true;
    public String country="86";
    private int i=60;
    private static final int CODE_REPEAT=1;
    private String sphone;
    private String scode;
    private EventHandler eh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MobSDK.init(this,appKey,appSercret);
        initviews();
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);
                        Log.d(TAG, "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Message msg = new Message();
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                        Log.d(TAG, "获取验证码成功");
                    }
                } else {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = "验证失败";
                    handler.sendMessage(msg);
                    Log.d(TAG, "验证失败");
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }
    private void initviews() {
        name_edittext_register = findViewById(R.id.name_edittext_register);
        name_edittext_register.setOnClickListener(this);
        send_edittext_register = findViewById(R.id.send_edittext_register);
        send_edittext_register.setOnClickListener(this);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        pwd_edittext_register = findViewById(R.id.pwd_edittext_register);
        pwd_edittext_register.setOnClickListener(this);
        register_sure = findViewById(R.id.register_sure);
        register_sure.setOnClickListener(this);
        init2();
        init3();
        init1();
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.3f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_edittext_register:
                break;
            case R.id.send_edittext_register:
                break;
            case R.id.send:
                sphone = name_edittext_register.getText().toString();
                if (sphone.equals("")) {
                    Toast.makeText(Register.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //填写了手机号
                    if (isMobileNO(sphone)) {
                        //如果手机号码无误，则发送验证请求
                        send.setClickable(true);
                        changeBtnGetCode();
                        getSupportedCountries();
                        getVerificationCode("86", sphone);
                    } else {
                        //手机号格式有误
                        Toast.makeText(Register.this, "手机号格式错误，请检查", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.pwd_edittext_register:
                break;
            case R.id.register_sure:
                //多线程

                initButton(register_sure);
                scode = send_edittext_register.getText().toString();

                if (name_edittext_register.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd_edittext_register.getText().toString().equals("")) {
                        Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (pwd_edittext_register.getText().toString().length() < 8) {
                        Toast.makeText(Register.this, "密码至少需要8位", Toast.LENGTH_SHORT).show();
                    } else {
                        if (scode.equals("")) {
                            Toast.makeText(Register.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            submitVerificationCode("86", sphone, scode);
//                            Intent intent1 = new Intent(Register.this, Register_success.class);
//                            intent1.putExtra("date",name_edittext_register.getText().toString().trim());
//                            showPopupWindow();
//                            openPopWindow();
//                            enterHome(intent1);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        retrofit = new Retrofit.Builder()
                                                .baseUrl("http://122.112.141.60:8080/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        registerService = retrofit.create(RegisterService.class);

                                        User_register user_register = new User_register();
                                        user_register.setPsd(pwd_edittext_register.getText().toString().trim());
                                        user_register.setTelephone(name_edittext_register.getText().toString().trim());
                                        Log.d(TAG, "12 " + user_register.getTelephone());
                                        Log.d(TAG, "13 " + user_register.getPsd());
                                        registerService.postObjectParamUser(user_register).enqueue(new Callback<User_register>() {
                                            @Override
                                            public void onResponse(Call<User_register> call, Response<User_register> response) {

                                                User_register user_register1 = response.body();
                                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                Intent intent1 = new Intent(Register.this, Register_success.class);
                                                intent1.putExtra("date", user_register.getTelephone());
                                                showPopupWindow();
                                                openPopWindow();
                                                enterHome(intent1);
                                            }

                                            @Override
                                            public void onFailure(Call<User_register> call, Throwable t) {
                                                Log.d(TAG, "注册失败" + t.getMessage());

                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                    break;
                }
        }
    }
    public void init1(){
        name_edittext_register.postDelayed(new Runnable() {
            @Override
            public void run() {
                name_edittext_register.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(name_edittext_register,0);
            }
        },1001);

        name_edittext_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        send_edittext_register.postDelayed(new Runnable() {
            @Override
            public void run() {
                send_edittext_register.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(send_edittext_register,0);
            }
        },100);

        send_edittext_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pwd_edittext_register.postDelayed(new Runnable() {
            @Override
            public void run() {
                pwd_edittext_register.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(pwd_edittext_register,0);
            }
        },100);

        pwd_edittext_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    //当发送验证码成功时，按钮样式变成倒计时
    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (Register.this == null) {
                            break;
                        }
                        Register.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                send.setText("获取(" + i + ")");
                                send.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (Register.this != null) {
                    Register.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            send.setText("发送");
                            send.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }
    private boolean isMobileNO(String sphone) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        //"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(sphone))
            return false;
        else
            return sphone.matches(telRegex);

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
                        finish();
                    }
                });
            }
        };time.schedule(tk, 2000);

    }
}