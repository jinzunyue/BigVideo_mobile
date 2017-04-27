package com.pbtd.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayActivity;
import com.pbtd.mobile.model.ProductInfoModel;

/**
 * Created by xuqinchao on 17/4/27.
 */

public class TempTabAdapter extends BaseListAdapter<ProductInfoModel> {
    public TempTabAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comment_big, null);
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

        ProductInfoModel productInfoModel = mDatas.get(position);
        viewHolder.tv_sub_title.setVisibility(View.GONE);
        viewHolder.sd_view.setImageURI(productInfoModel.pictureUrl);
        viewHolder.tv_main_title.setText(TextUtils.isEmpty(productInfoModel.productName)?"好电影":productInfoModel.productName);
        viewHolder.rl_root.setOnClickListener((v -> {
            Intent intent = new Intent(mContext, PlayActivity.class);
            intent.putExtra(PlayActivity.PRODUCT_CODE, productInfoModel.productCode);
            mContext.startActivity(intent);
        }));
        return convertView;
    }

    class ViewHolder{
        public SimpleDraweeView sd_view;
        public TextView tv_updata;
        public TextView tv_main_title;
        public TextView tv_sub_title;
        public RelativeLayout rl_root;
    }
}
