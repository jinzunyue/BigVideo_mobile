package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.pbtd.mobile.R;

public class LauncherActivity extends BaseActivity{

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mActivity.startActivity(new Intent(mActivity, MainActivity.class));
            LauncherActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void initView() {

    }
}
