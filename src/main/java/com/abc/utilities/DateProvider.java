package com.abc.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vahizan on 07/08/2017.
 */
public class DateProvider {

    private static DateProvider dateTime = new DateProvider();
    private DateProvider(){}
    public static DateProvider getInstance(){
        return dateTime;
    }
    public long generateTimestamp(){
        return Calendar.getInstance().getTimeInMillis();
    }
    public String formatDatetime(String format,Locale locale){
        SimpleDateFormat formatDate = new SimpleDateFormat(format,locale);
        return formatDate.format(Calendar.getInstance().getTime());
    }
    public String generateDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(Calendar.getInstance().getTime());
    }
    public int daysBetween(Date dateFrom,Date dateTo){
        long dateFromValue = dateFrom.getTime();
        long dateToValue=dateTo.getTime();
        return (int)((dateToValue-dateFromValue)/(1000*3600*24));
    }
    public Date stringToDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date date = new Date();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Cannot Find Last Withdrawal Date");
        }
        return date;
    }
}
