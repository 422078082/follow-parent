package com.follow.common.util;

import com.sun.jmx.snmp.Timestamp;
import org.apache.commons.lang3.StringUtils;

import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * 按 yyyy-MM-dd HH:mm:ss 格式化时间.
     */
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 按 yyyyMMddHHmmss 格式化时间.
     */
    public static final SimpleDateFormat DATE_TIME_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 按 yyyy-MM-dd HH:mm 格式化时间.
     */
    public static final SimpleDateFormat DATE_MINUTE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * 按 yyyy-MM-dd 格式化日期.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 按 yyyy/MM/dd 格式化日期.
     */
    public static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * 按 yyyy年MM月dd日 格式化日期.
     */
    public static final SimpleDateFormat DATE_FORMAT3 = new SimpleDateFormat("yyyy年MM月dd日");
    /**
     * 按 yyyyMMdd 格式化日期.
     */
    public static final SimpleDateFormat DATE_FORMAT4 = new SimpleDateFormat("yyyyMMdd");
    /**
     * 按 HH:mm 格式化日期.
     */
    public static final DateFormat DATE_FORMAT5 = new SimpleDateFormat("HH:mm");
    /**
     * 按 M-d H:m 格式化日期.
     */
    public static final SimpleDateFormat DATE_FORMAT6 = new SimpleDateFormat("M月d日 H:mm");
    /**
     * 数值格式化
     */
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.######");
    /**
     * 两位小数标准格式化
     */
    public static final DecimalFormat STANDARD_DEC_FORMAT = new DecimalFormat("#0.00");

    public static final long MONTH = 30 * 1000 * 24 * 60 * 60;
    public static final long DAY = 1000 * 24 * 60 * 60;
    public static final long HOURS = 1000 * 60 * 60;
    public static final long MINUTE = 1000 * 60;
    public static final long SECOND = 1000;

    /**
     * @Description: 计算时间段
     * @Param: [startTime, endTime]
     * @return: java.lang.String
     * @Date: 2018/10/11
     */
    public static String computeTime(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return "";
        }
        long stopTime = endTime.getTime() - startTime.getTime();
        // 计算差多少天
        long day = stopTime / DateUtil.DAY;
        // 计算差多少小时
        long hour = stopTime % DateUtil.DAY / DateUtil.HOURS;
        // 计算差多少分钟
        long min = stopTime % DateUtil.DAY % DateUtil.HOURS / DateUtil.MINUTE;
        if (day > 0)
            return day + "天" + hour + "小时" + min + "分钟";
        return hour + "小时" + min + "分钟";
    }

    public static long computeMinTime(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return -1;
        }
        long stopTime = endTime.getTime() - startTime.getTime();
        // 计算差多少分钟
        long min = stopTime % DateUtil.DAY % DateUtil.HOURS / DateUtil.MINUTE;
        return min;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        if (StringUtils.isBlank(strDate))
            return new Date();
        return DATE_TIME_FORMAT.parse(strDate, new ParsePosition(0));
    }

    /**
     * 计算巡查员上班的时长
     */
    public static Long computeTime2(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return null;
        }
        long startHour = startTime.getTime() / (1000 * 60 * 60) + 8;
        long endHour = endTime.getTime() / (1000 * 60 * 60) + 8;

        long startDay = startHour / 24;
        long endDay = endHour / 24;

        long start1 = startHour % 24;
        long end1 = endHour % 24;
        long hour = (endDay - startDay - 1) * (17 - 9) + Math.max(Math.min(end1, 17) - 9, 0)
                + Math.max(17 - Math.max(start1, 9), 0);
        if (hour > 8) {
            return hour;
        }
        return hour;
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(Calendar.DATE);
    }

    public static int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * @Description: 判断某个日期是否在日期数组
     * @Param: [sourceDate 日期数组, curDate 某个日期]
     * @return: boolean
     * @Date: 2018/10/26
     */
    public static boolean isInDate(String[] sourceDate, String curDate) {
        if (sourceDate == null || sourceDate.length == 0) {
            throw new IllegalArgumentException("Illegal Argument arg: 传递的日期数组是空！" + sourceDate.toString());
        }
        if (StringUtils.isBlank(curDate) || !curDate.contains("-")) {
            throw new IllegalArgumentException("Illegal Argument arg: 传递的日期是空或者格式不正确！" + curDate);
        }
        for (int i = 0; i < sourceDate.length; i++) {
            if (StringUtils.isNotBlank(sourceDate[i]) && sourceDate[i].equals(curDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00]
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    public static boolean isWeekend() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(String startAt, String endAt) {
        List<String> dates = new ArrayList<>();
        try {
            Date startDate = DATE_FORMAT.parse(startAt);
            Date endDate = DATE_FORMAT.parse(endAt);
            dates.addAll(queryData(startDate, endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(Date startAt, Date endAt) {
        List<String> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startAt);
        Calendar end = Calendar.getInstance();
        end.setTime(endAt);
        while (start.before(end) || start.equals(end)) {
            dates.add(DATE_FORMAT.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    /**
     * @Description: 比较时间大小，前者比后者大返回true
     * @Param: [time1, time2]
     * @return: boolean
     * @Date: 2018/11/1
     */
    public static boolean compareTime(String time1, String time2) {
        try {
            Date dt1 = DATE_TIME_FORMAT.parse(time1);//将字符串转换为date类型
            Date dt2 = DATE_TIME_FORMAT.parse(time2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * >=
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean compareTimeWithEqual(String time1, String time2) {
        try {
            Date dt1 = DATE_TIME_FORMAT.parse(time1);//将字符串转换为date类型
            Date dt2 = DATE_TIME_FORMAT.parse(time2);
            if (dt1.getTime() >= dt2.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Description: 比较日期大小，前者比后者大返回true
     * @Param: [date1, date2]
     * @return: boolean
     * @Date: 2018/12/20
     */
    public static boolean compareDate(String time1, String time2) {
        try {
            Date dt1 = DATE_FORMAT5.parse(time1);//将字符串转换为date类型
            Date dt2 = DATE_FORMAT5.parse(time2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Description: 持续多长时间
     * @Param: [startTime, endTime]
     * @return: long 毫秒数
     * @Date: 2018/11/1
     */
    public static long lastTime(String startTime, String endTime) {
        try {
            Date dt1 = DATE_FORMAT5.parse(startTime);//将字符串转换为date类型
            Date dt2 = DATE_FORMAT5.parse(endTime);
            return dt2.getTime() - dt1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //判断时间date1是否在时间date2之前
    //时间格式 2005-4-21 16:16:34
    public static boolean isDateBefore(Date date1, Date date2) {
        try {
            String date11 = DATE_TIME_FORMAT.format(date1);
            String date22 = DATE_TIME_FORMAT.format(date2);
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date11).before(df.parse(date22));
        } catch (ParseException e) {
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * 判断日期是否是周末
     *
     * @param source
     * @return
     */
    public static boolean checkWeekend(String source) {
        Date date = null;
        try {
            date = DATE_FORMAT.parse(source);
        } catch (Exception ex) {

        }
        if (date == null) {
            return false;
        }
        return checkWeekend(date);
    }

    /**
     * 判断日期是否是周末
     *
     * @param date
     * @return
     */
    public static boolean checkWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0 || week == 6) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期是否在时指定间段内
     *
     * @param compareDate    比较日期
     * @param betweenDateLow 时间段1
     * @param betweenDateHi  时间段2
     * @return 比较结果：true compareDate在betweenDateLow和betweenDateHi之间
     */
    public static boolean isBetweenDate(Date compareDate, Date betweenDateLow, Date betweenDateHi) {
        boolean result = false;
        String comps = DATE_FORMAT.format(compareDate), dateLow = DATE_FORMAT.format(betweenDateLow), dateHi = DATE_FORMAT
                .format(betweenDateHi);
        if (comps.compareTo(dateLow) >= 0 && comps.compareTo(dateHi) <= 0) {
            result = true;
        }
        return result;
    }

    /**
     * 判断时间是否在指定时间段内
     *
     * @param compareTime 比较时间
     * @param timeLow     时间段1，HH:mm格式
     * @param timeHi      时间段2，HH:mm格式
     * @return 比较结果：true compareDate在timeLow和timeHi之间
     */
    public static boolean isBetweenTime(Date compareTime, String timeLow, String timeHi) {
        boolean result = false;
        String comps = DATE_FORMAT5.format(compareTime);
        if (comps.compareTo(timeLow) >= 0 && comps.compareTo(timeHi) <= 0) {
            result = true;
        }
        return result;
    }

    /**
     * @param sourceDate
     * @return
     */
    public static String formatMobileDateTime(Date sourceDate) {
        // 时间格式由：汉字标识+时间格式组成， 1分钟之内统称“刚刚”，一小时之内为“x分钟之前”，与当前日期相同为“今天 xx:xx”
        String result = "";
        /** 按 H:m 格式化日期. */
        SimpleDateFormat formatScript = DATE_FORMAT6;
        if (sourceDate == null) {
            return result;
        }
        Date curDate = new Date();
        int diffDate = formatSubDateForDate(curDate, sourceDate);
        if (diffDate == 0) {
            int diffMin = formatSubDateForMinutes(curDate, sourceDate);
            if (diffMin < 1) {
                return "刚刚";
            } else if (diffMin < 60) {
                return diffMin + "分钟前";
            } else {
                formatScript = new SimpleDateFormat("今天 H:mm");
            }
        } else if (diffDate == 1) {
            formatScript = new SimpleDateFormat("昨天 H:mm");
        }
        result = formatScript.format(sourceDate);
        return result;
    }


    /**
     * 日期相减 计算相差毫秒数
     *
     * @param endDate   结束日期
     * @param startDate 开始日期
     * @return
     */
    public static long subtractDate(Date endDate, Date startDate) {
        // date1和date2都不能为null 否则返回0
        if (endDate == null || startDate == null) {
            return new Integer(0);
        }
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 日期相减 返回相差的天数、时分秒
     *
     * @param endDate   结束日期
     * @param startDate 开始日期
     * @return
     */
    public static String subtractDateAndLongTimeToDay(Date endDate, Date startDate) {
        // date1和date2都不能为null 否则返回0
        if (endDate == null || startDate == null) {
            return null;
        }
        long mss = endDate.getTime() - startDate.getTime();
        long days = mss / (1000 * 60 * 6024);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + ":" + hours + ":" + minutes + ":" + seconds;

    }


    /**
     * 日期相减 计算相差天数
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer formatSubDateForDate(Date endDate, Date startDate) {
        if (endDate == null || startDate == null) {
            return new Integer(0);
        }
        Calendar temp = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        temp.setTime(endDate);
        end.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DATE));
        temp.setTime(startDate);
        start.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DATE));

        double t = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24D);// 化为天数
        return new Integer((int) Math.floor(t));
        // return end.get(Calendar.DATE) - start.get(Calendar.DATE);// 获取相差天数

    }

    /**
     * 日期相减 计算相差分钟数
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer formatSubDateForMinutes(Date endDate, Date startDate) {
        double diff = subtractDate(endDate, startDate);
        double formatDiff = diff / (1000 * 60);
        return new Integer((int) Math.floor(formatDiff)); // 取相差分钟数，向下取整
    }

    /**
     * 根据天数小时数，返回计算后的绝对时间
     *
     * @param limitDay
     * @param hour
     * @param baseDate
     * @return
     */
    public static Date getWorkingDateByDayAndHour(Integer limitDay, Integer hour, Date baseDate) {
        Date result = null;
        int idx = 0;
        int ids = 0;
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        while (idx < limitDay) {
            c.add(Calendar.DAY_OF_YEAR, 1);

            if (!checkWeekend(c.getTime())) {
                idx++;
                result = c.getTime();
            }
        }
        while (ids < hour) {
            c.add(Calendar.HOUR_OF_DAY, 1);

            if (!checkWeekend(c.getTime())) {
                ids++;
                result = c.getTime();
            }
        }
        return result;
    }

    /**
     * 根据分钟数，返回计算后的绝对时间
     *
     * @param minute
     * @param baseDate
     * @return
     */
    public static Date getWorkingDateByMinute(Integer minute, Date baseDate) {
        Date result = null;
        int idx = 0;
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        while (idx < minute) {
            c.add(Calendar.MINUTE, 1);

            if (!checkWeekend(c.getTime())) {
                idx++;
                result = c.getTime();
            }
        }

        return result;
    }

    /**
     * @Description: 返回多少分钟后的时间
     * @Param: [minute, baseDate]
     * @return: java.util.Date
     * @Date: 2018/11/30
     */
    public static Date getAfterDateByMinute(Integer minute, Date baseDate) {
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    /**
     * 以下为计费用到的时间相关：
     */
    /**
     * 替换日期(oldDate,newDate)，把oldDate的日期换成newDate的,返回newdate的ymd +old的hms的新日期，计费用到
     * 计费用到
     *
     * @param oldTime
     * @param newDate
     * @return
     */
    public static Date replaceYmdWithNew(String oldTime, Date newDate) {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        int startYear = DateUtil.getYear(calendar);
        int startMonth = DateUtil.getMonth(calendar) + 1; //!!!!!!!要加1
        int startDay = DateUtil.getDay(calendar);
        String newtime = startYear + "-" + startMonth + "-" + startDay + " " + oldTime;
        return DateUtil.strToDate(newtime);
        */
        return DateUtil.strToDate(DATE_FORMAT.format(newDate) + " " + oldTime);

    }


    /**
     * 是否节假日
     *
     * @param currDate
     * @return
     */
    public static boolean isHoliday(Date currDate) {
        if (isWeekend(currDate)) return true;
        //if (currDate in 节假日表) return true;
        return false;
    }

    public static boolean isHoliday(String strCurrDate) {
        return false;
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public static java.sql.Date getSystemDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTime().getTime());
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static Timestamp getSystemTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * 获取当前日期 设置时分秒
     *
     * @return
     */
    public static Date setHourMinuteSecond(String time) {
        if (StringUtils.isBlank(time) || !time.contains(":")) {
            return null;
        }
        String[] timeArray = time.split(":");
        if (null == timeArray || timeArray.length != 3) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, new Integer(timeArray[0]));
        cal.set(Calendar.MINUTE, new Integer(timeArray[1]));
        cal.set(Calendar.SECOND, new Integer(timeArray[2]));
        cal.set(Calendar.MILLISECOND, 0);
        return new Date(cal.getTime().getTime());
    }

    /**
     * 根据指定的日期，增加或者减少天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 根据指定的日期，类型，增加或减少数量
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * @Description: date转LocalDateTime
     * @Param: [date]
     * @return: java.updateTime.LocalDateTime
     * @Date: 2019-5-24
     */
    public static LocalDateTime dateToFormatLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @Description: LocalDateTime转Date
     * @Param: [localDateTime]
     * @return: java.util.Date
     * @Date: 2019-5-24
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime stringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, LOCAL_DATE_TIME_FORMAT);
    }

    /**
     * @Description: 计算时间段
     * @Param: [startTime, endTime]
     * @return: java.lang.String
     * @Date: 2018/10/11
     */
    public static String computeTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return "";
        }
        java.time.Duration duration = java.time.Duration.between(startTime, endTime);
        long stopTime = duration.toMillis();
        // 计算差多少天
        long day = stopTime / DateUtil.DAY;
        // 计算差多少小时
        long hour = stopTime % DateUtil.DAY / DateUtil.HOURS;
        // 计算差多少分钟
        long min = stopTime % DateUtil.DAY % DateUtil.HOURS / DateUtil.MINUTE;
        if (day > 0)
            return day + "天" + hour + "小时" + min + "分";
        return hour + "小时" + min + "分";
    }

    //分钟
    public static String getTime(Integer stopTime) {
        if (null == stopTime) {
            return "";
        }
        // 计算差多少天
        long day = stopTime / (60 * 24);
        // 计算差多少小时
        long hour = stopTime % (60 * 24) / 60;
        // 计算差多少分钟
        long min = stopTime % (60 * 24) % 60;
        if (day > 0)
            return day + "天" + hour + "小时" + min + "分";
        if (hour > 0) {
            return hour + "小时" + min + "分";
        }
        return stopTime + "分";
    }
}
