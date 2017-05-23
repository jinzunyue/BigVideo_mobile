package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.utils.UIUtil;

public class VipBuyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_buy);

        initView();
    }

    @Override
    protected void initView() {
        Button btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(VipBuyActivity.this, "订购成功");
            }
        });

        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VipBuyActivity.this.finish();
            }
        });
    }
}
