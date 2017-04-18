package com.pbtd.mobile;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xuqinchao on 17/4/18.
 */

public class UIUtil {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
