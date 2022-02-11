package com.sunnyweather.arbitrarydoor.relaxation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextSwitcher;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Relaxation_Two extends AppCompatActivity implements View.OnClickListener,
        Runnable, ServiceConnection, SeekBar.OnSeekBarChangeListener{

    private static final int VIBRATE_TIME = 3000;
    private ImageView breathe_disc,breathe_needle,breathe_playingPre,breathe_playingPlay,breathe_playingNext;
    private boolean isPlaying = true;//0,1 判断是否处于播放状态

    //声明服务
    private static final String TAG = Relaxation_Two.class.getSimpleName();
    private Breathe_MediaService.MusicController1 mMusicController;
    //使用方法：mMusicController.play();播放   mMusicController.pause();暂停
    private boolean running;
    private TextSwitcher mSwitcher;
    private SeekBar breathe_mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relaxation_two);


        //滑动条部分
        breathe_mSeekBar = (SeekBar) findViewById(R.id.breathe_music_seek_bar);
        breathe_mSeekBar.setOnSeekBarChangeListener(this);
        mSwitcher = (TextSwitcher) findViewById(R.id.breathe_text_switcher);
        mSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        Intent intent = new Intent(this, Breathe_MediaService.class);
        //增加StartService，来增加后台播放功能
        startService(intent);
        // 绑定服务，使用context来绑定
        // 那个界面需要绑定 就用哪个 Activity
        // 参数1：Intent               代表需要绑定哪一个Service
        // 参数2：ServiceConnection    回调接口，可以接收到Service连接成功和断开的回调，成功就可以取到对象。
        // 绑定服务 参数2就是服务和指定的对象绑定在一起
        bindService(intent, this, BIND_AUTO_CREATE);

        Button return_relaxation_two = (Button) findViewById(R.id.return_relaxation_two);
        return_relaxation_two.setOnClickListener(this);


        breathe_playingPlay = (ImageView) findViewById(R.id.breathe_playing_play);
        breathe_playingPlay.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_relaxation_two:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                finish();
                break;
            case R.id.breathe_playing_play://播放中
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                if (isPlaying){
                    playing();
                }else {
                    mMusicController.pause();
                    breathe_playingPlay.setImageResource(R.drawable.pause);
                    isPlaying = true;
                }
                break;
            default:
                break;
        }
    }

    private void playing(){
        breathe_playingPlay.setImageResource(R.drawable.play);
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
                    breathe_mSeekBar.setMax((int) musicDuration);
                    breathe_mSeekBar.setProgress((int) position);
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
        mMusicController = ((Breathe_MediaService.MusicController1) service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMusicController = null;
    }

    public void btnStopService(View view) {
        Intent intent = new Intent(this, Breathe_MediaService.class);
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