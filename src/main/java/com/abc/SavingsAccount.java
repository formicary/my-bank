package com.abc;

import java.text.DecimalFormat;
import java util.Date;
import java.util.List;

public class SavingsAccount extends Account () {
            private double iThreshold; //interest threshold
            private double iAboveThreshold;//interest above threshold
            private double iBelowThreshold;//interest below threshold

        public SavingsAccount()
        {
            super();
            this.iThreshold = 1000;
            this.iAboveThreshold = 0.001;
            this.iBelowInterest = 0.002;
            super.setAccountName("Savings Account");
        }

        @Override
        public double iEarned()
        {
            double iEarn = 0;
            double temporaryBalance = 0;
            double totalPeriod = 0;
            List<Transaction> transactions = getTransactions();
            for (int j = 0; j <transactions.size() -1; j++)
            {
            temporaryBalance += transactions.get(j).getAmount();
            Date from transactions.get(j).getDate();
            Date to = transactions.get(j+1).getDate();
            totalPeriod = temporaryBalance*Math.pow(1 + yearlyIR / 365, DateProvider.getInstance().diffDays(from, to));
            }
            Date dateNow = DateProvider.getInstance().dateNow();
            Date lastTransactionDate = transactions.get(transactions.size() - 1).getDate();
            temporaryBalance += transactions.get(transaction.size() -1).getAmount();
            totalPeriod = temporaryBalance * Math.pow(1 + yearlyIR/365, DateProvider.getInstance().diffDays(lastTransactionDate, now));
            iEarn += (totalPeriod - temporaryBalance);
            DecimalFormat twoDP = new DecimalFormat("#.##"); //sets the decimal format to two decimal places
            Double.ValueOf(twoDP.format(iEarn));
        }
        public double getAboveIntrest()
        {
            return iAboveInterest;
        }
        public void setAboveInterest()
        {
            this.iAboveInterest = iAboveInterest;
        }
        public double getBelowInterest()
        {
            return iBelowInterest;
        }
        public void setBelowInterest()
        {
            this.iBelowInterest = iBelowInterest;
        }

}

