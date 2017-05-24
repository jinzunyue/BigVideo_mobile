package com.pbtd.mobile.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.pbtd.mobile.volley.VolleyController;

/**
 * Created by xuqinchao on 17/5/24.
 */

public class BasePresenter<T> {

    protected Context mContext;
    protected T mView;
    protected Gson mGson;
    protected VolleyController mVolley;

    public BasePresenter(Context ctx, T t){
        mContext = ctx;
        mView = t;
        mGson = new Gson();
        mVolley = new VolleyController(ctx);
    }
}
