package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.HistoryAdapter;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryAndCollectionActivity extends BaseActivity {

    public static final String IS_HISTORY = "is_history";
    private boolean mIsHistory;
    private ListView mListView;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();
    }

    @Override
    protected void initView() {
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        ImageView deleteView = (ImageView) findViewById(R.id.iv_delete);
        mListView = (ListView) findViewById(R.id.lv);

        backView.setOnClickListener(v -> HistoryAndCollectionActivity.this.finish());
        deleteView.setOnClickListener(v -> UIUtil.showToast(this, "删除所有"));
        titleView.setText(mIsHistory?"最近观看":"我的收藏");

        mAdapter = new HistoryAdapter(this, mIsHistory);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductModel productModel = mAdapter.getDatas().get(position);
                Intent intent = new Intent(HistoryAndCollectionActivity.this, PlayActivity.class);
                intent.putExtra(PlayActivity.PRODUCT_CODE, productModel.getSeriesCode());
                HistoryAndCollectionActivity.this.startActivity(intent);
            }
        });

        mAdapter.setDatas(getTemDatas());
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mIsHistory = mIntent.getBooleanExtra(IS_HISTORY, true);
    }

    private List<ProductModel> getTemDatas() {
        List<ProductModel> datas = new ArrayList<>();

        ProductModel model1 = new ProductModel();
        model1.setPictureurl1("http://images.ott.cibntv.net/2017/05/05/yujieguilaigq.jpg");
        model1.setSeriesName("御姐归来 高清");
        model1.setSeriesCode("2100140023170893348180029");

        for (int i = 0; i < 10; i++) {
            datas.add(model1);
        }

        return datas;
    }
}
