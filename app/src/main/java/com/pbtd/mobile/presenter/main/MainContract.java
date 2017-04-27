package com.pbtd.mobile.presenter.main;

import com.pbtd.mobile.model.NavigationInfoModel;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface MainContract {
    interface Presenter {
        void getNavigationInfo();
    }

    interface View {
        void showError(String error);
        void showNavigation(NavigationInfoModel navigationInfoModel);
    }
}
