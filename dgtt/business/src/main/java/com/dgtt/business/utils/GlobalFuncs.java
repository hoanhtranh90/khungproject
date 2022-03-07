/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author abc
 */
public class GlobalFuncs {


    public static Date parseDay(final String day) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(day);
        return dt;
    }

    public static boolean isValidDay(final String day) {
        try {
            parseDay(day);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static long diffDay(long t) {
        // cal.set(2019, 1, 1, 0, 0, 0); /// bat dau tu thang 2
        return TimeUnit.DAYS.convert((t - 1548954000692L), TimeUnit.MILLISECONDS);
    }

}
