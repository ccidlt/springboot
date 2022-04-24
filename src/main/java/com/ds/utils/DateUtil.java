package com.ds.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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


    public static Date absoluteDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int hour = c.get(11);
        int mim = c.get(12);
        int ss = c.get(13);

        c.set(0, 0, 0, hour, mim, ss);

        return c.getTime();
    }


    public static String dateToStringNoZone(Date inDate) {
        return DateFormatUtils.format(inDate,
                DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
    }



    public static String dateToStringWithZone(Date inDate) {
        return
                String.valueOf(DateFormatUtils.format(inDate, DateFormatUtils.ISO_DATETIME_FORMAT.getPattern())) + "Z";
    }




    public static Date stringToDateNoZone(String dateStr) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat();
        simpleFormat.applyPattern(DateFormatUtils.ISO_DATETIME_FORMAT
                .getPattern());
        return simpleFormat.parse(dateStr);
    }



    public static Date stringToDateWithZone(String dateStr) throws ParseException {
        String dateStrFormated = dateStr;
        int zoneIdx = -1;
        if (dateStr.indexOf("ZZ") > 0) {
            zoneIdx = dateStr.indexOf("ZZ");
        } else if (dateStr.indexOf("Z") > 0) {
            zoneIdx = dateStr.indexOf("Z");
        }
        if (zoneIdx > 0) {
            dateStrFormated = dateStr.substring(0, zoneIdx);
        }
        SimpleDateFormat simpleFormat = new SimpleDateFormat();
        simpleFormat.applyPattern(DateFormatUtils.ISO_DATETIME_FORMAT
                .getPattern());
        return simpleFormat.parse(dateStrFormated);
    }




    public static Date stringToDate(String dateStr) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat simpleFormat = new SimpleDateFormat();
        simpleFormat.applyPattern(DateFormatUtils.ISO_DATE_FORMAT
                .getPattern());
        return simpleFormat.parse(dateStr);
    }



    public static int getday() {
        Calendar c = Calendar.getInstance();
        int day = c.get(5);
        return day;
    }



    public static int getDayBy2Date(Date startDate, Date endDate, boolean flag) {
        int count = 0;
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.set(11, 0);
        startCalendar.set(12, 0);
        startCalendar.set(13, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.set(11, 0);
        endCalendar.set(12, 0);
        endCalendar.set(13, 0);
        while (endCalendar.getTime().getTime() / 86400000L -
                startCalendar.getTime().getTime() / 86400000L >= 0L) {
            count++;
            if (flag && (
                    startCalendar.get(7) == 7 ||
                            startCalendar.get(7) == 1)) {
                count--;
            }

            startCalendar.add(5, 1);
        }
        return count;
    }



    public static Date dayBeginDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);

        c.set(year, month, day, 0, 0, 0);

        return c.getTime();
    }


    public static Date dayEndDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);

        c.set(year, month, day + 1, 0, 0, 0);

        return c.getTime();
    }


    public static Date getDayFormZero(int ago) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);

        c.set(year, month, day - ago, 0, 0, 0);

        return c.getTime();
    }


    public static Date getEndDayFormZero(int ago) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);

        c.set(year, month, day - ago, 23, 59, 59);

        return c.getTime();
    }

    public static String getStringByDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date getStartMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getEndMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(5, 1);
        calendar.roll(5, -1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static boolean isStartMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        if (1 == calendar.get(5)) {
            return true;
        }
        return false;
    }

    public static Date getDateByString(String strTime, String pattern) {
        if (!StringUtils.isEmpty(strTime)) {
            SimpleDateFormat format = null;
            if (!StringUtils.isEmpty(pattern)) {
                format = new SimpleDateFormat(pattern);
            } else {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            if (format != null) {
                try {
                    return format.parse(strTime);
                } catch (ParseException e) {
                    logger.error("时间转换异常");
                }
            }
        }
        logger.error("时间为空");
        return null;
    }

    public static boolean isBeforeDay(Long timeInMillis, Integer beforeDay) {
        if (timeInMillis != null) {
            if (beforeDay == null) {
                beforeDay = Integer.valueOf(0);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -beforeDay.intValue());
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(timeInMillis.longValue());
            if (calendar.get(1) - time.get(1) > 0)
                return true;
            if (calendar.get(2) - time.get(2) > 0)
                return true;
            if (calendar.get(5) -
                    time.get(5) >= 0) {
                return true;
            }
            return false;
        }

        return true;
    }

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
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static long dayGapByDate(String dateStr1, String dateStr2) throws ParseException {
        Date date1 = strToDate(dateStr1);
        Date date2 = strToDate(dateStr2);
        return (date2.getTime()-date1.getTime())/24/60/60/1000;
    }

    public static long dayGapByTime(String dateStr1, String dateStr2) throws ParseException {
        Date date1 = strToTime(dateStr1);
        Date date2 = strToTime(dateStr2);
        return (int) Math.ceil((date2.getTime()-date1.getTime())/24/60/60/1000);
    }

    /**
     * 相差日期集合
     * @param dateStr1
     * @param dateStr2
     * @return
     * @throws ParseException
     */
    public static List<String> dayGapGather(String dateStr1, String dateStr2) throws ParseException {
        Date startDate = sdfDate.parse(dateStr1);
        Date endDate = sdfDate.parse(dateStr2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        List<String> dayList = new ArrayList<>();
        while (calendar.getTime().getTime() <= endDate.getTime()){
            calendar.add(Calendar.DATE, 1);
            dayList.add(sdfDate.format(calendar.getTime()));
        }
        return dayList;
    }
}
