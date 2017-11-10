package com.liubowen.socketiomahjong.util.time;

import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author liubowen
 * @date 2017/11/10 1:53
 * @description 时间工具类
 */
public class TimeUtil {

    private final static long defaultOffsetInMillis = TimeZone.getDefault().getRawOffset();

    private final static ZoneOffset defaultOffset = ZoneOffset.ofTotalSeconds((int) TimeUnit.MILLISECONDS.toSeconds(defaultOffsetInMillis));

    /** 时间源 */
    private static TimeSource source = new DefaultStandardTimeSource(defaultOffsetInMillis);

    /**
     * 修改时间，测试使用
     * @param currentTimeMillis 修改的时间
     */
    public static void changeCurrentTimeForTest(long currentTimeMillis) {
        source = new HackedTimeSource(source.timeDisfference, currentTimeMillis);
    }

    /** 当前时间，单位：毫秒 */
    public static long currentTimeMillis() {
        return source.currentTimeMillis();
    }

    /**
     * 往后推几小时
     * @param hours
     */
    public static void changeTimeDisfference(int hours) {
        source = new DefaultStandardTimeSource(defaultOffsetInMillis + TimeUnit.HOURS.toMillis(hours));
    }

    /** 当前时间，单位：秒 */
    public static long currentTimeSeconds(){
        return TimeUnit.MILLISECONDS.toSeconds(source.currentTimeMillis());
    }

    /** 当前时间，单位：天 */
    public static long currentTimeDays(){
        return millisToDays(source.currentTimeMillis());
    }

    /** 将毫秒转换成天 */
    public static int millisToDays(long millis) {
        return (int) TimeUnit.MILLISECONDS.toDays(millis + source.timeDisfference);
    }

    /** 将秒转换成天 */
    public static int secondsToDays(long seconds) {
        return (int) TimeUnit.MILLISECONDS.toDays(TimeUnit.SECONDS.toMillis(seconds) + source.timeDisfference);
    }

    /** 将天转换成毫秒 */
    public static long daysToMillis(long days) {
        return TimeUnit.DAYS.toMillis(days) - source.timeDisfference;
    }

    /** 将天转换成秒 */
    public static long daysToSeconds(long days) {
        return TimeUnit.MILLISECONDS.toSeconds(TimeUnit.DAYS.toMillis(days) - source.timeDisfference);
    }

    //
    public static int minutesInHour() {
        return millisToMinutesInHour(currentTimeMillis());
    }
    public static int millisToMinutesInHour(long millis) {
        return (int) (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
    }

    public static int secondsToMinutesInHour(long seconds) {
        return (int) (TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds)));
    }

    //小时
    public static int hoursInDay() {
        return millisToHoursInDay(source.currentTimeMillis());
    }

    /** 当前时间，单位：毫秒，距离今天0点的小时数 */
    public static int millisToHoursInDay(long millis) {
        return (int) TimeUnit.MILLISECONDS.toHours(millis - daysToMillis(millisToDays(millis)));
    }

    /** 当前时间，单位：秒，距离今天0点的小时数 */
    public static int secondsToHoursInDay(long seconds) {
        return (int) TimeUnit.SECONDS.toHours(seconds - daysToSeconds(secondsToDays(seconds)));
    }

    /** 判断当前是周几 1-7 */
    public static int daysInWeek() {
        return daysToDaysInWeek(currentTimeDays());
    }

    /** 把天数转换成周 */
    public static int daysToDaysInWeek(long days) {
        return (int) ((days - 3) % 7);
    }

    /** 将毫秒转换成周 */
    public static int millisToDaysInWeek(long millis) {
        return daysToDaysInWeek(millisToDays(millis));
    }
}
