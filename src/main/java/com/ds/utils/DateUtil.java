package com.ds.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: xuke
 * @time: 2019/12/17 10:01
 */
public class DateUtil {

    private  static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");


    public static Date strToDate(String dateStr) throws ParseException {
        return sdfDate.parse(dateStr);
    }

    public static String dateToStr(Date date) {
        return sdfDate.format(date);
    }

    public static Date strToTime(String dateStr) throws ParseException {
        return sdfTime.parse(dateStr);
    }

    public static String timeToStr(Date date) {
        return sdfTime.format(date);
    }

    /**
     * 相差天数
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static long dayGapByDate(String dateStart, String dateEnd) throws ParseException {
        Date date1 = strToDate(dateStart);
        Date date2 = strToDate(dateEnd);
        return (date2.getTime()-date1.getTime())/24/60/60/1000;
    }

    public static long dayGapByTime(String dateStr1, String dateStr2) throws ParseException {
        Date date1 = strToTime(dateStr1);
        Date date2 = strToTime(dateStr2);
        return (int) Math.ceil((date2.getTime()-date1.getTime())/24/60/60/1000);
    }

    public static String dayAddAndSubtract(int day){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        return sdfTime.format(calendar.getTime());
    }

    /**
     * 相差日期集合
     * @param dateStart
     * @param dateEnd
     * @return
     * @throws ParseException
     */
    public static List<String> dayGapGather(String dateStart, String dateEnd) throws ParseException {
        Date startDate = sdfDate.parse(dateStart);
        Date endDate = sdfDate.parse(dateEnd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        List<String> dayList = new ArrayList<>();
        while (calendar.getTime().getTime() <= endDate.getTime()){
            calendar.add(Calendar.DATE, 1);
            dayList.add(sdfDate.format(calendar.getTime()));
        }
        return dayList;
    }

    public static String getDatePoor(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = endDate.getTime() - startDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分钟";
    }

}
