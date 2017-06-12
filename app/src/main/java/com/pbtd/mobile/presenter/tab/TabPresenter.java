package com.pbtd.mobile.presenter.tab;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.presenter.BasePresenter;
import com.pbtd.mobile.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class TabPresenter extends BasePresenter<TabContract.View> implements TabContract.Presenter {

    public TabPresenter(Context context, TabContract.View view) {
        super(context, view);
    }

    @Override
    public void getProductList(String categoryCode, String start, String limit) {
        String url = "";
        if (TextUtils.isEmpty(categoryCode) || TextUtils.isEmpty(start) || TextUtils.isEmpty(limit)) {
            url = Constants.BASE_SERVER + "getRecommendList.action";
        } else {
            url = Constants.BASE_SERVER +
                    "getRecommendList.action?type_id=" + categoryCode
                    + "&start=" + start + "&limit=" + limit;
        }
        mVolley.requestGetAction(url, new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                BaseModel baseModel = mGson.fromJson(response, BaseModel.class);

                if (baseModel.success == true) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("result");
                        ProductModel[] productModels = mGson.fromJson(result.toString(), ProductModel[].class);
                        List<ProductModel> resultList = new ArrayList<ProductModel>(Arrays.asList(productModels));
                        mView.showProductList(resultList);
                    } catch (JSONException e) {
                        Logger.e(Constants.LOGGER_TAG, e.getMessage());
                        mView.showError("数据异常");
                    }
                } else {
                    mView.showError(baseModel.message);
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mView.showError(error.getMessage() + "");
            }
        });
    }
}
