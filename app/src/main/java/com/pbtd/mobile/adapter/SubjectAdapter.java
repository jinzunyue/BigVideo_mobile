package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.temp.SubjectModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuqinchao on 17/6/9.
 */

public class SubjectAdapter extends BaseListAdapter<SubjectModel> {
    public SubjectAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_subject, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SubjectModel subjectModel = mDatas.get(position);
        viewHolder.mTitle.setText(subjectModel.title);
        viewHolder.mDescription.setText(subjectModel.description);

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv)
        SimpleDraweeView mImage;
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_description)
        TextView mDescription;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
