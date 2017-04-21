package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.UIUtil;
import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.model.RecommendedVideo;
import com.pbtd.mobile.widget.FixGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/20.
 */

public class PageItemAdapter extends BaseAdapter {

    private boolean mIsRecomment;
    private Context mContext;
    private List<RecommendModel> mData = new ArrayList<>();

    public PageItemAdapter(Context context, boolean isRecomment) {
        mContext = context;
        mIsRecomment = isRecomment;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendModel recommendModel = mData.get(position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_page, null);
            viewHolder = new ViewHolder();
            viewHolder.mIconView = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.mIconLineView = (ImageView) convertView.findViewById(R.id.iv_icon_line);
            viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mGridView = (FixGridView) convertView.findViewById(R.id.grid_view);
            viewHolder.mBottomTip = (LinearLayout) convertView.findViewById(R.id.ll_bottom_tip);
            viewHolder.mTipView = (TextView) convertView.findViewById(R.id.tv_bottom_tip);
            boolean isBig;
            switch (recommendModel.getTypeID()) {
                case "3206":
                case "3207":
                    isBig = true;
                    break;
                case "3208":
                    isBig = false;
                    break;
                default:
                    isBig = true;
            }
            viewHolder.mAdapter = new CommentItemAdapter(mContext, isBig);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mIconView.setVisibility(mIsRecomment ?View.VISIBLE:View.GONE);
        viewHolder.mIconLineView.setVisibility(mIsRecomment ?View.GONE:View.VISIBLE);
//        viewHolder.mBottomTip.setOnClickListener((click_view) -> UIUtil.showToast(mContext, "更多精彩内容"));
        viewHolder.mBottomTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(mContext, "更多精彩内容");
            }
        });

        switch (recommendModel.getTypeID()) {
            case "3206"://点播
                viewHolder.mTitleView.setText("热门");
                viewHolder.mIconView.setImageResource(R.mipmap.hot);
                viewHolder.mGridView.setNumColumns(2);
                viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getRecommendedVideos(), 4));
                break;
            case "3207"://电视剧轮播
                viewHolder.mTitleView.setText("电视剧轮播");
                viewHolder.mIconView.setImageResource(R.mipmap.tv_video);
                viewHolder.mGridView.setNumColumns(3);
                viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getRecommendedVideos(), 6));
                break;
            case "3208"://电视剧列表
                viewHolder.mTitleView.setText("电视剧列表");
                viewHolder.mIconView.setImageResource(R.mipmap.movie);
                viewHolder.mGridView.setNumColumns(3);
                viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getRecommendedVideos(), 6));
                break;
            case "3203"://电影
                viewHolder.mTitleView.setText("电影");
                viewHolder.mIconView.setImageResource(R.mipmap.movie);
                viewHolder.mGridView.setNumColumns(3);
                viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getRecommendedVideos(), 6));
                break;
            default:
                viewHolder.mTitleView.setText("娱乐");
                viewHolder.mIconView.setImageResource(R.mipmap.entertainment);
                viewHolder.mGridView.setNumColumns(2);
                viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getRecommendedVideos(), 4));
                break;
        }

        viewHolder.mGridView.setAdapter(viewHolder.mAdapter);

        return convertView;
    }

    // TODO: 17/4/21 算法优化
    public void setData(List<RecommendModel> datas) {
        mData.clear();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getTypeID().equals("3206")) {
                mData.add(datas.get(i));
                datas.remove(i);
                break;
            }
        }

        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getTypeID().equals("3207")) {
                mData.add(datas.get(i));
                datas.remove(i);
                break;
            }
        }

        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getTypeID().equals("3208")) {
                mData.add(datas.get(i));
                datas.remove(i);
                break;
            }
        }

        mData.addAll(datas);
        notifyDataSetChanged();
    }

    class ViewHolder{
        public ImageView mIconView;
        public ImageView mIconLineView;
        public TextView mTitleView;
        public FixGridView mGridView;
        public LinearLayout mBottomTip;
        public TextView mTipView;
        public CommentItemAdapter mAdapter;
    }

    private List<RecommendedVideo> getWantedDatas(List<RecommendedVideo> list, int count) {
        if (list == null || list.size()==0) return new ArrayList<RecommendedVideo>();
        while (list.size() < count) {
            list.addAll(list);
        }

        while (list.size() > count) {
            list.remove(list.size() - 1);
        }
        return list;
    }

}
