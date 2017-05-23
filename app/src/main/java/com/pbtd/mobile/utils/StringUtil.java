package com.pbtd.mobile.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuqinchao on 17/5/15.
 */

public class StringUtil {

    public static String formatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 相对当前时间的前后天数
     * @param fix： 负数向前，正数向后
     * @return
     */
    public static String getCurrentDate(int fix) {
        Date date = new Date(System.currentTimeMillis() + fix * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 17/05/21 转成 05月21日
     * @return
     */
    public static String convertDate(String source) {
        Date date = null;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yy/MM/dd");
        try {
            date = simpleDateFormat1.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        return simpleDateFormat.format(date);
    }

}
