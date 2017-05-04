package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pbtd.mobile.R;

public class PlayLandActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String POSITION = "position";

    private VideoView mVideoView;
    private String mUrl;
    private int mPosition;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_land);

        mUrl = getIntent().getStringExtra(URL);
        mPosition = getIntent().getIntExtra(POSITION, 0);
        initView();
    }

    private void initView() {
        ImageView mBackView = (ImageView) findViewById(R.id.iv_back);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setOnPreparedListener((mp) -> {
            mp.start();
            mVideoView.seekTo(mPosition);
        });
        mVideoView.setOnErrorListener((mp, what, extra) -> false);

        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(mUrl);
        mVideoView.start();

        mBackView.setOnClickListener((back_view) -> {
            Intent intent = new Intent();
            intent.putExtra(POSITION, mVideoView.getCurrentPosition());
            setResult(RESULT_OK, intent);
            PlayLandActivity.this.finish();
        });
    }

}
