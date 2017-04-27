package com.pbtd.mobile.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.presenter.play.PlayContract;
import com.pbtd.mobile.presenter.play.PlayPresenter;

public class PlayActivity extends BaseActivity implements PlayContract.View{

    public static final String PRODUCT_CODE = "PRODUCT_CODE";

    private String mProduct_code = "";
    private PlayPresenter mPresenter;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mProduct_code = getIntent().getStringExtra(PRODUCT_CODE);
        mPresenter = new PlayPresenter(this, this);
        mPresenter.getPlayInfo(mProduct_code);
        initVideoView();
    }

    private void initVideoView() {
        ImageView imageview = (ImageView) findViewById(R.id.imageview);

        videoView = (VideoView) findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        imageview.setOnClickListener((v -> {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            videoView.setLayoutParams(layoutParams);
        }));
    }

    @Override
    public void showPlayInfo(PlayInfoModel playInfoModel) {
        videoView.setVideoPath(playInfoModel.programList.get(0).movieList.get(0).movieUrl);
        videoView.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
