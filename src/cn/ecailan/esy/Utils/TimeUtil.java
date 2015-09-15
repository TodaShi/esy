package cn.ecailan.esy.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by John on 2015/8/5.
 */
public class TimeUtil {

    private static final String TAG = "DateUtil";
    private static double EARTH_RADIUS = 6378.137;
    public static final String DATE_TYPE1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TYPE2 = "yyyyMMddHHmmssss";
    public static final String DATE_TYPE3 = "yyyy-MM-dd";
    public static final String DATE_TYPE4 = "MM月dd日";
    public static final String DATE_TYPE5 = "yyyyMM";
    public static final String DATE_TYPE6 = "yyyy";
    public static final String DATE_TYPE7 = "MM";
    public static final String DATE_TYPE8 = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_TYPE9 = "MM/dd";
    public static final String DATE_TYPE10 = "yyyy/MM/dd";
    public static final String DATE_TYPE18 = "yyyy年MM月dd日 HH:mm:ss";

    public static final String DATE_TYPE11 = "MM/ddE";
    public static final String DATE_TYPE12 = "MM/dd日  E";


    public static final String DATE_TYPE13 = "HH:mm";
    public static final String DATE_TYPE14 = "yyyy年MM月dd日";
    public static final String DATE_TYPE15 = "MM月dd日 HH:mm";
    public static final String DATE_TYPE16 = "yyyy-MM-dd HH:mm";
    public static final String DATE_TYPE17 = "HH:mm:ss";
    public static final String DATE_TYPE19 = "yyyy年MM月";

    public static final String DATE_TYPE20 = "yyyyMMdd";
    public static final String DATE_TYPE21="yyyy年";
    public static final String DATE_TYPE22 = "dd";

    private static DecimalFormat fnum1 = new DecimalFormat("#,##0.00");  //精确到小数点后两位，整数部分三位分隔
    private static DecimalFormat fnum2 = new DecimalFormat(",###");  //整数三位分隔



