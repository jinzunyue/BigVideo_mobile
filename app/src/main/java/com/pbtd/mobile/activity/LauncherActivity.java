package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;

import com.pbtd.mobile.R;
import com.pbtd.mobile.presenter.launcher.LauncherContract;
import com.pbtd.mobile.presenter.launcher.LauncherPresenter;

public class LauncherActivity extends BaseActivity implements LauncherContract.View{

    private LauncherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mPresenter = new LauncherPresenter(this, this);
//        mPresenter.doAuth();

        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
