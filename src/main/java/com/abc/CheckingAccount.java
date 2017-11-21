package com.abc;

import java.text.DecimalFormat;
import java util.Date;
import java.util.List;

public class CheckingAccount extends Account (){
        {
            private double yearlyIR; //Yearly Interest Rate
        }
        public CheckingAccount()
        {
            super();
            super.createAccountName("Checking Account");
            this.yearlyIR=0.001;
        }
        public setInterestRate(double j)
        {
            yearlyIR = ;
        }
        public double getInterestRate()
        {
            return yearlyIR;
        }

        @override
        public double interestEarned()
        {
            double iEarn = 0; //Interest Earned
            double temporaryBalance = 0; //Temp balance with the interest after each transaction
            double totalPeriod = 0; //Interest + Balance after each transaction occurs
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

}