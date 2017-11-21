package com.abc;

import java.text.DecimalFormat;
import java util.Date;
import java.util.List;

public class MaxiSavingsAccount extends Account ()
        {
            private double highIR = 0.05;
            private double lowIR = 0.001;
        }

        public MaxiSavingsAccount()
        {
            super();
            super.getAccountName("Maxi Savings Account");

        }

        @Override
        public double iEarn()
        {
            double interest = 0;
            double iEarn = 0; //interest earned
            double temporaryBalance = 0; //temp balance
            double totalPeriod = 0; //interest+balance after each transaction
            boolean overTenDays = false; //indicates whether or not any transactions occured in the past ten days
            List<Transaction> transactions = getTransactions();
            for(int j = transactions.size() - 1; j > 0; j--)
            {
                Date transactionTime = transaction.get(i).getDate();
                Date dateNow =  DateProvider.getInstance.dateNow();
                if (transactions.get(j).getAmount() < 0 && DateProvider.getInstance().diffDays(transactionTime, dateNow) <=10)
                {
                    overTenDays = true;
                    break;
                }

                else if (DateProvider.getInstance().diffDays(transactionTime, datenow) > 10)
                {
                    break;
                }

                if (overTenDays)
                {
                    interest = lowIR;
                } else
                {
                    interest = highIR;
                }
            }
        }