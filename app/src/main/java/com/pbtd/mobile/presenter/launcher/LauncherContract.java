package com.pbtd.mobile.presenter.launcher;

/**
 * Created by xuqinchao on 17/4/24.
 */

public interface LauncherContract {
    interface Presenter {
        void doAuth();
        void doInit(String comboCode);
    }
    interface View {
        void showError(String msg);
        void showSuccess();
    }
}
