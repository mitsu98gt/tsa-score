package com.envisageconsulting.primefaces.scoredaddy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getDateTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
