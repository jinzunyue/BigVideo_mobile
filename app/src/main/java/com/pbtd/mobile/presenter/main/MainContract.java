package com.pbtd.mobile.presenter.main;

import com.pbtd.mobile.model.CategoryModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface MainContract {
    interface Presenter {
        void getCategoryList();
    }

    interface View {
        void showError(String error);
        void showCategoryList(List<CategoryModel> list);
    }
}
