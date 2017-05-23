package com.pbtd.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayActivity;
import com.pbtd.mobile.model.ProductModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/20.
 */

public class CommentItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductModel> mDatas;
    private boolean mIsBig;

    public CommentItemAdapter(Context mContext, boolean isBig) {
        this.mContext = mContext;
        mIsBig = isBig;
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public ProductModel getItem(int position) {
        if (mDatas==null) return new ProductModel();
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, mIsBig?R.layout.item_comment_big:R.layout.item_comment_small, null);
            viewHolder = new ViewHolder();
            viewHolder.sd_view = (SimpleDraweeView) convertView.findViewById(R.id.sd_view);
            viewHolder.tv_main_title = (TextView) convertView.findViewById(R.id.tv_main_title);
            viewHolder.tv_sub_title = (TextView) convertView.findViewById(R.id.tv_sub_title);
            viewHolder.tv_updata = (TextView) convertView.findViewById(R.id.tv_update);
            viewHolder.rl_root = (RelativeLayout) convertView.findViewById(R.id.rl_root);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProductModel recommendedVideo = mDatas.get(position);
        viewHolder.sd_view.setImageURI(recommendedVideo.getPictureurl1());
        viewHolder.tv_updata.setText("更新至第33集");
        viewHolder.tv_main_title.setText(recommendedVideo.getSeriesName());
        viewHolder.tv_sub_title.setText("");
        viewHolder.rl_root.setTag(R.id.tag_first, recommendedVideo);
        viewHolder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel recommendedVideo = (ProductModel) v.getTag(R.id.tag_first);
                Intent intent = new Intent(mContext, PlayActivity.class);
                intent.putExtra(PlayActivity.PRODUCT_CODE, recommendedVideo.getSeriesCode());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void setData(List<ProductModel> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    class ViewHolder{
        public SimpleDraweeView sd_view;
        public TextView tv_updata;
        public TextView tv_main_title;
        public TextView tv_sub_title;
        public RelativeLayout rl_root;
    }
}
