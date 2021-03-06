package com.pbtd.mobile.widget;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.model.ProductModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/20.
 */

public class CommentItem {

    private Context mContext;
    private List<ProductModel> mData;
    private int mType_id;
    private boolean mIsRecomment;
    private View mRootView;

    /**
     * type_id == 3206 暂时为热门推荐
     *
     * @param context
     * @param type_id          每行个数由type_id确定
     * @param recommendedVideo
     * @param isRecomment      是否是首页
     */
    public CommentItem(Context context, int type_id, List<ProductModel> recommendedVideo, boolean isRecomment) {
        mContext = context;
        mData = recommendedVideo;
        mType_id = type_id;
        mIsRecomment = isRecomment;

        initView();
    }

    private void initView() {
        mRootView = View.inflate(mContext, R.layout.item_page, null);
        ImageView mIconView = (ImageView) mRootView.findViewById(R.id.iv_icon);
        TextView mTitleView = (TextView) mRootView.findViewById(R.id.tv_title);
        GridView mGridView = (GridView) mRootView.findViewById(R.id.grid_view);

        switch (mType_id) {
            case 3206://点播
                mTitleView.setText("热门");
                mIconView.setImageResource(R.mipmap.hot);
                mGridView.setColumnWidth(2);
                break;
            case 3207://电视剧轮播
                mTitleView.setText("电视剧轮播");
                mIconView.setImageResource(R.mipmap.tv_video);
                mGridView.setColumnWidth(3);
                break;
            case 3208://电视剧列表
                mTitleView.setText("电视剧列表");
                mIconView.setImageResource(R.mipmap.movie);
                mGridView.setColumnWidth(3);
                break;
        }

        mIconView.setVisibility(mIsRecomment?View.VISIBLE:View.GONE);
    }

    public View getRootView() {
        return mRootView;
    }
}
