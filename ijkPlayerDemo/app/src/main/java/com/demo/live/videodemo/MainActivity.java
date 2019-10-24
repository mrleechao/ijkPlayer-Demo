package com.demo.live.videodemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.demo.live.videodemo.media.IRenderView;
import com.demo.live.videodemo.media.IjkVideoView;
import com.demo.live.videodemo.media.PlayerManager;

import tv.danmaku.ijk.media.player.IMediaPlayer;


public class MainActivity extends AppCompatActivity {

    private IjkVideoView mVideoView;
    private PlayerManager player;

    private String url5 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // 初始化播放器
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
//这里使用的是Demo中提供的AndroidMediaController类控制播放相关操作
//        AndroidMediaController     mMediaController = new AndroidMediaController(this, false);
//        mMediaController.setSupportActionBar(actionBar);
//        mVideoView.setMediaController(mMediaController);


        /** 普通播放 start **/
        mVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        mVideoView.setVideoURI(Uri.parse(url5));
        mVideoView.start();
        /** 普通播放 end **/

        //initVideo();
    }

    //使用滑动控制的话解开注释
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (player.gestureDetector.onTouchEvent(event))
//            return true;
//        return super.onTouchEvent(event);
//    }

    /**
     * 可左半屏滑动控制亮度  右半屏控制音量  双击切换比例  （无提示）
     */
    private void initVideo() {
        player = new PlayerManager(this);
        player.setFullScreenOnly(true);
        player.live(true);
        player.setScaleType(PlayerManager.SCALETYPE_WRAPCONTENT);
        player.playInFullScreen(true);
        player.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() {
                Log.e("   player  status    :", "complete");
            }

            @Override
            public void onError() {
                Log.e("   player  status    :", "error");
            }

            @Override
            public void onLoading() {
                Log.e("   player  status    :", "loading");
            }

            @Override
            public void onPlay() {
                Log.e("   player  status    :", "play");
            }
        });
        player.play(url5);
        IjkVideoView videoView = player.getVideoView();
        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                switch (i) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        break;
                }
                return false;

            }
        });
    }
}
