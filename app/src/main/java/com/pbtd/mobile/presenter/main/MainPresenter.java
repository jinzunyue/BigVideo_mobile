package com.pbtd.mobile.presenter.main;

import android.content.Context;

import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.CategoryModel;
import com.pbtd.mobile.presenter.BasePresenter;
import com.pbtd.mobile.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(Context ctx, MainContract.View view) {
        super(ctx, view);
    }

    @Override
    public void getCategoryList() {
        mVolley.requestGetAction(Constants.BASE_SERVER + "getCategoryList.action",
                new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                BaseModel baseModel = mGson.fromJson(response, BaseModel.class);

                if (baseModel.success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("result");
                        CategoryModel[] categoryModels = mGson.fromJson(result.toString(), CategoryModel[].class);
                        mView.showCategoryList(Arrays.asList(categoryModels));
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
