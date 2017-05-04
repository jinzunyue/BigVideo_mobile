package com.pbtd.mobile.presenter.play;

import android.support.annotation.NonNull;

import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.model.ProductInfoListModel;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface PlayContract {
    interface Presenter {
        void getPlayInfo(@NonNull String product_code);

        void getRelativeProductInfoList(@NonNull String packageCodes, @NonNull String productCode,
                                        @NonNull String pageLimit, @NonNull String pageNum);
    }

    interface View {
        void showPlayInfo(PlayInfoModel playInfoModel);

        void showRelativeProductInfoList(ProductInfoListModel productInfoListModel);
    }
}
