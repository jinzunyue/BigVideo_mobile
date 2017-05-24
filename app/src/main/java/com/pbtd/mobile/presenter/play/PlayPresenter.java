package com.pbtd.mobile.presenter.play;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.presenter.BasePresenter;
import com.pbtd.mobile.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class PlayPresenter extends BasePresenter<PlayContract.View> implements PlayContract.Presenter {

    public PlayPresenter(Context context, PlayContract.View view) {
        super(context, view);
    }

    @Override
    public void getProductDetail(@NonNull final String productCode) {
        mVolley.requestGetAction(Constants.BASE_SERVER + "getAlbumsList.action" +
                "?seriesCode=" + productCode, new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                BaseModel baseModel = mGson.fromJson(response, BaseModel.class);

                if (baseModel.success == true) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("result");
                        ProductDetailModel[] productDetailModels = mGson.fromJson(result.toString(), ProductDetailModel[].class);
                        mView.showProductDetail(Arrays.asList(productDetailModels));
                    } catch (JSONException e) {
                        Logger.e(Constants.LOGGER_TAG, e.getMessage());
                        mView.showError("数据异常");
                    }
                } else {
                    mView.showError(baseModel.message+"");
                }

                getRelativeProductList(productCode);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mView.showError(error.getMessage() + "");
                getRelativeProductList(productCode);
            }
        });
    }

    @Override
    public void getRelativeProductList(@NonNull String productCode) {
        mVolley.requestGetAction(Constants.BASE_SERVER + "getRelateList.action?seriesCode="
                + productCode,
                new VolleyController.VolleyCallback() {
                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = mGson.fromJson(response, BaseModel.class);

                        if (baseModel.success == true) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("result");
                                ProductModel[] productModels = mGson.fromJson(result.toString(), ProductModel[].class);
                                mView.showRelativeProductInfo(Arrays.asList(productModels));
                            } catch (JSONException e) {
                                Logger.e(Constants.LOGGER_TAG, e.getMessage());
                                mView.showError("数据异常");
                            }
                        } else {
                            mView.showError(baseModel.message + "");
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.showError(error.getMessage() + "");
                    }
                });
    }
}
