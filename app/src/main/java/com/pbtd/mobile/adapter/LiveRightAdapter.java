package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.model.temp.LiveRightModel;

/**
 * Created by xuqinchao on 17/5/2.
 */

public class LiveRightAdapter extends BaseListAdapter<LiveRightModel> {
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTitle.setText(mDatas.get(position).title);
        return convertView;
    }

    class ViewHolder {
        public TextView mTitle;
    }
}
