package com.example.gagan.proj1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.gagan.proj1.utils.Constant.ONLINE_TIME_LIMIT;

/**
 * Created by Gagan on 4/18/2018.
 */

public class Utils {
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    public static String getTimeTODisplay(Date time) {
        return getTimeTODisplay(time, "hh:mm a");
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTimeTODisplay(Date time, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        String timeToRet = dateFormat.format(time);
        return timeToRet;
    }


    public static String getTimeId() {
        return getTimeTODisplay(getCurrentTime(), "yyyy-MM-dd HH:mm:ss");
    }
}
