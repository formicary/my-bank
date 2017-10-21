package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public static boolean tenDaysPassed(int days, List<Transaction> transactions)    {
        for(Transaction t: transactions){
            int transactionType = t.getTransactionType();

            if(transactionType == 0) {
                Date now = DateProvider.getInstance().now();
                Date transactionDate = t.getTransactionDate();
                //get time difference in milliseconds
                long diffInMillis = now.getTime() - transactionDate.getTime();
                long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillis,TimeUnit.MILLISECONDS);

                //24 hours per day * 60 minutes per hour = 1440
                if(diffInMinutes > 1440*days)
                    return true;
            }
        }
        return false;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
