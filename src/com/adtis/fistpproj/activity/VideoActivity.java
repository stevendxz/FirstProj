package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import com.adtis.fistpproj.R;

/**
 * Created by Administrator on 2015/12/11.
 */
public class VideoActivity extends Activity implements View.OnClickListener {
    public Button playButton;
    private VideoView videoView;
    private Uri videoUri;

    private int mPositionWhenPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        init();
        playButton = (Button) this.findViewById(R.id.btn_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoView.isPlaying()) {
                    videoView.start();
                } else {
                    videoView.pause();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        videoView.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mPositionWhenPaused = videoView.getCurrentPosition();
        videoView.stopPlayback();
        Log.d("MSG:", "OnStop: mPositionWhenPaused = " + mPositionWhenPaused);
        Log.d("MSG:", "OnStop: getDuration  = " + videoView.getDuration());
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(mPositionWhenPaused >= 0) {
            videoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }
        super.onResume();
    }

    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }

    public void init() {
        //默认播放视频横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView = (VideoView) this.findViewById(R.id.videoView);
        videoView.setMediaController(new MediaController(this));
        videoUri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/test.mp4");
        Log.v("URI:",Environment.getExternalStorageDirectory().getPath());
        videoView.setVideoURI(videoUri);
        //videoView.start();
    }
}
