package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.ProductModel;

import java.util.Random;

/**
 * Created by xuqinchao on 17/5/7.
 */

public class HistoryAdapter extends BaseListAdapter<ProductModel> {
    private boolean mIsHistory;
    public HistoryAdapter(Context mContext, boolean isHistory) {
        super(mContext);
        mIsHistory = isHistory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_history, null);
            viewHolder = new ViewHolder();
            viewHolder.sd = (SimpleDraweeView) convertView.findViewById(R.id.sd);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
            viewHolder.iv_play = (ImageView) convertView.findViewById(R.id.iv_play);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!mIsHistory) {
            viewHolder.iv_type.setVisibility(View.GONE);
            viewHolder.iv_play.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.tv_time.getLayoutParams();
            layoutParams.leftMargin = 0;
        }
        ProductModel productModel = mDatas.get(position);
        viewHolder.tv_title.setText(productModel.getSeriesName());
        viewHolder.sd.setImageURI(productModel.getPictureurl1());
        Random random = new Random();
        int i = random.nextInt(10);
        viewHolder.iv_type.setImageResource(i>5?R.mipmap.icon_tv:R.mipmap.icon_phone);
        return convertView;
    }
    class ViewHolder {
        public SimpleDraweeView sd;
        public TextView tv_title;
        public TextView tv_time;
        public ImageView iv_type;
        public ImageView iv_play;
    }
}
