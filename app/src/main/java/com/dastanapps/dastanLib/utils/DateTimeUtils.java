package com.dastanapps.dastanLib.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dastanapps.cloudmagictask.R;
import com.dastanapps.dastanLib.DastanApp;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IQBAL-MEBELKART on 12/31/2015.
 */
public class DateTimeUtils {
    public static String timeStampToDate(String s) {
        long timeInLong = Long.parseLong(s);
        Timestamp stamp = new Timestamp(timeInLong * 1000); //epoch to millisec converter
        Date dt = new Date(stamp.getTime());
        Log.i("format", DateFormat.getDateInstance().format(dt));
        return DateFormat.getDateInstance().format(dt).toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToHumanDate(String timestamp) {
        long timeInLong = Long.parseLong(timestamp);
        Calendar logTime = Calendar.getInstance();
        logTime.setTimeInMillis(timeInLong*1000);
        SimpleDateFormat dateFormat;
        if (isToday(logTime)) {
            //return "Today";
            dateFormat = new SimpleDateFormat(DastanApp.getInstance().getResources().getString(R.string.time_format));
            return dateFormat.format(logTime.getTime())+" min";
        } /*else if (isYesterday(logTime)) {
            //return "Yesterday";
            dateFormat = new SimpleDateFormat(DastanApp.getInstance().getResources().getString(R.string.time_format));
        } */else {
            dateFormat = new SimpleDateFormat(DastanApp.getInstance().getResources().getString(R.string.date_format));
            return dateFormat.format(logTime.getTime());
        }
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    private static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    private static boolean isYesterday(Calendar cal) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.roll(Calendar.DAY_OF_MONTH, -1);
        return isSameDay(cal, yesterday);
    }

}
