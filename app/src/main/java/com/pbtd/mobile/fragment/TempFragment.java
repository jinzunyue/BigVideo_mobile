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
import com.pbtd.mobile.model.ProductInfoModel;
import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.model.RecommendVideoModel;
import com.pbtd.mobile.presenter.recomment.RecommentContract;
import com.pbtd.mobile.presenter.recomment.RecommentPresenter;
import com.pbtd.mobile.widget.FixGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xuqinchao on 17/4/27.
 */

public class TempFragment extends BaseFragment implements RecommentContract.View{

    private SimpleDraweeView mTopView;
    private FixGridView mListView;
    private TempTabAdapter mAdapter;
    public static final String PACKAGE_CODE = "package_code";
    private String mPackageCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mPackageCode = arguments.getString(PACKAGE_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_tab_item, null);
        initView(view);
        return view;
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
                List<ProductInfoModel> datas = mAdapter.getDatas();
                if (datas != null) {
                    ProductInfoModel productInfoModel = datas.get(position);
                    Intent intent = new Intent(mActivity, PlayActivity.class);
                    intent.putExtra(PlayActivity.PRODUCT_CODE, productInfoModel.productCode);
                    intent.putExtra(PlayActivity.PACKAGE_CODE, mPackageCode);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecommentPresenter mPresenter = new RecommentPresenter(mActivity, this);
        mPresenter.getProductInfoList(mPackageCode);
    }

    @Override
    public void setRecomment(List<RecommendModel> list) {

    }

    @Override
    public void showRecommendVideo(RecommendVideoModel model) {

    }

    @Override
    public void showProductInfoList(List<ProductInfoModel> list) {
        if (list==null) list = new ArrayList<>();
        ProductInfoModel productInfoModel = list.get(0);
        mTopView.setImageURI(productInfoModel.pictureUrl);
        mTopView.setOnClickListener((v) -> {
            Intent intent = new Intent(mActivity, PlayActivity.class);
            intent.putExtra(PlayActivity.PRODUCT_CODE, productInfoModel.productCode);
            intent.putExtra(PlayActivity.PACKAGE_CODE, mPackageCode);
            startActivity(intent);
        });

        if (list.size()> 6) list = list.subList(0, 6);
        mAdapter.setDatas(list);
    }
}
