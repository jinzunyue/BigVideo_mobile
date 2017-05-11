package com.pbtd.mobile.presenter.live;

import com.pbtd.mobile.model.CategoryModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface LiveContract {
    interface Presenter {
        void getCategoryList();
    }

    interface View {
        void showError(String error);
        void showCategoryList(List<CategoryModel> list);
    }
}
