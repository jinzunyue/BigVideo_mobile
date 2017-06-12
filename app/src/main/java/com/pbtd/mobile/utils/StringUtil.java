package com.pbtd.mobile.utils;

import com.pbtd.mobile.Constants;

import java.net.URLEncoder;
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

    /**
     *
     * @param m3u8Url
     * @param begin_time
     * @param duration  秒
     * @return
     */
    public static String getLiveBackUrl(String m3u8Url, long begin_time, long duration) {
        String resutl = "";
        String encode_m3u8 = URLEncoder.encode(m3u8Url);
        resutl = Constants.BASE_LIVE_SERVER + "/v1.0/stream/playurl?"
                + "app_key=" + Constants.LIVE_KEY + "&version=1.0&" +
                "m3u8_url=" + encode_m3u8 + "&begin_time=" + begin_time;

        if (duration != 0) resutl += "&duration=" + duration;
        return resutl;
    }
}
