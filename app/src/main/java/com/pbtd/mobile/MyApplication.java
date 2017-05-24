package com.pbtd.mobile;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        Logger.init(Constants.LOGGER_TAG);
    }
}
