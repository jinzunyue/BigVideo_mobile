package com.pbtd.mobile.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pbtd.mobile.R;

/**
 * Created by xuqinchao on 17/4/25.
 */

public class FourGPagerAdapter extends PagerAdapter {

    private Context mContext;
    public FourGPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setBackground(mContext.getResources().getDrawable(R.drawable.temp_4g));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "爱看流量";
            case 1:
                return "普通流量";
            case 2:
                return "限时流量";
            default:
                return "流量";
        }
    }

    public void setData() {
    }
}
