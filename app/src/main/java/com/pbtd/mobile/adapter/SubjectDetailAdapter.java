package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.temp.SubjectDetailModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuqinchao on 17/6/9.
 */

public class SubjectDetailAdapter extends BaseListAdapter<SubjectDetailModel> {
    public SubjectDetailAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_subject_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SubjectDetailModel subjectDetailModel = mDatas.get(position);
        viewHolder.tip.setText(subjectDetailModel.tip);
        viewHolder.title.setText(subjectDetailModel.title);

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.sd)
        SimpleDraweeView sd;
        @BindView(R.id.tv_tip)
        TextView tip;
        @BindView(R.id.tv_title)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
