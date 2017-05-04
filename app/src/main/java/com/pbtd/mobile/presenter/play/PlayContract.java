package com.pbtd.mobile.presenter.play;

import android.support.annotation.NonNull;

import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface PlayContract {
    interface Presenter {
        void getProductDetail(@NonNull String productCode);
        void getRelativeProductList(@NonNull String productCode);
    }

    interface View {
        void showError(String error);
        void showProductDetail(List<ProductDetailModel> model);
        void showRelativeProductInfo(List<ProductModel> model);
    }
}
