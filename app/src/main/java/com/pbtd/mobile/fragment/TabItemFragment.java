package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayActivity;
import com.pbtd.mobile.adapter.TempTabAdapter;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.presenter.tab.TabContract;
import com.pbtd.mobile.presenter.tab.TabPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FixGridView;

import java.util.List;
import java.util.Random;

/**
 * Created by xuqinchao on 17/4/27.
 */

public class TabItemFragment extends BaseFragment implements TabContract.View{

    private SimpleDraweeView mTopView;
    private FixGridView mListView;
    private TempTabAdapter mAdapter;
    public static final String CATEGORY_CODE = "categorycode";
    private String mCategoryCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCategoryCode = arguments.getString(CATEGORY_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_tab_item, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabContract.Presenter mPresenter = new TabPresenter(mActivity, this);
        mPresenter.getProductList(mCategoryCode, "0", "7");
    }

    private void initView(View view) {
        mTopView = (SimpleDraweeView) view.findViewById(R.id.sd_top);
        mListView = (FixGridView) view.findViewById(R.id.lv);
        Random random = new Random();
        int i = random.nextInt(10);
        mAdapter = new TempTabAdapter(mActivity, i>4);
        mListView.setNumColumns(i>4?2:3);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ProductModel> datas = mAdapter.getDatas();
                if (datas != null) {
                    ProductModel productInfoModel = datas.get(position);
                    Intent intent = new Intent(mActivity, PlayActivity.class);
                    intent.putExtra(PlayActivity.PRODUCT_CODE, productInfoModel.getSeriesCode());
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void showError(String error) {
        UIUtil.showToast(mActivity, error+"");
    }

    @Override
    public void showProductList(List<ProductModel> list) {
        if (list != null) {
            ProductModel productModel = list.get(0);
            mTopView.setImageURI(productModel.getPictureurl1());
            mTopView.setOnClickListener((v) -> {
                Intent intent = new Intent(mActivity, PlayActivity.class);
                intent.putExtra(PlayActivity.PRODUCT_CODE, productModel.getSeriesCode());
                startActivity(intent);
            });

            mAdapter.setDatas(list.subList(1, 7));
        }
    }
}
