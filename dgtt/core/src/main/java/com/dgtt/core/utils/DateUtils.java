package com.dgtt.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {

    private DateUtils() {

    }

    public static final Date getDateCurrent() {
        LocalDateTime currentTime = LocalDateTime.now();
        return Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static final int getDayOfMonthCurrent() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static final int getDayOfYearCurrent() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    public static final int getHoursCurrent() {

        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static final int getMinuteCurrent() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static final long getTimeSpecificDate(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    public static final long distanceDayBetweenTwoDate(long date1, long date2) {
        return TimeUnit.DAYS.convert(Math.abs(date1 - date2), TimeUnit.MILLISECONDS);
    }

    public static final Long convertDayToLong(Long day) {
        return TimeUnit.MILLISECONDS.convert(day, TimeUnit.DAYS);
    }

    public static final String dateFormatToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    public static final String dateFormatCurrent(String pattern) {
        return new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime());
    }

    /**
     * Compare Two Date Specific To Minute. True if expiredDate > dateNow
     * (Specific to Minute).
     *
     * @param expiredDate
     * @param dateNow
     * @return
     */
    @SuppressWarnings("deprecation")
    public static final boolean compareTwoDateSpecificToMinute(Date expiredDate, Date dateNow) {

        int expiredHours = expiredDate.getHours();
        int expiredMinute = expiredDate.getMinutes();

        int hoursNow = dateNow.getHours();
        int minuteNow = dateNow.getMinutes();

        return ((expiredDate.compareTo(dateNow) < 0) || ((expiredDate.compareTo(dateNow) == 0)
                && ((expiredHours > hoursNow) || (expiredHours == hoursNow && expiredMinute > minuteNow))));

    }

    /**
     * Compare Two Date Specific To End Day. True if expiredDate compared to 12h
     * end of the day.
     *
     * @param expiredDate
     * @param dateNow
     * @return
     */
    @SuppressWarnings("deprecation")
    public static final boolean compareTwoDateSpecificToEndDay(Date expiredDate, Date dateNow) {

        int hoursNow = dateNow.getHours();
        int minuteNow = dateNow.getMinutes();

        return ((expiredDate.compareTo(dateNow) < 0)
                || ((expiredDate.compareTo(dateNow) == 0) && (hoursNow > 22 && minuteNow > 58)));

    }

    public static final Date getDateAllStartDate(Date input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }
    
    public static Date addDay(Date date, int days) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(c.DAY_OF_MONTH, days);
        }
        return c.getTime();
    }

    public static final Date getDateAllEndDate(Date input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }
    
    public static Date convertStringToDate(String date, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String convertDateToString(Date date, String formatDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate);
        if (date == null) {
            throw new IllegalArgumentException("Method argument must be not null");
        }
        return dateFormat.format(date);
    }

}
