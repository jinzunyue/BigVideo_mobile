package com.pbtd.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.pbtd.mobile.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private RelativeLayout mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mSearchView = (RelativeLayout) findViewById(R.id.rl_search);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(MainActivity.this, "搜索");
            }
        });
    }
}
