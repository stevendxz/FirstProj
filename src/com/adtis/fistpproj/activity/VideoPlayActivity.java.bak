package com.adtis.fistpproj.activity;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.adtis.fistpproj.R;

import java.io.File;

public class VideoPlayActivity extends Activity {
    private EditText nameText;
    private String path;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private boolean pause;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplay_activity);

        mediaPlayer = new MediaPlayer(); 
       /*取得文件名称*/
        nameText = (EditText) this.findViewById(R.id.filename);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        //把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区 
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //取得holder对象,设置控制类型,SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS,这个类型的作用:
        //把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区 
        surfaceView.getHolder().setFixedSize(176, 144);
        //设置显示视频的分辨率 
        surfaceView.getHolder().setKeepScreenOn(true);
        //让屏幕不要暗下去,锁屏,也就是在播放的时候,屏幕一直亮着. 
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        //这里通过surfaceView.getHolder().addCallback这个方法就可以监听到surfaceView的创建状态
        //当surfaceView被创建出来的时候就会调用SurfaceCallback类的surfaceCreated这个方法:
    } 
    /* 
     * 当这个activity不在前台的时候调用这个方法onPause,停止视频播放 
     * 当这个activity回到前台的时候调用这个方法onResume,继续播放视频 
     * 但是这里出现了一个问题,在播放的时候,只有声音没有画面,这是由于surfaceView需要处理造成的 
     * 这个问题的原因:当surfaceView所在的activity离开了前台surfaceView会被摧毁会被destory 
     * 当activity又重新回到前台时候,surfaceView会被重新创建,surfaceView是在onResume()方法之后被 
     * 创建,由于surfaceView是在onResume方法之后创建的所以没有画面只有声音. 
     * 
     * */

    // SurfaceCallback通过这个类实现
    private final class SurfaceCallback implements Callback{
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }//surfaceChanged这个方法是在绘图区域发生变化的时候自动调用这个方法
        //当被创建的时候如果存在播放点就进入到播放点进行播放
        public void surfaceCreated(SurfaceHolder holder) {
            if(position>0 && path!=null){
                play(position);
                position = 0;
            }
        }
        //当surfaceView所在的activity离开了前台surfaceView会被摧毁会被destory
        //这时候先要记录下播放位置,在创建的 时候surfaceView继续播放
        public void surfaceDestroyed(SurfaceHolder holder) {
            if(mediaPlayer.isPlaying()){
                position = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }
    }

    //只要activity不在前台就会调用这个方法 
    //如果只希望当来电话的时候,停止播放,挂电话的时候继续播放,这时候就不可以用这两个方法,因为只要这个activity不在前台 
    //那么就会选择性的调用这两个方法.即:当activity回到前台时候调用onResume,开始播放,当activity回到后台的时候调用onPause停止播放 
    @Override
    protected void onPause() {//停止
        if(mediaPlayer.isPlaying()) {
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        super.onPause();
    }
    //当activity重新回到前台的时候,这个方法就会必然调用,继续播放
    //如果只希望当来电话的时候,停止播放,挂电话的时候继续播放,这时候就不可以用这两个方法,因为只要这个activity不在前台 
    //那么就会选择性的调用这两个方法.即:当activity回到前台时候调用onResume,开始播放,当activity回到后台的时候调用onPause停止播放 
    @Override
    protected void onResume() {
        if(position>0 && path!=null){
            play(position);
            position = 0;
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }
    /*按钮响应的方法,这个方法不用添加按钮监听,只要在配置文件中配置好就可以了*/
    public void mediaplay(View v){
        switch (v.getId()) {
            case R.id.playbutton:
                /**
                 * 如果需要播放网络上的视频,就需要在清单文件中加上网络访问权限.
                 * 另外直接这样播放网络上的视频这样是不行的,因为这个文件在网络上
                 * 还不是流媒体文件,是不可以直接播放的
                 * 下面是解决方法:
                 * 流媒体的分发方式,渐进式下载(Progressive Download)和实时流媒体
                 * 渐进式下载(Progressive Download)可以通过HTTP或FTP协议来分发,需要web服务器或者是Ftp服务器
                 * 实时流媒体通过RTP和RTSP这种实时协议来分发,需要一个流媒体服务器.
                 * 注意,渐进式媒体文是和媒体的格式有关的,mp4不是渐进式的媒体文件.需要转换成其他格式的比如
                 * 3gp才可以..注意,这里在转换格式的时候一般都需要设置数据速率,这里的数据速率一般要设置的
                 * 和自己的网速差不多的时候最好了.太快或者太慢都不好,但是如果这里设置的速率超过网络的带宽就会
                 * 在android系统中播放不出来.也不要太低,太低了,视频的效果会很差建议在160---230之间
                 * 图像的大小设置,没有关系,因为可以放大缩小.转换完成后直接放到webroot的目录下然后在应用中输入网络地址
                 * 就可以直接播放了.
                 * --------------------------------------------
                 * 在以前的时候,是通过把一个大的视频切割成很多小的视频,然后把一个很小的视频先下载到本地然后播放
                 * 然后实现类似于视频在线播放的功能,其实也是本地转换.
                 * ----------------------------------------------
                 * 在显示企业中如果需要开发视频播放的时候,是不可能手动实现转码的,这时候可以借用quickTime提供的一套api来实现.
                 * -------------------------------------
                 */
                String filename = nameText.getText().toString();
                Toast.makeText(this, filename, Toast.LENGTH_LONG).show();

                //这个时候,是指需要播放网络视频
                if(filename.startsWith("http")){
                    path = filename;
                    play(0);
                }else{
                    //如果只需要播放本地文件的话,就直接写这个就可以了.
                    File file = new File(Environment.getExternalStorageDirectory(), filename);
                    if(file.exists()){
                        path = file.getAbsolutePath();
                        Toast.makeText(this, path, Toast.LENGTH_LONG).show();
                        mediaPlayer.start();
                        //play(0);
                    }else{
                        path = null;
                        Toast.makeText(this, R.string.filenoexsit, Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.pausebutton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pause = true;
                }else{
                    if(pause){
                        mediaPlayer.start();
                        pause = false;
                    }
                }
                break;

            case R.id.resetbutton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0);
                }else{
                    if(path!=null){
                        play(0);
                    }
                }
                break;
            case R.id.stopbutton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                break;
        }
    }

    private void play(int position) {
        try {
            mediaPlayer.reset();
            //path在上面定义并赋值完后在这里使用
            mediaPlayer.setDataSource(path);
            //这里需要把设置后的surfaceView的控制对象Holder进来.
            mediaPlayer.setDisplay(surfaceView.getHolder());
            //这个方法用来设置往activity的哪个地方显示视频,根据surfacView的设置情况
            mediaPlayer.prepare();//缓冲
            mediaPlayer.setOnPreparedListener(new PrepareListener(position));
            //mediaPlayer.setOnPreparedListener这个是用来监听缓冲是否完成的方法,缓冲结束后会自动的调用onPrepared这个方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //缓冲是否完成的监听类
    private final class PrepareListener implements OnPreparedListener{
        private int position;

        public PrepareListener(int position) {
            this.position = position;
        }

        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();//播放视频
            if(position>0) mediaPlayer.seekTo(position);
        }
    }
} 