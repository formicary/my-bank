package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateProvider {
    private static DateProvider instance = null;

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        System.out.print(timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS));
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public static boolean numberOfDaysPassed(int days,List<Transaction> transactions)
    {
        for(Transaction t: transactions){
            if(t.transactionType == 0) {
                Date now = DateProvider.getInstance().now();
                if(DateProvider.getDateDiff(now,t.transactionDate,TimeUnit.MINUTES) > 1440*days) //if there are more that *days* days until the last withdraw
                    return true;
            }
        }
        return false;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
