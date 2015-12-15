package com.adtis.fistpproj.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.adtis.fistpproj.R;

public class VideoActivity extends Activity {
    private static final String TAG = "VideoActivity";
    private final Context context = this;
    //private EditText filenameText;
    private TextView videoTitle;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private String filename = "test.mp4";//当前播放文件的名称
    private int position;//记录播放位置

    private ImageView btn_play, btn_stop, btn_previous, btn_next, btn_fullscreen, btn_close, btn_videoback;
    private SeekBar seekbar = null;
    private boolean playflag = true;
    private boolean pauseflag = false;
    private boolean isFullscreen = false;
    private RelativeLayout video_controlbar;

    //处理进度条更新
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //更新进度
                    int position = mediaPlayer.getCurrentPosition();

                    int time = mediaPlayer.getDuration();
                    int max = seekbar.getMax();

                    seekbar.setProgress(position * max / time);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        this.mediaPlayer = new MediaPlayer();
        this.surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        btn_play = (ImageView) this.findViewById(R.id.video_play);
        btn_stop = (ImageView) this.findViewById(R.id.video_stop);
        btn_previous = (ImageView) this.findViewById(R.id.video_previous);
        btn_next = (ImageView) this.findViewById(R.id.video_next);
        btn_fullscreen = (ImageView) this.findViewById(R.id.video_fullscreen);
        btn_close = (ImageView) this.findViewById(R.id.video_close);
        btn_videoback = (ImageView) this.findViewById(R.id.video_back);

        seekbar = (SeekBar) this.findViewById(R.id.video_seekBar);
        video_controlbar = (RelativeLayout)findViewById(R.id.video_relativeLayout2);

        ButtonClickListener listener = new ButtonClickListener();
        btn_play.setOnClickListener(listener);
        btn_stop.setOnClickListener(listener);
        btn_previous.setOnClickListener(listener);
        btn_next.setOnClickListener(listener);
        btn_fullscreen.setOnClickListener(listener);
        btn_close.setOnClickListener(listener);
        btn_videoback.setOnClickListener(listener);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //手动调节进度
                // TODO Auto-generated method stub
                int dest = seekBar.getProgress();
                int time = mediaPlayer.getDuration();
                int max = seekBar.getMax();

                mediaPlayer.seekTo(time * dest / max);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub

            }
        });

        /*下面设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前*/
        this.surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.surfaceView.getHolder().setFixedSize(176, 144);//设置分辨率
        this.surfaceView.getHolder().setKeepScreenOn(true);
        this.surfaceView.getHolder().addCallback(new SurfaceListener());
    }


    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.video_play://来自播放按钮
                        seekbar.setMax(mediaPlayer.getDuration());
                        boolean isPlay = mediaPlayer.isPlaying();
                        if (isPlay) {
                            playflag = false;
                            btn_play.setImageResource(R.drawable.iconfont_play);
                            mediaPlayer.pause();
                        } else {
                            pauseflag = false;
                            btn_play.setImageResource(R.drawable.iconfont_pause);
                            play();
                        }
                        break;
                    case R.id.video_stop://来自停止按钮
                        mediaPlayer.seekTo(0);
                        playflag = false;
                        btn_play.setImageResource(R.drawable.iconfont_play);
                        mediaPlayer.pause();
                        break;
                    case R.id.video_previous://来自上一个按钮
                        Toast.makeText(context, "点击了上一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.video_next://来自下一个按钮
                        Toast.makeText(context, "点击了下一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.video_fullscreen://来自全屏按钮
                        isFullscreen = !isFullscreen;
                        if (isFullscreen) {
                            videoFullScreen();
                            isFullscreen = true;
                        } else {
                            videoExitFullScreen();
                            isFullscreen = false;
                        }
                        break;
                    case R.id.video_close://来自退出播放按钮
                        videoExitFullScreen();
                        mediaPlayer.stop();

                        isFullscreen = false;//改变全屏/窗口的标记
                        break;
                    case R.id.video_back://来自退出播放按钮
                        videoExitFullScreen();
                        mediaPlayer.stop();
                        isFullscreen = false;//改变全屏/窗口的标记
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    public void updateSeekBar() {
        final int milliseconds = 100;
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(milliseconds);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    public void videoFullScreen() {
        /*params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        surfaceView.setLayoutParams(layoutParams);
        btn_fullscreen.setImageResource(R.drawable.iconfont_fullscreenexit);
        btn_close.setVisibility(View.VISIBLE);
    }

    public void videoExitFullScreen() {
        /*WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(params);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 240);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        surfaceView.setLayoutParams(lp);
        btn_fullscreen.setImageResource(R.drawable.iconfont_fullscreen);
        btn_close.setVisibility(View.INVISIBLE);
    }

    /**
     * 播放视频
     */
    private void play() throws IOException {
        //mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/" + filename);//设置需要播放的视频
        mediaPlayer.setDisplay(surfaceView.getHolder());//把视频画面输出到SurfaceView
        //mediaPlayer.prepareAsync();
        //mediaPlayer.start();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                updateSeekBar();
            }
        });
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener) this);
    }

    private class SurfaceListener implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {//方法在onResume()后被调用
            Log.i(TAG, "surfaceCreated()");
            if (position > 0 && filename != null) {
                try {
                    play();
                    mediaPlayer.seekTo(position);
                    position = 0;
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "surfaceDestroyed()");
        }
    }

    @Override
    protected void onPause() {//当其他Activity被打开，停止播放
        if (mediaPlayer.isPlaying()) {
            position = mediaPlayer.getCurrentPosition();//得到播放位置
            mediaPlayer.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

}
