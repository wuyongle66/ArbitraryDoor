package com.sunnyweather.arbitrarydoor.personalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

public class Problem_Personalcenter extends AppCompatActivity implements View.OnClickListener{

    private static final int VIBRATE_TIME = 3000;

    private EditText singleInput;
    private EditText problemfeedback;

    private Button return_problem;
    private Button return_problem1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_personalcenter);
        return_problem = (Button) findViewById(R.id.return_problem);
        return_problem.setOnClickListener(this);
        return_problem1 = (Button) findViewById(R.id.return_problem1);
        return_problem1.setOnClickListener(this);

        singleInput = (EditText) findViewById(R.id.single_input_problem);
        problemfeedback = (EditText) findViewById(R.id.problem_feedback);

        init1();
        init2();
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_problem:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_problem);
                initButton(return_problem1);
                finish();
                break;
            case R.id.return_problem1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_problem);
                initButton(return_problem1);
                finish();
                break;
        }
    }
    public void init1(){
        singleInput.postDelayed(new Runnable() {
            @Override
            public void run() {
                singleInput.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(singleInput,0);
            }
        },1000);

        singleInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        problemfeedback.postDelayed(new Runnable() {
            @Override
            public void run() {
                problemfeedback.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(problemfeedback,0);
            }
        },1000);

        problemfeedback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
}