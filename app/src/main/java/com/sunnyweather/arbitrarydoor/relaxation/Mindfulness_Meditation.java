package com.sunnyweather.arbitrarydoor.relaxation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextSwitcher;

import com.sunnyweather.arbitrarydoor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mindfulness_Meditation extends AppCompatActivity implements View.OnClickListener,
        Runnable, ServiceConnection, SeekBar.OnSeekBarChangeListener{

    private ImageView mindfulness_playingPlay;
    private boolean isPlaying = true;//0,1 判断是否处于播放状态

    //声明服务
    private static final String TAG = Mindfulness_Meditation.class.getSimpleName();
    private MediaService.MusicController mMusicController;
    //使用方法：mMusicController.play();播放   mMusicController.pause();暂停
    private boolean running;
    private TextSwitcher mSwitcher;
    private SeekBar mindfulness_mSeekBar;
    private Button return_mindfulness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulness__meditation);


        //滑动条部分
        mindfulness_mSeekBar = (SeekBar) findViewById(R.id.mindfulness_music_seek_bar);
        mindfulness_mSeekBar.setOnSeekBarChangeListener(this);
        mSwitcher = (TextSwitcher) findViewById(R.id.mindfulness_text_switcher);
        mSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        Intent intent = new Intent(this, MediaService.class);
        //增加StartService，来增加后台播放功能
        // 解决后台跳转慢的问题
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            pendingIntent.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 绑定服务，使用context来绑定
        // 那个界面需要绑定 就用哪个 Activity
        // 参数1：Intent               代表需要绑定哪一个Service
        // 参数2：ServiceConnection    回调接口，可以接收到Service连接成功和断开的回调，成功就可以取到对象。
        // 绑定服务 参数2就是服务和指定的对象绑定在一起
        bindService(intent, this, BIND_AUTO_CREATE);

        return_mindfulness = (Button) findViewById(R.id.return_mindfulness);
        return_mindfulness.setOnClickListener(this);

        initViews();//定义背景图

    }
    private void initButton(Button bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    private void initViews() {
        mindfulness_playingPlay = (ImageView) findViewById(R.id.mindfulness_playing_play);
        mindfulness_playingPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_mindfulness:
                initButton(return_mindfulness);
                finish();
                break;
            case R.id.mindfulness_playing_play://播放中
                if (isPlaying){
                    playing();
                }else {
                    mMusicController.pause();
                    mindfulness_playingPlay.setImageResource(R.drawable.pause);
                    isPlaying = true;
                }
                break;
            default:
                break;
        }
    }

    private void playing(){
        mindfulness_playingPlay.setImageResource(R.drawable.play);
        mMusicController.play();//播放
        isPlaying = false;
    }
    //===================================播放歌曲服务开启、停止、结束===============================
    @Override
    protected void onStart() {
        super.onStart();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onStop() {
        running = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 解除绑定
        unbindService(this);
        super.onDestroy();
    }

    //---------------------播放到当前音乐的滑动条及时间设置--------------------------
    @Override
    public void run() {
        running = true;
        try {
            while (running) {
                if (mMusicController != null) {
                    long musicDuration = mMusicController.getMusicDuration();
                    final long position = mMusicController.getPosition();
                    final Date dateTotal = new Date(musicDuration);
                    final SimpleDateFormat sb = new SimpleDateFormat("mm:ss");
                    mindfulness_mSeekBar.setMax((int) musicDuration);
                    mindfulness_mSeekBar.setProgress((int) position);
                    mSwitcher.post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Date date = new Date(position);
                                    String time = sb.format(date) + "/" + sb.format(dateTotal);
                                    mSwitcher.setCurrentText(time);
                                }
                            }
                    );
                }

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------
    //服务绑定与解除绑定的回调

    /**
     * 当服务与当前绑定对象，绑定成功，服务onBind方法调用并且返回之后
     * 回调给这个方法
     *
     * @param name
     * @param service IBinder 就是服务 onBind 返回的对象
     */

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMusicController = ((MediaService.MusicController) service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMusicController = null;
    }

    public void btnStopService(View view) {
        Intent intent = new Intent(this, MediaService.class);
        stopService(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMusicController.setPosition(seekBar.getProgress());
    }
}