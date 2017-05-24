package com.pbtd.mobile.utils;

import com.google.gson.Gson;
import com.pbtd.mobile.model.BaseModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/5/24.
 */

public class GsonUtil {
    private static GsonUtil mInstance;
    private Gson mGson;
    private GsonUtil() {
        mGson = new Gson();
    }

    public static GsonUtil getInstance() {
        if (mInstance == null) {
            synchronized (GsonUtil.class) {
                mInstance = new GsonUtil();
            }
        }

        return mInstance;
    }

    public <T> BaseModel<T> parseBaseModel(String json) {
        BaseModel<T> model = new BaseModel<>();
        return mGson.fromJson(json, model.getClass());
    }

    public <T> BaseModel<List<T>> parseBaseModelList(String json) {
        BaseModel<List<T>> model = new BaseModel<>();
        return mGson.fromJson(json, model.getClass());
    }

}
