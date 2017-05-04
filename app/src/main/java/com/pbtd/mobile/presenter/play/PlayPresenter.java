package com.pbtd.mobile.presenter.play;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.network.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class PlayPresenter implements PlayContract.Presenter {

    private final PlayContract.View mView;
    private final Context mContext;

    public PlayPresenter(Context context, PlayContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getProductDetail(@NonNull String productCode) {
        RetrofitUtil.getInstance().getRequestApi().getProductDetail(productCode)
                .enqueue(new Callback<BaseModel<List<ProductDetailModel>>>() {
                    @Override
                    public void onResponse(Call<BaseModel<List<ProductDetailModel>>> call, Response<BaseModel<List<ProductDetailModel>>> response) {
                        BaseModel<List<ProductDetailModel>> body = response.body();
                        if (body.success == true) {
                            mView.showProductDetail(body.result);
                        } else {
                            mView.showError(body.message+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<List<ProductDetailModel>>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getRelativeProductList(@NonNull String productCode) {
        RetrofitUtil.getInstance().getRequestApi().getRelativeProduct(productCode)
                .enqueue(new Callback<BaseModel<List<ProductModel>>>() {
                    @Override
                    public void onResponse(Call<BaseModel<List<ProductModel>>> call, Response<BaseModel<List<ProductModel>>> response) {
                        BaseModel<List<ProductModel>> body = response.body();
                        if (body.success == true) {
                            mView.showRelativeProductInfo(body.result);
                        } else {
                            mView.showError(body.message + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<List<ProductModel>>> call, Throwable t) {

                    }
                });
    }
}
