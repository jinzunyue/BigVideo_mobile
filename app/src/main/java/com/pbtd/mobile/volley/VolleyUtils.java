package com.pbtd.mobile.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyUtils {

    private static VolleyUtils mVolleyUtils;

    private RequestQueue mQueue;

    private static Context mContext;

    private VolleyUtils(Context ctx) {
        mContext = ctx;
        mQueue = getRequestQueue();
    }

    public static VolleyUtils getInstance(Context context) {

        if (mVolleyUtils == null) {
            synchronized (VolleyUtils.class) {
                if (mVolleyUtils == null)
                    mVolleyUtils = new VolleyUtils(context);
            }
        }
        return mVolleyUtils;
    }


    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            mQueue.start();
        }
        return mQueue;
    }

    /**
     * 添加任务
     *
     * @param reg
     */
    public <T> void addToRequestQueue(Request<T> reg) {
        getRequestQueue().add(reg);
    }

    /**
     * 取消任务
     */
    public void cancleAll() {
        getRequestQueue().cancelAll("volley_tag");
    }

}
