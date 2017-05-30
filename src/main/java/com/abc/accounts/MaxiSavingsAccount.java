package com.abc.accounts;

import com.abc.Account;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;


public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        this.transactions = Maps.newLinkedHashMap();
        this.balance = 0;
    }

    public double interestEarned() {
        double amount = getBalance();
        //Check if it has been less than 10 days since the last transaction
        Date lastTransactionDate = this.getLastTransactionDate();
        DateTime currentDate = DateTime.now();

        int difference = Days.daysBetween(new LocalDate(lastTransactionDate),
                new LocalDate(currentDate)).getDays();
        if (difference >= 10) {
            return amount * 0.05;
        }
        return amount * 0.001;

    }
}

