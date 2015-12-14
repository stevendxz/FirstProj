package com.adtis.fistpproj.activity;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.adtis.fistpproj.R;

public class VideoActivity extends Activity {
    private static final String TAG = "VideoActivity";
    private EditText filenameText;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private String filename;//当前播放文件的名称
    private int position;//记录播放位置

    private ImageView btn_play;
    private ImageView btn_pause;
    private ImageView btn_reset;
    private ImageView btn_stop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        this.mediaPlayer = new MediaPlayer();
        this.filenameText = (EditText) this.findViewById(R.id.editText);
        this.surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        btn_play = (ImageView) this.findViewById(R.id.play);
        btn_pause = (ImageView) this.findViewById(R.id.pause);
        btn_reset = (ImageView) this.findViewById(R.id.reset);
        btn_stop = (ImageView) this.findViewById(R.id.stop);
        btn_pause.setVisibility(View.INVISIBLE);
        btn_reset.setVisibility(View.INVISIBLE);
        btn_stop.setVisibility(View.INVISIBLE);

        ButtonClickListener listener = new ButtonClickListener();
        btn_play.setOnClickListener(listener);
        btn_pause.setOnClickListener(listener);
        btn_reset.setOnClickListener(listener);
        btn_stop.setOnClickListener(listener);

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
                    case R.id.play://来自播放按钮
                        filename = filenameText.getText().toString();
                        play();
                        btn_play.setVisibility(View.INVISIBLE);
                        btn_pause.setVisibility(View.VISIBLE);
                        break;

                    case R.id.pause://来自暂停按钮
                        if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
                            mediaPlayer.stop();
                            btn_play.setVisibility(View.INVISIBLE);
                            btn_pause.setVisibility(View.INVISIBLE);
                            btn_reset.setVisibility(View.VISIBLE);
                        } else if (mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            } else {
                                mediaPlayer.start();
                            }
                            btn_play.setVisibility(View.VISIBLE);
                            btn_pause.setVisibility(View.INVISIBLE);
                            btn_reset.setVisibility(View.INVISIBLE);
                        }
                        break;

                    case R.id.reset://来自重新播放按钮
                        if (!mediaPlayer.isPlaying())
                            play();
                        mediaPlayer.seekTo(0);
                        btn_play.setVisibility(View.INVISIBLE);
                        btn_pause.setVisibility(View.VISIBLE);
                        btn_reset.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.stop://来自停止按钮
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        btn_play.setVisibility(View.VISIBLE);
                        btn_pause.setVisibility(View.INVISIBLE);
                        btn_reset.setVisibility(View.INVISIBLE);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * 播放视频
     */
    private void play() throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/" + filename);//设置需要播放的视频
        mediaPlayer.setDisplay(surfaceView.getHolder());//把视频画面输出到SurfaceView
        //mediaPlayer.prepareAsync();
        //mediaPlayer.start();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.prepareAsync();
        //mediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener) this);
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