    /**
     * 获取指定格式时间 用于命名或者时间处理
     * @return
     */
    public static String getDateFormat(String dateType)
    {
        Date date = new Date();
        Long nowTime = date.getTime();
        String strTime = "";
        Timestamp a = new Timestamp(nowTime);
        SimpleDateFormat df = new SimpleDateFormat(dateType);
        strTime = df.format(a);
        return strTime;

    }

    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static long isGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime());
    }

    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static String getWeekOfString(String date) throws ParseException {
        String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static String getSlashDate(String date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date mDate=null;
        try {
            mDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sdfFormat.format(mDate);
    }

    public static Timestamp getTimeStamp(String date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat();
        Timestamp startTimeStamp = null;
        try {
            startTimeStamp = new Timestamp(sdf.parse(date).getTime());
            return startTimeStamp;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static Timestamp getTimeStampByTime(String time)
    {
        Timestamp startTimeStamp = new Timestamp(System.currentTimeMillis());
        try {
            return startTimeStamp.valueOf(time+":00");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static String getRampageDate(String date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date mDate=null;
        try {
            mDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sdfFormat.format(mDate);
    }


    /**
     * 获取指定格式时间 用于命名或者时间处理
     * @return
     */
    public static String getTimeFormat(Timestamp times)
    {
        String strTime = "";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm");
        strTime = df.format(times);
        return strTime;

    }

    public static String getTimeFormat(Timestamp times,String type)
    {
        String strTime = "";
        SimpleDateFormat df = new SimpleDateFormat(DATE_TYPE3);
        strTime = df.format(times);
        return strTime;

    }

    public static String getToMonthString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月");
        return sdf.format(date);
    }

    /**
     * 获取指定格式时间 用于命名或者时间处理
     * @return
     */
    public static String getDateFormat(Date date,String dateType)
    {
        if(date!=null){
            Long nowTime = date.getTime();
            String strTime = "";
            Timestamp a = new Timestamp(nowTime);
            SimpleDateFormat df = new SimpleDateFormat(dateType);
            strTime = df.format(a);
            return strTime;
        }else{
            return "";
        }
    }

    /**
     * 获取指定格式时间 用于命名或者时间处理
     * @return
     */
    public static String getDateFormatByType(String dateType)
    {
        Date date = new Date();
        if(date!=null){
            Long nowTime = date.getTime();
            String strTime = "";
            Timestamp a = new Timestamp(nowTime);
            SimpleDateFormat df = new SimpleDateFormat(dateType);
            strTime = df.format(a);
            return strTime;
        }else{
            return "";
        }
    }

    /**
     * 数字型字符串 转换为整型
     * @param str  字符串
     * @return 整型
     */
    public static int string2Int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 数字型字符串 转换为浮点型
     * @param str  字符串
     * @return 浮点型
     */
    public static float string2float(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if(str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    /**
     * 处理图片文件在SD卡上面的路径
     * @param str
     * @return
     */
    public static String getFileUrl(String str){
        String fileName = "";
        if(!TextUtils.isEmpty(str) && str.contains("/")){
            String [] spitStr = str.split("/");
            fileName = spitStr[spitStr.length-1];
        }
        return fileName;
    }

    //格式数字
    public static String getMoneyStr(String number) {
        NumberFormat nf= NumberFormat.getInstance(Locale.CHINA);
        double dNumber = Double.parseDouble(number);
        return nf.format(dNumber);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度指定的两点之间的直线距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 单位：千米
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 获取当月天数
     * @return
     */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     * */
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取上个月的年份 或者月份
     * @param flag
     * @return
     */
    public static int getLastMonth(boolean flag){
        //取得系统当前时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)-1));
        //输出上月最后一天日期
        if(flag){
            //如果是true则返回上个月的年份
            return cal.get(Calendar.YEAR);
        }else{
            //如果是false 则返回上个月的月份
            return (cal.get(Calendar.MONTH));
        }
    }

    /**
     * get window x,y
     *
     * @param context
     * @return dm DisplayMetrics
     */
    public static DisplayMetrics getWindowXY(Context context)
    {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取某个日期的下一天日期
//     * @param nowDate 某个日期(格式 mm/dd)
     * @return nowDate的下一天的日期
     */
    public static String getNextDay(String nowDateStr, boolean isWithYear) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE10);
        Date nowDate = null;
        try {
            nowDate = simpledate.parse(nowDateStr);
        } catch (ParseException ex) {
            System.out.println("日期格式不符合要求：" + ex.getMessage());
            return null;
        }
        now.setTime(nowDate);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH) + 1;
        now.set(year, month, day);
        String nextDay = "";
        if (isWithYear) {
            nextDay = simpledate.format(now.getTime());
        } else {
            simpledate = new SimpleDateFormat(
                    TimeUtil.DATE_TYPE9);
            nextDay = simpledate.format(now.getTime());
        }

        return nextDay;
    }


    public static String getDayWithYear(Date date) {
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE10);
        return simpledate.format(date);
    }

    public static String getDayWithoutYear(Date date) {
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE9);
        return simpledate.format(date);
    }

    public static String getTimeFormatWithoutSecond(java.sql.Time time) {
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE13);
        return simpledate.format(time);
    }

    public static String getDayWithYearForSqlDate(java.sql.Date date) {
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE10);
        return simpledate.format(date);
    }

    /**
     * 得到beginDay指定count之后的日期
     * */
    public static String getDayFromBeginWithSomeDay(String nowDateStr, int count) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat(
                TimeUtil.DATE_TYPE10);
        Date nowDate = null;
        try {
            nowDate = simpledate.parse(nowDateStr);
        } catch (ParseException ex) {
            System.out.println("日期格式不符合要求：" + ex.getMessage());
            return null;
        }
        now.setTime(nowDate);
        Log.i("pangyy", "1  =" + nowDate.toString());
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH) + count;
        now.set(year, month, day);
        Log.i("pangyy","2  ="+"year="+year+"m="+month+"day"+day+"  "+nowDate.toString());
        String nextDay = "";
        nextDay = simpledate.format(now.getTime());
        return nextDay;
    }


    /**
     * 获得从某个日期开始的指定数量的日期list
//     * @param someDay 开始日期（yyyy/mm/dd） 日期格式不符合要求时返回空的list
     * @param Count 所需日期列表size
     * @return 从someDay开始的连续的日期list，list的size为count
     */
    public static List<String> getDayListBeginWithSomeDay(String beginDay, int Count) {
        List<String> dayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE10);
        try {
            Date someDate = sdf.parse(beginDay);
            beginDay = sdf.format(someDate);
        } catch (ParseException e) {
            Log.v(TAG, "日期格式不符合要求：" + e.getMessage());
            e.printStackTrace();
            return dayList;
        }

        dayList.add(beginDay);
        String nextDayWithYear = beginDay;
        for (int i = 1; i < Count; i++) {
            nextDayWithYear = getNextDay(nextDayWithYear, true);
            if (dayList.indexOf(nextDayWithYear) == -1) {
                dayList.add(i, nextDayWithYear);
            }
        }
        return dayList;
    }

    /**
     * 获得今日（包含今日）之前的本月内以及上一个月的日期list
     *
     */
    @SuppressLint("SimpleDateFormat")
    public static List<String> getDayListBeforeWithToday() {
        List<String> dayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE10);
        Calendar cal = Calendar.getInstance();
        int currentDayNum=cal.get(Calendar.DAY_OF_MONTH);//获取当天是本月的第多少天
        int lastMonthDayNum=getMonthLastDay(getLastMonth(true), getLastMonth(false));//获取上个月的天数
        int countDay=currentDayNum+lastMonthDayNum;
        cal.set(Calendar.YEAR, getLastMonth(true));
        cal.set(Calendar.MONTH, getLastMonth(false));
        cal.set(Calendar.DATE, 1);//把日期设置为上月的第一天
        String benginDay=sdf.format(cal.getTime());
        dayList.add(benginDay);
        String nextDayWithYear = benginDay;
        for (int i = 1; i < countDay; i++) {
            nextDayWithYear = getNextDay(nextDayWithYear, true);
            if (dayList.indexOf(nextDayWithYear) == -1) {
                dayList.add(i, nextDayWithYear);
            }
        }
        Collections.reverse(dayList);
        return dayList;
    }


    /**
     *
     * @param dateWithYear(yyyy/mm/dd)
     * @return 巡店计划显示用的日期格式(今日:今日, 明日:明日+日期(mm/dd), 其他日期:mm/dd)
     */
    public static String convertRealDateToDisplayForTody(String dateWithYear) {
        String dateWithoutYear = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE10);
        String todayDate = sdf.format(cal.getTime());
        String tomorrowWithYear = getNextDay(todayDate, true);
        Date date = null;
        try {
            date = sdf.parse(dateWithYear);
            dateWithYear = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("日期格式不符合要求：" + e.getMessage());
            e.printStackTrace();
        }
        if (dateWithYear.equals(todayDate)) {
            dateWithoutYear = "今日" ;
        } else if (dateWithYear.equals(tomorrowWithYear)) {
            dateWithoutYear = "明日" ;
        }
        dateWithoutYear = dateWithoutYear + getDayWithoutYear(date) +"巡店计划";

        Log.v(TAG, "dateWithoutYear="+dateWithoutYear);
        return dateWithoutYear;
    }

    /**
     *
     * @param dateWithYear(yyyy/mm/dd)
     * @return 巡店计划显示用的日期格式(今日:今日, 明日:明日+日期(mm/dd), 其他日期:mm/dd)
     */
    public static String convertRealDateToDisplayForList(String dateWithYear) {
        String dateWithoutYear = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE10);
        String todayDate = sdf.format(cal.getTime());
        String tomorrowWithYear = getNextDay(todayDate, true);
        String tomorrowWithoutYear = getNextDay(todayDate, false);
        Date date = null;
        try {
            date = sdf.parse(dateWithYear);
            dateWithYear = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("日期格式不符合要求：" + e.getMessage());
            e.printStackTrace();
        }
        if (dateWithYear.equals(todayDate)) {
            dateWithoutYear = "今日";
        } else if (dateWithYear.equals(tomorrowWithYear)) {
            dateWithoutYear = "明日" + tomorrowWithoutYear;
        } else {
            dateWithoutYear = getDayWithoutYear(date);
        }
        Log.v(TAG, "dateWithoutYear="+dateWithoutYear);
        return dateWithoutYear;
    }
    /**
     *
     * @param dateWithYear(yyyy/mm/dd)
     * @return 巡店计划显示用的日期格式(今日:今日, 明日:明日+日期(mm/dd), 其他日期:mm/dd)
     */
    public static String convertRealDateToDisplayForDetail(String dateWithYear) {
        String dateWithoutYear = "";
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE10);
        Date date = null;
        try {
            date = sdf.parse(dateWithYear);
            dateWithYear = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("日期格式不符合要求：" + e.getMessage());
            e.printStackTrace();
        }

        dateWithoutYear = getDayWithoutYear(date)+"巡店计划";
        Log.v(TAG, "dateWithoutYear="+dateWithoutYear);
        return dateWithoutYear;
    }

    //格式化月初的第一天 格式化月末的最后一天
    public static Date getNowDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE3);
        String str = sdf.format(cal.getTime());
        Log.d(TAG,"====格式："+str);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static java.sql.Date getTodayForSqlType(){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat(TimeUtil.DATE_TYPE3);
        String todayString = sdf1.format(cal.getTime());//今天的日期，格式：YYYY-MM-DD
        java.sql.Date localDate = null;
        try {
            localDate = java.sql.Date.valueOf(todayString);;
        } catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }
        return localDate;

    }
    /**
     * 获取某个日期的本周的第一天
     * @return 某个日期(格式 yyyy-mm-dd)
     */
    public static String getCurrentMonday() {

        Calendar currentDate = Calendar.getInstance(Locale.getDefault());
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        int day=currentDate.get(Calendar.DAY_OF_WEEK);
        if(day==1){
            day=7;
            day=day-1;
        }else{
            day=day-1;
            day=day-1;
        }
        currentDate.setTime(new Date(System.currentTimeMillis()-day*24*3600*1000));
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE3);
        String preMonday = sdf.format(currentDate.getTime());
        return preMonday;
    }



    /**
     * 获取某个日期的本周的最后一天
     * @param strDate 某个日期(格式 yyyy-mm-dd)
     * @return
     */
    public static String getSundayWithDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE3);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            date = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 100 * 7);
            e.printStackTrace();
        }
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.setTime(date);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date monday = currentDate.getTime();
        String sunday = sdf.format(monday);
        return sunday;
    }


    /**
     * 获取给定开始日期和结束日期相差的天数
//     * @param strDate 某个日期(格式 yyyy-mm-dd)
     * @param endDate 某个日期(格式 yyyy-mm-dd)

     * @return
     */
    public static int getDifferDayNum(String startDate, String endDate) {
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DATE_TYPE3);
        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            days = (int) ((eDate.getTime() - sDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;// 从开始到结束一共天数包括首尾
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;

    }

    public static Date getDateFormat(String strDate,String type)
    {
        Date date =null;
        SimpleDateFormat df = new SimpleDateFormat(type);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){

        return str == null || "".equals(str);
    }

    /**
     * 处理重复点击问题
     *
     * @param lastClickTime
     * @return
     */
    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500)
        {
            Log.d(TAG, "==========执行=return ture");
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     *
     * 判断字符串str1是否包含字符串str2
     *
     * @param str1
     *            源字符串
     * @param str2
     *            指定字符串
     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
     */
    public static boolean contains(String str1, String str2)
    {
        return str1 != null && str1.contains(str2);
    }

    /**
     * 格式化金额
     * @param d
     * @return
     */
    public static String convertToString(double d){
        String s = "";
        int i = (int)d;

        if(i < d || i > d){
            s = fnum1.format(d);
        }else{
            s = fnum2.format(d);
        }
        return s;
    }

}
