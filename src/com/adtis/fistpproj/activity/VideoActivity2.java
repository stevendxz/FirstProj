package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.adtis.fistpproj.R;

public class VideoActivity2 extends Activity {
    private Context context = this;
    private Uri uri;
    private VideoView videoView;
    private ImageView btn_play, btn_stop, btn_previous, btn_next, btn_fullscreen, btn_close;
    private SeekBar seekbar = null;
    private boolean video_playflag = true;
    private boolean video_pauseflag = false;
    private boolean isFullscreen = false;
    private RelativeLayout video_controlbar;

    private static final String TAG = "VideoActivity";
    // 记录当前视频的播放位置
    private int position;

    private ImageView btn_left;
    private ImageView btn_right;
    private NavActivity nav_videoplayer;
    //全屏参数
    private static final int FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
            } else {
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }*/
        setContentView(R.layout.video2_activity);
        initVideoView();
    }

    public void initVideoView() {
        nav_videoplayer = (NavActivity)super.findViewById(R.id.nav_videoplayer);
        nav_videoplayer.setTitle(R.string.videoplay_title);
        btn_left = (ImageView)nav_videoplayer.findViewById(R.id.iv_nav_back);
        btn_right = (ImageView)nav_videoplayer.findViewById(R.id.iv_nav_right);
        btn_left.setImageResource(R.drawable.iconfont_back);
        btn_right.setVisibility(View.INVISIBLE);
        video_controlbar = (RelativeLayout)findViewById(R.id.relativeLayout2);
        nav_videoplayer.setClickCallback(new NavActivity.ClickCallback() {
            @Override
            public void onBackClick() {
                VideoActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/test.mp4");
        videoView = (VideoView)this.findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        btn_play = (ImageView)findViewById(R.id.play);
        btn_stop = (ImageView)findViewById(R.id.stop);
        btn_previous = (ImageView)findViewById(R.id.previous);
        btn_next = (ImageView)findViewById(R.id.next);
        btn_fullscreen = (ImageView)findViewById(R.id.fullscreen);
        btn_close = (ImageView)findViewById(R.id.video_close);

        seekbar = (SeekBar) findViewById(R.id.seekBar);

        ButtonClickListener2 listener = new ButtonClickListener2();
        btn_play.setOnClickListener(listener);
        btn_stop.setOnClickListener(listener);
        btn_previous.setOnClickListener(listener);
        btn_next.setOnClickListener(listener);
        btn_fullscreen.setOnClickListener(listener);
        btn_close.setOnClickListener(listener);
    }

    private class ButtonClickListener2 implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.play://来自播放按钮
                        boolean isPlaying = videoView.isPlaying();
                        if(isPlaying) {
                            video_playflag = false;
                            videoView.pause();
                            btn_play.setImageResource(R.drawable.iconfont_play);
                        } else {
                            video_pauseflag = false;
                            videoView.start();
                            btn_play.setImageResource(R.drawable.iconfont_pause);
                        }
                        seekbar.setMax(videoView.getDuration());
                        UpdateSeekbar2 update = new UpdateSeekbar2();
                        //执行者execute
                        update.execute(1000);
                        seekbar.setOnSeekBarChangeListener(this);
                        break;
                    case R.id.stop://来自停止按钮
                        videoView.seekTo(0);
                        video_playflag = false;
                        videoView.pause();
                        btn_play.setImageResource(R.drawable.iconfont_play);
                        //videoView.stopPlayback();
                        //Toast.makeText(context, "点击了停止按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.previous://来自上一个按钮
                        Toast.makeText(context, "点击了上一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.next://来自下一个按钮
                        Toast.makeText(context, "点击了下一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.fullscreen://来自全屏按钮
                        if(!isFullscreen){//设置RelativeLayout的全屏模式
                            videoFullScreen();
                            videoView.start();
                            isFullscreen = true;//改变全屏/窗口的标记

                        }else{//设置RelativeLayout的窗口模式
                            videoExitFullScreen();

                            isFullscreen = false;//改变全屏/窗口的标记
                        }
                        //Toast.makeText(context, "点击了全屏按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.video_close://来自退出播放按钮
                        videoExitFullScreen();

                        isFullscreen = false;//改变全屏/窗口的标记
                        //Toast.makeText(context, "点击了退出播放按钮", Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Toast.makeText(context, "Tag:"+seekBar.getProgress(), Toast.LENGTH_LONG).show();
            videoView.seekTo(seekBar.getProgress());
        }
    }

    public void videoFullScreen() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        videoView.setLayoutParams(layoutParams);
        btn_fullscreen.setImageResource(R.drawable.iconfont_fullscreenexit);
        btn_close.setVisibility(View.VISIBLE);
    }

    public void videoExitFullScreen() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 240);
        videoView.setLayoutParams(lp);
        btn_fullscreen.setImageResource(R.drawable.iconfont_fullscreen);
        btn_close.setVisibility(View.INVISIBLE);
    }

    private class UpdateSeekbar2 extends AsyncTask<Integer, Integer, String> {//AsyncTask的使用...
        protected void onPostExecute(String result) {
        }

        protected void onProgressUpdate(Integer... progress) {
            seekbar.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            while (video_playflag) {
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                this.publishProgress(videoView.getCurrentPosition());
            }
            return null;
        }
    }
}

