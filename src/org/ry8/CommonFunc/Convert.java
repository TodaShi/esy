package org.ry8.CommonFunc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Designer on 2015/5/18.
 */
public class Convert {
    static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

  public  static String toDateTimeString(Date date) {
        return sdfDateTime.format(date);
    }

    public  static String toDateString(Date date) {
        return sdfDate.format(date);
    }

    public  static String toMonthString(Date date) {
        return sdfMonth.format(date);
    }

    public  static Date parseDateTime(String datetime) {
        try {
            return sdfDateTime.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static Date parseDate(String date) {
        try {
            return sdfDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
