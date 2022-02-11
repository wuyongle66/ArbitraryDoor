package com.sunnyweather.arbitrarydoor.personalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

public class Arbitrary_Personalcenter extends AppCompatActivity implements View.OnClickListener{

    private static final int VIBRATE_TIME = 3000;
    private Button updata_arbitrary;
    private Button use_arbitrary;
    private Button return_arbitrary;
    private Button return_arbitrary1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_arbitrary_personalcenter);
        return_arbitrary = (Button) findViewById(R.id.return_arbitrary);
        return_arbitrary.setOnClickListener(this);

        return_arbitrary1 = (Button) findViewById(R.id.return_arbitrary1);
        return_arbitrary1.setOnClickListener(this);
        updata_arbitrary = (Button) findViewById(R.id.updata_arbitrary);
        updata_arbitrary.setOnClickListener(this);

        use_arbitrary = (Button) findViewById(R.id.use_arbitrary);
        use_arbitrary.setOnClickListener(this);


    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);//持续时间0.3s
        bt.startAnimation(animation);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_arbitrary:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_arbitrary);
                initButton(return_arbitrary1);
                finish();
                break;
            case R.id.return_arbitrary1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return_arbitrary1);
                initButton(return_arbitrary1);
                finish();
                break;
            case R.id.updata_arbitrary:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(updata_arbitrary);
                finish();
                break;
            case R.id.use_arbitrary:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(use_arbitrary);
                finish();
                break;
        }
    }
}