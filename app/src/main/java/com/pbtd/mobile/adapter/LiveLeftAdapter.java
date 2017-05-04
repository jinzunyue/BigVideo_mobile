package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbtd.mobile.R;

/**
 * Created by xuqinchao on 17/5/2.
 */

public class LiveLeftAdapter extends BaseListAdapter<String> {

    public LiveLeftAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_live_left, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTv.setSelected(position==0);

        viewHolder.mTv.setText(mDatas.get(position));
        return convertView;
    }

    class ViewHolder{
        public TextView mTv;
    }
}
