package com.pbtd.mobile.presenter.tab;

import com.pbtd.mobile.model.ProductModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface TabContract {
    interface Presenter {
        void getProductList(String categoryCode, String start, String limit);
    }

    interface View {
        void showError(String error);
        void showProductList(List<ProductModel> list);
    }
}
