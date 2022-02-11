package com.sunnyweather.arbitrarydoor.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.personalcenter.Personaldate_Personalcenter;

import java.util.Timer;
import java.util.TimerTask;

public class Register_success extends AppCompatActivity implements View.OnClickListener{

    private DYLoadingView dy1;
    private View contentView;
    private PopupWindow popupWindow;
    private String realStr = "";
    private TextView register_successs_phone;
    private Button return_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        Intent i = getIntent();
        Log.d("Max", "12 "+i.getStringExtra("date"));
        String phone=i.getStringExtra("date");
        phone = phone.substring(0, 5) + "******";
        register_successs_phone = findViewById(R.id.register_successs_phone);

        register_successs_phone.setText("任意门用户："+phone);
        register_successs_phone.addTextChangedListener(new MyTextWatcher());//隐藏后6位

        return_login = findViewById(R.id.return_login);
        return_login.setOnClickListener(this);
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.3f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_login:
                initButton(return_login);
                Intent intent1 = new Intent(Register_success.this, Login.class);
                showPopupWindow();
                openPopWindow();
                enterHome(intent1);
                break;
        }
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
            String old = register_successs_phone.getText().toString().trim();
            System.out.println("old ="+old+" displayStr = "+displayStr);
            register_successs_phone.setText(displayStr);
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
                if(i >= 5&& i <= 10){//把5和10区间的字符隐藏
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