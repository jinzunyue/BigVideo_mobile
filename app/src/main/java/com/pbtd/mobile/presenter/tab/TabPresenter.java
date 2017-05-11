package com.pbtd.mobile.presenter.tab;

import android.content.Context;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.network.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class TabPresenter implements TabContract.Presenter {

    private final TabContract.View mView;
    private final Context mContext;

    public TabPresenter(Context context, TabContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getProductList(String categoryCode, String start, String limit) {
        RetrofitUtil.getInstance(Constants.BASE_SERVER).getRequestApi().getProductList(categoryCode, start, limit)
                .enqueue(new Callback<BaseModel<List<ProductModel>>>() {
                    @Override
                    public void onResponse(Call<BaseModel<List<ProductModel>>> call, Response<BaseModel<List<ProductModel>>> response) {
                        BaseModel<List<ProductModel>> body = response.body();
                        if (body.success == true) {
                            mView.showProductList(body.result);
                        } else {
                            mView.showError(body.message);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<List<ProductModel>>> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
    }
}
