package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.adtis.fistpproj.R;

public class VideoActivity2 extends Activity {
    private Context context = this;
    private Uri uri;
    private VideoView videoView;
    private ImageView btn_play, btn_stop, btn_previous, btn_next, btn_fullscreen;
    private SeekBar seekbar = null;
    private boolean playflag = true;
    private boolean pauseflag = false;

    private static final String TAG = "VideoActivity";
    // 记录当前视频的播放位置
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video2_activity);
        initVideoView();
    }

    public void initVideoView() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/test.mp4");
        videoView = (VideoView)this.findViewById(R.id.videoView);
        //videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        //videoView.start();
        //videoView.requestFocus();
        btn_play = (ImageView)findViewById(R.id.play);
        btn_stop = (ImageView)findViewById(R.id.stop);
        btn_previous = (ImageView)findViewById(R.id.previous);
        btn_next = (ImageView)findViewById(R.id.next);
        btn_fullscreen = (ImageView)findViewById(R.id.fullscreen);

        seekbar = (SeekBar) findViewById(R.id.seekBar);

        ButtonClickListener listener = new ButtonClickListener();
        btn_play.setOnClickListener(listener);
        btn_stop.setOnClickListener(listener);
        btn_previous.setOnClickListener(listener);
        btn_next.setOnClickListener(listener);
        btn_fullscreen.setOnClickListener(listener);
    }

    private class ButtonClickListener implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.play://来自播放按钮
                        boolean isPlaying = videoView.isPlaying();
                        if(isPlaying) {
                            videoView.pause();
                            btn_play.setImageResource(R.drawable.iconfont_play);
                        } else {
                            videoView.start();
                            //btn_play.setBackgroundResource(R.drawable.iconfont_pause);
                            btn_play.setImageResource(R.drawable.iconfont_pause);
                        }
                        seekbar.setMax(videoView.getDuration());
                        UpdateSeekbar update = new UpdateSeekbar();
                        //执行者execute
                        update.execute(1000);
                        seekbar.setOnSeekBarChangeListener(this);
                        break;
                    case R.id.stop://来自停止按钮
                        videoView.seekTo(0);
                        videoView.pause();
                        btn_play.setImageResource(R.drawable.iconfont_play);
                        //videoView.stopPlayback();
                        Toast.makeText(context, "点击了停止按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.previous://来自上一个按钮
                        Toast.makeText(context, "点击了上一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.next://来自下一个按钮
                        Toast.makeText(context, "点击了下一个按钮", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.fullscreen://来自全屏按钮
                        Toast.makeText(context, "点击了全屏按钮", Toast.LENGTH_LONG).show();
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

    private class UpdateSeekbar extends AsyncTask<Integer, Integer, String> {//AsyncTask的使用...
        protected void onPostExecute(String result) {
        }

        protected void onProgressUpdate(Integer... progress) {
            seekbar.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            while (playflag) {
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

