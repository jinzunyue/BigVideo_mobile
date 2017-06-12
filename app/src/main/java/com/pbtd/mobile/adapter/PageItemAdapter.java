package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.model.temp.RecommendModel;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FixGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuqinchao on 17/4/20.
 */

public class PageItemAdapter extends BaseListAdapter<RecommendModel> {


    public PageItemAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendModel recommendModel = mDatas.get(position);

        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_page, null);
//            viewHolder = new ViewHolder(convertView);
//            boolean isBig;
//            switch (recommendModel.getName()) {
//                case "电视剧":
//                case "电影":
//                case "综艺":
//                    isBig = false;
//                    viewHolder.mGridView.setNumColumns(3);
//                    break;
//                default:
//                    viewHolder.mGridView.setNumColumns(2);
//                    isBig = true;
//            }
//            viewHolder.mAdapter = new CommentItemAdapter(mContext, isBig);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        convertView = View.inflate(mContext, R.layout.item_page, null);
        viewHolder = new ViewHolder(convertView);
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

        String name = recommendModel.getName();
        viewHolder.mMoreGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(mContext, "更多精彩内容");
            }
        });
        viewHolder.mChangeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(mContext, "换一批看看");
            }
        });

        viewHolder.mMoreView.setText("更多" + name);
        viewHolder.mTitleView.setText(name);
        viewHolder.mAdapter.setData(getWantedDatas(recommendModel.getList(),
                (name.equals("电影") || name.equals("电视剧") || name.equals("综艺")) ? 6 : 4));
        viewHolder.mGridView.setAdapter(viewHolder.mAdapter);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitleView;
        @BindView(R.id.grid_view)
        FixGridView mGridView;
        @BindView(R.id.ll_bottom_more)
        LinearLayout mMoreGroup;
        @BindView(R.id.ll_bottom_change)
        LinearLayout mChangeGroup;
        @BindView(R.id.tv_bottom_more)
        TextView mMoreView;
        @BindView(R.id.view_bottom_divider)
        View mDividerView;

        CommentItemAdapter mAdapter;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private List<ProductModel> getWantedDatas(List<ProductModel> list, int count) {
        if (list == null || list.size() == 0) return new ArrayList<ProductModel>();
        while (list.size() < count) {
            list.addAll(list);
        }

        while (list.size() > count) {
            list.remove(list.size() - 1);
        }
        return list;
    }


}
