package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xuqinchao on 17/4/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected AppCompatActivity mActivity;
    protected Intent mIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        getIntentData();
    }

    protected abstract void initView();

    protected void getIntentData() {
        mIntent = getIntent();
        if (mIntent == null) return;
    }
}
