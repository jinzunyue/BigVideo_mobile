package com.pbtd.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayActivity;
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
        return mDatas==null?0:1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        ProductModel recommendedVideo = mDatas.get(realPosition);
        View view = View.inflate(mContext, R.layout.item_recommend_top, null);
        RelativeLayout rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);
        SimpleDraweeView sd = (SimpleDraweeView) view.findViewById(R.id.sd);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        sd.setImageURI(recommendedVideo.getPictureurl1());
        tv.setText(recommendedVideo.getSeriesName());
        rl_root.setTag(recommendedVideo);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel tag = (ProductModel) v.getTag();
                String seriesCode = tag.getSeriesCode();
                Intent intent = new Intent(mContext, PlayActivity.class);
                intent.putExtra(PlayActivity.PRODUCT_CODE, seriesCode);
                mContext.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
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
