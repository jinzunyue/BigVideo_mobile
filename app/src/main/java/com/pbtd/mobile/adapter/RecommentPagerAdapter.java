package com.pbtd.mobile.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.model.ProductModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class RecommentPagerAdapter extends PagerAdapter {

    private List<ProductModel> mDatas;
    private Context mContext;

    public RecommentPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas==null?0:Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        ProductModel recommendedVideo = mDatas.get(realPosition);
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
        simpleDraweeView.setImageURI(recommendedVideo.getPictureurl1());
        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mDatas==null) return "";
        return mDatas.get(getRealPosition(position)).getSeriesName();
    }

    public void setData(List<ProductModel> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<ProductModel> getDatas() {
        return mDatas;
    }

    public int getDataSize() {
        return mDatas==null?0:mDatas.size();
    }

    public int getRealPosition(int position) {
        if (getDataSize()==0) return 0;
        return position % getDataSize();
    }

}
