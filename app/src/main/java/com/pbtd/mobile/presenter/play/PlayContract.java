package com.pbtd.mobile.presenter.play;

import android.support.annotation.NonNull;

import com.pbtd.mobile.model.PlayInfoModel;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface PlayContract {
    interface Presenter {
        void getPlayInfo(@NonNull String product_code);
    }

    interface View {
        void showPlayInfo(PlayInfoModel playInfoModel);
    }
}
