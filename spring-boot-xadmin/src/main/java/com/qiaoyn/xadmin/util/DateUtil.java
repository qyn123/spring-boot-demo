package com.qiaoyn.xadmin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author qiaoyanan
 * date:2022/10/12 15:13
 */
public class DateUtil {

    /**
     * 获取某天的 00:00:00
     */
    public static String getStartTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(simpleDateFormat.parse(date));
            day.set(Calendar.HOUR_OF_DAY, 0);
            day.set(Calendar.MINUTE, 0);
            day.set(Calendar.SECOND, 0);
            day.set(Calendar.MILLISECOND, 0);
            df.format(day.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(day.getTime());
    }

    /**
     * 获取某天的 23:59:59
     */
    public static String getEndTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(simpleDateFormat.parse(date));
            day.set(Calendar.HOUR_OF_DAY, 23);
            day.set(Calendar.MINUTE, 59);
            day.set(Calendar.SECOND, 59);
            day.set(Calendar.MILLISECOND, 999);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(day.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getStartTime("2022-10-12"));
        System.out.println(getEndTime("2022-10-12"));
    }

}
