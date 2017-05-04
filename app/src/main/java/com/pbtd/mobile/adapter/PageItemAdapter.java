package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.fragment.RecommendFragment;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.model.temp.RecommendModel;
import com.pbtd.mobile.utils.UIUtil;
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
            switch (recommendModel.getName()) {
                case "电视剧":
                case "电影":
                case "综艺":
                    isBig = false;
                    viewHolder.mGridView.setNumColumns(3);
                    break;
                default:
                    viewHolder.mGridView.setNumColumns(2);
                    isBig = true;
            }
            viewHolder.mAdapter = new CommentItemAdapter(mContext, isBig);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = recommendModel.getName();
        viewHolder.mIconView.setVisibility(mIsRecomment ?View.VISIBLE:View.GONE);
        viewHolder.mIconLineView.setVisibility(mIsRecomment ?View.GONE:View.VISIBLE);
        viewHolder.mBottomTip.setOnClickListener((click_view) -> UIUtil.showToast(mContext, "更多精彩内容"));

        viewHolder.mTitleView.setText(name);
        viewHolder.mIconView.setImageResource(RecommendFragment.mRecommendIcon.get(name));
        viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getList(),
                (name.equals("电影") || name.equals("电视剧") || name.equals("综艺"))?6:4));
        viewHolder.mGridView.setAdapter(viewHolder.mAdapter);

        return convertView;
    }

    public void setData(List<RecommendModel> datas) {
        mData.clear();
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

    private List<ProductModel> getWantedDatas(List<ProductModel> list, int count) {
        if (list == null || list.size()==0) return new ArrayList<ProductModel>();
        while (list.size() < count) {
            list.addAll(list);
        }

        while (list.size() > count) {
            list.remove(list.size() - 1);
        }
        return list;
    }

}
