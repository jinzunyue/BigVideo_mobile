package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.live.ProgramTimeModel;
import com.pbtd.mobile.utils.StringUtil;
import com.pbtd.mobile.utils.UIUtil;

/**
 * Created by xuqinchao on 17/5/2.
 */

public class LiveRightAdapter extends BaseListAdapter<CategoryInnerModel> {
    public LiveRightAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_live_right, null);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mNow = (TextView) convertView.findViewById(R.id.tv_now);
            viewHolder.mNext = (TextView) convertView.findViewById(R.id.tv_next);
            viewHolder.mSd = (SimpleDraweeView) convertView.findViewById(R.id.sd_icon);
            viewHolder.mPlay = (ImageView) convertView.findViewById(R.id.iv_play);
            viewHolder.mCollect = (ImageView) convertView.findViewById(R.id.iv_collect);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryInnerModel categoryInnerModel = mDatas.get(position);
        viewHolder.mTitle.setText(categoryInnerModel.getVideoName());
        viewHolder.mNow.setText(categoryInnerModel.getmTimeProgram().getCurrent().getEpgName());
        ProgramTimeModel.InnerModel next = categoryInnerModel.getmTimeProgram().getNext();
        String time = StringUtil.formatTime(next.getStartTime());
        viewHolder.mNext.setText(time + "  " + next.getEpgName());
        viewHolder.mSd.setImageURI(categoryInnerModel.getVideoImage());
        viewHolder.mCollect.setTag(categoryInnerModel.getVideoName());
        viewHolder.mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) v.getTag();
                UIUtil.showToast(mContext, "收藏成功" + name);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public TextView mTitle;
        public TextView mNow;
        public TextView mNext;
        public ImageView mPlay;
        public ImageView mCollect;
        public SimpleDraweeView mSd;
    }
}
