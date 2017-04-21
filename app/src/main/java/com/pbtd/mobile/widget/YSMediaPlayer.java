package com.pbtd.mobile.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pbtd.mobile.R;

import java.util.Timer;
import java.util.TimerTask;


public class YSMediaPlayer extends RelativeLayout {
	private final int PROGRESS_BAR_ID = 0x1000001;
	private final static int MSG_WHAT_PROGRESS = 2000001;
	private Context mContext;
    private CustomVideoView videoView;
	private ProgressBar progressBar;
    private ResolutionUtil resolutionUtil;
    private Timer timer;
    private ImageView playImg;
    private RelativeLayout progressBarLayout;
    private TextView playTimeText;
    private TextView countTimeText;
    private TextView nameText;
    private int barShowTime = 0;
    private int position = 0;
    private boolean isUpdateProgress = true;
	int lastPosition = 0;

    private Handler mHandler = new Handler(){
    	@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case MSG_WHAT_PROGRESS:
                if(videoView != null && videoView.isPlaying()){
                    setProgress2();
                }

				if(barShowTime > 10){
					progressBarLayout.setVisibility(View.GONE);
                    nameText.setVisibility(View.GONE);
					barShowTime = 0;
				}
				break;

			default:
				break;
			}

		}

    };

	public YSMediaPlayer(Context context) {
		super(context);
		this.mContext = context;
		resolutionUtil = new ResolutionUtil(context);
		initView();
		startTimerTask();
	}

	private void initView() {
        createVideoView();
        LayoutParams layoutParams;

        createPlayBtn();

        createProgressLayout();

        createNameText();


//		 bar.setIndeterminateDrawable(ctx.getResources().getDrawable(R.drawable.progress_bar));//进度条前进时候的背景
//		 bar.setMinimumHeight(3);
	}

    private void createNameText() {
        nameText = new TextView(mContext);
        nameText.setVisibility(View.INVISIBLE);
        addView(nameText);
        nameText.setTextColor(Color.WHITE);
        nameText.setTextSize(resolutionUtil.px2sp2px(24));
        LayoutParams layoutParams = (LayoutParams) nameText.getLayoutParams();
        layoutParams.topMargin = resolutionUtil.px2dp2pxHeight(20);
        layoutParams.leftMargin = resolutionUtil.px2dp2pxWidth(20);
    }

    @SuppressWarnings("ResourceType")
    private void createProgressLayout() {
        LayoutParams layoutParams;
        progressBarLayout = new RelativeLayout(mContext);
        progressBarLayout.setVisibility(View.GONE);
        addView(progressBarLayout);
        progressBarLayout.setBackgroundColor(getResources().getColor(R.color.black));
        layoutParams = (LayoutParams) progressBarLayout.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = resolutionUtil.px2dp2pxHeight(60);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        progressBar = new ProgressBar(mContext,null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(PROGRESS_BAR_ID);
        progressBarLayout.addView(progressBar);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_style));

        layoutParams = (LayoutParams) progressBar.getLayoutParams();
        layoutParams.width = resolutionUtil.px2dp2pxWidth(600);
        layoutParams.height = resolutionUtil.px2dp2pxHeight(5);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);


        playTimeText = new TextView(mContext);
        progressBarLayout.addView(playTimeText);
        playTimeText.setText("00:00:00");
        playTimeText.setTextColor(Color.WHITE);
        playTimeText.setTextSize(resolutionUtil.px2sp2px(20));
        layoutParams = (LayoutParams) playTimeText.getLayoutParams();
        layoutParams.addRule(RelativeLayout.LEFT_OF, PROGRESS_BAR_ID);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.rightMargin = resolutionUtil.px2dp2pxWidth(140);


        countTimeText = new TextView(mContext);
        progressBarLayout.addView(countTimeText);
        countTimeText.setText("00:00:00");
        countTimeText.setTextColor(Color.WHITE);
        countTimeText.setTextSize(resolutionUtil.px2sp2px(20));
        layoutParams = (LayoutParams) countTimeText.getLayoutParams();
        layoutParams.addRule(RelativeLayout.RIGHT_OF, PROGRESS_BAR_ID);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.leftMargin = resolutionUtil.px2dp2pxWidth(140);
    }

    private void createPlayBtn() {
        LayoutParams layoutParams;
        playImg = new ImageView(mContext);
        playImg.setVisibility(View.GONE);
        playImg.setImageResource(R.drawable.play);
        addView(playImg);
        layoutParams = (LayoutParams) playImg.getLayoutParams();
        layoutParams.width = resolutionUtil.px2dp2pxWidth(100);
        layoutParams.height = resolutionUtil.px2dp2pxWidth(100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    }

    private void createVideoView() {
        videoView = new CustomVideoView(mContext);
        addView(videoView);

        LayoutParams layoutParams = (LayoutParams) videoView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;

    }

    public void setPlayUrl(String url){
		videoView.setVideoURI(Uri.parse(url));
	}

    public void setPlayName(String name){
        nameText.setText(name);
    }
	
	public void start(int position){
        this.position = position;
		videoView.start();

		playImg.setVisibility(View.GONE);
	}


    public void start(){
        videoView.start();
        playImg.setVisibility(View.GONE);
    }

	public void pause(){
		
		videoView.pause();
		playImg.setVisibility(View.VISIBLE);
	}
	
	public void stop(){
//		timer.cancel();
		isUpdateProgress = false;
		videoView.stopPlayback();
	}
	
	public void setProgress(){
		progressBar.setMax(videoView.getDuration());
		progressBar.setProgress(lastPosition);
		playTimeText.setText(getTimeToString(lastPosition));
		countTimeText.setText(getTimeToString(videoView.getDuration()));
	}

	public void setProgress2(){
		progressBar.setMax(videoView.getDuration());
		progressBar.setProgress(videoView.getCurrentPosition());
		playTimeText.setText(getTimeToString(videoView.getCurrentPosition()));
		countTimeText.setText(getTimeToString(videoView.getDuration()));
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if(event.getAction() == KeyEvent.ACTION_DOWN){
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_DPAD_CENTER:



                return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if(videoView.isPlaying()){
					barShowTime = 0;
					progressBarLayout.setVisibility(View.VISIBLE);
					lastPosition = lastPosition - 10000;
					if(lastPosition < 0){
						lastPosition = 0;
					}

					setProgress();
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if(videoView.isPlaying()){
					barShowTime = 0;
					progressBarLayout.setVisibility(View.VISIBLE);
					lastPosition = lastPosition + 10000;
					if(lastPosition > videoView.getDuration()){
						lastPosition = videoView.getDuration();
					}
					setProgress();
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_UP:
				
				break;

            case KeyEvent.KEYCODE_MENU:

                break;

			default:
				break;
			}
			
		}else if(event.getAction() == KeyEvent.ACTION_UP){
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					if(videoView.isPlaying()){
						videoView.seekTo(lastPosition);

					}
					return true;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if(videoView.isPlaying()){
						barShowTime = 0;
						videoView.seekTo(lastPosition);
					}
					return true;
				default:
					return true;
			}

		}else{
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	
	class ResolutionUtil {
		
		public static final int WINDOWS_STANDARD_SIZE_WIDTH = 1280;
		public static final int WINDOWS_STANDARD_SIZE_HIGH = 720;
		
		private static final float DEFAULTDENSITY = 160;
		
		private static final float DEFAULTFONTDESITY = 1.0F;
		
		private float density;
		
		private float fontDesity;
		
		private float scale;
		
		private int deviceWidth;

		private int deviceHeight;
		
		private float scaleWidth ;
		
		private float scaleHeight;
		
		public ResolutionUtil(Context ctx){
			DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
			this.deviceWidth  = dm.widthPixels;
			this.deviceHeight = dm.heightPixels;
			density = dm.densityDpi;
			fontDesity = dm.scaledDensity;
//			if(deviceWidth > deviceHeight){
//				scaleWidth = (float)deviceWidth / WINDOWS_STANDARD_SIZE_HIGH;
//				scaleHeight = (float)deviceHeight / WINDOWS_STANDARD_SIZE_WIDTH;
//			}else{
				scaleWidth = (float)deviceWidth / WINDOWS_STANDARD_SIZE_WIDTH;
				scaleHeight = (float)deviceHeight / WINDOWS_STANDARD_SIZE_HIGH;
//			}


		}
		
		public int getWidth(){
			return deviceWidth;
		}
		
		public int getHeight(){
			return deviceHeight;
		}
		
		public int px2dp2pxWidth(float pxVlaue){
			float dp = pxVlaue / (density / DEFAULTDENSITY);
			return (int) (dp * (density / DEFAULTDENSITY) * scaleWidth);
				
		}
		
		public int px2dp2pxHeight(float pxVlaue){
			float dp = pxVlaue / (density / DEFAULTDENSITY);
			return (int) (dp * (density / DEFAULTDENSITY) * scaleHeight);
		}
		
		public int px2sp2px(float spVlaue){
			float dp = spVlaue / fontDesity;
			int px = (int) (dp * (fontDesity / DEFAULTFONTDESITY) / fontDesity * scaleWidth);
			return px;
		}
		
	    public int dip2px(float dipValue){ 
			return (int)((int)(dipValue * scale) * density +0.5);
		} 

		public int px2dip(Context context, float pxValue) {
			return (int)((int)(pxValue * scale) / density + 0.5 );
		}
		
	}
	
	public String getTimeToString(int time){
		StringBuffer sb = new StringBuffer();
		if(time > 0){
			int second = time / 1000 % 60;
			int minute = time / 1000 / 60 % 60;
			int hour = time / 1000 / 60 / 60;
			if(hour < 10){
				sb.append("0" + hour);
			}else{
				sb.append("" + hour);
			}
			
			if(minute < 10){
				sb.append(":0" + minute);
			}else{
				sb.append(":" + minute);
			}
			
			if(second < 10){
				sb.append(":0" + second);
			}else{
				sb.append(":" + second);
			}
			return sb.toString();
		}
		
		return "00:00:00";
	}
	
	private void startTimerTask(){
		
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while(isUpdateProgress){
//					barShowTime += 1;
//					mHandler.sendEmptyMessage(MSG_WHAT_PROGRESS);
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}).start();
		
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				barShowTime += 1;
				mHandler.sendEmptyMessage(MSG_WHAT_PROGRESS);
			}
		};

		timer.schedule(timerTask,1000, 1000); //延时1000ms后执行，1000ms执行一次
	}
	
	public void seekTo(int msec){
		videoView.seekTo(msec);
	}

	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return videoView.isPlaying();
	}

	public int getCurrentPosition() {
		return videoView.getCurrentPosition();
	}

	public int getDuration() {
		return videoView.getDuration();
	}
	
	public void invisibleProgress(){
		progressBarLayout.setVisibility(View.GONE);
	}

	public CustomVideoView getVideoView() {
		return videoView;
	}

	public void setVideoView(CustomVideoView videoView) {
		this.videoView = videoView;
	}
	
	public void dispatchCenter(){
        if(videoView.isPlaying()){
            pause();
        }else{
            start();
        }

        if(progressBarLayout.getVisibility() == View.VISIBLE){
            progressBarLayout.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
        }else{
            progressBarLayout.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
        }
    }

    public void dispatchMenu(){
        if(progressBarLayout.getVisibility() == View.VISIBLE){
            progressBarLayout.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
        }else{
            progressBarLayout.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
        }
    }

}
