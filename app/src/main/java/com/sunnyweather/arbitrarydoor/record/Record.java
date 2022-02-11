package com.sunnyweather.arbitrarydoor.record;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyweather.arbitrarydoor.DYLoadingView;
import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.User.User_mood;
import com.sunnyweather.arbitrarydoor.User.User_person;
import com.sunnyweather.arbitrarydoor.User.User_update;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.personalcenter.Personalcenter;
import com.sunnyweather.arbitrarydoor.personalcenter.Personaldate_Personalcenter;
import com.sunnyweather.arbitrarydoor.service.MoodDiaryService;
import com.sunnyweather.arbitrarydoor.service.PersonService;
import com.sunnyweather.arbitrarydoor.service.UpdateService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Record extends AppCompatActivity implements View.OnClickListener {

    private Retrofit retrofit;
    private MoodDiaryService moodDiaryService;
    private User_mood user_mood;
    private View contentView;
    private PopupWindow popupWindow;
    private RelativeLayout record_keep_background;
    private RelativeLayout record_keep_button;
    private ImageView record_keep_pen;
    private TextView record_keep_text;
    private final int VIBRATE_TIME = 10000;

    Intent xcnb = new Intent();
    private Button keep;
    private Button return1;

    private ImageButton commemoration;
    private ImageButton travel;
    private ImageButton overtime;
    private ImageButton crossed_love;
    private ImageButton hobby;
    private ImageButton sleep_record;
    private ImageButton food;
    private ImageButton read;
    private ImageButton sport;

    private ImageView happy;
    private ImageView meet;
    private ImageView sleepy;
    private ImageView doubt;
    private ImageView puzzle;

    private EditText textView_record;
    int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;

    int num=1;
    String num_record;
    private ImageSpan span_sport;
    private SpannableString spannableString_sport;
    int resultcode=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        Intent i = getIntent();
        num_record = i.getStringExtra("num_record");
        textView_record = (EditText) findViewById(R.id.textView_record);
        textView_record.setOnClickListener(this);

        init1();
        keep = (Button) findViewById(R.id.record_keep);
        keep.setOnClickListener(this);
        return1 = (Button) findViewById(R.id.record_return);
        return1.setOnClickListener(this);

        meet = (ImageView) findViewById(R.id.meet);
        meet.setOnClickListener(this);
        sleepy = (ImageView) findViewById(R.id.sleepy);
        sleepy.setOnClickListener(this);
        doubt = (ImageView) findViewById(R.id.doubt);
        doubt.setOnClickListener(this);
        puzzle = (ImageView) findViewById(R.id.puzzle);
        puzzle.setOnClickListener(this);
        happy = (ImageView) findViewById(R.id.happy);
        happy.setOnClickListener(this);

        commemoration = (ImageButton) findViewById(R.id.commemoration);
        commemoration.setOnClickListener(this);
        travel = (ImageButton) findViewById(R.id.travel);
        travel.setOnClickListener(this);
        overtime = (ImageButton) findViewById(R.id.overtime);
        overtime.setOnClickListener(this);
        crossed_love = (ImageButton) findViewById(R.id.crossed_love);
        crossed_love.setOnClickListener(this);
        hobby = (ImageButton) findViewById(R.id.hobby);
        hobby.setOnClickListener(this);
        sleep_record = (ImageButton) findViewById(R.id.sleep_record);
        sleep_record.setOnClickListener(this);
        food = (ImageButton) findViewById(R.id.food);
        food.setOnClickListener(this);
        read = (ImageButton) findViewById(R.id.read);
        read.setOnClickListener(this);
        sport = (ImageButton) findViewById(R.id.sport);
        sport.setOnClickListener(this);
    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    private void initImage(ImageView bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    private void initImageButton(ImageButton bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commemoration:
                initImageButton(commemoration);
                if (num<=3) {
                    Bitmap bitmap_commemoration = BitmapFactory.decodeResource(getResources(),
                            R.drawable.record2);
//定义一个可输入EditText的字符串对象
                    SpannableString spannableString_commemoration = new SpannableString("@");
//将可输入的EditText框替换成位图对象
                    ImageSpan span_commemoration = new ImageSpan(Record.this, bitmap_commemoration);
                    spannableString_commemoration.setSpan(span_commemoration, 0, 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView_record.append(spannableString_commemoration);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.travel:
                initImageButton(travel);
                if (num<=3){
                Bitmap bitmap_travel = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record3);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_travel = new SpannableString("#");
//将可输入的EditText框替换成位图对象
                ImageSpan span_travel = new ImageSpan(Record.this,bitmap_travel);
                spannableString_travel.setSpan(span_travel, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_travel);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.overtime:
                initImageButton(overtime);
                if (num<=3){
                Bitmap bitmap_overtime = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record4);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_overtime = new SpannableString("$");
//将可输入的EditText框替换成位图对象
                ImageSpan span_overtime = new ImageSpan(Record.this,bitmap_overtime);
                spannableString_overtime.setSpan(span_overtime, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_overtime);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.crossed_love:
                initImageButton(crossed_love);
                if (num<=3){
                Bitmap bitmapcrossed_love = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record5);
//定义一个可输入EditText的字符串对象
                SpannableString spannableStringcrossed_love = new SpannableString("%");
//将可输入的EditText框替换成位图对象
                ImageSpan spancrossed_love = new ImageSpan(Record.this,bitmapcrossed_love);
                spannableStringcrossed_love.setSpan(spancrossed_love, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableStringcrossed_love);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.hobby:
                initImageButton(hobby);
                if (num<=3){
                Bitmap bitmap_hobby = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record6);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_hobby = new SpannableString("^");
//将可输入的EditText框替换成位图对象
                ImageSpan span_hobby = new ImageSpan(Record.this,bitmap_hobby);
                spannableString_hobby.setSpan(span_hobby, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_hobby);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sleep_record:
                initImageButton(sleep_record);
                if (num<=3){
                Bitmap bitmap_sleep = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record7);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_sleep = new SpannableString("&");
//将可输入的EditText框替换成位图对象
                ImageSpan span_sleep = new ImageSpan(Record.this,bitmap_sleep);
                spannableString_sleep.setSpan(span_sleep, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_sleep);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.food:
                initImageButton(food);
                if (num<=3){
                Bitmap bitmap_food = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record8);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_food = new SpannableString("*");
//将可输入的EditText框替换成位图对象
                ImageSpan span_food = new ImageSpan(Record.this,bitmap_food);
                spannableString_food.setSpan(span_food, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_food);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.read:
                initImageButton(read);
                if (num<=3){
                Bitmap bitmap_read = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record9);
//定义一个可输入EditText的字符串对象
                SpannableString spannableString_read = new SpannableString("(");
//将可输入的EditText框替换成位图对象
                ImageSpan span_read = new ImageSpan(Record.this,bitmap_read);
                spannableString_read.setSpan(span_read, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_read);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sport:
                initImageButton(sport);
                if (num<=3){
                Bitmap bitmap_sport = BitmapFactory.decodeResource(getResources(),
                        R.drawable.record10);
//定义一个可输入EditText的字符串对象
                spannableString_sport = new SpannableString(")");
//将可输入的EditText框替换成位图对象
                 span_sport = new ImageSpan(Record.this,bitmap_sport);
                spannableString_sport.setSpan(span_sport, 0, 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_record.append(spannableString_sport);
                    num++;
                } else{
                    Toast.makeText(this,"事件最多添加三个",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.record_keep:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(keep);

                String edit;
                edit = textView_record.getText().toString().trim();
                containsEmoji(edit);

                showPopupWindow();
                openPopWindow();
                break;
            case R.id.record_return:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initButton(return1);


                    setResult(resultcode, xcnb);

                finish();
                break;
            case R.id.meet:
                initImage(meet);
                a++;
                if (a%2==0){
                    meet.setImageResource(R.drawable.return1);
                }else {
                    meet.setImageResource(R.drawable.return1_down);
                    sleepy.setImageResource(R.drawable.return2);
                    doubt.setImageResource(R.drawable.return3);
                    happy.setImageResource(R.drawable.return4);
                    puzzle.setImageResource(R.drawable.return5);
                    b=0;
                    c=0;
                    d=0;
                    e=0;
                }
                break;
            case R.id.sleepy:
                initImage(sleepy);
                b++;
                if (b%2==0){
                    sleepy.setImageResource(R.drawable.return2);
                }else {
                    meet.setImageResource(R.drawable.return1);
                    sleepy.setImageResource(R.drawable.return2_down);
                    doubt.setImageResource(R.drawable.return3);
                    happy.setImageResource(R.drawable.return4);
                    puzzle.setImageResource(R.drawable.return5);
                    a=0;
                    c=0;
                    d=0;
                    e=0;
                }
                break;
            case R.id.doubt:
                initImage(doubt);
                c++;
                if (c%2==0){
                    doubt.setImageResource(R.drawable.return3);
                }else {
                    meet.setImageResource(R.drawable.return1);
                    sleepy.setImageResource(R.drawable.return2);
                    doubt.setImageResource(R.drawable.return3_down);
                    happy.setImageResource(R.drawable.return4);
                    puzzle.setImageResource(R.drawable.return5);
                    b=0;
                    a=0;
                    d=0;
                    e=0;
                }
                break;
            case R.id.happy:
                initImage(happy);
                d++;
                if (d%2==0){
                    happy.setImageResource(R.drawable.return4);
                }else {
                    meet.setImageResource(R.drawable.return1);
                    sleepy.setImageResource(R.drawable.return2);
                    doubt.setImageResource(R.drawable.return3);
                    happy.setImageResource(R.drawable.return4_down);
                    puzzle.setImageResource(R.drawable.return5);
                    b=0;
                    c=0;
                    a=0;
                    e=0;
                }
                break;
            case R.id.puzzle:
                initImage(puzzle);
                e++;
                if (e%2==0){
                    puzzle.setImageResource(R.drawable.return5);
                }else {
                    meet.setImageResource(R.drawable.return1);
                    sleepy.setImageResource(R.drawable.return2);
                    doubt.setImageResource(R.drawable.return3);
                    happy.setImageResource(R.drawable.return4);
                    puzzle.setImageResource(R.drawable.return5_down);
                    b=0;
                    c=0;
                    d=0;
                    a=0;
                }
                break;
        }
    }
    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public void containsEmoji(String source) {
        user_mood = new User_mood();
        List<String> lists = new ArrayList<>();
        List<String> lists_text = new ArrayList<>();
        Log.d("Record",source);
        int len = source.length();
        for (int i = 0; i < 3; i++) {
            char codePoint = source.charAt(i);
            if (codePoint=='@'){
                lists.add("#纪念日# ");
            }else
            if (codePoint=='#'){
                lists.add("#旅行# ");
            }
            else
            if (codePoint=='$'){
                lists.add("#加班# ");
            }
            else
            if (codePoint=='%'){
                lists.add("#失恋# ");
            }
            else
            if (codePoint=='^'){
                lists.add("#爱好# ");
            }
            else
            if (codePoint=='&'){
                lists.add("#睡觉# ");
            }
            else
            if (codePoint=='*'){
                lists.add("#美食# ");
            }
            else
            if (codePoint=='('){
                lists.add("#阅读# ");
            }
            else
            if (codePoint==')'){
                lists.add("#运动# ");
            }
            else{
                String code1=codePoint+"";
                lists_text.add(code1);
            }
        }
        String joinList = TextUtils.join("", lists);
        for(int i=3;i<source.length();i++){
            char codePoint = source.charAt(i);
            String code=codePoint+"";
            lists_text.add(code);
        }
        String joinList_text = TextUtils.join("",lists_text);

        if (a==1){
            user_mood.setEmotionalStickers("满足");
        }
        else
        if (b==1){
            user_mood.setEmotionalStickers("犯困");
        }
        else
        if (c==1){
            user_mood.setEmotionalStickers("疑惑");
        }
        else
        if (d==1){
            user_mood.setEmotionalStickers("开心");
        }
        else
        if (e==1){
            user_mood.setEmotionalStickers("迷茫");
        }
        user_mood.setEventRecord(joinList);
        user_mood.setTextRecord(joinList_text);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    retrofit = new Retrofit.Builder()
                            .baseUrl("http://122.112.141.60:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    moodDiaryService = retrofit.create(MoodDiaryService.class);

                    Log.d("Record",user_mood.getEmotionalStickers());
                    Log.d("Record",user_mood.getEventRecord());
                    Log.d("Record",user_mood.getTextRecord());
                    Log.d("Record",num_record);
                    moodDiaryService.postObjectParamUser(user_mood,num_record).enqueue(new Callback<User_mood>() {
                        @Override
                        public void onResponse(Call<User_mood> call, Response<User_mood> response) {
                            User_mood user_mood1=response.body();

                            Log.d("TAG", "User_mood请求成功");
                            xcnb.putExtra("getEmotionalStickers",user_mood.getEmotionalStickers());
                            a=0;b=0;c=0;d=0;e=0;

                            meet.setImageResource(R.drawable.return1);
                            sleepy.setImageResource(R.drawable.return2);
                            doubt.setImageResource(R.drawable.return3);
                            happy.setImageResource(R.drawable.return4);
                            puzzle.setImageResource(R.drawable.return5);
                            textView_record.setText("");

                            resultcode=1;
                        }

                        @Override
                        public void onFailure(Call<User_mood> call, Throwable t) {
                            Log.d("TAG", "User_mood请求失败");
                        }
                    });
                }catch (Exception e){e.printStackTrace();}
            }
        }).start();

    }



    public void init1(){
        textView_record.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView_record.requestFocus();
                InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(textView_record,0);
            }
        },1000);

        textView_record.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE));
                    if (manager != null) manager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
    private void showPopupWindow(){
        //加载popupwindow布局
        contentView = LayoutInflater.from(Record.this).inflate(
                R.layout.activity_record_keep, null);

        //设置各控件的点击事件
        RelativeLayout record_keep_background = (RelativeLayout) contentView.findViewById(R.id.record_keep_background);
        record_keep_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        RelativeLayout record_keep_button = (RelativeLayout) contentView.findViewById(R.id.record_keep_button);
        record_keep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ImageView record_keep_pen = (ImageView) contentView.findViewById(R.id.record_keep_pen);
        record_keep_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        TextView record_keep_text = (TextView) contentView.findViewById(R.id.record_keep_text);
        record_keep_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
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

}