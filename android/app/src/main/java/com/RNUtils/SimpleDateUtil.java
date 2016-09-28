package com.RNUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by will on 2016/9/23.
 */

public class SimpleDateUtil {

    public static String longToString(long lTime){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lTime);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static long stringToLong(String dateString){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = formatter.parse(dateString);
            long lTime = date.getTime();
            return lTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.parseLong(null);
    }
}
