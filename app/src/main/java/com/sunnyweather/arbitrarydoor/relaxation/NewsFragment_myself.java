package com.sunnyweather.arbitrarydoor.relaxation;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunnyweather.arbitrarydoor.R;
import com.sunnyweather.arbitrarydoor.VibrateHelp;
import com.sunnyweather.arbitrarydoor.music.LrcView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by cg on 2015/10/21.
 */
public class NewsFragment_myself extends Fragment implements View.OnClickListener,Runnable, ServiceConnection, SeekBar.OnSeekBarChangeListener {
    private static final int VIBRATE_TIME = 3000;
    private ImageView disc,needle,playingPre,playingPlay,playingNext,return_relaxation_myself1,single_cycle;
    private ObjectAnimator discAnimation,needleAnimation;//自定义指针和唱盘
    private boolean isPlaying = true;//0,1 判断是否处于播放状态

    private LrcView lrcView;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();

    //声明服务
    private static final String TAG = NewsFragment_myself.class.getSimpleName();
    private MediaService.MusicController mMusicController;
    //使用方法：mMusicController.play();播放   mMusicController.pause();暂停
    private boolean running;
    private TextSwitcher mSwitcher;
    private SeekBar mSeekBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_relaxation_myself1, container, false);
        return_relaxation_myself1 = (ImageView) view.findViewById(R.id.return_relaxation_myself1);
        return_relaxation_myself1.setOnClickListener(this);
        return_relaxation_myself1.setImageResource(R.drawable.return_relaxation_myself);

        single_cycle = (ImageView) view.findViewById(R.id.single_cycle);
        single_cycle.setImageResource(R.drawable.single_cycle);
        //滑动条部分
        mSeekBar = (SeekBar) view.findViewById(R.id.music_seek_bar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSwitcher = (TextSwitcher) view.findViewById(R.id.text_switcher);
        mSwitcher.setInAnimation(getContext(), android.R.anim.fade_in);
        mSwitcher.setOutAnimation(getContext(), android.R.anim.fade_out);

        Intent intent = new Intent(getContext(), MediaService.class);
        //增加StartService，来增加后台播放功能
        getActivity().startService(intent);
        // 绑定服务，使用context来绑定
        // 那个界面需要绑定 就用哪个 Activity
        // 参数1：Intent               代表需要绑定哪一个Service
        // 参数2：ServiceConnection    回调接口，可以接收到Service连接成功和断开的回调，成功就可以取到对象。
        // 绑定服务 参数2就是服务和指定的对象绑定在一起
        getActivity().bindService(intent, this, BIND_AUTO_CREATE);

        //歌词部分
        lrcView = view.findViewById(R.id.lrc_view);
        playingPlay = view.findViewById(R.id.playing_play);
        try {
            mediaPlayer.reset();
            AssetFileDescriptor fileDescriptor = getActivity().getAssets().openFd("send_it.m4a");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mSeekBar.setMax(mediaPlayer.getDuration());
                mSeekBar.setProgress(0);
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                lrcView.updateTime(0);
                mSeekBar.setProgress(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载歌词文本
        String mainLrcText = getLrcText("send_it_en.lrc");
        String secondLrcText = getLrcText("send_it_cn.lrc");
        lrcView.loadLrc(mainLrcText, secondLrcText);


        lrcView.setDraggable(true,(view1,time) -> {
            mediaPlayer.seekTo((int) time);
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();
                handler.post(runnable);
            }
            return true;
        });

        lrcView.setOnTapListener((view1,x,y) -> {
            Toast.makeText(getActivity(), "点击歌词", Toast.LENGTH_SHORT).show();
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });

        //指针和唱片部分

        init(view);
        setAnimations();
        return view;
    }

    public void init(View view){
        playingPre = (ImageView) view.findViewById(R.id.playing_pre);
        playingPlay = (ImageView) view.findViewById(R.id.playing_play);
        playingNext = (ImageView) view.findViewById(R.id.playing_next);
        disc = (ImageView) view.findViewById(R.id.disc);
        needle = (ImageView) view.findViewById(R.id.needle);
        playingPre.setImageResource(R.drawable.last_music);
        playingPlay.setImageResource(R.drawable.pause);
        playingNext.setImageResource(R.drawable.next_music);
        disc.setImageResource(R.drawable.album);
        needle.setImageResource(R.drawable.needle);
        playingPre.setOnClickListener(this);
        playingPlay.setOnClickListener(this);
        playingNext.setOnClickListener(this);
    }
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                mSeekBar.setProgress((int) time);
            }
            handler.postDelayed(this, 300);
        }
    };

    private void setAnimations() {
        discAnimation = ObjectAnimator.ofFloat(disc, "rotation", 0, 360);
        discAnimation.setDuration(10000);
        discAnimation.setInterpolator(new LinearInterpolator());
        discAnimation.setRepeatCount(ValueAnimator.INFINITE);

        needleAnimation = ObjectAnimator.ofFloat(needle, "rotation", 0, -30);
        needle.setPivotX(0);
        needle.setPivotY(0);
        needleAnimation.setDuration(500);
        needleAnimation.setInterpolator(new LinearInterpolator());
    }
    private void initImageView(ImageView bt){
        Animation animation=new AlphaAnimation(1.0f,0.5f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (v.getId()){
            case R.id.return_relaxation_myself1:
                VibrateHelp.vSimple(v.getContext(),VIBRATE_TIME);
                initImageView(return_relaxation_myself1);
                getActivity().finish();
                break;
            case R.id.playing_pre://前一曲
                if (discAnimation != null) {
                    discAnimation.end();
                    playing();
                }
                break;
            case R.id.playing_play://播放中
                if (isPlaying){
                    playing();
                }else {
                    if (needleAnimation != null) {
                        needleAnimation.reverse();
                        needleAnimation.end();
                        mMusicController.pause();
                    }
                    if (discAnimation != null && discAnimation.isRunning()) {
                        discAnimation.cancel();
                        mMusicController.pause();
                        float valueAvatar = (float) discAnimation.getAnimatedValue();
                        discAnimation.setFloatValues(valueAvatar, 360f + valueAvatar);
                    }
                    playingPlay.setImageResource(R.drawable.pause);
                    isPlaying = true;
                }
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
                break;
            case R.id.playing_next://下一曲
                if (discAnimation != null) {
                    discAnimation.end();
                    playing();
                }
                break;
            default:
                break;

        }
    }
    //播放：1、播放音乐 2、动画旋转 3、暂停图片切换为播放按钮图片
    private void playing(){
        needleAnimation.start();
        discAnimation.start();
        playingPlay.setImageResource(R.drawable.play);
        mMusicController.play();//播放
        isPlaying = false;
    }
    //===================================播放歌曲服务开启、停止、结束===============================

    @Override
    public void onStart() {
        super.onStart();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void onStop() {
        running = false;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 解除绑定
        getActivity().unbindService(this);
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
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
        Intent intent = new Intent(getActivity(), MediaService.class);
        getActivity().stopService(intent);
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
                    mSeekBar.setMax((int) musicDuration);
                    mSeekBar.setProgress((int) position);
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
}