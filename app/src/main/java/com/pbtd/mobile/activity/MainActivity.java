package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.fragment.FourGFragment;
import com.pbtd.mobile.fragment.LiveVideoFragment;
import com.pbtd.mobile.fragment.MainFragment;
import com.pbtd.mobile.fragment.MyFragment;
import com.pbtd.mobile.fragment.VIPFragment;
import com.pbtd.mobile.widget.PBFragmentTabHost;

public class MainActivity extends BaseActivity{

    private PBFragmentTabHost mTabHost;
    private Class mFragmentArray[] = {MainFragment.class,LiveVideoFragment.class,VIPFragment.class,FourGFragment.class,MyFragment.class};
    private int mImageViewArray[] = {R.drawable.select_tab_main,R.drawable.select_tab_live_video,R.drawable.select_tab_vip,
            R.drawable.select_tab_4g,R.drawable.select_tab_my};
    private String mTextviewArray[] = {"首页", "直播", "会员", "流量", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void initView(){
        mTabHost = (PBFragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        for(int i = 0; i < 5; i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = View.inflate(this, R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }
}
