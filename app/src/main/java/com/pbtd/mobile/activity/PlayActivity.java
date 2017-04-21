package com.pbtd.mobile.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pbtd.mobile.R;
import com.pbtd.mobile.UIUtil;
import com.pbtd.mobile.widget.YSMediaPlayer;

public class PlayActivity extends AppCompatActivity {

    private YSMediaPlayer mVideoView;
    private static final String TEST_URL = "http://vod.dispatcher.gitv.boss12580.com/gitv/388694800/388694800/2.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        LinearLayout ll_root = (LinearLayout) findViewById(R.id.ll_root);

        String play_url = getIntent().getStringExtra("video_id");

        mVideoView = new YSMediaPlayer(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.convertDpToPixel(this, 210));
        ll_root.addView(mVideoView, layoutParams);
        mVideoView.setPlayUrl(TEST_URL);
        mVideoView.setPlayName("测试视频");
        mVideoView.start();
        mVideoView.getVideoView().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mVideoView.getVideoView().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer arg0) {
                PlayActivity.this.finish();
            }
        });

        mVideoView.getVideoView().setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                System.out.println(what + "");
                return false;
            }
        });

        mVideoView.getVideoView().setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                System.out.println(what + "");
                return false;
            }
        });

    }
}
